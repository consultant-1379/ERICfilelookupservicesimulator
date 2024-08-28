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

import java.util.Arrays;
import java.util.List;

import javax.ejb.*;
import javax.inject.Inject;

import com.ericsson.oss.services.fls.listener.config.ConfigurationChangeListener;
import org.slf4j.Logger;

import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.itpf.sdk.resources.Resources;
import com.ericsson.oss.services.fls.api.FLSDirectoryResourceService;

/**
 * FLS Directory resource service form managing the reading and deletion from resources inside transaction.
 *
 * @author ehimnay
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local(FLSDirectoryResourceService.class)
public class FLSDirectoryResourceServiceImpl implements FLSDirectoryResourceService {

    @Inject
    private Logger logger;

    @Inject
    private ConfigurationChangeListener configurationChangeListener;

    @Override
    public Resource initialize() {
        final String defaultResourcePath = Arrays.asList(configurationChangeListener.getPmicNfsShareList().split("\\,")).get(0);
        logger.debug("FLS resource initiated with default resource path {}", defaultResourcePath);
        return Resources.getFileSystemResource(defaultResourcePath);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeResource(final Resource defaultResource,
            final List<String> resourceList) {
        for (final String resource : resourceList) {
            defaultResource.setURI(resource);
            if (defaultResource.exists()) {
                final String status = defaultResource.delete() ? "Successful" : "Failed";
                logger.debug("Resource {} deleted {}.", resource, status);
            } else {
                logger.debug("Resource {} already deleted.", resource);
            }
        }
    }

}
