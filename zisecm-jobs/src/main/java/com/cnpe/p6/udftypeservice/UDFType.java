
package com.cnpe.p6.udftypeservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>UDFType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="UDFType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DataType" minOccurs="0">
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
 *         &lt;element name="IsCalculated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsConditional" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsSecureCode" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastUpdateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SubjectArea" minOccurs="0">
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
 *         &lt;element name="Title" minOccurs="0">
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
@XmlType(name = "UDFType", propOrder = {
    "createDate",
    "createUser",
    "dataType",
    "isCalculated",
    "isConditional",
    "isSecureCode",
    "lastUpdateDate",
    "lastUpdateUser",
    "objectId",
    "subjectArea",
    "title"
})
public class UDFType {

    @XmlElementRef(name = "CreateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> createDate;
    @XmlElement(name = "CreateUser")
    protected String createUser;
    @XmlElement(name = "DataType")
    protected String dataType;
    @XmlElementRef(name = "IsCalculated", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isCalculated;
    @XmlElementRef(name = "IsConditional", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isConditional;
    @XmlElement(name = "IsSecureCode")
    protected Boolean isSecureCode;
    @XmlElementRef(name = "LastUpdateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastUpdateDate;
    @XmlElement(name = "LastUpdateUser")
    protected String lastUpdateUser;
    @XmlElement(name = "ObjectId")
    protected Integer objectId;
    @XmlElement(name = "SubjectArea")
    protected String subjectArea;
    @XmlElement(name = "Title")
    protected String title;

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
     * 获取dataType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * 设置dataType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * 获取isCalculated属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsCalculated() {
        return isCalculated;
    }

    /**
     * 设置isCalculated属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsCalculated(JAXBElement<Boolean> value) {
        this.isCalculated = value;
    }

    /**
     * 获取isConditional属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsConditional() {
        return isConditional;
    }

    /**
     * 设置isConditional属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsConditional(JAXBElement<Boolean> value) {
        this.isConditional = value;
    }

    /**
     * 获取isSecureCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSecureCode() {
        return isSecureCode;
    }

    /**
     * 设置isSecureCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSecureCode(Boolean value) {
        this.isSecureCode = value;
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
     * 获取objectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getObjectId() {
        return objectId;
    }

    /**
     * 设置objectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setObjectId(Integer value) {
        this.objectId = value;
    }

    /**
     * 获取subjectArea属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectArea() {
        return subjectArea;
    }

    /**
     * 设置subjectArea属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectArea(String value) {
        this.subjectArea = value;
    }

    /**
     * 获取title属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
