package com.ecm.core.db.sqlserver;

import java.util.Date;

import com.ecm.common.util.DateUtils;
import com.ecm.core.db.IDBUtils;

public class SqlServerlUtils implements IDBUtils {

	@Override
	public String getSingleQuotationMark() {
		// TODO Auto-generated method stub
		return "''";
	}

	@Override
	public String getDBNullDate() {
		// TODO Auto-generated method stub
		return "null";
	}

	@Override
	public String getString(String dataStr) {
		// TODO Auto-generated method stub
		return dataStr.replace("'", getSingleQuotationMark());
	}

	@Override
	public String getDBDateString(String date) {
		// TODO Auto-generated method stub
		Date d = DateUtils.getDate(date);
		return getDBDateString(d);
	}

	@Override
	public String getDBDateString(Date d) {
		// TODO Auto-generated method stub
		return "'"+DateUtils.sdfAll.format(d)+"'";
	}

	@Override
	public String getDBDateNow() {
		// TODO Auto-generated method stub
		return "GETDATE()";
	}

}
