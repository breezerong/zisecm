package com.ecm.core.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.common.util.DateUtils;
import com.ecm.core.dao.EcmQueryMapper;
import com.ecm.core.entity.EcmFolder;
/**
 * 目录服务
 * @author Haihong Rong
 * @date 2019年11月18日 上午8:21:10
 */
@Service
public class FolderPathService extends EcmService {
	
	private static final Logger logger = LoggerFactory.getLogger(FolderPathService.class);

	@Autowired
	private EcmQueryMapper ecmQuery;
	@Autowired
	private FolderService folderService;
	
	private static final Pattern attrFormat = Pattern.compile("\\{[^\\}]*\\}");
	
	/**
	 * 获取发布目录
	 * @param token 
	 * @param values 文档属性集
	 * @return
	 * @throws Exception
	 */
	public String getReleaseFolderId(String token, Map<String, Object> values ) throws Exception {
		return getFolderId(token,values, "3");
	}
	/**
	 * 获取整编目录
	 * @param token
	 * @param values 文档属性集
	 * @return
	 * @throws Exception
	 */
	public String getArrangeFolderId(String token, Map<String, Object> values ) throws Exception {
		return getFolderId(token,values, "2");
	}
	/**
	 * 获取预归档目录
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-11-16 17:23:29 
	 * @Description:       
	 * @param token
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public String getPreArchiveFolderId(String token, Map<String, Object> values ) throws Exception {
		return getFolderId(token,values, "4");
	}
	/**
	 * 获取移交目录
	 * @param token
	 * @param values 文档属性集
	 * @return
	 * @throws Exception
	 */
	public String getDeliveryFolderId(String token, Map<String, Object> values ) throws Exception {
		return getFolderId(token,values, "1");
	}
	/**
	 * 获取文件夹ID
	 * @param token
	 * @param values
	 * @param type 1:移交目录，2：整编目录，3：发布目录，4：预归档目录
	 * @return
	 */
	@Transactional
	public String getFolderId(String token, Map<String, Object> values, String type) {	
		// TODO Auto-generated method stub
		String id=null;
		
		
		String typeName = values.get("TYPE_NAME").toString();
		/*
		 * 目录规则
		NAME	名称
		TITLE	条件
		SUB_TYPE	对象类型值
		C_COMMENT	发布目录规则，使用英文分号分隔
		C_STRING1	移交目录，使用英文分号分隔，固定为：/移交库
		C_STRING2	整编目录，使用英文分号分隔
		C_STRING3 	发布目录，使用英文分号分隔
		C_STRING4 	预归档目录，使用英文分号分隔
		 */
		String sql="select ID,TITLE,C_COMMENT,C_STRING1,C_STRING2,C_STRING3,C_STRING4 from ecm_document where SUB_TYPE='"
		 + typeName +"' and TYPE_NAME='目录规则' order by C_ORDER_INDEX ASC";
		
		List<Map<String, Object>> policyList = ecmQuery.executeSQL(sql);
		for(Map<String, Object> policy: policyList) {
			String cond = (String)policy.get("TITLE");
			String folderPolicy = (String)policy.get("C_COMMENT");
			if(type.equals("1")) {
				folderPolicy = (String)policy.get("C_STRING1");
				if(StringUtils.isEmpty(folderPolicy)) {
					EcmFolder fld = folderService.getObjectByPath(token, "/移交库");
					return fld.getId();
				}
			}
			if("2".equals(type)) {
				folderPolicy = (String)policy.get("C_STRING2");
			}
			else if("3".equals(type)) {
				folderPolicy = (String)policy.get("C_STRING3");
			}
			else if("4".equals(type)) {
				folderPolicy = (String)policy.get("C_STRING4");
			}
			if(!StringUtils.isEmpty(cond)) {
				if(conditionExcute(values,cond)) {
					id = createFolder(token, values, folderPolicy);
					break;
				}
			}else if(policyList.size()==1 || StringUtils.isEmpty(cond)){
				id = createFolder(token, values, folderPolicy);
				break;
			}
		}
		return id;
	}
	@Transactional
	public String getFolderByPath(String token,String folderPath) {
		EcmFolder fld = folderService.getObjectByPath(token, folderPath);
		String fldId = "";
		if(fld!=null) {
			fldId = fld.getId();
		}else {
			String[] names = folderPath.split("/");
			String fullPath ="";
			String parentId ="0";
			for(int i=1;i<names.length;i++) {
				fullPath += "/"+names[i];
				fld = folderService.getObjectByPath(token, fullPath);
				if(fld == null) {
					fld = new EcmFolder();
					fld.setName(names[i]);
					fld.setParentId(parentId);
					parentId = folderService.newObject(token, fld);
				}
				parentId = fld.getId();
			}
			fldId = parentId;
		}
		return fldId;
	}
	
