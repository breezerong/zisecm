package com.ecm.portal.common.dataexchange;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.entity.EcmDocument;

public class AttrUtils {
	@Autowired
	private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;

	/** 自定义方法配置文件路径 **/
	/** 格式化配置文件 **/
	//public static final String FORMATTER_CONFIG_FILE_PATH = "com/ecm/portal/archivecd/common/dataexchange/format.properties";
	
	/** 签名图片路径 **/
	//public static final String FOLDER_PATH = "/Configuration/签名";
	
	
	//public static final String SIGN_TEMP_PATH="c:\\documentum\\sign\\";
	private static boolean checkPath = false;
	private static String checkedPath="";
	
	//private static Properties methodPro = initPropertiesFile(METHOD_CONFIG_FILE_PATH);
	//private static Properties formatterPro = initPropertiesFile(FORMATTER_CONFIG_FILE_PATH);
	
	public static String getDataByBookMarkName(List<ArrayList<String>> list,EcmDocument doc, String bookMark, List<EcmCfgActivity> pro) throws Exception {

		String[] attrArray = bookMark.split("_");
		int a=bookMark.indexOf("9");
		if(a!=-1) {
			int index = bookMark.lastIndexOf('9');
			bookMark = bookMark.substring(0, index);
		}
		String returnStr = "";
		String taskName ="";
		if(bookMark.indexOf("WF")>-1)
		{
			taskName = getSingleAttrFromStr(bookMark);
			taskName = taskName.split("_")[0];
		}
		if (bookMark.startsWith("ATTR_")) {// 直接从文档属性读取值
			returnStr = getAttrFromDocument(doc, getSingleAttrFromStr(bookMark));
		} else if (bookMark.startsWith("FUN_")) {// 通过函数复杂处理
		//	IFunction function = (IFunction) Class.forName(methodPro.getProperty(attrArray[1])).newInstance();
			//returnStr =  function.getValue(doc, getMultiAttrFromStr(bookMark));
		} else if (bookMark.startsWith("IMGATT_")) {// 直接从文档属性获取签名图片
			String name= "";
			Object names = doc.getAttributeValue(getSingleAttrFromStr(bookMark));
			if(names!=null) {
				name=names.toString();
			}
			returnStr =  getImageFromDocument(list,name);
			
		} else if (bookMark.startsWith("WFUONE_")) {// 从工作流获取用户名一个值

			//返回结果倒序排列
			//有2个值，1取第二个，2取第一个
			//1个值直接取第一个
			if(bookMark.endsWith("_0"))
			{
				if(getAuditCount(list,taskName)>1)
				{
					returnStr =  getOneUserNameFromWF(list,taskName,1);
				}else {
					returnStr =  getOneUserNameFromWF(list,taskName,0);
				}
			}
			else if(bookMark.endsWith("_1"))
			{
				if(getAuditCount(list,taskName)>1)
				{
					returnStr =  getOneUserNameFromWF(list,taskName,0);
				}
			}
			else
			{
				returnStr =  getOneUserNameFromWF(list,taskName,0);
			}
		} else if (bookMark.startsWith("WFDONE_")) {// 从工作流获取时间一个值
			//返回结果倒序排列
			//有2个值，1取第二个，2取第一个
			//1个值直接取第一个
			if(bookMark.endsWith("_0"))
			{
				if(getAuditCount(list,taskName)>1)
				{
					returnStr =  getOneTimeFromWF(list,taskName,1);
				}else {
					returnStr =  getOneTimeFromWF(list,taskName,0);
				}
			}
			else if(bookMark.endsWith("_1"))
			{
				if(getAuditCount(list,taskName)>1)
				{
					returnStr =  getOneTimeFromWF(list,taskName,0);
				}
			} else {
				returnStr =  getOneTimeFromWF(list,taskName,0);
			}
			
		} else if (bookMark.startsWith("WFUALL_")) {// 从工作流获取用户名多个值
			returnStr =  getMultipleUserNameFromWF(list, getSingleAttrFromStr(bookMark));
		} else if (bookMark.startsWith("IMGWF_")) {// 从工作流获取签名图片
			//返回结果倒序排列
			//有2个值，1取第二个，2取第一个
			//1个值直接取第一个
			if(bookMark.endsWith("_0"))
			{
				if(getAuditCount(list,taskName)>1)
				{
					returnStr =  getImageFromWF(list,taskName,1);
				}else {
					returnStr =  getImageFromWF(list,taskName,0);
				}
			}
			else if(bookMark.endsWith("_1"))
			{
				if(getAuditCount(list,taskName)>1)
				{
					returnStr =  getImageFromWF(list,taskName,0);
				}
			} else {
				returnStr =  getImageFromWF(list,taskName,0);
			}
		}else if(bookMark.startsWith("RELONE_")) {// 直接从文档属性读取值
			//returnStr = getSingleRelValue(doc, bookMark);
		}
		return returnStr;
	}
	public  static List<ArrayList<String>> getDataByBookMarkNameForTable(List<ArrayList<String>> list,Object doc, String bookMark) throws Exception {
		String[] attrArray = bookMark.split("_");
		if (bookMark.startsWith("TBLID_")) {// 表格处理：通过ID获取
			return getAttrByIdForTable(doc);
		} else if (bookMark.startsWith("TBLSIGN_")) {// 表格处理：动态签名
		//	return getSignForTable(list,getTaskNames(bookMark));
		} else if (bookMark.startsWith("TBLREL_")) {// 表格处理：关系查询
			return getRelationForTable(doc);
		}
		return null;
	}

	
	/**
	 * 直接从文档中获得属性
	 * @param doc
	 * @param attribute
	 * @return
	 * @throws Exception
	 */
	private static  String getAttrFromDocument(EcmDocument doc, String attribute) throws Exception {
		String result = "";
		
		if(doc.getAttributeValue(attribute)!=null) {
			int a=attribute.indexOf("DATE");
			if(a!=-1) {
				SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd");
				result=dateFormat2.format(doc.getAttributeValue(attribute));
			}else {
				result = doc.getAttributeValue(attribute).toString();
			}
		}
		return result;
	}
	/***
	 * 
	 * @param doc
	 * @param bookmarkName RELONE_0_应附_c_coding__c_revision
	 * @return
	 * @throws Exception 
	 */
//	private static String getSingleRelValue(IDfSysObject doc, String bookmarkName) throws Exception
//	{
//		String val="";
//		String allStr = bookmarkName.replace("RELONE_", "");//0_应附_c_coding__c_revision
//		String[] strs = allStr.split("_");
//		int row = Integer.parseInt(strs[0]);
//		String relName = strs[1];
//		allStr = allStr.replace(strs[0]+"_"+strs[1]+"_", ""); //c_coding__c_revision
//		strs = allStr.split("__");
//		String dql = "select r_object_id";
//		for(String str:strs)
//		{
//			dql +=","+str;
//		}
//		dql += " from ecm_common where r_object_id in(select CHILD_ID from ecm_relation where relation_name='"
//				+relName+"' and parent_id='"+doc.getObjectId().getId()+"') order by r_object_id";
//		IDfCollection coll = DMUtils.query(doc.getSession(), dql);
//		int i=0;
//		while(coll.next()){
//			if(i==row)
//			{
//				for(String str:strs)
//				{
//					val +=  coll.getString(str)+" ";
//				}
//				val = val.trim();
//				break;
//			}
//			i++;
//		}
//		coll.close();
//		return val;
//	}

