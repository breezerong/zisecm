
package com.cnpe.p6.udfvalueservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>ReadCalculatedUDFValuesResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ReadCalculatedUDFValuesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CalculatedUDFValue" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ConditionalIndicator" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="Cost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="Double" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="FinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="Integer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="ProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="CalculatedUDFTypeDataType">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Start Date"/>
 *                         &lt;enumeration value="Finish Date"/>
 *                         &lt;enumeration value="Cost"/>
 *                         &lt;enumeration value="Double"/>
 *                         &lt;enumeration value="Integer"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="UDFTypeObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "ReadCalculatedUDFValuesResponse", propOrder = {
    "calculatedUDFValue"
})
public class ReadCalculatedUDFValuesResponse {

    @XmlElement(name = "CalculatedUDFValue")
    protected List<ReadCalculatedUDFValuesResponse.CalculatedUDFValue> calculatedUDFValue;

    /**
     * Gets the value of the calculatedUDFValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the calculatedUDFValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCalculatedUDFValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReadCalculatedUDFValuesResponse.CalculatedUDFValue }
     * 
     * 
     */
    public List<ReadCalculatedUDFValuesResponse.CalculatedUDFValue> getCalculatedUDFValue() {
        if (calculatedUDFValue == null) {
            calculatedUDFValue = new ArrayList<ReadCalculatedUDFValuesResponse.CalculatedUDFValue>();
        }
        return this.calculatedUDFValue;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ConditionalIndicator" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="Cost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="Double" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="FinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="Integer" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="ProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="CalculatedUDFTypeDataType">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Start Date"/>
     *               &lt;enumeration value="Finish Date"/>
     *               &lt;enumeration value="Cost"/>
     *               &lt;enumeration value="Double"/>
     *               &lt;enumeration value="Integer"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="UDFTypeObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "conditionalIndicator",
        "cost",
        "_double",
        "finishDate",
        "integer",
        "projectObjectId",
        "startDate",
        "calculatedUDFTypeDataType",
        "udfTypeObjectId"
    })
    public static class CalculatedUDFValue {

        @XmlElementRef(name = "ConditionalIndicator", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
        protected JAXBElement<Integer> conditionalIndicator;
        @XmlElementRef(name = "Cost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
        protected JAXBElement<Double> cost;
        @XmlElementRef(name = "Double", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
        protected JAXBElement<Double> _double;
        @XmlElementRef(name = "FinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
        protected JAXBElement<XMLGregorianCalendar> finishDate;
        @XmlElementRef(name = "Integer", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
        protected JAXBElement<Integer> integer;
        @XmlElement(name = "ProjectObjectId")
        protected int projectObjectId;
        @XmlElementRef(name = "StartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", type = JAXBElement.class)
        protected JAXBElement<XMLGregorianCalendar> startDate;
        @XmlElement(name = "CalculatedUDFTypeDataType", required = true)
        protected String calculatedUDFTypeDataType;
        @XmlElement(name = "UDFTypeObjectId")
        protected int udfTypeObjectId;

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
         * 获取projectObjectId属性的值。
         * 
         */
        public int getProjectObjectId() {
            return projectObjectId;
        }

        /**
         * 设置projectObjectId属性的值。
         * 
         */
        public void setProjectObjectId(int value) {
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
         * 获取calculatedUDFTypeDataType属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCalculatedUDFTypeDataType() {
            return calculatedUDFTypeDataType;
        }

        /**
         * 设置calculatedUDFTypeDataType属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCalculatedUDFTypeDataType(String value) {
            this.calculatedUDFTypeDataType = value;
        }

        /**
         * 获取udfTypeObjectId属性的值。
         * 
         */
        public int getUDFTypeObjectId() {
            return udfTypeObjectId;
        }

        /**
         * 设置udfTypeObjectId属性的值。
         * 
         */
        public void setUDFTypeObjectId(int value) {
            this.udfTypeObjectId = value;
        }

    }

}
