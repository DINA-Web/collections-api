/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.logic;
  
import se.nrm.dina.data.util.JpaReflectionHelper;
import java.io.IOException;
import java.io.Serializable; 
import java.lang.reflect.Field;
import java.sql.Timestamp; 
import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.Arrays;  
import java.util.List;
import java.util.Map; 
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;  
import javax.json.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.dina.data.exceptions.DinaConstraintViolationException;
import se.nrm.dina.data.exceptions.DinaDatabaseException;
import se.nrm.dina.data.exceptions.DinaException; 
import se.nrm.dina.data.jpa.DinaDao;   
import se.nrm.dina.logic.util.NamedQueries;
import se.nrm.dina.datamodel.EntityBean;     
import se.nrm.dina.json.converter.JsonConverter;
import se.nrm.dina.json.converter.impl.JsonConverterImpl; 
import se.nrm.dina.logic.util.HelpClass;  

/**
 *
 * @author idali
 * @param <T>
 */
@Stateless
public class DinaDataLogic<T extends EntityBean> implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
      
    private java.util.Date date; 
    private EntityBean createdByUserBean;

    private ObjectMapper mapper;
    private EntityBean bean;
    
    private JsonConverter converter;
    
    @EJB
    private DinaDao dao;
    
//    @EJB
//    private JsonConverter converter;
    
   
    public DinaDataLogic() { 
        converter = new JsonConverterImpl();
        mapper = new ObjectMapper();
    }
    
    public DinaDataLogic(DinaDao dao) {
        this.dao = dao;
    }

    public DinaDataLogic(DinaDao dao, ObjectMapper mapper, EntityBean bean) {
        this.dao = dao;
        this.mapper = mapper;
        this.bean = bean;
    }
   
    /**
     * Finds all the instances of an entity
     * 
     * @param entityName 
     * @param limit
     * @param offset 
     * @param orderby
     * @param sort   
     * @param meta   
     * 
     * @return List
     */ 
    public JsonObject findAll(String entityName, int limit, int offset, String sort, List<String> orderby, Map<String, Object> meta) {     
        try { 
            Class clazz = JpaReflectionHelper.getInstance().convertClassNameToClass(entityName);
            
            String strQuery = NamedQueries.getInstance().createQueryFindAll(clazz, sort, orderby);
            List<EntityBean> beans = dao.findAll(clazz, strQuery, limit, null, false, offset);  
            return converter.convert(beans, meta, 200);
        } catch (DinaException e) {  
            return converter.convert(meta, e.getErrorMessageList(), e.getErrorCode(), e.getErrorType(), entityName);
        }
    }
     

    /**
     * Finds all the instances of an entity by query
     * @param entityName
     * @param limit 
     * @param offset
     * @param sort
     * @param orderby
     * @param condition 
     * @param isExact 
     * @param meta 
     * 
     * @return List
     */
    public JsonObject findAllBySearchCriteria(String entityName, int limit, int offset, String sort, 
                                           List<String> orderby, Map<String, String> condition, 
                                           boolean isExact, Map<String, Object> meta) {

        logger.info("findAllBySearchCriteria : {}", condition);
 
        try { 
            Class clazz = JpaReflectionHelper.getInstance().convertClassNameToClass(entityName); 

            String strQuery = NamedQueries.getInstance()
                    .createQueryFindAllWithSearchCriteria(clazz,  sort, orderby, isExact, condition);
    
            List<EntityBean> beans = dao.findAll(clazz, strQuery, limit, condition, isExact, offset);
            return converter.convert(beans, meta, 200); 
        } catch (DinaException e) {
            return converter.convert(meta, e.getErrorMessageList(), e.getErrorCode(), e.getErrorType(), entityName); 
        }
    }


    /**
     * Finds an entity by its database id
     *
     * @param id
     * @param entityName  
     * @param meta  
     * @return T
     */ 
    public JsonObject findById(String id, String entityName, Map<String, Object> meta) {
        logger.info("findById : {} -- {}", id, entityName);
   
        bean = null;
        try {
            Class clazz = JpaReflectionHelper.getInstance().convertClassNameToClass(entityName); 
            if(HelpClass.getInstance().isNumric(id)) { 
                bean = (T) dao.findById(Integer.parseInt(id), clazz, JpaReflectionHelper.getInstance().isVersioned(clazz));
            } else {
                bean = (T) dao.findByStringId(id, clazz); 
            }    
            return bean == null ? converter.convert(meta, 200) : converter.convert(bean, meta, 200);
        } catch (DinaException e) {
            return converter.convert(meta, e.getErrorMessageList(), e.getErrorCode(), e.getErrorType(), entityName); 
        } 
    }
    
    private List<Integer> buildIdList(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return null;
        }
        
        if(ids.contains("(")) {
            ids = StringUtils.substringBetween(ids, "(", ")");
        }  
        try {
            List<Integer> intIds = Arrays.asList(ids.split(","))
                                .stream()
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());
            return intIds;
        } catch(NumberFormatException e) {
            throw new DinaException(400, "NumberFormatException", e.getMessage());
        } 
    }

    public JsonObject findEntitiesByids(String entityName, String ids, Map<String, Object> meta) {
        logger.info("findEntitiesByids : {} -- {}", entityName, ids);
         
        entityName = JpaReflectionHelper.getInstance().reformClassName(entityName);
        
        try { 
            List<Integer> intIds = buildIdList(ids); 
            String idFieldName = JpaReflectionHelper.getInstance().getIDfieldName(entityName); 
            List<EntityBean> beans = dao.findAll(entityName, idFieldName, intIds);
            return converter.convert(beans, meta, 200); 
        } catch (DinaException e) {
            return converter.convert(meta, e.getErrorMessageList(), e.getErrorCode(), e.getErrorType(), entityName);
        } 
    }

    /**
     * Finds the total number of an entity in database
     *
     * @param entityName
     * @param meta
     * @return JsonObject
     */
    public JsonObject findEntityCount(String entityName, Map<String, Object> meta) {

        try {
            int count = dao.getCountByQuery(NamedQueries.getInstance()
                                .createFindTotalCountNamedQuery(
                                            JpaReflectionHelper.getInstance().convertClassNameToClass(entityName)
                                                .getSimpleName()));
            
            return converter.convert(count, meta, entityName); 
        } catch (DinaException e) { 
            return converter.convert(meta, e.getErrorMessageList(), e.getErrorCode(), e.getErrorType(), entityName); 
        }
    }

    /**
     * Creates an entity in database
     *
     * @param entityName
     * @param json  
     * @param agentId  
     * @param meta  
     * @return EntityBean
     */
