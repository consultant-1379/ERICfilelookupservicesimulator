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
package com.ericsson.oss.services.fls.processor.topology;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ericsson.oss.services.fls.annotation.ClientTypeAnnotation;
import com.ericsson.oss.services.fls.db.dao.interfaces.PmRopInfoDao;
import com.ericsson.oss.services.fls.db.entity.PMRopInfo;
import com.ericsson.oss.services.fls.processor.AbstractClientProcessor;
import com.ericsson.oss.services.fls.util.RequestType;

import java.util.List;

import static com.ericsson.oss.services.fls.constants.FLSConstant.PM_FILE_MODEL_FOR_FLS;

/**
 * TOPOLOGY Processor class read topology resources, validate, persist to postgres and delete it from NFS.
 *
 * @author ehimnay
 */
@ApplicationScoped
@ClientTypeAnnotation(clientType = RequestType.TOPOLOGY)
public class TopologyProcessor extends AbstractClientProcessor {

    @Inject
    private PmRopInfoDao pmRopInfoDao;

    @Override
    public void createPMRopInfo(final List<String> resourcesList)
    {
        final List<PMRopInfo> pmRopInfoList = createPMRopInfoList(resourcesList, PM_FILE_MODEL_FOR_FLS);
        if(!pmRopInfoList.isEmpty()) {
            logger.info("No of record to be persisted {}.", pmRopInfoList.size());
            pmRopInfoDao.createPmRopInfoForTopology(pmRopInfoList);
        }
    }
}
