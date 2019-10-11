package com.ecm.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecm.common.util.DateUtils;

@Component
public class DBUtils {
	
	
	private static  String databaseType;
	
	@Value("${ecm.database.type}")
	public void setDatabaseType(String databaseType) {
		DBUtils.databaseType = databaseType;
	}

	public static String getSingleQuotationMark() {
		if ("mysql".equalsIgnoreCase(databaseType)) {
			return "\\\\'";
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			return "''";
		} else if ("sqlserver".equalsIgnoreCase(databaseType)) {
			return "''";
		}
		else if ("postgresql".equalsIgnoreCase(databaseType)) {
			return "''";
		}
		return "''";
	}
	
	public static String getDBNullDate() {
		if ("mysql".equalsIgnoreCase(databaseType)) {
			return "null";
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			return "null";
		} else if ("sqlserver".equalsIgnoreCase(databaseType)) {
			return "null";
		}
		else if ("postgresql".equalsIgnoreCase(databaseType)) {
			return "null";
		}
		return "null";
	}
	 
	public static String getString(String dataStr) {
		if(dataStr==null||dataStr.trim().length()==0) {
			return dataStr;
		}
		if ("mysql".equalsIgnoreCase(databaseType)) {
			return dataStr.replace("'", getSingleQuotationMark());
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			return dataStr.replace("'", getSingleQuotationMark());
		} else if ("sqlserver".equalsIgnoreCase(databaseType)) {
			return dataStr.replace("'", getSingleQuotationMark());
		}
		else if ("postgresql".equalsIgnoreCase(databaseType)) {
			return dataStr.replace("'", getSingleQuotationMark());
		}
		return dataStr.replace("'", "");
	}
	
	public static String getDBDateString(String date) {
		if(date==null||date.length()<1) {
			return date;
		}
		Date d=null;
		try
		{
			d = new Date(Long.parseLong(date));
		}
		catch(Exception ex)
		{
			date = date.replace("Z", " UTC").replace("+0000", "").replace("T", " ");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				d = format.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ("mysql".equalsIgnoreCase(databaseType)) {
			
			date = "'"+DateUtils.sdfAll.format(d)+"'";
		} 
		else if ("oracle".equalsIgnoreCase(databaseType)) {
			
		} 
		else if ("sqlserver".equalsIgnoreCase(databaseType)) {
			
		}
		else if ("postgresql".equalsIgnoreCase(databaseType)) {
			
		}
		return date;
	}
	
	public static String getDBDateNow() {
		if ("mysql".equalsIgnoreCase(databaseType)) {
			return "CURRENT_TIMESTAMP";
		} 
		else if ("oracle".equalsIgnoreCase(databaseType)) {
			
		} 
		else if ("sqlserver".equalsIgnoreCase(databaseType)) {
			
		}
		else if ("postgresql".equalsIgnoreCase(databaseType)) {
			
		}
		return null;
	}
}
