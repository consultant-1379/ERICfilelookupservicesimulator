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

package com.ericsson.oss.services.fls.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.api.exception.FlsDataTypeException;

/**
 * Bean representing a file lookup request
 */
public class FileLookupRequest {
    private static final Logger logger = LoggerFactory.getLogger(FileLookupRequest.class);

    private final String select;
    private final String filter;
    private final String limit;
    private final String offset;
    private final String orderBy;
    private FlsDataType flsDataType;

    public FileLookupRequest(final String select, final String filter, final String limit, final String offset, final String orderBy) {
        this.select = select;
        this.filter = filter;
        this.limit = limit;
        this.offset = offset;
        this.orderBy = orderBy;
    }

    /**
     * Accessor for select parameter
     *
     * @return select parameter
     */
    public String getSelect() {
        return select;
    }

    /**
     * Accessor for filter parameter
     *
     * @return filter parameter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Accessor for limit parameter
     *
     * @return limit parameter
     */
    public String getLimit() {
        return limit;
    }

    /**
     * Accessor for offset parameter
     *
     * @return offset parameter
     */
    public String getOffset() {
        return offset;
    }

    /**
     * Accessor for orderBy parameter
     *
     * @return orderBy parameter
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Accessor for flsDataType parameter
     *
     * @return flsDataType parameter
     */
    public FlsDataType getFlsDataType() {
        return flsDataType;
    }

    public void setFlsDataType(FlsDataType flsDataType) {
        this.flsDataType = flsDataType;
    }

    @Override
    public String toString() {
        return "FileLookupRequest{"
                + "select='" + select + '\''
                + ", filter='" + filter + '\''
                + ", limit='" + limit + '\''
                + ", offset='" + offset + '\''
                + ", orderBy='" + orderBy + '\''
                + ", flsDataType='" + flsDataType + '\'' + '}';
    }
}
