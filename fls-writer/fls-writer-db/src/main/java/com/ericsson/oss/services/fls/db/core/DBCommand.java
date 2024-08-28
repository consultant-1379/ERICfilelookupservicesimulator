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

import javax.persistence.EntityManager;

/**
 * Contains the logic of the command to be executed in the DB.
 *
 * @author ehimnay
 */
public interface DBCommand<T> {

    /**
     * Contains the logic of the command to be executed in the DB.
     *
     * @param em
     *            EntityManager used to perform the command operations.
     * @return An object of type defined in <T>
     */
    T execute(EntityManager em);

}
