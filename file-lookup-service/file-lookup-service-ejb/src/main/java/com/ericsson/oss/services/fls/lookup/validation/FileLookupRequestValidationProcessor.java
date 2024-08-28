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

package com.ericsson.oss.services.fls.lookup.validation;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;
import com.ericsson.oss.services.fls.lookup.validation.rule.AbstractValidator;

/**
 * Processor class to validate file lookup request
 */
public class FileLookupRequestValidationProcessor {

    @Inject
    @Any
    private Instance<AbstractValidator> validators;

    /**
     * Validate request using all configured validators
     *
     * @param fileLookupRequest
     * @throws FileLookupInvalidInputException
     */
    public void processValidation(final FileLookupRequest fileLookupRequest) throws FileLookupInvalidInputException {
        for (final AbstractValidator validator : validators) {
            validator.validate(fileLookupRequest);
        }
    }
}
