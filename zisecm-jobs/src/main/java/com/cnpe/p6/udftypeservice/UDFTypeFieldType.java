
package com.cnpe.p6.udftypeservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>UDFTypeFieldType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="UDFTypeFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CreateDate"/>
 *     &lt;enumeration value="CreateUser"/>
 *     &lt;enumeration value="DataType"/>
 *     &lt;enumeration value="IsCalculated"/>
 *     &lt;enumeration value="IsConditional"/>
 *     &lt;enumeration value="IsSecureCode"/>
 *     &lt;enumeration value="LastUpdateDate"/>
 *     &lt;enumeration value="LastUpdateUser"/>
 *     &lt;enumeration value="ObjectId"/>
 *     &lt;enumeration value="SubjectArea"/>
 *     &lt;enumeration value="Title"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UDFTypeFieldType")
@XmlEnum
public enum UDFTypeFieldType {

    @XmlEnumValue("CreateDate")
    CREATE_DATE("CreateDate"),
    @XmlEnumValue("CreateUser")
    CREATE_USER("CreateUser"),
    @XmlEnumValue("DataType")
    DATA_TYPE("DataType"),
    @XmlEnumValue("IsCalculated")
    IS_CALCULATED("IsCalculated"),
    @XmlEnumValue("IsConditional")
    IS_CONDITIONAL("IsConditional"),
    @XmlEnumValue("IsSecureCode")
    IS_SECURE_CODE("IsSecureCode"),
    @XmlEnumValue("LastUpdateDate")
    LAST_UPDATE_DATE("LastUpdateDate"),
    @XmlEnumValue("LastUpdateUser")
    LAST_UPDATE_USER("LastUpdateUser"),
    @XmlEnumValue("ObjectId")
    OBJECT_ID("ObjectId"),
    @XmlEnumValue("SubjectArea")
    SUBJECT_AREA("SubjectArea"),
    @XmlEnumValue("Title")
    TITLE("Title");
    private final String value;

    UDFTypeFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UDFTypeFieldType fromValue(String v) {
        for (UDFTypeFieldType c: UDFTypeFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
