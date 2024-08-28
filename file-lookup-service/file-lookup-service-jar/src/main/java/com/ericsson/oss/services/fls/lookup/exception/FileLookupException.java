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
 * Wrapper for exceptions if File Lookup Service
 */
public abstract class FileLookupException extends Exception {

    public FileLookupException(final String message) {
        super(message);
    }

    public FileLookupException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public abstract Response.Status getResponseStatus();
}

