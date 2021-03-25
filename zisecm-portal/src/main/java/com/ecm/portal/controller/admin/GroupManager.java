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

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.GroupService;
import com.ecm.core.service.UserService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.util.CustomInfo;

/**
 * 用户控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class GroupManager extends ControllerAbstract {

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private ExcSynDetailService detailService;
	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/getGroups", method = RequestMethod.POST)
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

	@RequestMapping(value = "/admin/getGroupCount", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocumentCount(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			long count = groupService.getGroupCount(getToken(), args.get("id").toString(),
					args.get("groupType").toString(), args.get("condition").toString());
			mp.put("itemCount", count);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@ResponseBody
	@RequestMapping(value = "/admin/getGroup", method = RequestMethod.POST)
	public Map<String, Object> getGroup(String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmGroup en = groupService.getGroup(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", en);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/getGroupByName", method = RequestMethod.POST)
	public Map<String, Object> getGroupByName(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			EcmGroup en = groupService.getGroupByName(getToken(), args.get("groupName").toString());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", en);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 更新用户
	 * 
	 * @param obj 用户实例
	 * @return
	 */
	@RequestMapping(value = "/admin/updateGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGroup(@RequestBody EcmGroup obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			
			EcmParameter paramObj= CacheManagerOper.getEcmParameters().get("groupNameOnly");
			if(paramObj!=null&&("是".equals(paramObj.getValue())
					||"Y".equals(paramObj.getValue().toUpperCase())
					||"TRUE".equals(paramObj.getValue().toUpperCase())
					)) {
				EcmGroup group= groupService.getGroupByName(getToken(),obj.getName());
				if(group!=null&&obj.getName().equals(group.getName())) {
					mp.put("code", ActionContext.FAILURE);
					mp.put("message", obj.getName()+"已存在！");
					return mp;
				}
			}
			
			groupService.updateGroup(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 删除用户
	 * 
	 * @param obj 用户实例
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteGroup(@RequestBody EcmGroup obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			groupService.deleteGroup(getToken(), obj.getId());
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 新建用户
	 * 
	 * @param obj 用户实例
	 * @return
	 */
	@RequestMapping(value = "/admin/newGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newGroup(@RequestBody EcmGroup obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			obj.createId();
			LoginUser currentUser  = this.getSession().getCurrentUser();
			String userName = currentUser.getUserName();
			obj.setCreator(userName);
			EcmParameter paramObj= CacheManagerOper.getEcmParameters().get("groupNameOnly");
			if(paramObj!=null&&("是".equals(paramObj.getValue())
					||"Y".equals(paramObj.getValue().toUpperCase())
					||"TRUE".equals(paramObj.getValue().toUpperCase())
					)) {
				EcmGroup group= groupService.getGroupByName(getToken(),obj.getName());
				if(group!=null&&obj.getName().equals(group.getName())) {
					mp.put("code", ActionContext.FAILURE);
					mp.put("message", obj.getName()+"已存在！");
					return mp;
				}
			}
			
			groupService.newGroup(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/addToGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addToGroup(@RequestBody String argStr) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		boolean result = groupService.addUserToGroup(getToken(), args.get("userId").toString(),
				args.get("deptId").toString());
		
		EcmUser user= userService.getObjectById(this.getToken(), args.get("userId").toString());
		
		EcmGroup group= groupService.getObjectById(getToken(), args.get("deptId").toString());
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", result ? ActionContext.SUCESS : ActionContext.FAILURE);
		return mp;
	}
	
	@RequestMapping(value = "/admin/addToRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addToRole(@RequestBody String argStr) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		boolean result = groupService.addUserToRole(getToken(), args.get("userId").toString(),
				args.get("deptId").toString());
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", result ? ActionContext.SUCESS : ActionContext.FAILURE);
		return mp;
	}

	@RequestMapping(value = "/admin/removeFromGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeFromGroup(@RequestBody String argStr) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		boolean result = groupService.removeUserFromGroup(getToken(), args.get("userId").toString(),
				args.get("deptId").toString());
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", result ? ActionContext.SUCESS : ActionContext.FAILURE);
		return mp;
	}
}
