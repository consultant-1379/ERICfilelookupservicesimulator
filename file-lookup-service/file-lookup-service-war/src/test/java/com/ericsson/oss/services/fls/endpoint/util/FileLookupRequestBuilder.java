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

package com.ericsson.oss.services.fls.endpoint.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.api.exception.FlsDataTypeException;

/**
 * Builder for {@link com.ericsson.oss.services.fls.api.FileLookupRequest} needed for test purpose
 */
public class FileLookupRequestBuilder {

    private static final String FILTER_CLAUSE_DELIMITER = ";";

    private String select;
    private final List<String> filter = new ArrayList<>();
    private String limit;
    private String offset;
    private String orderBy;

    public FileLookupRequestBuilder select(final String select) {
        this.select = select;
        return this;
    }

    public FileLookupRequestBuilder filter(final String filter) {
        this.filter.add(filter);
        return this;
    }

    public FileLookupRequestBuilder limit(final String limit) {
        this.limit = limit;
        return this;
    }

    public FileLookupRequestBuilder offset(final String offset) {
        this.offset = offset;
        return this;
    }

    public FileLookupRequestBuilder orderBy(final String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public FileLookupRequest build() throws FlsDataTypeException {
        return new FileLookupRequest(select, StringUtils.join(filter, FILTER_CLAUSE_DELIMITER), limit, offset, orderBy);
    }

}
