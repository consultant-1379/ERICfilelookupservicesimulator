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
import com.ericsson.oss.services.fls.lookup.model.PmFileMetadata

import javax.ws.rs.core.Response
import spock.lang.Unroll

class FieldSelectionSpec extends CdiSpecification {

    @ObjectUnderTest
    FileLookupEntryPoint fileLookupEntryPoint

    @ImplementationInstance
    PmRopInfoDao pmRopInfoDao = Mock(PmRopInfoDaoImpl)

    def static Date exampleDate = new Date()
    def static String exampleFileLocation = "/eriscsson/pmic1/fls/file.xml"

    @Override
    def addAdditionalInjectionProperties(InjectionProperties injectionProperties) {
        injectionProperties.autoLocateFrom('com.ericsson.oss.services.fls')
    }

    def setup(){

        pmRopInfoDao.findPmRopInfoList(_) >> new ArrayList<PMRopInfo>(){{
            add(createResult())
        }}
    }

    @Unroll
    def 'process request when correct filter provided'() {
        given: 'file lookup service request'
        when: 'querying file lookup service'
            Response response = fileLookupEntryPoint.getResultByFilters(select, "dataType==PM*", null, null, null)

        then: 'FLS should not throw an exception'
            PmFileMetadata singleResult = response.getEntity().getList().get(0)
            singleResult.getId() == id
            singleResult.getDataType() == dataType
            singleResult.getFileLocation() == fileLocation
            singleResult.getFileCreationTimeInOss() == fileCreationTimeInOss
            singleResult.getNodeName() == nodeName
            singleResult.getNodeType() == nodeType
            singleResult.getFileSize() == fileSize
            singleResult.getFileType() == fileType
            singleResult.getEndRopTimeInOss() == endRopTimeInOss
            singleResult.getStartRopTimeInOss() == startRopTimeInOss

        where:
            select              | id   | dataType  | fileLocation        | fileCreationTimeInOss | nodeName         | nodeType | fileSize | fileType | endRopTimeInOss | startRopTimeInOss
            null                | 1    | "PM_STAT" | exampleFileLocation | exampleDate           | null             | null     | null     | null     | null            | null
            "id"                | 1    | null      | null                | null                  | null             | null     | null     | null     | null            | null
            "nodeName,fileSize" | null | null      | null                | null                  | "LTE01ERBS00001" | null     | 1024     | null     | null            | null

    }

    def createResult() {
        PMRopInfo pmRopInfo = new PMRopInfo();
        pmRopInfo.setId(1)
        pmRopInfo.setDataType("PM_STAT")
        pmRopInfo.setFileLocation(exampleFileLocation)
        pmRopInfo.setFileCreationTimeInOss(exampleDate)
        pmRopInfo.setNodeName("LTE01ERBS00001")
        pmRopInfo.setNodeType("ERBS")
        pmRopInfo.setFileSize(1024)
        pmRopInfo.setEndRopTimeInOss(exampleDate)
        pmRopInfo.setStartRopTimeInOss(exampleDate)
        return pmRopInfo
    }

}
