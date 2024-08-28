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

package com.ericsson.oss.services.fls.db.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ericsson.oss.services.fls.db.core.FLSQuery;
import com.ericsson.oss.services.fls.db.entity.ULSAInfo;
import com.ericsson.oss.services.fls.exceptions.FlsDbException;

/**
 * This class handles all the ulsaInfo related methods.
 *
 * @author elucbol
 */
@Local
public interface UlsaInfoDao {

    /**
     * create list of ulsaInfo in db.
     *
     * @param ulsaInfoList
     *            list of ulsaInfoList
     * @throws FlsDbException
     */
    void createUlsaInfo(final List<ULSAInfo> ulsaInfoList) throws FlsDbException;

    /**
     * update list of ulsaInfo in db.
     *
     * @param ulsaInfoList
     *            list of ulsaInfoList
     * @throws FlsDbException
     */
    void updateUlsaInfo(final List<ULSAInfo> ulsaInfoList) throws FlsDbException;

    /**
     * Removes ulsaInfo from the db.
     *
     * @param flsQueryList
     *            properties for building dynamic query
     * @return no of record(s) deleted.
     * @throws FlsDbException
     */
    int removeUlsaInfo(final List<FLSQuery> flsQueryList) throws FlsDbException;

    /**
     * Get list of ulsaInfo. Method supports pagination of results.
     *
     * @param query
     *            the flsQuery
     * @return list of ulsaInfoList
     * @throws FlsDbException
     */
    List<ULSAInfo> findUlsaInfoList(final FLSQuery query) throws FlsDbException;
}
