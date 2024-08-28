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

import com.ericsson.oss.itpf.sdk.security.accesscontrol.SecurityViolationException;
import com.ericsson.oss.services.fls.endpoint.api.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * File lookup exception mapper for creating security violations
 */
@Provider
public class SecurityViolationExceptionMapper implements ExceptionMapper<SecurityViolationException> {

    private static final Logger logger = LoggerFactory.getLogger(SecurityViolationExceptionMapper.class);

    @Override
    public Response toResponse(final SecurityViolationException exception) {
        logger.debug("Received SecurityViolationException: [{}]", exception);
        return Response.status(Status.FORBIDDEN)
                .entity(new ResponseError(Status.UNAUTHORIZED, Status.UNAUTHORIZED.getReasonPhrase())).build();
    }
}