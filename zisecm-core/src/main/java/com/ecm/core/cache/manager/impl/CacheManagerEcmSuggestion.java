package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.dao.EcmStoreMapper;
import com.ecm.core.dao.EcmSuggestionMapper;
import com.ecm.core.entity.EcmComponent;
import com.ecm.core.entity.EcmStore;
import com.ecm.core.entity.EcmSuggestion;
/**
 * @ClassName  CacheManagerEcmStore   
 * @Description TODO(配置初始化 缓存类)   
 * @author Haihong Rong
 * @date 2019年6月27日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmSuggestion implements ICacheManager<EcmSuggestion>{
	@Autowired
	private EcmSuggestionMapper ecmSuggestionMapper;
	

	
	@Override
	public void initAllCaches() {
		CacheManagerOper.setSuggestionCache(ecmSuggestionMapper.selectAll());
	}

	@Override
	public EcmSuggestion refreshCache(String key) {
		
		return null;
	}
}
