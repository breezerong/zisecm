package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmGridViewItemMapper;
import com.ecm.core.dao.EcmGridViewMapper;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;

/**
 * 
 * @ClassName  CacheManagerEcmGridView   
 * @Description TODO(列表配置缓存类)   
 * @author Haihong Rong
 * @date 2018年6月26日 上午11:48:16  
 *
 */
@Component
public class CacheManagerEcmGridView implements ICacheManager<EcmGridView>{
	
	@Autowired
	private EcmGridViewMapper ecmGridViewMapper;
	@Autowired
	private EcmGridViewItemMapper ecmGridViewItemMapper;

	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmGridViews().clear();
		List<EcmGridView> eps = ecmGridViewMapper.selectAll();
		if(eps != null) {
			for(EcmGridView ep:eps) {
				List<EcmGridViewItem> items = ecmGridViewItemMapper.selectByParentId(ep.getId());
				ep.setGridViewItems(items);
				CacheManagerOper.getEcmGridViews().put(ep.getName(), ep);
			}
		}
	}

	@Override
	public EcmGridView refreshCache(String key) {
		EcmGridView ep = CacheManagerOper.getEcmGridViews().remove(key);
		if(ep != null) {
			ep = ecmGridViewMapper.selectByPrimaryKey(ep.getId());
			CacheManagerOper.getEcmGridViews().put(key, ep);
			return ep;
		}
		return null;
	}
	
}
