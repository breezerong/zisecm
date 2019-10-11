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

import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmFormItemMapper;
import com.ecm.core.entity.EcmDefType;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmStore;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.AttributeService;
import com.ecm.core.service.DefTypeService;
import com.ecm.core.service.FormItemService;
import com.ecm.core.service.FormService;
import com.ecm.core.service.StoreService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 表单控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class StoreController extends ControllerAbstract{

	/**
	 * 表单数据访问
	 */
	@Autowired
	private StoreService storeService;


	/**
	 * 获取所有类型定义
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin/getStore")
	public Map<String, Object> getStore() {
		List<EcmStore> list;
		Map<String, Object> mp = new HashMap<String, Object>();
		list = storeService.getAllObject(getToken());
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}

	

	/**
	 * 更新
	 * @param obj 实体    
	 * @return
	 */
	@RequestMapping(value = "/admin/updateStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStore(@RequestBody EcmStore obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			storeService.updateObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

	

	/**
	 * 删除
	 * 
	 * @param obj 实体
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStore(@RequestBody EcmStore obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if(storeService.deleteObject(getToken(),obj)) {
				mp.put("code", ActionContext.SUCESS);
			}else
			{
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		
		return mp;
	}

	/**
	 * 新建
	 * 
	 * @param obj 实体
	 * @return
	 */
	@RequestMapping(value = "/admin/newStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newStore(@RequestBody EcmStore obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			storeService.newObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

}
