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

/**
 *
 * @author idali
 */
@Entity
@Table(name = "taxon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxon.findAll", query = "SELECT t FROM Taxon t"),
    @NamedQuery(name = "Taxon.findByTaxonID", query = "SELECT t FROM Taxon t WHERE t.taxonID = :taxonID"), 
    @NamedQuery(name = "Taxon.findByAuthor", query = "SELECT t FROM Taxon t WHERE t.author = :author"),  
    @NamedQuery(name = "Taxon.findByCommonName", query = "SELECT t FROM Taxon t WHERE t.commonName = :commonName"),  
    @NamedQuery(name = "Taxon.findByFullName", query = "SELECT t FROM Taxon t WHERE t.fullName = :fullName"),
    @NamedQuery(name = "Taxon.findByGroupNumber", query = "SELECT t FROM Taxon t WHERE t.groupNumber = :groupNumber"),
    @NamedQuery(name = "Taxon.findByGuid", query = "SELECT t FROM Taxon t WHERE t.guid = :guid"),
    @NamedQuery(name = "Taxon.findByHighestChildNodeNumber", query = "SELECT t FROM Taxon t WHERE t.highestChildNodeNumber = :highestChildNodeNumber"),
    @NamedQuery(name = "Taxon.findByIsAccepted", query = "SELECT t FROM Taxon t WHERE t.isAccepted = :isAccepted"),
    @NamedQuery(name = "Taxon.findByIsHybrid", query = "SELECT t FROM Taxon t WHERE t.isHybrid = :isHybrid"),
    @NamedQuery(name = "Taxon.findByIsisNumber", query = "SELECT t FROM Taxon t WHERE t.isisNumber = :isisNumber"),
    @NamedQuery(name = "Taxon.findByLabelFormat", query = "SELECT t FROM Taxon t WHERE t.labelFormat = :labelFormat"),
    @NamedQuery(name = "Taxon.findByName", query = "SELECT t FROM Taxon t WHERE t.name = :name"), 
    @NamedQuery(name = "Taxon.findByNodeNumber", query = "SELECT t FROM Taxon t WHERE t.nodeNumber = :nodeNumber"), 
    @NamedQuery(name = "Taxon.findByRankID", query = "SELECT t FROM Taxon t WHERE t.rankID = :rankID") })
@DinaResource(type = "taxon")
public class Taxon extends BaseEntity {
     
    
//    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TaxonID")
    @DinaField(name = "taxon-id")
    @DinaId
    private Integer taxonID;
     
    @Size(max = 128)
    @Column(name = "Author")
    @DinaField(name = "author")
    private String author;
    
    @Size(max = 32)
    @Column(name = "CitesStatus")
    @DinaIgnor
    private String citesStatus;
    
    @Size(max = 32)
    @Column(name = "COLStatus")
    @DinaIgnor
    private String cOLStatus;
    
    @Size(max = 128)
    @Column(name = "CommonName")
    @DinaField(name = "common-nae")
    private String commonName;
    
    @Size(max = 32)
    @Column(name = "CultivarName")
    @DinaIgnor
    private String cultivarName;
    
    @Size(max = 64)
    @Column(name = "EnvironmentalProtectionStatus")
    @DinaIgnor
    private String environmentalProtectionStatus;
    
    @Size(max = 64)
    @Column(name = "EsaStatus")
    @DinaIgnor
    private String esaStatus;
    
    @Size(max = 255)
    @Column(name = "FullName")
    @DinaField(name = "full-name")
    private String fullName;
    
    @Size(max = 20)
    @Column(name = "GroupNumber")
    @DinaIgnor
    private String groupNumber;
    
    @Size(max = 128)
    @Column(name = "GUID")
    @DinaField(name = "guid")
    private String guid;
    
    @Column(name = "HighestChildNodeNumber")
    @DinaIgnor
    private Integer highestChildNodeNumber;
    
    @Column(name = "IsAccepted")
    @DinaField(name = "is-accepted")
    private Boolean isAccepted;
    
    @Column(name = "IsHybrid")
    @DinaIgnor
    private Boolean isHybrid;
    
    @Size(max = 16)
    @Column(name = "IsisNumber")
    @DinaIgnor
    private String isisNumber;
    
    @Size(max = 64)
    @Column(name = "LabelFormat")
    @DinaIgnor
    private String labelFormat;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    @DinaField(name = "name")
    private String name;
    
    @Size(max = 8)
    @Column(name = "NcbiTaxonNumber")
    @DinaIgnor
    private String ncbiTaxonNumber;
    
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
    
    @Size(max = 64)
    @Column(name = "Source")
    @DinaIgnor
    private String source;
    
    @Size(max = 50)
    @Column(name = "TaxonomicSerialNumber")
    @DinaIgnor
    private String taxonomicSerialNumber;
    
