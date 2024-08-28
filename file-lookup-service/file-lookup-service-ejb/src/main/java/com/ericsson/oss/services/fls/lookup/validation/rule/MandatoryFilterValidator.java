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

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import static com.ericsson.oss.services.fls.api.FileLookupConstants.INCORRECT_FILTER_CLAUSE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.MANDATORY_FILTER_ABSENT;
import static com.ericsson.oss.services.fls.lookup.util.FileLookupMetadataUtil.getMandatoryFilterFieldsList;

import java.util.List;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;

/**
 * Validate if request contains mandatory filters
 */
public class MandatoryFilterValidator extends AbstractValidator {

    private static final String FIELD_NAME_REGEX = "{fieldName}";
    private static final String VALIDATION_PATTERN = String.format(".*%s[!=].*", FIELD_NAME_REGEX);

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final FileLookupRequest fileLookupRequest) throws FileLookupInvalidInputException {
        final String filterClause = fileLookupRequest.getFilter();
        if (!isNotEmpty(filterClause)) {
            throw new FileLookupInvalidInputException(INCORRECT_FILTER_CLAUSE);
        }
        final List<String> mandatoryFilterFields = getMandatoryFilterFieldsList(fileLookupRequest.getFlsDataType().getFlsDataTypeClass());
        for (final String mandatoryFieldName : mandatoryFilterFields) {
            final String mandatoryFieldRegex = VALIDATION_PATTERN.replace(FIELD_NAME_REGEX, mandatoryFieldName);
            if (!filterClause.matches(mandatoryFieldRegex)) {
                throw new FileLookupInvalidInputException(MANDATORY_FILTER_ABSENT);
            }
        }
    }
}
