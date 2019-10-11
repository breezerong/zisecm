package com.ecm.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmCardSearch;
import com.ecm.core.entity.EcmCardSearchItem;
import com.ecm.core.entity.EcmFormItem;

@Service
public class CardSearchService extends EcmService{
	
	public List<EcmCardSearch> getEnabledCards(String token,String lang, boolean includeItem ,boolean includeAll){
		List<EcmCardSearch> list  = new ArrayList<EcmCardSearch>();
		for(String key:CacheManagerOper.getEcmCardSearchs().keySet()) {
			EcmCardSearch en = CacheManagerOper.getEcmCardSearchs().get(key);
			if(en != null && en.isEnabled() && ((!includeAll 
					&& !en.getName().equalsIgnoreCase("所有") && !en.getName().equalsIgnoreCase("All"))
					||includeAll)) {
				addToList(list,en.clone(lang,includeItem));
			}
		}
		return list;
	}
	
	private void addToList(List<EcmCardSearch> list, EcmCardSearch en) {
		if(list.size() == 0) {
			list.add(en);
			return;
		}
		for(int i=0; i< list.size(); i++) {
			if(en.getOrderIndex()<list.get(i).getOrderIndex()) {
				list.add(i, en);
				return;
			}
		}
		list.add(en);
	}
	
	public List<EcmFormItem> getFormItems(String token,String lang, String id){
		EcmCardSearch en = getCardSearch(id);
		if( en != null) {
			List<EcmCardSearchItem> list = en.clone(token, true).getEcmCardSearchItems();
			List<EcmFormItem> flist = new ArrayList<EcmFormItem>();
			for(EcmCardSearchItem item:list) {
				flist.add(item.getEcmFormItem());
			}
			return flist;
		}
		return null;
	}
	/**
	 * 
	 * @param token
	 * @param lang
	 * @param id
	 * @return
	 */
	public List<EcmCardSearchItem> getCardItems(String token,String lang, String id){
		EcmCardSearch en = getCardSearch(id);
		if( en != null) {
			return en.clone(token, true).getEcmCardSearchItems();
		}
		return null;
	}
	/**
	 * 
	 * @param token
	 * @param id
	 * @param lang
	 * @param includeItem
	 * @return
	 */
	public EcmCardSearch getCard(String token, String id, String lang, boolean includeItem) {
		EcmCardSearch en =  getCardSearch(id);
		if(en != null) {
			return en.clone(lang, includeItem);
		}
		return null;
	}
	
	private EcmCardSearch getCardSearch(String id) {
		for(String key:CacheManagerOper.getEcmCardSearchs().keySet()) {
			EcmCardSearch en = CacheManagerOper.getEcmCardSearchs().get(key);
			if(en.getId().equals(id))
			{
				return en;
			}
		}
		return null;
	}
}
