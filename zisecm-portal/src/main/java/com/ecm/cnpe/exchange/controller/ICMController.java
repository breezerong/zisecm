package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class ICMController  extends ControllerAbstract  {
	
	@Autowired
	private DocumentService documentService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private FolderPathService folderPathService;
	
	@RequestMapping(value = "/exchange/ICM/newICM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newICM(String metaData) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		Object fid= args.get("folderId");
		String folderId="";
		if(fid==null) {
			folderId = folderPathService.getFolderId(getToken(), doc.getAttributes(), "3");
		}else {
			folderId=fid.toString();
		}
		EcmFolder folder = folderService.getObjectById(getToken(), folderId);
		doc.setFolderId(folderId);
		doc.setAclName(folder.getAclName());
		String id = documentService.newObject(getToken(), doc, null);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}
	@PostMapping("/exchange/ICM/FeedBack")
	@ResponseBody
	public Map<String, Object> FeedBack(String metaData) throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		documentService.updateObject(getToken(), doc, null);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;	
	}
	@RequestMapping(value ="/exchange/ICM/AcceptICMFeedback",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ACCEPTFeedBack(@RequestBody String argStr) throws Exception{
		List<String> ids = JSONUtils.stringToArray(argStr);
		EcmDocument temp = new EcmDocument();
		for(int i = 0;i < ids.size();i++){
		temp = documentService.getObjectById(getToken(), ids.get(i));
		temp.addAttribute("C_PROCESS_STATUS", "已确认");
		documentService.updateObject(getToken(), temp, null);	
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	
}
