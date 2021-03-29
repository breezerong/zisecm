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

import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.FolderService;
import com.ecm.portal.service.DcFolderService;

@Controller
public class DocFolderController extends ControllerAbstract {
	
	@Autowired
	private DcFolderService dcFolderService;
	
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/getFoldersByPath", method = RequestMethod.POST)
	public Map<String, Object> getFoldersByPath(@RequestBody String folderPath) {
		List<EcmFolder> list = null;
		try {
			list = dcFolderService.getFoldersByParentPathWithPermission(getToken(),folderPath);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}
	
}
