package com.ecm.core.cache.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmLanguageMapper;
import com.ecm.core.entity.EcmLanguage;

/**
 * 客制化缓存
 * @Title:
 * @author Haihong Rong
 * @date:   2020-9-20 16:37:56 
 * @Description:
 */
@Component
public class CacheManagerCustom implements ICacheManager<Map<String,Object>>{
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getCustomCache().clear();
	}

	@Override
	public Map<String,Object> refreshCache(String key) {
		
		return null;
	}
	
	public Object getCustomCache(String key) {
		return CacheManagerOper.getCustomCache().get(key);
	}
}
