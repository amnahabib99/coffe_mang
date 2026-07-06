package com.coffeeshop.interfaces;

import java.util.List;

/**
 * Generic business operations for manageable entities.
 *
 * @param <T> entity type
 */
public interface Manageable<T> {
    /**
     * Adds a new entity.
     *
     * @param item entity
     * @throws Exception when adding fails
     */
    void add(T item) throws Exception;

    /**
     * Updates an existing entity.
     *
     * @param item entity
     * @throws Exception when updating fails
     */
    void update(T item) throws Exception;

    /**
     * Deletes an entity by id.
     *
     * @param id entity id
     * @throws Exception when deleting fails
     */
    void delete(int id) throws Exception;

    /**
     * @return all entities
     * @throws Exception when loading fails
     */
    List<T> getAll() throws Exception;
}
