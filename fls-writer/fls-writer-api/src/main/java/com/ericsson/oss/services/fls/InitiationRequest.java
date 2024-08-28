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
package com.ericsson.oss.services.fls;

import java.io.Serializable;
import java.util.List;

import com.ericsson.oss.services.fls.util.RequestType;

/**
 * The Initiation request for FLS.
 *
 * @author ehimnay
 */
public class InitiationRequest implements Serializable {

    private static final long serialVersionUID = 743688507399087348L;

    private RequestType type;

    private List<String> resourcePaths;

    /**
     * Instantiates a new Initiation request.
     */
    public InitiationRequest() {
    }

    /**
     * Instantiates a new Initiation request with persistence time.
     *
     * @param type
     *         the request type
     *
     */
    public InitiationRequest(final RequestType type, final List<String> resourcePaths) {
        super();
        this.type = type;
        this.resourcePaths = resourcePaths;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public RequestType getType() {
        return type;
    }

    /**
     * Gets resource paths.
     *
     * @return the resource paths
     */
    public List<String> getResourcePaths() {
        return resourcePaths;
    }

    /**
     * Sets persistence time.
     *
     * @param type
     *         the persistence time
     */
    public void setType(final RequestType type) {
        this.type = type;
    }

    /**
     * Sets resource paths.
     *
     * @param resourcePaths
     *         the resource paths
     */
    public void setResourcePaths(final List<String> resourcePaths) {
        this.resourcePaths = resourcePaths;
    }

    @Override
    public String toString() {
        return new StringBuilder()
        .append("InitiationRequest [type= ")
        .append(type)
        .append(" resourcePaths= ")
        .append(resourcePaths)
        .append("]").toString();
    }

}
