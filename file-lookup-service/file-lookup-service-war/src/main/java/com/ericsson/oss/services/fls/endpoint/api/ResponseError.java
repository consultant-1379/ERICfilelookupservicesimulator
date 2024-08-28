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
package com.ericsson.oss.services.fls.endpoint.api;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ericsson.oss.services.fls.lookup.util.JsonResponseCodeSerializer;

/**
 * Exception wrapper returned to client
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseError {

    @JsonSerialize(using = JsonResponseCodeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private Response.Status code;
    private String error;

    public ResponseError() {
    }

    public ResponseError(final Response.Status code, final String error) {
        this.code = code;
        this.error = error;
    }

    /**
     * Accessor for error code
     * 
     * @return error code
     */
    public Response.Status getCode() {
        return code;
    }

    /**
     * Mutator for code
     * 
     * @param code
     */
    public void setCode(final Response.Status code) {
        this.code = code;
    }

    /**
     * Accessor for error message
     * 
     * @return error message
     */
    public String getError() {
        return error;
    }

    /**
     * Mutator for error message
     * 
     * @param error
     *            message
     */
    public void setError(final String error) {
        this.error = error;
    }
}
