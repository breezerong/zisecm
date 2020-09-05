
package com.cnpe.p6.udfvalueservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>UDFValue complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="UDFValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodeValue" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="60"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ConditionalIndicator" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Cost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Description" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Double" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="-1.0E12"/>
 *               &lt;maxInclusive value="1.0E12"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ForeignObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Indicator" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="None"/>
 *               &lt;enumeration value="Red"/>
 *               &lt;enumeration value="Yellow"/>
 *               &lt;enumeration value="Green"/>
 *               &lt;enumeration value="Blue"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Integer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IsBaseline" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsTemplate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsUDFTypeCalculated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsUDFTypeConditional" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastUpdateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Text" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UDFCodeObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="UDFTypeDataType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Text"/>
 *               &lt;enumeration value="Start Date"/>
 *               &lt;enumeration value="Finish Date"/>
 *               &lt;enumeration value="Cost"/>
 *               &lt;enumeration value="Double"/>
 *               &lt;enumeration value="Integer"/>
 *               &lt;enumeration value="Indicator"/>
 *               &lt;enumeration value="Code"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UDFTypeObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="UDFTypeSubjectArea" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Activity"/>
 *               &lt;enumeration value="Activity Expense"/>
 *               &lt;enumeration value="Activity Step"/>
 *               &lt;enumeration value="Project"/>
 *               &lt;enumeration value="Project Issue"/>
 *               &lt;enumeration value="Project Risk"/>
 *               &lt;enumeration value="Resource"/>
 *               &lt;enumeration value="Resource Assignment"/>
 *               &lt;enumeration value="WBS"/>
 *               &lt;enumeration value="Work Products and Documents"/>
 *               &lt;enumeration value="Activity Step Template Item"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UDFTypeTitle" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UDFValue", propOrder = {
    "codeValue",
    "conditionalIndicator",
    "cost",
    "createDate",
    "createUser",
    "description",
    "_double",
    "finishDate",
    "foreignObjectId",
    "indicator",
    "integer",
    "isBaseline",
    "isTemplate",
    "isUDFTypeCalculated",
    "isUDFTypeConditional",
    "lastUpdateDate",
    "lastUpdateUser",
    "projectObjectId",
    "startDate",
    "text",
    "udfCodeObjectId",
    "udfTypeDataType",
    "udfTypeObjectId",
    "udfTypeSubjectArea",
    "udfTypeTitle"
})
public class UDFValue {

