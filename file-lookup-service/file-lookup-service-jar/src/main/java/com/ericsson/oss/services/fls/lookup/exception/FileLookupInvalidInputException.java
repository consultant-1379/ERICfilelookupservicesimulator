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
package com.ericsson.oss.services.fls.lookup.exception;

import javax.ws.rs.core.Response;

/**
 * Exception to handle incorrect parameter values sent by client in request.
 */
public class FileLookupInvalidInputException extends FileLookupException {

    public FileLookupInvalidInputException(final String message) {
        super(message);
    }

    public FileLookupInvalidInputException(final IllegalFlsRequestArgument illegalFlsRequestArgument) {
        super(illegalFlsRequestArgument.getMessage());
    }

    public Response.Status getResponseStatus() {
        return Response.Status.BAD_REQUEST;
    }
}