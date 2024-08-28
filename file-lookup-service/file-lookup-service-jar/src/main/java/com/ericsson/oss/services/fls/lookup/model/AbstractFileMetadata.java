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

import static com.ericsson.oss.services.fls.api.FileLookupConstants.ASC_ORDER;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.ericsson.oss.services.fls.lookup.annotation.DefaultOrderingField;
import com.ericsson.oss.services.fls.lookup.annotation.DefaultedSelectedField;
import com.ericsson.oss.services.fls.lookup.annotation.MandatoryFilterField;
import com.ericsson.oss.services.fls.lookup.util.JsonDateWithTimezoneSerializer;

/**
 * Representation of file metadata
 */
@JsonSerialize(include = Inclusion.NON_NULL)
@SuppressWarnings("PMD.ShortVariable")
public class AbstractFileMetadata implements Serializable {

    @DefaultedSelectedField
    @DefaultOrderingField(orderType = ASC_ORDER)
    private Long id;

    private String nodeName;
    private String nodeType;

    @MandatoryFilterField
    @DefaultedSelectedField
    private String dataType;

    private Integer fileSize;

    @DefaultedSelectedField
    private String fileLocation;

    @DefaultedSelectedField
    @JsonSerialize(using = JsonDateWithTimezoneSerializer.class, include = Inclusion.NON_NULL)
    private Date fileCreationTimeInOss;

    /**
     * Accessor for id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Mutator for id
     *
     * @param id
     *            the id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Accessor for nodeName
     *
     * @return nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Mutator for nodeName
     *
     * @param nodeName
     *            the node name
     */
    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * Accessor for nodeType
     *
     * @return nodeType
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * Mutator for nodeType
     *
     * @param nodeType
     *            the node type
     */
    public void setNodeType(final String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Accessor for dataType
     *
     * @return dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Mutator for dataType
     *
     * @param dataType
     *            the data type
     */
    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    /**
     * Accessor for fileSize
     *
     * @return fileSize
     */
    public Integer getFileSize() {
        return fileSize;
    }

    /**
     * Mutator for fileSize
     *
     * @param fileSize
     *            the file size
     */
    public void setFileSize(final Integer fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Accessor for fileLocation
     *
     * @return fileLocation
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * Mutator for fileLocation
     *
     * @param fileLocation
     *            the file location
     */
    public void setFileLocation(final String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * Accessor for fileCreationTimeInOss
     *
     * @return fileCreationTimeInOss
     */
    public Date getFileCreationTimeInOss() {
        return fileCreationTimeInOss;
    }

    /**
     * Mutator for fileCreationTimeInOss
     *
     * @param fileCreationTimeInOss
     *            the file creation time in OSS
     */
    public void setFileCreationTimeInOss(final Date fileCreationTimeInOss) {
        this.fileCreationTimeInOss = fileCreationTimeInOss;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id + ", nodeName=" + nodeName + ", nodeType=" + nodeType + ", dataType=" + dataType
                + ", fileSize="
                + fileSize + ", fileLocation=" + fileLocation + ", fileCreationTimeInOss=" + fileCreationTimeInOss + "]";
    }
}
