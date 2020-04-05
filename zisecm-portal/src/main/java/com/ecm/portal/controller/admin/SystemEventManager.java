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
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmSystemEvent;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.FormItemService;
import com.ecm.core.service.FormService;
import com.ecm.core.service.SystemEventService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 系统事件管理
 * @author Haihong Rong
 * Date:2020年4月5日 下午12:08:35
 */
@Controller
public class SystemEventManager extends ControllerAbstract{

	/**
	 * 表单数据访问
	 */
	@Autowired
	private SystemEventService systemEventService;


	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/admin/sysevent",method = RequestMethod.GET)
	public Map<String, Object> getEvents() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmSystemEvent> list = systemEventService.getAllObject(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	
	@ResponseBody
	@RequestMapping(value="/admin/sysevent/{id}",method = RequestMethod.GET)
	public Map<String,Object> getEvent(@PathVariable("id") String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", systemEventService.getObjectById(getToken(), id));
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	

	/**
	 * 更新事件
	 * 
	 * @param obj
	 *            实体
	 * @return
	 */
	@RequestMapping(value = "/admin/sysevent", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> updateEvent(@RequestBody EcmSystemEvent obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			systemEventService.updateObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	
	/**
	 * 删除
	 * 
	 * @param obj
	 *            实体
	 * @return
	 */
	@RequestMapping(value = "/admin/sysevent/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteEvent(@PathVariable("id") String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			
			systemEventService.deleteObjectById(getToken(),id);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
		}
		return mp;
	}

	/**
	 * 新建
	 * 
	 * @param obj
	 *            实体
	 * @return
	 */
	@RequestMapping(value = "/admin/sysevent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newEvent(@RequestBody EcmSystemEvent obj) {
		obj.createId();
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			systemEventService.newObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		//System.out.println("id:" + id);
		
		
		return mp;
	}

}
