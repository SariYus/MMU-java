package com.company.hit.dao;

/**
 * hard disk memory interface
 *
 * @param <ID> : ID of entity in memory
 * @param <T>  : type of data object
 */
public interface IDao<ID extends java.io.Serializable, T> {

    /**
     * save a data object in disk
     *
     * @param entity : data object to save
     */
    void save(T entity);

    /**
     * delete a data object from disk
     *
     * @param entity : data object to delete
     */
    void delete(T entity);

    /**
     * search a data object in disk
     *
     * @param id : id of requested item
     * @return data object if found, and null otherwise.
     */
    T find(ID id);
}
