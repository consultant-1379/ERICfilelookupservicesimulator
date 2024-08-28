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
package com.ericsson.oss.services.fls.test.util;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.FILTER;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.FLS_APPLICATION_PATH;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.LIMIT;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.OFFSET;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.ORDER_BY;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.SELECT;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

/**
 * Builder for FLS REST API http request, for test purpose
 */
public class FlsRestRequestBuilder {

    private static final String WEB_SERVICE_HOST_ADDRESS = "http://localhost:8080";

    private static final String MANDATORY_FILTER = "dataType==PM*";

    private final Map<String, String> paramMap = new HashMap<>();
    private final StringBuilder requestBuilder = new StringBuilder();

    public FlsRestRequestBuilder() {
        this.requestBuilder.append(WEB_SERVICE_HOST_ADDRESS);
    }

    public HttpGet build() {
        final HttpGet getFilesRequest = this.appContext().get();
        final URIBuilder uriBuilder = new URIBuilder(getFilesRequest.getURI());

        for (String paramName : paramMap.keySet()) {
            uriBuilder.addParameter(paramName, paramMap.get(paramName));
        }

        try {
            getFilesRequest.setURI(uriBuilder.build());
        } catch (final URISyntaxException exception) {
            throw new RuntimeException("Unable to build HTTP GET request", exception);
        }

        return getFilesRequest;
    }

    public FlsRestRequestBuilder simple() {
        this.paramMap.put(FILTER, MANDATORY_FILTER);
        return this;
    }

    public FlsRestRequestBuilder select(final String select) {
        this.paramMap.put(SELECT, select);
        return this;
    }

    public FlsRestRequestBuilder filter(final String filter) {
        this.paramMap.put(FILTER, filter);
        return this;
    }

    public FlsRestRequestBuilder limit(final String limit) {
        this.paramMap.put(LIMIT, limit);
        return this;
    }

    public FlsRestRequestBuilder offset(final String offset) {
        this.paramMap.put(OFFSET, offset);
        return this;
    }

    public FlsRestRequestBuilder orderBy(final String orderBy) {
        this.paramMap.put(ORDER_BY, orderBy);
        return this;
    }

    private HttpGet get() {
        return new HttpGet(this.requestBuilder.toString());
    }

    private FlsRestRequestBuilder appContext() {
        this.requestBuilder.append(FLS_APPLICATION_PATH);
        return this;
    }

}