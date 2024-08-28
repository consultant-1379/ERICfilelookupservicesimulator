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
import com.ericsson.oss.services.fls.util.FileResourceContainer;

/**
 * Interface to client processor class.
 *
 * @author ehimnay
 */
public interface Processor {
    /**
     * persist pmRopInfo entities to db.
     *
     * @param resource
     *            the list of resources to be read from the parent resource.
     * @param fileResourceContainer
     *            the container for the resources.
     */
    void process(final Resource resource, final FileResourceContainer fileResourceContainer);
}
