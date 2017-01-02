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
import se.nrm.dina.json.converter.annotation.DinaField;
import se.nrm.dina.json.converter.annotation.DinaId;
import se.nrm.dina.json.converter.annotation.DinaIgnor;
import se.nrm.dina.json.converter.annotation.DinaManyToOne;
import se.nrm.dina.json.converter.annotation.DinaOneToMany;
import se.nrm.dina.json.converter.annotation.DinaResource;
//import se.nrm.dina.datamodel.annotation.DinaField;
//import se.nrm.dina.datamodel.annotation.DinaIgnor;
//import se.nrm.dina.datamodel.annotation.DinaManyToOne;
//import se.nrm.dina.datamodel.annotation.DinaOneToMany;
//import se.nrm.dina.datamodel.annotation.DinaResource;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "storage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storage.findAll", query = "SELECT s FROM Storage s"),
    @NamedQuery(name = "Storage.findByStorageID", query = "SELECT s FROM Storage s WHERE s.storageID = :storageID"),  
    @NamedQuery(name = "Storage.findByFullName", query = "SELECT s FROM Storage s WHERE s.fullName = :fullName"),
    @NamedQuery(name = "Storage.findByHighestChildNodeNumber", query = "SELECT s FROM Storage s WHERE s.highestChildNodeNumber = :highestChildNodeNumber"),
    @NamedQuery(name = "Storage.findByIsAccepted", query = "SELECT s FROM Storage s WHERE s.isAccepted = :isAccepted"),
    @NamedQuery(name = "Storage.findByName", query = "SELECT s FROM Storage s WHERE s.name = :name"),
    @NamedQuery(name = "Storage.findByNodeNumber", query = "SELECT s FROM Storage s WHERE s.nodeNumber = :nodeNumber"), 
    @NamedQuery(name = "Storage.findByRankID", query = "SELECT s FROM Storage s WHERE s.rankID = :rankID") })
