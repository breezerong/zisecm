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
import com.ecm.core.cache.manager.impl.CacheManagerFinishLoadCacheTag;
import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;
import com.ecm.core.cache.manager.impl.CacheManagerLanguage;
//import com.ecm.core.sync.SyncPublicNet;

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

	@Autowired
	private CacheManagerFinishLoadCacheTag cacheManagerFinishLoadCacheTag;//活动配置
	
	

//	@Autowired
//	SyncPublicNet syncPublicNet;
	//@Autowired
	//private PdfSignService pdfService;

	@Override
	public int getOrder() {
		return 5;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Start load cache: "+ (new Date()).toString());
		long start = System.currentTimeMillis();
		//语言标签配置
		cacheManagerLanguage.initAllCaches();
		long cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerLanguage:"+cost);
		start = System.currentTimeMillis();
		cacheManagerLangInfo.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerLangInfo:"+cost);
		start = System.currentTimeMillis();
		
		//组件配置
		cacheManagerEcmComponent.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmComponent:"+cost);
		start = System.currentTimeMillis();
				
		//菜单配置
		cacheManagerEcmMenu.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmMenu:"+cost);
		start = System.currentTimeMillis();
		
		//初始化系统配置
		cacheManagerEcmParam.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmParam:"+cost);
		start = System.currentTimeMillis();
		//初始化列表配置
		cacheManagerEcmGridView.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmGridView:"+cost);
		start = System.currentTimeMillis();
		//初始化事件配置
		cacheManagerEcmAction.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmAction:"+cost);
		start = System.currentTimeMillis();
		//初始化表单配置
		cacheManagerEcmForm.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmForm:"+cost);
		start = System.currentTimeMillis();
		
		//初始化卡片搜索配置
		cacheManagerEcmCardSearch.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmCardSearch:"+cost);
		start = System.currentTimeMillis();
		
		
		
				
		//存储配置
		cacheManagerEcmStore.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmStore:"+cost);
		start = System.currentTimeMillis();
		
		cacheManagerEcmSystemEvent.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmSystemEvent:"+cost);
		start = System.currentTimeMillis();
		
		cacheManagerEcmDocType.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmDocType:"+cost);
		start = System.currentTimeMillis();
		
		cacheManagerEcmSuggestion.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmSuggestion:"+cost);
		start = System.currentTimeMillis();
		
		cacheManagerEcmDefType.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerEcmDefType:"+cost);
		start = System.currentTimeMillis();
		
		cacheManagerCfgActivity.initAllCaches();
		cost = System.currentTimeMillis() - start;
		logger.info("Load cacheManagerCfgActivity:"+cost);
		start = System.currentTimeMillis();
		
		cacheManagerFinishLoadCacheTag.initAllCaches();	//标记所有cache 加载完毕
		logger.info("Complete load cache: "+ (new Date()).toString());

	}

}
