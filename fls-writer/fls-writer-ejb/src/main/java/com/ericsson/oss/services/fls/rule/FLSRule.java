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
package com.ericsson.oss.services.fls.rule;

import java.util.List;

import com.ericsson.oss.services.fls.util.FileResourceContainer;

/**
 * This interface is provided to different rules on creating batch of resource.
 *
 * @author ehimnay
 */
public interface FLSRule {
    List<FileResourceContainer> applyRules(final String resourcePath);
}
