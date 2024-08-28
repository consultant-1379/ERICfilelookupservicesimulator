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
package com.ericsson.oss.services.fls.endpoint.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.endpoint.api.ResponseError;

/**
 * File lookup exception mapper for all uncatched exceptions
 */
@Provider
public class FileLookupGlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(FileLookupGlobalExceptionMapper.class);

    @Override
    public Response toResponse(final Throwable throwable) {
        logger.error("Received global exception: [{}]", throwable);
        return Response.serverError().entity(new ResponseError(Status.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR.getReasonPhrase())).build();
    }
}