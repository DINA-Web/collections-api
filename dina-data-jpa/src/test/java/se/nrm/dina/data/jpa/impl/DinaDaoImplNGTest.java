/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.data.jpa.impl;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;   
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.BeforeClass; 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock; 
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner; 
import se.nrm.dina.data.jpa.DinaDao;
import se.nrm.dina.datamodel.impl.Testentity;

/**
 *
 * @author idali
 */
@RunWith(MockitoJUnitRunner.class) 
public class DinaDaoImplNGTest {
    
    @Mock
    static EntityManager entityManager;
    
    @Mock
    static QueryBuilder queryBuilder;
    
    @Mock
    static Query query;
    
    @Mock
    TypedQuery<String> tq;
    
    @Mock
    TypedQuery<Object[]> tqObj;
    
    @Mock
    TypedQuery<Integer> intTQ;
    
    private String strQuery;
    private static DinaDao dao;
    
    public DinaDaoImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        dao = new DinaDaoImpl(entityManager, query);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        dao = null;
    }
    
     
    @Test
    public void testDinaDaoImplConstractor() throws Exception {
        System.out.println("testDinaDaoImplConstractor");
        dao = new DinaDaoImpl();
        assertNotNull(dao);
    }
     
    @Test
    public void testDinaDaoImplConstractorWithEntityManager() throws Exception {
        System.out.println("testDinaDaoImplConstractorWithEntityManager");
        dao = new DinaDaoImpl(entityManager);
        assertNotNull(dao);
    }
    
    @Test
    public void testDinaDaoImplConstractorWithEntityManagerAndQuery() throws Exception {
        System.out.println("testDinaDaoImplConstractorWithEntityManagerAndQuery");
        dao = new DinaDaoImpl(entityManager, query);
        assertNotNull(dao);
    }
 
    @Test
    public void testFindAll_Class() throws Exception {
        System.out.println("findAll");
   
        Class clazz = Testentity.class; 
        List<Testentity> testEntities = new ArrayList<>();
        Testentity tetEntity = new Testentity(20);
        testEntities.add(tetEntity);
        
        when(entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll")).thenReturn(query); 
        when(query.getResultList()).thenReturn(testEntities);
        int expResult = 1;
        
        dao = new DinaDaoImpl(entityManager, query);
        List result = dao.findAll(clazz);
        verify(entityManager).createNamedQuery(clazz.getSimpleName() + ".findAll");
        verify(query).getResultList();
        assertEquals(expResult, result.size());  
        assertSame(result, testEntities);
    }
    
    
    public void testFindAllWithCondition() throws Exception {
        System.out.println("testFindAllWithCondition");
        
        List<Testentity> testEntities = new ArrayList<>();
        Testentity testEntity = new Testentity(20);
        testEntities.add(testEntity);
        
        testEntity = new Testentity(21);
        testEntities.add(testEntity);
        
        testEntity = new Testentity(22);
        testEntities.add(testEntity);
        
//        testEntity = new Testentity(23);
//        testEntities.add(testEntity);
//        
//        testEntity = new Testentity(24);
//        testEntities.add(testEntity);
//        
//        testEntity = new Testentity(25);
//        testEntities.add(testEntity);
//        
//        testEntity = new Testentity(26);
//        testEntities.add(testEntity);
                
        Map<String, String> conditions = new HashMap<>();
        conditions.put("key", "value");
        boolean isFuzzSearh = false;
        
        Class clazz = Testentity.class;
        int limit = 2;
        int offset = 2;
        
        when(entityManager.createQuery(strQuery)).thenReturn(query); 
        when(queryBuilder.createQuery(query, clazz, conditions, isFuzzSearh)).thenReturn(query);
        when(query.getResultList()).thenReturn(testEntities);
        int expResult = 2;
        
        dao = new DinaDaoImpl(entityManager, query);
        List<Testentity> result = dao.findAll(clazz, strQuery, limit, conditions, isFuzzSearh, offset);  
        
        verify(entityManager).createQuery(strQuery);
        verify(query).getResultList();
        verify(query).setFirstResult(offset);
        assertEquals(expResult, result.size());   
    }
    
    
    /**
     * Test of findAll method, of class DinaDaoImpl.
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAll_6args() throws Exception {
        System.out.println("findAll");
        
        Class clazz = Testentity.class;
  
        int limit = 20;
        Map<String, String> conditions = null;
        boolean isFuzzSearh = false;
        int offset = 0;
        
        List<Testentity> testEntities = new ArrayList<>();
        Testentity tetEntity = new Testentity(20);
        testEntities.add(tetEntity);
        
        when(entityManager.createQuery(strQuery)).thenReturn(query); 
        when(query.getResultList()).thenReturn(testEntities);
        int expResult = 1;
        
        dao = new DinaDaoImpl(entityManager, query);
        List<Testentity> result = dao.findAll(clazz, strQuery, limit, conditions, isFuzzSearh, offset);  
        
        verify(entityManager).createQuery(strQuery);
        verify(query).getResultList();
        assertEquals(expResult, result.size()); 
    }
    
    
    
    

//    /**
//     * Test of findAll method, of class DinaDaoImpl.
//     *
//     * @throws java.lang.Exception
//     */
////    @Test
//    public void testFindAllWithConditionsException() throws Exception {
//        System.out.println("findAll");
//
//        Class clazz = Testentity.class;
//        int limit = 20;
//        Map<String, String> conditions = new HashMap();
//
//        DinaException e = new DinaException("error");
//        when(entityManager.createQuery(strQuery)).thenReturn(query);
//        when(query.setMaxResults(limit)).thenReturn(query);
//        when(query.getResultList()).thenThrow(e);
//
//        try {
//            dao.findAll(clazz, strQuery, limit, conditions);            
//            fail("Expected a DinaException to be thrown");
//        } catch (DinaException ex) {            
//            assertEquals(ex.getMessage(), "error");
//        }
//    }


//
//    /**
//     * Test of findById method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testFindById() throws Exception {
//        System.out.println("findById");
//        int id = 0;
//        Class<T> clazz = null;
//        boolean isVersioned = false;
//        DinaDaoImpl instance = new DinaDaoImpl();
//        Object expResult = null;
//        Object result = instance.findById(id, clazz, isVersioned);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findByStringId method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testFindByStringId() throws Exception {
//        System.out.println("findByStringId");
//        String id = "";
//        Class<T> clazz = null;
//        DinaDaoImpl instance = new DinaDaoImpl();
//        Object expResult = null;
//        Object result = instance.findByStringId(id, clazz);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findByReference method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testFindByReference() throws Exception {
//        System.out.println("findByReference");
//        int id = 0;
//        Class<T> clazz = null;
//        DinaDaoImpl instance = new DinaDaoImpl();
//        Object expResult = null;
//        Object result = instance.findByReference(id, clazz);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of create method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testCreate() throws Exception {
//        System.out.println("create");
//        Object entity = null;
//        DinaDaoImpl instance = new DinaDaoImpl();
//        Object expResult = null;
//        Object result = instance.create(entity);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of merge method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testMerge() throws Exception {
//        System.out.println("merge");
//        Object entity = null;
//        DinaDaoImpl instance = new DinaDaoImpl();
//        Object expResult = null;
//        Object result = instance.merge(entity);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateByJPQL method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testUpdateByJPQL() throws Exception {
//        System.out.println("updateByJPQL");
//        String jpql = "";
//        DinaDaoImpl instance = new DinaDaoImpl();
//        boolean expResult = false;
//        boolean result = instance.updateByJPQL(jpql);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEntityByJPQL method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testGetEntityByJPQL() throws Exception {
//        System.out.println("getEntityByJPQL");
//        String jpql = "";
//        DinaDaoImpl instance = new DinaDaoImpl();
//        Object expResult = null;
//        Object result = instance.getEntityByJPQL(jpql);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCountByQuery method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testGetCountByQuery() throws Exception {
//        System.out.println("getCountByQuery");
//        String strQuery = "";
//        DinaDaoImpl instance = new DinaDaoImpl();
//        int expResult = 0;
//        int result = instance.getCountByQuery(strQuery);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testDelete() throws Exception {
//        System.out.println("delete");
//        Object entity = null;
//        DinaDaoImpl instance = new DinaDaoImpl();
//        instance.delete(entity);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRootCause method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testGetRootCause() throws Exception {
//        System.out.println("getRootCause");
//        Throwable throwable = null;
//        Throwable expResult = null;
//        Throwable result = DinaDaoImpl.getRootCause(throwable);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getThrowableList method, of class DinaDaoImpl.
//     */
//    @Test
//    public void testGetThrowableList() throws Exception {
//        System.out.println("getThrowableList");
//        Throwable throwable = null;
//        List expResult = null;
//        List result = DinaDaoImpl.getThrowableList(throwable);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}