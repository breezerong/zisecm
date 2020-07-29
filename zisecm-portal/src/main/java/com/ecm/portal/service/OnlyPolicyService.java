package com.ecm.portal.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.service.DocumentService;
@Service
@Scope("prototype")
public class OnlyPolicyService extends DocumentService{
	public boolean validateOnlyOne(String token,String typeName,Map<String,Object> data) {
		
		
		
		return false;
		
	}
	
	
	
	private String getConditionByConfig(String token,String typeName,Map<String,Object> data) {
		String condition=" TYPE_NAME='唯一性规则' and SUB_TYPE='"+typeName+"'";
		List<Map<String, Object>> onlyPolicys= this.getObjectMap(token, condition);
		
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
				for(String rule : rules) {
					rule=rule.substring(rule.indexOf("{")+1,rule.indexOf("}"));
					if(data.get(rule)!=null) {
						whereSql+=rule+"='"+data.get(rule).toString()+"'";
					}
				}
			}
		}
		if(!"".equals(whereSql)) {
			docCondition+=" and ("+whereSql+")";
		}
		return docCondition;
		
	}
	
	public boolean validateOnlyByParentID(String token,String typeName,String id) {
		return false;
		
	}
}
