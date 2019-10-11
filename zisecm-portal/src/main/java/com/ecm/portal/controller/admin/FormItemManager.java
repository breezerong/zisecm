package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.service.FormItemService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 表单项控制器
 * @author Administrator
 *
 */
@Controller
public class FormItemManager extends ControllerAbstract{
	/**
	 * 表单项数据访问
	 */
	@Autowired
	private FormItemService formItemService;
	
	/**
	 * 获取所有表单项
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value="/admin/getFormItem", method = RequestMethod.POST)
	 public   Map<String, Object>  getFormItem(@RequestBody String parentId) {
		 parentId = parentId.replace("\"", "");
		 List<EcmFormItem> list =null;//
		 if(parentId!=null&&parentId.trim().length()>0&&!"0".equals(parentId)&&!"".equals(parentId))
		 {
			 list = formItemService.getFormItems(getToken(),parentId);
		 }
		 else
		 {
			 list = formItemService.getAllObject(getToken());
		 }
//		for(EcmFormItem it:list) {
//			MergeFormItemCommon.merger(it);
//		}
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 mp.put("data", list);
		 return mp;
	 }
	 
	 /**
	  * 更新表单项
	  * @param obj 表单项实例
	  * @return
	  */
	 @RequestMapping(value="/admin/updateFormItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateFormItem(@RequestBody  EcmFormItem obj) {
		 formItemService.updateObject(getToken(),obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 删除表单项
	  * @param obj 表单项实例
	  * @return
	  */
	 @RequestMapping(value="/admin/deleteFormItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteFormItem(@RequestBody  EcmFormItem obj) {
		 formItemService.deleteObject(getToken(),obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 新建表单项
	  * @param obj 表单项实例
	  * @return
	  */
	 @RequestMapping(value="/admin/newFormItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newFormItem(@RequestBody  EcmFormItem obj) {
		 obj.createId();
		 formItemService.newObject(getToken(),obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }

}
