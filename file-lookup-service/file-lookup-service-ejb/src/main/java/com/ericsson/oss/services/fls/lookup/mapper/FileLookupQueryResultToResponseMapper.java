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

package com.ericsson.oss.services.fls.lookup.mapper;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.COMMA;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_SELECT_CLAUSE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INVALID_METADATA_CLASS_TYPE;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupMetadataUtil.getDefaultSelectedFields;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupMetadataUtil.getFileMetadataAccessor;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupMetadataUtil.getFileMetadataMutator;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupMetadataUtil.getMetaDataClassFromEntryClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupException;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;

/**
 * Bean provides support for mapping of database result into response
 */
public class FileLookupQueryResultToResponseMapper<T, C> {

    @Inject
    private Logger logger;

    /**
     * Map query results of file lookup request into list of beans returned to client
     *
     * @param entityClassList
     *            query result list
     * @param request
     *            request sent to File Lookup Service
     * @return query result list mapped to response beans
     * @throws FileLookupException
     */
    public List<C> mapQueryResultToResponse(final List<T> entityClassList, final FileLookupRequest request)
            throws FileLookupException {

        final Class<T> clazz = (Class<T>) request.getFlsDataType().getFlsDataTypeClass();
        if (isNotBlank(request.getSelect())) {
            final List<String> requestedFields = Arrays.asList(request.getSelect().split(COMMA));
            if (!requestedFields.isEmpty()) {
                logger.debug("Selection of fields provided by client : {}", requestedFields);
                return transformEntityClass(entityClassList, requestedFields, clazz);
            }
        }
        final List<String> defaultSelectedFields = getDefaultSelectedFields(clazz);
        logger.debug("Default fields selected for request without select clause : {}", defaultSelectedFields);
        return transformEntityClass(entityClassList, defaultSelectedFields, clazz);
    }

    /**
     * Transform database query results into a result beans returned to client
     *
     * @param entityClassList
     *            query result list
     * @param selectClauseFields
     *            fields to be mapped into response
     * @return
     * @throws FileLookupException
     */
    private List<C> transformEntityClass(final List<T> entityClassList, final List<String> selectClauseFields, final Class<T> clazz)
            throws FileLookupException {
        try {
            final List<C> resultList = new ArrayList<>();
            for (final T entityClass : entityClassList) {
                C fileMetadata;
                try {
                    fileMetadata = (C) getMetaDataClassFromEntryClass(clazz).newInstance();
                } catch (final InstantiationException e) {
                    throw new FileLookupInvalidInputException(INVALID_METADATA_CLASS_TYPE);
                }
                for (final String fieldName : selectClauseFields) {
                    final Method sourceMethod = getFileMetadataAccessor(clazz, fieldName);
                    final Method destinationMethod = getFileMetadataMutator(clazz, fieldName);
                    if (sourceMethod == null || destinationMethod == null) {
                        throw new FileLookupInvalidInputException(INCORRECT_SELECT_CLAUSE);
                    }
                    destinationMethod.invoke(fileMetadata, sourceMethod.invoke(entityClass));
                }
                resultList.add(fileMetadata);
            }
            logger.info("Number of query results : {}", resultList.size());
            return resultList;
        } catch (final InvocationTargetException | IllegalAccessException exception) {
            logger.debug("Field names provided in SELECT clause are not correct : {}", selectClauseFields);
            throw new FileLookupInvalidInputException(INCORRECT_SELECT_CLAUSE);
        }
    }
}
