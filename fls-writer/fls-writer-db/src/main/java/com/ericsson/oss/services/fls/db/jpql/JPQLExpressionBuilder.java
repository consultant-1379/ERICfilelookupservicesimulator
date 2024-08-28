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

import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.ENTITY_ALIAS;
import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.PARAMETER_DEFINITION_ALIAS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Builder of {@link JPQLExpression}
 */
public class JPQLExpressionBuilder {

    private final static String OR = " OR ";
    private final static String AND = " AND ";
    private final static String ORDER_BY = " ORDER BY ";
    private final static String COMMA = " , ";

    private final static String LIKE_WILDCARD_STAR = "*";
    private final static String LIKE_WILDCARD_PERCENT = "%";

    private final static String EQUAL = "=";
    private final static String NOT_EQUAL = "!=";
    private final static String LIKE = "LIKE";
    private final static String NOT_LIKE = "NOT LIKE";
    private final static String GREATER_THAN = ">";
    private final static String GREATER_EQUAL = ">=";
    private final static String LESS_THAN = "<";
    private final static String LESS_EQUAL = "<=";
    private final static String IN = "IN";
    private final static String NOT_IN = "NOT IN";

    private final static String COMPARE_TEMPLATE = ENTITY_ALIAS + ".%s %s " + PARAMETER_DEFINITION_ALIAS;
    private final static String MULTIPLE_COMPARE_TEMPLATE = ENTITY_ALIAS + ".%s %s (" + PARAMETER_DEFINITION_ALIAS +")";
    private final static String IS_NULL_TEMPLATE = ENTITY_ALIAS + ".%s IS NULL";
    private final static String IS_NOT_NULL_TEMPLATE = ENTITY_ALIAS + ".%s IN NOT NULL";
    private final static String ORDER_BY_TEMPLATE = ENTITY_ALIAS + ".%s";
    private final static String PARENTHESES_SURROUNDED_TEMPLATE = "(%s)";


    /**
     * Builder of 'OR' logical expression
     * @param expressions the JPQL expressions array
     * @return the JPQL expression
     */
    public static JPQLExpression or(final JPQLExpression... expressions) {
        return mergeExpressions(OR, true, expressions);
    }

    /**
     * Builder of 'AND' logical expression
     * @param expressions the JPQL expressions array
     * @return the JPQL expression
     */
    public static JPQLExpression and(final JPQLExpression... expressions) {
        return mergeExpressions(AND, true, expressions);
    }

    /**
     * Builder of 'AND' logical expression
     * @param expressions the JPQL expressions array
     * @return the JPQL expression
     */
    public static JPQLExpression orderBy(final JPQLExpression... expressions) {
        return mergeExpressions(ORDER_BY, false, expressions);
    }

    /**
     * Builder of equal expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression equal(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, EQUAL, value);
    }

    /**
     * Builder of not equal expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression notEqual(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, NOT_EQUAL, value);
    }

    /**
     * Builder of like expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression like(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, LIKE, ((String) value).replace(LIKE_WILDCARD_STAR, LIKE_WILDCARD_PERCENT));
    }

    /**
     * Builder of 'not like' expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression notLike(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, NOT_LIKE, ((String) value).replace(LIKE_WILDCARD_STAR, LIKE_WILDCARD_PERCENT));
    }

    /**
     * Builder of 'is null' expression
     * @param fieldName the name of field
     * @return the JPQL expression
     */
    public static JPQLExpression isNull(final String fieldName) {
        return new JPQLExpression(String.format(IS_NULL_TEMPLATE, fieldName));
    }

    /**
     * Builder of 'is not null' expression
     * @param fieldName the name of field
     * @return the JPQL expression
     */
    public static JPQLExpression isNotNull(final String fieldName) {
        return new JPQLExpression(String.format(IS_NOT_NULL_TEMPLATE, fieldName));
    }

    /**
     * Builder of 'greater than' expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression greaterThan(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, GREATER_THAN, value);
    }

    /**
     * Builder of 'greater and equal' expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression greaterEqual(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, GREATER_EQUAL, value);
    }

    /**
     * Builder of 'less than' expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression lessThan(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, LESS_THAN, value);
    }

    /**
     * Builder of 'less and equal' expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression lessEqual(final String fieldName, final Object value) {
        return comparisonExpression(fieldName, LESS_EQUAL, value);
    }

    /**
     * Builder of 'in' expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression in(final String fieldName, final List<Object> value) {
        return multipleComparisonExpression(fieldName, IN, value);
    }

    /**
     * Builder of 'not in' expression
     * @param fieldName the name of field
     * @param value the argument value
     * @return the JPQL expression
     */
    public static JPQLExpression notIn(final String fieldName, final List<Object> value) {
        return multipleComparisonExpression(fieldName, NOT_IN, value);
    }

    /**
     * Builder of 'sort' expression
     * @param orderBy the sorting data
     * @return the JPQL expression
     */
    public static JPQLExpression sortExpression(final String... orderBy) {
        final List<JPQLExpression> orderByExpressions = new ArrayList<>();
        for(final String singleOrderBY : orderBy){
            orderByExpressions.add(new JPQLExpression(String.format(ORDER_BY_TEMPLATE, singleOrderBY)));
        }
        return mergeExpressions(COMMA, false, orderByExpressions.toArray(new JPQLExpression[orderByExpressions.size()]));
    }

    /**
     * Builder of comparison expression
     * @param fieldName the name of field
     * @param comparisonOperator the comparison operator
     * @param value the argument value
     * @return the JPQL expression
     */
    private static JPQLExpression comparisonExpression(final String fieldName, final String comparisonOperator, final Object value) {
        return new JPQLExpression(String.format(COMPARE_TEMPLATE, fieldName, comparisonOperator), value);
    }

    /**
     * Builder of multiple comparison expression. This provide support for IN and NOT IN operators
     * @param fieldName the name of field
     * @param comparisonOperator the comparison operator
     * @param value the argument value
     * @return the JPQL expression
     */
    private static JPQLExpression multipleComparisonExpression(final String fieldName, final String comparisonOperator, final Object value) {
        return new JPQLExpression(String.format(MULTIPLE_COMPARE_TEMPLATE, fieldName, comparisonOperator), value);
    }

    /**
     * Builder of 'AND' logical expression
     * @param logicalOperator the logical operator
     * @param useParenthesis flag to indicate parenthesis required
     * @param expressions the JPQL expressions array
     * @return the JPQL expression
     */
    private static JPQLExpression mergeExpressions(final String logicalOperator, final boolean useParenthesis, final JPQLExpression... expressions) {
        final List<String> expressionList = new ArrayList<>();
        final List<Object> parameters = new ArrayList<>();
        for (final JPQLExpression expression : expressions) {
            expressionList.add(expression.getWhereClause());
            for(final Object object : expression.getParameters()){
                parameters.add(object);
            }
        }
        final String mergedExpression = joinStrings(expressionList, logicalOperator, useParenthesis);
        return new JPQLExpression(mergedExpression, parameters);
    }

    /**
     * Join elements of collection using provided separator and surround by parenthesis
     * @param collection the elements to be join
     * @param separator the separator
     * @param useParenthesis flag to indicate parenthesis required
     * @return joined expression
     */
    private static String joinStrings(final Collection collection, final String separator, final boolean useParenthesis) {
        if (useParenthesis) {
            return String.format(PARENTHESES_SURROUNDED_TEMPLATE, StringUtils.join(collection, separator));
        } else {
            return StringUtils.join(collection, separator);
        }
    }

}