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
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.GroupService;
/**
 * 角色和组操作
 * @author Haihong Rong
 *
 */
@Controller
public class GroupContoller extends ControllerAbstract{

	@Autowired
	private GroupService groupService;

	
	@ResponseBody
	@RequestMapping(value = "/group/getGroups", method = RequestMethod.POST)
	public Map<String, Object> getGroups(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmGroup> list = null;
			Pager pager = null;
			if (args.get("pageSize") != null) {
				pager = new Pager();
				pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
				pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
				list = groupService.getGroups(getToken(), args.get("id").toString(), args.get("groupType").toString(),
						pager, args.get("condition").toString());
			} else {
				list = groupService.getAllGroups(getToken(), args.get("id").toString(),
						args.get("groupType").toString());
			}
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/group/getChildRoles", method = RequestMethod.POST)
	public Map<String, Object> getChildRoles(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmGroup> list = null;
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			list = groupService.getChildRoles(getToken(), args.get("id").toString(), 
			pager, args.get("condition").toString());
			
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	@ResponseBody
	@RequestMapping(value = "/group/addRoles", method = RequestMethod.POST)
	public Map<String, Object> addRoles(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String id = args.get("id").toString();
			String names = args.get("names").toString();
			String[] strs = names.split(";");
			for(String name: strs) {
				EcmGroup g = groupService.getGroupByName(getToken(), name);
				if(g != null) {
					groupService.addRole(getToken(), id, g.getId());
				}
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
}
