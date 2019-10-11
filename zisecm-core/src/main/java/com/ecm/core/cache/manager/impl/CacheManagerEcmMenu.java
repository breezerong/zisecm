package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmMenuMapper;
import com.ecm.core.entity.EcmMenu;
/**
 * @ClassName  CacheManagerEcmMenu   
 * @Description TODO(事件配置初始化 缓存类)   
 * @author lgx
 * @date 2018年6月27日 上午11:48:16 
 *
 */
@Component
public class CacheManagerEcmMenu implements ICacheManager<EcmMenu>{
	@Autowired
	private EcmMenuMapper ecmMenuMapper;
	
	
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmMenuList().clear();
		List<EcmMenu> objList = ecmMenuMapper.selectAll();
		if(objList != null) {
			CacheManagerOper.getEcmMenuList().addAll(objList); //带顺序的集合
			for(EcmMenu obj:objList) {
				CacheManagerOper.getEcmMenus().put(obj.getName(), obj);
			}
		}
		
	}
	

	@Override
	public EcmMenu refreshCache(String key) {
		EcmMenu em = CacheManagerOper.getEcmMenus().remove(key);
		EcmMenu obj = ecmMenuMapper.selectByPrimaryKey(em.getId());
		if(obj != null) {
			CacheManagerOper.getEcmMenus().put(key, obj);
			return obj;
		}
		return null;
	}

}
