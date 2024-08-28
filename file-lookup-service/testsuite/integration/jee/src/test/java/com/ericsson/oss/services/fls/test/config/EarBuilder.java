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
package com.ericsson.oss.services.fls.test.config;

import java.io.File;

import com.ericsson.oss.services.fls.test.scenario.AvailabilityArquillianTest;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.test.scenario.AbstractFlsArquillianTest;
import com.ericsson.oss.services.fls.test.scenario.AuthorizationArquillianTest;
import com.ericsson.oss.services.fls.test.util.FlsRestRequestBuilder;

/**
 * Configuration for EAR Deployment for the Arquillian environment.
 */
public class EarBuilder {

    private static final Logger logger = LoggerFactory.getLogger(EarBuilder.class);

    public static final File MANIFEST_FM_FILE = new File("src/test/resources/META-INF/MANIFEST.MF");
    public static final File BEANS_XML_FILE = new File("src/test/resources/META-INF/beans.xml");
    private static final String GROUP_ID_SFWK = "com.ericsson.oss.itpf.sdk";
    private static final String GROUP_HTTP_CLIENT = "org.apache.httpcomponents";

    public static Archive<?> createTestEAR() {
        logger.info("Creating test EAR : FileLookupServiceTestEar.ear");
        final EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "FileLookupServiceTestEar.ear");
        addEarRequiredLibraries(ear);
        ear.addAsModule(createTestLibArchive());
        ear.setManifest(MANIFEST_FM_FILE);
        ear.addAsApplicationResource(BEANS_XML_FILE);
        return ear;
    }

    private static void addEarRequiredLibraries(final EnterpriseArchive archive) {
        archive.addAsLibraries(resolveAsFiles(GROUP_ID_SFWK, "service-framework-dist"));
        archive.addAsLibraries(resolveAsFiles(GROUP_HTTP_CLIENT, "httpcore"));
        archive.addAsLibraries(resolveAsFiles(GROUP_HTTP_CLIENT, "httpclient"));
    }

    private static File[] resolveAsFiles(final String groupId, final String artifactId) {
        return Maven.resolver().loadPomFromFile("pom.xml").resolve(groupId + ":" + artifactId).withTransitivity().asFile();
    }

    private static Archive<?> createTestLibArchive(@SuppressWarnings("rawtypes") final Class... testClasses) {
        final WebArchive archive = ShrinkWrap.create(WebArchive.class, "fls-test-case.war")
                .addAsResource(MANIFEST_FM_FILE)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addClass(EarBuilder.class)
                .addClass(Deployments.class)
                .addClass(AbstractFlsArquillianTest.class)
                .addClass(AuthorizationArquillianTest.class)
                .addClass(AvailabilityArquillianTest.class)
                .addClass(FlsRestRequestBuilder.class);
        return archive;
    }

}
