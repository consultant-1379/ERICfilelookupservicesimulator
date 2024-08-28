/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.itpf.sdk.security.accesscontrol.classic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.itpf.sdk.security.accesscontrol.EAccessControl;
import com.ericsson.oss.itpf.sdk.security.accesscontrol.EPredefinedRole;
import com.ericsson.oss.itpf.sdk.security.accesscontrol.ESecurityAction;
import com.ericsson.oss.itpf.sdk.security.accesscontrol.ESecurityResource;
import com.ericsson.oss.itpf.sdk.security.accesscontrol.ESecuritySubject;
import com.ericsson.oss.itpf.sdk.security.accesscontrol.SecurityViolationException;

/**
 * Implementation of EAccessControl interface for testing.
 */
public class EAccessControlAltImplForTest extends EAccessControlImpl implements EAccessControl {
    private static final Logger logger = LoggerFactory.getLogger(EAccessControlAltImplForTest.class);

    /**
     * The expected authorized calls to isAuthorized.
     * The string format is based on the arguments passed to isAuthorized: subject;annotatedRoles;resource;action
     *
     * @see the accesscontrol annotations for more details
     */
    private static final String[] AUTHORIZED = new String[] {
        "fls_operator_user;[administrator];file-lookup-service;read"
    };

    /**
     * Same as AUTHORIZED but for unauthorized requests.
     */
    private static final String[] UNAUTHORIZED = new String[] {
        "user_with_no_role;[administrator];file-lookup-service;read",
    };

    @Override
    public ESecuritySubject getAuthUserSubject() throws SecurityViolationException {
        logger.debug("************************************************************");
        logger.debug("AccessControlAltImplForTest IS NOT FOR PRODUCTION USE.");
        logger.debug("AccessControlAltImplForTest: getAuthUserSubject called.");
        logger.debug("************************************************************");

        final String tmpDir = System.getProperty("java.io.tmpdir");
        final String useridFile = String.format("%s/currentAuthUser", tmpDir);
        String currentUser;
        try {
            currentUser = new String(Files.readAllBytes(Paths.get(useridFile)));
        } catch (final IOException ioe) {
            logger.error("Error reading {}, Details: {}", useridFile, ioe.getMessage());
            currentUser = "ioerror";
        }

        logger.debug("AccessControlAltImplForTest: getAuthUserSubject: user is <{}>", currentUser);
        return new ESecuritySubject(currentUser);
    }

    @Override
    public boolean isAuthorized(final ESecuritySubject secSubject, final ESecurityResource secResource, final ESecurityAction secAction,
                                final EPredefinedRole[] roles) throws SecurityViolationException, IllegalArgumentException {
        logger.debug("************************************************************");
        logger.debug("AccessControlAltImplForTest IS NOT FOR PRODUCTION USE.");
        logger.debug("AccessControlAltImplForTest: isAuthorized 1 called");
        logger.debug("************************************************************");
        final TreeSet<String> annotatedRoles = new TreeSet<>();
        final String action = secAction.getActionId().toLowerCase();
        final String subject = secSubject.getSubjectId().toLowerCase();
        final String resource = secResource.getResourceId().toLowerCase();

        for (final EPredefinedRole role : roles) {
            annotatedRoles.add(role.getLabel().toLowerCase());
        }

        final String authorization = String.format("%s;%s;%s;%s", subject, annotatedRoles, resource, action);

        if (Arrays.asList(AUTHORIZED).contains(authorization)) {
            return true;
        }

        if (Arrays.asList(UNAUTHORIZED).contains(authorization)) {
            // return false;
            throw new SecurityViolationException("The file-lookup-service dummy access control UNAUTHORIZED ACCESS for " + subject);
        }

        throw new IllegalStateException("The file-lookup-service dummy access control doesn't expect the authorization string : \"" + authorization
                + "\". Add the authorization string to " + EAccessControlAltImplForTest.class + ".UNAUTHORIZED or .AUTHORIZED");
    }

    @Override
    public boolean isAuthorized(final ESecuritySubject secSubject, final ESecurityResource secResource, final ESecurityAction secAction)
            throws SecurityViolationException, IllegalArgumentException {
        logger.debug("************************************************************");
        logger.debug("AccessControlAltImplForTest IS NOT FOR PRODUCTION USE.");
        logger.debug("AccessControlAltImplForTest: isAuthorized 2 called");
        logger.debug("************************************************************");
        final EPredefinedRole[] roles = { EPredefinedRole.OPERATOR, EPredefinedRole.ADMINISTRATOR };
        return isAuthorized(secSubject, secResource, secAction, roles);
    }

    @Override
    public boolean isAuthorized(final ESecurityResource secResource, final ESecurityAction secAction, final EPredefinedRole[] roles)
            throws SecurityViolationException, IllegalArgumentException {
        logger.debug("************************************************************");
        logger.debug("AccessControlAltImplForTest IS NOT FOR PRODUCTION USE.");
        logger.debug("AccessControlAltImplForTest: isAuthorized 3 called");
        logger.debug("************************************************************");
        return true;
    }

    @Override
    public boolean isAuthorized(final ESecurityResource secResource, final ESecurityAction secAction) throws SecurityViolationException,
            IllegalArgumentException {
        logger.debug("************************************************************");
        logger.debug("AccessControlAltImplForTest IS NOT FOR PRODUCTION USE.");
        logger.debug("AccessControlAltImplForTest: isAuthorized 4 called");
        logger.debug("************************************************************");
        return true;
    }
}
