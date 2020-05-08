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
import com.ecm.core.service.RelationService;
import com.ecm.portal.archive.services.ArchiveFolderService;
import com.ecm.portal.archive.services.ArchiveRelationService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class Test1Controller extends ControllerAbstract{

	
	@Autowired
	private NumberService numberService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private FolderPathService folderPathService;
	

	
	
	
	@RequestMapping(value = "/test/createFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createFolder(@RequestParam("folderPath")String folderPath ) throws Exception {
		
		String fldId = folderPathService.getFolderByPath(getToken(), folderPath);
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", fldId);
		return mp;
	}
	
	@RequestMapping(value = "/test/newNumber/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> newNumber(@PathVariable("id") String id) throws Exception {
		EcmDocument doc = documentService.getObjectById(getToken(), id);
		String number = numberService.getNumber(getToken(), doc.getAttributes());
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", number);
		return mp;
	}
	
	@RequestMapping(value = "/test/getPath/{id}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPath(@PathVariable("id") String id,@PathVariable("type") String type) throws Exception {
		EcmDocument doc = documentService.getObjectById(getToken(), id);
		String path = folderPathService.getFolderId(getToken(), doc.getAttributes(),type);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", path);
		return mp;
	}
	
	@RequestMapping(value = "/test/builderPath/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> builderPath(@PathVariable("id") String id) throws Exception {
		folderPathService.buildFullPath(getToken(), id);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", "OK");
		return mp;
	}
}
	
