/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.data.service.util;
     
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.HashMap;
import java.util.List;     
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors; 
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.lang.StringUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
 
/**
 *
 * @author idali
 */
public class Helpclass {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Helpclass instance = null;
    
    private final String MAINTENANCE_CONTACT = "admin@dina-system.org";
    private final List<String> SUPPORTED_LANGUAGES; 
    private final String LICENSES = "";
//    private final DateFormat FORMATTER = new SimpleDateFormat("MMM dd, yyyy 'at' HH.mm.ss");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH.mm.ss");
    
    Helpclass() {
        SUPPORTED_LANGUAGES = new ArrayList<>();
        SUPPORTED_LANGUAGES.add("GB_en");
        SUPPORTED_LANGUAGES.add("SE_sv");
    }
     
    public static synchronized Helpclass getInstance() {
        if (instance == null) {
            instance = new Helpclass();
        }
        return instance;
    }
     
    public Map<String, Object> buildMetaMapWithFilter(String entity, String url, String locale, int offset, 
                                                     int limit, String sort, String orderby, boolean isExact, 
                                                     Map<String, String> filterMap) {  
        return buildMetaMap(entity, url, locale, offset, limit, sort, orderby, buildFilter(filterMap, isExact)); 
    }
    
    public Map<String, Object> buildMetaMap(String entity, String url, String locale, int offset, 
                                            int limit, String sort, String orderby, String filters) {
         
        Map<String, Object> meta = buildBaseMetaMap(entity, buildUrl(offset, limit, sort, orderby, url, filters), locale); 
        meta.put("offest", offset);
        meta.put("sortOrder", sort);
        meta.put("limit", limit);
        meta.put("orderBy", Helpclass.getInstance().getOrderList(orderby)); 
        
        String previous = buildPreviousPaging(offset, limit, sort, orderby, url, filters);
        if(previous != null ) {
            meta.put("previous", previous);
        } 
        meta.put("next", buildNextPaging(offset, limit, sort, orderby, url, filters));  
        return meta;
    }
     
    public Map<String, Object> buildBaseMetaMap(String entity, String url, String locale) {   
        Map<String, Object> meta = new HashMap<>();
        meta.put("callEndpoint", url); 
        meta.put("apiVersion", getApiVersion(url, entity));
        meta.put("contentLicenses", LICENSES);
        meta.put("callDate", getStrTimeStamp());
        meta.put("maintenanceContact", MAINTENANCE_CONTACT);
        meta.put("supportedLanguages", SUPPORTED_LANGUAGES);
        meta.put("resultLanguages", locale); 
        return meta;
    }

    public String getMaintenanceContact() {
        return MAINTENANCE_CONTACT;
    }

    public List<String> getSupportedLanguages() {
        return SUPPORTED_LANGUAGES;
    }

    public String getLicenses() {
        return LICENSES;
    }
  
    public int getApiVersion(String url, String entityName) {
        String strVersion = StringUtils.substringBetween(url, "/v", "/" + entityName);
        try {
            return strVersion == null ? 1 : Integer.parseInt(strVersion);
        } catch(NumberFormatException e) {
            return 1;
        } 
    } 
        
    public String buildPreviousPaging(int offset, int limit, String sort, String orderby, String url, String filters) { 
        if(offset > 0) {
            if (offset >= limit) {
                int previousOffset = offset - limit;
                return buildUrl(previousOffset, limit, sort, orderby, url, filters); 
            }   
        }  
        return null;
    }
    
    public String buildNextPaging(int offset, int limit, String sort, String orderby, String url, String filters) { 
        int nextOffset = offset + limit;
        return buildUrl(nextOffset, limit, sort, orderby, url, filters);    
    }
     
    public String buildUrl(int theOffset, int limit, String sort, String orderby, String requestUrl, String filters) {
        StringBuilder sb = new StringBuilder();
        sb.append(requestUrl);
        sb.append("?offset=");
        sb.append(theOffset);
        sb.append("&limit=");
        sb.append(limit);
        sb.append("&sort=");
        sb.append(sort);
        if(filters != null) {
            sb.append("&");
            sb.append(filters);
        }
        if (orderby != null && !orderby.isEmpty()) {
            sb.append("&orderby=");
            sb.append(orderby);
        }

        return sb.toString();
    }

    private String buildFilter(Map<String, String> map, boolean isExact) {
        if (map == null || map.isEmpty()) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("&");
            map.entrySet().stream()
                    .forEach(e -> {
                        sb.append(e.getKey());
                        sb.append("=");
                        sb.append(e.getValue());
                    });
            if (isExact) {
                sb.append("&exact=");
                sb.append(isExact);
            }
            return sb.toString();
        }
    }
 
     
    private String getStrTimeStamp() {  
        return FORMATTER.format(LocalDateTime.now());
    }
      
    
    public List<String> getOrderList(String order) {
        return order == null ? new ArrayList() : Arrays.asList(StringUtils.split(order, ","));
    }
     
    public int getOffset(MultivaluedMap<String, String> map) {
        String offset = map.getFirst("offset");
        if(offset == null || !isNumric(offset)) {
            return 0;
        }  
        return Integer.parseInt(offset); 
    } 
    
    public int getLimit(MultivaluedMap<String, String> map) {
        String limit = map.getFirst("limit");
        if(limit == null || !isNumric(limit)) {
            return 50;
        }  
        return Integer.parseInt(limit); 
    } 
    
    public boolean getExactSearch(MultivaluedMap<String, String> map) {
        
        String strExact = map.getFirst("exact");
        if(strExact == null || strExact.isEmpty()) {
            return false;
        }  
        try {
            return Boolean.valueOf(strExact.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getSort(MultivaluedMap<String, String> map) {
        String sort = map.getFirst("sort");
        
        if(sort == null) {
            return "ASC";
        }

        if (sort.equalsIgnoreCase("asc") || sort.equalsIgnoreCase("desc")) {
            return sort.toUpperCase(); 
        } else {
            return "ASC";
        }
    }
      
    public Map<String, String> getSearchCondition(MultivaluedMap<String, String> map) {
        return map.entrySet()
                .stream()
                .filter(filterCondition())
                .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue().get(0)));
    }

    private boolean isNumric(String s) {
        return StringUtils.isNumeric(s);
    }

    
    private Predicate<Map.Entry<String, List<String>>> filterCondition() {
        return s -> !s.getKey().equals("offset")
                && !s.getKey().equals("limit") 
                && !s.getKey().equals("orderby")
                && !s.getKey().equals("exact")
                && !s.getKey().equals("sort");
    } 
      
}
