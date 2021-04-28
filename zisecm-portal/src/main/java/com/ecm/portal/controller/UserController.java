package com.ecm.portal.controller;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.common.util.FileUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmComponent;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.GroupService;
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
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private DocumentService documentService;
	

	@ResponseBody
	@RequestMapping(value = "/user/getUser", method = RequestMethod.POST)
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
	@RequestMapping(value = "/user/getUserByName", method = RequestMethod.POST)
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
	public Map<String, Object> updateSignImage(String id,@RequestParam("uploadFile") MultipartFile[] uploadFileList) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			InputStream instream = null;
			String fileName = null;
			
			for(int i=0; i<uploadFileList.length; i++) {
				if(uploadFileList[i]!=null) {
					instream = uploadFileList[i].getInputStream();
					fileName = uploadFileList[i].getOriginalFilename();
				}
				userService.updateSignImage(getToken(),id.replace("\"", ""),instream,fileName);
				if(instream!=null) {
					instream.close();
				}
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
	
	@RequestMapping(value = "/user/UaGSignImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> UaGSignImage(@RequestParam("uploadFile") MultipartFile[] uploadFileList, HttpServletResponse response) {
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			InputStream instream = null;
			String fileName = null;
			String userName = null;
			String id = null;
			
			for(int i=0; i<uploadFileList.length; i++) {
				if(uploadFileList[i]!=null) {
					instream = uploadFileList[i].getInputStream();
					fileName = uploadFileList[i].getOriginalFilename();
					
					if ((fileName != null) && (fileName.length() > 0)) {
						int dot = fileName.lastIndexOf('.');
						if ((dot >-1) && (dot < (fileName.length()))) {
							userName =  fileName.substring(0, dot);
						}

					}
				}
				
				String sqlByUserName = "select * from ecm_user where LOGIN_NAME = '"+ userName +"'";
				id = (String) documentService.getMapList(getToken(), sqlByUserName).get(0).get("ID");
				
				userService.updateSignImage(getToken(),id,instream,fileName);
				if(instream!=null) {
					instream.close();
				}
				
				execAddDocument(uploadFileList[i], userName, "b7ae6823040a4dcbbf42ed1bc73ff24d");
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
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
	
	//L 通过名字查找用户所有组角色
	@RequestMapping(value = "/user/getGroupByUserName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGroupByUserName(@RequestBody String userName){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmGroup> list =null;
			list = userService.getUserGroupsByName(getToken(), userName);
			if (list.size()>0) {
					mp.put("data", list);
					mp.put("code", ActionContext.SUCESS);
			}else {
					mp.put("data", list);
					mp.put("code", ActionContext.SUCESS);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mp;
	}
	/**
	 * 验证权限
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/user/validatapermission", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validataPermission(@RequestBody String componentName){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			LoginUser user = userService.getCurrentUser(getToken());
			EcmComponent comp = CacheManagerOper.getEcmComponents().get(componentName);
			if(comp != null) {
				String roleName = comp.getRoleName();
				if(!StringUtils.isEmpty(roleName)) {
					String[] roles = roleName.split(";");
					mp.put("code", ActionContext.FAILURE);
					for(String role: roles) {
						if(user.getRoles().contains(role)) {
							mp.put("code", ActionContext.SUCESS);
							break;
						}
					}
				}else {
					mp.put("code", ActionContext.SUCESS);
				}
			}else {
				mp.put("code", ActionContext.SUCESS);
			}
			
			mp.put("data", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		return mp;
	}
	
	public void execAddDocument(MultipartFile uploadFile, String userName, String folderId) throws Exception {
		EcmContent en = null;
		EcmDocument doc = new EcmDocument();
		EcmFolder folder= folderService.getObjectById(getToken(), folderId);
		
		//&& documentService.getObjectById(getToken(), docId) == null
		doc.setAclName(folder.getAclName());
		doc.setFolderId(folderId);
		String name = uploadFile.getOriginalFilename();
		doc.setName(name.substring(0,name.lastIndexOf(".")));
		doc.setTypeName("签名照片");

		en = new EcmContent();
		en.setName(uploadFile.getOriginalFilename());
		en.setContentSize(uploadFile.getSize());
		en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
		en.setInputStream(uploadFile.getInputStream());	
		
		String sqlGetDocId = "select * from ecm_document where NAME = '"+ userName +"' AND FOLDER_ID = 'b7ae6823040a4dcbbf42ed1bc73ff24d'";
		List<Map<String, Object>> docList = documentService.getMapList(getToken(), sqlGetDocId);
		
		if(docList.size() <= 0) {
			doc.setStatus("新建");
			documentService.newObject(getToken(), doc, en);
		}else {
			documentService.updateObject(getToken(), doc, en);
		}
	}
	
}

