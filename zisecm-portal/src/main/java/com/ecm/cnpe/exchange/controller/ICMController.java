package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.util.CustomInfo;

@RestController

public class ICMController  extends ControllerAbstract  {
	
	@Autowired
	private DocumentService documentService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private FolderPathService folderPathService;
	
	
	@Autowired
	private ExcSynDetailService detailService;
	
	
	@RequestMapping(value = "/exchange/ICM/newICM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newICM(String metaData) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
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
			doc.setId(id);
			OptionLogger.logger(getToken(), detailService,doc, "新建",CustomInfo.OwnerCompany);
			mp.put("code", ActionContext.SUCESS);
			mp.put("id", id);
		}catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	@PostMapping("/exchange/ICM/OpenFeedBack")
	@ResponseBody
	public Map<String, Object> OpenFeedBack(String metaData) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			LoginUser userObj=null;
			userObj=getSession().getCurrentUser();
			String company = userObj.getCompany();
			Map<String, Object> args = JSONUtils.stringToMap(metaData);
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(args);
			documentService.updateObject(getToken(), doc, null);
			OptionLogger.logger(getToken(), detailService,doc, "延误打开反馈",company);
			mp.put("code", ActionContext.SUCESS);
		}
		catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;	
	}
	
	@PostMapping("/exchange/ICM/CloseFeedBack")
	@ResponseBody
	public Map<String, Object> CloseFeedBack(String metaData) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			LoginUser userObj=null;
			userObj=getSession().getCurrentUser();
			String company = userObj.getCompany();
			Map<String, Object> args = JSONUtils.stringToMap(metaData);
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(args);
			documentService.updateObject(getToken(), doc, null);
			OptionLogger.logger(getToken(), detailService,doc, "延误关闭反馈",company);
			mp.put("code", ActionContext.SUCESS);
		}
		catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;	
	}
	
	@PostMapping("/exchange/ICM/DelayConfirm")
	@ResponseBody
	public Map<String, Object> CRM(String metaData) throws Exception{
		LoginUser userObj=null;
		Date d1 = new Date();
		userObj=getSession().getCurrentUser();
		String company = userObj.getCompany();
		String name = userObj.getLoginName();
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		Map<String,Object> map = new HashMap<String,Object>();
		EcmDocument temp = new EcmDocument();
		String id=args.get("id").toString();
		String comment2 = args.get("C_COMMENT2")==null?"":args.get("C_COMMENT2").toString();
		String c_ex3_date = args.get("C_EX3_DATE")==null?"":args.get("C_EX3_DATE").toString();
		String comment3 = args.get("C_COMMENT3")==null?"":args.get("C_COMMENT3").toString();
		String c_type1 =  args.get("C_TYPE1")==null?"":args.get("C_TYPE1").toString();
		String comment4 = args.get("C_COMMENT4")==null?"":args.get("C_COMMENT4").toString();
		String c_ex6_date = args.get("C_EX6_DATE")==null?"":args.get("C_EX6_DATE").toString();
		String comment6 = args.get("C_COMMENT6")==null?"":args.get("C_COMMENT6").toString();
		temp=documentService.getObjectById(getToken(), id);
		temp.addAttribute("C_COMMENT2", comment2);
		temp.addAttribute("C_EX3_DATE", c_ex3_date);
		temp.addAttribute("C_COMMENT3", comment3);
		temp.addAttribute("C_TYPE1", c_type1);
		temp.addAttribute("C_COMMENT4", comment4);
		temp.addAttribute("C_EX6_DATE", c_ex6_date);
		temp.addAttribute("C_COMMENT6", comment6);
		temp.addAttribute("C_PROCESS_STATUS", "新建");
		temp.addAttribute("C_DRAFTER1", name);
		temp.addAttribute("C_DRAFT1_DATE",d1);
		documentService.updateObject(getToken(), temp, null);
		OptionLogger.logger(getToken(), detailService,temp, "延误回复反馈",company);
		map.put("code", 1);
		return map;
	}
	
	
	
	
	
	@PostMapping("/exchange/ICM/UpdataICMLogger")
	@ResponseBody
	public void UpdataICMLogger(String metaData) throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		OptionLogger.logger(getToken(), detailService,doc, "修改",CustomInfo.OwnerCompany);
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
		OptionLogger.logger(getToken(), detailService, temp, "延误打开确认", CustomInfo.OwnerCompany);
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	@RequestMapping(value ="/exchange/ICM/ICMDelayConfirm",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ICMDC(@RequestBody String argStr) throws Exception{
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company = userObj.getCompany();
		List<String> ids = JSONUtils.stringToArray(argStr);
		EcmDocument temp = new EcmDocument();
		for(int i = 0;i < ids.size();i++){
		temp = documentService.getObjectById(getToken(), ids.get(i));
		temp.addAttribute("C_PROCESS_STATUS", "已确认");
		documentService.updateObject(getToken(), temp, null);	
		OptionLogger.logger(getToken(), detailService, temp, "延误回复确认", company);
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	@RequestMapping(value ="/exchange/ICM/ICMDelayCloseConfirm",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ICMDCC(@RequestBody String metadata) throws Exception{
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company = userObj.getCompany();
		Map<String, Object> args = JSONUtils.stringToMap(metadata);
		Object o1 = args.get("ids");

		
		List<String> ids = new ArrayList<String>();
		ids.add(String.class.cast(o1));
		String isTrue = args.get("isTrue").toString();
		for(int i=0;i<ids.size();i++) {
			String reg="\"";
			String str =ids.get(i);	
			 Pattern p = Pattern.compile(reg);
			 Matcher m = p.matcher(str);//这里把想要替换的字符串传进来
			 String temp =m.replaceAll("").trim();
			 temp=temp.replaceAll("\\[", "").replaceAll("\\]", "");	
			 ids.set(i, temp);			//去掉特殊字符
		}
		EcmDocument temp = new EcmDocument();
		for(int i = 0;i < ids.size();i++){
		temp = documentService.getObjectById(getToken(), ids.get(i));
		temp.addAttribute("C_PROCESS_STATUS", "已确认");
		temp.addAttribute("C_ITEM_STATUS4", isTrue);
		documentService.updateObject(getToken(), temp, null);	
		OptionLogger.logger(getToken(), detailService, temp, "延误关闭确认", company);
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
}
