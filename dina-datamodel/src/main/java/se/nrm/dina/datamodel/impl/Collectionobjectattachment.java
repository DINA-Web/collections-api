/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.datamodel.impl;
 
import com.fasterxml.jackson.annotation.JsonProperty;
import se.nrm.dina.datamodel.BaseEntity;
import java.util.Date;
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
//import se.nrm.dina.datamodel.annotation.DinaField;
//import se.nrm.dina.datamodel.annotation.DinaIgnor;
//import se.nrm.dina.datamodel.annotation.DinaManyToOne;
//import se.nrm.dina.datamodel.annotation.DinaResource;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collectionobjectattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobjectattachment.findAll", query = "SELECT c FROM Collectionobjectattachment c"),
    @NamedQuery(name = "Collectionobjectattachment.findByCollectionObjectAttachmentID", query = "SELECT c FROM Collectionobjectattachment c WHERE c.collectionObjectAttachmentID = :collectionObjectAttachmentID"),
     @NamedQuery(name = "Collectionobjectattachment.findByCollectionMemberID", query = "SELECT c FROM Collectionobjectattachment c WHERE c.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Collectionobjectattachment.findByOrdinal", query = "SELECT c FROM Collectionobjectattachment c WHERE c.ordinal = :ordinal")})
@DinaResource(type = "collectionObjectAttachment")
public class Collectionobjectattachment extends BaseEntity {
  
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CollectionObjectAttachmentID")
    @DinaField(name = "collection-object-attachment-id")
    @DinaId
    private Integer collectionObjectAttachmentID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    @DinaField(name = "collection-member-id")
    private int collectionMemberID;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ordinal")
    @DinaField(name = "ordinal")
    private int ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    @DinaField(name = "remarks")
    private String remarks;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @DinaIgnor
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @DinaManyToOne(name = "attachment", type = "attachment")
    private Attachment attachmentID;

    public Collectionobjectattachment() {
    }

    public Collectionobjectattachment(Integer collectionObjectAttachmentID) {
        this.collectionObjectAttachmentID = collectionObjectAttachmentID;
    }

    public Collectionobjectattachment(Integer collectionObjectAttachmentID, Date timestampCreated, int collectionMemberID, int ordinal) {
        this.collectionObjectAttachmentID = collectionObjectAttachmentID;
        this.timestampCreated = timestampCreated;
        this.collectionMemberID = collectionMemberID;
        this.ordinal = ordinal;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(collectionObjectAttachmentID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + collectionObjectAttachmentID;
//    }
    
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return collectionObjectAttachmentID;
    }

    @JsonProperty("collection-object-attachment-id")
    public Integer getCollectionObjectAttachmentID() {
        return collectionObjectAttachmentID;
    }

    public void setCollectionObjectAttachmentID(Integer collectionObjectAttachmentID) {
        this.collectionObjectAttachmentID = collectionObjectAttachmentID;
    }
 
    @JsonProperty("collection-member-id")
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    @JsonProperty("created-by-agent-id")
    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlIDREF
    @JsonProperty("attachment-id")
    public Attachment getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Attachment attachmentID) {
        this.attachmentID = attachmentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectionObjectAttachmentID != null ? collectionObjectAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobjectattachment)) {
            return false;
        }
        Collectionobjectattachment other = (Collectionobjectattachment) object;
        return !((this.collectionObjectAttachmentID == null && other.collectionObjectAttachmentID != null) || (this.collectionObjectAttachmentID != null && !this.collectionObjectAttachmentID.equals(other.collectionObjectAttachmentID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Collectionobjectattachment[ collectionObjectAttachmentID=" + collectionObjectAttachmentID + " ]";
    } 
}