    @Size(max = 32)
    @Column(name = "Text1")
    @DinaIgnor
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    @DinaIgnor
    private String text2;
    
    @Size(max = 50)
    @Column(name = "UnitInd1")
    @DinaIgnor
    private String unitInd1;
    
    @Size(max = 50)
    @Column(name = "UnitInd2")
    @DinaIgnor
    private String unitInd2;
    
    @Size(max = 50)
    @Column(name = "UnitInd3")
    @DinaIgnor
    private String unitInd3;
    
    @Size(max = 50)
    @Column(name = "UnitInd4")
    @DinaIgnor
    private String unitInd4;
    
    @Size(max = 50)
    @Column(name = "UnitName1")
    @DinaIgnor
    private String unitName1;
    
    @Size(max = 50)
    @Column(name = "UnitName2")
    @DinaIgnor
    private String unitName2;
    
    @Size(max = 50)
    @Column(name = "UnitName3")
    @DinaIgnor
    private String unitName3;
    
    @Size(max = 50)
    @Column(name = "UnitName4")
    @DinaIgnor
    private String unitName4;
    
    @Size(max = 16)
    @Column(name = "UsfwsCode")
    @DinaIgnor
    private String usfwsCode;
    
    @Column(name = "Visibility")
    @DinaIgnor
    private Short visibility;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number3")
    @DinaIgnor
    private Float number3;
    
    @Column(name = "Number4")
    @DinaIgnor
    private Float number4;
    
    @Column(name = "Number5")
    @DinaIgnor
    private Float number5;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text3")
    @DinaIgnor
    private String text3;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text4")
    @DinaIgnor
    private String text4;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text5")
    @DinaIgnor
    private String text5;
    
    @Column(name = "YesNo1")
    @DinaIgnor
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    @DinaIgnor
    private Boolean yesNo2;
    
    @Column(name = "YesNo3")
    @DinaIgnor
    private Boolean yesNo3;
    
