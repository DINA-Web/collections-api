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
import se.nrm.dina.json.converter.annotation.DinaOneToMany;
import se.nrm.dina.json.converter.annotation.DinaResource; 

/**
 *
 * @author idali
 */
@Entity
@Table(name = "picklist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Picklist.findAll", query = "SELECT p FROM Picklist p"),
    @NamedQuery(name = "Picklist.findByPickListID", query = "SELECT p FROM Picklist p WHERE p.pickListID = :pickListID"), 
    @NamedQuery(name = "Picklist.findByFieldName", query = "SELECT p FROM Picklist p WHERE p.fieldName = :fieldName"),
    @NamedQuery(name = "Picklist.findByName", query = "SELECT p FROM Picklist p WHERE p.name = :name"),
    @NamedQuery(name = "Picklist.findByReadOnly", query = "SELECT p FROM Picklist p WHERE p.readOnly = :readOnly"),
    @NamedQuery(name = "Picklist.findBySizeLimit", query = "SELECT p FROM Picklist p WHERE p.sizeLimit = :sizeLimit"),
    @NamedQuery(name = "Picklist.findBySortType", query = "SELECT p FROM Picklist p WHERE p.sortType = :sortType"),
    @NamedQuery(name = "Picklist.findByTableName", query = "SELECT p FROM Picklist p WHERE p.tableName = :tableName"),
    @NamedQuery(name = "Picklist.findByType", query = "SELECT p FROM Picklist p WHERE p.type = :type")})
@DinaResource(type = "pickList")
public class Picklist extends BaseEntity {
  
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PickListID")
    @DinaField(name = "pick-list-id")
    @DinaId
    private Integer pickListID;
    
    
    @Size(max = 64)
    @Column(name = "FieldName")
    @DinaField(name = "field-name")
    private String fieldName;
    
    @Size(max = 32)
    @Column(name = "FilterFieldName")
    @DinaIgnor
    private String filterFieldName;
    
    @Size(max = 32)
    @Column(name = "FilterValue")
    @DinaIgnor
    private String filterValue;
    
    @Size(max = 64)
    @Column(name = "Formatter")
    @DinaIgnor
    private String formatter;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsSystem")
    @DinaIgnor
    private boolean isSystem;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    @DinaField(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReadOnly")
    @DinaIgnor
    private boolean readOnly;
    
    @Column(name = "SizeLimit")
    @DinaIgnor
    private Integer sizeLimit;
    
    @Column(name = "SortType")
    @DinaIgnor
    private Short sortType;
    
    @Size(max = 64)
    @Column(name = "TableName")
    @DinaIgnor
    private String tableName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Type")
    @DinaField(name = "type")
    private short type;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pickListID", fetch = FetchType.LAZY)
    @DinaOneToMany(name = "items", type = "pickListItem")
    private List<Picklistitem> picklistitemList;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    @DinaIgnor
    private Collection collectionID;

    public Picklist() {
    }

    public Picklist(Integer pickListID) {
        this.pickListID = pickListID;
    }

    public Picklist(Integer pickListID, Date timestampCreated, boolean isSystem, String name, boolean readOnly, short type) {
        this.pickListID = pickListID;
        this.timestampCreated = timestampCreated;
        this.isSystem = isSystem;
        this.name = name;
        this.readOnly = readOnly;
        this.type = type;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(pickListID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + pickListID;
//    }
    
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return pickListID;
    }
    
    @JsonProperty("pick-list-id")
    public Integer getPickListID() {
        return pickListID;
    }

    public void setPickListID(Integer pickListID) {
        this.pickListID = pickListID;
    }
  
    @JsonProperty("field-name")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @JsonProperty("filter-field-name")
    public String getFilterFieldName() {
        return filterFieldName;
    }

    public void setFilterFieldName(String filterFieldName) {
        this.filterFieldName = filterFieldName;
    }

    @JsonProperty("filter-value")
    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @JsonProperty("is-system")
    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("read-only")
    public boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @JsonProperty("size-limit")
    public Integer getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(Integer sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    @JsonProperty("sort-type")
    public Short getSortType() {
        return sortType;
    }

    public void setSortType(Short sortType) {
        this.sortType = sortType;
    }

    @JsonProperty("table-name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    @XmlTransient
    public List<Picklistitem> getPicklistitemList() {
        return picklistitemList;
    }

    public void setPicklistitemList(List<Picklistitem> picklistitemList) {
        this.picklistitemList = picklistitemList;
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
    @JsonProperty("collection-id")
    public Collection getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(Collection collectionID) {
        this.collectionID = collectionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pickListID != null ? pickListID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Picklist)) {
            return false;
        }
        Picklist other = (Picklist) object;
        return !((this.pickListID == null && other.pickListID != null) || (this.pickListID != null && !this.pickListID.equals(other.pickListID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Picklist[ pickListID=" + pickListID + " ]";
    } 
}
