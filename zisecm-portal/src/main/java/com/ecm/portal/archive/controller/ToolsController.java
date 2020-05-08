package com.ecm.portal.archive.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.QueryService;
import com.ecm.core.service.RelationService;
import com.ecm.portal.archive.services.ArchiveFolderService;
import com.ecm.portal.archive.services.ArchiveRelationService;
import com.ecm.portal.archive.services.DepartmentServiceEx;
import com.ecm.portal.archive.services.ImportService;
import com.ecm.portal.archive.services.ToolsService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class ToolsController extends ControllerAbstract{


	
	@Autowired
	private ToolsService toolsService;
	
	@Autowired
	private DepartmentServiceEx departmentServiceEx;
	
	
	
	
	@RequestMapping(value = "/tools/batchUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchUpdate(@RequestParam("excel") MultipartFile excel) throws AccessDeniedException{
		Map<String, Object> mp = new HashMap<String, Object>();
		String msg;
		try {
			msg = toolsService.updateByExcel(getToken(),excel);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
	}
	
	@RequestMapping(value = "/tools/mountByExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mountByExcel(@RequestParam("excel") MultipartFile excel,@RequestParam("files") MultipartFile[] files) throws AccessDeniedException{
		Map<String, Object> mp = new HashMap<String, Object>();
		String msg;
		try {
			msg = toolsService.mountByExcel(getToken(), excel, files);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
	}
	
	@RequestMapping(value = "/tools/mountByFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mountByFile(@RequestParam("files") MultipartFile[] files) throws AccessDeniedException{
		Map<String, Object> mp = new HashMap<String, Object>();
		String msg;
		try {
			msg = toolsService.mountByFile(getToken(), files);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
	}
	
	
	@RequestMapping(value = "/tools/updateDepartment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDepartment() throws AccessDeniedException{
		Map<String, Object> mp = new HashMap<String, Object>();
		String msg;
		try {
			msg = departmentServiceEx.updateDeparement(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
	}
	
	
	
	
}
	
