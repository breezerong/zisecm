package com.ecm.portal.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.QueueItemService;
/**
 * 队列操作
 * @author Haihong Rong
 *
 */
@Controller
public class QueueContoller extends ControllerAbstract{

	@Autowired
	private QueueItemService queueItemService;

	
	@ResponseBody
	@RequestMapping(value = "/queue/getQueueItems", method = RequestMethod.POST)
	public Map<String, Object> getQueueItems(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmQueueItem> list = null;
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			String condition = args.get("condition").toString();
			list = queueItemService.getObjects(getToken(),pager, condition);
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queue/getQueueItem", method = RequestMethod.POST)
	public Map<String, Object> getQueueItem(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmQueueItem obj = queueItemService.getObjectById(getToken(), id);
			mp.put("data", obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queue/newQueueItem", method = RequestMethod.POST)
	public Map<String, Object> newQueueItem(@RequestBody EcmQueueItem item) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = queueItemService.newObject(getToken(), item);
			mp.put("data", id);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queue/updateQueueItem", method = RequestMethod.POST)
	public Map<String, Object> updateQueueItem(@RequestBody EcmQueueItem item) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			queueItemService.updateObject(getToken(), item);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queue/updateQueueItemStatus", method = RequestMethod.POST)
	public Map<String, Object> updateQueueItemStatus(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String id = args.get("id").toString();
			int status = Integer.parseInt(args.get("status").toString());
			String message = args.get("message").toString();
			queueItemService.updateQueueItemStatus(getToken(), id, status, message);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queue/deleteQueueItem", method = RequestMethod.POST)
	public Map<String, Object> deleteQueueItem(@RequestBody EcmQueueItem item) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			queueItemService.deleteObject(getToken(), item);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queue/deleteQueueItemById", method = RequestMethod.POST)
	public Map<String, Object> deleteQueueItemById(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			queueItemService.deleteObjectById(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
}
