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
package com.ericsson.oss.services.fls.constants;

/**
 * FLS constants.
 *
 * @author ehimnay
 */
public abstract class FLSConstant {

    public static final String DLM_SCHEDULE_INVOKE = "dlm_schedule:invoke";
    public static final String REQUEST_TYPE = "requestType";
    public static final String FLS_RESOURCE_URI = "fls/";
    public static final String FLS_RESOURCE_URI_FOR_TOPOLOGY = "/topology/fls/";
    public static final String CSV_FILE = ".csv";
    public static final String STREAM_MAPPING_NAME = "data";
    public static final String PM_FILE_MODEL_FOR_FLS = "pmRopInfo.xml";
    public static final String FLS_PM_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String TIMEOUT_IN_MILLI_SEC = "timeoutInMilliSecond";
    public static final String FLS_PIB_SCRIPT = "script";
    public static final String FLS_PIB_SCRIPT_ARGUMENT = "argument";
    public static final String PMIC_NFS_SHARE_LIST="/ericsson/pmic1/,/ericsson/pmic2/";
}
