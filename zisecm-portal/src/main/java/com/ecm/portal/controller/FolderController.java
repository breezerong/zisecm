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
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderService;

/**
 * 文件夹控制器
 * 
 * @author Haihong Rong
 *
 */
@Controller
public class FolderController  extends ControllerAbstract {

	/**
	 * 表单数据访问
	 */
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private DocumentService documentService;
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/folder/getFolderByConfige", method = RequestMethod.POST)
	public Map<String, Object> getFolderByConfige(@RequestBody String param) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = CacheManagerOper.getEcmParameters().get(param).getValue();
			EcmFolder folder = folderService.getObjectById(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", folder);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value="/folder/copyFolders", method = RequestMethod.POST)
	public Map<String, Object> copyFolders(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String sourceId = args.get("sourceId").toString();
			String targetId = args.get("targetId").toString();
			folderService.copyFolders(getToken(), sourceId, targetId, false);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 * @throws AccessDeniedException 
	 */
	@ResponseBody
	@RequestMapping(value="/admin/getFolder", method = RequestMethod.POST)
	public Map<String, Object> getFolder(@RequestBody String parentId) throws AccessDeniedException {
		List<EcmFolder> list = folderService.getFoldersByParentId(getToken(), parentId); //Integer.parseInt(parentId));
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}

	

	/**
	 * 更新表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 * @throws AccessDeniedException 
	 */
	@RequestMapping(value = "/admin/updateFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFolder(@RequestBody EcmFolder obj) throws AccessDeniedException {
		folderService.updateObject(getToken(),obj);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	/**
	 * 删除表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/admin/deleteFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFolder(@RequestBody EcmFolder obj) throws Exception {
		int code =1;
		String msg ="";
		if(folderService.getFolderCount(getToken(), obj.getId())>0)
		{
			code = 2;
			msg="文件夹包含文件夹，请先删除文件夹。";
		}
		else if(documentService.getObjectCount(getToken(),
				null,  obj.getId(), null)>0)
		{
			code = 3;
			msg="文件夹包含文件，请先删除文件。";
		}
		else {
			folderService.deleteObject(getToken(),obj);
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", code);
		mp.put("msg", msg);
		return mp;
	}

	/**
	 * 新建表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 * @throws NoPermissionException 
	 * @throws AccessDeniedException 
	 * @throws EcmException 
	 */
	@RequestMapping(value = "/admin/newFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newFolder(@RequestBody EcmFolder obj) throws EcmException, AccessDeniedException, NoPermissionException {
		
		folderService.newObject(getToken(),obj);
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	

}
