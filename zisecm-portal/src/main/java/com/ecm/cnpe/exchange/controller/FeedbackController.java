package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class FeedbackController  extends ControllerAbstract  {
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ExcSynDetailService detailService;
	
	@RequestMapping(value = "/exchange/feedback/searchReplay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchReplay(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String id = args.get("C_FROM_CODING").toString();
			String sql="select CREATOR,CREATION_DATE,C_CONTENT,TITLE, C_TO from ecm_document where ";
			sql+="C_FROM_CODING='"+id+"' ORDER BY CREATION_DATE";
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/exchange/feedback/searchCreator", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchCreator(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String id = args.get("C_FROM_CODING").toString();
			String sql="select CREATOR,CREATION_DATE,SUB_TYPE,C_CONTENT,TITLE, C_TO from ecm_document where ";
			sql+="ID='"+id+"'";
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/exchange/feedback/newraplay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newReplay(String metaData) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(metaData);
			EcmDocument Parentargs=documentService.getObjectById(getToken(), args.get("C_FROM_CODING").toString());
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(args);
			doc.setAclName(Parentargs.getAclName());
			doc.setFolderId(Parentargs.getFolderId());
			String id = documentService.newObject(getToken(), doc, null);
			doc.setId(id);
			EcmDocument temp = new EcmDocument();
			temp = documentService.getObjectById(getToken(), args.get("C_FROM_CODING").toString());
			temp.addAttribute("C_ITEM_STATUS", "已回复");
			documentService.updateObject(getToken(), temp, null);
			mp.put("code", ActionContext.SUCESS);
			mp.put("id", id);
			String com=getSession().getCurrentUser().getCompany();
			OptionLogger.logger(getToken(), detailService, doc, "回复问题",com);
		}catch(AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
}
