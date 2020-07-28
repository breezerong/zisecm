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
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class ICMController  extends ControllerAbstract  {
	
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/ICM/newICM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newICM(String metaData) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
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
}