	private String createFolder(String token,Map<String, Object> values, String policy) {
		String[] strs = policy.split(";");
		String folderPath="";
		for(String str:strs) {
			if(str.startsWith("{")) {
				String attrName = str.replace("{", "").replace("}", "");
				if(values.get(attrName)==null){
					logger.info("ID:"+values.get("ID"));
					logger.info("Property :"+attrName+", value is null..");
				}
				folderPath +=  ((String)values.get(attrName)).replace("/", "-");
			}
			else if(str.startsWith("Date(")) {
				String tempStr = str.replace("Date(", "").replace(")", "");
				String[] temps = tempStr.split(",");
				String attrName = temps[0];
				String format = temps[1];
				Date dt = new Date();
				if(!attrName.equalsIgnoreCase("now")) {
					try {
						dt = (Date)values.get(attrName);	
					}
					catch(Exception ex) {
						try {
							dt = DateUtils.StrToDate(values.get(attrName).toString(), "yyyy-MM-dd HH:mm:ss");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}			
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				folderPath +=  sdf.format(dt);
			}
			
			else {
				folderPath +=  str;
			}
		}
		EcmFolder fld = folderService.getObjectByPath(token, folderPath);
		if(fld!=null) {
			return fld.getId();
		}else {
			String[] names = folderPath.split("/");
			String fullPath ="";
			String parentId ="0";
			for(int i=1;i<names.length;i++) {
				fullPath += "/"+names[i];
				fld = folderService.getObjectByPath(token, fullPath);
				if(fld == null) {
					fld = new EcmFolder();
					fld.setName(names[i]);
					fld.setParentId(parentId);
					parentId = folderService.newObject(token, fld);
				}
				parentId = fld.getId();
			}
			return parentId;
		}
	}
	
	/**
	 * 执行条件表达式
	 * @param attrValues
	 * @param codition
	 * @return
	 */
	public  Boolean conditionExcute(Map<String, Object> attrValues, String codition){
		StringBuffer str = new StringBuffer();
		String the_receiving = null;
		if (!StringUtils.isEmpty(codition)) {
			String var = codition;
			// 适用表达式匹配
			Matcher m = attrFormat.matcher(codition);
			while (m.find()) {
				String attrName = m.group().replace("{", "").replace("}", "").toUpperCase();
				the_receiving = (String) attrValues.get(attrName);
				var = var.replace("{" + attrName + "}", the_receiving);
			}
			str.append(var);
		}
		Boolean flg = excuteCondition(str.toString());
		return flg;
	}
	/**
	 * 执行JavaScript表达式
	 * @param cond
	 * @return
	 */
	private static boolean excuteCondition(String cond) {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		try {
			return engine.eval(cond).toString().equals("true");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void buildFullPath(String token, String id) {
		EcmFolder fld = folderService.getObjectById(token, id);
		fld.setFolderPath(folderService.getFullPath(token, fld, fld.getName()));
		folderService.updateObject(token, fld);
		List<EcmFolder> fldList = folderService.getFoldersByParentId(token, fld.getId());
		for(EcmFolder childFolder: fldList) {
			buildFullPath(token,childFolder.getId());
		}
	}
}
