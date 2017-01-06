/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.data.service;
     
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;   
import java.util.List;    
import java.util.Map;  
import javax.ejb.EJB; 
import javax.ejb.Stateless;  
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;   
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE; 
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;  
import javax.ws.rs.core.Context;  
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;  
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import se.nrm.dina.data.exceptions.DinaException;   
import se.nrm.dina.data.service.util.Helpclass;     
import se.nrm.dina.logic.DinaDataLogic;  
 
/**
 *
 * @author idali
 */
@Path("/v1") 
//@Path("/") 
@Consumes({MediaType.APPLICATION_JSON+";charset=UTF-8"})
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
@Stateless
public class DinaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

    @EJB
    private DinaDataLogic logic;

    public DinaService() {

    }

    public DinaService(DinaDataLogic logic) {
        this.logic = logic;
    }

    /**
     * Get all the records from database by the name of the entity
     *
     * @param req - HttpServletRequeset
     * @param entity - Entity name 
     * @param info
     * @return Response
     */
    @GET
    @Path("{entity}")  
    public Response getAllByEntityName(@Context HttpServletRequest req, 
                                        @PathParam("entity") String entity, 
                                        @Context UriInfo info) {

        logger.info("getAllByEntityName : {} ", entity);
        
        MultivaluedMap<String, String> map = info.getQueryParameters();
  
        int offset = Helpclass.getInstance().getOffset(map); 
        int limit = Helpclass.getInstance().getLimit(map);
        String orderBy = map.getFirst("orderby");
        String sort = Helpclass.getInstance().getSort(map); 
   
        Map<String, Object> meta = Helpclass.getInstance().buildMetaMap(entity, req.getRequestURL().toString(), 
                                                        req.getLocale().toLanguageTag(), offset, limit, sort, orderBy, null); 
         
        JsonObject json = logic.findAll(entity, limit, offset, sort, Helpclass.getInstance().getOrderList(orderBy), meta); 
        int statusCode = json.getJsonObject("meta").getInt("statusCode");
        if(statusCode == 200) {
            return Response.ok(json).build(); 
        } else {
            return Response.status(statusCode).entity(json).build(); 
        } 
    }
 
    
    /**
     * Get all the records from database by the name of the entity and queries
     * @param req           - HttpServletRequest
     * @param entity        - the name of the entity
     * @param info          - QueryParameters
     * @return Response
     */
    @GET
    @Path("{entity}/search")
    public Response getData(@Context HttpServletRequest req, 
                            @PathParam("entity") String entity, 
                            @Context UriInfo info) {

        logger.info("getData : {} -- {}", entity, info); 
        
        MultivaluedMap<String, String> map = info.getQueryParameters();
  
        int offset = Helpclass.getInstance().getOffset(map); 
        int limit = Helpclass.getInstance().getLimit(map);
        String orderBy = map.getFirst("orderby");
        String sort = Helpclass.getInstance().getSort(map);
        boolean exact = Helpclass.getInstance().getExactSearch(map);
         
        List<String> order = new ArrayList<>();
        if (orderBy != null) {
            order = Arrays.asList(orderBy.split(","));
        }
        
        Map<String, String> condition = Helpclass.getInstance().getSearchCondition(map);  
        Map<String, Object> meta = Helpclass.getInstance().buildMetaMapWithFilter(entity, 
                                                    req.getRequestURL().toString(), req.getLocale().toLanguageTag(), 
                                                    offset, limit, sort, orderBy, exact, condition);
  
        JsonObject json = logic.findAllBySearchCriteria(entity, limit, offset, sort, order, condition, exact, meta);
        int statusCode = json.getJsonObject("meta").getInt("statusCode");
        if(statusCode == 200) {
            return Response.ok(json).build(); 
        } else {
            return Response.status(statusCode).entity(json).build(); 
        }  
    } 
    
        
        
    /**
     * Get single record frome database by the name of the entity and it's id
     * This method passes in a PathParam entity class name and entity id 
     *  
     * @param req
     * @param entity - class name of the entity
     * @param id - entity id
     * 
     * @return entity 
     */
    @GET
    @Path("{entity}/{id}/") 
    public Response getEntityById(@Context HttpServletRequest req,
                                  @PathParam("entity") String entity, 
                                  @PathParam("id") String id) { 
        logger.info("getEntityById - entity: {}, id :  {}", entity, id); 
             
        Map<String, Object> meta =  Helpclass.getInstance().buildBaseMetaMap(entity, 
                                                                    req.getRequestURL().toString(), 
                                                                    req.getLocale().toLanguageTag());
        
        JsonObject json = logic.findById(id, entity, meta);
        int statusCode = json.getJsonObject("meta").getInt("statusCode");
        if(statusCode == 200) {
            return Response.ok(json).build(); 
        } else {
            return Response.status(statusCode).entity(json).build(); 
        }  
    }
     
    /**
     * Get a list of records frome database by the name of the entity and a list of corresponding ids. 
     *
     * @param req       - HttpServletRequest
     * @param entity    - The name of the entity
     * @param ids       - List of ids separated by ','
     * 
     * @return Response 
     */
    @GET
    @Path("{entity}/search/{ids}/") 
    public Response getEntityByIds(@Context HttpServletRequest req,
                                    @PathParam("entity") String entity, 
                                    @PathParam("ids") String ids) { 
        logger.info("getEntityByIds - entity: {}, id :  {}", entity, ids); 
        Map<String, Object> meta = Helpclass.getInstance().buildBaseMetaMap(entity, 
                                                                            req.getRequestURL().toString(), 
                                                                            req.getLocale().toLanguageTag()); 
        JsonObject json = logic.findEntitiesByids(entity, ids, meta);
        int statusCode = json.getJsonObject("meta").getInt("statusCode");
        if(statusCode == 200) {
            return Response.ok(json).build(); 
        } else {
            return Response.status(statusCode).entity(json).build(); 
        }   
    }
 

    /**
     * Get total count of records in the database for a given entity by its name   
     * 
     * @param req       - HttpServletRequest
     * @param entity    - Class name of the entity 
     * 
     * @return Response 
     */
    @GET
    @Path("{entity}/count")  
    public Response getEntityCount(@Context HttpServletRequest req, @PathParam("entity") String entity ) { 
        logger.info("getEntityById - entity: {} ", entity );
  
        Map<String, Object> meta = Helpclass.getInstance().buildBaseMetaMap(entity, 
                                                                            req.getRequestURL().toString(), 
                                                                            req.getLocale().toLanguageTag());  
        JsonObject json = logic.findEntityCount(entity, meta);
        int statusCode = json.getJsonObject("meta").getInt("statusCode");
        if(statusCode == 200) {
            return Response.ok(json).build(); 
        } else {
            return Response.status(statusCode).entity(json).build(); 
        }  
    } 

    /**
     * Create an entity
     * 
     * @param req       - HttpServletRequest
     * @param entity    - The name of the entity
     * @param json      - Json String 
     * @return  Response
     *
     */
    @POST
    @Path("{entity}")
    @Consumes("application/json")
    public Response createNewEntity(@Context HttpServletRequest req,
                                    @PathParam("entity") String entity, String json) {
        logger.info("createNewEntity - entity: {}", json);
     
        Map<String, Object> meta = Helpclass.getInstance().buildBaseMetaMap(entity, 
                                                                            req.getRequestURL().toString(), 
                                                                            req.getLocale().toLanguageTag());  
        try {  
            int agentId = getAgentIdToken(req);   
      //      int agentId = 1;              // local test
            JsonObject jsonResult = logic.createEntity(entity, json, agentId, meta);  
            int statusCode = jsonResult.getJsonObject("meta").getInt("statusCode");
            if(statusCode == 201) {
                return Response.created(buildUri(jsonResult, req)).entity(jsonResult).build(); 
            } else {
                return Response.status(statusCode).entity(jsonResult).build(); 
            }  
        } catch (URISyntaxException ex) {
            return Response.status(500).build();  
        } catch (RuntimeException ex) {
            return Response.status(400).entity(ex.getMessage()).build();
        }  
    }
    
    private URI buildUri(JsonObject json, HttpServletRequest req) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        sb.append(req.getRequestURI());
        sb.append("/");
        sb.append(json.getJsonObject("data").getInt("id"));
        
        return new URI(sb.toString());
    }
     
         

    private int getAgentIdToken(HttpServletRequest req) {
        
        Principal userPrincipal = req.getUserPrincipal();  
        if (userPrincipal instanceof KeycloakPrincipal) { 
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) userPrincipal;
            Map<String, Object> map = kp.getKeycloakSecurityContext().getToken().getOtherClaims();
            
            try {
                String strId = (String)map.get("agentId");  
                return Integer.parseInt(strId); 
            } catch(NumberFormatException e) {
                throw e; 
            } 
        } else { 
//            return 1;
            throw new RuntimeException("Failed to get logged in user id"); 
        } 
    }
    
    

    /**
     * Update an entity
     * 
     * @param entity    - The name of the entity
     * @param json      - Json String
     *
     * @return Response
     *
     */
    @PUT
    @Path("{entity}") 
    public Response updateEntity(@PathParam("entity") String entity, String json) {
    
        logger.info("update entity: {} -- {}", entity, json);
        
         try {  
            return Response.ok(logic.updateEntity(entity, json)).build(); 
         } catch(DinaException e) { 
            return Response.status(e.getErrorCode()) 
                    .entity(e.getMessage()).build();
        }  
    }
      
    /**
     * Delete an record from database by the name of the entity and it's id
     * 
     * @param entity    - Class name of the entity 
     * @param id        - The id of the entity to be deleted
     * 
     * @return Response 
     */
    @DELETE
    @Path("{entity}/{id}")  
    public Response deleteEntityById(@PathParam("entity") String entity, @PathParam("id") int id) {
        
        logger.info("deleteEntityById - entity -- id: {} -- {}", entity, id );

        try {   
            logic.deleteEntity(entity, id);  
            return Response.ok().build();  
        } catch(DinaException e) { 
            logger.error("not ok");
            return Response.status(e.getErrorCode()) 
                    .entity(e.getMessage()).build();
        }  
    }  
}
