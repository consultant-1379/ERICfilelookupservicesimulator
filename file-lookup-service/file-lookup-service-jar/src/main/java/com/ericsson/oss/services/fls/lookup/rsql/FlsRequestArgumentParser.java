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
package com.ericsson.oss.services.fls.lookup.rsql;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.DATE_TIME_PATTERN;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.DATE_TIME_TIMEZONE_PATTERN;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_ARGUMENT_TYPE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_DATE_FORMAT;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.fls.lookup.exception.IllegalFlsRequestArgument;

/**
 * Parser used for parsing given string argument from RSQL query according to type of the target property
 */
public class FlsRequestArgumentParser {

    private static final Logger logger = LoggerFactory.getLogger(FlsRequestArgumentParser.class);

    private static final Pattern DATE_TIME_REGEXP_PATTERN           = Pattern.compile("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])(T)([01]\\d|2[0-3])(:[0-5]\\d){2}$");
    private static final Pattern DATE_TIME_TIMEZONE_REGEXP_PATTERN  = Pattern.compile("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])(T)([01]\\d|2[0-3])(:[0-5]\\d){2}(-|\\+)\\d{4}$");

    /**
     * Create an array of arguments casted to their correct types.
     *
     * @param arguments
     *            string arguments
     * @param type
     *            class type
     * @param <T>
     *            type of given argument
     * @return the list of arguments in proper type
     */
    public static <T> List<T> parse(final List<String> arguments, final Class<T> type) {
        final List<T> castedArguments = new ArrayList<>();
        for (final String argument : arguments) {
            castedArguments.add(parse(argument, type));
        }
        return castedArguments;
    }

    /**
     * Parse given string argument as the specified class type.
     *
     * @param argument
     *            the given string argument
     * @param type
     *            class type
     * @param <T>
     *            type of given argument
     * @return argument in proper type
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity" })
    private static <T> T parse(final String argument, final Class<T> type) {
        logger.debug("Parsing argument '{}' as type {}", argument, type);

        if (argument == null || "null".equals(argument.trim().toLowerCase())) {
            return null;
        }

        try {
            if (type.equals(String.class)) {
                return (T) argument;
            } else if (type.equals(Integer.class)|| type.equals(Integer.TYPE)) {
                return (T) Integer.valueOf(argument);
            } else if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
                return (T) Boolean.valueOf(argument);
            } else if (type.isEnum()) {
                return (T) Enum.valueOf((Class<Enum>) type, argument);
            } else if (type.equals(Float.class) || type.equals(Float.TYPE)) {
                return (T) Float.valueOf(argument);
            } else if (type.equals(Double.class) || type.equals(Double.TYPE)) {
                return (T) Double.valueOf(argument);
            } else if (type.equals(Long.class) || type.equals(Long.TYPE)) {
                return (T) Long.valueOf(argument);
            } else if (type.equals(BigDecimal.class)) {
                return (T) new BigDecimal(argument);
            } else if (type.equals(Date.class)) {
                return (T) parseDate(argument);
            }
        } catch (final IllegalArgumentException exception) {
            throw new IllegalArgumentException(INCORRECT_ARGUMENT_TYPE);
        }
        throw new IllegalFlsRequestArgument(INCORRECT_ARGUMENT_TYPE);

    }

    /**
     * Parse a date in one of format {@link static DATE_TIME_PATTERN} {@link static DATE_TIME_TIMEZONE_PATTERN}
     *
     * @param dateArgument
     *            the given date
     * @return date
     */
    private static Date parseDate(final String dateArgument) {
        try {
            if (DATE_TIME_REGEXP_PATTERN.matcher(dateArgument).matches()) {
                return new SimpleDateFormat(DATE_TIME_PATTERN).parse(dateArgument);
            } else if (DATE_TIME_TIMEZONE_REGEXP_PATTERN.matcher(dateArgument).matches()) {
                return new SimpleDateFormat(DATE_TIME_TIMEZONE_PATTERN).parse(dateArgument);
            }
        } catch (final ParseException exception) {
            throw new IllegalFlsRequestArgument(INCORRECT_DATE_FORMAT);
        }
        throw new IllegalFlsRequestArgument(INCORRECT_DATE_FORMAT);
    }

}