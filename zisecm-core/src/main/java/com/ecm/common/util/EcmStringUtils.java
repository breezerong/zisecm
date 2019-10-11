package com.ecm.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EcmStringUtils {
	public static final boolean isEmpty(String value) {
		if (value != null && value.trim().length() > 0)
			return false;
		return true;
	}

	public static boolean isAlpha(String str) {
		if (str == null)
			return false;
		int sz = str.length();
		for (int i = 0; i < sz; i++)
			if (!Character.isLetter(str.charAt(i)))
				return false;

		return true;
	}

	public static boolean isNumeric(String str) {
		if (str == null)
			return false;
		int sz = str.length();
		for (int i = 0; i < sz; i++)
			if (!Character.isDigit(str.charAt(i)))
				return false;

		return true;
	}

	public static String toUpper(String str) {
		if (isEmpty(str))
			return "";

		return str.toUpperCase();
	}

	public static String substring(String str, int length) {
		if (length <= 0 || isEmpty(str))
			return "";

		if (str.length() > length)
			return str.substring(0, length);
		return str;
	}

	public static int toInt(String str) {
		if (isEmpty(str))
			return 0;

		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
		}
		return 0;
	}
	
	public static int findFirstChineseChar(String s){
		int n = 0;
		for (int i = 0; i < s.length(); i++) {
			n = (int) s.charAt(i);
			if ((19968 <= n && n < 40623)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * 从开始结束提取字符串
	 * @param inStr
	 * @return
	 */
	public static List<String> getStringsByRegular(String inStr,String startStr,String endStr){
		
		List<String> list=new ArrayList<String>();
		Pattern p = Pattern.compile("("+startStr+"[^"+endStr+"]*"+endStr+")");
		Matcher m = p.matcher(inStr);
		while(m.find()){
			list.add(m.group().substring(1, m.group().length()-1));
		}
		return list;
	}
}
