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

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.oss.services.fls.test.util.FlsRestRequestBuilder;

/**
 *  Integration tests for testing availability (deployment) of FLS REST API
 */
@RunWith(Arquillian.class)
public class AvailabilityArquillianTest extends AbstractFlsArquillianTest {

    @Before
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void init() throws Exception {
        setupUser(FLS_NBI_OPERATOR_USER);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_root_context() {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().build();
        executeTest(getFilesRequest, SC_BAD_REQUEST);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_simple_query_with_mandatory_filter() {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().simple().build();
        executeTest(getFilesRequest, SC_OK);
    }

    private void executeTest(final HttpGet request, final int expectedStatusCode) {
        final CloseableHttpResponse response = sendRestCall(request);
        final int statusCode = response.getStatusLine().getStatusCode();
        assertEquals("Unexpected response status code", expectedStatusCode, statusCode);
    }

}