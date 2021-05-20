package com.ecm.core.util;

import java.util.Map;

import com.ecm.core.cache.manager.SessionManager;
import com.ecm.icore.service.IEcmSession;

public class TCAttributeHandle {
	/**
	 * 根据TC要求,处理tc属性
	 * 
	 * @param args
	 * @return
	 */
	public static Map<String, Object> attributeHandle(Map<String, Object> args) {
		args.put("C_COMMENT", "");// 备注：默认空
		args.put("C_DRAFTER", transfValue(args.get("C_DRAFTER").toString()));// 编制者：去括号
		args.put("C_PAGE_SIZE", transfValue(args.get("C_PAGE_SIZE").toString()));// 图纸规格：去括号
		args.put("C_CREATE_UNIT", getUnit(args.get("C_CREATE_UNIT").toString()));// 单位:核电工艺所.河北分公司.CNPE

		return args;
	}

	/**
	 * 截取（外的string字符串
	 * 
	 * @param original
	 * @return
	 */
	private static String transfValue(String original) {
		if (original.indexOf("(") > 0) {
			original = original.substring(0, original.indexOf("("));
		} else if (original.indexOf("（") > 0) {
			original = original.substring(0, original.indexOf("（"));
		}

		return original;
	}

	/**
	 * 处理单位信息，截取数据到所
	 * 
	 * @param oriUnit
	 * @return
	 */
	private static String getUnit(String oriUnit) {
		String[] unitArray = oriUnit.split("\\.");
		int i = 0;
		for (i = 0; i < unitArray.length; i++) {
			if (unitArray[i].indexOf("所") > 0) {
				break;
			}
		}
		StringBuffer newUnit = new StringBuffer();
		for (i = i; i < unitArray.length; i++) {
			newUnit.append(unitArray[i]);
			if (i != unitArray.length - 1) {
				newUnit.append(".");
			}
		}
		return newUnit.toString();
	}

	/**
	 * 返回序号
	 * 
	 * @param num
	 * @return
	 */
	public static String getTCNum(int num) {
		String numStringValue = String.valueOf(num);
		if (numStringValue.length() == 1) {
			return "00" + numStringValue;
		} else if (numStringValue.length() == 2) {
			return "0" + numStringValue;
		}
		return numStringValue;
	}
}
