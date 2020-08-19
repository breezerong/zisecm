package com.ecm.portal.controller.admin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.GroupService;
import com.ecm.core.service.UserService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 用户控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class UserManager extends ControllerAbstract {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	@Autowired
	private ExcSynDetailService detailService;

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/getUsers", method = RequestMethod.POST)
	public Map<String, Object> getUsers(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String noGroup = "";
			if (args.get("noGroup") != null) {
				noGroup = args.get("noGroup").toString();
			}
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			List<EcmUser> list = userService.getUsers(getToken(), pager, "1".equals(noGroup),
					args.get("condition").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/getUserList", method = RequestMethod.POST)
	public Map<String, Object> getUserList(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String noGroup = "";
			if (args.get("noGroup") != null) {
				noGroup = args.get("noGroup").toString();
			}
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			List<Map<String,Object>> list = userService.getUserList(getToken(), pager, "1".equals(noGroup),
					args.get("condition").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	/**
	 * 获取部门所有用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/getGroupUsers", method = RequestMethod.POST)
	public Map<String, Object> getGroupUsers(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmUser> list = userService.getGroupUsers(getToken(), args.get("deptId").toString());
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

//	@RequestMapping(value = "/admin/getUserCount", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
//	@ResponseBody
//	public Map<String, Object> getUserCount(@RequestBody String argStr) {
//		Map<String, Object> args = JSONUtils.stringToMap(argStr);
//		String noGroup = "";
//		if(args.get("noGroup")!=null)
//		{
//			noGroup = args.get("noGroup").toString();
//		}
//		long count = userService.getUserCount(getToken(),noGroup,
//				args.get("condition").toString());
//		Map<String, Object> mp = new HashMap<String, Object>();
//		mp.put("itemCount", count);
//		mp.put("code", 1);
//		return mp;
//	}

	/**
	 * 获取角色所有用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/getRoleUsers", method = RequestMethod.POST)
	public Map<String, Object> getRoleUsers(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String noGroup = "";
			if (args.get("noGroup") != null) {
				noGroup = args.get("noGroup").toString();
			}
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			List<EcmUser> list = userService.getRoleUsers(getToken(), pager, noGroup, args.get("groupId").toString(),
					args.get("condition").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/getRoleAllUsers", method = RequestMethod.POST)
	public Map<String, Object> getRoleAllUsers(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			List<EcmUser> list = userService.getRoleAllUsers(getToken(), pager, args.get("groupId").toString(),
					args.get("condition").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	/**
	 * 获取角色所有用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/getUsersByGroupName", method = RequestMethod.POST)
	public Map<String, Object> getUsersByGroupName(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String noGroup = "";
			if (args.get("noGroup") != null) {
				noGroup = args.get("noGroup").toString();
			}
			String roleName = "";
			if (args.get("roleName") != null) {
				roleName = args.get("roleName").toString();
			}
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			List<EcmUser> list = userService.getUsersByGroupName(getToken(), pager, noGroup,roleName,
					args.get("condition").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

//	@RequestMapping(value = "/admin/getRoleUserCount", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
//	@ResponseBody
//	public Map<String, Object> getRoleUserCount(@RequestBody String argStr) {
//		Map<String, Object> args = JSONUtils.stringToMap(argStr);
//		String noGroup = "";
//		if(args.get("noGroup")!=null)
//		{
//			noGroup = args.get("noGroup").toString();
//		}
//		long count = userService.getRoleUserCount(getToken(),noGroup,
//				args.get("groupId").toString(),args.get("condition").toString());
//		Map<String, Object> mp = new HashMap<String, Object>();
//		mp.put("itemCount", count);
//		mp.put("code", 1);
//		return mp;
//	}

	@ResponseBody
	@RequestMapping(value = "/admin/getUser", method = RequestMethod.POST)
	public Map<String, Object> getUser(String id) {
		id = id.replace("\"", "");
		EcmUser en;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			en = userService.getObjectById(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", en);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
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

	@ResponseBody
	@RequestMapping(value = "/admin/getUserImage")
	public void getUserImage(HttpServletRequest request, HttpServletResponse response) {
		InputStream iStream = null;
		OutputStream toClient = null;
		try {
			String id = "";
			if (request.getAttribute("id") != null) {
				id = request.getAttribute("id").toString();
			} else {
				id = request.getParameter("id");
			}
			EcmUser en = userService.getObjectById(getToken(), id.replace("\"", ""));
			if (en.getSignImage() == null || en.getSignImage().length() < 5) {
				return;
			}
			File f = new File(en.getSignImage());
			if (!f.exists()) {
				return;
			}

			iStream = new BufferedInputStream(new FileInputStream(en.getSignImage()));

			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(en.getName(), "UTF-8"));
			response.addHeader("Content-Length", "" + f.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = iStream.read(buffer)) != -1) {
				toClient.write(buffer, 0, bytesRead);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (toClient != null) {
				try {
					toClient.flush();
					toClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param obj 用户实例
	 * @return
	 */
	@RequestMapping(value = "/admin/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(@RequestBody EcmUser obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			userService.updateObject(getToken(), obj);
			if("CNPE".equals(this.getSession().getCurrentUser().getCompany())) {
				OptionLogger.loggerUser(detailService, obj, obj.getCompanyName(),"修改用户");
			}else {
				OptionLogger.loggerUser(detailService, obj, "CNPE","修改用户");
			}
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
	 * 删除用户
	 * 
	 * @param obj 用户实例
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUser(@RequestBody EcmUser obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			userService.deleteObject(getToken(), obj);
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
	 * 新建用户
	 * 
	 * @param obj 用户实例
	 * @return
	 */
	@RequestMapping(value = "/admin/newUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newUser(String user, MultipartFile uploadFile) {
		Map<String, Object> mp = new HashMap<String, Object>();
		EcmUser en = JSON.parseObject(user, EcmUser.class);
		try {
			InputStream instream = null;
			String fileName = null;
			if (uploadFile != null) {
				instream = uploadFile.getInputStream();
				fileName = uploadFile.getOriginalFilename();
			}
			userService.newObject(getToken(), en, instream, fileName);
			if("CNPE".equals(this.getSession().getCurrentUser().getCompany())) {
				OptionLogger.loggerUser(detailService, en, en.getCompanyName(),"新建用户");
			}else {
				OptionLogger.loggerUser(detailService, en, "CNPE","新建用户");
			}
			
			if (instream != null) {
				instream.close();
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

	@RequestMapping(value = "/admin/updateSignImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSignImage(String id, MultipartFile uploadFile) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			InputStream instream = null;
			String fileName = null;
			if (uploadFile != null) {
				instream = uploadFile.getInputStream();
				fileName = uploadFile.getOriginalFilename();
			}
			userService.updateSignImage(getToken(), id, instream, fileName);
			if (instream != null) {
				instream.close();
			}
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
	 * 更新用户
	 * 
	 * @param obj 用户实例
	 * @return
	 */
	@RequestMapping(value = "/admin/moveUserDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> moveUserDepartment(@RequestBody EcmUser obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			userService.moveUserDepartment(getToken(), obj);
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

	@RequestMapping(value = "/admin/removeUserGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeUserGroup(@RequestBody EcmUser obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			groupService.removeUserFromGroup(getToken(), obj);
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

	@RequestMapping(value = "/admin/removeUserRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeUser(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			groupService.removeUserFromRole(getToken(), args.get("userId").toString(), args.get("roleId").toString());
			EcmUser user= userService.getObjectById(this.getToken(), args.get("userId").toString());
			
			EcmGroup group= groupService.getObjectById(getToken(), args.get("roleId").toString());
			
			if("CNPE".equals(this.getSession().getCurrentUser().getCompany())) {
				OptionLogger.loggerGroup(detailService, user, group.getName(),user.getCompanyName(),"移除用户");
			}else {
				OptionLogger.loggerGroup(detailService, user, group.getName(),"CNPE","移除用户");
			}
			
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
	
	@RequestMapping(value = "/admin/removeRoleFromRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeRoleFromRole(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			groupService.removeRoleFromRole(getToken(), args.get("parentId").toString(), args.get("childId").toString());
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