//    public EntityBean createEntity(String entityName, String json, int agentId) { 
    public JsonObject createEntity(String entityName, String json, int agentId, Map<String, Object> meta) { 
        logger.info("createEntity : {} ", entityName);
 
        LocalDateTime ld = LocalDateTime.now();
        date = Timestamp.valueOf(ld);
 
        try {
            bean = mappObject(entityName, json);  
            Class clazz = JpaReflectionHelper.getInstance().getCreatedByClazz();
            createdByUserBean = dao.findById(agentId, clazz, JpaReflectionHelper.getInstance().isVersioned(clazz));
  
            if(createdByUserBean == null) {
                List<String> errors = new ArrayList<>();
                errors.add("Agent is not registered in database");
                return converter.convert(meta, errors, 400, "Missing agent", entityName);
            }
            Field[] fields = bean.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .forEach(f -> {
                        setValueToBean(bean, f);
                    });

            setTimeStampCreated(bean);
            setCreatedByUser(bean, createdByUserBean); 
            return converter.convert(dao.create(bean), meta, 200); 
         } catch (DinaConstraintViolationException ex) {
            return converter.convert(meta, ex.getErrorMessageList(), ex.getErrorCode(), ex.getErrorType(), entityName);
        } catch (DinaDatabaseException ex) {
            return converter.convert(meta, ex.getErrorMessageList(), ex.getErrorCode(), ex.getErrorType(), entityName);
        } catch (DinaException ex) {
            return converter.convert(meta, ex.getErrorMessageList(), ex.getErrorCode(), ex.getErrorType(), entityName);
        }
    }


    /**
     * Updates an entity in database
     *
     * @param entityName
     * @param json
     * @return EntityBean
     */
    public EntityBean updateEntity(String entityName, String json) {
        logger.info("updateEntity : {} -- {}", entityName, json);

        try {
            bean = mappObject(entityName, json);
            return dao.merge(bean);
        } catch (DinaException ex) {
            throw ex;
        }
    }

    private EntityBean mappObject(String entityName, String json) {
        logger.info("mappObject : {} -- {}", entityName, json);
 
        bean = null;
        try {
            bean = (EntityBean) mapper.readValue(json, JpaReflectionHelper.getInstance().convertClassNameToClass(entityName));
        } catch (IOException ex) {  
            if(ex.getCause() != null) {
                throw new DinaException(400, ex.getCause().getClass().getSimpleName(), ex.getMessage());
            } else {
                throw new DinaException(400, null, ex.getMessage());
            }
            
        }
        return bean;
    }

    /**
     * Deletes an entity in database
     *
     * @param entityName
     * @param id
     */
    public void deleteEntity(String entityName, int id) {

        logger.info("deleteEntity : {} -- {}", entityName, id);

        try {
            bean = dao.findByReference(id, JpaReflectionHelper.getInstance().convertClassNameToClass(entityName));

            if (bean != null) {
                dao.delete(bean);
            }
        } catch (DinaException e) { 
            throw e;
        }
    }

    private void setChildToBean(EntityBean parent, Field f) { 
        try {
            f.setAccessible(true);
            EntityBean child = (EntityBean) f.get(parent); 
            if (child != null) {
                Field field = JpaReflectionHelper.getInstance().getIDField(child);

                field.setAccessible(true);
                if (field.get(child) != null && (Integer) field.get(child) > 0) { 
                    Class clazz = child.getClass(); 
                    EntityBean entity = dao.findById((Integer) field.get(child), clazz, JpaReflectionHelper.getInstance().isVersioned(clazz)); 
                    if (entity == null) {
                        setTimeStampCreated(child);
                        setCreatedByUser(child, createdByUserBean);
                        f.set(parent, child);
                        Field[] fields = child.getClass().getDeclaredFields();
                        Arrays.stream(fields)
                                .forEach(fd -> {
                                    setValueToBean(child, fd);
                                });
                        setParentToChild(fields, child, parent);
                    } else {
                        f.set(parent, entity);
                    } 
                } else {
                    setTimeStampCreated(child);
                    setCreatedByUser(child, createdByUserBean);
                    f.set(parent, child);
                    Field[] fields = child.getClass().getDeclaredFields();
                    Arrays.stream(fields)
                            .forEach(fd -> {
                                setValueToBean(child, fd);
                            });
                    setParentToChild(fields, child, parent);
                }  
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new DinaException(400, ex.getCause().getClass().getSimpleName(), ex.getMessage());
        }
    }

    private void setChildrenToBean(EntityBean parent, Field field) { 
        try {
            field.setAccessible(true);
            List<EntityBean> children = (List) field.get(parent); 
            Field[] fields;
            if (children != null && !children.isEmpty()) {
                for (EntityBean child : children) { 
                    setTimeStampCreated(child);
                    setCreatedByUser(child, createdByUserBean);
                    fields = child.getClass().getDeclaredFields(); 
                    setParentToChild(fields, child, parent);
                }
                field.set(parent, children);

            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new DinaException(400, ex.getCause().getClass().getSimpleName(), ex.getMessage());
        }
    }

    private void setParentToChild(Field[] fields, EntityBean child, EntityBean parent) {
        Arrays.asList(fields).stream()
                .forEach(f -> {
                    if (JpaReflectionHelper.getInstance().isEntity(child.getClass(), f.getName())) {
                        try {
                            setChildToBean(child, f);
                            f.setAccessible(true);
                            if (f.getName().toLowerCase().contains(parent.getClass().getSimpleName().toLowerCase())) {
                                f.set(child, parent);
                            }
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            throw new DinaException(400, ex.getCause().getClass().getSimpleName(), ex.getMessage());
                        }
                    }
                });
    }

    private void setValueToBean(EntityBean parent, Field f) {
        try {
            if (JpaReflectionHelper.getInstance().isEntity(parent.getClass(), f.getName())) {
                setChildToBean(parent, f);
            } else if (JpaReflectionHelper.getInstance().isCollection(parent.getClass(), f.getName())) {
                setChildrenToBean(parent, f);
            }
        } catch (DinaException e) {
            throw e;
        }
    }

    private void setCreatedByUser(EntityBean bean, EntityBean userBean) {
        Field field = JpaReflectionHelper.getInstance().getCreatedByField(bean.getClass());

        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(bean, userBean);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                throw new DinaException(400, ex.getCause().getClass().getSimpleName(), ex.getMessage()); 
            }
        } 
    }
     
    
    private void setTimeStampCreated(EntityBean bean) {
        
 //       logger.info("setTimeStampCreated bean : {}", bean);
        Field field = JpaReflectionHelper.getInstance().getTimestampCreated(bean.getClass());
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(bean, date);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                throw new DinaException(400, ex.getCause().getClass().getSimpleName(), ex.getMessage()); 
            }
        }
    }  
}
