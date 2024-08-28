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
package com.ericsson.oss.services.fls.db.api;

/**
 * Definition of constants for File Lookup Service DB module
 */
public class FlsCommonConstants {

    private FlsCommonConstants() {
    }

    /**
     * SystemRecorder Constants
     */
    public static final String FLS = "File lookup service";
    public static final String THRESHOLD_REACHED = "DB_THRESHOLD_REACHED";
    public static final String ADDITIONAL_INFO_USAGE = "FLS DB usage reached 90%";
    public static final String STOP_FLS = "Stop FLS DB";

    public static final String SCRIPT_EXECUTION_FAILED = "SCRIPT_EXECUTION_FAILED";
    public static final String ADDITIONAL_INFO_SCRIPT = "Execution of external script fails";

}
