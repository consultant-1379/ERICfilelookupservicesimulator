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

package com.ericsson.oss.services.fls.lookup.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.ericsson.oss.services.fls.lookup.util.JsonDateWithTimezoneSerializer;

/**
 * Representation of file metadata
 */
@JsonSerialize(include = Inclusion.NON_NULL)
@SuppressWarnings("PMD.ShortVariable")
public class PmFileMetadata extends AbstractFileMetadata implements Serializable {

    private String fileType;

    @JsonSerialize(using = JsonDateWithTimezoneSerializer.class, include = Inclusion.NON_NULL)
    private Date startRopTimeInOss;

    @JsonSerialize(using = JsonDateWithTimezoneSerializer.class, include = Inclusion.NON_NULL)
    private Date endRopTimeInOss;

    /**
     * Accessor for fileType
     *
     * @return fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Mutator for fileType
     *
     * @param fileType
     *            the file type
     */
    public void setFileType(final String fileType) {
        this.fileType = fileType;
    }

    /**
     * Accessor for startRopTimeInOss
     *
     * @return startRopTimeInOss
     */
    public Date getStartRopTimeInOss() {
        return startRopTimeInOss;
    }

    /**
     * Mutator for startRopTimeInOss
     *
     * @param startRopTimeInOss
     *            the start rop time in OSS
     */
    public void setStartRopTimeInOss(final Date startRopTimeInOss) {
        this.startRopTimeInOss = startRopTimeInOss;
    }

    /**
     * Accessor for endRopTimeInOss
     *
     * @return endRopTimeInOss
     */
    public Date getEndRopTimeInOss() {
        return endRopTimeInOss;
    }

    /**
     * Mutator for endRopTimeInOss
     *
     * @param endRopTimeInOss
     *            the end rop time in OSS
     */
    public void setEndRopTimeInOss(final Date endRopTimeInOss) {
        this.endRopTimeInOss = endRopTimeInOss;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + super.toString() + ", fileType=" + fileType + ", startRopTimeInOss=" + startRopTimeInOss
                + ", endRopTimeInOss=" + endRopTimeInOss + "]";
    }
}
