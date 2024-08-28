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

import com.ericsson.oss.services.fls.api.FileLookupRequest;
import com.ericsson.oss.services.fls.lookup.exception.FileLookupInvalidInputException;

/**
 * Abstract definition of file lookup request validator
 */
public abstract class AbstractValidator {

    /**
     * Validate request data and throw exception if validation fail.
     *
     * @param fileLookupRequest
     *            request sent to File Lookup Service
     * @throws FileLookupInvalidInputException
     */
    public abstract void validate(final FileLookupRequest fileLookupRequest) throws FileLookupInvalidInputException;

}
