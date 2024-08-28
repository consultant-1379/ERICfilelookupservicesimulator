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
package com.ericsson.oss.services.fls.test.scenario;

import com.ericsson.oss.itpf.sdk.security.accesscontrol.ESecuritySubject;
import com.ericsson.oss.services.fls.test.config.Deployments;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public abstract class AbstractFlsArquillianTest extends Deployments {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final String FLS_NBI_OPERATOR_USER = "fls_operator_user";
    protected static final String NO_ROLE_USER = "user_with_no_role";

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(final Description description) {
            logger.info("*******************************");
            logger.info("Starting test: {}()", description.getMethodName());
            logger.info("*******************************");
        }

        @Override
        protected void finished(final Description description) {
            logger.info("*******************************");
            logger.info("Ending test: {}()", description.getMethodName());
            logger.info("*******************************");
        }
    };

    protected ESecuritySubject setupUser(final String user) {
        String tmpDir;
        final String osName = System.getProperty("os.name");
        if ("Linux".equals(osName)) {
            tmpDir = "/tmp";
        } else {
            tmpDir = System.getProperty("java.io.tmpdir");
        }

        FileWriter fw;
        try {
            fw = new FileWriter(new File(tmpDir, "currentAuthUser"));
            fw.write(user);
            fw.close();
        } catch (final IOException e) {
            logger.error("\n\n" + e.getLocalizedMessage(), e);
            assertTrue("Exception hit: " + e.getMessage(), false);
        }

        return new ESecuritySubject(user);
    }

    protected CloseableHttpResponse sendRestCall(final HttpRequestBase request) {
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(request);
        } catch (final IOException e) {
            logger.error("\n\n" + e.getLocalizedMessage(), e);
            assertTrue("Exception hit: " + e.getMessage(), false);
        }
        return response;
    }

}
