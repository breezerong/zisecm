package com.ecm.portal.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmCardSearchItemMapper;
import com.ecm.core.dao.EcmFormItemMapper;
import com.ecm.core.dao.EcmQueryMapper;
import com.ecm.core.dao.EcmSelectValueMapper;
import com.ecm.core.entity.EcmCardSearchItem;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.entity.EcmQuery;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.entity.ExEcmCardSearchItem;
import com.ecm.core.util.SysConfig;


/**
 * 卡片表单项控制器
 * @author Administrator
 *
 */
@Controller
public class CardSearchItemManager extends ControllerAbstract{
	
	/**
	 * 表单项数据访问
	 */
	@Autowired
	private EcmFormItemMapper ecmFormItem;
	
	/**
	 * 卡片表单项数据访问
	 */
	@Autowired
	private EcmCardSearchItemMapper ecmCardSearchItem;
	
	@Autowired
	private EcmQueryMapper ecmQueryMapper;
	
	@Autowired
	private EcmSelectValueMapper ecmSelectValueMapper;
	
	/**
	 * 获取所有卡片表单项
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value="/admin/getCardSearchItem", method = RequestMethod.POST)
	 public   Map<String, Object>  getCardSearchItem(@RequestBody String parentId) {
		 List<EcmCardSearchItem> list0 =null;//
		 if(parentId!=null&&parentId.trim().length()>0&&!"".equals(parentId)&&!"0".equals(parentId))
		 {
			 list0 = ecmCardSearchItem.selectByParentId(parentId);
		 }
		 else
		 {
			 list0 = ecmCardSearchItem.selectAll();
		 }
		 List<ExEcmCardSearchItem> list = merger(list0);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 mp.put("data", list);
		 return mp;
	 }
	 
	 /**
	  * 更新卡片表单项
	  * @param obj 卡片表单项实例
	  * @return
	  */
	 @RequestMapping(value="/admin/updateCardSearchItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateCardSearchItem(@RequestBody  ExEcmCardSearchItem obj) {
		 ecmCardSearchItem.updateByPrimaryKey(obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 删除卡片表单项
	  * @param obj 卡片表单项实例
	  * @return
	  */
	 @RequestMapping(value="/admin/deleteCardSearchItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteCardSearchItem(@RequestBody  ExEcmCardSearchItem obj) {
		 ecmCardSearchItem.deleteByPrimaryKey(obj.getId());
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 新建卡片表单项
	  * @param obj 卡片表单项实例
	  * @return
	  */
	 @RequestMapping(value="/admin/newCardSearchItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newCardSearchItem(@RequestBody  EcmCardSearchItem obj) {
		 obj.createId();
		 ecmCardSearchItem.insert(obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 合并属性标签和名称
	  * @param fromList
	  * @return
	  */
	 private List<ExEcmCardSearchItem> merger(List<EcmCardSearchItem> fromList)
	 {
		 List<ExEcmCardSearchItem> list = new ArrayList<ExEcmCardSearchItem>();
		 for(EcmCardSearchItem fromEn:fromList)
		 {
			 ExEcmCardSearchItem en = new ExEcmCardSearchItem();
			 en.setParentId(fromEn.getParentId());
			 en.setId(fromEn.getId());
			 en.setFormItemId(fromEn.getFormItemId());
			 en.setOrderIndex(fromEn.getOrderIndex());
			 EcmFormItem formItem = ecmFormItem.selectByPrimaryKey(en.getFormItemId());
			 initValueList(formItem);
			 en.setAttrName(formItem.getAttrName());
			 en.setLabel(formItem.getLabel());
			 en.setControlType(formItem.getControlType());
			 en.setDefaultValue(formItem.getDefaultValue());
			 en.setRequired(formItem.getRequired());
			 en.setValidValues(formItem.getValidValues());
			 list.add(en);
		 }
		 return list;
	 }
	 
	 private void initValueList(EcmFormItem it)
	 {
		 if("Department".equals(it.getControlType())){
				it.setControlType("Select");
				it.setValidValues(new ArrayList<String>());
				
				try {
					EcmParameter param = CacheManagerOper.getEcmParameters().get(SysConfig.getDepartmentRootId());
					if(param == null) {
						throw new RuntimeException("系统没有配置参数："+SysConfig.getDepartmentRootId());
					}
//					MemberService memberService = OtAuthentication.getMemberServiceWSBindingProvider(OtStaticValue.getUserTest());
//					Member rootM = memberService.getMemberById(Integer.valueOf(param.getValue()));
//					//递归查询部门
//					createDepartment(rootM,it.getValidValues(), memberService);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}else if("SQLSelect".equals(it.getControlType())) {
				it.setControlType("Select");
				it.setValidValues(new ArrayList<String>());
				
				String queryName = it.getQueryName();
				EcmQuery ecmQuery = ecmQueryMapper.selectByName(queryName);
				if(ecmQuery != null) {
					List<Map<String,String>> list = ecmSelectValueMapper.selectEcmSelectValuesBySql(ecmQuery.getSqlString());
					for(Map<String,String> m:list){
						it.getValidValues().add(m.get(ecmQuery.getLabelColumn()));
					}
				}
			}else {
				//MergeFormItemCommon.merger(it);
			}
	 }
//	 private void createDepartment(Member member,List<String> departs,MemberService memberService) {
//			departs.add(member.getName());
//			List<Member> members = memberService.listMembers(member.getID());
//			for(Member m:members) {
//				if("Group".equals(m.getType())) {
//					createDepartment(m,departs,memberService);
//				}
//			}
//		}

}
