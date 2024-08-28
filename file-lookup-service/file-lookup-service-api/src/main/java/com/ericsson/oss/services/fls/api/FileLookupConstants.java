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

package com.ericsson.oss.services.fls.api;

/**
 * Definition of constants for REST API of File Lookup Service
 */
public final class FileLookupConstants {

    /**
     * End points & links
     */
    public static final String FLS_APPLICATION_END_POINT = "/files";
    public static final String FLS_APPLICATION_VERSION = "/v1";
    public static final String FLS_APPLICATION_PATH = "/file/v1/files/";
    public static final String FLS_CONTEXT_PATH = "/";
    public static final String LOGIN_CONTEXT_PATH = "/login";
    public static final String SECURITY_DOMAIN_NAME= "ApplicationRealm";
    
    /**
     * Pagination support values
     */
    public static final int MAX_LIMIT = 10_000;
    public static final int DEFAULT_OFFSET = 0;
    public static final int SUCCESS_CODE= 302;
    public static final int FAILURE_CODE= 200;
    /**
     * Hypertext Application Language
     */
    public static final String HAL_JSON = "application/hal+json";
    public static final String HAL_LINKS_TAG = "_links";
    public static final String HAL_SELF_TAG = "self";
    public static final String HAL_HREF_TAG = "href";

    /**
     * Constants to fetch a REST http GET requests
     */
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String SELECT = "select";
    public static final String FILTER = "filter";
    public static final String ORDER_BY = "orderBy";
    // Parse dataType filter condition for single and multiple dataTypes supporting OR conditions in FIQL. e. g. datatype==xyx; (datatype==abc,datatype==def)
    public static final String DATA_TYPE_REGEX = "dataType[!=][^=]*=[(]?((?:(?!(,dataType|\\)|;)).)*)";

    /**
     * Constants to unmarshal request and marshal response
     */
    public static final String SEMICOLON = ";";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_TIME_TIMEZONE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Error messages responded to client
     */
    public static final String INCORRECT_FILTER_CLAUSE = "Incorrect filter clause";
    public static final String INCORRECT_ARGUMENT_TYPE = "Incorrect argument type provided in filter clause";
    public static final String INCORRECT_DATE_FORMAT = "Incorrect date format. Provide date in any one of the following formats: " + DATE_TIME_PATTERN
            + " , " + DATE_TIME_PATTERN + "(-|+)HHmm";
    public static final String INCORRECT_REQUEST_FORMAT = "Incorrect request format";
    public static final String INCORRECT_SELECT_CLAUSE = "Field names provided in SELECT clause are not correct";
    public static final String INVALID_ENTITY_CLASS_TYPE = "Incorrect entity class provided";
    public static final String INVALID_METADATA_CLASS_TYPE = "Incorrect metadata class provided";
    public static final String INCORRECT_ORDER_BY_CLAUSE = "Incorrect order by clause";
    public static final String MANDATORY_FILTER_ABSENT = "Mandatory filter dataType absent";
    public static final String INVALID_PAGINATION_DATA = "Invalid pagination parameters";
    public static final String INVALID_LIMIT_VALUE = "Invalid limit value. Maximum limit is " + MAX_LIMIT;
    public static final String INVALID_QUERY_MULTIPLE_TABLES = "Query on different tables is not supported.";
    public static final String FAILURE_MESSAGE = "Authentication failed.";
    /**
     * SQL
     */
    public static final String ASC_ORDER = "ASC";
    public static final String DESC_ORDER = "DESC";

    /**
     * Authorization
     */
    public static final String AUTHORIZATION_POLICY_NAME = "file-lookup-service";
    public static final String AUTHORIZATION_POLICY_ACTION_READ = "read";

    /**
     * Utility class has only private constructor.
     */
    private FileLookupConstants() {}
}
