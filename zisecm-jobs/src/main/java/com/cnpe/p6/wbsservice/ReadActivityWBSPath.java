
package com.cnpe.p6.wbsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ReadActivityWBSPath complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ReadActivityWBSPath">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActivityObjectId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="Field" type="{http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2}WBSFieldType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadActivityWBSPath", propOrder = {
    "activityObjectId",
    "field"
})
public class ReadActivityWBSPath {

    @XmlElement(name = "ActivityObjectId", type = Integer.class)
    protected List<Integer> activityObjectId;
    @XmlElement(name = "Field", required = true)
    @XmlSchemaType(name = "string")
    protected List<WBSFieldType> field;

    /**
     * Gets the value of the activityObjectId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activityObjectId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivityObjectId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getActivityObjectId() {
        if (activityObjectId == null) {
            activityObjectId = new ArrayList<Integer>();
        }
        return this.activityObjectId;
    }

    /**
     * Gets the value of the field property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WBSFieldType }
     * 
     * 
     */
    public List<WBSFieldType> getField() {
        if (field == null) {
            field = new ArrayList<WBSFieldType>();
        }
        return this.field;
    }

}
