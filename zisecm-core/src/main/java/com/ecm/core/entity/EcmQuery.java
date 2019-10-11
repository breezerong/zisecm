package com.ecm.core.entity;

public class EcmQuery extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String labelColumn;

    private String valueColumn;

    private String sqlString;

    public String getLabelColumn() {
        return labelColumn;
    }

    public void setLabelColumn(String labelColumn) {
        this.labelColumn = labelColumn == null ? null : labelColumn.trim();
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn == null ? null : valueColumn.trim();
    }

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString == null ? null : sqlString.trim();
    }
}