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

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.oss.services.fls.test.util.FlsRestRequestBuilder;

/**
 * Integration tests for testing authorization of FLS REST API
 */
@RunWith(Arquillian.class)
public class AuthorizationArquillianTest extends AbstractFlsArquillianTest {

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_unauthorized_user() {
        setupUser(NO_ROLE_USER);
        executeAuthorizationTest(SC_FORBIDDEN);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_authorized_user() {
        setupUser(FLS_NBI_OPERATOR_USER);
        executeAuthorizationTest(SC_OK);
    }

    private void executeAuthorizationTest(final int expectedStatusCode){
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().simple().build();
        final CloseableHttpResponse response = sendRestCall(getFilesRequest);
        final int statusCode = response.getStatusLine().getStatusCode();
        assertEquals("Unexpected response status code", expectedStatusCode, statusCode);
    }
}