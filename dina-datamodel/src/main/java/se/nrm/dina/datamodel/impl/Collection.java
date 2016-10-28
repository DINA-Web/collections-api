/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.datamodel.impl;
 
import com.fasterxml.jackson.annotation.JsonProperty;
import se.nrm.dina.datamodel.BaseEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;  
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;  

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collection.findAll", query = "SELECT c FROM Collection c"),
    @NamedQuery(name = "Collection.findByUserGroupScopeId", query = "SELECT c FROM Collection c WHERE c.userGroupScopeId = :userGroupScopeId"), 
    @NamedQuery(name = "Collection.findByCode", query = "SELECT c FROM Collection c WHERE c.code = :code"),
    @NamedQuery(name = "Collection.collectionid", query = "SELECT c FROM Collection c WHERE c.collectionId = :collectionId"),
    @NamedQuery(name = "Collection.collectionname", query = "SELECT c FROM Collection c WHERE c.collectionName = :collectionName"),
    @NamedQuery(name = "Collection.findByCollectionType", query = "SELECT c FROM Collection c WHERE c.collectionType = :collectionType"), 
    @NamedQuery(name = "Collection.findByGuid", query = "SELECT c FROM Collection c WHERE c.guid = :guid") })
public class Collection extends BaseEntity {
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserGroupScopeId")
    private Integer userGroupScopeId;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "CatalogFormatNumName")
    private String catalogFormatNumName;
    
    @Size(max = 50)
    @Column(name = "Code")
    private String code;
    
    @Column(name = "collectionId")
    private Integer collectionId;
    
    @Size(max = 50)
    @Column(name = "CollectionName")
    private String collectionName;
    
    @Size(max = 32)
    @Column(name = "CollectionType")
    private String collectionType;
    
    @Size(max = 32)
    @Column(name = "DbContentVersion")
    private String dbContentVersion;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 32)
    @Column(name = "DevelopmentStatus")
    private String developmentStatus;
    
    @Column(name = "EstimatedSize")
    private Integer estimatedSize;
    
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Size(max = 32)
    @Column(name = "InstitutionType")
    private String institutionType;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsEmbeddedCollectingEvent")
    private boolean isEmbeddedCollectingEvent;
    
    @Size(max = 24)
    @Column(name = "IsaNumber")
    private String isaNumber;
    
    @Size(max = 32)
    @Column(name = "KingdomCoverage")
    private String kingdomCoverage;
    
    @Size(max = 32)
    @Column(name = "PreservationMethodType")
    private String preservationMethodType;
    
    @Size(max = 32)
    @Column(name = "PrimaryFocus")
    private String primaryFocus;
    
    @Size(max = 32)
    @Column(name = "PrimaryPurpose")
    private String primaryPurpose;
    
    @Size(max = 24)
    @Column(name = "RegNumber")
    private String regNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Scope")
    private String scope;
    
    @Size(max = 255)
    @Column(name = "WebPortalURI")
    private String webPortalURI;
    
    @Size(max = 255)
    @Column(name = "WebSiteURI")
    private String webSiteURI;
    
    @JoinTable(name = "autonumsch_coll", joinColumns = {
        @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")}, inverseJoinColumns = {
        @JoinColumn(name = "AutoNumberingSchemeID", referencedColumnName = "AutoNumberingSchemeID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Autonumberingscheme> autonumberingschemeList;
    
    @OneToMany(mappedBy = "collectionID", fetch = FetchType.LAZY)
    private List<Sptasksemaphore> sptasksemaphoreList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID", fetch = FetchType.LAZY)
    private List<Preptype> preptypeList;
    
    @OneToMany(mappedBy = "leftSideCollectionID", fetch = FetchType.LAZY)
    private List<Collectionreltype> collectionreltypeList;
    
    @OneToMany(mappedBy = "rightSideCollectionID", fetch = FetchType.LAZY)
    private List<Collectionreltype> collectionreltypeList1;
    
    @OneToMany(mappedBy = "collectionCCID", fetch = FetchType.LAZY)
    private List<Agent> agentList;
    
    @OneToMany(mappedBy = "collectionTCID", fetch = FetchType.LAZY)
    private List<Agent> agentList1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID", fetch = FetchType.LAZY)
    private List<Collectionobject> collectionobjectList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID", fetch = FetchType.LAZY)
    private List<Fieldnotebook> fieldnotebookList;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Agent createdByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Discipline disciplineID;
    
    @JoinColumn(name = "InstitutionNetworkID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Institution institutionNetworkID;
    
    @OneToMany(mappedBy = "collectionID", fetch = FetchType.LAZY)
    private List<Spappresourcedir> spappresourcedirList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID", fetch = FetchType.LAZY)
    private List<Picklist> picklistList;

    public Collection() {
    }

    public Collection(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Collection(Integer userGroupScopeId, Date timestampCreated, String catalogFormatNumName, boolean isEmbeddedCollectingEvent) {
        this.userGroupScopeId = userGroupScopeId;
//        this.timestampCreated = timestampCreated;
        this.catalogFormatNumName = catalogFormatNumName;
        this.isEmbeddedCollectingEvent = isEmbeddedCollectingEvent;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(userGroupScopeId);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + userGroupScopeId;
//    }
  
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return userGroupScopeId;
    }

 
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @JsonProperty("user-group-scop-id")
    public Integer getUserGroupScopeId() {
        return userGroupScopeId;
    }

    public void setUserGroupScopeId(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    @JsonProperty("catalog-format-num-name")
    public String getCatalogFormatNumName() {
        return catalogFormatNumName;
    }

    public void setCatalogFormatNumName(String catalogFormatNumName) {
        this.catalogFormatNumName = catalogFormatNumName;
    }

    @JsonProperty("collection-id")
    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    @JsonProperty("collection-name")
    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @JsonProperty("collection-type")
    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    @JsonProperty("db-content-version")
    public String getDbContentVersion() {
        return dbContentVersion;
    }

    public void setDbContentVersion(String dbContentVersion) {
        this.dbContentVersion = dbContentVersion;
    }

    @JsonProperty("development-status")
    public String getDevelopmentStatus() {
        return developmentStatus;
    }

    public void setDevelopmentStatus(String developmentStatus) {
        this.developmentStatus = developmentStatus;
    }

    @JsonProperty("estimated-size")
    public Integer getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(Integer estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    @JsonProperty("institution-type")
    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    @JsonProperty("is-embedded-collectingevent")
    public boolean isIsEmbeddedCollectingEvent() {
        return isEmbeddedCollectingEvent;
    }

    public void setIsEmbeddedCollectingEvent(boolean isEmbeddedCollectingEvent) {
        this.isEmbeddedCollectingEvent = isEmbeddedCollectingEvent;
    }

    @JsonProperty("is-anumber")
    public String getIsaNumber() {
        return isaNumber;
    }

    public void setIsanumber(String isaNumber) {
        this.isaNumber = isaNumber;
    }

    @JsonProperty("kingdom-coverage")
    public String getKingdomCoverage() {
        return kingdomCoverage;
    }

    public void setKingdomCoverage(String kingdomCoverage) {
        this.kingdomCoverage = kingdomCoverage;
    }

    @JsonProperty("preservation-method-type")
    public String getPreservationMethodType() {
        return preservationMethodType;
    }

    public void setPreservationmethodtype(String preservationMethodType) {
        this.preservationMethodType = preservationMethodType;
    }

    @JsonProperty("primary-focus")
    public String getPrimaryFocus() {
        return primaryFocus;
    }

    public void setPrimaryfocus(String primaryFocus) {
        this.primaryFocus = primaryFocus;
    }

    @JsonProperty("primary-purpose")
    public String getPrimaryPurpose() {
        return primaryPurpose;
    }

    public void setPrimarypurpose(String primaryPurpose) {
        this.primaryPurpose = primaryPurpose;
    }
 
    @JsonProperty("reg-number")
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegnumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @JsonProperty("webportal-uri")
    public String getWebPortalURI() {
        return webPortalURI;
    }

    public void setWebportaluri(String webPortalURI) {
        this.webPortalURI = webPortalURI;
    }

    @JsonProperty("website-uri")
    public String getWebSiteURI() {
        return webSiteURI;
    }

    public void setWebsiteuri(String webSiteURI) {
        this.webSiteURI = webSiteURI;
    }

    

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

   
    @XmlTransient
    @JsonProperty("autonumberingschemes")
    public List<Autonumberingscheme> getAutonumberingschemeList() {
        return autonumberingschemeList;
    }

    public void setAutonumberingschemeList(List<Autonumberingscheme> autonumberingschemeList) {
        this.autonumberingschemeList = autonumberingschemeList;
    }

    @XmlTransient
    @JsonProperty("sptasksemaphores")
    public List<Sptasksemaphore> getSptasksemaphoreList() {
        return sptasksemaphoreList;
    }

    public void setSptasksemaphoreList(List<Sptasksemaphore> sptasksemaphoreList) {
        this.sptasksemaphoreList = sptasksemaphoreList;
    }

    @XmlTransient
    @JsonProperty("preptypes")
    public List<Preptype> getPreptypeList() {
        return preptypeList;
    }

    public void setPreptypeList(List<Preptype> preptypeList) {
        this.preptypeList = preptypeList;
    }

    @XmlTransient
    @JsonProperty("collectionreltypes")
    public List<Collectionreltype> getCollectionreltypeList() {
        return collectionreltypeList;
    }

    public void setCollectionreltypeList(List<Collectionreltype> collectionreltypeList) {
        this.collectionreltypeList = collectionreltypeList;
    }

    @XmlTransient
    @JsonProperty("collectionreltypes")
    public List<Collectionreltype> getCollectionreltypeList1() {
        return collectionreltypeList1;
    }

    public void setCollectionreltypeList1(List<Collectionreltype> collectionreltypeList1) {
        this.collectionreltypeList1 = collectionreltypeList1;
    }

    @XmlTransient
    @JsonProperty("agents")
    public List<Agent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    @XmlTransient
    @JsonProperty("agent1s")
    public List<Agent> getAgentList1() {
        return agentList1;
    }

    public void setAgentList1(List<Agent> agentList1) {
        this.agentList1 = agentList1;
    }

    @XmlTransient
    @JsonProperty("collectionobjects")
    public List<Collectionobject> getCollectionobjectList() {
        return collectionobjectList;
    }

    public void setCollectionobjectList(List<Collectionobject> collectionobjectList) {
        this.collectionobjectList = collectionobjectList;
    }

    @XmlTransient
    @JsonProperty("fieldnotebooks")
    public List<Fieldnotebook> getFieldnotebookList() {
        return fieldnotebookList;
    }

    public void setFieldnotebookList(List<Fieldnotebook> fieldnotebookList) {
        this.fieldnotebookList = fieldnotebookList;
    }

    @XmlIDREF
    @JsonProperty("modified-by-agent-id")
    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @XmlIDREF
    @JsonProperty("create-by-agent-id")
    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlIDREF
    @JsonProperty("discipline-id")
    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }

    @XmlIDREF
    @JsonProperty("institution-network-id")
    public Institution getInstitutionNetworkID() {
        return institutionNetworkID;
    }

    public void setInstitutionNetworkID(Institution institutionNetworkID) {
        this.institutionNetworkID = institutionNetworkID;
    }

    @XmlTransient
    @JsonProperty("spappresourcedirs")
    public List<Spappresourcedir> getSpappresourcedirList() {
        return spappresourcedirList;
    }

    public void setSpappresourcedirList(List<Spappresourcedir> spappresourcedirList) {
        this.spappresourcedirList = spappresourcedirList;
    }

    @XmlTransient
    @JsonProperty("picklists")
    public List<Picklist> getPicklistList() {
        return picklistList;
    }

    public void setPicklistList(List<Picklist> picklistList) {
        this.picklistList = picklistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userGroupScopeId != null ? userGroupScopeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collection)) {
            return false;
        }
        Collection other = (Collection) object;
        return !((this.userGroupScopeId == null && other.userGroupScopeId != null) || (this.userGroupScopeId != null && !this.userGroupScopeId.equals(other.userGroupScopeId)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Collection[ userGroupScopeId=" + userGroupScopeId + " ]";
    } 
}
