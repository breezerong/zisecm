
package com.cnpe.p6.udfvalueservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CreateUDFValuesResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CreateUDFValuesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectId" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="UDFTypeObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="ForeignObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "CreateUDFValuesResponse", propOrder = {
    "objectId"
})
public class CreateUDFValuesResponse {

    @XmlElement(name = "ObjectId")
    protected List<CreateUDFValuesResponse.ObjectId> objectId;

    /**
     * Gets the value of the objectId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreateUDFValuesResponse.ObjectId }
     * 
     * 
     */
    public List<CreateUDFValuesResponse.ObjectId> getObjectId() {
        if (objectId == null) {
            objectId = new ArrayList<CreateUDFValuesResponse.ObjectId>();
        }
        return this.objectId;
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
     *         &lt;element name="UDFTypeObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="ForeignObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
        "udfTypeObjectId",
        "foreignObjectId"
    })
    public static class ObjectId {

        @XmlElement(name = "UDFTypeObjectId")
        protected int udfTypeObjectId;
        @XmlElement(name = "ForeignObjectId")
        protected int foreignObjectId;

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

        /**
         * 获取foreignObjectId属性的值。
         * 
         */
        public int getForeignObjectId() {
            return foreignObjectId;
        }

        /**
         * 设置foreignObjectId属性的值。
         * 
         */
        public void setForeignObjectId(int value) {
            this.foreignObjectId = value;
        }

    }

}
