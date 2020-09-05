
package com.cnpe.p6.authenticationservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DatabaseTypeType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="DatabaseTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Oracle"/>
 *     &lt;enumeration value="SQL Server"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DatabaseTypeType")
@XmlEnum
public enum DatabaseTypeType {

    @XmlEnumValue("Oracle")
    ORACLE("Oracle"),
    @XmlEnumValue("SQL Server")
    SQL_SERVER("SQL Server");
    private final String value;

    DatabaseTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DatabaseTypeType fromValue(String v) {
        for (DatabaseTypeType c: DatabaseTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
