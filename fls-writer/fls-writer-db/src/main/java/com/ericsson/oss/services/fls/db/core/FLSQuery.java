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
package com.ericsson.oss.services.fls.db.core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Contains the properties to build dynamic jpql/native query.
 *
 * @author ehimnay
 */
public class FLSQuery implements Serializable {

    private static final long serialVersionUID = 1292698055339372912L;

    private String expression;
    private List<Object> values;
    private int limit;
    private int offset;

    public FLSQuery(final String expression, final List<Object> values) {
        this.expression = expression;
        this.values = values;
    }

    public FLSQuery(final String expression, final Object value) {
        this(expression, value == null ? Collections.emptyList() : Arrays.asList(value));
    }

    public FLSQuery(final String expression, final List<Object> values, final int limit, final int offset) {
        this(expression,values);
        this.limit = limit;
        this.offset = offset;
    }
    
    /**
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(final String expression) {
        this.expression = expression;
    }

    /**
     * @return the values
     */
    public List<Object> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(final List<Object> values) {
        this.values = values;
    }
    
    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(final int limit) {
        this.limit = limit;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public String toString() {
        return new StringBuffer()
                .append("FLSQuery [expression=").append(expression)
                .append(", values=").append(values)
                .append(", limit=").append(limit)
                .append(", offset=").append(offset)
                .toString();
    }

}