    @XmlElement(name = "CodeValue")
    protected String codeValue;
    @XmlElementRef(name = "ConditionalIndicator", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> conditionalIndicator;
    @XmlElementRef(name = "Cost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Double> cost;
    @XmlElementRef(name = "CreateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> createDate;
    @XmlElement(name = "CreateUser")
    protected String createUser;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElementRef(name = "Double", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Double> _double;
    @XmlElementRef(name = "FinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> finishDate;
    @XmlElement(name = "ForeignObjectId")
    protected Integer foreignObjectId;
    @XmlElement(name = "Indicator")
    protected String indicator;
    @XmlElementRef(name = "Integer", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> integer;
    @XmlElementRef(name = "IsBaseline", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isBaseline;
    @XmlElement(name = "IsTemplate")
    protected Boolean isTemplate;
    @XmlElementRef(name = "IsUDFTypeCalculated", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isUDFTypeCalculated;
    @XmlElementRef(name = "IsUDFTypeConditional", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isUDFTypeConditional;
    @XmlElementRef(name = "LastUpdateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastUpdateDate;
    @XmlElement(name = "LastUpdateUser")
    protected String lastUpdateUser;
    @XmlElementRef(name = "ProjectObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> projectObjectId;
    @XmlElementRef(name = "StartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> startDate;
    @XmlElement(name = "Text")
    protected String text;
    @XmlElementRef(name = "UDFCodeObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> udfCodeObjectId;
    @XmlElement(name = "UDFTypeDataType")
    protected String udfTypeDataType;
    @XmlElement(name = "UDFTypeObjectId")
    protected Integer udfTypeObjectId;
    @XmlElement(name = "UDFTypeSubjectArea")
    protected String udfTypeSubjectArea;
    @XmlElement(name = "UDFTypeTitle")
    protected String udfTypeTitle;

    /**
     * 获取codeValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeValue() {
        return codeValue;
    }

    /**
     * 设置codeValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeValue(String value) {
        this.codeValue = value;
    }

    /**
     * 获取conditionalIndicator属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getConditionalIndicator() {
        return conditionalIndicator;
    }

    /**
     * 设置conditionalIndicator属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setConditionalIndicator(JAXBElement<Integer> value) {
        this.conditionalIndicator = value;
    }

    /**
     * 获取cost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCost() {
        return cost;
    }

    /**
     * 设置cost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCost(JAXBElement<Double> value) {
        this.cost = value;
    }

    /**
     * 获取createDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getCreateDate() {
        return createDate;
    }

    /**
     * 设置createDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setCreateDate(JAXBElement<XMLGregorianCalendar> value) {
        this.createDate = value;
    }

    /**
     * 获取createUser属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置createUser属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateUser(String value) {
        this.createUser = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取double属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getDouble() {
        return _double;
    }

    /**
     * 设置double属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setDouble(JAXBElement<Double> value) {
        this._double = value;
    }

    /**
     * 获取finishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFinishDate() {
        return finishDate;
    }

    /**
     * 设置finishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.finishDate = value;
    }

    /**
     * 获取foreignObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getForeignObjectId() {
        return foreignObjectId;
    }

    /**
     * 设置foreignObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setForeignObjectId(Integer value) {
        this.foreignObjectId = value;
    }

    /**
     * 获取indicator属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicator() {
        return indicator;
    }

    /**
     * 设置indicator属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicator(String value) {
        this.indicator = value;
    }

    /**
     * 获取integer属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getInteger() {
        return integer;
    }

    /**
     * 设置integer属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setInteger(JAXBElement<Integer> value) {
        this.integer = value;
    }

    /**
     * 获取isBaseline属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsBaseline() {
        return isBaseline;
    }

    /**
     * 设置isBaseline属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsBaseline(JAXBElement<Boolean> value) {
        this.isBaseline = value;
    }

    /**
     * 获取isTemplate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTemplate() {
        return isTemplate;
    }

    /**
     * 设置isTemplate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTemplate(Boolean value) {
        this.isTemplate = value;
    }

    /**
     * 获取isUDFTypeCalculated属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsUDFTypeCalculated() {
        return isUDFTypeCalculated;
    }

    /**
     * 设置isUDFTypeCalculated属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsUDFTypeCalculated(JAXBElement<Boolean> value) {
        this.isUDFTypeCalculated = value;
    }

    /**
     * 获取isUDFTypeConditional属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsUDFTypeConditional() {
        return isUDFTypeConditional;
    }

    /**
     * 设置isUDFTypeConditional属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsUDFTypeConditional(JAXBElement<Boolean> value) {
        this.isUDFTypeConditional = value;
    }

    /**
     * 获取lastUpdateDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * 设置lastUpdateDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLastUpdateDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lastUpdateDate = value;
    }

    /**
     * 获取lastUpdateUser属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * 设置lastUpdateUser属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdateUser(String value) {
        this.lastUpdateUser = value;
    }

    /**
     * 获取projectObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getProjectObjectId() {
        return projectObjectId;
    }

    /**
     * 设置projectObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setProjectObjectId(JAXBElement<Integer> value) {
        this.projectObjectId = value;
    }

    /**
     * 获取startDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getStartDate() {
        return startDate;
    }

    /**
     * 设置startDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.startDate = value;
    }

    /**
     * 获取text属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * 设置text属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * 获取udfCodeObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getUDFCodeObjectId() {
        return udfCodeObjectId;
    }

    /**
     * 设置udfCodeObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setUDFCodeObjectId(JAXBElement<Integer> value) {
        this.udfCodeObjectId = value;
    }

    /**
     * 获取udfTypeDataType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUDFTypeDataType() {
        return udfTypeDataType;
    }

    /**
     * 设置udfTypeDataType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUDFTypeDataType(String value) {
        this.udfTypeDataType = value;
    }

    /**
     * 获取udfTypeObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUDFTypeObjectId() {
        return udfTypeObjectId;
    }

    /**
     * 设置udfTypeObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUDFTypeObjectId(Integer value) {
        this.udfTypeObjectId = value;
    }

    /**
     * 获取udfTypeSubjectArea属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUDFTypeSubjectArea() {
        return udfTypeSubjectArea;
    }

    /**
     * 设置udfTypeSubjectArea属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUDFTypeSubjectArea(String value) {
        this.udfTypeSubjectArea = value;
    }

    /**
     * 获取udfTypeTitle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUDFTypeTitle() {
        return udfTypeTitle;
    }

    /**
     * 设置udfTypeTitle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUDFTypeTitle(String value) {
        this.udfTypeTitle = value;
    }

}
