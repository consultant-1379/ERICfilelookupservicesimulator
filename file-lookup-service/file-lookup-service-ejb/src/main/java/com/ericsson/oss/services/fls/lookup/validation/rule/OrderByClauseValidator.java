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

package com.ericsson.oss.services.fls.lookup.validation.rule;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.COMMA;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_ORDER_BY_CLAUSE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.SPACE;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupMetadataUtil.isFileMetadataField;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupUtil.isCorrectOrderType;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;

/**
 * Validate if request contains valid orderBy clause
 */
public class OrderByClauseValidator extends AbstractValidator {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final FileLookupRequest fileLookupRequest) throws FileLookupInvalidInputException {
        final String orderByClause = fileLookupRequest.getOrderBy();
        if (isNotEmpty(orderByClause)) {
            try {
                final String[] orderByClauseArray = fileLookupRequest.getOrderBy().split(COMMA);
                for (final String singleOrderByClause : orderByClauseArray) {
                    final String[] orderByClauseValues = singleOrderByClause.split(SPACE);
                    final String fieldName = orderByClauseValues[0];
                    final String order = orderByClauseValues[1];
                    if (isBlank(fieldName) || isBlank(order) || !isCorrectOrderType(order)
                            || !isFileMetadataField(fileLookupRequest.getFlsDataType().getFlsDataTypeClass(), fieldName)) {
                        throw new FileLookupInvalidInputException(INCORRECT_ORDER_BY_CLAUSE);
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException exception) {
                throw new FileLookupInvalidInputException(INCORRECT_ORDER_BY_CLAUSE);
            }
        }
    }
}
