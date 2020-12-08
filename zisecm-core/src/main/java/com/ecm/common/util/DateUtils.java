package com.ecm.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static String DateToFolderPath(Date dt,String splitStr) {
		String formatStr = "yyyy"+splitStr+"MM"+splitStr+"dd";
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String str = format.format(dt);
		return str;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date,String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 * @throws ParseException 
	 */
	public static Date StrToDate(String str,String formatStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = format.parse(str);
		return date;
	}
	
    /**
     * 获取指定日期的年度
     * @param date
     * @return
     */
    public static int getYear(Date date) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(date);
		return now.get(Calendar.YEAR);
	}
    /**
     * 获取指定日期月度
     * @param date
     * @return
     */
	public static int getMonth(Date date) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(date);
		return now.get(Calendar.MONTH) + 1;
	}
	/**
	 * 获取指定日期的日
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(date);
		return now.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前系统时间
	 * @param format
	 * @return
	 */
	public static String currentDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date(System.currentTimeMillis()));
	}
	
	
	//ISO日期转换为UTC日期  
	public static XMLGregorianCalendar xmlToDate(Date date){  
	    GregorianCalendar cal = new GregorianCalendar();  
	        cal.setTime(date);  
	        XMLGregorianCalendar gc = null;  
	        try {  
	            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);  
	        } catch (Exception e) {  
	             e.printStackTrace();  
	        }  
	    return gc;  
	}  
	

	//UTC日期转换为ISO日期  
	public static Date DateToXML(XMLGregorianCalendar gc){  
	    GregorianCalendar ca = gc.toGregorianCalendar();  
	        return ca.getTime();  
	}  
	
	public static Date getDate(String date) {
		Date d=null;
		try
		{
			d = new Date(Long.parseLong(date));
		}
		catch(Exception ex)
		{
			date = date.replace("Z", " UTC").replace("+0000", "").replace("T", " ");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			if(date.indexOf(" ")<0&&date.indexOf(":")<0) {
				format = new SimpleDateFormat("yyyy-MM-dd");
			}
			try {
				d = format.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					d = format.parse(date);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					try {
						d = format.parse(date);
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		}
		return d;
	}
	
	public static int compareDate(Date date1,Date date2) {
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
	String s1 = df.format(date1);  
	String s2 = df.format(date2);  
	  
	return s1.compareTo(s2);  
	}
}
