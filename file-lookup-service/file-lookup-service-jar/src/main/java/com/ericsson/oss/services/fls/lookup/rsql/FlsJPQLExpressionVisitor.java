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
package com.ericsson.oss.services.fls.lookup.rsql;

import com.ericsson.oss.services.fls.db.jpql.JPQLExpression;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

/**
 * Visitor class for {@link com.ericsson.oss.services.fls.db.jpql.JPQLExpression} creation from RSQL AST Nodes.
 */
public class FlsJPQLExpressionVisitor<T> implements RSQLVisitor<JPQLExpression, Void> {

    private final Class<T> entityClass;

    public FlsJPQLExpressionVisitor(final T... entityClass) {
        if (entityClass.length == 0) {
            this.entityClass = (Class<T>) entityClass.getClass().getComponentType();
        } else {
            this.entityClass = (Class<T>) entityClass[0].getClass();
        }
    }

    @Override
    public JPQLExpression visit(final AndNode andNode, final Void aVoid) {
        return FlsExpressionBuilder.createExpression(andNode, this.entityClass);
    }

    @Override
    public JPQLExpression visit(final OrNode orNode, final Void aVoid) {
        return FlsExpressionBuilder.createExpression(orNode, this.entityClass);
    }

    @Override
    public JPQLExpression visit(final ComparisonNode comparisonNode, final Void aVoid) {
        return FlsExpressionBuilder.createExpression(comparisonNode, this.entityClass);
    }
}
