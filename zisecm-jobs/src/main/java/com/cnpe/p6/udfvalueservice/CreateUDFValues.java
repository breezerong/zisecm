
package com.cnpe.p6.udfvalueservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CreateUDFValues complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CreateUDFValues">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UDFValue" type="{http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1}UDFValue" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateUDFValues", propOrder = {
    "udfValue"
})
public class CreateUDFValues {

    @XmlElement(name = "UDFValue", required = true)
    protected List<UDFValue> udfValue;

    /**
     * Gets the value of the udfValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the udfValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUDFValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UDFValue }
     * 
     * 
     */
    public List<UDFValue> getUDFValue() {
        if (udfValue == null) {
            udfValue = new ArrayList<UDFValue>();
        }
        return this.udfValue;
    }

}
