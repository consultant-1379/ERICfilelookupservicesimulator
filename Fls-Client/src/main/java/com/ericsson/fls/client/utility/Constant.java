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
package com.ericsson.fls.client.utility;

public interface Constant {
    String LOG_TIMESTAMP = "log.timestamp";
    String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    String REMOTE = "remote://";
    String HOSTNAME = "HOSTNAME";
    String PORT = "PORT";
    String RESOURCE_PATH = "RESOURCE_PATH";
    String LOG4J_CONFIGURATION = "log4j.configuration";
    String SECURITY_PRINCIPAL = "SECURITY_PRINCIPAL";
    String SECURITY_CREDENTIALS = "SECURITY_CREDENTIALS";
    String CLIENT_EJB_CONTEXT = "jboss.naming.client.ejb.context";
    boolean CLIENT_EJB_CONTEXT_VALUE = true;
    String LOG4J_DATE_FORMAT = "yyyyMMdd_HHmmss";
    String JNDI_LOOKUP = "java:fls-writer-ear-1.16.1-SNAPSHOT/fls-writer-ejb-1.16.1-SNAPSHOT/FLSManagementServiceImpl!com.ericsson.oss.services.fls.api.FLSManagementService";
    String PROPERTYFILE = "propertyFile";
    String COLLON = ":";

}
