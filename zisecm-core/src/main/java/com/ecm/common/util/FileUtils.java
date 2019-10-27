package com.ecm.common.util;
/**
 * 文件工具类
 * @author Haihong Rong
 * @date 2019年7月19日 上午9:38:49
 */
public class FileUtils {

	/**
	 * 获取扩展名
	 * @param fileName
	 * @return
	 */
	public static String getExtention(String fileName) {
		String ext = "null";
		if(fileName.lastIndexOf(".")>0)
		{
			ext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		}
		return ext;
	}
}
