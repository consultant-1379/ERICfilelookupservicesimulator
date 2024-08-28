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
package com.ericsson.oss.services.fls.endpoint.app;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.FLS_APPLICATION_VERSION;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.ericsson.oss.services.fls.endpoint.exceptionmapper.FileLookupDefinedExceptionMapper;
import com.ericsson.oss.services.fls.endpoint.exceptionmapper.FileLookupGlobalExceptionMapper;
import com.ericsson.oss.services.fls.endpoint.exceptionmapper.SecurityViolationExceptionMapper;
import com.ericsson.oss.services.fls.endpoint.rest.FileLookupEntryPoint;
import com.ericsson.oss.services.fls.endpoint.rest.LoginPage;
/**
 * Definition of end point path of File Lookup Service
 */
//@ApplicationPath(FLS_APPLICATION_VERSION)
@ApplicationPath("/")
public class FLSApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(FileLookupEntryPoint.class);
        classes.add(LoginPage.class);
        classes.add(FileLookupDefinedExceptionMapper.class);
        classes.add(FileLookupGlobalExceptionMapper.class);
        classes.add(SecurityViolationExceptionMapper.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return new HashSet<>();
    }

}
