/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.oss.services.fls.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import com.ericsson.oss.services.fls.exceptions.FlsDbException;
import com.ericsson.oss.services.fls.interceptor.binding.CheckFlsAvailability;
import com.ericsson.oss.services.fls.listener.config.ConfigurationChangeListener;

/**
 * Interceptor used to validate FLS status. It raise an exception in case the FLS is not available.
 *
 * @author ebialan
 */
@Interceptor
@CheckFlsAvailability
public class FlsAvailabilityInterceptor {

    @Inject
    private Logger logger;

    @Inject
    private ConfigurationChangeListener configurationChangeListener;

    @AroundInvoke
    public Object intercept(final InvocationContext ctx) throws Exception {
        if (configurationChangeListener.isStopAllOperationOnFlsDB()) {
            logger.warn("FLS database migration in progress.");
            throw new FlsDbException("FLS is unavailable: Migration in progress.");
        }
        final String className = ctx.getMethod().getDeclaringClass().getName();
        final String methodName = ctx.getMethod().getName();
        final long startTime = System.currentTimeMillis();
        final Object obj = ctx.proceed();
        logger.debug("FLS operation {}:{} took {} ms.", className, methodName, System.currentTimeMillis() - startTime);
        return obj;
    }
}
