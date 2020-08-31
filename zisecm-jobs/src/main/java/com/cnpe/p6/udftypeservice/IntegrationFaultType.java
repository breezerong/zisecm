
package com.cnpe.p6.udftypeservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>IntegrationFaultType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="IntegrationFaultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorType" type="{http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1}IntegrationFaultCodeType"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ErrorDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StackTrace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntegrationFaultType", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", propOrder = {
    "errorType",
    "errorCode",
    "errorDescription",
    "stackTrace"
})
public class IntegrationFaultType {

    @XmlElement(name = "ErrorType", required = true)
    @XmlSchemaType(name = "string")
    protected IntegrationFaultCodeType errorType;
    @XmlElement(name = "ErrorCode")
    protected int errorCode;
    @XmlElement(name = "ErrorDescription")
    protected String errorDescription;
    @XmlElement(name = "StackTrace")
    protected String stackTrace;

    /**
     * 获取errorType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link IntegrationFaultCodeType }
     *     
     */
    public IntegrationFaultCodeType getErrorType() {
        return errorType;
    }

    /**
     * 设置errorType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link IntegrationFaultCodeType }
     *     
     */
    public void setErrorType(IntegrationFaultCodeType value) {
        this.errorType = value;
    }

    /**
     * 获取errorCode属性的值。
     * 
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 设置errorCode属性的值。
     * 
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    /**
     * 获取errorDescription属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * 设置errorDescription属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

    /**
     * 获取stackTrace属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * 设置stackTrace属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStackTrace(String value) {
        this.stackTrace = value;
    }

}
