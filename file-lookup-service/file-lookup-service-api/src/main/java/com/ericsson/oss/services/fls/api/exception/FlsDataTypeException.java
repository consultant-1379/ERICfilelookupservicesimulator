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

package com.ericsson.oss.services.fls.api.exception;

import javax.ws.rs.core.Response;

public class FlsDataTypeException extends Exception {

    public FlsDataTypeException(final String message) {
        super(message);
    }

    public FlsDataTypeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public Response.Status getResponseStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
