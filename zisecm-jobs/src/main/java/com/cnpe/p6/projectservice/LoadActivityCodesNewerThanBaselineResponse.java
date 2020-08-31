
package com.cnpe.p6.projectservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LoadActivityCodesNewerThanBaselineResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="LoadActivityCodesNewerThanBaselineResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActivityCodeAssignmentObjectIds" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadActivityCodesNewerThanBaselineResponse", propOrder = {
    "activityCodeAssignmentObjectIds"
})
public class LoadActivityCodesNewerThanBaselineResponse {

    @XmlElement(name = "ActivityCodeAssignmentObjectIds", required = true)
    protected List<String> activityCodeAssignmentObjectIds;

    /**
     * Gets the value of the activityCodeAssignmentObjectIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activityCodeAssignmentObjectIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivityCodeAssignmentObjectIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getActivityCodeAssignmentObjectIds() {
        if (activityCodeAssignmentObjectIds == null) {
            activityCodeAssignmentObjectIds = new ArrayList<String>();
        }
        return this.activityCodeAssignmentObjectIds;
    }

}
