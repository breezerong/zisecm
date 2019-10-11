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

import com.ecm.core.dao.EcmFormItemMapper;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
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
		List<EcmForm> list = formService.getAllObject(getToken());
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("success", true);
		mp.put("data", list);
		return mp;
	}

	
	@ResponseBody
	@RequestMapping("/admin/getFormObj/{typeName}/{actionName}")
	public Map<String,Object> getFormObj(@PathVariable("typeName") String typeName,@PathVariable("actionName") String actionName){
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("success", true);
		mp.put("data", this.getECMForm(typeName, actionName));
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
		List<EcmForm> list = formService.getAllObject(getToken());
		for(int i=list.size()-1;i>=0;i--)
		{
			if(!"1".equals(list.get(i).getIsDefault()))
			{
				list.remove(list.get(i));
			}
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("success", true);
		mp.put("data", list);
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
		formService.updateObject(getToken(),obj);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("success", true);
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

		String name = obj.getTypeName() + " Copy";
		String action = obj.getAction();
		String fromId = obj.getId();
		obj.createId();
		obj.setTypeName(name);
		formService.newObject(getToken(),obj);

		obj = getECMForm(name, action);
		Map<String, Object> mp = new HashMap<String, Object>();
		if (obj != null) {
			List<EcmFormItem> items = formItemService.getFormItems(getToken(),fromId);
			for (EcmFormItem en : items) {
				en.createId();
				en.setParentId(obj.getId());
				formItemService.newObject(getToken(),en);
			}
			mp.put("success", true);
		} else {
			mp.put("success", false);
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

		List<EcmFormItem> items = formItemService.getFormItems(getToken(),obj.getId());
		for (EcmFormItem en : items) {
			formItemService.deleteObject(getToken(),en);
		}
		formService.deleteObject(getToken(),obj);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("success", true);
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
		formService.newObject(getToken(),obj);
		//System.out.println("id:" + id);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("success", true);
		return mp;
	}

	/**
	 * 跟他讲名称和事件获取对象
	 * 
	 * @param name
	 * @param action
	 * @return
	 */
	private EcmForm getECMForm(String name, String action) {
		List<EcmForm> list = formService.getAllObject(getToken());
		for (EcmForm frm : list) {
			if (frm.getTypeName().equals(name) && frm.getAction().equals(action))
				return frm;
		}
		return null;
	}

}
