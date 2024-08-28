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

import static com.ericsson.oss.services.fls.api.FileLookupConstants.*

import com.ericsson.cds.cdi.support.configuration.InjectionProperties
import com.ericsson.cds.cdi.support.rule.ImplementationInstance
import com.ericsson.cds.cdi.support.rule.ObjectUnderTest
import com.ericsson.cds.cdi.support.spock.CdiSpecification
import com.ericsson.oss.services.fls.db.dao.impl.PmRopInfoDaoImpl
import com.ericsson.oss.services.fls.db.dao.impl.UlsaInfoDaoImpl
import com.ericsson.oss.services.fls.db.dao.interfaces.PmRopInfoDao
import com.ericsson.oss.services.fls.db.dao.interfaces.UlsaInfoDao
import com.ericsson.oss.services.fls.db.entity.PMRopInfo
import com.ericsson.oss.services.fls.db.entity.ULSAInfo
import com.ericsson.oss.services.fls.endpoint.rest.FileLookupEntryPoint
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException
import spock.lang.Unroll

class FilterValidationSpec extends CdiSpecification {

    @ObjectUnderTest
    FileLookupEntryPoint fileLookupEntryPoint

    @ImplementationInstance
    PmRopInfoDao pmRopInfoDao = Mock(PmRopInfoDaoImpl)

    @ImplementationInstance
    UlsaInfoDao ulsaInfoDao = Mock(UlsaInfoDaoImpl)

    @Override
    def addAdditionalInjectionProperties(InjectionProperties injectionProperties) {
        injectionProperties.autoLocateFrom('com.ericsson.oss.services.fls')
    }

    def setup(){
        pmRopInfoDao.findPmRopInfoList(_) >> new ArrayList<PMRopInfo>()
        ulsaInfoDao.findUlsaInfoList(_) >> new ArrayList<ULSAInfo>()
    }

    @Unroll("process request when correct filter provided (#filter)")
    def 'process request when correct filter provided'() {
        given: 'mandatory filter'
        when: 'querying file lookup service'
            fileLookupEntryPoint.getResultByFilters(null,filter, null, null, null)
        then: 'FLS should not throw an exception'
            notThrown(FileLookupInvalidInputException)
        where:
            filter << [
                "dataType==ULSA",
                "dataType==PM*",
                "dataType==TOPOLOGY*",
                "dataType==TOPOLOGY_*",
                "dataType!=ULSA",
                "dataType!=PM_STATISTICAL",
                "(dataType==PM_STATISTICAL,dataType==PM_EBSL_ERBS);nodeType==ERBS;id=gt=12279842",
                "(dataType==PM_STATISTICAL,dataType==PM_EBSL_ERBS,dataType==PM_CTUM);nodeType==ERBS;id=gt=12279842",
                "dataType==PM_STATISTICAL;(nodeType==ERBS,nodeType==SGSN)",
                "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017-09-09T12:11:10",
                "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017-09-09T12:11:10+0100",
                "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017-09-09T12:11:10-0430",
                "dataType==PM*;id=gt=100;(nodeName==*LTE*,nodeName==*RNNODE*);fileSize=le=4096;fileCreationTimeInOss==2017-09-09T12:11:10-0430",
                "dataType==PM*;fileType==xml;fileLocation==*pmic1*",
                "dataType==PM*;startRopTimeInOss=ge=2016-09-09T12:11:10+0000;endRopTimeInOss=le=2017-09-09T12:11:10+0000",
                "dataType==PM*;fileType=in=(xml,txt)",
                "dataType=out=(PM_STATISTICAL,PM_CTUM)",
                "dataType=in=(PM_STATISTICAL,PM_CTUM)",
                "dataType=in=(ULSA)",
                "dataType==ULSA;radioUnit==RadioUnitName",
                "dataType==ULSA;sampleTime=ge=2016-09-09T12:11:10+0000"
                ]
    }

    @Unroll("throw exception when incorrect mandatory field present (#filter)")
    def 'throw exception when incorrect mandatory field present'() {
        given: 'incorrect filter clause'
        when: 'querying file lookup service'
            fileLookupEntryPoint.getResultByFilters(null,filter, null, null, null)
        then: 'FLS should throw an exception'
            FileLookupInvalidInputException exception = thrown()
            exception.message == exceptionMessage
            exception.getResponseStatus().statusCode == 400
            0 * pmRopInfoDao.findPmRopInfoList(_)
        where:
            filter                                                                     | exceptionMessage
            null                                                                       | INCORRECT_FILTER_CLAUSE
            ''                                                                         | INCORRECT_FILTER_CLAUSE
            'dataTYPE==PM*'                                                            | MANDATORY_FILTER_ABSENT
            'dataTYPE=PM*'                                                             | MANDATORY_FILTER_ABSENT
            'dataTYPE=[PM*'                                                            | MANDATORY_FILTER_ABSENT
            'dataType=UNOP=PM*'                                                        | INCORRECT_FILTER_CLAUSE
            'dataType=out=(PM_STATISTICAL,ULSA)'                                       | INVALID_QUERY_MULTIPLE_TABLES
            '(dataType==PM_STATISTICAL,dataType==ULSA)'                                | INVALID_QUERY_MULTIPLE_TABLES
            '(dataType==PM_STATISTICAL,dataType==ULSA,dataType==PM_CTUM)'              | INVALID_QUERY_MULTIPLE_TABLES
            "dataType==PM_STATISTICAL;unknownField==ERBS"                              | INCORRECT_FILTER_CLAUSE
            "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017/09/09"               | INCORRECT_DATE_FORMAT
            "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017-09-09T12.12.12"      | INCORRECT_DATE_FORMAT
            "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017-09-09T12-12-12Z"     | INCORRECT_DATE_FORMAT
            "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017-09-09T12-12-12z0100" | INCORRECT_DATE_FORMAT
            "dataType==PM_STATISTICAL;fileCreationTimeInOss==2017-09-09T12-12-12+01"   | INCORRECT_DATE_FORMAT
            "dataType==PM_STATISTICAL;sampleTime=ge=2016-09-09T12:11:10+0000"          | INCORRECT_FILTER_CLAUSE
            "dataType==ULSA;startRopTimeInOss=ge=2016-09-09T12:11:10+0000"             | INCORRECT_FILTER_CLAUSE
    }

}
