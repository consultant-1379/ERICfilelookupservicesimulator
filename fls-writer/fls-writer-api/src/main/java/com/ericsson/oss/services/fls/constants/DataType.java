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
package com.ericsson.oss.services.fls.constants;

/**
 * DataType enum is used to identify the file.
 *
 * @author ehimnay
 */
public enum DataType {
    STATISTICAL("PM_STATISTICAL"),
    CELLTRACE("PM_CELLTRACE"),
    UETRACE("PM_UETRACE"),
    EVENTS("PM_EVENTS"),
    EBS("PM_EBS"),
    EBM("PM_EBM"),
    CONTINUOUSCELLTRACE("PM_CONTINUOUSCELLTRACE"),
    CTUM("PM_CTUM"),
    UETR("PM_UETR"),
    CTR("PM_CTR"),
    GPEH("PM_GPEH"),
    CELLTRAFFIC("PM_CELLTRAFFIC"),
    EBSM_3GPP("PM_EBSM_3GPP"),
    EBSM_ENIQ("PM_EBSM_ENIQ"),
    EBSL("PM_EBSL"),
    ULSA("ULSA"),
    UNKNOWN("UNKNOWN");

    private final String dataType;

    DataType(final String dataType) {
        this.dataType = dataType;
    }

    /**
     * This returns {@link #name()}
     *
     * @return String representation of enum constant
     */
    public String value() {
        return dataType;
    }

    /**
     * This returns enum equivalent of string.
     *
     * @return enum representation of string constant
     */
    public static DataType getEnum(final String value) {
        for (final DataType type : values()) {
            if (type.value().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return DataType.UNKNOWN;
    }

}
