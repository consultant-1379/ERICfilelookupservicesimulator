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

package com.ericsson.oss.services.fls.endpoint.api;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.ericsson.oss.services.fls.lookup.model.AbstractFileMetadata;

/**
 * Wrapper for list of results returned as response of file lookup request
 */
public class ResponseWrapper {

    @JsonProperty("files")
    private final List<? extends AbstractFileMetadata> list;

    public ResponseWrapper(final List<? extends AbstractFileMetadata> list) {
        this.list = list;
    }

    /**
     * get for result list
     *
     * @return result list
     */
    public List<? extends AbstractFileMetadata> getList() {
        return list;
    }
}
