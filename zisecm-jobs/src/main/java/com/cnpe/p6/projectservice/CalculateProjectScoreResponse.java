
package com.cnpe.p6.projectservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CalculateProjectScoreResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CalculateProjectScoreResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculateProjectScoreResponse", propOrder = {
    "score"
})
public class CalculateProjectScoreResponse {

    @XmlElement(name = "Score")
    protected int score;

    /**
     * 获取score属性的值。
     * 
     */
    public int getScore() {
        return score;
    }

    /**
     * 设置score属性的值。
     * 
     */
    public void setScore(int value) {
        this.score = value;
    }

}
