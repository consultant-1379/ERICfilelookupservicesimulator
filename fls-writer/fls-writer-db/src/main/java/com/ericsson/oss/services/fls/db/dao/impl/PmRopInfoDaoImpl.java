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
package com.ericsson.oss.services.fls.db.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import com.ericsson.oss.services.fls.db.util.NodeNameComparator;

import com.ericsson.oss.services.fls.db.core.AbstractBaseDao;
import com.ericsson.oss.services.fls.db.core.FLSQuery;
import com.ericsson.oss.services.fls.db.dao.interfaces.PmRopInfoDao;
import com.ericsson.oss.services.fls.db.entity.PMRopInfo;

import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.PM_ROP_INFO_DATA_TYPE;
import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.TOPOLOGY_PATTERN;

/**
 * implementation class acts as a single point entry for all pmRopInfo entries.
 *
 * @author ehimnay
 */
@Stateless
public class PmRopInfoDaoImpl extends AbstractBaseDao<String, PMRopInfo> implements PmRopInfoDao {

    @Override
    protected Class<PMRopInfo> getPersistentClass() {
        return PMRopInfo.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPmRopInfo(final List<PMRopInfo> pmRopInfoList) {
        for (final PMRopInfo pmRopInfo : pmRopInfoList) {
            add(pmRopInfo);
            logger.debug("Entity inserted successfully.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePmRopInfo(final List<PMRopInfo> pmRopInfoList) {
        for (final PMRopInfo pmRopInfo : pmRopInfoList) {
            update(pmRopInfo);
            logger.debug("Entity updated successfully.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPmRopInfoForTopology(final List<PMRopInfo> pmRopInfoList) {
        final List<PMRopInfo> topologyRopInfoList = findPmRopInfoList(PM_ROP_INFO_DATA_TYPE, TOPOLOGY_PATTERN);
        for (final PMRopInfo pmRopInfo : pmRopInfoList) {
            if(topologyRopInfoList != null && !topologyRopInfoList.isEmpty()) {
                final int index = Collections.binarySearch(topologyRopInfoList, pmRopInfo, new NodeNameComparator());
                if(index >= 0) {
                    pmRopInfo.setId(topologyRopInfoList.get(index).getId());
                }
            }
            update(pmRopInfo);
            logger.debug("Topology entity updated successfully.");
        }
    }

    @Override
    public int removePmRopInfo(final List<FLSQuery> flsQueryList) {
        return delete(flsQueryList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PMRopInfo> findPmRopInfoList(final FLSQuery query) {
        logger.debug("File Lookup Service Request sent to DB. Received FLSQuery : {}", query);
        final List<PMRopInfo> results = findByQuery(query);
        logger.debug("DB find query executed. {} has been found", results.size());
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PMRopInfo> findPmRopInfoListForTopology(final String columnName, final String pattern){
        return findPmRopInfoList(columnName, pattern);
    }

}
