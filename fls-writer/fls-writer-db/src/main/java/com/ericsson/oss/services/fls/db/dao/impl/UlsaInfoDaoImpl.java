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

package com.ericsson.oss.services.fls.db.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.ericsson.oss.services.fls.db.core.AbstractBaseDao;
import com.ericsson.oss.services.fls.db.core.FLSQuery;
import com.ericsson.oss.services.fls.db.dao.interfaces.UlsaInfoDao;
import com.ericsson.oss.services.fls.db.entity.ULSAInfo;
import com.ericsson.oss.services.fls.exceptions.FlsDbException;
import com.ericsson.oss.services.fls.interceptor.FlsAvailabilityInterceptor;

/**
 * implementation class acts as a single point entry for all ulsaInfo entries.
 *
 * @author elucbol
 */
@Stateless
@Interceptors(FlsAvailabilityInterceptor.class)
public class UlsaInfoDaoImpl extends AbstractBaseDao<String, ULSAInfo> implements UlsaInfoDao {

    @Override
    protected Class<ULSAInfo> getPersistentClass() {
        return ULSAInfo.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUlsaInfo(final List<ULSAInfo> ulsaInfoList) throws FlsDbException {
        for (final ULSAInfo ulsaInfo : ulsaInfoList) {
            add(ulsaInfo);
            logger.debug("Entity inserted successfully.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUlsaInfo(final List<ULSAInfo> ulsaInfoList) throws FlsDbException {
        for (final ULSAInfo ulsaInfo : ulsaInfoList) {
            update(ulsaInfo);
            logger.debug("Entity updated successfully.");
        }
    }

    @Override
    public int removeUlsaInfo(final List<FLSQuery> flsQueryList) throws FlsDbException {
        return delete(flsQueryList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ULSAInfo> findUlsaInfoList(final FLSQuery query) throws FlsDbException {
        logger.debug("File Lookup Service Request sent to DB. Received FLSQuery : {}", query);
        final List<ULSAInfo> results = findByQuery(query);
        logger.debug("DB find query executed. {} has been found", results.size());
        return results;
    }
}
