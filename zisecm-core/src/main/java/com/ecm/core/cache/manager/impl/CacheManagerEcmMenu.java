package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.entity.EcmMenu;
import com.ecm.core.service.MenuService;
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
	private MenuService menuService;
	
	
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmMenus().clear();
		List<EcmMenu> objList = menuService.getMenuWithChild(null);
		if(objList != null) {
			for(EcmMenu obj:objList) {
				CacheManagerOper.getEcmMenus().put(obj.getName(), obj);
			}
		}
		
	}
	

	@Override
	public EcmMenu refreshCache(String key) {
		CacheManagerOper.getEcmMenus().remove(key);
		EcmMenu obj = menuService.getObjectByName(null, key);
		if(obj != null) {
			CacheManagerOper.getEcmMenus().put(key, obj);
			return obj;
		}
		return null;
	}

}
