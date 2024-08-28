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
package com.ericsson.oss.services.fls.api;

import java.util.List;

import javax.ejb.Local;

import com.ericsson.oss.itpf.sdk.resources.Resource;

/**
 * This class handles all the fls directory related methods
 *
 * @author ehimnay
 */
@Local
public interface FLSDirectoryResourceService {

    /**
     * initialize the file resource jca with a default path.
     *
     * @return an instance of the default resource.
     */
    Resource initialize();

    /**
     * remove the resources from the sfs in a transaction
     *
     * @param resource
     *            default resource.
     * @param resourceList
     *            list of resource under the parent resource.
     */
    void removeResource(final Resource resource, final List<String> resourceList);

}
