
package com.cnpe.p6.wbsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ReadWBSPathResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ReadWBSPathResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WBS" type="{http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2}WBS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadWBSPathResponse", propOrder = {
    "wbs"
})
public class ReadWBSPathResponse {

    @XmlElement(name = "WBS")
    protected List<WBS> wbs;

    /**
     * Gets the value of the wbs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wbs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWBS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WBS }
     * 
     * 
     */
    public List<WBS> getWBS() {
        if (wbs == null) {
            wbs = new ArrayList<WBS>();
        }
        return this.wbs;
    }

}
