package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.util.CustomInfo;

@RestController

public class QuestionImportController extends ControllerAbstract {
	@Autowired
	private DocumentService documentService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private FolderPathService folderPathService;
	
	@Autowired
	private ExcSynDetailService detailService;
	
	@RequestMapping(value = "/exchange/Ques/newQues", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> newQues(String metaData) throws Exception {
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
		EcmDocument temp = new EcmDocument();
		temp = documentService.getObjectById(getToken(), id);
		OptionLogger.logger(getToken(), detailService, temp, "新建问题", CustomInfo.OwnerCompany);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}

}
