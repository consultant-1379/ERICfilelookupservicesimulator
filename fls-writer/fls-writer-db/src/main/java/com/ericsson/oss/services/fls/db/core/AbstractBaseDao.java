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

import static com.ericsson.oss.services.fls.db.api.FlsDbConstants.*;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract DAO interacts with a database via JPA. Provides a few basic methods so that we don't need to re-implement
 * them for every entity of the system. As this class uses {@link DBCommandExecutor} to manage the transactions, all the
 * operations are liable to throw {@link IllegalStateException} if something goes wrong while committing/rolling back a
 * transaction.
 *
 * @param <ID>
 *            The type of the id of the entity to be managed by this repository.
 * @param <T>
 *            The type of the entity to be managed by this repository.
 * @see DBCommandExecutor#executeCommand(DBCommand)
 *
 * @author ehimnay
 */
public abstract class AbstractBaseDao<ID, T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    protected DBCommandExecutor dbCommandExecutor;

    /**
     * @return The class of the type T, i.e., the type of the managed entity.
     */
    protected abstract Class<T> getPersistentClass();

    /**
     * Adds the given {@code entity} to the database.
     *
     * @param entity
     *            To be persisted.
     * @return The added entity.
     */
    protected T add(final T entity) {
        return dbCommandExecutor.executeCommand(new DBCommand<T>() {

            @Override
            public T execute(final EntityManager em) {
                em.persist(entity);
                return entity;
            }
        });
    }

    /**
     * Updates the given {@code entity} in the database.
     *
     * @param entity
     *            To be updated.
     */
    protected void update(final T entity) {
        dbCommandExecutor.executeCommand(new DBCommand<Void>() {

            @Override
            public Void execute(final EntityManager em) {
                em.merge(entity);
                return null;
            }

        });
    }

    /**
     * @param orderField
     *            The optional field used to order the records in the database.
     * @return A {@link java.util.List} with all the records from the DB ordered by the given {@code orderField}, if specified.
     */
    protected List<T> findAll(final String orderField) {
        return dbCommandExecutor.executeCommand(new DBCommand<List<T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public List<T> execute(final EntityManager em) {
                final StringBuilder query = new StringBuilder(SELECT_FROM_CLAUSE);
                query.append(getPersistentClass().getSimpleName());
                query.append(" e");
                if (orderField != null && !orderField.isEmpty()) {
                    query.append(" Order by e.").append(orderField);
                }
                return em.createQuery(query.toString()).getResultList();
            }
        });
    }


    /**
     * Query for pmRopInfos.
     * @param flsQuery file lookup query
     * @return list of pmRopInfo
     */
    protected List<T> findByQuery(final FLSQuery flsQuery) {
        return dbCommandExecutor.executeCommand(new DBCommand<List<T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public List<T> execute(final EntityManager em) {
                return buildFindQuery(flsQuery).getResultList();
            }
        });
    }

    /**
     * Removes a record from the database using the given {@code id}.
     *
     * @param id
     *            ID used to find the record to be removed.
     * @return A boolean indicating whether the record was removed or not. If not, it means no record was found for the given {@code id}.
     */
    protected Boolean delete(final ID id) {
        return dbCommandExecutor.executeCommand(new DBCommand<Boolean>() {

            @Override
            public Boolean execute(final EntityManager em) {
                final T existingData = em.find(getPersistentClass(), id);
                if (existingData == null) {
                    return false;
                }
                em.remove(existingData);
                return true;
            }
        });
    }

    /**
     * Removes a record from the database using the given {@code id}.
     *
     * @param flsQueryList
     *            jpql/native query to be executed.
     * @return no of record(s) deleted.
     */
    protected int delete(final List<FLSQuery> flsQueryList) {
        return dbCommandExecutor.executeCommand(new DBCommand<Integer>() {
            @Override
            public Integer execute(final EntityManager em) {
                //TODO : going with jpql/native as CriteriaDelete is only supported in jpa 2.1. JBoss EAP 6.x support jpa 2.0
                final Query query = buildDeleteQuery(em, flsQueryList);
                final int noOfRecords = query.executeUpdate();
                logger.info("no of records deleted {}", noOfRecords);
                return noOfRecords;
            }
        });
    }

    /**
     * @return An int with the number of records in the database.
     */
    protected int countAll() {
        return dbCommandExecutor.executeCommand(new DBCommand<Integer>() {

            @Override
            public Integer execute(final EntityManager em) {
                return ((Long) em.createQuery("Select Count(e) From " + getPersistentClass().getSimpleName() + " e").getSingleResult()).intValue();
            }
        });
    }

    /**
     * Find records in database using the given pattern.
     *
     * @param columnName
     *          column Name which is defined in database.
     * @param pattern
     *          pattern used to find records in database.
     * @return list of pmRopInfo for given pattern
     */
    protected List<T> findPmRopInfoList(final String columnName, final String pattern) {
        return dbCommandExecutor.executeCommand(new DBCommand<List<T>>() {

            @SuppressWarnings("unchecked")
            @Override
            public List<T> execute(final EntityManager em) {
                return em.createQuery(SELECT_FROM_CLAUSE + getPersistentClass().getSimpleName() + WHERE_CLAUSE +columnName+ " LIKE "+ pattern+" ORDER BY "+PM_ROP_INFO_NODE_NAME).getResultList();
            }
        });
    }

    /**
     * Build dynamic delete query.
     *
     * @param flsQueryList
     *            properties needed to create delete query.
     * @return delete query.
     */
    protected Query buildDeleteQuery(final EntityManager em, final List<FLSQuery> flsQueryList) {
        final StringBuffer queryBuilder = new StringBuffer();
        if (flsQueryList != null && !flsQueryList.isEmpty()) {
            queryBuilder.append(" e where");
            for (final FLSQuery criteria : flsQueryList) {
                queryBuilder.append(" e.")
                            .append(criteria.getExpression())
                            .append(" AND");
            }
            queryBuilder.delete(queryBuilder.lastIndexOf(" AND"), queryBuilder.length());
        }
        final Query query = em.createQuery("Delete From " + getPersistentClass().getSimpleName() + queryBuilder);
        final StringBuilder queryValues = new StringBuilder();
        if (flsQueryList != null) {
            int ordinalParameter = ORDINAL_PARAMETER;
            for (final FLSQuery criteria : flsQueryList) {
                final Object value = criteria.getValues().get(0);
                query.setParameter(Character.toString((char)ordinalParameter++), value);
                queryValues.append(value).append("#");
            }
        }
        logger.info("Query for delete and values: {} #{}", queryBuilder, queryValues);
        return query;
    }

    /**
     * Build dynamic search query
     *
     * @param flsQuery
     *            the file lookup service database query
     * @return search query
     */
    protected Query buildFindQuery(final FLSQuery flsQuery) {
        return dbCommandExecutor.executeCommand(new DBCommand<Query>() {
            @Override
            public Query execute(final EntityManager em) {
                final String whereClause = flsQuery.getExpression();
                final Query query = em.createQuery(SELECT_FROM_CLAUSE + getPersistentClass().getSimpleName() + WHERE_CLAUSE + whereClause);
                for (int iterator = 1; iterator <= flsQuery.getValues().size(); iterator++) {
                    query.setParameter(iterator, flsQuery.getValues().get(iterator - 1));
                }
                query.setFirstResult(flsQuery.getOffset());
                query.setMaxResults(flsQuery.getLimit());
                return query;
            }
        });
    }

}