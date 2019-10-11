package com.ecm.core.cache.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmCardSearchItemMapper;
import com.ecm.core.dao.EcmCardSearchMapper;
import com.ecm.core.dao.EcmFormItemMapper;
import com.ecm.core.entity.EcmCardSearch;
import com.ecm.core.entity.EcmCardSearchItem;
import com.ecm.core.entity.EcmFormItem;

/**
 * @ClassName  CacheManagerEcmCardSearch   
 * @Description TODO(卡片搜索缓存类)   
 * @author yaozhigang
 * @date 2018年6月27日 上午9:48:16 
 *
 */
@Component
public class CacheManagerEcmCardSearch implements ICacheManager<EcmCardSearch>{
	
	
	@Autowired
	private EcmCardSearchMapper ecmCardSearchMapper;
	
	@Autowired
	private EcmCardSearchItemMapper ecmCardSearchItemMapper;
	
	@Autowired
	private EcmFormItemMapper ecmFormItemMapper;
	
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getEcmCardSearchs().clear();
		List<EcmCardSearch> objList = ecmCardSearchMapper.selectAll();
		if(objList != null) {
			EcmFormItem it = null;
			for(EcmCardSearch obj:objList) {
		
				CacheManagerOper.getEcmCardSearchs().put(obj.getName(), obj);
				
				//获取属性定义，更新到缓存中
				List<EcmCardSearchItem> oldItems = ecmCardSearchItemMapper.selectByParentId(obj.getId());
				if(oldItems!=null)
				{
					for(EcmCardSearchItem ecs:oldItems) {
						if(ecs.getFormItemId() != null && ecs.getFormItemId().length()>0) {
							it = CacheManagerOper.getEcmFormItems().get(ecs.getFormItemId());
							if(it == null) {
								it = ecmFormItemMapper.selectByPrimaryKey(ecs.getFormItemId());
							}
							ecs.setEcmFormItem(it);
						}
					}
				}
				obj.setEcmCardSearchItems(oldItems);
			}
		}
	}

	@Override
	public EcmCardSearch refreshCache(String key) {
		EcmCardSearch ep = CacheManagerOper.getEcmCardSearchs().remove(key);
		if(ep != null) {
			ep = ecmCardSearchMapper.selectByPrimaryKey(ep.getId());
			CacheManagerOper.getEcmCardSearchs().put(key, ep);
			return ep;
		}
		return null;
	}
	
}
