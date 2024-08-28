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
package com.ericsson.oss.services.fls.api;

import java.util.concurrent.Future;

import javax.ejb.Remote;

import com.ericsson.oss.itpf.sdk.core.annotation.EService;
import com.ericsson.oss.services.fls.InitiationRequest;
import com.ericsson.oss.services.fls.dto.FLSDeleteRequest;
import com.ericsson.oss.services.fls.dto.FLSDeleteResponse;

/**
 * This class handles the initiation of all FLS timer and delete pmRopInfo.
 *
 * @author ehimnay
 */
@EService
@Remote
public interface FLSManagementService {
    /**
     * initialize the fls timer.
     */
    void initiate(final InitiationRequest init);

    /**
     * Delete records from pmRopInfo based on delete request
     *
     * @param FLSDeleteRequest
     *            request object for deletion
     * @return deleteResponse
     */
    FLSDeleteResponse deletePMRopInfo(final FLSDeleteRequest FLSDeleteRequest);

    /**
     * Delete records from pmRopInfo based on delete request
     *
     * @param path
     *            path of the parent directory
     * @param retentionPeriodInMinutes
     *            retention period in minutes
     * @return no of files deleted
     */
    Future<Integer> deleteFLSFiles(final String path, final Integer retentionPeriodInMinutes);

    /**
     * Monitor fls usages in postgres file system.
     *
     * @param script
     *            script to be executed
     * @param timeoutInMilliSec
     *            timeout for the process.
     * @param argument
     *            list of argument to be passed to script.
     * @return response of the script.
     */
    String monitorPostgresFS(final String script, final Long timeoutInMilliSec, final String... argument);

    /**
     * Delete record from pmRopInfo based on file location
     *
     * @param flsDeleteRequest
     *            request object for deletion
     * @return deleteResponse
     */
    FLSDeleteResponse deleteTopologyInfoByLocation(final FLSDeleteRequest flsDeleteRequest);
}
