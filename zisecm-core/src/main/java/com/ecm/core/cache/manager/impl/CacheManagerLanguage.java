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
 * 
 * @author Haihong Rong
 * @date 2019年9月29日 下午3:50:36
 */
@Component
public class CacheManagerLanguage implements ICacheManager<Map<String,String>>{
	@Autowired
	private EcmLanguageMapper ecmLanguageMapper;
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getLanguages().clear();
		List<EcmLanguage> list = ecmLanguageMapper.selectAll();
		if(list != null) {
			for(EcmLanguage lang : list) {
				CacheManagerOper.getLanguages().put(lang.getName(), lang.getDescription());
			}
		}
	}

	@Override
	public Map<String,String> refreshCache(String key) {
		return null;
	}
}
