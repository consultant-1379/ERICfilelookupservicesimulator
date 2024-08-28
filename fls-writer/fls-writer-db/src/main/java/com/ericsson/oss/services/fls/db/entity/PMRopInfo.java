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
package com.ericsson.oss.services.fls.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PMRopInfo.
 *
 * @author  ehimnay
 */
@Entity
@Table(name = "pm_rop_info")
@Access(value=AccessType.FIELD)
public class PMRopInfo implements Serializable {

    private static final long serialVersionUID = 8012517824551327312L;

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

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Column(name = "file_location", nullable = false)
    private String fileLocation;

    @Column(name = "file_creationtime_in_oss", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fileCreationTimeInOss;

    @Column(name = "start_roptime_in_oss", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startRopTimeInOss;

    @Column(name = "end_roptime_in_oss", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endRopTimeInOss;

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
     * @return the datatype
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param datatype
     *            the datatype to set.
     */
    public void setDataType(final String datatype) {
        this.dataType = datatype;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     *            the fileType to set.
     */
    public void setFileType(final String fileType) {
        this.fileType = fileType;
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
     * @return the startRopTimeInOss
     */
    public Date getStartRopTimeInOss() {
        return startRopTimeInOss;
    }

    /**
     * @param startRopTimeInOss
     *            the startRopTimeInOss to set.
     */
    public void setStartRopTimeInOss(final Date startRopTimeInOss) {
        this.startRopTimeInOss = startRopTimeInOss;
    }

    /**
     * @return the endRopTimeInOss
     */
    public Date getEndRopTimeInOss() {
        return endRopTimeInOss;
    }

    /**
     * @param endRopTimeInOss
     *            the endRopTimeInOss to set.
     */
    public void setEndRopTimeInOss(final Date endRopTimeInOss) {
        this.endRopTimeInOss = endRopTimeInOss;
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
        final PMRopInfo other = (PMRopInfo) obj;
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
        return new StringBuffer()
                .append("PMRopInfo [id=" + id)
                .append(", nodeName=" + nodeName)
                .append(", nodeType=" + nodeType)
                .append(", dataType=" + dataType)
                .append(", fileType=" + fileType)
                .append(", fileSize=" + fileSize)
                .append(", fileLocation=" + fileLocation)
                .append(", fileCreationTimeInOss=" + fileCreationTimeInOss)
                .append(", startRopTimeInOss=" + startRopTimeInOss)
                .append(", endRopTimeInOss=" + endRopTimeInOss + "]").toString();
    }

}
