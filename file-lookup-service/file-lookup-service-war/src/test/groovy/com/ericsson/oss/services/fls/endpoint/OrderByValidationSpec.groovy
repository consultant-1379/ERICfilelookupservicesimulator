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
package com.ericsson.oss.services.fls.endpoint

import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_ORDER_BY_CLAUSE

import com.ericsson.cds.cdi.support.configuration.InjectionProperties
import com.ericsson.cds.cdi.support.rule.ImplementationInstance
import com.ericsson.cds.cdi.support.rule.ObjectUnderTest
import com.ericsson.cds.cdi.support.spock.CdiSpecification
import com.ericsson.oss.services.fls.api.FileLookupRequest
import com.ericsson.oss.services.fls.db.dao.impl.PmRopInfoDaoImpl
import com.ericsson.oss.services.fls.db.dao.interfaces.PmRopInfoDao
import com.ericsson.oss.services.fls.db.entity.PMRopInfo
import com.ericsson.oss.services.fls.endpoint.rest.FileLookupEntryPoint
import com.ericsson.oss.services.fls.endpoint.util.FileLookupRequestBuilder
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException

import spock.lang.Unroll

class OrderByValidationSpec extends CdiSpecification {

    @ObjectUnderTest
    FileLookupEntryPoint fileLookupEntryPoint

    @ImplementationInstance
    PmRopInfoDao pmRopInfoDao = Mock(PmRopInfoDaoImpl)

    @Override
    def addAdditionalInjectionProperties(InjectionProperties injectionProperties) {
        injectionProperties.autoLocateFrom('com.ericsson.oss.services.fls')
    }

    def setup(){
        pmRopInfoDao.findPmRopInfoList(_) >> new ArrayList<PMRopInfo>()
    }

    @Unroll("process request when order by clause is correct (#orderBy)")
    def 'process request when order by clause is correct'() {
        given: 'request with correct order by'
        when: 'querying file lookup service'
            fileLookupEntryPoint.getResultByFilters(null, "dataType==PM*", null, null, orderBy)
        then: 'FLS should not throw an exception'
            notThrown(FileLookupInvalidInputException)
        where:
            orderBy                | _
            "id asc"               | _
            "id desc"              | _
            "id desc,nodeName asc" | _
    }

    @Unroll("throw exception when order by clause is incorrect (#orderBy)")
    def 'throw exception when order by clause is incorrect'() {
        given: 'request with correct order by'
        when: 'querying file lookup service'
            fileLookupEntryPoint.getResultByFilters(null, "dataType==PM*", null, null, orderBy)
        then: 'FLS should not throw an exception'
            FileLookupInvalidInputException exception = thrown()
            exception.message == INCORRECT_ORDER_BY_CLAUSE
            exception.getResponseStatus().statusCode == 400
            0 * pmRopInfoDao.findPmRopInfoList(_)
        where:
            orderBy                           | _
            "idasc"                           | _
            "id ascs"                         | _
            "nodeName asc,nodeTypedesc"       | _
            "nodeSSSSSName asc,nodeType desc" | _
    }

}
