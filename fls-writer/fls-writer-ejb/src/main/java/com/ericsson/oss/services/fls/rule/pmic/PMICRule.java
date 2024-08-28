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
package com.ericsson.oss.services.fls.rule.pmic;

import javax.enterprise.context.ApplicationScoped;

import com.ericsson.oss.services.fls.annotation.RuleTypeAnnotation;
import com.ericsson.oss.services.fls.rule.AbstractFLSRule;
import com.ericsson.oss.services.fls.util.RequestType;

import static com.ericsson.oss.services.fls.constants.FLSConstant.CSV_FILE;

/**
 * Apply different rules during batching of pmic resource.
 *
 * @author ehimnay
 */
@ApplicationScoped
@RuleTypeAnnotation(ruleType = RequestType.PMIC)
public class PMICRule extends AbstractFLSRule {

    @Override
    protected int getNoOfResourceToScan() {
        return configurationChangeListener.getNoOfResourceToScan();
    }

    @Override
    protected String getFileType() {
        return CSV_FILE;
    }

    @Override
    protected int getFlsResourceBatchSize() {
        return configurationChangeListener.getFlsResourceBatchSize();
    }
}
