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
@Table(name = "collector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collector.findAll", query = "SELECT c FROM Collector c"),
    @NamedQuery(name = "Collector.findByCollectorID", query = "SELECT c FROM Collector c WHERE c.collectorID = :collectorID"), 
    @NamedQuery(name = "Collector.findByIsPrimary", query = "SELECT c FROM Collector c WHERE c.isPrimary = :isPrimary"),
    @NamedQuery(name = "Collector.findByOrderNumber", query = "SELECT c FROM Collector c WHERE c.orderNumber = :orderNumber")})
@DinaResource(type = "collector")
public class Collector extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CollectorID")
    @DinaField(name = "collector-id")
    @DinaId
    private Integer collectorID;
    
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsPrimary")
    @DinaField(name = "is-primary")
    private boolean isPrimary;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderNumber")
    @DinaField(name = "order-number")
    private int orderNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    @DinaField(name = "remarks")
    private String remarks;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    @DinaManyToOne(name = "agent", type = "agent")
    private Agent agentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    @DinaIgnor
    private Division divisionID;
    
    @JoinColumn(name = "CollectingEventID", referencedColumnName = "CollectingEventID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @DinaManyToOne(name = "collecting-event", type = "collectingevent")
    private Collectingevent collectingEventID;

    public Collector() {
    }

    public Collector(Integer collectorID) {
        this.collectorID = collectorID;
    }

    public Collector(Integer collectorID, Date timestampCreated, boolean isPrimary, int orderNumber) {
        this.collectorID = collectorID;
        this.timestampCreated = timestampCreated;
        this.isPrimary = isPrimary;
        this.orderNumber = orderNumber;
    }
    
     
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(collectorID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + collectorID;
//    }
    
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return collectorID;
    }

    @JsonProperty("collector-id")
    public Integer getCollectorID() {
        return collectorID;
    }

    public void setCollectorID(Integer collectorID) {
        this.collectorID = collectorID;
    }
 
    @JsonProperty("is-primary")
    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    @JsonProperty("order-number")
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlIDREF
    @JsonProperty("agent-id")
    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
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
    @JsonProperty("division-id")
    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
    }

    @XmlIDREF
    @JsonProperty("collecting-event-id")
    public Collectingevent getCollectingEventID() {
        return collectingEventID;
    }

    public void setCollectingEventID(Collectingevent collectingEventID) {
        this.collectingEventID = collectingEventID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectorID != null ? collectorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collector)) {
            return false;
        }
        Collector other = (Collector) object;
        return !((this.collectorID == null && other.collectorID != null) || (this.collectorID != null && !this.collectorID.equals(other.collectorID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Collector[ collectorID=" + collectorID + " ]";
    } 
}
