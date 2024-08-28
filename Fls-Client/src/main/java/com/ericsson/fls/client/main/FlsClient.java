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
package com.ericsson.fls.client.main;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ericsson.fls.client.context.lookup.ContextLookup;
import com.ericsson.fls.client.utility.Constant;
import com.ericsson.fls.client.utility.Utility;
import com.ericsson.oss.services.fls.InitiationRequest;
import com.ericsson.oss.services.fls.api.FLSManagementService;
import com.ericsson.oss.services.fls.util.RequestType;

public class FlsClient implements Constant {
    static Logger log = Logger.getLogger(FlsClient.class);
    static Properties prop;
    static {
        System.setProperty(LOG_TIMESTAMP, new SimpleDateFormat(LOG4J_DATE_FORMAT).format(new Date()));
        PropertyConfigurator.configure(System.getProperty(LOG4J_CONFIGURATION));
        prop = Utility.loadpropertFile();
    }

    public static void main(final String[] args) {
        FLSManagementService flsManagementService = null;
        flsManagementService = (FLSManagementService) ContextLookup.lookup(prop);
        final InitiationRequest initiationRequest = new InitiationRequest();
        initiationRequest.setType(RequestType.PMIC);
        final List<String> resourceList = new ArrayList<String>();
        resourceList.add(prop.getProperty(RESOURCE_PATH));
        log.info("Fls Services Initiated......");
        initiationRequest.setResourcePaths(resourceList);
        flsManagementService.initiate(initiationRequest);
        log.info("Fls Services completed ");
    }
}
