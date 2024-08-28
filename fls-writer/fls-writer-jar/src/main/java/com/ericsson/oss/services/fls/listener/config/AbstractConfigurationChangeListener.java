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
package com.ericsson.oss.services.fls.listener.config;

import javax.inject.Inject;

import com.ericsson.oss.itpf.sdk.recording.EventLevel;
import com.ericsson.oss.itpf.sdk.recording.SystemRecorder;

/**
 * Abstract configuration change listener.
 *
 * @author ehimnay
 */
public abstract class AbstractConfigurationChangeListener {

    @Inject
    private SystemRecorder systemRecorder;

    /**
     * Validate path for string value.
     *
     * @param parameterName
     *            - name of parameter to be validated
     * @param oldValue
     *            - old value of parameter
     * @param newValue
     *            - new value of parameter
     */
    protected void validatePathForStringValue(final String parameterName, final String oldValue,
                                              final String newValue) {
        if (oldValue.equals(newValue)) {
            logNoChange(parameterName, oldValue);
        } else {
            logChange(parameterName, oldValue, newValue);
        }
    }

    /**
     * Log change.
     *
     * @param parameterName
     *            - name of parameter to be validated
     * @param oldValue
     *            - old value of parameter
     * @param newValue
     *            - new value of parameter
     */
    protected void logChange(final String parameterName, final Object oldValue, final Object newValue) {
        systemRecorder.recordEvent("FLS.CONFIGURATION_CHANGE_NOTIFICATION", EventLevel.COARSE, "CONFIGURATION_SERVICE",
                parameterName, parameterName + " parameter value changed, old value = '" + oldValue + "' new value = '"
                        + newValue + "'");
    }

    /**
     * Log no change.
     *
     * @param parameterName
     *            - name of parameter to be validated
     * @param oldValue
     *            - old value of parameter
     */
    protected void logNoChange(final String parameterName, final Object oldValue) {
        systemRecorder.recordEvent("FLS.CONFIGURATION_CHANGE_NOTIFICATION", EventLevel.COARSE, "CONFIGURATION_SERVICE",
                parameterName,
                parameterName
                        + " parameter was not changed. Either the new value is the same or the new value was malformed"
                        + " and the change was ignored. Value is still " + oldValue);
    }
}
