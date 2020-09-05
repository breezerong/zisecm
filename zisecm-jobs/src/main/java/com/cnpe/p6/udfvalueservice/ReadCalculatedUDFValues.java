
package com.cnpe.p6.udfvalueservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ReadCalculatedUDFValues complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ReadCalculatedUDFValues">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Field" type="{http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1}CalculatedUDFValueFieldType" maxOccurs="unbounded"/>
 *         &lt;element name="UDFTypeObjectId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="ProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadCalculatedUDFValues", propOrder = {
    "field",
    "udfTypeObjectId",
    "projectObjectId"
})
public class ReadCalculatedUDFValues {

    @XmlElement(name = "Field", required = true)
    @XmlSchemaType(name = "string")
    protected List<CalculatedUDFValueFieldType> field;
    @XmlElement(name = "UDFTypeObjectId", type = Integer.class)
    protected List<Integer> udfTypeObjectId;
    @XmlElement(name = "ProjectObjectId", type = Integer.class)
    protected List<Integer> projectObjectId;

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
     * {@link CalculatedUDFValueFieldType }
     * 
     * 
     */
    public List<CalculatedUDFValueFieldType> getField() {
        if (field == null) {
            field = new ArrayList<CalculatedUDFValueFieldType>();
        }
        return this.field;
    }

    /**
     * Gets the value of the udfTypeObjectId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the udfTypeObjectId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUDFTypeObjectId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getUDFTypeObjectId() {
        if (udfTypeObjectId == null) {
            udfTypeObjectId = new ArrayList<Integer>();
        }
        return this.udfTypeObjectId;
    }

    /**
     * Gets the value of the projectObjectId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projectObjectId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjectObjectId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getProjectObjectId() {
        if (projectObjectId == null) {
            projectObjectId = new ArrayList<Integer>();
        }
        return this.projectObjectId;
    }

}
