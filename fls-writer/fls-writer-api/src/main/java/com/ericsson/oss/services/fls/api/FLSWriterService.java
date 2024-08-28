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

import javax.ejb.Local;

import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.services.fls.util.RequestType;

/**
 * This class handles all the FLS related methods
 *
 * @author ehimnay
 */
@Local
public interface FLSWriterService {
    /**
     * Loads the resource content into postgres. It does the following in loading the files:
     * <p>
     * 1. Checks if there are files to be loaded.
     * <p>
     * 2. Reads the files from disk, apply rules curing creating batch and upload the content to postgres.
     * <p>
     * 3. Remove the files from the disk.
     *
     * @param resourcePath
     *            absolute path of the parent resource.
     * @param resource
     *            parent resource.
     * @param requestType
     *            list of resource under the parent resource.
     */
    void createResourceInFls(final String resourcePath, final Resource resource, final RequestType requestType);
}
