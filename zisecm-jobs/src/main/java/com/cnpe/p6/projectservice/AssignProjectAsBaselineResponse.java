
package com.cnpe.p6.projectservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AssignProjectAsBaselineResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AssignProjectAsBaselineResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaselineProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssignProjectAsBaselineResponse", propOrder = {
    "baselineProjectObjectId"
})
public class AssignProjectAsBaselineResponse {

    @XmlElement(name = "BaselineProjectObjectId")
    protected int baselineProjectObjectId;

    /**
     * 获取baselineProjectObjectId属性的值。
     * 
     */
    public int getBaselineProjectObjectId() {
        return baselineProjectObjectId;
    }

    /**
     * 设置baselineProjectObjectId属性的值。
     * 
     */
    public void setBaselineProjectObjectId(int value) {
        this.baselineProjectObjectId = value;
    }

}
