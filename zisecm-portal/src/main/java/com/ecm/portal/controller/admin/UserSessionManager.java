package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.impl.CacheManagerCfgActivity;
import com.ecm.core.cache.manager.impl.CacheManagerEcmAction;
import com.ecm.core.cache.manager.impl.CacheManagerEcmCardSearch;
import com.ecm.core.cache.manager.impl.CacheManagerEcmComponent;
import com.ecm.core.cache.manager.impl.CacheManagerEcmDefType;
import com.ecm.core.cache.manager.impl.CacheManagerEcmDocType;
import com.ecm.core.cache.manager.impl.CacheManagerEcmForm;
import com.ecm.core.cache.manager.impl.CacheManagerEcmGridView;
import com.ecm.core.cache.manager.impl.CacheManagerEcmMenu;
import com.ecm.core.cache.manager.impl.CacheManagerEcmParam;
import com.ecm.core.cache.manager.impl.CacheManagerEcmStore;
import com.ecm.core.cache.manager.impl.CacheManagerEcmSuggestion;
import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;
import com.ecm.core.cache.manager.impl.CacheManagerLanguage;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.search.ESClient;
import com.ecm.core.search.SearchClient;
import com.ecm.core.service.SessionService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class UserSessionManager extends ControllerAbstract{
	
	@Autowired
	SessionService sessionService;

	 @ResponseBody
	 @RequestMapping("/admin/getAllSession")
	 public   Map<String, Object>  initAllCache() {
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			mp.put("data", sessionService.getAllObject(getToken()));
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException | NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", e.getMessage());
		}
		 return mp;
	 }
	 
	 @ResponseBody
	 @RequestMapping(value = "/admin/removeSession", method = RequestMethod.POST)
	 public Map<String, Object> removeSession(HttpServletRequest request, HttpServletResponse response, @RequestBody String id) {
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			if(sessionService.deleteObjectById(getToken(), id)) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", e.getMessage());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", e.getMessage());
		}
		 return mp;
	 }
}
