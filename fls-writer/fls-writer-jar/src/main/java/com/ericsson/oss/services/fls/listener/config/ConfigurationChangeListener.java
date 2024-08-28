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
package com.ericsson.oss.services.fls.listener.config;

import static com.ericsson.oss.services.fls.constant.FLSModelledConfigConstants.*;
import static com.ericsson.oss.services.fls.constant.FLSModelledConfigConstants.Topology.PROP_FLS_NO_OF_TOPOLOGY_RESOURCE_TO_SCAN;
import static com.ericsson.oss.services.fls.constant.FLSModelledConfigConstants.Topology.PROP_FLS_TOPOLOGY_RESOURCE_BATCH_SIZE;
import static com.ericsson.oss.services.fls.constants.FLSConstant.PMIC_NFS_SHARE_LIST;
import static com.ericsson.oss.services.fls.constant.FLSModelledConfigConstants.Topology.PROP_TOPOLOGY_SERVICE_ROOT_LOCATION;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
/**
 * Listener for fls Configuration parameter (PIB) changes
 *
 * @author ehimnay
 */
@ApplicationScoped
public class ConfigurationChangeListener extends AbstractConfigurationChangeListener {

   // @Inject
   // @Configured(propertyName = PROP_PMIC_NFS_SHARE_LIST)
    private String pmicNfsShareList=PMIC_NFS_SHARE_LIST;

   // @Inject
   // @Configured(propertyName = PROP_TOPOLOGY_SERVICE_ROOT_LOCATION)
    private String ENIQTopologyService_root_location;

   // @Inject
 //   @Configured(propertyName = PROP_FLS_NO_OF_RESOURCE_TO_SCAN)
    private Integer noOfResourceToScan=30;

   // @Inject
  //  @Configured(propertyName = PROP_FLS_NO_OF_TOPOLOGY_RESOURCE_TO_SCAN)
    private Integer noOfTopologyResourceToScan;

   // @Inject
   // @Configured(propertyName = PROP_FLS_RESOURCE_BATCH_SIZE)
    private Integer flsResourceBatchSize=30;

   // @Inject
    //@Configured(propertyName = PROP_FLS_TOPOLOGY_RESOURCE_BATCH_SIZE)
    private Integer flsTopologyResourceBatchSize;

   // @Inject
   // @Configured(propertyName = PROP_STOP_ALL_OPERATION_ON_FLS_DB)
    private Boolean stopAllOperationOnFlsDB=false;

  //  @Inject
  //  @Configured(propertyName = PROP_FILE_RETENTION_PERIOD_IN_MINUTES)
    private Integer fileRetentionPeriodInMinutes;

    /**
     * Listens for PROP_PMIC_NFS_SHARE_LIST changes
     *
     * @param newValueForPmicNfsShareList
     *            - new value for PMIC Network File System Share list
     */
    /*void listenToPmicNfsShareList(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_PMIC_NFS_SHARE_LIST) final String newValueForPmicNfsShareList) {
        logChange(PROP_PMIC_NFS_SHARE_LIST, pmicNfsShareList, newValueForPmicNfsShareList);
        this.pmicNfsShareList = newValueForPmicNfsShareList;
    }*/

    /**
     * Listens for PROP_TOPOLOGY_SERVICE_ROOT_LOCATION changes
     *
     * @param newValueForENIQTopologyService_root_location
     *            - new value for Topology Network File System Share list
     */
    /*void listenToENIQTopologyService_root_location(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_TOPOLOGY_SERVICE_ROOT_LOCATION) final String newValueForENIQTopologyService_root_location) {
        logChange(PROP_TOPOLOGY_SERVICE_ROOT_LOCATION, ENIQTopologyService_root_location, newValueForENIQTopologyService_root_location);
        this.ENIQTopologyService_root_location = newValueForENIQTopologyService_root_location;
    }*/

