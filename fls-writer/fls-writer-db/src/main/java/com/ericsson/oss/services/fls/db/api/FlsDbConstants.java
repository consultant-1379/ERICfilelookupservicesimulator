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
package com.ericsson.oss.services.fls.db.api;

/**
 * Definition of constants for File Lookup Service DB module
 */
public class FlsDbConstants {

    private FlsDbConstants() {
    }

    public static final String FLS_PU = "fls-pu";

    /**
     * JPQL Constants
     */
    public static final String ENTITY_ALIAS = "e";
    public static final String PARAMETER_DEFINITION_ALIAS = ":param";

    public static final String SELECT_FROM_CLAUSE = "SELECT " + ENTITY_ALIAS + " FROM ";
    public static final String WHERE_CLAUSE = " " + ENTITY_ALIAS + " WHERE ";

    public static final String PM_ROP_INFO_FILE_LOCATION = "fileLocation";
    public static final String PM_ROP_INFO_DATA_TYPE = "dataType";
    public static final String PM_ROP_INFO_NODE_NAME = "nodeName";
    public static final String TOPOLOGY_PATTERN = "'TOPOLOGY%'";

    public static final int ORDINAL_PARAMETER = 97;

}
