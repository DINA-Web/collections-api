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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient; 
import org.codehaus.jackson.annotate.JsonIgnore;
import se.nrm.dina.json.converter.annotation.DinaField;
import se.nrm.dina.json.converter.annotation.DinaId;
import se.nrm.dina.json.converter.annotation.DinaIgnor;
import se.nrm.dina.json.converter.annotation.DinaResource; 

/**
 *
 * @author idali
 */
@Entity
@Table(name = "attachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachment.findAll", query = "SELECT a FROM Attachment a"),
    @NamedQuery(name = "Attachment.findByAttachmentID", query = "SELECT a FROM Attachment a WHERE a.attachmentID = :attachmentID"), 
    @NamedQuery(name = "Attachment.findByAttachmentLocation", query = "SELECT a FROM Attachment a WHERE a.attachmentLocation = :attachmentLocation"),  
    @NamedQuery(name = "Attachment.findByFileCreatedDate", query = "SELECT a FROM Attachment a WHERE a.fileCreatedDate = :fileCreatedDate"),
    @NamedQuery(name = "Attachment.findByLicense", query = "SELECT a FROM Attachment a WHERE a.license = :license"),
    @NamedQuery(name = "Attachment.findByMimeType", query = "SELECT a FROM Attachment a WHERE a.mimeType = :mimeType"),
    @NamedQuery(name = "Attachment.findByOrigFilename", query = "SELECT a FROM Attachment a WHERE a.origFilename = :origFilename"),
    @NamedQuery(name = "Attachment.findByTitle", query = "SELECT a FROM Attachment a WHERE a.title = :title"), 
    @NamedQuery(name = "Attachment.findByGuid", query = "SELECT a FROM Attachment a WHERE a.guid = :guid"),
    @NamedQuery(name = "Attachment.findByVisibility", query = "SELECT a FROM Attachment a WHERE a.visibility = :visibility"),
    @NamedQuery(name = "Attachment.findByAttachmentImageAttributeID", query = "SELECT a FROM Attachment a WHERE a.attachmentImageAttributeID = :attachmentImageAttributeID")})
@DinaResource(type = "attachment")
public class Attachment extends BaseEntity {
   
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AttachmentID")
    @DinaField(name = "attachment-id")
    @DinaId
    private Integer attachmentID;
    
    
    @Size(max = 128)
    @Column(name = "AttachmentLocation")
    @DinaIgnor
    private String attachmentLocation;
    
    @Size(max = 64)
    @Column(name = "CopyrightDate")
    @DinaIgnor
    private String copyrightDate;
    
    @Size(max = 64)
    @Column(name = "CopyrightHolder")
    @DinaIgnor
    private String copyrightHolder;
    
    @Size(max = 64)
    @Column(name = "Credit")
    @DinaIgnor
    private String credit;
    
    @Size(max = 64)
    @Column(name = "DateImaged")
    @DinaIgnor
    private String dateImaged;
    
    @Column(name = "FileCreatedDate")
    @Temporal(TemporalType.DATE)
    @DinaIgnor
    private Date fileCreatedDate;
    
    @Size(max = 64)
    @Column(name = "License")
    @DinaField(name = "license")
    private String license;
    
    @Size(max = 64)
    @Column(name = "MimeType")
    @DinaIgnor
    private String mimeType;
    
    @Size(max = 20000)
    @Column(name = "origFilename")
    @DinaField(name = "orig-fiename")
    private String origFilename;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    @DinaField(name = "remarks")
    private String remarks;
    
    @Size(max = 255)
    @Column(name = "title")
    @DinaField(name = "title")
    private String title;
    
    @Basic(optional = false)
    @Column(name = "TableID")
    @DinaField(name = "table")
    private Short tableID;
    
    
    @Column(name = "ScopeID")
    @DinaIgnor
    private Integer scopeID;
    
    @Column(name = "ScopeType")
    @DinaIgnor
    private Short scopeType;
    
    @Size(max = 128)
    @Column(name = "GUID")
    @DinaField(name = "guid")
    private String guid;
    
    @Column(name = "Visibility")
    @DinaIgnor
    private Short visibility;
    
    @Column(name = "AttachmentImageAttributeID")
    @DinaIgnor
    private Integer attachmentImageAttributeID;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsPublic")
    @DinaField(name = "is-public")
    private boolean isPublic;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    @DinaIgnor
    private List<Treatmenteventattachment> treatmenteventattachmentList;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collectingeventattachment> collectingeventattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Preparationattachment> preparationattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Repositoryagreementattachment> repositoryagreementattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Agentattachment> agentattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Permitattachment> permitattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Accessionattachment> accessionattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Dnasequenceattachment> dnasequenceattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collectionobjectattachment> collectionobjectattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Storageattachment> storageattachmentList;
    