@DinaResource(type = "storage")
public class Storage extends BaseEntity {
    
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "StorageID")
    @DinaField(name = "storage-id")
    @DinaId
    private Integer storageID;
    
    
    @Size(max = 16)
    @Column(name = "Abbrev")
    @DinaIgnor
    private String abbrev;
    
    @Size(max = 255)
    @Column(name = "FullName")
    @DinaField(name = "full-name")
    private String fullName;
    
    @Column(name = "HighestChildNodeNumber")
    @DinaIgnor
    private Integer highestChildNodeNumber;
    
    @Column(name = "IsAccepted")
    @DinaIgnor
    private Boolean isAccepted;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    @DinaField(name = "name")
    private String name;
    
    @Column(name = "NodeNumber")
    @DinaIgnor
    private Integer nodeNumber;
    
    @Column(name = "Number1")
    @DinaIgnor
    private Integer number1;
    
    @Column(name = "Number2")
    @DinaIgnor
    private Integer number2;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    @DinaField(name = "rank-id")
    private int rankID;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    @DinaField(name = "remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Text1")
    @DinaIgnor
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    @DinaIgnor
    private String text2;
    
    @Column(name = "TimestampVersion")
    @Temporal(TemporalType.TIMESTAMP)
    @DinaIgnor
    private Date timestampVersion;
    
    @OneToMany(mappedBy = "storageID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Container> containerList;
    
    @OneToMany(mappedBy = "storageID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Preparation> preparationList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageID",  fetch = FetchType.LAZY)
    @DinaOneToMany(name = "storageattachments", type = "storageattachment")
    private List<Storageattachment> storageattachmentList;
    
    @OneToMany(mappedBy = "acceptedID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Storage> storageList;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "StorageID")
    @ManyToOne
    @DinaManyToOne(name = "storage", type = "storage")
    private Storage acceptedID;
    
    @JoinColumn(name = "StorageTreeDefItemID", referencedColumnName = "StorageTreeDefItemID")
    @ManyToOne(optional = false)
    @DinaManyToOne(name = "storage-tree-def-item", type = "stroageTreeDefItem")
    private Storagetreedefitem storageTreeDefItemID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "StorageTreeDefID", referencedColumnName = "StorageTreeDefID")
    @ManyToOne(optional = false)
    @DinaManyToOne(name = "storage-tree-def", type = "storageTreeDef")
    private Storagetreedef storageTreeDefID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @OneToMany(mappedBy = "parentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Storage> storageList1;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "StorageID")
    @ManyToOne
    @DinaManyToOne(name = "parent", type = "storage")
    private Storage parentID;

    public Storage() {
    }

    public Storage(Integer storageID) {
        this.storageID = storageID;
    }

    public Storage(Integer storageID, Date timestampCreated, String name, int rankID) {
        this.storageID = storageID;
        this.timestampCreated = timestampCreated;
        this.name = name;
        this.rankID = rankID;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(storageID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + storageID;
//    }
    
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return storageID;
    }

    @JsonProperty("storage-id")
    public Integer getStorageID() {
        return storageID;
    }

    public void setStorageID(Integer storageID) {
        this.storageID = storageID;
    }
 

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    @JsonProperty("full-name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("highest-child-node-number")
    public Integer getHighestChildNodeNumber() {
        return highestChildNodeNumber;
    }

    public void setHighestChildNodeNumber(Integer highestChildNodeNumber) {
        this.highestChildNodeNumber = highestChildNodeNumber;
    }

    @JsonProperty("is-accepted")
    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    @JsonProperty("node-number")
    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }

    @JsonProperty("rank-id")
    public int getRankID() {
        return rankID;
    }

    public void setRankID(int rankID) {
        this.rankID = rankID;
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

    @JsonProperty("timestamp-version")
    public Date getTimestampVersion() {
        return timestampVersion;
    }

    public void setTimestampVersion(Date timestampVersion) {
        this.timestampVersion = timestampVersion;
    }

    @XmlTransient
    public List<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }

    @XmlTransient
    public List<Preparation> getPreparationList() {
        return preparationList;
    }

    public void setPreparationList(List<Preparation> preparationList) {
        this.preparationList = preparationList;
    }

    @XmlTransient
    public List<Storageattachment> getStorageattachmentList() {
        return storageattachmentList;
    }

    public void setStorageattachmentList(List<Storageattachment> storageattachmentList) {
        this.storageattachmentList = storageattachmentList;
    }

    @XmlTransient
    public List<Storage> getStorageList() {
        return storageList;
    }

    public void setStorageList(List<Storage> storageList) {
        this.storageList = storageList;
    }

    @XmlIDREF 
    @JsonProperty("accepted-id")
    public Storage getAcceptedID() {
        return acceptedID;
    }

    public void setAcceptedID(Storage acceptedID) {
        this.acceptedID = acceptedID;
    }

    @XmlIDREF 
    @JsonProperty("storage-tree-def-item-id")
    public Storagetreedefitem getStorageTreeDefItemID() {
        return storageTreeDefItemID;
    }

    public void setStorageTreeDefItemID(Storagetreedefitem storageTreeDefItemID) {
        this.storageTreeDefItemID = storageTreeDefItemID;
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
    @JsonProperty("storage-tree-def-id")
    public Storagetreedef getStorageTreeDefID() {
        return storageTreeDefID;
    }

    public void setStorageTreeDefID(Storagetreedef storageTreeDefID) {
        this.storageTreeDefID = storageTreeDefID;
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
    public List<Storage> getStorageList1() {
        return storageList1;
    }

    public void setStorageList1(List<Storage> storageList1) {
        this.storageList1 = storageList1;
    }

    @XmlIDREF 
    @JsonProperty("parent-id")
    public Storage getParentID() {
        return parentID;
    }

    public void setParentID(Storage parentID) {
        this.parentID = parentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storageID != null ? storageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storage)) {
            return false;
        }
        Storage other = (Storage) object;
        return !((this.storageID == null && other.storageID != null) || (this.storageID != null && !this.storageID.equals(other.storageID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Storage[ storageID=" + storageID + " ]";
    } 
}
