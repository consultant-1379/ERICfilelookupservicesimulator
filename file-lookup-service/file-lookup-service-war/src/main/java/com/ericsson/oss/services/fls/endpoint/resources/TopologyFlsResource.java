/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.oss.services.fls.endpoint.resources;

import javax.enterprise.context.ApplicationScoped;

import com.ericsson.oss.services.fls.api.FlsDataType;

/**
 * Topology Fls Resource
 *
 */
@ApplicationScoped
public class TopologyFlsResource extends PmFlsResource {

    @Override
    public FlsDataType getFlsDataType() {
        return FlsDataType.TOPOLOGY;
    }
}
