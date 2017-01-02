/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.datamodel.impl;
 
import com.fasterxml.jackson.annotation.JsonProperty;
import se.nrm.dina.datamodel.BaseEntity;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size; 
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;    
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import se.nrm.dina.json.converter.annotation.DinaField;
import se.nrm.dina.json.converter.annotation.DinaId;
import se.nrm.dina.json.converter.annotation.DinaIgnor;
import se.nrm.dina.json.converter.annotation.DinaManyToOne;
import se.nrm.dina.json.converter.annotation.DinaOneToMany;
import se.nrm.dina.json.converter.annotation.DinaResource; 

/**
 *
 * @author idali
 */
@Entity
@Table(name = "accession")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accession.findAll", query = "SELECT a FROM Accession a"),
    @NamedQuery(name = "Accession.findByAccessionID", query = "SELECT a FROM Accession a WHERE a.accessionID = :accessionID"),   
    @NamedQuery(name = "Accession.findByAccessionNumber", query = "SELECT a FROM Accession a WHERE a.accessionNumber = :accessionNumber"),
    @NamedQuery(name = "Accession.findByDateAccessioned", query = "SELECT a FROM Accession a WHERE a.dateAccessioned = :dateAccessioned"),
    @NamedQuery(name = "Accession.findByDateAcknowledged", query = "SELECT a FROM Accession a WHERE a.dateAcknowledged = :dateAcknowledged"),
    @NamedQuery(name = "Accession.findByDateReceived", query = "SELECT a FROM Accession a WHERE a.dateReceived = :dateReceived"), 
    @NamedQuery(name = "Accession.findByType", query = "SELECT a FROM Accession a WHERE a.type = :type") })
//@JsonIgnoreProperties(ignoreUnknown = true)  
@DinaResource(type = "accession")
public class Accession extends BaseEntity {
     
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AccessionID")
    @DinaField(name = "accession-id")
    @DinaId
    private Integer accessionID;
     
    @Size(max = 255)
    @Column(name = "AccessionCondition")
    @DinaIgnor
    private String accessionCondition;
    
    @Basic(optional = false)
    @NotNull(message="AccessNumber must be specified.")
    @Size(min = 1, max = 60)
    @Column(name = "AccessionNumber")
    @DinaField(name = "accession-number")
    private String accessionNumber;
    
    @Column(name = "DateAccessioned")
    @Temporal(TemporalType.DATE)
    @DinaField(name = "date-accessioned")
    private Date dateAccessioned;
    
    @Column(name = "DateAcknowledged")
    @Temporal(TemporalType.DATE)
    @DinaField(name = "date-acknowledged")
    private Date dateAcknowledged;
    
    @Column(name = "DateReceived")
    @Temporal(TemporalType.DATE)
    @DinaField(name = "date-received")
    private Date dateReceived;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    @DinaIgnor
    private Float number1;
    
    @Column(name = "Number2")
    @DinaIgnor
    private Float number2;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")  
    @DinaField(name = "remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Status")
    @DinaField(name = "status")
    private String status;
    
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
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text3")
    @DinaIgnor
    private String text3;
    
    @Column(name = "TotalValue")
    @DinaIgnor
    private BigDecimal totalValue;
    
    @Size(max = 32)
    @Column(name = "Type")
    @DinaField(name = "type")
    private String type;
    
    @Size(max = 50)
    @Column(name = "VerbatimDate")
    @DinaIgnor
    private String verbatimDate;
    
    @Column(name = "YesNo1")
    @DinaIgnor
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    @DinaIgnor
    private Boolean yesNo2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessionID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Accessionattachment> accessionattachmentList;
    
