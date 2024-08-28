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

/**
 * Data Transfer Object for Delete Response
 *
 * @author ehimnay
 */
public class FLSDeleteResponse implements Serializable {

    private static final long serialVersionUID = 4061410942362785790L;

    private Integer noOfRecordsDeleted;

    public FLSDeleteResponse() {
    }

    public FLSDeleteResponse(final Integer noOfRecordsDeleted) {
        this.noOfRecordsDeleted = noOfRecordsDeleted;
    }

    /**
     * @return the noOfRecordsDeleted
     */
    public Integer getNoOfRecordsDeleted() {
        return noOfRecordsDeleted;
    }

    /**
     * @param noOfRecordsDeleted
     *            the noOfRecordsDeleted to set.
     */
    public void setNoOfRecordsDeleted(final Integer noOfRecordsDeleted) {
        this.noOfRecordsDeleted = noOfRecordsDeleted;
    }

    @Override
    public String toString() {
        return "FLSDeleteResponse [noOfRecordsDeleted=" + noOfRecordsDeleted + "]";
    }

}