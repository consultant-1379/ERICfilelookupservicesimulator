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

package com.ericsson.oss.services.fls.api;

import java.util.HashMap;
import java.util.Map;

import com.ericsson.oss.services.fls.db.entity.PMRopInfo;
import com.ericsson.oss.services.fls.db.entity.ULSAInfo;

/**
 * Enum to define FLS Data Type.
 */
public enum FlsDataType {
    PM("PM", PMRopInfo.class),
    TOPOLOGY("TOPOLOGY", PMRopInfo.class),
    ULSA("ULSA", ULSAInfo.class);

    private String identifier;
    private Class<?> flsDataTypeClass;

    FlsDataType(String identifier, final Class<?> flsDataTypeClass) {
        this.identifier = identifier;
        this.flsDataTypeClass = flsDataTypeClass;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Class<?> getFlsDataTypeClass() {
        return flsDataTypeClass;
    }
}
