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

import static com.ericsson.oss.services.fls.api.FileLookupConstants.DATE_TIME_TIMEZONE_PATTERN;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * Json serializer to map long representation of date into a string consists of data and timezone.
 */
public class JsonDateWithTimezoneSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(final Date date, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        if (date != null) {
            final SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_TIMEZONE_PATTERN);
            final String formattedDate = formatter.format(date);
            jsonGenerator.writeString(formattedDate);
        }
    }
}