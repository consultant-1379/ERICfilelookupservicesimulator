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
package com.ericsson.oss.services.fls.constant;

/**
 * FLS modelled configuration constants.
 *
 * @author ehimnay
 */
public class FLSModelledConfigConstants {

    private FLSModelledConfigConstants() {
    }

    public static final String PROP_PMIC_NFS_SHARE_LIST = "pmicNfsShareList";
    public static final String PROP_FLS_NO_OF_RESOURCE_TO_SCAN = "noOfResourceToScan";
    public static final String PROP_FLS_RESOURCE_BATCH_SIZE = "flsResourceBatchSize";
    public static final String PROP_STOP_ALL_OPERATION_ON_FLS_DB = "stopAllOperationOnFlsDB";
    public static final String PROP_FILE_RETENTION_PERIOD_IN_MINUTES = "fileRetentionPeriodInMinutes";

    public final class Topology {
        public static final String PROP_FLS_NO_OF_TOPOLOGY_RESOURCE_TO_SCAN = "noOfTopologyResourceToScan";
        public static final String PROP_FLS_TOPOLOGY_RESOURCE_BATCH_SIZE = "flsTopologyResourceBatchSize";
        public static final String PROP_TOPOLOGY_SERVICE_ROOT_LOCATION = "ENIQTopologyService_root_location";
    }
}
