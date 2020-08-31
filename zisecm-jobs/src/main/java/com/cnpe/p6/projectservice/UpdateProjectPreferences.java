
package com.cnpe.p6.projectservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>UpdateProjectPreferences complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="UpdateProjectPreferences">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EnablePublication" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PublicationPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LastPublishedOn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PublishLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeploymentNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeleteDeployments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnifierEnabledFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnifierProjectNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnifierProjectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnifierScheduleSheetName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnifierDataMappingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnifierDeleteActivitiesFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnifierCBSTasksOnlyFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ProjectScheduleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SyncWbsHierarchyFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ScheduleWBSHierarchyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WBSHierarchyLevels" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="EnableSummarization" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SummaryLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SummarizeToWBSLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LastSummarizedDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HistoryLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HistoryInterval" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateProjectPreferences", propOrder = {
    "objectId",
    "enablePublication",
    "publicationPriority",
    "lastPublishedOn",
    "publishLevel",
    "deploymentNames",
    "deleteDeployments",
    "unifierEnabledFlag",
    "unifierProjectNumber",
    "unifierProjectName",
    "unifierScheduleSheetName",
    "unifierDataMappingName",
    "unifierDeleteActivitiesFlag",
    "unifierCBSTasksOnlyFlag",
    "projectScheduleType",
    "syncWbsHierarchyFlag",
    "scheduleWBSHierarchyType",
    "wbsHierarchyLevels",
    "enableSummarization",
    "summaryLevel",
    "summarizeToWBSLevel",
    "lastSummarizedDate",
    "historyLevel",
    "historyInterval"
})
public class UpdateProjectPreferences {

    @XmlElement(name = "ObjectId")
    protected int objectId;
    @XmlElement(name = "EnablePublication")
    protected Boolean enablePublication;
    @XmlElement(name = "PublicationPriority")
    protected Integer publicationPriority;
    @XmlElement(name = "LastPublishedOn")
    protected String lastPublishedOn;
    @XmlElement(name = "PublishLevel")
    protected String publishLevel;
    @XmlElement(name = "DeploymentNames")
    protected String deploymentNames;
    @XmlElement(name = "DeleteDeployments")
    protected String deleteDeployments;
    @XmlElement(name = "UnifierEnabledFlag")
    protected Boolean unifierEnabledFlag;
    @XmlElement(name = "UnifierProjectNumber")
    protected String unifierProjectNumber;
    @XmlElement(name = "UnifierProjectName")
    protected String unifierProjectName;
    @XmlElement(name = "UnifierScheduleSheetName")
    protected String unifierScheduleSheetName;
    @XmlElement(name = "UnifierDataMappingName")
    protected String unifierDataMappingName;
    @XmlElement(name = "UnifierDeleteActivitiesFlag")
    protected Boolean unifierDeleteActivitiesFlag;
    @XmlElement(name = "UnifierCBSTasksOnlyFlag")
    protected Boolean unifierCBSTasksOnlyFlag;
    @XmlElement(name = "ProjectScheduleType")
    protected String projectScheduleType;
    @XmlElement(name = "SyncWbsHierarchyFlag")
    protected Boolean syncWbsHierarchyFlag;
    @XmlElement(name = "ScheduleWBSHierarchyType")
    protected String scheduleWBSHierarchyType;
    @XmlElement(name = "WBSHierarchyLevels")
    protected Integer wbsHierarchyLevels;
    @XmlElement(name = "EnableSummarization")
    protected Boolean enableSummarization;
    @XmlElement(name = "SummaryLevel")
    protected String summaryLevel;
    @XmlElement(name = "SummarizeToWBSLevel")
    protected Integer summarizeToWBSLevel;
    @XmlElement(name = "LastSummarizedDate")
    protected String lastSummarizedDate;
    @XmlElement(name = "HistoryLevel")
    protected String historyLevel;
    @XmlElement(name = "HistoryInterval")
    protected String historyInterval;

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
     * 获取enablePublication属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnablePublication() {
        return enablePublication;
    }

