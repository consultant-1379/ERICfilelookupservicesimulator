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

package com.ericsson.oss.services.fls.service

import com.ericsson.oss.itpf.sdk.resources.AbstractFileResource
import com.ericsson.oss.itpf.sdk.resources.ResourcesException

public class ResourceImpl extends AbstractFileResource {
    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public boolean delete() {
        return true;
    }

    @Override
    public boolean createFile() {
        return true;
    }

    @Override
    public int write(byte[] content, boolean append) {
        return 0;
    }

    @Override
    public void createDirectory() {

    }

    @Override
    public void deleteDirectory() {

    }

    @Override
    public void setPermission(String permission) {

    }

    @Override
    public String getPermission() throws ResourcesException {
        return null;
    }

    @Override
    public String getGroup() throws ResourcesException {
        return null;
    }

    @Override
    public boolean isDirectoryExists() {
        return false;
    }
}
