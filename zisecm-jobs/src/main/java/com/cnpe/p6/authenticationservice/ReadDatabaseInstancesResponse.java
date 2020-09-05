
package com.cnpe.p6.authenticationservice;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="DatabaseInstance" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DatabaseInstanceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="DatabaseEncoding" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DatabaseName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DatabaseType" type="{http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1}DatabaseTypeType"/>
 *                   &lt;element name="DatabaseUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "", propOrder = {
    "databaseInstance"
})
@XmlRootElement(name = "ReadDatabaseInstancesResponse")
public class ReadDatabaseInstancesResponse {

    @XmlElement(name = "DatabaseInstance", required = true)
    protected List<ReadDatabaseInstancesResponse.DatabaseInstance> databaseInstance;

    /**
     * Gets the value of the databaseInstance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the databaseInstance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatabaseInstance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReadDatabaseInstancesResponse.DatabaseInstance }
     * 
     * 
     */
    public List<ReadDatabaseInstancesResponse.DatabaseInstance> getDatabaseInstance() {
        if (databaseInstance == null) {
            databaseInstance = new ArrayList<ReadDatabaseInstancesResponse.DatabaseInstance>();
        }
        return this.databaseInstance;
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
     *         &lt;element name="DatabaseInstanceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="DatabaseEncoding" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DatabaseName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DatabaseType" type="{http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1}DatabaseTypeType"/>
     *         &lt;element name="DatabaseUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "databaseInstanceId",
        "databaseEncoding",
        "databaseName",
        "databaseType",
        "databaseUrl"
    })
    public static class DatabaseInstance {

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

    }

}
