package com.ecm.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public final class ToolUtils {
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean parseBoolean(String value) {
		return value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")
				|| value.equalsIgnoreCase("y") || value.equals("1"));
	}

	public static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Throwable t) {
			return 0;
		}
	}

	public static double parseDouble(String value) {
		try {
			return Double.valueOf(value);
		} catch (Throwable t) {
			return 0;
		}
	}

	public static String parseString(int value) {
		try {
			return String.valueOf(value);
		} catch (Throwable t) {
			return "";
		}
	}

	/**
	 * 将任意的时间格式转化为yyyy-mm-dd格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(String nodeTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
        String retTime= "";
		try {
			Date ss = df.parse(nodeTime);
			SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");  
         retTime = format0.format(ss.getTime());//这个就是把时间戳经过处理得到期望格式的时间  
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        return retTime;
        
	}


	/**
	 * 判断时间是否格式有效,如：2009-3-9, Mar 9, 2009, 3/9/2009
	 * 
	 * @param date
	 * @return
	 */
	public static boolean dateIsValid(String date) {
		return date.matches(
				"([\\d]{2,4}-[\\d]{1,2}-[\\d]{1,2})|([\\w]{3}\\s[\\d]{1,2},\\s[\\d]{2,4})|([\\w]{3}\\s[\\d]{1,2},[\\d]{2,4})|([\\d]{1,2}\\/[\\d]{1,2}\\/[\\d]{2,4})");
	}

	/**
	 * 判断时间是否为空
	 * 
	 * @param date
	 * @return
	 */
	public static boolean dateIsEmpty(String date) {
		boolean empty = false;
		if (StringUtils.isEmpty(date) || date.equalsIgnoreCase("date") || date.equalsIgnoreCase("日期"))
			empty = true;
		return empty;
	}

	/**
	 * 得到某个时间的前/后几天的时间
	 * 
	 * @param date
	 * @param diff
	 *            负数：前几天 整数：后几天
	 * @return
	 * @throws ParseException
	 */
	public static String getDateDiff(String date, int diff) throws ParseException {
		Calendar day = java.util.Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = formatDate(date);
		day.setTime(sdf.parse(date));
		day.add(Calendar.DATE, diff);
		return sdf.format(day.getTime());
	}

	/**
	 * 得到某个时间的前/后几天的时间
	 * 
	 * @param date
	 * @param diff
	 *            负数：前几天 整数：后几天
	 * @return
	 * @throws ParseException
	 */
	public static String getDateYearDiff(String date, int diff) {
		Calendar day = java.util.Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = formatDate(date);
		try {
			day.setTime(sdf.parse(date));
			day.add(Calendar.YEAR, diff);
		} catch (ParseException e) {
		}
		return sdf.format(day.getTime());
	}

	/**
	 * 比较时间的大小
	 * 
	 * @author Jeff.Liu
	 * @param srcDate
	 * @param disDate
	 * @return
	 */
	public static int compareDate(String srcDate, String disDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		srcDate = formatDate(srcDate);
		disDate = formatDate(disDate);

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(srcDate);
			d2 = format.parse(disDate);
		} catch (java.text.ParseException e) {
			// DMSystem.printStackTrace(e);
			return -2;
		}
		return compareDate(d1, d2);
	}

	public static int compareDate(Date srcDate, Date disDate) {
		return srcDate.compareTo(disDate);
	}
	
	/**
	 * 属性转换 例如：Attr_64416_8截取为64416_8
	 * @param attrs
	 * @return
	 */
	public static Map<String,String> arrtConvertMap(Map<String,String> attrs) {
		Map<String,String> categoryMap = new HashMap<String,String>();//截取后的map,
		if(attrs == null) {
			return categoryMap;
		}		
		for(Map.Entry<String,String> en:attrs.entrySet()) {			
			String key = en.getKey().substring(5);
			categoryMap.put(String.valueOf(key),en.getValue());
		}
		return categoryMap;
	}

}
