package com.ecm.core.cache.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmLangInfoMapper;
import com.ecm.core.dao.EcmLangItemMapper;
import com.ecm.core.dao.EcmLanguageMapper;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.EcmLangItem;
import com.ecm.core.entity.EcmLanguage;

/**
 * 
 * @author Haihong Rong
 * @date 2019年9月29日 下午3:50:36
 */
@Component
public class CacheManagerLangInfo implements ICacheManager<Map<String,String>>{
	@Autowired
	private EcmLangInfoMapper ecmLangInfoMapper;
	@Autowired
	private EcmLangItemMapper ecmLangItemMapper;
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getLangInfos().clear();
		List<EcmLangInfo> list = ecmLangInfoMapper.selectAll();
		List<EcmLangItem> listItem = ecmLangItemMapper.selectAll();
		if(list != null) {
			for(EcmLangInfo lang : list) {
				addItem(lang, listItem);
				CacheManagerOper.getLangInfos().put(lang.getMessageKey(), lang);
			}
		}
	}
	
	private void addItem(EcmLangInfo lang, List<EcmLangItem> listItem) {
		for(EcmLangItem item: listItem) {
			if(item.getMessageKey().equals(lang.getMessageKey())) {
				lang.getLangItems().add(item);
			}
		}
	}

	@Override
	public Map<String,String> refreshCache(String key) {
		return null;
	}
	/**
	 * 获取语言
	 * @param lang
	 * @param attrName
	 * @return
	 */
	public static EcmLangInfo getLanguage(String key) {
		EcmLangInfo info =  CacheManagerOper.getLangInfos().get(key);
		if(info == null) {
			info = new EcmLangInfo();
		}
		return info;
	}
}
