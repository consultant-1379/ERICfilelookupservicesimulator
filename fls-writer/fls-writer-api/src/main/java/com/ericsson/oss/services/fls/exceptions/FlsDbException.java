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

package com.ericsson.oss.services.fls.exceptions;

/**
 * Class for FLS availability exception.
 *
 * @author sunrise
 */
public class FlsDbException extends Exception {
    private static final long serialVersionUID = -3275368727691776713L;

    public FlsDbException(final String message) {
        super(message);
    }

    public FlsDbException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
