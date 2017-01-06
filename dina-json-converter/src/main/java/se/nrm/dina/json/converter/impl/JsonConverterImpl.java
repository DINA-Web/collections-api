/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.json.converter.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map; 
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import se.nrm.dina.json.converter.JsonConverter; 
import se.nrm.dina.json.converter.annotation.DinaField;
import se.nrm.dina.json.converter.annotation.DinaId;
import se.nrm.dina.json.converter.annotation.DinaIgnor;
import se.nrm.dina.json.converter.annotation.DinaManyToOne;
import se.nrm.dina.json.converter.annotation.DinaOneToMany;
import se.nrm.dina.json.converter.annotation.DinaResource;
import se.nrm.dina.json.converter.util.Util;

/**
 *
 * @author idali
 */ 
public class JsonConverterImpl implements JsonConverter, Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public JsonObject convert(Object object, Map<String, Object> map, int status) {

        if(object instanceof List) { 
            List<Object> beans = (List<Object>)object;
            return convertList(beans,  map,  status);
        } else { 
            return convertBean(object, map, status);
        } 
    }

    private JsonObject convertList(List<Object> beans, Map<String, Object> map, int status) {

        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonBuilder = factory.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder dataArrBuilder = Json.createArrayBuilder(); 

        if (!beans.isEmpty()) {
            String type = beans.get(0).getClass().getAnnotation(DinaResource.class).type();
            beans.stream()
                    .forEach(b -> { 
                        buildDataJson(b, type, dataBuilder);
                        dataArrBuilder.add(dataBuilder);
                    });
        }

        jsonBuilder.add("meta", buildMetadata(map, beans.size(), status));
        jsonBuilder.add("data", dataArrBuilder);
        return jsonBuilder.build();
    }

    private void buildDataJson(Object object, String type, JsonObjectBuilder dataBuilder) {
         
        JsonObjectBuilder attBuilder = Json.createObjectBuilder();
        JsonObjectBuilder relBuilder = Json.createObjectBuilder();
           
        dataBuilder.add("type", type);
        dataBuilder.add("id", getIdField(object));
        
        Field[] fields = object.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(DinaIgnor.class))
                .forEach(f -> {
                    Object fieldValue = getFieldValue(f, object);
                    if (fieldValue != null) {
                        if (f.isAnnotationPresent(DinaManyToOne.class)) {
                            buildManyToOneRelationship(f, fieldValue, relBuilder);
                        } else if (f.isAnnotationPresent(DinaOneToMany.class)) {
                            buildOneToManyRelationship(f, fieldValue, relBuilder);
                        } else {
                            String fieldName = f.getAnnotation(DinaField.class).name();
                            addAttributes(attBuilder, fieldValue, fieldName);
                        }
                    }
                });

        dataBuilder.add("attributes", attBuilder);
        dataBuilder.add("relationships", relBuilder); 
    }

    private JsonObject convertBean(Object object, Map<String, Object> map, int status) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonBuilder = factory.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder(); 

        String type = object.getClass().getAnnotation(DinaResource.class).type();
 
        buildDataJson(object, type, dataBuilder);
        jsonBuilder.add("meta", buildMetadata(map, 1, status));
        jsonBuilder.add("data", dataBuilder); 
        return jsonBuilder.build();
    }
  
    @Override
    public JsonObject convert(Map<String, Object> meta, int status) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonBuilder = factory.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder(); 
        
        jsonBuilder.add("meta", buildMetadata(meta, 0, status));
        jsonBuilder.add("data", dataBuilder);
        return jsonBuilder.build();  
    }
    
    @Override
    public JsonObject convert(int count, Map<String, Object> meta, String entityName) {
        
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonBuilder = factory.createObjectBuilder();
        JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
        dataBuilder.add("type", entityName);
        dataBuilder.add("count", count);
        
        jsonBuilder.add("meta", buildMetadata(meta, 1, 200)); 
        jsonBuilder.add("data", dataBuilder);
        return jsonBuilder.build(); 
    }
    
    
    // TODO:
    /*
    
    {
        "errors": [
          {
            "detail": "This username is already taken!",
            "source": {
              "pointer": "data/attributes/username"
            }
          }, {
            "detail": "Doesn't look like a valid email.",
            "source": {
              "pointer": "data/attributes/email"
            }
          }
        ]
   }
    
    
    
    {
        "errors": [
            {
                "detail": "Some generic non property error message",
                "source": {
                    "pointer": "data"
                }
            }
        ]
    }
    */
     
    @Override
    public JsonObject convert(Map<String, Object> meta, List<String> errorMsgs, 
                                int status, String errorType, String source) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder jsonBuilder = factory.createObjectBuilder();
        JsonObjectBuilder errorBuilder = Json.createObjectBuilder(); 
 
        jsonBuilder.add("meta", buildMetadata(meta, 0, status));
        
        if(source != null) {
            errorBuilder.add("type", source);
        }
        errorBuilder.add("errorType", errorType);
        errorBuilder.add("errorCode", status);
        
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        errorMsgs.stream()
                .forEach(l -> {
                    arrBuilder.add(l);
                });
        errorBuilder.add("detail", arrBuilder); 
        jsonBuilder.add("errors", errorBuilder);

        return jsonBuilder.build();
    }

    private void buildOneToManyRelationship(Field field, Object bean, JsonObjectBuilder relBuilder) {

        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        JsonObjectBuilder subDataBuilder = Json.createObjectBuilder();
        JsonArrayBuilder subDataArrBuilder = Json.createArrayBuilder();
 
        List<Object> subBeans = (List<Object>) bean;
        if (subBeans != null && !subBeans.isEmpty()) {
            String fieldName = field.getAnnotation(DinaOneToMany.class).name();
            String type = field.getAnnotation(DinaOneToMany.class).type(); 
             
            subBeans.stream()
                    .forEach(b -> {  
                        subDataBuilder.add("type", type);
                        subDataBuilder.add("id", getIdField(b));
                        subDataArrBuilder.add(subDataBuilder);
                    });
            subBuilder.add("data", subDataArrBuilder);
            relBuilder.add(fieldName, subBuilder);
        } 
    }

    private void buildManyToOneRelationship(Field field, Object bean, JsonObjectBuilder relBuilder) {
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        if (bean != null) {
            String fieldName = field.getAnnotation(DinaManyToOne.class).name();
            String type = field.getAnnotation(DinaManyToOne.class).type(); 
            JsonObjectBuilder subDataBuilder = Json.createObjectBuilder(); 
            subDataBuilder.add("type", type);
            subDataBuilder.add("id", getIdField(bean));
            subBuilder.add("data", subDataBuilder);
            relBuilder.add(fieldName, subBuilder);
        }  
    } 
    
    
    private void addAttributes(JsonObjectBuilder attBuilder, Object value, String key) {
        if (value instanceof Integer) {
            attBuilder.add(key, (int) value);
        } else if (value instanceof Short) {
            attBuilder.add(key, (Short) value);
        } else if (value instanceof Date) { 
            attBuilder.add(key, Util.getInstance().dateToString((Date) value));
        } else if (value instanceof java.util.Date) {
            attBuilder.add(key, Util.getInstance().dateToString((java.util.Date) value));
        } else if (value instanceof BigDecimal) {
            attBuilder.add(key, (BigDecimal) value);
        } else if (value instanceof Boolean) {
            attBuilder.add(key, (Boolean) value);
        } else if (value instanceof Double) {
            attBuilder.add(key, (Double) value);
        } else {
            attBuilder.add(key, (String) value);
        }
    }

    private int getIdField(Object bean) {
        try {
            Field field = Arrays.asList(bean.getClass().getDeclaredFields())
                    .stream()
                    .filter(f -> f.isAnnotationPresent(DinaId.class))
                    .findAny()
                    .get();
            
            field.setAccessible(true); 
            return (Integer) field.get(bean);
        } catch (IllegalArgumentException | IllegalAccessException | java.util.NoSuchElementException ex) {
            return 0;
        }
    }

    private JsonObjectBuilder buildMetadata(Map<String, Object> map, int numOfResult, int status) {
        JsonObjectBuilder metaBuilder = Json.createObjectBuilder();
        map.entrySet().stream() 
                .forEach(m -> {
                    Object value = m.getValue();
                    if (value instanceof java.util.List) {
                        List<String> list = (List) value;
                        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
                        list.stream()
                                .forEach(l -> {
                                    arrBuilder.add(l);
                                });  
                        metaBuilder.add(m.getKey(), arrBuilder);
                    } else if(value instanceof Integer) {
                        metaBuilder.add(m.getKey(), (int) value);
                    } else {
                        metaBuilder.add(m.getKey(), (String) value);
                    }
                });
        metaBuilder.add("statusCode", status);
        metaBuilder.add("resultCount", numOfResult);
        return metaBuilder;
    }
    
    private Object getFieldValue(Field field, Object bean) {
        try {
            field.setAccessible(true);
            return field.get(bean); 
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            logger.error(ex.getMessage());
            return null;
        } 
    } 
}
