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
import com.ecm.core.entity.EcmQuery;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.QueryService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 组件控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class QueryManager extends ControllerAbstract {
	/**
	 * 组件数据访问
	 */
	@Autowired
	private QueryService queryService;

	/**
	 * 获取所有组件
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin/getQuery")
	public Map<String, Object> getQuery() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmQuery> list = queryService.getAllObject(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 更新组件
	 * 
	 * @param obj 组件实例
	 * @return
	 */
	@RequestMapping(value = "/admin/updateQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateQuery(@RequestBody EcmQuery obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			queryService.updateObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 删除组件
	 * 
	 * @param obj 组件实例
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteQuery(@RequestBody EcmQuery obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			queryService.deleteObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 新建组件
	 * 
	 * @param obj 组件实例
	 * @return
	 */
	@RequestMapping(value = "/admin/newQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newQuery(@RequestBody EcmQuery obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			queryService.newObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

}
