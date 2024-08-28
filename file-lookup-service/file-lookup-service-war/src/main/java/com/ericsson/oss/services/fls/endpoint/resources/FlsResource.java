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

import javax.ws.rs.core.Response;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.api.FlsDataType;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupException;

/**
 * Interface to client processor class.
 *
 */
public interface FlsResource {

    /**
     * Get Fls Data Type
     *
     */
     FlsDataType getFlsDataType();

    /**
     * Get the Response
     *
     * @param fileLookupRequest
     *            the list of resources to be read from the parent resource.
     */
    Response getResponse(FileLookupRequest fileLookupRequest) throws FileLookupException;
}
