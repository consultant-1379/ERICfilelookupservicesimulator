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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.oss.services.fls.test.util.FlsRestRequestBuilder;

/**
 * Integration tests for testing querying of FLS REST API
 */
@RunWith(Arquillian.class)
public class QueryingArquillianTest extends AbstractFlsArquillianTest {

    private final static ObjectMapper jsonMapper = new ObjectMapper();

    private final static int ROWS_IN_DB_FOR_PM_STAR = 9;
    private final static int ROWS_FOR_NODE_LTE01ERBS00002 = 2;
    private final static int ROWS_FOR_OFFSET_BY_5 = 4;
    private final static int ROWS_GT_2017_10_03_03_20_00 = 1;
    private final static int ROWS_GE_2017_10_03_03_20_00 = 4;
    private final static int ROWS_NODE_TYPE_IN_SGSN_MGW = 4;
    private final static int ROWS_PARENTHESES_GROUPING = 2;
    private final static int ROWS_ZERO = 0;
    private final static int FIRST_ID_FOR_DEFAULT_SORTING = 1;
    private final static int FIRST_ID_FOR_ID_DESC_SORTING = 9;

    @Before
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void init() throws Exception {
        setupUser(FLS_NBI_OPERATOR_USER);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_get_all_request_with_mandatory_filter() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().simple().build();
        executeTest(getFilesRequest, ROWS_IN_DB_FOR_PM_STAR);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_get_all_for_node_name() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().filter("dataType==PM*;nodeName==LTE01ERBS00002").build();
        executeTest(getFilesRequest, ROWS_FOR_NODE_LTE01ERBS00002);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_get_all_greater_than_date() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().filter("dataType==PM*;fileCreationTimeInOss=gt=2017-10-03T03:20:00").build();
        executeTest(getFilesRequest, ROWS_GT_2017_10_03_03_20_00);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_get_all_greater_equal_date() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().filter("dataType==PM*;fileCreationTimeInOss=ge=2017-10-03T03:20:00").build();
        executeTest(getFilesRequest, ROWS_GE_2017_10_03_03_20_00);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_in_operator() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().filter("dataType==PM*;nodeType=in=(SGSN-MME,MGW)").build();
        executeTest(getFilesRequest, ROWS_NODE_TYPE_IN_SGSN_MGW);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_parentheses_grouping() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().filter("dataType==PM_STATISTICAL;(nodeType==MGW,nodeType==SGSN-MME)").build();
        executeTest(getFilesRequest, ROWS_PARENTHESES_GROUPING);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_pagination_offset() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().simple().limit("10000").offset("5").build();
        executeTest(getFilesRequest, ROWS_FOR_OFFSET_BY_5);
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_select() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().simple().select("id,nodeName").build();
        final CloseableHttpResponse response = sendRestCall(getFilesRequest);
        final String responseBody = getResponseBodyAsString(response);
        final List<JsonNode> idElements = jsonMapper.readValue(responseBody, JsonNode.class).findValues("id");
        assertEquals(ROWS_IN_DB_FOR_PM_STAR, idElements.size());
        final List<JsonNode> nodeNameElements = jsonMapper.readValue(responseBody, JsonNode.class).findValues("nodeName");
        assertEquals(ROWS_IN_DB_FOR_PM_STAR, nodeNameElements.size());
        final List<JsonNode> fileLocationElements = jsonMapper.readValue(responseBody, JsonNode.class).findValues("fileLocation");
        assertEquals(ROWS_ZERO, fileLocationElements.size());
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_default_sorting() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().simple().build();
        final CloseableHttpResponse response = sendRestCall(getFilesRequest);
        final String responseBody = getResponseBodyAsString(response);
        final List<JsonNode> nodes = jsonMapper.readValue(responseBody, JsonNode.class).findValues("id");
        assertEquals(FIRST_ID_FOR_DEFAULT_SORTING, nodes.get(0).asInt());
    }

    @Test
    @OperateOnDeployment(FLS_TEST_DEPLOYMENT)
    public void test_sort_by_id_desc() throws Exception {
        final HttpGet getFilesRequest = new FlsRestRequestBuilder().simple().orderBy("id desc").build();
        final CloseableHttpResponse response = sendRestCall(getFilesRequest);
        final String responseBody = getResponseBodyAsString(response);
        final List<JsonNode> nodes = jsonMapper.readValue(responseBody, JsonNode.class).findValues("id");
        assertEquals(FIRST_ID_FOR_ID_DESC_SORTING, nodes.get(0).asInt());
    }

    private void executeTest(final HttpGet getFilesRequest, int expectedResultSize) throws Exception {
        final CloseableHttpResponse response = sendRestCall(getFilesRequest);
        final String responseBody = getResponseBodyAsString(response);
        final List<JsonNode> nodes = jsonMapper.readValue(responseBody, JsonNode.class).findValues("fileLocation");
        assertEquals(expectedResultSize, nodes.size());
    }

    private String getResponseBodyAsString(final CloseableHttpResponse response) {
        try {
            final String bodyAsString = IOUtils.toString(response.getEntity().getContent());
            return bodyAsString;
        } catch (final IOException e) {
            logger.error("\n\n" + e.getLocalizedMessage(), e);
            assertTrue("Exception hit: " + e.getMessage(), false);
        }
        return null;
    }

}