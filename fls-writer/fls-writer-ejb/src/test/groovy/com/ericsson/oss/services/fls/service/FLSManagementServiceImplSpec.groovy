/*------------------------------------------------------------------------------
*******************************************************************************
* COPYRIGHT Ericsson 2016
*
* The copyright to the computer program(s) herein is the property of
* Ericsson Inc. The programs may be used and/or copied only with written
* permission from Ericsson Inc. or in accordance with the terms and
* conditions stipulated in the agreement/contract under which the
* program(s) have been supplied.
******************************************************************************* *----------------------------------------------------------------------------*/

package com.ericsson.oss.services.fls.service

import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import javax.persistence.EntityManager
import javax.persistence.Query

import com.ericsson.cds.cdi.support.configuration.InjectionProperties
import com.ericsson.cds.cdi.support.rule.ImplementationInstance
import com.ericsson.cds.cdi.support.rule.ObjectUnderTest
import com.ericsson.cds.cdi.support.spock.CdiSpecification
import com.ericsson.oss.services.fls.InitiationRequest
import com.ericsson.oss.services.fls.constants.DataType
import com.ericsson.oss.services.fls.db.core.EntityManagerProvider
import com.ericsson.oss.services.fls.db.entity.PMRopInfo
import com.ericsson.oss.services.fls.dto.FLSDeleteRequest
import com.ericsson.oss.services.fls.dto.FLSDeleteResponse
import com.ericsson.oss.services.fls.listener.config.ConfigurationChangeListener
import com.ericsson.oss.services.fls.util.RequestType

import spock.lang.Unroll

class FLSManagementServiceImplSpec extends CdiSpecification {

    @ObjectUnderTest
    private FLSManagementServiceImpl flsManagementService

    @ImplementationInstance
    private EntityManagerProvider entityManagerProvider = Mock()

    @ImplementationInstance
    private EntityManager entityManager = Mock()

    @ImplementationInstance
    private Query query = Mock()

    @ImplementationInstance
    private ConfigurationChangeListener configurationChangeListener = Mock(ConfigurationChangeListener);

    final source = 'src/test/resources/'

    final target = source + 'input/'

    final resource = 'pmic_pmRopInfo.csv'

    final fileLocaation = '/ericsson/pmic1/lteTopology/SubNetwork=SubNetwork,MeContext=LTE02ERBS00001.xml'

    @Override
    def addAdditionalInjectionProperties(InjectionProperties injectionProperties) {
        injectionProperties.autoLocateFrom('com.ericsson.oss.services.fls')
    }

    def copyFileUsingStream() throws IOException {
        final File file = new File(target + resource)
        if (!file.exists())
            Files.copy(Paths.get(source + resource), Paths.get(target + resource))
    }

    def setup() {
        copyFileUsingStream()
        TimeUnit.SECONDS.sleep(5)
        entityManager.createQuery(_) >> query
        entityManagerProvider.getEntityManager() >> entityManager
        configurationChangeListener.isStopAllOperationOnFlsDB() >> false
        configurationChangeListener.flsResourceBatchSize >> 1
        configurationChangeListener.noOfResourceToScan >> 10
        configurationChangeListener.pmicNfsShareList >> target + ',/ericsson/pmic2/fls'
    }

    def 'when valid input without nodeType available remove resource in FLS'() {

        when: 'When create resource in fls is called'
            FLSDeleteRequest request = new FLSDeleteRequest(1, DataType.STATISTICAL)
            FLSDeleteResponse response = flsManagementService.deletePMRopInfo(request)

        then: 'resource should be deleted in FLS'
            response.getNoOfRecordsDeleted() >= 0
    }

    def 'when valid input available remove resource in FLS'() {

        when: 'When create resource in fls is called'
           FLSDeleteRequest request = new FLSDeleteRequest(1, DataType.STATISTICAL, "NODE_TYPE")
           FLSDeleteResponse response = flsManagementService.deletePMRopInfo(request)

        then: 'resource should be deleted in FLS'
           response.getNoOfRecordsDeleted() >= 0
    }

   def 'when valid input available create resource in FLS'() {
       given: 'when initiation request is available'
           PMRopInfo pmRopInfo = new PMRopInfo()
           List resourcePathList = new ArrayList();
           resourcePathList.add(target)
           InitiationRequest initiationRequest = new InitiationRequest(RequestType.PMIC, resourcePathList)

       when: 'When create resource in fls is called'
           flsManagementService.initiate(initiationRequest)

       then: 'resource should be created in FLS'
           2 * entityManager.persist(pmRopInfo)
   }

    /*
     * This test may fail on a windows as during copy windows changes creation and access time
     * without changing modification time where as Linux uses only modification and access time.
     */

    @Unroll
    def 'when retention time of old files are #retentionTime then #filesDeleted are deleted from file system.'() {
        given: 'old file is available in the file system'
            copyFileUsingStream()

        when: 'delete files is called'
            Future<Integer> count = flsManagementService.deleteFLSFiles(target, retentionTime)

        then: '#filesDeleted file is deleted'
            count.get() == filesDeleted

        where:
            filesDeleted | retentionTime
            1            | 0
            0            | 1
    }

    @Unroll
    def 'when #script , #argument and #timeoutInMilliSecond then #result'() {
        given: 'when validate argument is available'

        when: 'When create resource in fls is called'
            final String actual = flsManagementService.monitorPostgresFS(script, timeoutInMilliSecond, argument)

        then: 'resource should be created in FLS'
            actual == expected

        where:
            script                  | argument                                  | timeoutInMilliSecond  | expected
            "script/updatePIB.sh"   | "stopAllOperationOnFlsDB,true".split()    | 15000L                | ''
    }

    def 'when valid input available remove topology resource in FLS'() {

        when: 'When delete topology resource in fls is called'
            FLSDeleteRequest request = new FLSDeleteRequest()
            request.setFileLocation(fileLocaation)
            FLSDeleteResponse response = flsManagementService.deleteTopologyInfoByLocation(request)

        then: 'resource should be deleted in FLS'
            response.getNoOfRecordsDeleted() >= 0
    }
}