    @OneToMany(mappedBy = "taxonID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Determination> determinationList;
    
    @OneToMany(mappedBy = "preferredTaxonID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Determination> determinationList1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Taxonattachment> taxonattachmentList;
    
    @OneToMany(mappedBy = "acceptedID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Taxon> taxonList;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "TaxonID")
    @ManyToOne
    @DinaManyToOne(name = "accepted-id", type = "taxon")
    private Taxon acceptedID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "hybridParent1ID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Taxon> taxonList1;
    
    @JoinColumn(name = "HybridParent1ID", referencedColumnName = "TaxonID")
    @ManyToOne
    @DinaIgnor
    private Taxon hybridParent1ID;
    
    @OneToMany(mappedBy = "hybridParent2ID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Taxon> taxonList2;
    
    @JoinColumn(name = "HybridParent2ID", referencedColumnName = "TaxonID")
    @ManyToOne
    @DinaIgnor
    private Taxon hybridParent2ID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @DinaIgnor
    private Agent createdByAgentID;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    @DinaIgnor
    private Specifyuser visibilitySetByID;
    
    @JoinColumn(name = "TaxonTreeDefItemID", referencedColumnName = "TaxonTreeDefItemID")
    @ManyToOne(optional = false)
    @DinaManyToOne(name = "taxon-tree-def-item", type = "taxonTreeDefItem")
    private Taxontreedefitem taxonTreeDefItemID;
    
    @OneToMany(mappedBy = "parentID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Taxon> taxonList3;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "TaxonID")
    @ManyToOne
    @DinaManyToOne(name = "parent", type = "taxon")
    private Taxon parentID;
    
    @JoinColumn(name = "TaxonTreeDefID", referencedColumnName = "TaxonTreeDefID")
    @ManyToOne(optional = false)
    @DinaManyToOne(name = "taxon-tree-def", type = "taxonTreeDef")
    private Taxontreedef taxonTreeDefID;
    
    @OneToMany(mappedBy = "hostTaxonID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Collectingeventattribute> collectingeventattributeList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonID", fetch = FetchType.LAZY)
    @DinaIgnor
    private List<Taxoncitation> taxoncitationList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonID", fetch = FetchType.LAZY)
    @DinaOneToMany(name = "commonnnametxs", type = "commonnametx")
    private List<Commonnametx> commonnametxList;

    public Taxon() {
    }

    public Taxon(Integer taxonID) {
        this.taxonID = taxonID;
    }

    public Taxon(Integer taxonID, Date timestampCreated, String name, int rankID) {
        this.taxonID = taxonID;
        this.timestampCreated = timestampCreated;
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(Integer taxonID) {
        this.taxonID = taxonID;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return String.valueOf(taxonID);
    }
    
//    @XmlAttribute(name = "uuid") 
//    @Override
//    public String getUUID() {
//        return Util.getInstance().getURLLink(this.getClass().getSimpleName()) + taxonID;
//    }
    
    @Override
    @JsonProperty("entity-id")
    public int getEntityId() {
        return taxonID;
    }
 
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("cites-status")
    public String getCitesStatus() {
        return citesStatus;
    }

    public void setCitesStatus(String citesStatus) {
        this.citesStatus = citesStatus;
    }

    @JsonProperty("c-ol-status")
    public String getCOLStatus() {
        return cOLStatus;
    }

    public void setCOLStatus(String cOLStatus) {
        this.cOLStatus = cOLStatus;
    }

    @JsonProperty("common-name")
    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @JsonProperty("cultivar-name")
    public String getCultivarName() {
        return cultivarName;
    }

    public void setCultivarName(String cultivarName) {
        this.cultivarName = cultivarName;
    }

    @JsonProperty("environmental-protection-status")
    public String getEnvironmentalProtectionStatus() {
        return environmentalProtectionStatus;
    }

    public void setEnvironmentalProtectionStatus(String environmentalProtectionStatus) {
        this.environmentalProtectionStatus = environmentalProtectionStatus;
    }

    @JsonProperty("esa-status")
    public String getEsaStatus() {
        return esaStatus;
    }

    public void setEsaStatus(String esaStatus) {
        this.esaStatus = esaStatus;
    }

    @JsonProperty("full-name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("group-number")
    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    @JsonProperty("is-hybrid")
    public Boolean getIsHybrid() {
        return isHybrid;
    }

    public void setIsHybrid(Boolean isHybrid) {
        this.isHybrid = isHybrid;
    }

    @JsonProperty("is-number")
    public String getIsisNumber() {
        return isisNumber;
    }

    public void setIsisNumber(String isisNumber) {
        this.isisNumber = isisNumber;
    }

    @JsonProperty("label-format")
    public String getLabelFormat() {
        return labelFormat;
    }

    public void setLabelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("ncbi-taxon-number")
    public String getNcbiTaxonNumber() {
        return ncbiTaxonNumber;
    }

    public void setNcbiTaxonNumber(String ncbiTaxonNumber) {
        this.ncbiTaxonNumber = ncbiTaxonNumber;
    }

    @JsonProperty("node-number")
    public Integer getNodeNumber() {
        return nodeNumber;
    }

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("taxonomic-serial-number")
    public String getTaxonomicSerialNumber() {
        return taxonomicSerialNumber;
    }

    public void setTaxonomicSerialNumber(String taxonomicSerialNumber) {
        this.taxonomicSerialNumber = taxonomicSerialNumber;
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

    @JsonProperty("unit-ind1")
    public String getUnitInd1() {
        return unitInd1;
    }

    public void setUnitInd1(String unitInd1) {
        this.unitInd1 = unitInd1;
    }

    @JsonProperty("unit-ind2")
    public String getUnitInd2() {
        return unitInd2;
    }

    public void setUnitInd2(String unitInd2) {
        this.unitInd2 = unitInd2;
    }

    @JsonProperty("unit-ind3")
    public String getUnitInd3() {
        return unitInd3;
    }

    public void setUnitInd3(String unitInd3) {
        this.unitInd3 = unitInd3;
    }

    @JsonProperty("unit-ind4")
    public String getUnitInd4() {
        return unitInd4;
    }

    public void setUnitInd4(String unitInd4) {
        this.unitInd4 = unitInd4;
    }

    @JsonProperty("unit-name1")
    public String getUnitName1() {
        return unitName1;
    }

    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    @JsonProperty("unit-name2")
    public String getUnitName2() {
        return unitName2;
    }

    public void setUnitName2(String unitName2) {
        this.unitName2 = unitName2;
    }

    @JsonProperty("unit-name3")
    public String getUnitName3() {
        return unitName3;
    }

    public void setUnitName3(String unitName3) {
        this.unitName3 = unitName3;
    }

    @JsonProperty("unit-name4")
    public String getUnitName4() {
        return unitName4;
    }

    public void setUnitName4(String unitName4) {
        this.unitName4 = unitName4;
    }

    @JsonProperty("usfws-code")
    public String getUsfwsCode() {
        return usfwsCode;
    }

    public void setUsfwsCode(String usfwsCode) {
        this.usfwsCode = usfwsCode;
    }

    public Short getVisibility() {
        return visibility;
    }

    public void setVisibility(Short visibility) {
        this.visibility = visibility;
    }

    public Float getNumber3() {
        return number3;
    }

    public void setNumber3(Float number3) {
        this.number3 = number3;
    }

    public Float getNumber4() {
        return number4;
    }

    public void setNumber4(Float number4) {
        this.number4 = number4;
    }

    public Float getNumber5() {
        return number5;
    }

    public void setNumber5(Float number5) {
        this.number5 = number5;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
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

    @JsonProperty("yes-no3")
    public Boolean getYesNo3() {
        return yesNo3;
    }

    public void setYesNo3(Boolean yesNo3) {
        this.yesNo3 = yesNo3;
    }

    @XmlTransient
    public List<Determination> getDeterminationList() {
        return determinationList;
    }

    public void setDeterminationList(List<Determination> determinationList) {
        this.determinationList = determinationList;
    }

    @XmlTransient
    public List<Determination> getDeterminationList1() {
        return determinationList1;
    }

    public void setDeterminationList1(List<Determination> determinationList1) {
        this.determinationList1 = determinationList1;
    }

    @XmlTransient
    public List<Taxonattachment> getTaxonattachmentList() {
        return taxonattachmentList;
    }

    public void setTaxonattachmentList(List<Taxonattachment> taxonattachmentList) {
        this.taxonattachmentList = taxonattachmentList;
    }

    @XmlTransient
    public List<Taxon> getTaxonList() {
        return taxonList;
    }

    public void setTaxonList(List<Taxon> taxonList) {
        this.taxonList = taxonList;
    }

    @XmlIDREF
    @JsonProperty("accepted-id")
    public Taxon getAcceptedID() {
        return acceptedID;
    }

    public void setAcceptedID(Taxon acceptedID) {
        this.acceptedID = acceptedID;
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
    public List<Taxon> getTaxonList1() {
        return taxonList1;
    }

    public void setTaxonList1(List<Taxon> taxonList1) {
        this.taxonList1 = taxonList1;
    }

    @XmlIDREF
    @JsonProperty("hybrid-parent1-id")
    public Taxon getHybridParent1ID() {
        return hybridParent1ID;
    }

    public void setHybridParent1ID(Taxon hybridParent1ID) {
        this.hybridParent1ID = hybridParent1ID;
    }

    @XmlTransient
    public List<Taxon> getTaxonList2() {
        return taxonList2;
    }

    public void setTaxonList2(List<Taxon> taxonList2) {
        this.taxonList2 = taxonList2;
    }

    @XmlIDREF
    @JsonProperty("hybrid-parent2-id")
    public Taxon getHybridParent2ID() {
        return hybridParent2ID;
    }

    public void setHybridParent2ID(Taxon hybridParent2ID) {
        this.hybridParent2ID = hybridParent2ID;
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

    @XmlIDREF
    @JsonProperty("taxon-tree-def-item-id")
    public Taxontreedefitem getTaxonTreeDefItemID() {
        return taxonTreeDefItemID;
    }

    public void setTaxonTreeDefItemID(Taxontreedefitem taxonTreeDefItemID) {
        this.taxonTreeDefItemID = taxonTreeDefItemID;
    }

    @XmlTransient
    public List<Taxon> getTaxonList3() {
        return taxonList3;
    }

    public void setTaxonList3(List<Taxon> taxonList3) {
        this.taxonList3 = taxonList3;
    }

    @XmlIDREF
    @JsonProperty("parent-id")
    public Taxon getParentID() {
        return parentID;
    }

    public void setParentID(Taxon parentID) {
        this.parentID = parentID;
    }

    @XmlIDREF
    @JsonProperty("taxon-tree-def-id")
    public Taxontreedef getTaxonTreeDefID() {
        return taxonTreeDefID;
    }

    public void setTaxonTreeDefID(Taxontreedef taxonTreeDefID) {
        this.taxonTreeDefID = taxonTreeDefID;
    }

    @XmlTransient
    public List<Collectingeventattribute> getCollectingeventattributeList() {
        return collectingeventattributeList;
    }

    public void setCollectingeventattributeList(List<Collectingeventattribute> collectingeventattributeList) {
        this.collectingeventattributeList = collectingeventattributeList;
    }

    @XmlTransient
    public List<Taxoncitation> getTaxoncitationList() {
        return taxoncitationList;
    }

    public void setTaxoncitationList(List<Taxoncitation> taxoncitationList) {
        this.taxoncitationList = taxoncitationList;
    }

    @XmlTransient
    public List<Commonnametx> getCommonnametxList() {
        return commonnametxList;
    }

    public void setCommonnametxList(List<Commonnametx> commonnametxList) {
        this.commonnametxList = commonnametxList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonID != null ? taxonID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxon)) {
            return false;
        }
        Taxon other = (Taxon) object;
        return !((this.taxonID == null && other.taxonID != null) || (this.taxonID != null && !this.taxonID.equals(other.taxonID)));
    }

    @Override
    public String toString() {
        return "se.nrm.dina.datamodel.Taxon[ taxonID=" + taxonID + " ]";
    } 
}
