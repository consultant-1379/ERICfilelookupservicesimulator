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
package com.ericsson.oss.services.fls.rule;

import com.ericsson.oss.services.fls.listener.config.ConfigurationChangeListener;
import com.ericsson.oss.services.fls.util.FLSHelper;
import com.ericsson.oss.services.fls.util.FileResourceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Abstract FLS Rule
 *
 * @author ehimnay
 */
public abstract class AbstractFLSRule implements FLSRule {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private FLSHelper flsHelper;

    @Inject
    protected ConfigurationChangeListener configurationChangeListener;

    /**
     * Applies the following rules on pmic and topology resources
     * 1. scan files based on "no of resource to scan" and filter the pmic or topology supported files from unsupported files.
     * 2. create batch based on "pmic or topology batch size".
     *
     * @param resourcePath
     *            path of the resource.
     * @return list of file resource container
     */
    @Override
    public List<FileResourceContainer> applyRules(final String resourcePath) {
        List<FileResourceContainer> fileResourceContainerBatch = new ArrayList<>();
        if (resourcePath != null) {
            final List<String> resources = listFiles(resourcePath);
            fileResourceContainerBatch = createBatchRule(resources);
        } else {
            logger.debug("No file(s) found for the given location.");
        }
        return fileResourceContainerBatch;
    }

    /**
     * Returns a list of files from the resource directory after filtering.
     *
     * @param resourcePath
     *            path of the resource directory which need to be scan.
     * @return list of files from the resource directory.
     */
    private List<String> listFiles(final String resourcePath) {
        final int resourceToScan = getNoOfResourceToScan();
        final List<String> resources = new ArrayList<>(resourceToScan);
        final Path parentPath = Paths.get(resourcePath);
        if (Files.exists(parentPath)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(parentPath)) {
                final Iterator<Path> paths = directoryStream.iterator();
                for (int count = 1; paths.hasNext() && count <= resourceToScan; count++) {
                    final Path path = paths.next();
                    final String absolutePath = path.toAbsolutePath().toString();
                    if (absolutePath.endsWith(getFileType())) {
                        resources.add(absolutePath);
                    }
                }
            } catch (final IOException ioException) {
                logger.error("Exception during scan {} ", resourcePath, ioException.getMessage());
            }
        }
        if (!resources.isEmpty()) {
            logger.info("No of files read from {} are {}", resourcePath, resources.size());
        }
        return resources;
    }

    /**
     * Returns a list of resource.
     *
     * @param resources
     *            list of resource to be process.
     */
    private List<FileResourceContainer> createBatchRule(final List<String> resources) {
        final List<FileResourceContainer> fileResourceContainerBatch = new ArrayList<>();
        final int noOfResInABatch = getFlsResourceBatchSize();
        for (final List<String> recordsList : flsHelper.splitList(resources, noOfResInABatch)) {
            fileResourceContainerBatch.add(new FileResourceContainer(recordsList));
        }
        return fileResourceContainerBatch;
    }

    protected abstract int getNoOfResourceToScan();

    protected abstract String getFileType();

    protected abstract int getFlsResourceBatchSize();
}
