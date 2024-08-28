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

package com.ericsson.oss.services.fls.lookup.mapper;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.COMMA;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.DEFAULT_OFFSET;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_FILTER_CLAUSE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INVALID_ENTITY_CLASS_TYPE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.MAX_LIMIT;
import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.PARAMETER_DEFINITION_ALIAS;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupMetadataUtil.getDefaultOrderByFieldName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.slf4j.Logger;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.db.core.FLSQuery;
import com.ericsson.oss.services.fls.db.jpql.JPQLExpression;
import com.ericsson.oss.services.fls.db.jpql.JPQLExpressionBuilder;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupException;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;
import com.ericsson.oss.services.fls.lookup.exception.IllegalFlsRequestArgument;
import com.ericsson.oss.services.fls.lookup.rsql.FlsJPQLExpressionVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.RSQLParserException;
import cz.jirutka.rsql.parser.ast.Node;

/**
 * Bean provides support for mapping of request into database query
 */
public class FileLookupRequestToQueryMapper<T> {

    private final static Pattern paramPattern = Pattern.compile(PARAMETER_DEFINITION_ALIAS);

    @Inject
    private Logger logger;

    /**
     * Map file lookup request into JPA query.
     *
     * @param request
     *            request sent to File Lookup Service
     * @return the query to FLS DB
     * @throws FileLookupException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public FLSQuery mapRequestToQuery(final FileLookupRequest request)
            throws FileLookupException {
        try {
            final T instance = (T) request.getFlsDataType().getFlsDataTypeClass().newInstance();
            final FlsJPQLExpressionVisitor<T> visitor = new FlsJPQLExpressionVisitor<T>(instance);
            final Node rootNode = new RSQLParser().parse(request.getFilter());
            final JPQLExpression jpqlExpression = rootNode.accept(visitor);
            final JPQLExpression sortedJpqlExpression = configureSorting(request, jpqlExpression);
            return createFlsQuery(request, sortedJpqlExpression);
        } catch (final RSQLParserException exception) {
            throw new FileLookupInvalidInputException(INCORRECT_FILTER_CLAUSE);
        } catch (final IllegalFlsRequestArgument exception) {
            throw new FileLookupInvalidInputException(exception);
        } catch (final InstantiationException | IllegalAccessException exception) {
            throw new FileLookupInvalidInputException(INVALID_ENTITY_CLASS_TYPE);
        }

    }

    /**
     * And GroupBy clause to request if provided or sort by id (asc).
     *
     * @param request
     *            request sent to File Lookup Service
     * @param jpqlWhereExpression
     *            expression contains where clause
     * @return JPQLExpression with where and orderBy clause
     */
    private JPQLExpression configureSorting(final FileLookupRequest request, final JPQLExpression jpqlWhereExpression) {
        final JPQLExpression sortingExpression;
        if (isNotBlank(request.getOrderBy())) {
            logger.debug("Sorting : orderBy clause provided by user: {}", request.getOrderBy());
            final String[] orderByClauseArray = request.getOrderBy().split(COMMA);
            sortingExpression = JPQLExpressionBuilder.sortExpression(orderByClauseArray);
        } else {
            final Class<?> clazz = request.getFlsDataType().getFlsDataTypeClass();
            logger.debug("Sorting : Empty orderBy clause. Continue with default ordering by {}", getDefaultOrderByFieldName(clazz));
            sortingExpression = JPQLExpressionBuilder.sortExpression(getDefaultOrderByFieldName(clazz));
        }
        return JPQLExpressionBuilder.orderBy(jpqlWhereExpression, sortingExpression);
    }

    /**
     * Create a query to FLS DB
     *
     * @param fileLookupRequest
     *            request sent to File Lookup Service
     * @param jpqlExpression
     *            JPQLExpression with where and orderBy clause
     * @return the query to FLS DB
     */
    private FLSQuery createFlsQuery(final FileLookupRequest fileLookupRequest, final JPQLExpression jpqlExpression) {
        int limit = MAX_LIMIT;
        int offset = DEFAULT_OFFSET;
        final String expression = replaceAliases(jpqlExpression.getWhereClause());
        if (isNotBlank(fileLookupRequest.getLimit())) {
            limit = Integer.parseInt(fileLookupRequest.getLimit());
            offset = Integer.parseInt(fileLookupRequest.getOffset());
        }
        return new FLSQuery(expression, jpqlExpression.getParameters(), limit, offset);
    }

    /**
     * Replace parameter aliases with values
     *
     * @param whereClause
     *            the where clause contains {@link static PARAMETER_DEFINITION_ALIAS} aliases
     * @return where clause with iterated parameter aliases
     */
    private String replaceAliases(final String whereClause) {
        final Matcher matcher = paramPattern.matcher(whereClause);
        final StringBuffer buffer = new StringBuffer();
        int iterator = 1;
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "?" + iterator++);
        }
        matcher.appendTail(buffer);
        final String newWhereClause = buffer.toString();
        logger.debug("FLS query with param aliases replaced by iterated parameters{}", newWhereClause);
        return newWhereClause;
    }

}
