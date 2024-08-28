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
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.endpoint.api.ResponseError;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupException;

/**
 * File lookup exception mapper for creating response from and exception
 */
@Provider
public class FileLookupDefinedExceptionMapper implements ExceptionMapper<FileLookupException> {

    private static final Logger logger = LoggerFactory.getLogger(FileLookupDefinedExceptionMapper.class);

    @Override
    public Response toResponse(final FileLookupException exception) {
        logger.debug("Received FileLookupException: ", exception);
        return Response.status(exception.getResponseStatus()).entity(new ResponseError(exception.getResponseStatus(), exception.getMessage()))
                .build();
    }
}
