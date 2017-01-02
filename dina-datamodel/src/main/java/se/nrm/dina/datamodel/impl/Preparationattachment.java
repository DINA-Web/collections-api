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
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "preparationAttachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preparationattachment.findAll", query = "SELECT p FROM Preparationattachment p"),
    @NamedQuery(name = "Preparationattachment.findByPreparationAttachmentID", query = "SELECT p FROM Preparationattachment p WHERE p.preparationAttachmentID = :preparationAttachmentID"), 
    @NamedQuery(name = "Preparationattachment.findByCollectionMemberID", query = "SELECT p FROM Preparationattachment p WHERE p.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Preparationattachment.findByOrdinal", query = "SELECT p FROM Preparationattachment p WHERE p.ordinal = :ordinal")})
@DinaResource(type = "preparationAttachment")
public class Preparationattachment extends BaseEntity {
   
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PreparationAttachmentID")
    @DinaField(name = "preparation-attachment-id")
    @DinaId
    private Integer preparationAttachmentID;
    
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    @DinaField(name = "collection-member-id")
    private int collectionMemberID;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ordinal")
    @DinaIgnor
    private int ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    @DinaField(name = "remarks")
    private String remarks;
    
    @JoinColumn(name = "PreparationID", referencedColumnName = "PreparationID")
    @ManyToOne(optional = false)
    @DinaManyToOne(name = "preparation", type = "preparation")
    private Preparation preparationID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    @DinaManyToOne(name = "attachment", type = "attachment")
    private Attachment attachmentID;

    public Preparationattachment() {
    }

    public Preparationattachment(Integer preparationAttachmentID) {
        this.preparationAttachmentID = preparationAttachmentID;
    }

    public Preparationattachment(Integer preparationAttachmentID, Date timestampCreated, int collectionMemberID, int ordinal) {
        this.preparationAttachmentID = preparationAttachmentID;
        this.timestampCreated = timestampCreated;
        this.collectionMemberID = collectionMemberID;
        this.ordinal = ordinal;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(preparationAttachmentID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + preparationAttachmentID;
//    }
    
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return preparationAttachmentID;
    }

    @JsonProperty("preparation-attachment-id")
    public Integer getPreparationAttachmentID() {
        return preparationAttachmentID;
    }

    public void setPreparationAttachmentID(Integer preparationAttachmentID) {
        this.preparationAttachmentID = preparationAttachmentID;
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

    @XmlTransient
    @JsonProperty("preparation-id")
    public Preparation getPreparationID() {
        return preparationID;
    }

    public void setPreparationID(Preparation preparationID) {
        this.preparationID = preparationID;
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
        hash += (preparationAttachmentID != null ? preparationAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preparationattachment)) {
            return false;
        }
        Preparationattachment other = (Preparationattachment) object;
        return !((this.preparationAttachmentID == null && other.preparationAttachmentID != null) || (this.preparationAttachmentID != null && !this.preparationAttachmentID.equals(other.preparationAttachmentID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Preparationattachment[ preparationAttachmentID=" + preparationAttachmentID + " ]";
    } 
}
