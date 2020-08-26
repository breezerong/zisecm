package com.ecm.core.cache.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmFormItemMapper;
import com.ecm.core.dao.EcmFormMapper;
import com.ecm.core.dao.EcmQueryMapper;
import com.ecm.core.dao.EcmSelectValueMapper;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.entity.EcmQuery;
import com.ecm.core.util.SysConfig;

/**
 * 
 * @ClassName  CacheManagerEcmForm   
 * @Description TODO(表单配置缓存类)   
 * @author Haihong Rong
 * @date 2018年6月26日 上午17:48:16  
 *
 */
@Component
public class CacheManagerEcmForm implements ICacheManager<EcmForm>{
	private static final Logger logger = LoggerFactory.getLogger(CacheManagerEcmForm.class);
	
	@Autowired
	private EcmFormMapper ecmFormMapper;
	
	@Autowired
	private EcmFormItemMapper ecmFormItemMapper;
	
	@Autowired
	private EcmQueryMapper ecmQueryMapper;
	
	@Autowired
	private EcmSelectValueMapper ecmSelectValueMapper;

	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmForms().clear();
		CacheManagerOper.getEcmFormItems().clear();
		List<EcmForm> eps = ecmFormMapper.selectAll();
		
		if(eps != null) {
			for(EcmForm ep:eps) {
				CacheManagerOper.getEcmForms().put(ep.getTypeName()+"_"+ep.getAction(), ep);
				if(ep.getIsDefault()) {
					CacheManagerOper.getEcmForms().put(ep.getTypeName()+"_1", ep);
				}
				
				updateItem( ep);
				
			}
		}
	}
	
	private void updateItem(EcmForm ep) {
		List<EcmFormItem> oldItems = ecmFormItemMapper.selectByParentId(ep.getId());				
		ep.setEcmFormItems(oldItems);
		for(EcmFormItem it:oldItems) {
			CacheManagerOper.getEcmFormItems().put(it.getId(), it);
			//把可查询的字段存放到list里
			if("1".equals(it.getSearchable())) {
				ep.getEcmFormSearchItems().add(it);
			}
			
			if("Department".equals(it.getControlType())){
				it.setControlType("Select");
				it.setValidValues(new ArrayList<String>());
				
				try {
					EcmParameter param = CacheManagerOper.getEcmParameters().get(SysConfig.getDepartmentRootId());
					if(param == null) {
						throw new RuntimeException("系统没有配置参数："+SysConfig.getDepartmentRootId());
					}
					//MemberService memberService = OtAuthentication.getMemberServiceWSBindingProvider(OtStaticValue.getUserTest());
					//Member rootM = memberService.getMemberById(Integer.valueOf(param.getValue()));
					//递归查询部门
					//createDepartment(rootM,it.getValidValues(), memberService);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}else if("SQLSelect".equals(it.getControlType())) {
				it.setControlType("Select");
				it.setValidValues(new ArrayList<String>());
				
				EcmQuery ecmQuery = ecmQueryMapper.selectByName(it.getQueryName());
				if(ecmQuery != null) {
					if(!EcmStringUtils.isEmpty(ecmQuery.getSqlString()))
					{
						List<Map<String,String>> list = ecmSelectValueMapper.selectEcmSelectValuesBySql(ecmQuery.getSqlString());
						for(Map<String,String> m:list){
							it.getValidValues().add(m.get(ecmQuery.getLabelColumn()));
						}
					}
				}
			}else if("ValueSelect".equals(it.getControlType())){
				it.setControlType("Select");
				it.setValidValues(new ArrayList<String>());
				if(!EcmStringUtils.isEmpty(it.getValueList()))
				{
					it.setValidValues(Arrays.asList(it.getValueList().split(";")));
				}
			}
		}
	}

	@Override
	public EcmForm refreshCache(String key) {
		EcmForm ep = CacheManagerOper.getEcmForms().remove(key);
		if(ep != null) {
			ep = ecmFormMapper.selectByPrimaryKey(ep.getId());
			CacheManagerOper.getEcmForms().put(key, ep);
			updateItem(ep);
			return ep;
		}else {
			ep = ecmFormMapper.selectByPrimaryKey(key);
			if(ep != null) {
				CacheManagerOper.getEcmForms().put(ep.getTypeName()+"_"+ep.getAction(), ep);
				if(ep.getIsDefault()) {
					CacheManagerOper.getEcmForms().put(ep.getTypeName()+"_1", ep);
				}
				updateItem(ep);
			}
		}
		return ep;
	}
	
//	private void createDepartment(Member member,List<String> departs,MemberService memberService) {
//		departs.add(member.getName());
//		List<Member> members = memberService.listMembers(member.getID());
//		for(Member m:members) {
//			if("Group".equals(m.getType())) {
//				createDepartment(m,departs,memberService);
//			}
//		}
//	}

}
