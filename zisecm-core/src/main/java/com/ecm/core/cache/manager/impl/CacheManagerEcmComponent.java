package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.entity.EcmComponent;
/**
 * @ClassName  CacheManagerEcmComponent   
 * @Description TODO(配置初始化 缓存类)   
 * @author lgx
 * @date 2018年6月27日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmComponent implements ICacheManager<EcmComponent>{
	@Autowired
	private EcmComponentMapper ecmComponentMapper;
	

	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmComponents().clear();
		List<EcmComponent> objList = ecmComponentMapper.selectAll();
		if(objList != null) {
			for(EcmComponent obj:objList) {
				CacheManagerOper.getEcmComponents().put(obj.getName(), obj);
			}
		}
	}

	@Override
	public EcmComponent refreshCache(String key) {
		EcmComponent em = CacheManagerOper.getEcmComponents().remove(key);
		EcmComponent obj = ecmComponentMapper.selectByPrimaryKey(em.getId());
		if(obj != null) {
			CacheManagerOper.getEcmComponents().put(key, obj);
			return obj;
		}
		return null;
	}
}
