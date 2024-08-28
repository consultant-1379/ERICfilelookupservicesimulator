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
package com.ericsson.oss.services.fls.handler;

import static com.ericsson.oss.services.fls.constants.FLSConstant.FLS_PM_DATE_FORMAT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;

/**
 * Date type handler convert the date field in the resource to the desired format.
 *
 * @author ehimnay
 */
public class DateTypeHandler implements TypeHandler {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(FLS_PM_DATE_FORMAT);

    /**
     * parse the record to object with the default timezone.
     *
     * @param text
     *            text of the resource to be parsed.
     * @throws TypeConversionException
     *            thrown if the text cannot be parsed.
     * @return parsed object.
     */
    @Override
    public Object parse(final String text) throws TypeConversionException {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.parse(text);
        } catch (ParseException ex) {
            throw new TypeConversionException(ex);
        }
    }

    /**
     * change the format of the date field.
     *
     * @param value
     *            date field to be formatted.
     * @return formatted date field.
     */
    @Override
    public String format(final Object value) {
        if (value == null || value.toString().isEmpty()) {
            return "";
        }
        return dateFormat.format(value);
    }

    /**
     * data type of the field.
     *
     * @return instance of date class.
     */
    @Override
    public Class<?> getType() {
        return java.util.Date.class;
    }

}
