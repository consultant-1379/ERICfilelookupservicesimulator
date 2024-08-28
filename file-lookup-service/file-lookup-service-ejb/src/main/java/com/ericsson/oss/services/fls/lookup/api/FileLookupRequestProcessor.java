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
package com.ericsson.oss.services.fls.lookup.api;

import java.util.List;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupException;
import com.ericsson.oss.services.fls.lookup.model.PmFileMetadata;

/**
 * Processor class to parse a file lookup request and query data for response
 */
public interface FileLookupRequestProcessor {

    /**
     * Applies appropriate logic to parse the input parameters to produce a list of results.
     * 
     * @param FileLookupRequest
     *            request sent to File Lookup Service
     * @return - result of file lookup query
     */
    List<PmFileMetadata> processRequest(final FileLookupRequest FileLookupRequest) throws FileLookupException;
}
