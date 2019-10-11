package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.dao.EcmStoreMapper;
import com.ecm.core.entity.EcmComponent;
import com.ecm.core.entity.EcmStore;
/**
 * @ClassName  CacheManagerEcmStore   
 * @Description TODO(配置初始化 缓存类)   
 * @author Haihong Rong
 * @date 2019年6月27日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmStore implements ICacheManager<EcmStore>{
	@Autowired
	private EcmStoreMapper ecmStoreMapper;
	

	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmStores().clear();
		List<EcmStore> objList = ecmStoreMapper.selectAll();
		if(objList != null) {
			for(EcmStore obj:objList) {
				CacheManagerOper.getEcmStores().put(obj.getName(), obj);
			}
		}
	}

	@Override
	public EcmStore refreshCache(String key) {
		EcmStore em = CacheManagerOper.getEcmStores().remove(key);
		EcmStore obj = ecmStoreMapper.selectByPrimaryKey(em.getId());
		if(obj != null) {
			CacheManagerOper.getEcmStores().put(key, obj);
			return obj;
		}
		return null;
	}
}
