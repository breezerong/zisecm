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
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AttributeService;
import com.ecm.core.service.DefTypeService;
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
public class DefTypeController extends ControllerAbstract{

	/**
	 * 表单数据访问
	 */
	@Autowired
	private DefTypeService typeService;

	/**
	 * 获取所有类型定义
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin/getType")
	public Map<String, Object> getType() {
		List<EcmDefType> list;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			list = typeService.getAllObject(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

	

	/**
	 * 更新
	 * @param obj 实体    
	 * @return
	 */
	@RequestMapping(value = "/admin/updateType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateType(@RequestBody EcmDefType obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			typeService.updateObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

	/**
	 * 复制
	 * 
	 * @param obj 实体
	 * @return
	 */
	@RequestMapping(value = "/admin/copyType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyType(@RequestBody EcmDefType obj) {

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if(typeService.copy(getToken(),obj)) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
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
	@RequestMapping(value = "/admin/deleteType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteType(@RequestBody EcmDefType obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if(typeService.deleteObject(getToken(),obj)) {
				mp.put("code", ActionContext.SUCESS);
			}else
			{
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
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
	@RequestMapping(value = "/admin/newType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newType(@RequestBody EcmDefType obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			obj.createId();
			typeService.newObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

}
