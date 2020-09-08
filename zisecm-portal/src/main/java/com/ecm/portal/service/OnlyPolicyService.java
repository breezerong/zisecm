package com.ecm.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoOnlyPolicyException;
import com.ecm.core.service.DocumentService;
@Service
@Scope("prototype")
public class OnlyPolicyService extends DocumentService{
	/**
	 * 验证数据唯一（主文件或子文件）
	 * @param token
	 * @param data
	 * @return
	 * @throws NoOnlyPolicyException
	 * @throws EcmException
	 */
	public boolean validateOnlyOne(String token,Map<String,Object> data) throws NoOnlyPolicyException, EcmException {
		
		Object pid=data.get("parentDocId");
		if(pid!=null&&!"".equals(pid.toString())) {
			return validateChildOnly(token,data);
		}
		return validate(token,data);
		
	}
	/**
	 * 验证子文件数据唯一
	 * @param token
	 * @param data
	 * @return
	 * @throws NoOnlyPolicyException
	 * @throws EcmException
	 */
	public boolean validateChildOnly(String token,Map<String,Object> data) throws NoOnlyPolicyException, EcmException {
		String typeName=data.get("TYPE_NAME").toString();
		String parentID=data.get("parentDocId").toString();
		String sql="select * from ( " + 
				"select a.*,b.PARENT_ID from ecm_document a,ecm_relation b "
				+ "where a.id=b.CHILD_ID and a.TYPE_NAME='"+typeName+"' and b.parent_id='"+parentID+"'" + 
				") t where  ";
		
//		if(!StringUtils.isEmpty(parentID)) {
//			
//		}
		String condition=getConditionByConfig(token,data);
		sql+=condition;
		List<Map<String, Object>> result= this.getMapList(token, sql);
		if(result==null||result.size()==0) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * 验证数据唯一
	 * @param token
	 * @param condition
	 * @return
	 * @throws NoOnlyPolicyException 
	 */
	public boolean validate(String token,Map<String,Object> data) throws NoOnlyPolicyException {
		String condition=getConditionByConfig(token,data);
		List<Map<String,Object>> result= this.getObjectMap(token, condition);
		if(result!=null&&result.size()>0) {
			return false;
		}
		return true;
	}
	/**
	 * 获取判断唯一的规则
	 * @param token
	 * @param data
	 * @return
	 * @throws NoOnlyPolicyException
	 */
	private String getConditionByConfig(String token,Map<String,Object> data) throws NoOnlyPolicyException {
		String typeName=data.get("TYPE_NAME").toString();
		String condition=" TYPE_NAME='唯一性规则' and SUB_TYPE='"+typeName+"'";
		List<Map<String, Object>> onlyPolicys= this.getObjectMap(token, condition);
		if(onlyPolicys==null||onlyPolicys.size()==0) {
			throw new NoOnlyPolicyException("此类型“"+typeName+"”没有唯一性规则！");
		}
		String docCondition=" TYPE_NAME='"+typeName+"'";
		if(data.get("ID")!=null) {
			docCondition+=" and ID !='"+data.get("ID").toString()+"'";
		}
		String whereSql="";
		if(onlyPolicys!=null&&onlyPolicys.size()>0) {
			Map<String,Object> onlyPolicy= (Map<String,Object>)onlyPolicys.get(0);
			Object policy= onlyPolicy.get("C_COMMENT");
			if(policy!=null) {
				String rulesStr=policy.toString();
				String[] rules= rulesStr.split(";");
				for(int i=0;i<rules.length;i++) {
					String rule = rules[i];
					rule=rule.substring(rule.indexOf("{")+1,rule.indexOf("}"));
					if(data.get(rule)!=null) {
						if(i==rules.length-1) {
							whereSql+=rule+"='"+data.get(rule).toString()+"'";
						}else {
							whereSql+=rule+"='"+data.get(rule).toString()+"' and ";
						}
						
					}
				}
			}
		}
		if(!"".equals(whereSql)) {
			docCondition+=" and ("+whereSql+")";
		}
		return docCondition;
		
	}
	
	
}
