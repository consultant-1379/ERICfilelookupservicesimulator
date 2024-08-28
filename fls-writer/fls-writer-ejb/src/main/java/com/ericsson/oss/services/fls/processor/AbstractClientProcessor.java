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
package com.ericsson.oss.services.fls.processor;

import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.services.fls.api.FLSDirectoryResourceService;
import com.ericsson.oss.services.fls.db.entity.PMRopInfo;
import com.ericsson.oss.services.fls.util.FLSHelper;
import com.ericsson.oss.services.fls.util.FileResourceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract Client Processor
 *
 * @author ehimnay.
 */
public abstract class AbstractClientProcessor implements Processor{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private FLSDirectoryResourceService flsDirectoryResourceService;

    @Inject
    private FLSHelper flsHelper;

    @Override
    public void process(final Resource resource, final FileResourceContainer fileResourceContainer) {
        logger.debug("PMICProcessor is processing the request.");
        final List<String> resourceList = fileResourceContainer.getResourceList();
        createPMRopInfo(resourceList);
        flsDirectoryResourceService.removeResource(resource, resourceList);
    }

    /**
     * create pmRopInfo entities for pmic.
     *
     * @param resources
     *            the list of resources to be read from the parent resource.
     * @return a list of pmRopInfo entities.
     */
    protected List<PMRopInfo> createPMRopInfoList(final List<String> resources, final String model)
            throws IllegalStateException, SecurityException {
        final List<PMRopInfo> pmRopInfoList = new ArrayList<>();
        final Iterator<String> resourceItr = resources.iterator();
        while (resourceItr.hasNext()) {
            final String resource = resourceItr.next();
            try {
                pmRopInfoList.addAll(flsHelper
                        .convert(flsHelper.unMarshall(resource, model)));
            } catch (final Exception exception) {
                logger.error("Exception during parsing {} resource. Skipping the resource.", resource,
                        exception.getMessage());
                resourceItr.remove();
            }
        }
        return pmRopInfoList;
    }


    /**
     * create pmRopInfo for pmic and topology.
     *
     * @param resources
     *            the list of resources to be read from the parent resource.
     */
    protected abstract void  createPMRopInfo(final List<String> resources);
}
