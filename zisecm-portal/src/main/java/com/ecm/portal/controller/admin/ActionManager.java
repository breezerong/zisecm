package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmActionMapper;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.service.ActionService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.log.LogAopAction;

/**
 * 
 * @author Administrator 事件管理控制器
 */
@Controller
public class ActionManager extends ControllerAbstract{
	
	private final Logger logger = LoggerFactory.getLogger(LogAopAction.class);

	@Autowired
	ActionService actionService;

	/**
	 * 获取所有事件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin/getAction")
	public Map<String, Object> getAction() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmAction> list = actionService.getAllObject(getToken());
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		
		return mp;
	}

	/**
	 * 更新事件
	 * 
	 * @param obj 事件jason对象
	 * @return  
	 */
	@RequestMapping(value = "/admin/updateAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAction(@RequestBody EcmAction obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			actionService.updateObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}

	/**
	 * 删除事件
	 * 
	 * @param obj
	 *            事件对象
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAction(@RequestBody EcmAction obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			actionService.deleteObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

	/**
	 * 新建事件
	 * 
	 * @param obj
	 *            事件对象
	 * @return
	 */
	@RequestMapping(value = "/admin/newAction", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newAction(@RequestBody EcmAction obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			obj.createId();
			actionService.newObject(getToken(),obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

}