    @OneToMany(mappedBy = "accessionID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Treatmentevent> treatmenteventList;
    
    @OneToMany(mappedBy = "accessionID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collectionobject> collectionobjectList;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "accessionID", cascade = CascadeType.ALL  )
    @DinaOneToMany(name = "accession-agents", type = "accessionAgent")
    private List<Accessionagent> accessionagentList;
    
    @JoinColumn(name = "RepositoryAgreementID", referencedColumnName = "RepositoryAgreementID")
    @ManyToOne( fetch = FetchType.LAZY)
    @DinaIgnor
    private Repositoryagreement repositoryAgreementID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor 
    private Agent createdByAgentID;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false,  fetch = FetchType.LAZY)
    @DinaManyToOne(name = "division", type = "division")
    private Division divisionID;
    
    @JoinColumn(name = "AddressOfRecordID", referencedColumnName = "AddressOfRecordID")
    @ManyToOne( fetch = FetchType.LAZY)
    @DinaIgnor
    private Addressofrecord addressOfRecordID;
    
    @OneToMany(mappedBy = "accessionID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Appraisal> appraisalList;
    
    @OneToMany(mappedBy = "accessionID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Accessionauthorization> accessionauthorizationList;
    
    @OneToMany(mappedBy = "accessionID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Deaccession> deaccessionList;

    public Accession() { 
    }

    public Accession(Integer accessionID) { 
        this.accessionID = accessionID;
    }

    public Accession(Integer accessionID, Date timestampCreated, String accessionNumber) {
        this.accessionID = accessionID;
//        this.timestampCreated = timestampCreated;
        this.accessionNumber = accessionNumber;
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(accessionID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + accessionID;
//    }
    
    @XmlTransient 
    @Override 
    public int getEntityId() {
        return accessionID == null ? 0 : accessionID;
    }
  
    @JsonProperty("accession-id")
    public Integer getAccessionID() {
        return accessionID;
    }

    public void setAccessionID(Integer accessionID) {
        this.accessionID = accessionID;
    }
 
    @JsonProperty("accession-condition")
    public String getAccessionCondition() {
        return accessionCondition;
    }

    public void setAccessionCondition(String accessionCondition) {
        this.accessionCondition = accessionCondition;
    }

    @JsonProperty("accession-number")
    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    @JsonProperty("date-accessioned")
    public Date getDateAccessioned() {
        return dateAccessioned;
    }

    public void setDateAccessioned(Date dateAccessioned) {
        this.dateAccessioned = dateAccessioned;
    }

    @JsonProperty("date-acknowledged")
    public Date getDateAcknowledged() {
        return dateAcknowledged;
    }

    public void setDateAcknowledged(Date dateAcknowledged) {
        this.dateAcknowledged = dateAcknowledged;
    }

    @JsonProperty("date-received")
    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    @JsonProperty("number1")
    public Float getNumber1() {
        return number1;
    }

    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    @JsonProperty("number2")
    public Float getNumber2() {
        return number2;
    }

    public void setNumber2(Float number2) {
        this.number2 = number2;
    }

    @JsonProperty("remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("text1")
    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    @JsonProperty("text2")
    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    @JsonProperty("text3")
    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    @JsonProperty("total-value")
    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("verbatim-date")
    public String getVerbatimDate() {
        return verbatimDate;
    }

    public void setVerbatimDate(String verbatimDate) {
        this.verbatimDate = verbatimDate;
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

    @XmlTransient
    @JsonProperty("accessionattachments")
    public List<Accessionattachment> getAccessionattachmentList() {
        return accessionattachmentList;
    }

    public void setAccessionattachmentList(List<Accessionattachment> accessionattachmentList) {
        this.accessionattachmentList = accessionattachmentList;
    }

    @XmlTransient
    @JsonProperty("treatmentevents")
    public List<Treatmentevent> getTreatmenteventList() {
        return treatmenteventList;
    }

    public void setTreatmenteventList(List<Treatmentevent> treatmenteventList) {
        this.treatmenteventList = treatmenteventList;
    }

    @XmlTransient
    @JsonProperty("collectionobjects")
    public List<Collectionobject> getCollectionobjectList() {
        return collectionobjectList;
    }

    public void setCollectionobjectList(List<Collectionobject> collectionobjectList) {
        this.collectionobjectList = collectionobjectList;
    }

    @XmlIDREF
//    @XmlElementWrapper(name = "accessionAgents")
//    @XmlElement(name = "accessionAgent")
    @JsonProperty("accessionagents")
    public List<Accessionagent> getAccessionagentList() {
        return accessionagentList;
    }

    public void setAccessionagentList(List<Accessionagent> accessionagentList) {
        this.accessionagentList = accessionagentList;
    }

    @XmlTransient
    @JsonProperty("repository-agreement-id")
    public Repositoryagreement getRepositoryAgreementID() {
        return repositoryAgreementID;
    }

    public void setRepositoryAgreementID(Repositoryagreement repositoryAgreementID) {
        this.repositoryAgreementID = repositoryAgreementID;
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
    @JsonProperty("division-id")
    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
    }

    @XmlTransient
    @JsonProperty("address-of-record-id")
    public Addressofrecord getAddressOfRecordID() {
        return addressOfRecordID;
    }

    public void setAddressOfRecordID(Addressofrecord addressOfRecordID) {
        this.addressOfRecordID = addressOfRecordID;
    }

    @XmlTransient
    @JsonProperty("appraisals")
    public List<Appraisal> getAppraisalList() {
        return appraisalList;
    }

    public void setAppraisalList(List<Appraisal> appraisalList) {
        this.appraisalList = appraisalList;
    }

    @XmlTransient
    @JsonProperty("accessionauthorizations")
    public List<Accessionauthorization> getAccessionauthorizationList() {
        return accessionauthorizationList;
    }

    public void setAccessionauthorizationList(List<Accessionauthorization> accessionauthorizationList) {
        this.accessionauthorizationList = accessionauthorizationList;
    }

    @XmlTransient
    @JsonProperty("deaccessions")
    public List<Deaccession> getDeaccessionList() {
        return deaccessionList;
    }

    public void setDeaccessionList(List<Deaccession> deaccessionList) {
        this.deaccessionList = deaccessionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionID != null ? accessionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accession)) {
            return false;
        }
        Accession other = (Accession) object;
        return !((this.accessionID == null && other.accessionID != null) || (this.accessionID != null && !this.accessionID.equals(other.accessionID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Accession[ accessionID=" + accessionID + " ]";
    }  
 
}
