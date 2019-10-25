package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmDefTypeMapper;
import com.ecm.core.entity.EcmDefType;
/**
 * @ClassName     
 * @Description TODO(配置初始化 缓存类)   
 * @author Haihong Rong
 * @date 2019年6月27日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmDefType implements ICacheManager<EcmDefType>{
	@Autowired
	private EcmDefTypeMapper ecmDefTypeMapper;
	

	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmDefTypes().clear();
		List<EcmDefType> objList = ecmDefTypeMapper.selectAll();
		if(objList != null) {
			for(EcmDefType obj:objList) {
				CacheManagerOper.getEcmDefTypes().put(obj.getName(), obj);
			}
		}
	}

	@Override
	public EcmDefType refreshCache(String key) {
		EcmDefType em = CacheManagerOper.getEcmDefTypes().get(key);
		EcmDefType obj = ecmDefTypeMapper.selectByPrimaryKey(em.getId());
		if(obj != null) {
			CacheManagerOper.getEcmDefTypes().put(key, obj);
			return obj;
		}
		return null;
	}
}
