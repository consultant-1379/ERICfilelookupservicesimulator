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
package com.ericsson.oss.services.fls.lookup.util;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.ASC_ORDER;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.DESC_ORDER;

/**
 * Utility class contains methods supports processing of file lookup requests.
 */
public class FileLookupUtil {

    /**
     * Check if correct sql order
     *
     * @param sqlOrder
     *            sorting type
     * @return true if sql order is correct
     */
    public static boolean isCorrectOrderType(final String sqlOrder) {
        final String sqlOrderUpperCase = sqlOrder.toUpperCase();
        return ASC_ORDER.equals(sqlOrderUpperCase) || DESC_ORDER.equals(sqlOrderUpperCase);
    }

}
