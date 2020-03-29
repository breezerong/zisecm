package com.ecm.portal.controller.admin;

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
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.EcmLangItem;
import com.ecm.core.entity.EcmLanguage;
import com.ecm.core.entity.EcmMenu;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.LangInfoService;
import com.ecm.core.service.LanguageService;
import com.ecm.portal.controller.ControllerAbstract;
/**
 * 语言
 * @author Haihong Rong
 * Date:2020年3月27日 上午9:11:04
 */
@Controller
public class LanguageManager extends ControllerAbstract{

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private LangInfoService langInfoService;
	
	@ResponseBody
	@RequestMapping("/lang/getLanguage")
	public Map<String, Object> getLanguage() {
		List<EcmLanguage> list;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			list = languageService.getAllObject(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	} 
	
	@RequestMapping(value = "/lang/updateLanguge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateLanguge(@RequestBody EcmLanguage obj){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			languageService.updateObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/lang/newLanguge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newLanguge(@RequestBody EcmLanguage obj){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			languageService.newObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value="/lang/getLangInfos", method = RequestMethod.POST)
	public Map<String, Object> getLangInfos(@RequestBody String argStr) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmLangInfo> list = null;
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			list = langInfoService.getObjectByCondition(getToken(), args.get("condition").toString(), pager);
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (SqlDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			e.printStackTrace();
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			e.printStackTrace();
		}
		return mp;
		
	} 
	
	@ResponseBody
	@RequestMapping(value="/lang/getLangInfoByKey", method = RequestMethod.POST)
	public Map<String, Object> getLangInfoByKey(@RequestBody String msgKey) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", langInfoService.getObjectByMsgKey(getToken(), msgKey));
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
		
	}
	
	@RequestMapping(value = "/lang/updateLangInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateLangInfo(@RequestBody EcmLangInfo obj){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			langInfoService.updateObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/lang/newLangInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newLangInfo(@RequestBody EcmLangInfo obj){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			langInfoService.newObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/lang/deleteLangInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteLangInfo(@RequestBody EcmLangInfo obj){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			langInfoService.deleteObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value="/lang/getLangItem", method = RequestMethod.POST)
	public Map<String, Object> getLangItem(@RequestBody String msgKey) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
		
			List<EcmLangItem> list = langInfoService.getLangItem(getToken(), msgKey);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
		
	} 
	
	@RequestMapping(value = "/lang/updateLangItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateLangItem(@RequestBody EcmLangItem obj){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			langInfoService.updateLangItem(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/lang/newLangItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newLangItem(@RequestBody EcmLangItem obj){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			langInfoService.newLangItem(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/lang/deleteLangItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteLangItem(@RequestBody String id){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			langInfoService.deleteLangItem(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
}
