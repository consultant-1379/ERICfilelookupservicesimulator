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

import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.FLS_PU;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EntityManager provider class for fls persistence unit.
 *
 * @author ehimnay
 */
@ApplicationScoped
public class FLSEntityManagerProvider implements EntityManagerProvider {

    @PersistenceContext(name = FLS_PU)
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

}