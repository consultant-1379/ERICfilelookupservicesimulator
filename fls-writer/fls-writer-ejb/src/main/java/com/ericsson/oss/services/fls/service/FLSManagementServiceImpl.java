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
package com.ericsson.oss.services.fls.service;

import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.PM_ROP_INFO_FILE_LOCATION;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.services.fls.InitiationRequest;
import com.ericsson.oss.services.fls.api.FLSDirectoryResourceService;
import com.ericsson.oss.services.fls.api.FLSManagementService;
import com.ericsson.oss.services.fls.api.FLSWriterService;
import com.ericsson.oss.services.fls.db.core.FLSQuery;
import com.ericsson.oss.services.fls.db.dao.interfaces.PmRopInfoDao;
import com.ericsson.oss.services.fls.dto.FLSDeleteRequest;
import com.ericsson.oss.services.fls.dto.FLSDeleteResponse;
import com.ericsson.oss.services.fls.util.FLSHelper;

/**
 * file lookup service initiation for pmic.
 *
 * @author ehimnay
 */
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FLSManagementServiceImpl implements FLSManagementService {

    private final AtomicBoolean busy = new AtomicBoolean(false);

    @Inject
    private Logger logger;

    @Inject
    private FLSWriterService flsService;

    @Inject
    private FLSDirectoryResourceService flsDirectoryResourceService;

    @Inject
    private PmRopInfoDao pmRopInfoDao;

    @Inject
    private FLSHelper flsHelper;

    /**
     * On time out, start the fls timer.
     */
    @Override
    @Lock(LockType.READ)
    public void initiate(final InitiationRequest init) {
        if (flsHelper.isFLSAllowedToUse()) {
            if (!busy.compareAndSet(false, true)) {
                logger.debug("Previous scheduler still on progress. Skipping current flow.");
                return;
            }
            try {
                final List<String> resourcePaths = init.getResourcePaths();
                logger.debug("PMIC NFS Resource Path(s) to process are {}", resourcePaths);
                final Resource defaultResource = flsDirectoryResourceService.initialize();
                if (defaultResource != null) {
                    for (final String resourcePath : resourcePaths) {
                        defaultResource.setURI(resourcePath);
                        flsService.createResourceInFls(resourcePath, defaultResource, init.getType());
                    }
                } else {
                    logger.info("FLS File System defaultResource couldn't be obtained. FLS Loading will skip be skipped.");
                }
            } finally {
                busy.set(false);
            }
        }
    }

    @Override
    @Lock(LockType.READ)
    public FLSDeleteResponse deletePMRopInfo(final FLSDeleteRequest FLSDeleteRequest) {
        final String nodeType = FLSDeleteRequest.getNodeType();
        final FLSDeleteResponse response = new FLSDeleteResponse();
        if (flsHelper.isFLSAllowedToUse()) {
            final List<FLSQuery> flsQueryList = new ArrayList<FLSQuery>() {
                {
                    add(new FLSQuery("fileCreationTimeInOss < :a", flsHelper.removeMinFromDate(FLSDeleteRequest.getRetentionPeriodInMinutes())));
                    add(new FLSQuery("dataType = :b", FLSDeleteRequest.getDataType().value()));
                    if ((nodeType != null) && (!nodeType.isEmpty())) {
                        add(new FLSQuery("nodeType = :c", nodeType));
                    }
                }
            };
            response.setNoOfRecordsDeleted(pmRopInfoDao.removePmRopInfo(flsQueryList));
        }
        return response;
    }

    @Override
    @Lock(LockType.READ)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Asynchronous
    public Future<Integer> deleteFLSFiles(final String path, final Integer retentionPeriodInMinutes) {
        final Integer filesDeleted = flsHelper.deleteFiles(path, retentionPeriodInMinutes);
        if (filesDeleted > 0) {
            logger.warn("{} files which are found in fls path {} for more than {} min(s) are deleted.", filesDeleted, path, retentionPeriodInMinutes);
        }
        return new AsyncResult<>(filesDeleted);
    }

    @Override
    @Lock(LockType.READ)
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String monitorPostgresFS(final String script, final Long timeoutInMilliSec, final String... argument) {
        return flsHelper.execute(script, timeoutInMilliSec, argument);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Lock(LockType.READ)
    public FLSDeleteResponse deleteTopologyInfoByLocation(final FLSDeleteRequest flsDeleteRequest) {
        final FLSDeleteResponse response = new FLSDeleteResponse();
        if (flsHelper.isFLSAllowedToUse()) {
            final List<FLSQuery> flsQueryList = new ArrayList<FLSQuery>() {
                {
                    add(new FLSQuery(PM_ROP_INFO_FILE_LOCATION + " = :a", flsDeleteRequest.getFileLocation()));
                }
            };
            response.setNoOfRecordsDeleted(pmRopInfoDao.removePmRopInfo(flsQueryList));
        }
        return response;
    }
}
