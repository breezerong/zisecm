package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmAttributeMapper;
import com.ecm.core.entity.EcmAttribute;
/**
 * @ClassName  CacheManagerEcmStore   
 * @Description TODO(配置初始化 缓存类)   
 * @author Haihong Rong
 * @date 2019年6月27日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmDocType implements ICacheManager<EcmAttribute>{
	@Autowired
	private EcmAttributeMapper ecmAttributeMapper;
	

	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmAttributes().clear();
		List<EcmAttribute> objList = ecmAttributeMapper.selectAll();
		if(objList != null) {
			for(EcmAttribute obj:objList) {
				CacheManagerOper.getEcmAttributes().put(obj.getName(), obj);
			}
		}
	}

	@Override
	public EcmAttribute refreshCache(String key) {
		
		return null;
	}
}
