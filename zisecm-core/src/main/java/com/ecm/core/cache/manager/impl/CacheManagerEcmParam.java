package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.StaticVar;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmParameterMapper;
import com.ecm.core.entity.EcmParameter;

/**
 * 
 * @ClassName  CacheManagerEcmParam   
 * @Description TODO(系统参数缓存类)   
 * @author yaozhigang
 * @date 2018年6月26日 上午11:48:16  
 *
 */
@Component
public class CacheManagerEcmParam implements ICacheManager<EcmParameter>{
	
	@Autowired
	private EcmParameterMapper ecmParameterMapper;

	@Override
	public void initAllCaches() {
		StaticVar.TEMPLATE_CHANGED = true;
		CacheManagerOper.getEcmParameters().clear();
		List<EcmParameter> eps = ecmParameterMapper.selectAll();
		if(eps != null) {
			for(EcmParameter ep:eps) {
				CacheManagerOper.getEcmParameters().put(ep.getName(), ep);
			}
		}
	}

	@Override
	public EcmParameter refreshCache(String key) {
		CacheManagerOper.getEcmParameters().remove(key);
		EcmParameter ep = ecmParameterMapper.selectByName(key);
		if(ep != null) {
			CacheManagerOper.getEcmParameters().put(key, ep);
			return ep;
		}
		return null;
	}

}