    @OneToMany(mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Attachmentmetadata> attachmentmetadataList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Taxonattachment> taxonattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Conserveventattachment> conserveventattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Localityattachment> localityattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Conservdescriptionattachment> conservdescriptionattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Fieldnotebookpageattachment> fieldnotebookpageattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Fieldnotebookattachment> fieldnotebookattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Attachmenttag> attachmenttagList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Dnasequencerunattachment> dnasequencerunattachmentList;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID") 
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    @DinaIgnor
    private Specifyuser visibilitySetByID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Loanattachment> loanattachmentList;

    public Attachment() {
    }

    public Attachment(Integer attachmentID) {
        this.attachmentID = attachmentID;
    }

    public Attachment(Integer attachmentID, Date timestampCreated) {
        this.attachmentID = attachmentID;
        this.timestampCreated = timestampCreated;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(attachmentID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + attachmentID;
//    }
    
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return attachmentID;
    }
    
    @JsonProperty("attachment-id")
    public Integer getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Integer attachmentID) {
        this.attachmentID = attachmentID;
    }
 
    @JsonProperty("attachment-location")
    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation) {
        this.attachmentLocation = attachmentLocation;
    }

    @JsonProperty("copyright-date")
    public String getCopyrightDate() {
        return copyrightDate;
    }

    public void setCopyrightDate(String copyrightDate) {
        this.copyrightDate = copyrightDate;
    }

    @JsonProperty("copyright-holder")
    public String getCopyrightHolder() {
        return copyrightHolder;
    }

