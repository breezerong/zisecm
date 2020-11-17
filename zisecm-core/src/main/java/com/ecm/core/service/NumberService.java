package com.ecm.core.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.dao.EcmQueryMapper;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.exception.EcmException;
/**
 * 取号服务
 * @author Haihong Rong
 * @date 2019年11月17日 上午8:38:39
 */
@Service
public class NumberService extends EcmService {

	@Autowired
	private EcmQueryMapper ecmQuery;
//	@Autowired
//	private DocumentService documentService;
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private FolderService folderService;
	
	private static final Pattern attrFormat = Pattern.compile("\\{[^\\}]*\\}");
	
	@Transactional
	public String getNumber(String token, Map<String, Object> values ) throws Exception {
		// TODO Auto-generated method stub
		String num=null;
		
		String typeName = values.get("TYPE_NAME").toString();
		/*
		取号规则
		字段	说明
		NAME	名称
		TITLE	条件
		C_COUNT1	起始号
		SUB_TYPE	取号对象类型值
		C_COMMENT	取号规则，使用英文分号分隔
		 */
		String sql="select ID,TITLE,C_COUNT1,C_COMMENT from ecm_document where SUB_TYPE='"
		 + typeName +"' and TYPE_NAME='取号规则' order by C_ORDER_INDEX ASC";
		
		List<Map<String, Object>> policyList = ecmQuery.executeSQL(sql);
		if(policyList==null||policyList.size()==0) {
			throw new Exception("没有取号规则，请检查！");
		}
		for(Map<String, Object> policy: policyList) {
			String cond = (String)policy.get("TITLE");
			String numPolicy = (String)policy.get("C_COMMENT");
			int startIndex = 1;
			try {
				startIndex = Integer.parseInt((String)policy.get("C_COUNT1"));
			}catch(Exception ex) {
				
			}
			if(!StringUtils.isEmpty(cond)) {
				if(conditionExcute(values,cond)) {
					num = createNumber(token, values, numPolicy,startIndex);
					break;
				}
			}else if(policyList.size()==1){
				num = createNumber(token, values, numPolicy,startIndex);
				break;
			}
		}
		return num;
	}
	
