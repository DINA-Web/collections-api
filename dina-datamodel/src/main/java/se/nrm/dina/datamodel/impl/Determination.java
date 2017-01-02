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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
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
//import se.nrm.dina.datamodel.annotation.DinaField;
//import se.nrm.dina.datamodel.annotation.DinaIgnor;
//import se.nrm.dina.datamodel.annotation.DinaManyToOne;
//import se.nrm.dina.datamodel.annotation.DinaResource;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "determination")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Determination.findAll", query = "SELECT d FROM Determination d"),
    @NamedQuery(name = "Determination.findByDeterminationID", query = "SELECT d FROM Determination d WHERE d.determinationID = :determinationID"), 
    @NamedQuery(name = "Determination.findByCollectionMemberID", query = "SELECT d FROM Determination d WHERE d.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Determination.findByAddendum", query = "SELECT d FROM Determination d WHERE d.addendum = :addendum"),
    @NamedQuery(name = "Determination.findByAlternateName", query = "SELECT d FROM Determination d WHERE d.alternateName = :alternateName"), 
    @NamedQuery(name = "Determination.findByDeterminedDate", query = "SELECT d FROM Determination d WHERE d.determinedDate = :determinedDate"), 
    @NamedQuery(name = "Determination.findByIsCurrent", query = "SELECT d FROM Determination d WHERE d.isCurrent = :isCurrent"),
    @NamedQuery(name = "Determination.findByMethod", query = "SELECT d FROM Determination d WHERE d.method = :method"), 
    @NamedQuery(name = "Determination.findByQualifier", query = "SELECT d FROM Determination d WHERE d.qualifier = :qualifier"), 
    @NamedQuery(name = "Determination.findByTypeStatusName", query = "SELECT d FROM Determination d WHERE d.typeStatusName = :typeStatusName"), 
    @NamedQuery(name = "Determination.findByGuid", query = "SELECT d FROM Determination d WHERE d.guid = :guid")})
@DinaResource(type = "determination")
public class Determination extends BaseEntity {
  
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DeterminationID")
    @DinaField(name = "determination-id")
    @DinaId
    private Integer determinationID;
    
    @Basic(optional = false)
    @NotNull
    @Min(value = 1, message = "collectionMemberID can not be null")
    @Column(name = "CollectionMemberID")
    @DinaField(name = "collection-member")
    private int collectionMemberID;
 
    
    @Size(max = 16)
    @Column(name = "Addendum")
    @DinaIgnor
    private String addendum;
    
    @Size(max = 128)
    @Column(name = "AlternateName")
    @DinaIgnor
    private String alternateName;
    
    @Size(max = 50)
    @Column(name = "Confidence")
    @DinaField(name = "confidence")
    private String confidence;
    
    @Column(name = "DeterminedDate")
    @Temporal(TemporalType.DATE)
    @DinaField(name = "determined-date")
    private Date determinedDate;
    
    @Column(name = "DeterminedDatePrecision")
    @DinaField(name = "determined-date-precision")
    private Short determinedDatePrecision;
    
    @Size(max = 50)
    @Column(name = "FeatureOrBasis")
    @DinaIgnor
    private String featureOrBasis;
    
    @Basic(optional = false)
    @NotNull(message = "IsCurrent can not be null")
    @Column(name = "IsCurrent")
    @DinaField(name = "is-current")
    private boolean isCurrent;
    
    @Size(max = 50)
    @Column(name = "Method")
    @DinaField(name = "method")
    private String method;
    
    @Size(max = 64)
    @Column(name = "NameUsage")
    @DinaIgnor
    private String nameUsage;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    @DinaIgnor
    private Float number1;
    
    @Column(name = "Number2")
    @DinaIgnor
    private Float number2;
    
    @Size(max = 16)
    @Column(name = "Qualifier")
    @DinaIgnor
    private String qualifier;
     
    @Size(max = 16)
    @Column(name = "SubSpQualifier")
    @DinaIgnor
    private String subSpQualifier;
    
