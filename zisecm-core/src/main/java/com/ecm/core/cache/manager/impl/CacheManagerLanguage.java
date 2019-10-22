package com.ecm.core.cache.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.ICacheManager;
import com.ecm.core.dao.EcmLanguageMapper;
import com.ecm.core.entity.EcmLanguage;

/**
 * 
 * @author Haihong Rong
 * @date 2019年9月29日 下午3:50:36
 */
@Component
public class CacheManagerLanguage implements ICacheManager<Map<String,String>>{
	@Autowired
	private EcmLanguageMapper ecmLanguageMapper;
	
	@Override
	public void initAllCaches() {
		CacheManagerOper.getLanguageCaches().clear();
		List<EcmLanguage> list = ecmLanguageMapper.selectAll();
		if(list != null) {
			for(EcmLanguage lang : list) {
				String key = lang.getLangKey()+"_"+lang.getTypeName()+"_"+lang.getAttrName();
				CacheManagerOper.getLanguageCaches().put(key, lang.getAttrLabel());
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
	public static String getLanguage(String lang, String attrName) {
		return getLanguage( lang,  null,  attrName);
	}

	/**
	 * 获取语言
	 * @param lang
	 * @param typeName
	 * @param attrName
	 * @return
	 */
	public static String getLanguage(String lang, String typeName, String attrName) {
		if(typeName == null) {
			typeName = "";
		}
		String key = lang + "_" + typeName + "_" + attrName;
		return CacheManagerOper.getLanguageCaches().get(key);
	}
}
