package com.ecm.portal.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmPermit;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AclService;
/**
 * Acl操作
 * @author Haihong Rong
 *
 */
@Controller
public class AclContoller extends ControllerAbstract{

	@Autowired
	private AclService aclService;

	
	@ResponseBody
	@RequestMapping(value = "/acl/getAcls", method = RequestMethod.POST)
	public Map<String, Object> getAcls(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmAcl> list = null;
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			list = aclService.getAcls(getToken(), pager, args.get("name").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/getAclById", method = RequestMethod.POST)
	public Map<String, Object> getAclById(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmAcl obj = aclService.getObjectById(getToken(), id);
			mp.put("data", obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/getAclByName", method = RequestMethod.POST)
	public Map<String, Object> getAclByName(@RequestBody String name) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmAcl obj = aclService.getObjectByName(getToken(), name);
			mp.put("data", obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/acl/getPermitsByName", method = RequestMethod.POST)
	public Map<String, Object> getPermitsByName(@RequestBody String name) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmPermit> list = null;
			EcmAcl acl= aclService.getObjectByName(getToken(), name);
			if(acl == null) {
				mp.put("code", ActionContext.FAILURE);
				mp.put("message","Acl Name isnot exists:"+name);
			}else {
				list = aclService.getPermits(getToken(), acl.getId());
				mp.put("data", list);
				mp.put("code", ActionContext.SUCESS);
			}
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/getPermitsById", method = RequestMethod.POST)
	public Map<String, Object> getPermitsById(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmPermit> list = null;
			list = aclService.getPermits(getToken(), id);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/newAcl", method = RequestMethod.POST)
	public Map<String, Object> newAcl(@RequestBody EcmAcl acl) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = aclService.newObject(getToken(), acl);
			mp.put("data", id);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (EcmException e) {
			mp.put("code", ActionContext.FAILURE);
		} catch (NoPermissionException e) {
			mp.put("code", ActionContext.NO_PERMSSION);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/updateAcl", method = RequestMethod.POST)
	public Map<String, Object> updateAcl(@RequestBody EcmAcl acl) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			aclService.updateObject(getToken(), acl);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (EcmException e) {
			mp.put("code", ActionContext.FAILURE);
		} catch (NoPermissionException e) {
			mp.put("code", ActionContext.NO_PERMSSION);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/deleteAcl", method = RequestMethod.POST)
	public Map<String, Object> deleteAcl(@RequestBody EcmAcl acl) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			aclService.deleteObject(getToken(), acl);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (EcmException e) {
			mp.put("code", ActionContext.FAILURE);
		} catch (NoPermissionException e) {
			mp.put("code", ActionContext.NO_PERMSSION);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/copyAcl", method = RequestMethod.POST)
	public Map<String, Object> copyAcl(@RequestBody EcmAcl acl) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmAcl newAcl= aclService.copy(getToken(), acl.getId(), acl.getName(), acl.getDescription());
			mp.put("data", newAcl.getId());
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/acl/grantPermit", method = RequestMethod.POST)
	public Map<String, Object> grantPermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if(StringUtils.isEmpty(permit.getTargetName())) {
				mp.put("code", ActionContext.FAILURE);
			}else {
				String[] targetNames = permit.getTargetName().split(";");
				for(String targetName: targetNames) {
					if(permit.getTargetType()==1) {
						aclService.grantUser(getToken(), permit.getParentId(), targetName, permit.getPermission(), permit.getExpireDate());
					}else {
						aclService.grantGroup(getToken(), permit.getParentId(), targetName, permit.getPermission(), permit.getExpireDate());
					}
				}
				mp.put("code", ActionContext.SUCESS);
			}
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	
	@ResponseBody
	@RequestMapping(value = "/acl/revokePermit", method = RequestMethod.POST)
	public Map<String, Object> revokePermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if(permit.getTargetType()==1) {
				aclService.revokeUser(getToken(), permit.getParentId(), permit.getTargetName());
			}else {
				aclService.revokeGroup(getToken(), permit.getParentId(), permit.getTargetName());
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
}
