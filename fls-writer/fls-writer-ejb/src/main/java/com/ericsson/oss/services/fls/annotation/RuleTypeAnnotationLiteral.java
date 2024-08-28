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
package com.ericsson.oss.services.fls.annotation;

import javax.enterprise.util.AnnotationLiteral;

import com.ericsson.oss.services.fls.util.RequestType;

/**
 * The Class RuleTypeAnnotationLiteral.
 *
 * @author ehimnay
 */
public class RuleTypeAnnotationLiteral extends AnnotationLiteral<RuleTypeAnnotation> implements RuleTypeAnnotation {

    private static final long serialVersionUID = 3104827482839286984L;
    private final RequestType ruleType;

    /**
     * Instantiates a new rule annotation literal.
     *
     * @param ruleType
     *            the client requestType
     */
    public RuleTypeAnnotationLiteral(final RequestType ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestType ruleType() {
        return ruleType;
    }
}