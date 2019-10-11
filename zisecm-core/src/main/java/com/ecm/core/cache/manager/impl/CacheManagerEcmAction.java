package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmActionMapper;
import com.ecm.core.entity.EcmAction;

/**
 * @ClassName  CacheManagerEcmParam   
 * @Description TODO(事件配置初始化 缓存类)   
 * @author lgx
 * @date 2018年6月26日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmAction implements ICacheManager<EcmAction>{
	
	
	@Autowired
	private EcmActionMapper ecmActionMapper;
	
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmActions().clear();
		List<EcmAction> objList = ecmActionMapper.selectAll();
		if(objList != null) {
			for(EcmAction obj:objList) {
				CacheManagerOper.getEcmActions().put(obj.getName(), obj);
			}
		}
	}

	@Override
	public EcmAction refreshCache(String key) {
//		CacheManagerOper.getEcmActions().remove(key);
//		EcmAction obj = ecmActionMapper.selectByPrimaryKey(key);
//		if(obj != null) {
//			CacheManagerOper.getEcmActions().put(key, obj);
//			return obj;
//		}
		return null;
	}



}