	/**
	 * 直接从文档属性获取签名图片
	 * @param pro 
	 */
	private static  String getImageFromDocument(List<ArrayList<String>> list,String userName) throws Exception {
		String imageFilePath ="";
		if(userName==null||userName.trim().length()==0)
			return "";
		if(userName.indexOf(";")!=-1) {
			String[] strs = userName.split(";");
			for (int j = 0; j < strs.length; j++) {
				if(imageFilePath!="") {
					imageFilePath=imageFilePath+";";
				}
				 imageFilePath =imageFilePath+strs[j];
			}
		}
		else {
			imageFilePath=userName;
		}
		return imageFilePath;
	}

	/**
	 * 从工作流获取用户名一个值
	 */
	private static  String getOneUserNameFromWF(List<ArrayList<String>> list, String taskName,int index) throws Exception {
		ArrayList<String> audit  = getAudit(list,  taskName, index);
		if(audit!=null) {
			return audit.get(1);
		}
		return "";
	}

	/**
	 * 从工作流获得多个用户名
	 */
	private static  String getMultipleUserNameFromWF(List<ArrayList<String>> list, String taskName) throws Exception {
		String userName = "";
		
		for(ArrayList<String> strs:list) {
			if(strs.get(0).equals(taskName)) {
				userName += strs.get(1) + ",";
			}
		}
		
		if (userName.contains(",")) {
			userName = userName.substring(0, userName.length() - 1);
		}
		return userName;
	}

	/**
	 * 从工作流获取时间一个值
	 */
	private static  String getOneTimeFromWF( List<ArrayList<String>> list, String taskName,int index) throws Exception {
		
		ArrayList<String> audit  = getAudit(list,  taskName, index);
		if(audit!=null) {
			return audit.get(2);
		}
		return "";
	}
	
	/**
	 * 获取任务日志
	 * @param list
	 * @param taskName
	 * @param index 从0开始计数
	 * @return
	 */
	private static ArrayList<String> getAudit(List<ArrayList<String>> list, String taskName,int index){
		int i=0;
		for(ArrayList<String> strs:list) {
			if(strs.get(0).equals(taskName)&&index==i) {
				return strs;
			} else if(strs.get(0).equals(taskName)) {
				i++;
			}
		}
		return null;
	}
	
