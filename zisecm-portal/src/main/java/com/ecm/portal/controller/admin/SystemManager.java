package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.ecm.core.search.ESClient;
import com.ecm.core.search.SearchClient;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class SystemManager extends ControllerAbstract{
	
	@Autowired
	CacheManagerEcmAction cacheManagerEcmAction;
	
	@Autowired
	CacheManagerEcmCardSearch cacheManagerEcmCardSearch;
	
	@Autowired
	CacheManagerEcmComponent cacheManagerEcmComponent;
	
	@Autowired
	CacheManagerEcmForm cacheManagerEcmForm;
	
	@Autowired
	CacheManagerEcmGridView cacheManagerEcmGridView;
	
	@Autowired
	CacheManagerEcmMenu cacheManagerEcmMenu;
	
	@Autowired
	CacheManagerEcmParam cacheManagerEcmParam;
	
	@Autowired
	CacheManagerLanguage cacheManagerLanguage;
	
	@Autowired
	CacheManagerLangInfo cacheManagerLangInfo;
	
	@Autowired
	CacheManagerEcmStore cacheManagerEcmStore;
	
	@Autowired
	CacheManagerEcmDefType cacheManagerEcmDefType;
	
	@Autowired
	CacheManagerEcmDocType cacheManagerEcmDocType;
	
	@Autowired
	private CacheManagerEcmSuggestion cacheManagerEcmSuggestion;
	
	@Autowired
	private CacheManagerCfgActivity cacheManagerCfgActivity;
	
	 @ResponseBody
	 @RequestMapping("/admin/initAllCache")
	 public   Map<String, Object>  initAllCache() {
		 cacheManagerEcmAction.initAllCaches();
		 
		 cacheManagerEcmComponent.initAllCaches();
		 cacheManagerEcmForm.initAllCaches();
		 cacheManagerEcmGridView.initAllCaches();
		 cacheManagerEcmMenu.initAllCaches();
		 cacheManagerEcmParam.initAllCaches();
		 cacheManagerLanguage.initAllCaches();
		 cacheManagerLangInfo.initAllCaches();
		 cacheManagerEcmStore.initAllCaches();
		 cacheManagerEcmDocType.initAllCaches();
		 cacheManagerEcmCardSearch.initAllCaches();
		 cacheManagerEcmSuggestion.initAllCaches();
		 cacheManagerEcmDefType.initAllCaches();
		 cacheManagerCfgActivity.initAllCaches();
		 CacheManagerOper.getCustomCache().clear();
		 
		 SearchClient.getInstance().refreshCache();
		 
		 ESClient.getInstance().refreshCache();
		 
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 return mp;
	 }
}
