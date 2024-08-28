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
package com.ericsson.oss.services.fls.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import com.ericsson.oss.services.fls.util.RequestType;

/**
 * The Annotation ClientTypeAnnotation. Used for Annotating Concrete Processor base on client RequestType
 *
 * @author ehimnay
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE })
public @interface ClientTypeAnnotation {

    /**
     * Client type.
     *
     * @return the client type
     */
    RequestType clientType();
}