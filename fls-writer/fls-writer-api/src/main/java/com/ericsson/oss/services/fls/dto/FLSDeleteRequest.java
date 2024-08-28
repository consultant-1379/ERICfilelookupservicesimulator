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
package com.ericsson.oss.services.fls.dto;

import java.io.Serializable;

import com.ericsson.oss.services.fls.constants.DataType;

/**
 * Data Transfer Object for Delete Request
 *
 * @author ehimnay
 */
public class FLSDeleteRequest implements Serializable {

    private static final long serialVersionUID = 2972764867199967792L;

    private static final String EMPTY_STRING = "";

    private Integer retentionPeriodInMinutes;

    private DataType dataType;

    private String fileLocation;

    private String nodeType = EMPTY_STRING;

    public FLSDeleteRequest() {
    }

    public FLSDeleteRequest(final Integer retentionPeriodInMinutes, final DataType dataType, final String nodeType) {
        this.retentionPeriodInMinutes = retentionPeriodInMinutes;
        this.dataType = dataType;
        this.nodeType = nodeType;
    }

    public FLSDeleteRequest(final Integer retentionPeriodInMinutes, final DataType dataType) {
        this.retentionPeriodInMinutes = retentionPeriodInMinutes;
        this.dataType = dataType;
        this.nodeType = EMPTY_STRING;
    }

    /**
     * @return the retentionPeriodInMinutes
     */
    public Integer getRetentionPeriodInMinutes() {
        return retentionPeriodInMinutes;
    }

    /**
     * @param retentionPeriodInMinutes
     *            the retentionPeriodInMinutes to set.
     */
    public void setRetentionPeriodInMinutes(final Integer retentionPeriodInMinutes) {
        this.retentionPeriodInMinutes = retentionPeriodInMinutes;
    }

    /**
     * @return the dataType
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * @param dataType
     *            the dataType to set.
     */
    public void setDataType(final DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the fileLocation
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * @param fileLocation
     *            the fileLocation to set.
     */
    public void setFileLocation(final String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * @return the nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * @param nodeType
     *            the nodeType to set
     */
    public void setNodeType(final String nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public String toString() {
        return "FLSDeleteRequest [retentionPeriodInMinutes=" + retentionPeriodInMinutes + ", dataType=" + dataType + ", nodeType=" + nodeType
                + ", fileLocation=" + fileLocation + "]";
    }

}
