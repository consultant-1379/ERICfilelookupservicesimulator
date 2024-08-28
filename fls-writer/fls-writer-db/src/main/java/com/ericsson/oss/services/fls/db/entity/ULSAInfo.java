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
package com.ericsson.oss.services.fls.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ULSAInfo.
 *
 * @author erobnas
 */
@Entity
@Table(name = "ulsa_info")
@Access(value = AccessType.FIELD)
public class ULSAInfo implements Serializable {

    private static final long serialVersionUID = -1772711958209111199L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "node_name", nullable = false)
    private String nodeName;

    @Column(name = "node_type", nullable = false)
    private String nodeType;

    @Column(name = "data_type", nullable = false)
    private String dataType;

    @Column(name = "radio_unit", nullable = false)
    private String radioUnit;

    @Column(name = "rf_port", nullable = false)
    private String rfPort;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Column(name = "file_location", nullable = false)
    private String fileLocation;

    @Column(name = "file_creationtime_in_oss", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fileCreationTimeInOss;

    @Column(name = "sample_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sampleTime;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param nodeName
     *            the nodeName to set.
     */
    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return the nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * @param nodeType
     *            the nodeType to set.
     */
    public void setNodeType(final String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType
     *            the dataType to set.
     */
    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the radioUnit
     */
    public String getRadioUnit() {
        return radioUnit;
    }

    /**
     * @param radioUnit
     *            the radioUnit to set.
     */
    public void setRadioUnit(final String radioUnit) {
        this.radioUnit = radioUnit;
    }

    /**
     * @return the rfPort
     */
    public String getRfPort() {
        return rfPort;
    }

    /**
     * @param rfPort
     *            the rfPort to set.
     */
    public void setRfPort(final String rfPort) {
        this.rfPort = rfPort;
    }

    /**
     * @return the fileSize
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize
     *            the fileSize to set.
     */
    public void setFileSize(final Integer fileSize) {
        this.fileSize = fileSize;
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
     * @return the fileCreationTimeInOss
     */
    public Date getFileCreationTimeInOss() {
        return fileCreationTimeInOss;
    }

    /**
     * @param fileCreationTimeInOss
     *            the fileCreationTimeInOss to set.
     */
    public void setFileCreationTimeInOss(final Date fileCreationTimeInOss) {
        this.fileCreationTimeInOss = fileCreationTimeInOss;
    }

    /**
     * @return the sampleTime
     */
    public Date getSampleTime() {
        return sampleTime;
    }

    /**
     * @param sampleTime
     *            the sampleTimeInOss to set.
     */
    public void setSampleTime(final Date sampleTime) {
        this.sampleTime = sampleTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ULSAInfo other = (ULSAInfo) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringBuffer().append("ULSAInfo [id=" + id).append(", nodeName=" + nodeName).append(", nodeType=" + nodeType)
                .append(", dataType=" + dataType).append(", radioUnit=" + radioUnit).append(", rfPort=" + rfPort).append(", fileSize=" + fileSize)
                .append(", fileLocation=" + fileLocation).append(", fileCreationTimeInOss=" + fileCreationTimeInOss)
                .append(", sampleTime" + sampleTime + "]").toString();
    }

}
