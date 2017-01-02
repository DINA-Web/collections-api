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
import se.nrm.dina.json.converter.annotation.DinaField;
import se.nrm.dina.json.converter.annotation.DinaId;
import se.nrm.dina.json.converter.annotation.DinaIgnor;
import se.nrm.dina.json.converter.annotation.DinaManyToOne;
import se.nrm.dina.json.converter.annotation.DinaResource; 

/**
 *
 * @author idali
 */
@Entity
@Table(name = "discipline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discipline.findAll", query = "SELECT d FROM Discipline d"),
    @NamedQuery(name = "Discipline.findByUserGroupScopeId", query = "SELECT d FROM Discipline d WHERE d.userGroupScopeId = :userGroupScopeId"), 
    @NamedQuery(name = "Discipline.findByDisciplineId", query = "SELECT d FROM Discipline d WHERE d.disciplineId = :disciplineId"),
    @NamedQuery(name = "Discipline.findByName", query = "SELECT d FROM Discipline d WHERE d.name = :name"),
    @NamedQuery(name = "Discipline.findByRegNumber", query = "SELECT d FROM Discipline d WHERE d.regNumber = :regNumber"),
    @NamedQuery(name = "Discipline.findByType", query = "SELECT d FROM Discipline d WHERE d.type = :type"),
    @NamedQuery(name = "Discipline.findByIsPaleoContextEmbedded", query = "SELECT d FROM Discipline d WHERE d.isPaleoContextEmbedded = :isPaleoContextEmbedded"),
    @NamedQuery(name = "Discipline.findByPaleoContextChildTable", query = "SELECT d FROM Discipline d WHERE d.paleoContextChildTable = :paleoContextChildTable")})
@DinaResource(type = "discipline")
public class Discipline extends BaseEntity {
    
//    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserGroupScopeId") 
    @DinaField(name = "user-group-scope-id")
    @DinaId
    private Integer userGroupScopeId;
    
    @Column(name = "disciplineId")
    @DinaField(name = "discipline-id")
    private Integer disciplineId;
    
    @Size(max = 64)
    @Column(name = "Name")
    @DinaField(name = "name")
    private String name;
    
    @Size(max = 24)
    @Column(name = "RegNumber")
    @DinaIgnor
    private String regNumber;
    
    @Size(max = 64)
    @Column(name = "Type") 
    @DinaField(name = "type")
    private String type;
    
    @Column(name = "IsPaleoContextEmbedded")
    @DinaIgnor
    private Boolean isPaleoContextEmbedded;
    
    @Size(max = 50)
    @Column(name = "PaleoContextChildTable")
    @DinaIgnor
    private String paleoContextChildTable;
    
    @JoinTable(name = "autonumsch_dsp", joinColumns = {
        @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")}, inverseJoinColumns = {
        @JoinColumn(name = "AutoNumberingSchemeID", referencedColumnName = "AutoNumberingSchemeID")}) 
    @ManyToMany(fetch = FetchType.LAZY)  
    @DinaIgnor
    private List<Autonumberingscheme> autonumberingschemeList;
     
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Gift> giftList;
    
    @JoinColumn(name = "LithoStratTreeDefID", referencedColumnName = "LithoStratTreeDefID")
    @ManyToOne
    @DinaIgnor
    private Lithostrattreedef lithoStratTreeDefID;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId") 
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @DinaManyToOne(name = "division", type = "division")
    private Division divisionID;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", referencedColumnName = "GeologicTimePeriodTreeDefID")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @DinaIgnor
    private Geologictimeperiodtreedef geologicTimePeriodTreeDefID;
    
