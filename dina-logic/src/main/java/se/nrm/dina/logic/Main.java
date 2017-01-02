/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.logic;
 
import java.time.LocalDate;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


/**
 *
 * @author idali
 */
public class Main {
    
    public static void main(String ... args) {
        
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonBuilder = factory.createObjectBuilder();
        JsonObjectBuilder metaBuilder = Json.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder dataArrBuilder = Json.createArrayBuilder();
        JsonObjectBuilder attBuilder = Json.createObjectBuilder();
        JsonObjectBuilder relBuilder = Json.createObjectBuilder();
        
        metaBuilder.add("callEndpoint", "http://localhost:8080/discipline");
        metaBuilder.add("callDate", LocalDate.now().toString());
        metaBuilder.add("apiVersion", "v1");
        
        dataBuilder.add("id", 1);
        dataBuilder.add("type", "discipline");
        
        attBuilder.add("user-group-scope-id", 1);
        attBuilder.add("discipline-id", 1);
        attBuilder.add("name", "test discipline"); 
        
        dataBuilder.add("attributes", attBuilder);
        dataBuilder.add("relationships", relBuilder);
        dataArrBuilder.add(dataBuilder);

        jsonBuilder.add("meta", metaBuilder);
        jsonBuilder.add("data", dataArrBuilder);
        JsonObject json = jsonBuilder.build();
        
        
 
         
        System.out.println("result : " + json); 
        
         
    }
     

}

 