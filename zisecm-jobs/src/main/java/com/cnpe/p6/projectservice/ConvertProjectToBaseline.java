
package com.cnpe.p6.projectservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConvertProjectToBaseline complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ConvertProjectToBaseline">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OriginalProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TargetProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConvertProjectToBaseline", propOrder = {
    "originalProjectObjectId",
    "targetProjectObjectId"
})
public class ConvertProjectToBaseline {

    @XmlElement(name = "OriginalProjectObjectId")
    protected int originalProjectObjectId;
    @XmlElement(name = "TargetProjectObjectId")
    protected int targetProjectObjectId;

    /**
     * 获取originalProjectObjectId属性的值。
     * 
     */
    public int getOriginalProjectObjectId() {
        return originalProjectObjectId;
    }

    /**
     * 设置originalProjectObjectId属性的值。
     * 
     */
    public void setOriginalProjectObjectId(int value) {
        this.originalProjectObjectId = value;
    }

    /**
     * 获取targetProjectObjectId属性的值。
     * 
     */
    public int getTargetProjectObjectId() {
        return targetProjectObjectId;
    }

    /**
     * 设置targetProjectObjectId属性的值。
     * 
     */
    public void setTargetProjectObjectId(int value) {
        this.targetProjectObjectId = value;
    }

}
