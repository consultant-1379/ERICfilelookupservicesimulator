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

/**
 * Exception to handle incorrect arguments during parsing of filter clause
 */
public class IllegalFlsRequestArgument extends RuntimeException {

    public IllegalFlsRequestArgument(final String message) {
        super(message);
    }

}