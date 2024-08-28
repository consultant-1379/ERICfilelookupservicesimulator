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

import static com.ericsson.oss.services.fls.api.FileLookupConstants.INVALID_LIMIT_VALUE;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.INVALID_PAGINATION_DATA;
import static com.ericsson.oss.services.fls.api.FileLookupConstants.MAX_LIMIT;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;

/**
 * Validate if request contains valid fields needed for pagination
 */
public class ValidPaginationDataValidator extends AbstractValidator {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final FileLookupRequest fileLookupRequest) throws FileLookupInvalidInputException {

        if (isNotEmpty(fileLookupRequest.getLimit())) {
            final int limit;
            try {
                limit = Integer.parseInt(fileLookupRequest.getLimit());
                Integer.parseInt(fileLookupRequest.getOffset());
            } catch (final NumberFormatException exception) {
                throw new FileLookupInvalidInputException(INVALID_PAGINATION_DATA);
            }
            if (limit > MAX_LIMIT) {
                throw new FileLookupInvalidInputException(INVALID_LIMIT_VALUE);
            }
        }
    }
}
