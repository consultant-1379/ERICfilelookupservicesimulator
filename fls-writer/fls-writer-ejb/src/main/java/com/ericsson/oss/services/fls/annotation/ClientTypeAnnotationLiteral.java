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

import javax.enterprise.util.AnnotationLiteral;

import com.ericsson.oss.services.fls.util.RequestType;

/**
 * The Class ClientTypeAnnotationLiteral.
 *
 * @author ehimnay
 */
public class ClientTypeAnnotationLiteral extends AnnotationLiteral<ClientTypeAnnotation> implements ClientTypeAnnotation {

    private static final long serialVersionUID = 3104827482839286984L;
    private final RequestType requestType;

    /**
     * Instantiates a new client annotation literal.
     *
     * @param requestType
     *            the client requestType
     */
    public ClientTypeAnnotationLiteral(final RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestType clientType() {
        return requestType;
    }
}