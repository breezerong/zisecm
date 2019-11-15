package com.ecm.portal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.UserService;
import com.ecm.portal.entity.UserInfoEntity;

/**
 * 用户控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class UserController extends ControllerAbstract{

	@Autowired
	private UserService userService;

	

	@ResponseBody
	@RequestMapping(value = "/user/getUser", method = RequestMethod.GET)
	public Map<String, Object> getUser(@RequestBody String id) {
		id = id.replace("\"", "");
		EcmUser en;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			en = userService.getObjectById(getToken(),id);
			
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", en);
		} catch (EcmException e) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/getUserByName", method = RequestMethod.GET)
	public Map<String, Object> getUserByName(@RequestBody String userName) {
		userName = userName.replace("\"", "");
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmUser en = userService.getObjectByName(getToken(),userName);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", en);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/getUserInfo", method = RequestMethod.POST)
	public Map<String, Object> getUserInfo(@RequestBody String loginName) {
		loginName = loginName.replace("\"", "");
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmUser en = userService.getObjectByName(getToken(),loginName);
			List<String> rolsNames = new ArrayList<>();
			Map<String, Object> infoMap = new HashMap<>();
			infoMap.put("user", en);
			infoMap.put("roles", rolsNames);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", infoMap);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/user/getUserImage")
	public void getUserImage(HttpServletRequest request,HttpServletResponse response) {
		InputStream iStream =null;
		OutputStream toClient = null;
		try {
			String id = "";
			if(request.getAttribute("id")!=null){
				id = request.getAttribute("id").toString();
			}
			else{
				id = request.getParameter("id");
			}
			EcmUser en = userService.getObjectById(getToken(),id);
			if(en==null || en.getSignImage()==null||en.getSignImage().length()<5) {
				return;
			}
			File f = new File(en.getSignImage());
			if(!f.exists()) {
				return;
			}
			
			iStream = new BufferedInputStream(new FileInputStream(en.getSignImage()));
			
			// 清空response
	        response.reset();
	        // 设置response的Header
	        response.setCharacterEncoding("UTF-8");
	        response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(en.getName(), "UTF-8"));
	        response.addHeader("Content-Length", "" + f.length());
	        toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");
	        byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = iStream.read(buffer)) != -1) {
				toClient.write(buffer, 0, bytesRead);
			}
			
		}
        catch(Exception ex) {
        	ex.printStackTrace();
        }
		finally {
			if(iStream!=null) {
				try {
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(toClient!=null) {
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
	 * @param obj
	 *            用户实例
	 * @return
	 */
	@RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(@RequestBody EcmUser obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			userService.updateObject(getToken(),obj);
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

	@RequestMapping(value = "/user/updateSignImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSignImage(String id,MultipartFile uploadFile) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			InputStream instream = null;
			String fileName = null;
			if(uploadFile!=null) {
				instream = uploadFile.getInputStream();
				fileName = uploadFile.getOriginalFilename();
			}
			userService.updateSignImage(getToken(),id.replace("\"", ""),instream,fileName);
			if(instream!=null) {
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
	
	@RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePassword(@RequestBody UserInfoEntity userInfo) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			userService.updatePassword(getToken(),userInfo.getName(), userInfo.getPassword(), userInfo.getNewPassword());
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

