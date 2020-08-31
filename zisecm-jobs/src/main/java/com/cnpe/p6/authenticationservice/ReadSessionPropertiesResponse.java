
package com.cnpe.p6.authenticationservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="IsValid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="UserObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DatabaseInstanceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DatabaseEncoding" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DatabaseName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DatabaseType" type="{http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1}DatabaseTypeType"/>
 *         &lt;element name="DatabaseUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IgnoreNullComplexFields" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "isValid",
    "userObjectId",
    "userName",
    "databaseInstanceId",
    "databaseEncoding",
    "databaseName",
    "databaseType",
    "databaseUrl",
    "ignoreNullComplexFields"
})
@XmlRootElement(name = "ReadSessionPropertiesResponse")
public class ReadSessionPropertiesResponse {

    @XmlElement(name = "IsValid")
    protected boolean isValid;
    @XmlElement(name = "UserObjectId")
    protected int userObjectId;
    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "DatabaseInstanceId")
    protected int databaseInstanceId;
    @XmlElement(name = "DatabaseEncoding", required = true)
    protected String databaseEncoding;
    @XmlElement(name = "DatabaseName", required = true)
    protected String databaseName;
    @XmlElement(name = "DatabaseType", required = true)
    @XmlSchemaType(name = "string")
    protected DatabaseTypeType databaseType;
    @XmlElement(name = "DatabaseUrl", required = true)
    protected String databaseUrl;
    @XmlElement(name = "IgnoreNullComplexFields")
    protected boolean ignoreNullComplexFields;

    /**
     * 获取isValid属性的值。
     * 
     */
    public boolean isIsValid() {
        return isValid;
    }

    /**
     * 设置isValid属性的值。
     * 
     */
    public void setIsValid(boolean value) {
        this.isValid = value;
    }

    /**
     * 获取userObjectId属性的值。
     * 
     */
    public int getUserObjectId() {
        return userObjectId;
    }

    /**
     * 设置userObjectId属性的值。
     * 
     */
    public void setUserObjectId(int value) {
        this.userObjectId = value;
    }

    /**
     * 获取userName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * 获取databaseInstanceId属性的值。
     * 
     */
    public int getDatabaseInstanceId() {
        return databaseInstanceId;
    }

    /**
     * 设置databaseInstanceId属性的值。
     * 
     */
    public void setDatabaseInstanceId(int value) {
        this.databaseInstanceId = value;
    }

    /**
     * 获取databaseEncoding属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabaseEncoding() {
        return databaseEncoding;
    }

    /**
     * 设置databaseEncoding属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabaseEncoding(String value) {
        this.databaseEncoding = value;
    }

    /**
     * 获取databaseName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * 设置databaseName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabaseName(String value) {
        this.databaseName = value;
    }

    /**
     * 获取databaseType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DatabaseTypeType }
     *     
     */
    public DatabaseTypeType getDatabaseType() {
        return databaseType;
    }

    /**
     * 设置databaseType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DatabaseTypeType }
     *     
     */
    public void setDatabaseType(DatabaseTypeType value) {
        this.databaseType = value;
    }

    /**
     * 获取databaseUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatabaseUrl() {
        return databaseUrl;
    }

    /**
     * 设置databaseUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatabaseUrl(String value) {
        this.databaseUrl = value;
    }

    /**
     * 获取ignoreNullComplexFields属性的值。
     * 
     */
    public boolean isIgnoreNullComplexFields() {
        return ignoreNullComplexFields;
    }

    /**
     * 设置ignoreNullComplexFields属性的值。
     * 
     */
    public void setIgnoreNullComplexFields(boolean value) {
        this.ignoreNullComplexFields = value;
    }

}
