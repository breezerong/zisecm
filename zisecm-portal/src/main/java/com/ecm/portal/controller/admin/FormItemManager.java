package com.ecm.portal.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.impl.CacheManagerEcmForm;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormClassification;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.FormItemService;
import com.ecm.core.service.FormService;
import com.ecm.core.service.GridViewService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 表单项控制器
 * @author Administrator
 *
 */
@Controller
public class FormItemManager extends ControllerAbstract{
	/**
	 * 表单数据访问
	 */
	@Autowired
	private FormService formService;
	/**
	 * 表单项数据访问
	 */
	@Autowired
	private FormItemService formItemService;
	
	@Autowired
	private CacheManagerEcmForm cacheManagerEcmForm;
	
	@Autowired
	private GridViewService ecmGridView;
	
	@ResponseBody
	@RequestMapping("/admin/getFormItems/{gridviewName}/{language}")
	public Map<String,Object> getFormItemsByGridViewName(@PathVariable("gridviewName") String gridviewName,@PathVariable("language") String language){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmGridView gObj = ecmGridView.getObjectByName(getToken(), gridviewName);
			EcmForm formObj = this.getECMForm(gObj.getTypeName(), "NEW");
			String formId = formObj.getId();
			List<EcmFormItem> list = formItemService.getFormItems(getToken(),formId);
			List<EcmFormItem> datalist = new ArrayList<EcmFormItem>();
			for (EcmFormItem item : list) {
				datalist.add(item.clone(language));
			}
			
			mp.put("code", ActionContext.SUCESS);
			
			mp.put("data", datalist); 
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	/**
	 * 跟他讲名称和事件获取对象
	 * 
	 * @param name
	 * @param action
	 * @return
	 * @throws AccessDeniedException 
	 */
	private EcmForm getECMForm(String name, String action) throws AccessDeniedException {
		List<EcmForm> list = formService.getAllObject(getToken());
		for (EcmForm frm : list) {
			if (frm.getTypeName().equals(name) && frm.getAction().equals(action))
				return frm;
		}
		return null;
	}
	
	/**
	 * 获取所有表单项
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value="/admin/getFormItem", method = RequestMethod.POST)
	 public   Map<String, Object>  getFormItem(@RequestBody String parentId) {
		 parentId = parentId.replace("\"", "");
		 List<EcmFormItem> list =null;//
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
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
			 
			 mp.put("code", ActionContext.SUCESS);
			 mp.put("data", list);
		 } catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
		 }
		 return mp;
	 }
	 
	 @ResponseBody
		@RequestMapping(value = "/admin/getFormItemByLang", method = RequestMethod.POST)
		public Map<String, Object> getFormItemByLang(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String id = args.get("id").toString();
			String lang = args.get("lang").toString();
			List<EcmFormClassification> list = null;
			Map<String, Object> mp = new HashMap<String, Object>();
			try {
				EcmForm frm =  cacheManagerEcmForm.refreshCache(id);
				if (frm != null) {
					list = frm.getFormClassifications(getSession(), lang);
					mp.put("code", ActionContext.SUCESS);
				}	
			} catch (Exception ex) {
				mp.put("code", ActionContext.FAILURE);
			}
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
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			formItemService.updateObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		}
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
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			 formItemService.deleteObject(getToken(),obj);
		 mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException e) {
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			}
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
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			 obj.createId();
			 formItemService.newObject(getToken(),obj);
			 mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		}
		 return mp;
	 }

}
