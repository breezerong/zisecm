package com.ecm.portal.controller.admin;

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

import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmFormItemMapper;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.FormItemService;
import com.ecm.core.service.FormService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 表单控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class FromManager extends ControllerAbstract{

	/**
	 * 表单数据访问
	 */
	@Autowired
	private FormService formService;

	@Autowired
	private FormItemService formItemService;

	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin/getForm")
	public Map<String, Object> getForm() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmForm> list = formService.getAllObject(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	
	@ResponseBody
	@RequestMapping("/admin/getFormObj/{typeName}/{actionName}")
	public Map<String,Object> getFormObj(@PathVariable("typeName") String typeName,@PathVariable("actionName") String actionName){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", this.getECMForm(typeName, actionName));
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	/**
	 * 获取默认表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin/getDefaultForm")
	public Map<String, Object> getDefaultForm() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmForm> list = formService.getAllObject(getToken());
			for(int i=list.size()-1;i>=0;i--)
			{
				if(!"1".equals(list.get(i).getIsDefault()))
				{
					list.remove(list.get(i));
				}
			}
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 更新表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 */
	@RequestMapping(value = "/admin/updateForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateForm(@RequestBody EcmForm obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			formService.updateObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 复制表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 */
	@RequestMapping(value = "/admin/copyForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyForm(@RequestBody EcmForm obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String name = obj.getTypeName() + " Copy";
			String action = obj.getAction();
			String fromId = obj.getId();
			obj.createId();
			obj.setTypeName(name);
			formService.newObject(getToken(),obj);
	
			obj = getECMForm(name, action);
			if (obj != null) {
				List<EcmFormItem> items = formItemService.getFormItems(getToken(),fromId);
				for (EcmFormItem en : items) {
					en.createId();
					en.setParentId(obj.getId());
					formItemService.newObject(getToken(),en);
				}
				mp.put("code", ActionContext.SUCESS);
			} else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 删除表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteForm(@RequestBody EcmForm obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmFormItem> items = formItemService.getFormItems(getToken(),obj.getId());
			for (EcmFormItem en : items) {
				formItemService.deleteObject(getToken(),en);
			}
			formService.deleteObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 新建表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 */
	@RequestMapping(value = "/admin/newForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newForm(@RequestBody EcmForm obj) {
		obj.createId();
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			formService.newObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		//System.out.println("id:" + id);
		
		
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

}