	/**
	 * 获取日志数量
	 * @param list
	 * @param taskName
	 * @return
	 */
	private static int getAuditCount(List<ArrayList<String>> list, String taskName) {
		int count = 0;
		for(ArrayList<String> strs:list) {
			if(strs.get(0).equals(taskName)) {
				count ++;
			}
		}
		return count;
	}
//	
//	private static String getSignImage(IDfSysObject image) throws DfException
//	{
//		String 	imageFilePath="";
//		if(!checkPath)
//		{
//			File f = new File(SIGN_TEMP_PATH);
//			if(f!=null&&f.isDirectory())
//			{
//				checkedPath = SIGN_TEMP_PATH;
//			}
//			checkPath = true;
//			System.out.println("Sign Image path:"+checkedPath);
//		}
//		if(checkedPath.length()>0)
//		{
//			imageFilePath = checkedPath+image.getObjectId().getId()+"_"+image.getModifyDate().getDate().getTime()+".jpg";
//			File f = new File(imageFilePath);
//			if(!f.exists())
//			{
//				System.out.println("Sign Image Id:"+image.getObjectId().getId());
//				image.getFileEx(imageFilePath, image.getFormat().getName(), 0, false);
//			}
//		}
//		else
//		{
//			imageFilePath = image.getFile("");
//		}
//		return 	imageFilePath;
//	}

	/**
	 * 从工作流获取签名图片
	 * 
	 * @param session
	 * @throws DfException
	 */
	private static  String getImageFromWF(List<ArrayList<String>> list, String taskName,int index) throws Exception {
		
		ArrayList<String> audit  = getAudit(list,  taskName, index);
		if(audit!=null) {
			return audit.get(1);
		}
		return "";
	}

	/**
	 * 表格处理：通过ID获取
	 * 
	 * @param doc
	 */
	private static  List<ArrayList<String>> getAttrByIdForTable(Object doc) {
		return null;
	}

	/**
	 * 获取所有流程日志
	 * @param doc
	 * @return
	 * @throws DfException
	 */
	public static List<ArrayList<String>> getWorkitemAudits(String processInstanceId,EcmAuditWorkitemMapper ecmAuditWorkitemMapper) throws Exception {
		List<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		try {
			List<EcmAuditWorkitem> tasks = ecmAuditWorkitemMapper.selectByCondition("PROCESS_INSTANCE_ID='" + processInstanceId + "'  and END_TIME is not null order by CREATE_TIME desc");
			for (EcmAuditWorkitem task : tasks) {
				ArrayList<String> array = new ArrayList<String>();
				if(task.getResult().equals("通过")) {
					array.add(task.getTaskName());
					array.add(task.getAssignee());
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
					array.add(task.getEndTime()==null?"":f.format(task.getEndTime()));
					array.add(task.getProcessDefId());
					results.add(array);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} 
		return results;
	}

	
	
	/**
	 * 表格处理：关系查询
	 */
	private static  List<ArrayList<String>> getRelationForTable(Object doc) {
		return null;
	}

	

	/**
	 * 取得配置文件
	 * 
	 * @return
	 */
	public static Properties initPropertiesFile(String filePath) {

		InputStream stream = AttrUtils.class.getResourceAsStream(filePath);
		if (stream == null) {
			throw new RuntimeException("Can't find properties file '" + filePath + "'");
		}
		Properties properties = new Properties();
		try {
			properties.load(new BufferedInputStream(stream));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 处理传入字符串的参数
	 * 
	 * @param innerStr
	 * @return
	 */
	private static  String[] getMultiAttrFromStr(String innerStr) {
		String[] innerArray = innerStr.split("_");
		String endStr = "";
		StringBuilder afterReplate = new StringBuilder();
		for (int i = 3; i < innerArray.length; i++) {
			afterReplate.append(innerArray[i] + "_");
		}
		endStr = afterReplate.toString();
		if (endStr.contains("_")) {
			endStr = endStr.substring(0, endStr.length() - 1);
		}
		return endStr.split("__");
	}

	private static  String getSingleAttrFromStr(String innerStr) {
		String[] innerArray = innerStr.split("_");
		String endStr = "";
		StringBuilder afterReplate = new StringBuilder();
		for (int i = 2; i < innerArray.length; i++) {
			afterReplate.append(innerArray[i] + "_");
		}
		endStr = afterReplate.toString();
		if (endStr.contains("_")) {
			endStr = endStr.substring(0, endStr.length() - 1);
		}
		return endStr;
	}

	private static  String getTaskNames(String innerStr) {
		String[] innerArray = innerStr.split("_");
		String endStr = "";
		StringBuilder afterReplate = new StringBuilder();
		for (int i = 1; i < innerArray.length; i++) {
			afterReplate.append("'" + innerArray[i] + "',");
		}
		endStr = afterReplate.toString();
		if (endStr.contains(",")) {
			endStr = endStr.substring(0, endStr.length() - 1);
		}
		return endStr;
	}

}
