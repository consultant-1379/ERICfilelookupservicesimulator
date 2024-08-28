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
package com.ericsson.oss.services.fls.db.jpql;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Representation of Java Persistence Query Language expression
 */
public class JPQLExpression {

    private final String expression;
    private final List<Object> parameters;

    public JPQLExpression(final String expression, final List<Object> params) {
        this.expression = expression;
        this.parameters = Collections.unmodifiableList(params);
    }

    public JPQLExpression(final String whereClause, final Object... params) {
        this(whereClause, params == null ? Collections.emptyList() : Arrays.asList(params));
    }

    /**
     * @return the expression
     */
    public String getWhereClause() {
        return expression;
    }

    /**
     * @return the parameters
     */
    public List<Object> getParameters() {
        return parameters;
    }

}