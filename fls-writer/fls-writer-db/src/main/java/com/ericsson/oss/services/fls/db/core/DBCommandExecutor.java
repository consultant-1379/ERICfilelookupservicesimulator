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
package com.ericsson.oss.services.fls.db.core;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Helper class responsible for running a given {@link DBCommand} in the DB.
 *
 * @author ehimnay
 */
public class DBCommandExecutor {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    /**
     * wrapper method to execute db commands.
     *
     * @param dbCommand
     *          commands to be executed.
     * @return An object of type defined in <T>
     */
    public <T> T executeCommand(final DBCommand<T> dbCommand) {
        final EntityManager entityManager = getEntityManager();
        try {
            return dbCommand.execute(entityManager);
        } catch (final Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerProvider.getEntityManager();
    }
}