    @JoinColumn(name = "GeographyTreeDefID", referencedColumnName = "GeographyTreeDefID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @DinaIgnor
    private Geographytreedef geographyTreeDefID;
    
    @JoinColumn(name = "DataTypeID", referencedColumnName = "DataTypeID")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @DinaIgnor
    private Datatype dataTypeID;
    
    @JoinColumn(name = "TaxonTreeDefID", referencedColumnName = "TaxonTreeDefID")
    @ManyToOne
    @DinaIgnor
    private Taxontreedef taxonTreeDefID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor 
    private List<Sptasksemaphore> sptasksemaphoreList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collectingtrip> collectingtripList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Locality> localityList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Localitycitation> localitycitationList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Fieldnotebookpageset> fieldnotebookpagesetList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Paleocontext> paleocontextList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Loanreturnpreparation> loanreturnpreparationList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Localitynamealias> localitynamealiasList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collectingeventattribute> collectingeventattributeList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Attributedef> attributedefList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collectingevent> collectingeventList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Fieldnotebook> fieldnotebookList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Shipment> shipmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Splocalecontainer> splocalecontainerList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collection> collectionList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Spappresourcedir> spappresourcedirList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Spexportschema> spexportschemaList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Loan> loanList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Fieldnotebookpage> fieldnotebookpageList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Giftagent> giftagentList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Loanpreparation> loanpreparationList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Giftpreparation> giftpreparationList;
    
    @OneToMany(mappedBy = "disciplineID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Loanagent> loanagentList;

    public Discipline() {
    }

    public Discipline(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Discipline(Integer userGroupScopeId, Date timestampCreated) {
        this.userGroupScopeId = userGroupScopeId;
        this.timestampCreated = timestampCreated;
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
    public int getEntityId() {
        return userGroupScopeId;
    }
     
    @JsonProperty("user-group-scope-id")
    public Integer getUserGroupScopeId() {
        return userGroupScopeId;
    }

    public void setUserGroupScopeId(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }
  
    @JsonProperty("discipline-id")
    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    @JsonProperty("reg-number")
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
 
    @JsonProperty("is-paleo-context-embedded")
    public Boolean getIsPaleoContextEmbedded() {
        return isPaleoContextEmbedded;
    }

    public void setIsPaleoContextEmbedded(Boolean isPaleoContextEmbedded) {
        this.isPaleoContextEmbedded = isPaleoContextEmbedded;
    }
 
    @JsonProperty("paleo-context-child-table")
    public String getPaleoContextChildTable() {
        return paleoContextChildTable;
    }

    public void setPaleoContextChildTable(String paleoContextChildTable) {
        this.paleoContextChildTable = paleoContextChildTable;
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
    @JsonProperty("gifts")
    public List<Gift> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }

    @XmlIDREF 
    @JsonProperty("litho-strat-tree-def-id")
    public Lithostrattreedef getLithoStratTreeDefID() {
        return lithoStratTreeDefID;
    }

    public void setLithoStratTreeDefID(Lithostrattreedef lithoStratTreeDefID) {
        this.lithoStratTreeDefID = lithoStratTreeDefID;
    }

    @XmlIDREF 
    @JsonProperty("division-id")
    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
    }

    @XmlIDREF 
    @JsonProperty("geologic-time-period-tree-def-id")
    public Geologictimeperiodtreedef getGeologicTimePeriodTreeDefID() {
        return geologicTimePeriodTreeDefID;
    }

    public void setGeologicTimePeriodTreeDefID(Geologictimeperiodtreedef geologicTimePeriodTreeDefID) {
        this.geologicTimePeriodTreeDefID = geologicTimePeriodTreeDefID;
    }

    @XmlIDREF 
    @JsonProperty("geography-tree-def-id")
    public Geographytreedef getGeographyTreeDefID() {
        return geographyTreeDefID;
    }

    public void setGeographyTreeDefID(Geographytreedef geographyTreeDefID) {
        this.geographyTreeDefID = geographyTreeDefID;
    }

    @XmlIDREF 
    @JsonProperty("data-type-id")
    public Datatype getDataTypeID() {
        return dataTypeID;
    }

    public void setDataTypeID(Datatype dataTypeID) {
        this.dataTypeID = dataTypeID;
    }

    @XmlIDREF 
    @JsonProperty("taxon-tree-def-id")
    public Taxontreedef getTaxonTreeDefID() {
        return taxonTreeDefID;
    }

    public void setTaxonTreeDefID(Taxontreedef taxonTreeDefID) {
        this.taxonTreeDefID = taxonTreeDefID;
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
    @JsonProperty("created-by-agent-id")
    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
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
    @JsonProperty("collectingtrips")
    public List<Collectingtrip> getCollectingtripList() {
        return collectingtripList;
    }

    public void setCollectingtripList(List<Collectingtrip> collectingtripList) {
        this.collectingtripList = collectingtripList;
    }

    @XmlTransient
    @JsonProperty("localitys")
    public List<Locality> getLocalityList() {
        return localityList;
    }

    public void setLocalityList(List<Locality> localityList) {
        this.localityList = localityList;
    }

    @XmlTransient
    @JsonProperty("localitycitations")
    public List<Localitycitation> getLocalitycitationList() {
        return localitycitationList;
    }

    public void setLocalitycitationList(List<Localitycitation> localitycitationList) {
        this.localitycitationList = localitycitationList;
    }

    @XmlTransient
    @JsonProperty("fieldnotebookpagesets")
    public List<Fieldnotebookpageset> getFieldnotebookpagesetList() {
        return fieldnotebookpagesetList;
    }

    public void setFieldnotebookpagesetList(List<Fieldnotebookpageset> fieldnotebookpagesetList) {
        this.fieldnotebookpagesetList = fieldnotebookpagesetList;
    }

    @XmlTransient
    @JsonProperty("paleocontexts")
    public List<Paleocontext> getPaleocontextList() {
        return paleocontextList;
    }

    public void setPaleocontextList(List<Paleocontext> paleocontextList) {
        this.paleocontextList = paleocontextList;
    }

    @XmlTransient
    @JsonProperty("loanreturnpreparations")
    public List<Loanreturnpreparation> getLoanreturnpreparationList() {
        return loanreturnpreparationList;
    }

    public void setLoanreturnpreparationList(List<Loanreturnpreparation> loanreturnpreparationList) {
        this.loanreturnpreparationList = loanreturnpreparationList;
    }

    @XmlTransient
    @JsonProperty("localitynamealiases")
    public List<Localitynamealias> getLocalitynamealiasList() {
        return localitynamealiasList;
    }

    public void setLocalitynamealiasList(List<Localitynamealias> localitynamealiasList) {
        this.localitynamealiasList = localitynamealiasList;
    }

    @XmlTransient
    @JsonProperty("collectingeventattributes")
    public List<Collectingeventattribute> getCollectingeventattributeList() {
        return collectingeventattributeList;
    }

    public void setCollectingeventattributeList(List<Collectingeventattribute> collectingeventattributeList) {
        this.collectingeventattributeList = collectingeventattributeList;
    }

    @XmlTransient
    @JsonProperty("attributedefs")
    public List<Attributedef> getAttributedefList() {
        return attributedefList;
    }

    public void setAttributedefList(List<Attributedef> attributedefList) {
        this.attributedefList = attributedefList;
    }

    @XmlTransient
    @JsonProperty("collectingevents")
    public List<Collectingevent> getCollectingeventList() {
        return collectingeventList;
    }

    public void setCollectingeventList(List<Collectingevent> collectingeventList) {
        this.collectingeventList = collectingeventList;
    }

    @XmlTransient
    @JsonProperty("fieldnotebooks")
    public List<Fieldnotebook> getFieldnotebookList() {
        return fieldnotebookList;
    }

    public void setFieldnotebookList(List<Fieldnotebook> fieldnotebookList) {
        this.fieldnotebookList = fieldnotebookList;
    }

    @XmlTransient
    @JsonProperty("shipments")
    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
    }

    @XmlTransient
    @JsonProperty("splocalecontainers")
    public List<Splocalecontainer> getSplocalecontainerList() {
        return splocalecontainerList;
    }

    public void setSplocalecontainerList(List<Splocalecontainer> splocalecontainerList) {
        this.splocalecontainerList = splocalecontainerList;
    }

    @XmlTransient
    @JsonProperty("collections")
    public List<Collection> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Collection> collectionList) {
        this.collectionList = collectionList;
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
    @JsonProperty("spexportschemas")
    public List<Spexportschema> getSpexportschemaList() {
        return spexportschemaList;
    }

    public void setSpexportschemaList(List<Spexportschema> spexportschemaList) {
        this.spexportschemaList = spexportschemaList;
    }

    @XmlTransient
    @JsonProperty("loans")
    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    @XmlTransient
    @JsonProperty("fieldnotebookpages")
    public List<Fieldnotebookpage> getFieldnotebookpageList() {
        return fieldnotebookpageList;
    }

    public void setFieldnotebookpageList(List<Fieldnotebookpage> fieldnotebookpageList) {
        this.fieldnotebookpageList = fieldnotebookpageList;
    }

    @XmlTransient
    @JsonProperty("giftagents")
    public List<Giftagent> getGiftagentList() {
        return giftagentList;
    }

    public void setGiftagentList(List<Giftagent> giftagentList) {
        this.giftagentList = giftagentList;
    }

    @XmlTransient
    @JsonProperty("laonpreparations")
    public List<Loanpreparation> getLoanpreparationList() {
        return loanpreparationList;
    }

    public void setLoanpreparationList(List<Loanpreparation> loanpreparationList) {
        this.loanpreparationList = loanpreparationList;
    }

    @XmlTransient
    @JsonProperty("giftpreparations")
    public List<Giftpreparation> getGiftpreparationList() {
        return giftpreparationList;
    }

    public void setGiftpreparationList(List<Giftpreparation> giftpreparationList) {
        this.giftpreparationList = giftpreparationList;
    }

    @XmlTransient
    @JsonProperty("loanagents")
    public List<Loanagent> getLoanagentList() {
        return loanagentList;
    }

    public void setLoanagentList(List<Loanagent> loanagentList) {
        this.loanagentList = loanagentList;
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
        if (!(object instanceof Discipline)) {
            return false;
        }
        Discipline other = (Discipline) object;
        return !((this.userGroupScopeId == null && other.userGroupScopeId != null) || (this.userGroupScopeId != null && !this.userGroupScopeId.equals(other.userGroupScopeId)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Discipline[ userGroupScopeId=" + userGroupScopeId + " ]";
    } 
}
