package com.ecm.core.cache.product;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

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
import com.ecm.core.cache.manager.impl.CacheManagerEcmSystemEvent;
import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;
import com.ecm.core.cache.manager.impl.CacheManagerLanguage;

/**
 * 
 * @ClassName  CacheProduct   
 * @Description TODO(系统启动加载各种缓存)   
 * @author Haihong Rong
 * @date 2018年6月26日 上午11:33:29  
 *
 */
@Component
public class CacheProduct implements ApplicationRunner,Ordered {
	
	private final Logger logger = LoggerFactory.getLogger(CacheProduct.class);
	
	@Autowired
	private CacheManagerEcmParam cacheManagerEcmParam; //系统参数配置
	
	@Autowired
	private CacheManagerEcmAction cacheManagerEcmAction;//系统事件配置
	
	@Autowired
	private CacheManagerEcmGridView cacheManagerEcmGridView;//列表配置
	
	@Autowired
	private CacheManagerEcmForm cacheManagerEcmForm;//表单配置	
	
	@Autowired
	private CacheManagerEcmMenu cacheManagerEcmMenu;//菜单配置
	
	@Autowired
	private CacheManagerEcmCardSearch cacheManagerEcmCardSearch;//卡片搜索配置
	
	@Autowired
	private CacheManagerLanguage cacheManagerLanguage;//语言配置
	
	@Autowired
	private CacheManagerLangInfo cacheManagerLangInfo;//语言标签配置
	
	@Autowired
	private CacheManagerEcmComponent cacheManagerEcmComponent;//组件配置
	
	@Autowired
	private CacheManagerEcmStore cacheManagerEcmStore;//存储配置
	
	@Autowired
	private CacheManagerEcmSystemEvent cacheManagerEcmSystemEvent;//事件配置
	
	@Autowired
	private CacheManagerEcmDocType cacheManagerEcmDocType;//类型配置
	
	@Autowired
	private CacheManagerEcmSuggestion cacheManagerEcmSuggestion;
	
	@Autowired
	private CacheManagerEcmDefType cacheManagerEcmDefType;//类型配置
	
	@Autowired
	private CacheManagerCfgActivity cacheManagerCfgActivity;//活动配置
	
	//@Autowired
	//private PdfSignService pdfService;

	@Override
	public int getOrder() {
		return 5;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Start load cache: "+ (new Date()).toString());
		//语言标签配置
		cacheManagerLanguage.initAllCaches();
		cacheManagerLangInfo.initAllCaches();
		//初始化系统配置
		cacheManagerEcmParam.initAllCaches();

		//初始化列表配置
		cacheManagerEcmGridView.initAllCaches();

		//初始化事件配置
		cacheManagerEcmAction.initAllCaches();
		
		//初始化表单配置
		cacheManagerEcmForm.initAllCaches();

		//菜单配置
		cacheManagerEcmMenu.initAllCaches();

		//初始化卡片搜索配置
		cacheManagerEcmCardSearch.initAllCaches();
		
		
		
		//组件配置
		cacheManagerEcmComponent.initAllCaches();
		//存储配置
		cacheManagerEcmStore.initAllCaches();
		
		cacheManagerEcmSystemEvent.initAllCaches();
		
		cacheManagerEcmDocType.initAllCaches();
		
		cacheManagerEcmSuggestion.initAllCaches();
		
		cacheManagerEcmDefType.initAllCaches();
		
		cacheManagerCfgActivity.initAllCaches();
		
		logger.info("Complete load cache: "+ (new Date()).toString());
		//pdfService.run();
	}

}
