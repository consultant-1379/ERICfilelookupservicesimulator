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

package com.ericsson.oss.services.fls.lookup.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.ericsson.oss.services.fls.lookup.annotation.DefaultedSelectedField;
import com.ericsson.oss.services.fls.lookup.util.JsonDateWithTimezoneSerializer;

/**
 * Representation of file metadata
 */
@JsonSerialize(include = Inclusion.NON_NULL)
@SuppressWarnings("PMD.ShortVariable")
public class UlsaFileMetadata extends AbstractFileMetadata implements Serializable {

    private String radioUnit;
    private String rfPort;

    @DefaultedSelectedField
    @JsonSerialize(using = JsonDateWithTimezoneSerializer.class, include = Inclusion.NON_NULL)
    private Date sampleTime;

    /**
     * Accessor for radioUnit
     *
     * @return radioUnit
     */
    public String getRadioUnit() {
        return radioUnit;
    }

    /**
     * Mutator for radioUnit
     *
     * @param radioUnit
     *            the Radio Unit
     */
    public void setRadioUnit(final String radioUnit) {
        this.radioUnit = radioUnit;
    }

    /**
     * Accessor for rfPort
     *
     * @return radioUnit
     */
    public String getRfPort() {
        return rfPort;
    }

    /**
     * Mutator for rfPort
     *
     * @param rfPort
     *            the RF Port
     */
    public void setRfPort(final String rfPort) {
        this.rfPort = rfPort;
    }

    /**
     * Accessor for sampleTime
     *
     * @return sampleTime
     */
    public Date getSampleTime() {
        return sampleTime;
    }

    /**
     * Mutator for sampleTime
     *
     * @param sampleTime
     *            the file creation time of the sample file
     */
    public void setSampleTime(final Date sampleTime) {
        this.sampleTime = sampleTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + super.toString() + ", radioUnit=" + radioUnit + ", rfPort=" + rfPort + ", sampleTime=" + sampleTime
                + "]";
    }
}
