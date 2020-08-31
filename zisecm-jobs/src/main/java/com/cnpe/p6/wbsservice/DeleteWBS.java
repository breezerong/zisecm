
package com.cnpe.p6.wbsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DeleteWBS complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DeleteWBS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="DeleteWithReplacement" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *                   &lt;element name="ReplacementObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "DeleteWBS", propOrder = {
    "objectId",
    "deleteWithReplacement"
})
public class DeleteWBS {

    @XmlElement(name = "ObjectId", type = Integer.class)
    protected List<Integer> objectId;
    @XmlElement(name = "DeleteWithReplacement")
    protected List<DeleteWBS.DeleteWithReplacement> deleteWithReplacement;

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
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getObjectId() {
        if (objectId == null) {
            objectId = new ArrayList<Integer>();
        }
        return this.objectId;
    }

    /**
     * Gets the value of the deleteWithReplacement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deleteWithReplacement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeleteWithReplacement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeleteWBS.DeleteWithReplacement }
     * 
     * 
     */
    public List<DeleteWBS.DeleteWithReplacement> getDeleteWithReplacement() {
        if (deleteWithReplacement == null) {
            deleteWithReplacement = new ArrayList<DeleteWBS.DeleteWithReplacement>();
        }
        return this.deleteWithReplacement;
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
     *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
     *         &lt;element name="ReplacementObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
        "objectId",
        "replacementObjectId"
    })
    public static class DeleteWithReplacement {

        @XmlElement(name = "ObjectId", type = Integer.class)
        protected List<Integer> objectId;
        @XmlElement(name = "ReplacementObjectId")
        protected int replacementObjectId;

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
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getObjectId() {
            if (objectId == null) {
                objectId = new ArrayList<Integer>();
            }
            return this.objectId;
        }

        /**
         * 获取replacementObjectId属性的值。
         * 
         */
        public int getReplacementObjectId() {
            return replacementObjectId;
        }

        /**
         * 设置replacementObjectId属性的值。
         * 
         */
        public void setReplacementObjectId(int value) {
            this.replacementObjectId = value;
        }

    }

}
