/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.nrm.dina.data.jpa.impl;
  
import java.io.Serializable;    
import java.util.ArrayList;
import java.util.List; 
import java.util.Map;  
import java.util.Set;
import javax.ejb.Stateless;   
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;  
import javax.persistence.LockModeType;   
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException; 
import javax.persistence.PersistenceContext;
import javax.persistence.Query; 
import javax.validation.ConstraintViolation; 
import javax.validation.ConstraintViolationException; 
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;  
import se.nrm.dina.data.exceptions.DinaConstraintViolationException;
import se.nrm.dina.data.exceptions.DinaDatabaseException;
import se.nrm.dina.data.exceptions.DinaException;   
import se.nrm.dina.data.jpa.DinaDao; 
import se.nrm.dina.datamodel.EntityBean;
import se.nrm.dina.data.util.HelpClass;  

/**
 * CRUD operations to database
 *
 * @author idali
 * @param <T>
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DinaDaoImpl<T extends EntityBean> implements DinaDao<T>, Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PersistenceContext(unitName = "jpaPU")                  //  persistence unit connect to production database  
    private EntityManager entityManager;

    private Query query;

    public DinaDaoImpl() {

    }
    
    public DinaDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public DinaDaoImpl(EntityManager entityManager, Query query) {
        this.entityManager = entityManager;
        this.query = query; 
    } 

  
    @Override
    public List<T> findAll(Class<T> clazz) {
//        logger.info("findAll : {}", clazz);
        
        query = entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll"); 
        return query.getResultList(); 
    }
    
    @Override
    public List<T> findAll(Class<T> clazz, String jpql, int limit, Map<String, String> conditions, boolean isExact, int offset) {
        logger.info("findAll : {} -- {}", jpql, conditions);
         
        try {
            query = entityManager.createQuery(jpql);   
            if(conditions != null && !conditions.isEmpty()) {
                query =  QueryBuilder.getInstance().createQuery(query, clazz, conditions, isExact);
            } 
            if(offset > 0) {
                query.setFirstResult(offset);
            }
            query.setMaxResults(HelpClass.getInstance().maxLimit(limit));
            return query.getResultList();  
        } catch (Exception e) { 
            logger.error("e : {}", e.getMessage());
            throw new DinaException(400, getRootCauseName(e), e.getMessage());
        }
    }
    
    @Override
    public List<T> findAll(String entityName, String idFieldName, List<Integer> ids) {
         
        logger.info("findAll : {}", ids);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM ");
        sb.append(entityName);
        sb.append(" e WHERE e.");
        sb.append(idFieldName);
        sb.append(" in :ids");
        
//        String namedQuery = entityName + ".findAllByIDs";
//        query = entityManager.createNamedQuery(namedQuery);
        
        query = entityManager.createQuery(sb.toString()); 
        query.setParameter("ids", ids);
        return query.getResultList(); 
    }
     
  
    @Override
    public T findById(int id, Class<T> clazz, boolean isVersioned) {
        logger.info("findById - class : {} - id : {}", clazz, id);
 
        T tmp = null;
        try {
            if(isVersioned) {
                tmp = entityManager.find(clazz, id, LockModeType.OPTIMISTIC); 
            } else {
                tmp = entityManager.find(clazz, id, LockModeType.PESSIMISTIC_READ);
            }  
            entityManager.flush();
            return tmp; 
        } catch (OptimisticLockException ex) { 
            entityManager.refresh(tmp); 
            throw new DinaDatabaseException(400, "OptimisticLockException", ex.getMessage());
        } catch(Exception ex) { 
            throw new DinaDatabaseException(400, getRootCauseName(ex), ex.getMessage());
        }   
    }
 
    @Override
    public T findByStringId(String id, Class<T> clazz) {
        logger.info("findByStringId - class : {} - id : {}", clazz, id); 

        T tmp = null;
        try {
            tmp = entityManager.find(clazz, id, LockModeType.NONE);
            entityManager.flush();
        } catch (OptimisticLockException ex) { 
            entityManager.refresh(tmp);
            throw new DinaDatabaseException(400, "OptimisticLockException", ex.getMessage());
        } catch(Exception ex) {
            throw new DinaDatabaseException(400, getRootCauseName(ex), ex.getMessage());
        }  
        return tmp; 
    }


    @Override
    public T findByReference(int id, Class<T> clazz) {
        return entityManager.getReference(clazz, id);
    }
 
    @Override
    public T create(T entity) {
        logger.info("create(T) : {}", entity);

        T tmp = entity;
        try {
            entityManager.persist(entity);
            entityManager.flush(); 
        } catch (javax.persistence.PersistenceException ex) {  
            if (ex.getCause() instanceof  org.hibernate.exception.ConstraintViolationException) {  
                org.hibernate.exception.ConstraintViolationException e = (org.hibernate.exception.ConstraintViolationException) ex.getCause(); 
                throw new DinaConstraintViolationException(400, "ConstraintViolationException", handleHibernateConstraintViolation(e)); 
            }
        } catch(ConstraintViolationException e) {
            throw new DinaConstraintViolationException(400, "ConstraintViolationException", handleConstraintViolations(e)); 
        } catch (Exception e) {  
            throw new DinaDatabaseException(400, getRootCauseName(e), e.getMessage());
        }
        return tmp;
    }

    @Override
    public T merge(T entity) {

        logger.info("merge: {}", entity);

        T tmp = entity;
        try { 
            tmp = entityManager.merge(entity); 
            entityManager.flush();                              // this one used for throwing OptimisticLockException if method called with web service
        } catch (OptimisticLockException e) { 
            throw new DinaException(400, "OptimisticLockException", e.getMessage());
        } catch (ConstraintViolationException e) {  
            throw new DinaConstraintViolationException(400, "ConstraintViolationException", handleConstraintViolations(e)); 
        } catch (Exception e) {  
            logger.warn(e.getMessage());
        }  
        return tmp;
    }
    
    @Override
    public boolean updateByJPQL(String jpql ) {
//        logger.info("updateByJPQL : {} ", jpql );
        query = entityManager.createQuery(jpql);
 
        int updated = query.executeUpdate();
        return updated == 1;
    }
    
    @Override
    public T getEntityByJPQL(String jpql) {

//        logger.info("getEntityByJPQL - jpql: {}", jpql);
        query = entityManager.createQuery(jpql);
        try {
            return (T) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            throw new DinaDatabaseException(400, "NonUniqueResultException", ex.getMessage());
        } 
    }
 
    @Override
    public int getCountByQuery(String strQuery) {

        logger.info("getCountByQuery: {} ", strQuery);
        
        Number number;
        query = entityManager.createQuery(strQuery);
        
        try {
            number = (Number) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }  
        return number.intValue();
    }

    @Override
    public void delete(T entity) {
//        logger.info("delete - {}", entity);

        try {
            entityManager.remove(entity);
            entityManager.flush();                              // this is needed for throwing internal exception
        } catch (ConstraintViolationException e) {
            throw new DinaConstraintViolationException(400, "ConstraintViolationException", handleConstraintViolations(e));  
        } catch (Exception e) {
            throw new DinaException(400, getRootCauseName(e), e.getMessage());  
        }
    }

   
    
 

    
    private String getRootCauseName(final Throwable throwable) {
        return getRootCause(throwable).getClass().getSimpleName();
    }
    
    private Throwable getRootCause(final Throwable throwable) {
        final List<Throwable> list = getThrowableList(throwable);
        return list.size() < 2 ? null : (Throwable) list.get(list.size() - 1);
    }

    private List<Throwable> getThrowableList(Throwable throwable) {
        final List<Throwable> list = new ArrayList<>();
        while (throwable != null && list.contains(throwable) == false) {
            list.add(throwable);
            throwable = ExceptionUtils.getCause(throwable);
        }
        return list;
    }
    
    private String handleHibernateConstraintViolation(org.hibernate.exception.ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRootCause(e).getMessage());
        sb.append(" [");
        sb.append(getRootCause(e).getClass().getSimpleName());
        sb.append("]");
        return sb.toString();
    }
     
    private List<String> handleConstraintViolations(ConstraintViolationException e) { 
        logger.error("handleConstraintViolations"); 

        StringBuilder sb = new StringBuilder();
        List<String> msgs = new ArrayList<>();
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();

        if (cvs != null) {
            cvs.stream().forEach(cv -> {
                sb.append(cv.getMessage());
                sb.append(": ");
                sb.append(cv.getRootBeanClass().getSimpleName());
                sb.append(" [Constrian Key:");
                sb.append(cv.getPropertyPath().toString());
                sb.append(" Value: ");
                sb.append(cv.getInvalidValue() == null ? null : cv.getInvalidValue().toString());
                sb.append("]");
                msgs.add(sb.toString()); 
            });
        } 
        return msgs;
    }


    
//    /**
//     * Method handles ConstraintViolationException. It logs exception messages,
//     * entity properties with invalid values.
//     *
//     * @param e
//     * @return
//     */
//    private String handleConstraintViolation(ConstraintViolationException e) {
//        logger.error("handleConstraintViolation : {}", e.getMessage());
//        StringBuilder sb = new StringBuilder();
//
//        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
//        if (cvs != null) { 
//            cvs.stream().map((cv) -> {
//                logger.info("------------------------------------------------");
//                return cv;
//            }).map((cv) -> {
//                logger.info("Violation: {}", cv.getMessage());
//                return cv;
//            }).map((cv) -> {
//                sb.append("Violation:");
//                sb.append(cv.getMessage());
//                return cv;
//            }).map((cv) -> {
//                logger.info("Entity: {}", cv.getRootBeanClass().getSimpleName());
//                return cv;
//            }).map((cv) -> {
//                sb.append(" - Entity: ");
//                sb.append(cv.getRootBeanClass().getSimpleName());
//                return cv;
//            }).map((cv) -> {
//                if (cv.getLeafBean() != null && cv.getRootBean() != cv.getLeafBean()) {
//                    logger.info("Embeddable: {}", cv.getLeafBean().getClass().getSimpleName());
//                    sb.append(" - Embeddable: ");
//                    sb.append(cv.getLeafBean().getClass().getSimpleName());
//                }
//                return cv;
//            }).map((cv) -> {
//                logger.info("Attribute: {}", cv.getPropertyPath());
//                return cv;
//            }).map((cv) -> {
//                sb.append(" - Attribute: ");
//                sb.append(cv.getPropertyPath());
//                return cv;
//            }).map((cv) -> {
//                logger.info("Invalid value: {}", cv.getInvalidValue());
//                return cv;
//            }).forEach((cv) -> {
//                sb.append(" - Invalid value: ");
//                sb.append(cv.getInvalidValue());
//            });
//        }
//        return sb.toString();
//    }
}
