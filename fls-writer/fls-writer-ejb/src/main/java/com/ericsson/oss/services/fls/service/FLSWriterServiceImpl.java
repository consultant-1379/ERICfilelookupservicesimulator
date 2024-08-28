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
package com.ericsson.oss.services.fls.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.services.fls.api.FLSWriterService;
import com.ericsson.oss.services.fls.processor.Processor;
import com.ericsson.oss.services.fls.processor.ProcessorSelector;
import com.ericsson.oss.services.fls.rule.FLSRule;
import com.ericsson.oss.services.fls.rule.RuleSelector;
import com.ericsson.oss.services.fls.util.FileResourceContainer;
import com.ericsson.oss.services.fls.util.RequestType;

/**
 * FLS writer manages the resource from nfs to postgres.
 *
 * @author ehimnay
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local(FLSWriterService.class)
public class FLSWriterServiceImpl implements FLSWriterService {

    @Inject
    private Logger logger;

    @Inject
    private ProcessorSelector processorSelector;

    @Inject
    private RuleSelector ruleSelector;

    @Override
    public void createResourceInFls(final String resourcePath, final Resource resource, final RequestType requestType) {
        final FLSRule flsRule = ruleSelector.select(requestType);
        final List<FileResourceContainer> fileResourceContainerBatch = flsRule.applyRules(resourcePath);
        if (fileResourceContainerBatch != null && !fileResourceContainerBatch.isEmpty()) {
            for (final FileResourceContainer fileResourceContainer : fileResourceContainerBatch) {
                if (!fileResourceContainer.isEmpty() && fileResourceContainer.getResourceList() != null
                        && !fileResourceContainer.getResourceList().isEmpty()) {
                    final Processor processor = processorSelector.select(requestType);
                    processor.process(resource, fileResourceContainer);
                }
            }
        } else {
            logger.debug("No resource(s) found at location {}", resourcePath);
        }

    }
}
