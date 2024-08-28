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
package com.ericsson.oss.services.fls.util;

import java.util.List;

/**
 * A container class that abstracts a list of created, updated and deleted files.
 *
 * @author ehimnay
 */

public class FileResourceContainer {

    private final List<String> resourceList;

    public FileResourceContainer(final List<String> resourceList) {
        this.resourceList = resourceList;
    }

    /**
     * Method that returns the list of create files.
     *
     * @return list of resource
     */
    public List<String> getResourceList() {
        return resourceList;
    }

    /**
     * Method that returns either true or false based on existence files in the given resourceLocation.
     *
     * @return true if there are no files in the given resourceLocation.
     *         false if there are files in the given resourceLocation.
     */
    public boolean isEmpty() {
        return getResourceList().isEmpty();
    }

}
