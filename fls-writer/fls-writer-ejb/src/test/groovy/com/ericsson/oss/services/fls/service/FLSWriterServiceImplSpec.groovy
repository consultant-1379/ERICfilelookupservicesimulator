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

package com.ericsson.oss.services.fls.service

import com.ericsson.cds.cdi.support.configuration.InjectionProperties
import com.ericsson.cds.cdi.support.rule.ImplementationInstance
import com.ericsson.cds.cdi.support.rule.ObjectUnderTest
import com.ericsson.cds.cdi.support.spock.CdiSpecification
import com.ericsson.oss.itpf.sdk.resources.Resource
import com.ericsson.oss.services.fls.db.core.EntityManagerProvider
import com.ericsson.oss.services.fls.db.entity.PMRopInfo
import com.ericsson.oss.services.fls.listener.config.ConfigurationChangeListener
import com.ericsson.oss.services.fls.util.RequestType

import javax.persistence.EntityManager
import javax.persistence.Query
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

class FLSWriterServiceImplSpec extends CdiSpecification {

    @ObjectUnderTest
    private FLSWriterServiceImpl flsWriterService

    @ImplementationInstance
    private EntityManagerProvider entityManagerProvider = Mock()

    @ImplementationInstance
    private EntityManager entityManager = Mock()

    @ImplementationInstance
    private Resource mockResource = Mock()

    @ImplementationInstance
    private Query query= Mock()

    @ImplementationInstance
    private ConfigurationChangeListener configurationChangeListener = Mock(ConfigurationChangeListener);

    private final ResourceImpl testResource = new ResourceImpl();

    private String parentResourcePath = 'src/test/resources/'
    private String parentTopologyResourcePath = 'src/test/resources/topology/'

    protected Resource resource = [
            listFiles: { testResource.listFiles() },
            name: {''},
            setURI: {'src/test/resources/'},
            exists: {false}
    ] as Resource

    def copyFileUsingStream() throws IOException {
        File file = new File("src/test/resources/pmic_pmRopInfo.csv")

        if (!file.exists()) {
            Files.copy(Paths.get("src/test/resources/input/pmic_pmRopInfo.csv"), Paths.get("src/test/resources/pmic_pmRopInfo.csv"))
        }
    }

    def copyTopologyFileUsingStream() throws IOException {
        File file = new File("src/test/resources/topology/topology_PmRopInfo.csv")

        if (!file.exists()) {
            Files.copy(Paths.get("src/test/resources/input/topology_PmRopInfo.csv"), Paths.get("src/test/resources/topology/topology_PmRopInfo.csv"))
        }
    }

    def setup() {
        copyFileUsingStream()
        TimeUnit.SECONDS.sleep(5)
        entityManagerProvider.getEntityManager() >> entityManager
        testResource.setURI(parentResourcePath)
        entityManager.createQuery(_) >> query
        configurationChangeListener.flsResourceBatchSize >> 1
        configurationChangeListener.flsTopologyResourceBatchSize >> 1
        configurationChangeListener.pmicNfsShareList >> '/ericsson/pmic1/fls,/ericsson/pmic2/fls'
        configurationChangeListener.noOfResourceToScan >> 10
        configurationChangeListener.noOfTopologyResourceToScan >> 10
        configurationChangeListener.ENIQTopologyService_root_location >> '/ericsson/pmic1/topology/fls'
    }

    @Override
    def addAdditionalInjectionProperties(InjectionProperties injectionProperties) {
        injectionProperties.autoLocateFrom('com.ericsson.oss.services.fls')
    }

    def 'when resource not available do not create resource in FLS'() {
        when: 'When create resource in fls is called'
            flsWriterService.createResourceInFls(parentResourcePath, mockResource, RequestType.PMIC)

        then: 'resource should not be created in FLS'
            mockResource.listFiles() == null
    }

    def 'when valid input available create resource in FLS and 2 resource saved but file will not delete'() {
        given: 'An invalid resource, resource path, requestType given'
            PMRopInfo pmRopInfo = new PMRopInfo()

        when: 'When create resource in fls is called'
            flsWriterService.createResourceInFls(parentResourcePath, resource, RequestType.PMIC)

        then: 'resource should be created in FLS'
            resource.listFiles().size() == 3
            2 * entityManager.persist(pmRopInfo)
    }

    def 'when valid topology input available create resource in FLS and 1 resource saved but file will not delete'() {
        given: 'An invalid resource, resource path, requestType given'
            copyTopologyFileUsingStream()
            testResource.setURI(parentTopologyResourcePath)
            Resource topologyResource = [
                listFiles: { testResource.listFiles() },
                setURI: {'src/test/resources/topology/'},
                exists: {false}
            ] as Resource
            PMRopInfo pmRopInfo = new PMRopInfo()

        when: 'When create topology resource in fls is called'
            flsWriterService.createResourceInFls(parentTopologyResourcePath, topologyResource, RequestType.TOPOLOGY)

        then: 'topology resource should be created/updated in FLS'
            resource.listFiles().size() == 1
            1 * entityManager.merge(pmRopInfo)
    }
}
