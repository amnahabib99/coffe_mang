package com.coffeeshop.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository contract for database access.
 *
 * @param <T> entity type
 */
public interface Repository<T> {
    /**
     * Saves an entity.
     *
     * @param entity entity
     * @return saved entity with id
     * @throws Exception when saving fails
     */
    T save(T entity) throws Exception;

    /**
     * Updates an entity.
     *
     * @param entity entity
     * @throws Exception when updating fails
     */
    void update(T entity) throws Exception;

    /**
     * Deletes an entity by id.
     *
     * @param id id
     * @throws Exception when deleting fails
     */
    void delete(int id) throws Exception;

    /**
     * Finds an entity by id.
     *
     * @param id id
     * @return optional entity
     * @throws Exception when loading fails
     */
    Optional<T> findById(int id) throws Exception;

    /**
     * Loads every entity of this repository type.
     *
     * @return all entities
     * @throws Exception when loading fails
     */
    List<T> findAll() throws Exception;
}