    public void setCopyrightHolder(String copyrightHolder) {
        this.copyrightHolder = copyrightHolder;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @JsonProperty("date-imaged")
    public String getDateImaged() {
        return dateImaged;
    }

    public void setDateImaged(String dateImaged) {
        this.dateImaged = dateImaged;
    }

    @JsonProperty("file-created-date")
    public Date getFileCreatedDate() {
        return fileCreatedDate;
    }

    public void setFileCreatedDate(Date fileCreatedDate) {
        this.fileCreatedDate = fileCreatedDate;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @JsonProperty("mime-type")
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @JsonProperty("orig-filename")
    public String getOrigFilename() {
        return origFilename;
    }

    public void setOrigFilename(String origFilename) {
        this.origFilename = origFilename;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("table-id")
    public Short getTableID() {
        return tableID;
    }

    public void setTableID(Short tableID) {
        this.tableID = tableID;
    }

    @JsonProperty("scope-id")
    public Integer getScopeID() {
        return scopeID;
    }

    public void setScopeID(Integer scopeID) {
        this.scopeID = scopeID;
    }

    @JsonProperty("scope-type")
    public Short getScopeType() {
        return scopeType;
    }

    public void setScopeType(Short scopeType) {
        this.scopeType = scopeType;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Short getVisibility() {
        return visibility;
    }

    public void setVisibility(Short visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("attachment-image-attributed-id")
    public Integer getAttachmentImageAttributeID() {
        return attachmentImageAttributeID;
    }

    public void setAttachmentImageAttributeID(Integer attachmentImageAttributeID) {
        this.attachmentImageAttributeID = attachmentImageAttributeID;
    }

    @XmlTransient
    public List<Collectingeventattachment> getCollectingeventattachmentList() {
        return collectingeventattachmentList;
    }

    public void setCollectingeventattachmentList(List<Collectingeventattachment> collectingeventattachmentList) {
        this.collectingeventattachmentList = collectingeventattachmentList;
    }

    @XmlTransient
    public List<Preparationattachment> getPreparationattachmentList() {
        return preparationattachmentList;
    }

    public void setPreparationattachmentList(List<Preparationattachment> preparationattachmentList) {
        this.preparationattachmentList = preparationattachmentList;
    }

    @XmlTransient
    public List<Repositoryagreementattachment> getRepositoryagreementattachmentList() {
        return repositoryagreementattachmentList;
    }

    public void setRepositoryagreementattachmentList(List<Repositoryagreementattachment> repositoryagreementattachmentList) {
        this.repositoryagreementattachmentList = repositoryagreementattachmentList;
    }

    @XmlTransient
    public List<Agentattachment> getAgentattachmentList() {
        return agentattachmentList;
    }

    public void setAgentattachmentList(List<Agentattachment> agentattachmentList) {
        this.agentattachmentList = agentattachmentList;
    }

    @XmlTransient
    public List<Permitattachment> getPermitattachmentList() {
        return permitattachmentList;
    }

    public void setPermitattachmentList(List<Permitattachment> permitattachmentList) {
        this.permitattachmentList = permitattachmentList;
    }

    @XmlTransient
    public List<Accessionattachment> getAccessionattachmentList() {
        return accessionattachmentList;
    }

    public void setAccessionattachmentList(List<Accessionattachment> accessionattachmentList) {
        this.accessionattachmentList = accessionattachmentList;
    }

    @XmlTransient
    public List<Dnasequenceattachment> getDnasequenceattachmentList() {
        return dnasequenceattachmentList;
    }

    public void setDnasequenceattachmentList(List<Dnasequenceattachment> dnasequenceattachmentList) {
        this.dnasequenceattachmentList = dnasequenceattachmentList;
    }

    @XmlTransient
    public List<Collectionobjectattachment> getCollectionobjectattachmentList() {
        return collectionobjectattachmentList;
    }

    public void setCollectionobjectattachmentList(List<Collectionobjectattachment> collectionobjectattachmentList) {
        this.collectionobjectattachmentList = collectionobjectattachmentList;
    }

    @XmlTransient
    public List<Storageattachment> getStorageattachmentList() {
        return storageattachmentList;
    }

    public void setStorageattachmentList(List<Storageattachment> storageattachmentList) {
        this.storageattachmentList = storageattachmentList;
    }

    @XmlTransient
    public List<Attachmentmetadata> getAttachmentmetadataList() {
        return attachmentmetadataList;
    }

    public void setAttachmentmetadataList(List<Attachmentmetadata> attachmentmetadataList) {
        this.attachmentmetadataList = attachmentmetadataList;
    }

    @XmlTransient
    public List<Taxonattachment> getTaxonattachmentList() {
        return taxonattachmentList;
    }

    public void setTaxonattachmentList(List<Taxonattachment> taxonattachmentList) {
        this.taxonattachmentList = taxonattachmentList;
    }

    @XmlTransient
    public List<Conserveventattachment> getConserveventattachmentList() {
        return conserveventattachmentList;
    }

    public void setConserveventattachmentList(List<Conserveventattachment> conserveventattachmentList) {
        this.conserveventattachmentList = conserveventattachmentList;
    }

    @XmlTransient
    public List<Localityattachment> getLocalityattachmentList() {
        return localityattachmentList;
    }

    public void setLocalityattachmentList(List<Localityattachment> localityattachmentList) {
        this.localityattachmentList = localityattachmentList;
    }

    @XmlTransient
    public List<Conservdescriptionattachment> getConservdescriptionattachmentList() {
        return conservdescriptionattachmentList;
    }

    public void setConservdescriptionattachmentList(List<Conservdescriptionattachment> conservdescriptionattachmentList) {
        this.conservdescriptionattachmentList = conservdescriptionattachmentList;
    }

    @XmlTransient
    public List<Fieldnotebookpageattachment> getFieldnotebookpageattachmentList() {
        return fieldnotebookpageattachmentList;
    }

    public void setFieldnotebookpageattachmentList(List<Fieldnotebookpageattachment> fieldnotebookpageattachmentList) {
        this.fieldnotebookpageattachmentList = fieldnotebookpageattachmentList;
    }

    @XmlTransient
    public List<Fieldnotebookattachment> getFieldnotebookattachmentList() {
        return fieldnotebookattachmentList;
    }

    public void setFieldnotebookattachmentList(List<Fieldnotebookattachment> fieldnotebookattachmentList) {
        this.fieldnotebookattachmentList = fieldnotebookattachmentList;
    }

    @XmlTransient
    public List<Attachmenttag> getAttachmenttagList() {
        return attachmenttagList;
    }

    public void setAttachmenttagList(List<Attachmenttag> attachmenttagList) {
        this.attachmenttagList = attachmenttagList;
    }

    @XmlTransient
    public List<Dnasequencerunattachment> getDnasequencerunattachmentList() {
        return dnasequencerunattachmentList;
    }

    public void setDnasequencerunattachmentList(List<Dnasequencerunattachment> dnasequencerunattachmentList) {
        this.dnasequencerunattachmentList = dnasequencerunattachmentList;
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

    @XmlIDREF
    @JsonProperty("visibility-set-by-id")
    public Specifyuser getVisibilitySetByID() {
        return visibilitySetByID;
    }

    public void setVisibilitySetByID(Specifyuser visibilitySetByID) {
        this.visibilitySetByID = visibilitySetByID;
    }

    @XmlTransient
    public List<Fieldnotebookpagesetattachment> getFieldnotebookpagesetattachmentList() {
        return fieldnotebookpagesetattachmentList;
    }

    public void setFieldnotebookpagesetattachmentList(List<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentList) {
        this.fieldnotebookpagesetattachmentList = fieldnotebookpagesetattachmentList;
    }

    @XmlTransient
    public List<Loanattachment> getLoanattachmentList() {
        return loanattachmentList;
    }

    public void setLoanattachmentList(List<Loanattachment> loanattachmentList) {
        this.loanattachmentList = loanattachmentList;
    }

    @JsonProperty("is-public")
    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @XmlTransient
    @JsonIgnore
    public List<Treatmenteventattachment> getTreatmenteventattachmentList() {
        return treatmenteventattachmentList;
    }

    public void setTreatmenteventattachmentList(List<Treatmenteventattachment> treatmenteventattachmentList) {
        this.treatmenteventattachmentList = treatmenteventattachmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attachmentID != null ? attachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        return !((this.attachmentID == null && other.attachmentID != null) || (this.attachmentID != null && !this.attachmentID.equals(other.attachmentID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Attachment[ attachmentID=" + attachmentID + " ]";
    } 

    
}
