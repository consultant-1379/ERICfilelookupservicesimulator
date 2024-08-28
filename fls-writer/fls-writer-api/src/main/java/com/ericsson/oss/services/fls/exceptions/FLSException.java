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
package com.ericsson.oss.services.fls.exceptions;

/**
 * Class for creating FLS exception.
 *
 * @author ehimnay
 */
public class FLSException extends Exception {
    private static final long serialVersionUID = -7715919196634888214L;

    public FLSException(final String message) {
        super(message);
    }

    public FLSException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
