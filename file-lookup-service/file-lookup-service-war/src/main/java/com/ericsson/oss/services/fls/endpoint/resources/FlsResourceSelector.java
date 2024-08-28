/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.oss.services.fls.endpoint.resources;

import static org.apache.commons.lang.StringUtils.isEmpty;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.COMMA;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.DATA_TYPE_REGEX;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_FILTER_CLAUSE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INVALID_QUERY_MULTIPLE_TABLES;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.MANDATORY_FILTER_ABSENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.api.FlsDataType;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;

/**
 * This Class ProcessorSelector, selects an instance of FlsResource Class.
 *
 * @author ebialan
 */
@ApplicationScoped
public class FlsResourceSelector {
    private static final Logger logger = LoggerFactory.getLogger(FlsResourceSelector.class);

    @Any
    @Inject
    private Instance<FlsResource> flsResources;

    /**
     * Gets the instance of FlsResource.
     *
     * @param fileLookupRequest
     *            the requestType
     * @return single instance of FlsResource
     */
    public FlsResource select(final FileLookupRequest fileLookupRequest) throws FileLookupInvalidInputException {
        final String filter = fileLookupRequest.getFilter();
        final List<String> dataTypeFilters = getDataTypeFilters(filter);

        logger.debug("DataTypeFilters: {}", dataTypeFilters);

        for (final FlsResource flsResource : flsResources) {
            int numOfMatchingFound = 0;
            for (String dataTypeFilter: dataTypeFilters) {
                FlsDataType flsDataType = flsResource.getFlsDataType();
                numOfMatchingFound = dataTypeFilter.startsWith(flsDataType.getIdentifier()) ? ++numOfMatchingFound : numOfMatchingFound;
            }
            //No match found for the flsResource, go ahead with the next one
            if (numOfMatchingFound == 0) {
                continue;
            }
            //Partial match found for the flsResource
            if (numOfMatchingFound != dataTypeFilters.size()) {
                throw new FileLookupInvalidInputException(INVALID_QUERY_MULTIPLE_TABLES);
            }
            //FlsResource has been found
            fileLookupRequest.setFlsDataType(flsResource.getFlsDataType());
            return flsResource;
        }
        logger.error("No flsResource defined for provided dataTypeFilters {}.", dataTypeFilters);
        throw new FileLookupInvalidInputException(INCORRECT_FILTER_CLAUSE);
    }

    private List<String> getDataTypeFilters(final String filter) throws FileLookupInvalidInputException {
        if (isEmpty(filter)) {
            throw new FileLookupInvalidInputException(INCORRECT_FILTER_CLAUSE);
        }

        final List<String> dataTypeFilters = new ArrayList<>();
        final Matcher matcher = Pattern.compile(DATA_TYPE_REGEX).matcher(filter);
        while (matcher.find()) {
            dataTypeFilters.addAll(Arrays.asList(matcher.group(1).split(COMMA)));
        }

        if (dataTypeFilters.isEmpty()) {
            throw new FileLookupInvalidInputException(MANDATORY_FILTER_ABSENT);
        }

        return dataTypeFilters;
    }
}