    /**
     * Listens for PROP_FLS_NO_OF_RESOURCE_TO_SCAN changes.
     *
     * @param newValueForNoOfResourceToScan
     *            new value for no of resource to scan.
     */
    /*void listenToNoOfResourceToScan(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_FLS_NO_OF_RESOURCE_TO_SCAN) final Integer newValueForNoOfResourceToScan) {
        logChange(PROP_FLS_NO_OF_RESOURCE_TO_SCAN, this.noOfResourceToScan, newValueForNoOfResourceToScan);
        this.noOfResourceToScan = newValueForNoOfResourceToScan;
    }
*/
    /**
     * Listens for PROP_FLS_NO_OF_TOPOLOGY_RESOURCE_TO_SCAN changes.
     *
     * @param newValueForNoOfTopologyResourceToScan
     *            new value for no of Topology resource to scan.
     */
    /*void listenToNoOfTopologyResourceToScan(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_FLS_NO_OF_TOPOLOGY_RESOURCE_TO_SCAN) final Integer newValueForNoOfTopologyResourceToScan) {
        logChange(PROP_FLS_NO_OF_TOPOLOGY_RESOURCE_TO_SCAN, this.noOfTopologyResourceToScan, newValueForNoOfTopologyResourceToScan);
        this.noOfTopologyResourceToScan = newValueForNoOfTopologyResourceToScan;
    }*/

    /**
     * Listens for PROP_FLS_RESOURCE_BATCH_SIZE changes.
     *
     * @param newValueForFlsResourceBatchSize
     *            new value for fls resource batch size.
     */
   /* void listenToFlsResourceBatchSize(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_FLS_RESOURCE_BATCH_SIZE) final Integer newValueForFlsResourceBatchSize) {
        logChange(PROP_FLS_RESOURCE_BATCH_SIZE, this.flsResourceBatchSize, newValueForFlsResourceBatchSize);
        this.flsResourceBatchSize = newValueForFlsResourceBatchSize;
    }*/

    /**
     * Listens for PROP_FLS_TOPOLOGY_RESOURCE_BATCH_SIZE changes.
     *
     * @param newValueForFlsTopologyResourceBatchSize
     *            new value for fls Topology resource batch size.
     */
    /*void listenToFlsTopologyResourceBatchSize(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_FLS_TOPOLOGY_RESOURCE_BATCH_SIZE) final Integer newValueForFlsTopologyResourceBatchSize) {
        logChange(PROP_FLS_TOPOLOGY_RESOURCE_BATCH_SIZE, this.flsTopologyResourceBatchSize, newValueForFlsTopologyResourceBatchSize);
        this.flsTopologyResourceBatchSize = newValueForFlsTopologyResourceBatchSize;
    }
*/
    /**
     * Listens for PROP_STOP_ALL_OPERATION_ON_FLS_DB changes.
     *
     * @param newValueForStopAllOperationOnFlsDB
     *            new value for stop all operations on fls db.
     */
   /* void listenToStopAllOperationOnFlsDB(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_STOP_ALL_OPERATION_ON_FLS_DB) final Boolean newValueForStopAllOperationOnFlsDB) {
        logChange(PROP_STOP_ALL_OPERATION_ON_FLS_DB, this.stopAllOperationOnFlsDB, newValueForStopAllOperationOnFlsDB);
        this.stopAllOperationOnFlsDB = newValueForStopAllOperationOnFlsDB;
    }*/

    /**
     * Listens for PROP_FILE_RETENTION_PERIOD_IN_MINUTES changes.
     *
     * @param newValueForFileRetentionPeriodInMinutes
     *            new value for file retention period in minutes.
     */
   /* void listenToSFSRetentionPeriodInMinutes(
            @Observes @ConfigurationChangeNotification(propertyName = PROP_FILE_RETENTION_PERIOD_IN_MINUTES) final Integer newValueForFileRetentionPeriodInMinutes) {
        logChange(PROP_FILE_RETENTION_PERIOD_IN_MINUTES, this.fileRetentionPeriodInMinutes, newValueForFileRetentionPeriodInMinutes);
        this.fileRetentionPeriodInMinutes = newValueForFileRetentionPeriodInMinutes;
    }
*/
    public String getPmicNfsShareList() {
        return pmicNfsShareList;
    }

    public String getENIQTopologyService_root_location() {
        return ENIQTopologyService_root_location;
    }

    public Integer getNoOfResourceToScan() {
        return noOfResourceToScan;
    }

    public Integer getNoOfTopologyResourceToScan() {
        return noOfTopologyResourceToScan;
    }

    public Integer getFlsResourceBatchSize() {
        return flsResourceBatchSize;
    }

    public Integer getFlsTopologyResourceBatchSize() {
        return flsTopologyResourceBatchSize;
    }

    public Boolean isStopAllOperationOnFlsDB() {
        return stopAllOperationOnFlsDB;
    }

    public Integer getFileRetentionPeriodInMinutes() {
        return fileRetentionPeriodInMinutes;
    }

}
