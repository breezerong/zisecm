package com.ecm.core.cache.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmCfgActivityMapper;
import com.ecm.core.dao.EcmLangInfoMapper;
import com.ecm.core.dao.EcmLangItemMapper;
import com.ecm.core.dao.EcmLanguageMapper;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.EcmLangItem;
import com.ecm.core.entity.EcmLanguage;

/**
 * 
 * @author Haihong Rong
 * @date 2019年9月29日 下午3:50:36
 */
@Component
public class CacheManagerCfgActivity implements ICacheManager<Map<String,String>>{
	@Autowired
	private EcmCfgActivityMapper ecmCfgActivityMapper;
	
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getCfgActivityCache().clear();
		List<EcmCfgActivity> list = ecmCfgActivityMapper.selectAll();
		if(list != null) {
			for(EcmCfgActivity item : list) {
				CacheManagerOper.getCfgActivityCache().put(item.getProcessName()+item.getActivityName(), item);
			}
		}
	}
	
	

	@Override
	public Map<String,String> refreshCache(String key) {
		return null;
	}

	/**
	 * 获取活动配置
	 * @param processName
	 * @param activityName
	 * @return
	 */
	public static EcmCfgActivity getCfgActivity(String processName,String activityName) {
		return CacheManagerOper.getCfgActivityCache().get(processName+activityName);
	}
}
