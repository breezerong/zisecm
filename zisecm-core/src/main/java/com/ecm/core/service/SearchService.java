package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.AuditContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.icore.service.ISearchService;

@Service
public class SearchService  extends EcmService implements ISearchService {

	@Autowired
	private ESSearchService esSearchService;
	
	@Autowired
	private DBSearchService dbSearchService;
	
	@Autowired
	private AuditService auditService;
	
	@Override
	public Map<String, Object> findByContent(String token, Pager pager, String keyword) {
		// TODO Auto-generated method stub
		return esConfiged()?esSearchService.findByContent(token,pager,keyword):dbSearchService.findByContent(token,pager,keyword);
	}

	@Override
	public Map<String, Object> findByQuery(String token, Pager pager, String query) {
		// TODO Auto-generated method stub
		return esConfiged()?esSearchService.findByQuery(token,pager,query):dbSearchService.findByQuery(token,pager,query);
	}

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, String keyword, boolean onlyProperty) {
		// TODO Auto-generated method stub
		return esConfiged()?esSearchService.findByContent(token, pager, keyword, onlyProperty):dbSearchService.findByContent(token, pager, keyword, onlyProperty);
	}
	
	private boolean esConfiged() {
		if(CacheManagerOper.getEcmParameters().get("IndexServer") != null &&
				StringUtils.isNotEmpty(CacheManagerOper.getEcmParameters().get("IndexServer").getValue())) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) throws AccessDeniedException {
		// TODO Auto-generated method stub
		Map<String, Object>  list = esSearchService.findByContent(token, pager, typeNames, keyword, termCondition, onlyProperty);
		auditService.newAudit(token, null, AuditContext.FULL_TEXT_SEARCH, null, null, keyword);
		return list;
	}

	@Override
	public Map<String, Object> findByContentScroll(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, int searchType) throws AccessDeniedException {
		// TODO Auto-generated method stub
		
		Map<String, Object>  list =  esSearchService.findByContentScroll(token, pager, typeNames, keyword, termCondition, searchType);
		auditService.newAudit(token, null, AuditContext.FULL_TEXT_SEARCH, null, null, keyword);
		return list;
	}

	public List<String> getSuggestion(String token, String keyword) {
		// TODO Auto-generated method stub
		return dbSearchService.getSuggestion(keyword);
	}

	@Override
	public List<String> getSuggestion(String keyword) {
		// TODO Auto-generated method stub
		return dbSearchService.getSuggestion(keyword);
	}
	@Override
	public Object findByCard(String token, String gridName, Pager pager, String typeName, List<EcmFormItem> conditions) {
		// TODO Auto-generated method stub
		return dbSearchService.findByCard( token,  gridName,  pager,  typeName, conditions);
	}

}
