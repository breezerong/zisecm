package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmSystemEventMapper;
import com.ecm.core.entity.EcmSystemEvent;
/**
 * @ClassName  CacheManagerEcmStore   
 * @Description TODO(配置初始化 缓存类)   
 * @author Haihong Rong
 * @date 2019年6月27日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmSystemEvent implements ICacheManager<EcmSystemEvent>{
	@Autowired
	private EcmSystemEventMapper ecmSystemEvent;
	
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmSystemEvents().clear();
		List<EcmSystemEvent> objList = ecmSystemEvent.selectAll();
		if(objList != null) {
			for(EcmSystemEvent obj:objList) {
				CacheManagerOper.getEcmSystemEvents().put(obj.getName(), obj);
			}
		}
	}

	@Override
	public EcmSystemEvent refreshCache(String key) {
		EcmSystemEvent em = CacheManagerOper.getEcmSystemEvents().remove(key);
		EcmSystemEvent obj = ecmSystemEvent.selectByPrimaryKey(em.getId());
		if(obj != null) {
			CacheManagerOper.getEcmSystemEvents().put(key, obj);
			return obj;
		}
		return null;
	}
}
