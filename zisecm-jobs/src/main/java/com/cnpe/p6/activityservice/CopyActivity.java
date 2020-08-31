
package com.cnpe.p6.activityservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CopyActivity complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CopyActivity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TargetProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TargetWBSObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TargetActivityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CopyResourceAndRoleAssignments" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyRelationships" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivityCodes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivityNotes" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivityExpenses" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyActivitySteps" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CopyProjectDocuments" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "CopyActivity", propOrder = {
    "objectId",
    "targetProjectObjectId",
    "targetWBSObjectId",
    "targetActivityId",
    "copyResourceAndRoleAssignments",
    "copyRelationships",
    "copyActivityCodes",
    "copyActivityNotes",
    "copyActivityExpenses",
    "copyActivitySteps",
    "copyProjectDocuments",
    "copyPastPeriodActuals"
})
public class CopyActivity {

    @XmlElement(name = "ObjectId")
    protected int objectId;
    @XmlElement(name = "TargetProjectObjectId")
    protected Integer targetProjectObjectId;
    @XmlElement(name = "TargetWBSObjectId")
    protected Integer targetWBSObjectId;
    @XmlElement(name = "TargetActivityId")
    protected String targetActivityId;
    @XmlElement(name = "CopyResourceAndRoleAssignments")
    protected Boolean copyResourceAndRoleAssignments;
    @XmlElement(name = "CopyRelationships")
    protected Boolean copyRelationships;
    @XmlElement(name = "CopyActivityCodes")
    protected Boolean copyActivityCodes;
    @XmlElement(name = "CopyActivityNotes")
    protected Boolean copyActivityNotes;
    @XmlElement(name = "CopyActivityExpenses")
    protected Boolean copyActivityExpenses;
    @XmlElement(name = "CopyActivitySteps")
    protected Boolean copyActivitySteps;
    @XmlElement(name = "CopyProjectDocuments")
    protected Boolean copyProjectDocuments;
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
     * 获取targetProjectObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTargetProjectObjectId() {
        return targetProjectObjectId;
    }

    /**
     * 设置targetProjectObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTargetProjectObjectId(Integer value) {
        this.targetProjectObjectId = value;
    }

    /**
     * 获取targetWBSObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTargetWBSObjectId() {
        return targetWBSObjectId;
    }

    /**
     * 设置targetWBSObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTargetWBSObjectId(Integer value) {
        this.targetWBSObjectId = value;
    }

    /**
     * 获取targetActivityId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetActivityId() {
        return targetActivityId;
    }

    /**
     * 设置targetActivityId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetActivityId(String value) {
        this.targetActivityId = value;
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
