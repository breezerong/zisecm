package com.ecm.core.cache.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmCfgActivityMapper;
import com.ecm.core.dao.EcmLangInfoMapper;
import com.ecm.core.dao.EcmLangItemMapper;
import com.ecm.core.dao.EcmLanguageMapper;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.EcmLangItem;
import com.ecm.core.entity.EcmLanguage;

@Component
public class CacheManagerFinishLoadCacheTag implements ICacheManager<Map<String,String>>{
	
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.setFinishLoadCacheTag(true);
	}
	
	

	@Override
	public Map<String, String> refreshCache(String key) {
		CacheManagerOper.setFinishLoadCacheTag(false);
		return null;
	}

}