	private String createNumber(String token,Map<String, Object> values, String policy,int startIndex) throws Exception {
		String typeName = values.get("TYPE_NAME").toString();
		String[] strs = policy.split(";");
		String prefix="";
		int numberLen = 4;
		String numberStr ="";
		int currentIndex = startIndex;
		for(String str:strs) {
			if(str.startsWith("{")) {
				String attrName = str.replace("{", "").replace("}", "");
				if(values.get(attrName)==null) {
					throw new Exception(attrName+"为空！");
				}
				prefix +=  (String)values.get(attrName);
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
					}catch (Exception e) {
						// TODO: handle exception
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						dt=formatter.parse((String) values.get(attrName));
					}
				}
				if(dt==null) {
					throw new Exception(attrName+"为空！");
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				prefix +=  sdf.format(dt);
			}
			else if(str.startsWith("$N")) {
				numberStr = str;
				numberLen = str.length()-2;
				prefix +=  str;
			}
			else if(str.startsWith("Coding(")) {
				String tempStr = str.replace("Coding(", "").replace(")", "");
				String[] temps = tempStr.split(",");
				String attrName = temps[0];
				String queryName = temps[1];
				if(values.get(attrName)==null) {
					throw new Exception(attrName+"为空！");
				}
				String val = (String)values.get(attrName);
				
				prefix += getCoding( val,queryName);
			}
			else if(str.startsWith("FUN_DesignPhase")) {
				if(values.get("SUB_TYPE")==null) {
					throw new Exception("SUB_TYPE为空！");
				}
				if(values.get("C_PHASE")==null) {
					throw new Exception("C_PHASE为空！");
				}
				String subType = (String)values.get("SUB_TYPE");
				String phase = (String)values.get("C_PHASE");
				prefix += getDesignPhase(token, subType, phase);
			}
			else {
				prefix +=  str;
			}
		}
		/*
		 * NAME	前缀
			C_COUNT1	当前流水号
			SUB_TYPE	取号对象类型值
		 */
		String sql = "select ID,C_COUNT1 from ecm_document where TYPE_NAME='取号流水号' and NAME='"+prefix+"' and SUB_TYPE='"+typeName+"'";
		List<Map<String, Object>> numList = ecmQuery.executeSQL(sql);
		if(numList.size()>0) {
			currentIndex =  Integer.parseInt(numList.get(0).get("C_COUNT1").toString());
			currentIndex ++;
			do {
				String validateSql="select ID from ecm_document  where ID='"+numList.get(0).get("ID").toString()+"' and C_COUNT1="+currentIndex;
				List<Map<String, Object>> result= ecmQuery.executeSQL(validateSql);
				if(result==null||result.size()==0) {
					break;
				}else {
					currentIndex++;
				}
			}while(true);
			
			sql = "update ecm_document set C_COUNT1="+currentIndex+" where ID='"+numList.get(0).get("ID").toString()+"'";
			 ecmQuery.executeSQL(sql);
		}else {
			EcmFolder fld = folderService.getObjectByPath(token, "/系统配置/取号流水号");
//			EcmDocument doc = new EcmDocument();
//			doc.setTypeName("取号流水号");
//			doc.setName(prefix);
//			doc.setSubType(typeName);
//			doc.getAttributes().put("C_COUNT1", currentIndex);
//			doc.setFolderId(fld.getId());
//			documentService.newObject(token, doc, null);
			
			String insertSql="insert into ecm_document (ID,TYPE_NAME,NAME,SUB_TYPE,C_COUNT1,FOLDER_ID) "
					+ "values("+UUID.randomUUID().toString().replace("-", "")+",'取号流水号','"+prefix+"','"+typeName+"',"
							+currentIndex+",'"+fld.getId()+"')";
			ecmDocument.executeSQL(insertSql);
		}
		String num = String.format("%0"+numberLen+"d",currentIndex);
		return prefix.replace(numberStr, num);
	}
	/**
	 * 根据查询条件获取代码
	 * @param val
	 * @param queryName
	 * @return
	 */
	private String getCoding(String val,String queryName) {
		String sql = "SELECT LABEL_COLUMN, VALUE_COLUMN, SQL_STRING, ID, DEPEND_NAMES " + 
				"FROM ecm_query where NAME='"+queryName+"'";
		List<Map<String, Object>> qList = ecmQuery.executeSQL(sql);
		if(qList.size()>0) {
			sql = qList.get(0).get("SQL_STRING").toString();
			String labelColumn = qList.get(0).get("LABEL_COLUMN").toString();
			String valueColumn = qList.get(0).get("VALUE_COLUMN").toString();
			List<Map<String, Object>> valueList = ecmQuery.executeSQL(sql);
			for(Map<String, Object> item: valueList) {
				String name = item.get(labelColumn).toString();
				if(name.equals(val)) {
					return item.get(valueColumn).toString();
				}
			}
		}
		return "";
		
	}
	/**
	 * 获取设计阶段
	 * @param token
	 * @param subType
	 * @param phase
	 * @return
	 * @throws EcmException
	 */
	private String getDesignPhase(String token, String subType,String phase) throws EcmException,Exception {
		String path = "/系统配置/设计阶段/"+subType;
		EcmFolder folder = folderService.getObjectByPath(token, path);
		if(folder==null) {
			throw new Exception(path+"为空!");
		}
		String condition = "FOLDER_ID='"+folder.getId()+"' and NAME='"+phase+"'";
//		List<Map<String,Object>> docList = documentService.getObjectMap(token, condition);
		String sql = "select "+getDocumentAllColumns()+" from ecm_document where " + condition;
		List<Map<String,Object>> docList = ecmDocument.executeSQL(sql);
		if(docList.size()>0) {
			return (String)docList.get(0).get("CODING");
		}
		return "";
	}
	
	/**
	 * 获取文档所有列
	 * @return
	 */
	private String getDocumentAllColumns() {
		String cols = "";
		for(Entry<String, EcmAttribute> attr :CacheManagerOper.getEcmAttributes().entrySet()) {
			if(cols.length()==0) {
				cols = attr.getValue().getName();
			}else {
				cols += ","+attr.getValue().getName();
			}
		}
		return cols;
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

	
}
