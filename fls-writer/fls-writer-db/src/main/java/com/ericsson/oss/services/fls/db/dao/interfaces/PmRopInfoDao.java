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
package com.ericsson.oss.services.fls.db.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.ericsson.oss.services.fls.db.core.FLSQuery;
import com.ericsson.oss.services.fls.db.entity.PMRopInfo;

/**
 * This class handles all the pmRopInfo related methods.
 *
 * @author ehimnay
 */
@Local
public interface PmRopInfoDao {

    /**
     * create list of pmRopInfo in db.
     *
     * @param pmRopInfoList
     *            list of pmRopInfoList
     */
    void createPmRopInfo(final List<PMRopInfo> pmRopInfoList);

    /**
     * update list of pmRopInfo in db.
     *
     * @param pmRopInfoList
     *            list of pmRopInfoList
     */
    void updatePmRopInfo(final List<PMRopInfo> pmRopInfoList);

    /**
     * get list of pmRopInfo from db for topology.
     * search for existing pmRopInfo in list
     * create/update pmRopInfo in db.
     *
     * @param pmRopInfoList
     *            list of pmRopInfoList
     */
    void createPmRopInfoForTopology(final List<PMRopInfo> pmRopInfoList);

    /**
     * Removes pmRopInfo from the db.
     *
     * @param flsQueryList
     *          properties for building dynamic query
     * @return no of record(s) deleted.
     */
    int removePmRopInfo(final List<FLSQuery> flsQueryList);

    /**
     * Get list of pmRopInfo. Method supports pagination of results.
     * 
     * @param query
     *            the flsQuery
     * @return list of pmRopInfoList
     */
    List<PMRopInfo> findPmRopInfoList(final FLSQuery query);

    /**
     * Get list of pmRopInfo for Topology.
     *
     * @param columnName
     *            the columnName
     * @param pattern
     *            the pattern
     * @return list of pmRopInfoList
     */
    List<PMRopInfo> findPmRopInfoListForTopology(final String columnName, final String pattern);

}