    @Size(max = 16)
    @Column(name = "VarQualifier")
    @DinaIgnor
    private String varQualifier;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    @DinaField(name = "remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    @DinaIgnor
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    @DinaIgnor
    private String text2;
    
    @Size(max = 50)
    @Column(name = "TypeStatusName")
    @DinaField(name = "type-status")
    private String typeStatusName;
    
    @Column(name = "YesNo1")
    @DinaIgnor
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    @DinaIgnor
    private Boolean yesNo2;
    
    @Size(max = 128)
    @Column(name = "GUID")
    @DinaField(name = "guid")
    private String guid;
    
    @JoinColumn(name = "TaxonID", referencedColumnName = "TaxonID")
    @ManyToOne
    @DinaManyToOne(name = "taxon", type = "taxon")
    private Taxon taxonID;
    
    @JoinColumn(name = "PreferredTaxonID", referencedColumnName = "TaxonID")
    @ManyToOne
    @DinaManyToOne(name = "preferred-taxon", type = "taxon")
    private Taxon preferredTaxonID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false, cascade= CascadeType.ALL,  fetch = FetchType.LAZY)
    @DinaManyToOne(name = "collection-object-id", type = "collectionobject")
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @JoinColumn(name = "DeterminerID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaManyToOne(name = "determiner", type = "agent")
    private Agent determinerID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "determinationID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Determinationcitation> determinationcitationList;

    public Determination() {
    }

    public Determination(Integer determinationID) {
        this.determinationID = determinationID;
    }

    public Determination(Integer determinationID, Date timestampCreated, int collectionMemberID, boolean isCurrent) {
        this.determinationID = determinationID;
        this.timestampCreated = timestampCreated;
        this.collectionMemberID = collectionMemberID;
        this.isCurrent = isCurrent;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(determinationID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + determinationID;
//    }
    
    @XmlTransient
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return determinationID;
    }

    @JsonProperty("determination-id")
    public Integer getDeterminationID() {
        return determinationID;
    }

    public void setDeterminationID(Integer determinationID) {
        this.determinationID = determinationID;
    }
 
    @JsonProperty("collection-member-id")
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }
 
    public String getAddendum() {
        return addendum;
    }

    public void setAddendum(String addendum) {
        this.addendum = addendum;
    }

    @JsonProperty("alternate-name")
    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    @JsonProperty("determined-date")
    public Date getDeterminedDate() {
        return determinedDate;
    }

    public void setDeterminedDate(Date determinedDate) {
        this.determinedDate = determinedDate;
    }

    @JsonProperty("determined-date-precision")
    public Short getDeterminedDatePrecision() {
        return determinedDatePrecision;
    }

    public void setDeterminedDatePrecision(Short determinedDatePrecision) {
        this.determinedDatePrecision = determinedDatePrecision;
    }

    @JsonProperty("feature-or-basis")
    public String getFeatureOrBasis() {
        return featureOrBasis;
    }

    public void setFeatureOrBasis(String featureOrBasis) {
        this.featureOrBasis = featureOrBasis;
    }

    @JsonProperty("is-current")
    public boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @JsonProperty("name-usage")
    public String getNameUsage() {
        return nameUsage;
    }

    public void setNameUsage(String nameUsage) {
        this.nameUsage = nameUsage;
    }

    public Float getNumber1() {
        return number1;
    }

    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    public Float getNumber2() {
        return number2;
    }

    public void setNumber2(Float number2) {
        this.number2 = number2;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }
 
    @JsonProperty("sub-sp-qualifier")
    public String getSubSpQualifier() {
        return subSpQualifier;
    }

    public void setSubSpQualifier(String subSpQualifier) {
        this.subSpQualifier = subSpQualifier;
    }

    @JsonProperty("var-qualifier")
    public String getVarQualifier() {
        return varQualifier;
    }

    public void setVarQualifier(String varQualifier) {
        this.varQualifier = varQualifier;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    @JsonProperty("type-status-name")
    public String getTypeStatusName() {
        return typeStatusName;
    }

    public void setTypeStatusName(String typeStatusName) {
        this.typeStatusName = typeStatusName;
    }

    @JsonProperty("yes-no1")
    public Boolean getYesNo1() {
        return yesNo1;
    }

    public void setYesNo1(Boolean yesNo1) {
        this.yesNo1 = yesNo1;
    }

    @JsonProperty("yes-no2")
    public Boolean getYesNo2() {
        return yesNo2;
    }

    public void setYesNo2(Boolean yesNo2) {
        this.yesNo2 = yesNo2;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @XmlIDREF
    @JsonProperty("taxon-id")
    public Taxon getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(Taxon taxonID) {
        this.taxonID = taxonID;
    }

    @XmlIDREF
    @JsonProperty("preferred-taxon-id")
    public Taxon getPreferredTaxonID() {
        return preferredTaxonID;
    }

    public void setPreferredTaxonID(Taxon preferredTaxonID) {
        this.preferredTaxonID = preferredTaxonID;
    }

    @XmlIDREF
    @JsonProperty("modified-by-agent-id")
    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }
 

    @XmlTransient
    @JsonProperty("collection-object-id")
    public Collectionobject getCollectionObjectID() {
        return collectionObjectID;
    }

    public void setCollectionObjectID(Collectionobject collectionObjectID) {
        this.collectionObjectID = collectionObjectID;
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
    @JsonProperty("determiner-id")
    public Agent getDeterminerID() {
        return determinerID;
    }

    public void setDeterminerID(Agent determinerID) {
        this.determinerID = determinerID;
    }

    @XmlTransient
    public List<Determinationcitation> getDeterminationcitationList() {
        return determinationcitationList;
    }

    public void setDeterminationcitationList(List<Determinationcitation> determinationcitationList) {
        this.determinationcitationList = determinationcitationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (determinationID != null ? determinationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Determination)) {
            return false;
        }
        Determination other = (Determination) object;
        return !((this.determinationID == null && other.determinationID != null) || (this.determinationID != null && !this.determinationID.equals(other.determinationID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Determination[ determinationID=" + determinationID + " ]";
    } 
}