    /**
     * 设置enablePublication属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnablePublication(Boolean value) {
        this.enablePublication = value;
    }

    /**
     * 获取publicationPriority属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPublicationPriority() {
        return publicationPriority;
    }

    /**
     * 设置publicationPriority属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPublicationPriority(Integer value) {
        this.publicationPriority = value;
    }

    /**
     * 获取lastPublishedOn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastPublishedOn() {
        return lastPublishedOn;
    }

    /**
     * 设置lastPublishedOn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastPublishedOn(String value) {
        this.lastPublishedOn = value;
    }

    /**
     * 获取publishLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublishLevel() {
        return publishLevel;
    }

    /**
     * 设置publishLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublishLevel(String value) {
        this.publishLevel = value;
    }

    /**
     * 获取deploymentNames属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeploymentNames() {
        return deploymentNames;
    }

    /**
     * 设置deploymentNames属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeploymentNames(String value) {
        this.deploymentNames = value;
    }

    /**
     * 获取deleteDeployments属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteDeployments() {
        return deleteDeployments;
    }

    /**
     * 设置deleteDeployments属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteDeployments(String value) {
        this.deleteDeployments = value;
    }

    /**
     * 获取unifierEnabledFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnifierEnabledFlag() {
        return unifierEnabledFlag;
    }

    /**
     * 设置unifierEnabledFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnifierEnabledFlag(Boolean value) {
        this.unifierEnabledFlag = value;
    }

    /**
     * 获取unifierProjectNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierProjectNumber() {
        return unifierProjectNumber;
    }

    /**
     * 设置unifierProjectNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierProjectNumber(String value) {
        this.unifierProjectNumber = value;
    }

    /**
     * 获取unifierProjectName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierProjectName() {
        return unifierProjectName;
    }

    /**
     * 设置unifierProjectName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierProjectName(String value) {
        this.unifierProjectName = value;
    }

    /**
     * 获取unifierScheduleSheetName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierScheduleSheetName() {
        return unifierScheduleSheetName;
    }

    /**
     * 设置unifierScheduleSheetName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierScheduleSheetName(String value) {
        this.unifierScheduleSheetName = value;
    }

    /**
     * 获取unifierDataMappingName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierDataMappingName() {
        return unifierDataMappingName;
    }

    /**
     * 设置unifierDataMappingName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierDataMappingName(String value) {
        this.unifierDataMappingName = value;
    }

    /**
     * 获取unifierDeleteActivitiesFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnifierDeleteActivitiesFlag() {
        return unifierDeleteActivitiesFlag;
    }

    /**
     * 设置unifierDeleteActivitiesFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnifierDeleteActivitiesFlag(Boolean value) {
        this.unifierDeleteActivitiesFlag = value;
    }

    /**
     * 获取unifierCBSTasksOnlyFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUnifierCBSTasksOnlyFlag() {
        return unifierCBSTasksOnlyFlag;
    }

    /**
     * 设置unifierCBSTasksOnlyFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUnifierCBSTasksOnlyFlag(Boolean value) {
        this.unifierCBSTasksOnlyFlag = value;
    }

    /**
     * 获取projectScheduleType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectScheduleType() {
        return projectScheduleType;
    }

    /**
     * 设置projectScheduleType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectScheduleType(String value) {
        this.projectScheduleType = value;
    }

    /**
     * 获取syncWbsHierarchyFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSyncWbsHierarchyFlag() {
        return syncWbsHierarchyFlag;
    }

    /**
     * 设置syncWbsHierarchyFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSyncWbsHierarchyFlag(Boolean value) {
        this.syncWbsHierarchyFlag = value;
    }

    /**
     * 获取scheduleWBSHierarchyType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleWBSHierarchyType() {
        return scheduleWBSHierarchyType;
    }

    /**
     * 设置scheduleWBSHierarchyType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleWBSHierarchyType(String value) {
        this.scheduleWBSHierarchyType = value;
    }

    /**
     * 获取wbsHierarchyLevels属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWBSHierarchyLevels() {
        return wbsHierarchyLevels;
    }

    /**
     * 设置wbsHierarchyLevels属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWBSHierarchyLevels(Integer value) {
        this.wbsHierarchyLevels = value;
    }

    /**
     * 获取enableSummarization属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableSummarization() {
        return enableSummarization;
    }

    /**
     * 设置enableSummarization属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableSummarization(Boolean value) {
        this.enableSummarization = value;
    }

    /**
     * 获取summaryLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryLevel() {
        return summaryLevel;
    }

    /**
     * 设置summaryLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryLevel(String value) {
        this.summaryLevel = value;
    }

    /**
     * 获取summarizeToWBSLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSummarizeToWBSLevel() {
        return summarizeToWBSLevel;
    }

    /**
     * 设置summarizeToWBSLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSummarizeToWBSLevel(Integer value) {
        this.summarizeToWBSLevel = value;
    }

    /**
     * 获取lastSummarizedDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastSummarizedDate() {
        return lastSummarizedDate;
    }

    /**
     * 设置lastSummarizedDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastSummarizedDate(String value) {
        this.lastSummarizedDate = value;
    }

    /**
     * 获取historyLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryLevel() {
        return historyLevel;
    }

    /**
     * 设置historyLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryLevel(String value) {
        this.historyLevel = value;
    }

    /**
     * 获取historyInterval属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryInterval() {
        return historyInterval;
    }

    /**
     * 设置historyInterval属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryInterval(String value) {
        this.historyInterval = value;
    }

}
