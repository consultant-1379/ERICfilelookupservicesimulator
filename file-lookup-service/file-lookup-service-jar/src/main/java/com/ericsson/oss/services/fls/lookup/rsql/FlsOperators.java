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

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum converts RSQLOperators to an enumeration type.
 */
public enum FlsOperators {
    EQUAL(RSQLOperators.EQUAL),
    NOT_EQUAL(RSQLOperators.NOT_EQUAL),
    GREATER_THAN(RSQLOperators.GREATER_THAN),
    GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),
    LESS_THAN(RSQLOperators.LESS_THAN),
    LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),
    IN(RSQLOperators.IN),
    NOT_IN(RSQLOperators.NOT_IN);

    private final ComparisonOperator operator;

    private final static Map<ComparisonOperator, FlsOperators> CACHE =
            Collections.synchronizedMap(new HashMap<ComparisonOperator, FlsOperators>());

    static {
        for (final FlsOperators proxy : values()) {
            CACHE.put(proxy.getOperator(), proxy);
        }
    }

    FlsOperators(final ComparisonOperator operator) {
        this.operator = operator;
    }

    private ComparisonOperator getOperator() {
        return this.operator;
    }

    public static FlsOperators asEnum(final ComparisonOperator operator) {
        return CACHE.get(operator);
    }
}
