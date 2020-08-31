
package com.cnpe.p6.projectservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CopyProject complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CopyProject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EPSObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CopyRisks" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyIssuesThresholds" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyReports" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyProjectDocuments" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyFundingSources" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopySummaryData" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyProjectNotes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyWBSMilestones" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivities" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyHighLevelResourcePlanning" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyResourceAndRoleAssignments" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyRelationships" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyOnlyBetweenCopiedActivities" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivityExpenses" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivityCodes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivityNotes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivitySteps" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyPastPeriodActuals" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CopyProject", propOrder = {
    "objectId",
    "epsObjectId",
    "copyRisks",
    "copyIssuesThresholds",
    "copyReports",
    "copyProjectDocuments",
    "copyFundingSources",
    "copySummaryData",
    "copyProjectNotes",
    "copyWBSMilestones",
    "copyActivities",
    "copyHighLevelResourcePlanning",
    "copyResourceAndRoleAssignments",
    "copyRelationships",
    "copyOnlyBetweenCopiedActivities",
    "copyActivityExpenses",
    "copyActivityCodes",
    "copyActivityNotes",
    "copyActivitySteps",
    "copyPastPeriodActuals"
})
public class CopyProject {

    @XmlElement(name = "ObjectId")
    protected int objectId;
    @XmlElement(name = "EPSObjectId")
    protected int epsObjectId;
    @XmlElement(name = "CopyRisks")
    protected Boolean copyRisks;
    @XmlElement(name = "CopyIssuesThresholds")
    protected Boolean copyIssuesThresholds;
    @XmlElement(name = "CopyReports")
    protected Boolean copyReports;
    @XmlElement(name = "CopyProjectDocuments")
    protected Boolean copyProjectDocuments;
    @XmlElement(name = "CopyFundingSources")
    protected Boolean copyFundingSources;
    @XmlElement(name = "CopySummaryData")
    protected Boolean copySummaryData;
    @XmlElement(name = "CopyProjectNotes")
    protected Boolean copyProjectNotes;
    @XmlElement(name = "CopyWBSMilestones")
    protected Boolean copyWBSMilestones;
    @XmlElement(name = "CopyActivities")
    protected Boolean copyActivities;
    @XmlElement(name = "CopyHighLevelResourcePlanning")
    protected Boolean copyHighLevelResourcePlanning;
    @XmlElement(name = "CopyResourceAndRoleAssignments")
    protected Boolean copyResourceAndRoleAssignments;
    @XmlElement(name = "CopyRelationships")
    protected Boolean copyRelationships;
    @XmlElement(name = "CopyOnlyBetweenCopiedActivities")
    protected Boolean copyOnlyBetweenCopiedActivities;
    @XmlElement(name = "CopyActivityExpenses")
    protected Boolean copyActivityExpenses;
    @XmlElement(name = "CopyActivityCodes")
    protected Boolean copyActivityCodes;
    @XmlElement(name = "CopyActivityNotes")
    protected Boolean copyActivityNotes;
    @XmlElement(name = "CopyActivitySteps")
    protected Boolean copyActivitySteps;
    @XmlElement(name = "CopyPastPeriodActuals")
    protected Boolean copyPastPeriodActuals;

    /**
     * 获取objectId属性的值。
     * 
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * 设置objectId属性的值。
     * 
     */
    public void setObjectId(int value) {
        this.objectId = value;
    }

    /**
     * 获取epsObjectId属性的值。
     * 
     */
    public int getEPSObjectId() {
        return epsObjectId;
    }

    /**
     * 设置epsObjectId属性的值。
     * 
     */
    public void setEPSObjectId(int value) {
        this.epsObjectId = value;
    }

