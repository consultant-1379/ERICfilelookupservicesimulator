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
package com.ericsson.oss.services.fls.rule;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.ericsson.oss.services.fls.annotation.RuleTypeAnnotationLiteral;
import com.ericsson.oss.services.fls.util.RequestType;
import org.slf4j.Logger;

/**
 * This Class RuleSelector, selects an instance of RuleSelector Class.
 *
 * @author ehimnay
 */
@ApplicationScoped
public class RuleSelector {

    @Inject
    private Logger logger;

    @Any
    @Inject
    private Instance<FLSRule> processor;

    /**
     * Gets the instance of Rule based on requestType.
     *
     * @param requestType
     *            the requestType
     * @return single instance of Rule
     */
    public FLSRule select(final RequestType requestType) {
        final RuleTypeAnnotationLiteral selector = new RuleTypeAnnotationLiteral(requestType);
        final Instance<FLSRule> selectedInstance = processor.select(selector);
        if (selectedInstance.isUnsatisfied()) {
            logger.error("No processor defined for client requestType {}.", requestType);
            throw new UnsupportedOperationException("RequestType: " + requestType + " is not currently supported");
        }
        return selectedInstance.get();
    }
}