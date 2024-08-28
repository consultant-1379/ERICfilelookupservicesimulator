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
package com.ericsson.oss.services.fls.util;

/**
 * RequestType enum is used to identify the client.
 *
 * @author ehimnay
 */
public enum RequestType {

    PMIC("PMIC"), TOPOLOGY("TOPOLOGY"), ULSA("ULSA"), UNKNOWN("UNKNOWN");

    private final String client;

    RequestType(final String client) {
        this.client = client;
    }

    /**
     * This returns {@link #name()}
     *
     * @return String representation of enum constant
     */
    public String value() {
        return client;
    }

    /**
     * This returns enum equivalent of string.
     *
     * @return enum representation of string constant
     */
    public static RequestType getEnum(final String value) {
        for (final RequestType type : values()) {
            if (type.value().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return RequestType.UNKNOWN;
    }

}
