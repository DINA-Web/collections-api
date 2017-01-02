/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.json.converter;
 
import java.util.List; 
import java.util.Map;
import javax.json.JsonObject;
//import se.nrm.dina.data.vo.ErrorBean;
//import se.nrm.dina.datamodel.EntityBean;
//import se.nrm.dina.logic.vo.MetaBean; 

/**
 *
 * @author idali 
 */
public interface JsonConverter { 
    
    JsonObject convert(Object bean, Map<String, Object> map, int statusCode);
    
    JsonObject convert(Map<String, Object> meta, int statusCode);
     
    JsonObject convert(int count, Map<String, Object>  meta, String source);
    
    JsonObject convert(Map<String, Object> meta, List<String> errorMsgs, int statusCode, String errorType, String source); 
    
}
