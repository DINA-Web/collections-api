/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.logic.util;

import java.sql.Date;
import java.time.LocalDateTime;  
import org.junit.After;
import org.junit.Before;
import static org.testng.Assert.*; 
import org.testng.annotations.Test;

/**
 *
 * @author idali
 */
public class HelpClassNGTest {
    
    private HelpClass instance;
    
    public HelpClassNGTest() {
    }

    @Before 
    public static void setUpClass() throws Exception {
    }

    @After 
    public static void tearDownClass() throws Exception {
    }

 

    /**
     * Test of getInstance method, of class HelpClass.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
 
        HelpClass result = HelpClass.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of getTodaysDate method, of class HelpClass.
     */
    @Test
    public void testGetTodaysDate() {
        System.out.println("getTodaysDate");
         
        instance = HelpClass.getInstance();
        Date result = instance.getTodaysDate();
        
        java.util.Date today = new java.util.Date();
        Date expResult = new java.sql.Date(today.getTime()); 
        assertEquals(expResult.getYear(), result.getYear()); 
        assertEquals(expResult.getMonth(), result.getMonth()); 
        assertEquals(expResult.getDay(), result.getDay()); 
        assertEquals(expResult.getDate(), result.getDate()); 
    }

    /**
     * Test of dateTimeToString method, of class HelpClass.
     */
    @Test
    public void testDateTimeToString() {
        System.out.println("dateTimeToString");
        LocalDateTime date = LocalDateTime.now(); 
         
        instance = HelpClass.getInstance();
        String result = instance.dateTimeToString(date);
        assertNotNull(result);
    }
    
    @Test
    public void testDateTimeToStringNull() {
        System.out.println("dateTimeToString");
        LocalDateTime date = null; 
         
        instance = HelpClass.getInstance();
        String result = instance.dateTimeToString(date);
        assertNull(result);
    }

 

    /**
     * Test of isNumric method, of class HelpClass.
     */
    @Test
    public void testIsNumric() {
        System.out.println("isNumric");
   
        String s = "20";
        instance = new HelpClass(); 
        boolean result = instance.isNumric(s);
        assertTrue(result);
    }
    
    @Test
    public void testIsNumricFalse() {
        System.out.println("isNumric");
   
        String s = "s";
        instance = new HelpClass(); 
        boolean result = instance.isNumric(s);
        assertFalse(result);
    }

 
 

  
    
 
    
        
 
  
}
