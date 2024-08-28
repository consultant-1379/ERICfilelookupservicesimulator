/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2018
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.fls.client.context.lookup;

import java.util.Properties;

import javax.naming.*;

import org.apache.log4j.Logger;

import com.ericsson.fls.client.utility.Constant;

public class ContextLookup implements Constant {
    static Logger log = Logger.getLogger(ContextLookup.class);

    public static Object lookup(final Properties prop) {
        final String providerURL = REMOTE + prop.getProperty(HOSTNAME) + COLLON + prop.getProperty(PORT);
        final Properties contextLookupProp = new Properties();
        contextLookupProp.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        contextLookupProp.put(Context.PROVIDER_URL, providerURL);
        contextLookupProp.put(Context.SECURITY_PRINCIPAL, prop.getProperty(SECURITY_PRINCIPAL));
        contextLookupProp.put(Context.SECURITY_CREDENTIALS, prop.getProperty(SECURITY_CREDENTIALS));
        contextLookupProp.put(CLIENT_EJB_CONTEXT, CLIENT_EJB_CONTEXT_VALUE);
        InitialContext context = null;
        Object object = null;
        try {
            context = new InitialContext(contextLookupProp);
            object = context.lookup(JNDI_LOOKUP);
        } catch (final NamingException e) {
            log.error(e);
        }

        return object;

    }
}
