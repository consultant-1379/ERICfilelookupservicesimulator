/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.services.fls.util;

import static com.ericsson.oss.services.fls.db.api.FlsCommonConstants.*;
import static com.ericsson.oss.services.fls.constants.FLSConstant.STREAM_MAPPING_NAME;
import static java.nio.file.FileVisitResult.CONTINUE;
import static org.apache.commons.lang.StringUtils.EMPTY;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ericsson.oss.itpf.sdk.recording.ErrorSeverity;
import com.ericsson.oss.services.fls.listener.config.ConfigurationChangeListener;
import com.ericsson.oss.itpf.sdk.recording.SystemRecorder;
import org.apache.commons.exec.*;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;

import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.itpf.sdk.resources.Resources;

/**
 * utility class for file lookup service.
 *
 * @author ehimnay
 */
@ApplicationScoped
public class FLSHelper {

    private final Map<String, StreamFactory> beanFactoryMap = new ConcurrentHashMap<>();

    @Inject
    private SystemRecorder systemRecorder;

    @Inject
    private Logger logger;

    @Inject
    private ConfigurationChangeListener configurationChangeListener;

    /**
     * split list by give size.
     *
     * @param list
     *            the list to split.
     * @param size
     *            the size of each splitted list.
     * @return a splitted lists.
     */
    public <T> List<List<T>> splitList(final List<T> list, final long size) {
        final List<List<T>> choppedList = new ArrayList<>();
        final int listSize = list.size();
        for (int index = 0; index < listSize; index += size) {
            choppedList.add(new ArrayList<>(list.subList(index, Math.min(listSize, index + (int) size))));
        }
        return choppedList;
    }

    /**
     * split list by give size.
     *
     * @param resource
     *            the resource to be load.
     * @return a inputStream instance of the resource.
     */
    public InputStream loadResource(final String resource) {
        final Resource resourceInCP = Resources.getClasspathResource(resource);
        InputStream inputStream = null;
        if (resourceInCP.exists()) {
            logger.debug("Model {} loaded successfully.", resource);
            inputStream = resourceInCP.getInputStream();
        } else {
            logger.error("Model {} could not located.", resource);
        }
        return inputStream;
    }

    /**
     * validate the resource against the provided model and parse it to entities.
     *
     * @param resource
     *            the resource to be read.
     * @param model
     *            the model to be used for validating the resource.
     * @throws IOException
     *             ioException when processing the beanIO model
     * @return a list of java objects.
     */
    public List<Object> unMarshall(final String resource, final String model) throws IOException {
        BeanReader beanReader = null;
        final List<Object> recordList = new ArrayList<>();
        try {
            final StreamFactory factory = getBeanIOFactory(model);
            beanReader = factory.createReader(STREAM_MAPPING_NAME, resource);
            Object record;
            while ((record = beanReader.read()) != null) {
                recordList.add(record);
            }
        } finally {
            if (beanReader != null) {
                beanReader.close();
            }
        }
        return recordList;
    }

    /**
     * convert list of object to list of entities.
     *
     * @param list
     *            the list to be convert.
     * @return a converted list.
     */
    public List convert(final List<Object> list) {
        return (ArrayList) list;
    }

    /**
     * Remove a number of minutes to a date returning a new date.
     *
     * @param retentionPeriodInMinutes
     *            no of min to be removed from current date.
     * @return a new date with removed days.
     */
    public Date removeMinFromDate(final int retentionPeriodInMinutes) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.add(Calendar.MINUTE, -retentionPeriodInMinutes);
        return calendar.getTime();
    }

    /**
     * create beanIO factory for the provided mapping file
     *
     * @param model
     *            name of the beanIO MappingFile.
     * @return a new instance of the beanFactory if not found in map else return the value from the map.
     */
    public StreamFactory getBeanIOFactory(final String model) throws IOException {
        logger.debug("size of the beanIOFactory map {}", beanFactoryMap.size());
        if (beanFactoryMap.containsKey(model)) {
            return beanFactoryMap.get(model);
        } else {
            final StreamFactory factory = StreamFactory.newInstance();
            factory.load(loadResource(model));
            beanFactoryMap.put(model, factory);
            return factory;
        }
    }

    /**
     * Delete files older than retention period.
     *
     * @param parentPath
     *            - directory path of the parent directory
     * @param retentionPeriodInMinutes
     *            - file retention period in minutes
     * @return no of files deleted
     */
    public int deleteFiles(final String parentPath, final int retentionPeriodInMinutes) {
        final Path directoryPath = Paths.get(parentPath);
        final AtomicInteger count = new AtomicInteger(0);
        if (Files.exists(directoryPath)) {
            try {
                Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(final Path path, final BasicFileAttributes attributes) {
                        try {
                            final long lastModifiedTimed = attributes.lastModifiedTime().toMillis();
                            if (isOlderThanRetentionPeriod(lastModifiedTimed, retentionPeriodInMinutes)) {
                                Files.delete(path);
                                count.incrementAndGet();
                                logger.debug("File {} last modified time {} deleted.", path, lastModifiedTimed);
                            }
                        } catch (final IOException ioException) {
                            logger.error("IOException while deleting file {} ", path, ioException.getMessage());
                        }
                        return CONTINUE;
                    }
                });
            } catch (final Exception exception) {
                logger.error("Exception while removing files ", exception.getMessage());
            }
        }
        return count.get();
    }

    private boolean isOlderThanRetentionPeriod(final long modifiedTime, final int retentionPeriodInMinutes) {
        final DateTime fileModifiedTime = new DateTime(modifiedTime);
        final DateTime currentTime = new DateTime(System.currentTimeMillis());
        return Minutes.minutesBetween(fileModifiedTime, currentTime).getMinutes() >= retentionPeriodInMinutes;
    }

    /**
     * execute external script.
     *
     * @param script
     *            - external script which need to be executed.
     * @param timeoutInMilliSec
     *            - timeout in milliseconds
     * @param argument
     *            - argument to the script.
     * @return result of the script.
     */
    public String execute(final String script, final long timeoutInMilliSec, final String... argument) {
        logger.debug("FLSHelper executing request for monitoringPostgresFS");

        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            final PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            final File file = new File(script);
            final CommandLine commandLine = new CommandLine(file.getAbsolutePath());
            for (final String arg : argument) {
                commandLine.addArgument(arg);
            }
            final DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(streamHandler);
            executor.setWorkingDirectory(file.getParentFile());
            executor.setWatchdog(new ExecuteWatchdog(timeoutInMilliSec));
            executor.execute(commandLine);
            return outputStream.toString();
        } catch (final ExecuteException executeException) {
            systemRecorder.recordError(THRESHOLD_REACHED, ErrorSeverity.ERROR, FLS, ADDITIONAL_INFO_USAGE, STOP_FLS);
            logger.error("File lookup service usages of postgres DB reached the threashold. stop fls.", executeException);
        } catch (final Exception exception) {
            systemRecorder.recordError(SCRIPT_EXECUTION_FAILED, ErrorSeverity.ERROR, FLS,ADDITIONAL_INFO_SCRIPT, script);
            logger.error("Exception message {} during execution of external script {} ", exception.getMessage(), script);
        }
        return EMPTY;
    }

    /**
     * check if FLS DB operation is stopped
     *
     * @return true or false
     */
    public boolean isFLSAllowedToUse(){
        if (configurationChangeListener.isStopAllOperationOnFlsDB()) {
            logger.info("FLS database migration in progress. Skipping current flow.");
            return false;
        }
        return true;
    }

}