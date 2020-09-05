
package com.cnpe.p6.udfvalueservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>UDFValueFieldType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="UDFValueFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CodeValue"/>
 *     &lt;enumeration value="ConditionalIndicator"/>
 *     &lt;enumeration value="Cost"/>
 *     &lt;enumeration value="CreateDate"/>
 *     &lt;enumeration value="CreateUser"/>
 *     &lt;enumeration value="Description"/>
 *     &lt;enumeration value="Double"/>
 *     &lt;enumeration value="FinishDate"/>
 *     &lt;enumeration value="ForeignObjectId"/>
 *     &lt;enumeration value="Indicator"/>
 *     &lt;enumeration value="Integer"/>
 *     &lt;enumeration value="IsBaseline"/>
 *     &lt;enumeration value="IsTemplate"/>
 *     &lt;enumeration value="IsUDFTypeCalculated"/>
 *     &lt;enumeration value="IsUDFTypeConditional"/>
 *     &lt;enumeration value="LastUpdateDate"/>
 *     &lt;enumeration value="LastUpdateUser"/>
 *     &lt;enumeration value="ProjectObjectId"/>
 *     &lt;enumeration value="StartDate"/>
 *     &lt;enumeration value="Text"/>
 *     &lt;enumeration value="UDFCodeObjectId"/>
 *     &lt;enumeration value="UDFTypeDataType"/>
 *     &lt;enumeration value="UDFTypeObjectId"/>
 *     &lt;enumeration value="UDFTypeSubjectArea"/>
 *     &lt;enumeration value="UDFTypeTitle"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UDFValueFieldType")
@XmlEnum
public enum UDFValueFieldType {

    @XmlEnumValue("CodeValue")
    CODE_VALUE("CodeValue"),
    @XmlEnumValue("ConditionalIndicator")
    CONDITIONAL_INDICATOR("ConditionalIndicator"),
    @XmlEnumValue("Cost")
    COST("Cost"),
    @XmlEnumValue("CreateDate")
    CREATE_DATE("CreateDate"),
    @XmlEnumValue("CreateUser")
    CREATE_USER("CreateUser"),
    @XmlEnumValue("Description")
    DESCRIPTION("Description"),
    @XmlEnumValue("Double")
    DOUBLE("Double"),
    @XmlEnumValue("FinishDate")
    FINISH_DATE("FinishDate"),
    @XmlEnumValue("ForeignObjectId")
    FOREIGN_OBJECT_ID("ForeignObjectId"),
    @XmlEnumValue("Indicator")
    INDICATOR("Indicator"),
    @XmlEnumValue("Integer")
    INTEGER("Integer"),
    @XmlEnumValue("IsBaseline")
    IS_BASELINE("IsBaseline"),
    @XmlEnumValue("IsTemplate")
    IS_TEMPLATE("IsTemplate"),
    @XmlEnumValue("IsUDFTypeCalculated")
    IS_UDF_TYPE_CALCULATED("IsUDFTypeCalculated"),
    @XmlEnumValue("IsUDFTypeConditional")
    IS_UDF_TYPE_CONDITIONAL("IsUDFTypeConditional"),
    @XmlEnumValue("LastUpdateDate")
    LAST_UPDATE_DATE("LastUpdateDate"),
    @XmlEnumValue("LastUpdateUser")
    LAST_UPDATE_USER("LastUpdateUser"),
    @XmlEnumValue("ProjectObjectId")
    PROJECT_OBJECT_ID("ProjectObjectId"),
    @XmlEnumValue("StartDate")
    START_DATE("StartDate"),
    @XmlEnumValue("Text")
    TEXT("Text"),
    @XmlEnumValue("UDFCodeObjectId")
    UDF_CODE_OBJECT_ID("UDFCodeObjectId"),
    @XmlEnumValue("UDFTypeDataType")
    UDF_TYPE_DATA_TYPE("UDFTypeDataType"),
    @XmlEnumValue("UDFTypeObjectId")
    UDF_TYPE_OBJECT_ID("UDFTypeObjectId"),
    @XmlEnumValue("UDFTypeSubjectArea")
    UDF_TYPE_SUBJECT_AREA("UDFTypeSubjectArea"),
    @XmlEnumValue("UDFTypeTitle")
    UDF_TYPE_TITLE("UDFTypeTitle");
    private final String value;

    UDFValueFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UDFValueFieldType fromValue(String v) {
        for (UDFValueFieldType c: UDFValueFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
