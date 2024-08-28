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

import static com.ericsson.oss.services.fls.api.FileLookupConstants.INVALID_LIMIT_VALUE
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INVALID_PAGINATION_DATA

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

class PaginationDataValidationSpec extends CdiSpecification {

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

    @Unroll("process request when pagination parameters are correct (limit=#limit, offset=#offset)")
    def 'process request when pagination parameters are correct'() {
        given: 'request with correct order by'
        when: 'querying file lookup service'
            fileLookupEntryPoint.getResultByFilters(null, "dataType==PM*", limit, offset, null)
        then: 'FLS should not throw an exception'
            notThrown(FileLookupInvalidInputException)
        where:
            limit   | offset
            null    | null
            "10000" | "0"
            "10000" | "1000"
    }

    @Unroll("throw exception when pagination parameters are incorrect (limit=#limit, offset=#offset)")
    def 'throw exception when pagination parameters are incorrect'() {
        given: 'request with correct order by'
        when: 'querying file lookup service'
            fileLookupEntryPoint.getResultByFilters(null, "dataType==PM*", limit, offset, null)
        then: 'FLS should not throw an exception'
            FileLookupInvalidInputException exception = thrown()
            exception.message == exceptionMessage
            exception.getResponseStatus().statusCode == 400
            0 * pmRopInfoDao.findPmRopInfoList(_)
        where:
            limit      | offset | exceptionMessage
            "10001"    | "0"    | INVALID_LIMIT_VALUE
            "thousand" | "0"    | INVALID_PAGINATION_DATA
            "10000"    | "ten"  | INVALID_PAGINATION_DATA
            "1000"     | null   | INVALID_PAGINATION_DATA
    }

}
