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

import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_FILTER_CLAUSE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_REQUEST_FORMAT;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.db.jpql.JPQLExpression;
import com.ericsson.oss.services.fls.db.jpql.JPQLExpressionBuilder;
import com.ericsson.oss.services.fls.lookup.exception.IllegalFlsRequestArgument;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;

/**
 * Class with utility methods for {@link JPQLExpression} creation from RSQL AST nodes.
 */
public class FlsExpressionBuilder {

    private static final Logger logger = LoggerFactory.getLogger(FlsExpressionBuilder.class);

    private FlsExpressionBuilder() {}

    /**
     * Create JPQL expression for generic node
     *
     * @param node
     *            the node
     * @param entity
     *            the entity
     * @param <T>
     *            the entity type
     * @return the JPQL expression
     */
    private static <T> JPQLExpression createExpression(final Node node, final Class<T> entity) {
        if (node instanceof LogicalNode) {
            return createExpression((LogicalNode) node, entity);
        }
        if (node instanceof ComparisonNode) {
            return createExpression((ComparisonNode) node, entity);
        }
        logger.debug("Undefined type of node {}", node);
        throw new IllegalFlsRequestArgument(INCORRECT_REQUEST_FORMAT);
    }

    /**
     * Create JPQL expression for logical node
     *
     * @param logicalNode
     *            the logical node
     * @param entity
     *            the entity
     * @param <T>
     *            the entity type
     * @return the JPQL logical expression
     */
    public static <T> JPQLExpression createExpression(final LogicalNode logicalNode, final Class<T> entity) {
        final List<JPQLExpression> expressions = new ArrayList<>();
        for (final Node node : logicalNode.getChildren()) {
            expressions.add(createExpression(node, entity));
        }
        switch (logicalNode.getOperator()) {
            case AND:
                return JPQLExpressionBuilder.and(expressions.toArray(new JPQLExpression[expressions.size()]));
            case OR:
                return JPQLExpressionBuilder.or(expressions.toArray(new JPQLExpression[expressions.size()]));
        }
        logger.debug("Unknown logical operator provided in FLS Request");
        throw new IllegalFlsRequestArgument(INCORRECT_REQUEST_FORMAT);
    }

    /**
     * Create JPQL expression for comparison node
     *
     * @param comparisonNode
     *            the comparison node
     * @param entity
     *            the entity
     * @param <T>
     *            the entity type
     * @return the JPQL comparison expression
     */
    public static <T> JPQLExpression createExpression(final ComparisonNode comparisonNode, final Class<T> entity) {
        final String fieldName = comparisonNode.getSelector();
        final ComparisonOperator operator = comparisonNode.getOperator();
        final List<Object> castedArguments = FlsRequestArgumentParser.parse(comparisonNode.getArguments(), getFieldType(entity, fieldName));
        return createExpression(fieldName, operator, castedArguments);
    }

    /**
     * Create JPQL expression for comparison node
     *
     * @param fieldName
     *            the field name
     * @param operator
     *            the operator
     * @param argumentList
     *            the argument list
     * @return the JPQL expression
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity" })
    private static JPQLExpression createExpression(final String fieldName, final ComparisonOperator operator, final List<Object> argumentList) {
        logger.debug("Creating predicate: fieldName: {} operator: {} arguments: {}", fieldName, operator, argumentList);
        final Object argument = argumentList.get(0);
        switch (FlsOperators.asEnum(operator)) {
            case EQUAL: {
                if (argument instanceof String) {
                    return JPQLExpressionBuilder.like(fieldName, argument);
                } else if (argument == null) {
                    return JPQLExpressionBuilder.isNull(fieldName);
                } else {
                    return JPQLExpressionBuilder.equal(fieldName, argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return JPQLExpressionBuilder.notLike(fieldName, argument);
                } else if (argument == null) {
                    return JPQLExpressionBuilder.isNotNull(fieldName);
                } else {
                    return JPQLExpressionBuilder.notEqual(fieldName, argument);
                }
            }
            case GREATER_THAN: {
                return JPQLExpressionBuilder.greaterThan(fieldName, argument);

            }
            case GREATER_THAN_OR_EQUAL: {
                return JPQLExpressionBuilder.greaterEqual(fieldName, argument);

            }
            case LESS_THAN: {
                return JPQLExpressionBuilder.lessThan(fieldName, argument);

            }
            case LESS_THAN_OR_EQUAL: {
                return JPQLExpressionBuilder.lessEqual(fieldName, argument);

            }
            case IN: {
                return JPQLExpressionBuilder.in(fieldName, argumentList);
            }
            case NOT_IN: {
                return JPQLExpressionBuilder.notIn(fieldName, argumentList);
            }

        }
        logger.debug("Unknown comparison operator provided in FLS Request");
        throw new IllegalFlsRequestArgument(INCORRECT_REQUEST_FORMAT);
    }

    /**
     * Get the type of entity field
     *
     * @param entity
     *            the entity
     * @param fieldName
     *            the field name
     * @param <T>
     *            the entity type
     * @return the type of entity field
     */
    private static <T> Class getFieldType(final Class<T> entity, final String fieldName) {
        try {
            return entity.getDeclaredField(fieldName).getType();
        } catch (final NoSuchFieldException exception) {
            logger.debug("Field name provided in filter parameter is incorrect. Class:{} Field:{}", entity, fieldName);
            throw new IllegalFlsRequestArgument(INCORRECT_FILTER_CLAUSE);
        }
    }

}