    /**
     * 获取copyRisks属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyRisks() {
        return copyRisks;
    }

    /**
     * 设置copyRisks属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyRisks(Boolean value) {
        this.copyRisks = value;
    }

    /**
     * 获取copyIssuesThresholds属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyIssuesThresholds() {
        return copyIssuesThresholds;
    }

    /**
     * 设置copyIssuesThresholds属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyIssuesThresholds(Boolean value) {
        this.copyIssuesThresholds = value;
    }

    /**
     * 获取copyReports属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyReports() {
        return copyReports;
    }

    /**
     * 设置copyReports属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyReports(Boolean value) {
        this.copyReports = value;
    }

    /**
     * 获取copyProjectDocuments属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyProjectDocuments() {
        return copyProjectDocuments;
    }

    /**
     * 设置copyProjectDocuments属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyProjectDocuments(Boolean value) {
        this.copyProjectDocuments = value;
    }

    /**
     * 获取copyFundingSources属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyFundingSources() {
        return copyFundingSources;
    }

    /**
     * 设置copyFundingSources属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyFundingSources(Boolean value) {
        this.copyFundingSources = value;
    }

    /**
     * 获取copySummaryData属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopySummaryData() {
        return copySummaryData;
    }

    /**
     * 设置copySummaryData属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopySummaryData(Boolean value) {
        this.copySummaryData = value;
    }

    /**
     * 获取copyProjectNotes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyProjectNotes() {
        return copyProjectNotes;
    }

    /**
     * 设置copyProjectNotes属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyProjectNotes(Boolean value) {
        this.copyProjectNotes = value;
    }

    /**
     * 获取copyWBSMilestones属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyWBSMilestones() {
        return copyWBSMilestones;
    }

    /**
     * 设置copyWBSMilestones属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyWBSMilestones(Boolean value) {
        this.copyWBSMilestones = value;
    }

    /**
     * 获取copyActivities属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyActivities() {
        return copyActivities;
    }

    /**
     * 设置copyActivities属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyActivities(Boolean value) {
        this.copyActivities = value;
    }

    /**
     * 获取copyHighLevelResourcePlanning属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyHighLevelResourcePlanning() {
        return copyHighLevelResourcePlanning;
    }

    /**
     * 设置copyHighLevelResourcePlanning属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyHighLevelResourcePlanning(Boolean value) {
        this.copyHighLevelResourcePlanning = value;
    }

    /**
     * 获取copyResourceAndRoleAssignments属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyResourceAndRoleAssignments() {
        return copyResourceAndRoleAssignments;
    }

    /**
     * 设置copyResourceAndRoleAssignments属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyResourceAndRoleAssignments(Boolean value) {
        this.copyResourceAndRoleAssignments = value;
    }

    /**
     * 获取copyRelationships属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyRelationships() {
        return copyRelationships;
    }

    /**
     * 设置copyRelationships属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyRelationships(Boolean value) {
        this.copyRelationships = value;
    }

    /**
     * 获取copyOnlyBetweenCopiedActivities属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyOnlyBetweenCopiedActivities() {
        return copyOnlyBetweenCopiedActivities;
    }

    /**
     * 设置copyOnlyBetweenCopiedActivities属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyOnlyBetweenCopiedActivities(Boolean value) {
        this.copyOnlyBetweenCopiedActivities = value;
    }

    /**
     * 获取copyActivityExpenses属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyActivityExpenses() {
        return copyActivityExpenses;
    }

    /**
     * 设置copyActivityExpenses属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyActivityExpenses(Boolean value) {
        this.copyActivityExpenses = value;
    }

    /**
     * 获取copyActivityCodes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyActivityCodes() {
        return copyActivityCodes;
    }

    /**
     * 设置copyActivityCodes属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyActivityCodes(Boolean value) {
        this.copyActivityCodes = value;
    }

    /**
     * 获取copyActivityNotes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyActivityNotes() {
        return copyActivityNotes;
    }

    /**
     * 设置copyActivityNotes属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyActivityNotes(Boolean value) {
        this.copyActivityNotes = value;
    }

    /**
     * 获取copyActivitySteps属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyActivitySteps() {
        return copyActivitySteps;
    }

    /**
     * 设置copyActivitySteps属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyActivitySteps(Boolean value) {
        this.copyActivitySteps = value;
    }

    /**
     * 获取copyPastPeriodActuals属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCopyPastPeriodActuals() {
        return copyPastPeriodActuals;
    }

    /**
     * 设置copyPastPeriodActuals属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCopyPastPeriodActuals(Boolean value) {
        this.copyPastPeriodActuals = value;
    }

}
