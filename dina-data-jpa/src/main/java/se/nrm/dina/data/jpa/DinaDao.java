/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.data.jpa;

import java.util.List;
import java.util.Map;   
import se.nrm.dina.datamodel.EntityBean;

/**
 *
 * @author idali
 * @param <T>{@link BaseEntity}
 */
public interface DinaDao<T extends EntityBean> {

    /**
     * Saves a transient or persistent {@link BaseEntity} to the database.
     *
     * @param entity the entity to save.
     * @return a persistent copy of the entity.
     */
    public T create(T entity);

    /**
     * Merges a transient {@link BaseEntity} to the database.
     *
     * @param entity the entity to merge.
     * @return a persistent copy of the entity.
     */
    public T merge(T entity);

    /**
     * Deletes a {@link BaseEntity} from the database. If the delete was
     * successful, the entity's ID will be null.
     *
     * @param entity the entity to delete.
     */
    public void delete(T entity);

    /**
     * Finds a {@link BaseEntity} by its database ID.
     *
     * @param id the database id of the entity we want.
     * @param clazz
     * @param isVersioned
     *
     * @return the instance of the entity from the database with the given id.
     */
    public T findById(int id, Class<T> clazz, boolean isVersioned);

    /**
     * Finds a {@link BaseEntity} by its database ID.
     *
     * Note: Specify has both database id in Integer and String. This method is
     * a workaround to find entity with String ID format
     *
     * @param id the database id of the entity we want.
     * @param clazz
     * @return the instance of the entity from the database with the given id.
     */
    public T findByStringId(String id, Class<T> clazz);

    /**
     * Finds an instance whose state may be lazily fetched
     *
     * @param id the database id of the entity we want.
     * @param clazz
     *
     * @return the instance of the entity from the database with the given id.
     */
    public T findByReference(int id, Class<T> clazz);

    /**
     * Finds all the instances of an entity in the database.
     *
     * @param clazz
     * @return a <code>List</code> of all the entities in the database.
     */
    public List<T> findAll(Class<T> clazz);
    
    public List<T> findAll(String entityName, String idField, List<Integer> ids);

    /**
     * Finds all the instances of an entity in the database by the search
     * criterion.
     *
     * @param clazz the entity class
     * @param entityName the name of the entity
     * @param limit the number of instances to return
     * @param conditions the search criterion 
     * @param isFuzzSearch  
     * @param offset  
     *
     * @return a <code>List</code> of all the entities in the database.
     */
    public List<T> findAll(Class<T> clazz, String entityName, int limit, Map<String, String> conditions, boolean isFuzzSearch, int offset);
 
    /**
     * A generic method to update an entity by query.
     *
     * @param jpql
     * @return
     */
    public boolean updateByJPQL(String jpql);

    /**
     * Find an instance by given jpql
     *
     * @param jpql
     * @return T
     */
    public T getEntityByJPQL(String jpql);
    
    
    /**
     * Finds the total number of instances of an entity
     *
     * @param strQuery the String of a named query
     * @return
     */
    public int getCountByQuery(String strQuery); 
}
