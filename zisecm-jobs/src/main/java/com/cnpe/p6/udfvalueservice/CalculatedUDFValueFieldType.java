
package com.cnpe.p6.udfvalueservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CalculatedUDFValueFieldType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="CalculatedUDFValueFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ConditionalIndicator"/>
 *     &lt;enumeration value="Cost"/>
 *     &lt;enumeration value="Double"/>
 *     &lt;enumeration value="FinishDate"/>
 *     &lt;enumeration value="Integer"/>
 *     &lt;enumeration value="ProjectObjectId"/>
 *     &lt;enumeration value="StartDate"/>
 *     &lt;enumeration value="UDFTypeDataType"/>
 *     &lt;enumeration value="UDFTypeObjectId"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CalculatedUDFValueFieldType")
@XmlEnum
public enum CalculatedUDFValueFieldType {

    @XmlEnumValue("ConditionalIndicator")
    CONDITIONAL_INDICATOR("ConditionalIndicator"),
    @XmlEnumValue("Cost")
    COST("Cost"),
    @XmlEnumValue("Double")
    DOUBLE("Double"),
    @XmlEnumValue("FinishDate")
    FINISH_DATE("FinishDate"),
    @XmlEnumValue("Integer")
    INTEGER("Integer"),
    @XmlEnumValue("ProjectObjectId")
    PROJECT_OBJECT_ID("ProjectObjectId"),
    @XmlEnumValue("StartDate")
    START_DATE("StartDate"),
    @XmlEnumValue("UDFTypeDataType")
    UDF_TYPE_DATA_TYPE("UDFTypeDataType"),
    @XmlEnumValue("UDFTypeObjectId")
    UDF_TYPE_OBJECT_ID("UDFTypeObjectId");
    private final String value;

    CalculatedUDFValueFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CalculatedUDFValueFieldType fromValue(String v) {
        for (CalculatedUDFValueFieldType c: CalculatedUDFValueFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
