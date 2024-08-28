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
package com.ericsson.oss.services.fls.lookup.util;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Json serializer to map status code of response
 */
public class JsonResponseCodeSerializer extends JsonSerializer<Response.Status> {

    @Override
    public void serialize(final Response.Status status, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider)
            throws IOException {

        if (status != null) {
            jsonGenerator.writeString(String.valueOf(status.getStatusCode()));
        }
    }
}