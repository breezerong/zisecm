package com.ecm.icore.service;

import java.util.List;
import java.util.Map;

import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
/**
 * 查询服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:58:57
 */
public interface ISearchService {
	Map<String,Object> findByContent(String token, Pager pager, String keyword);
	Map<String,Object> findByQuery(String token, Pager pager, String query);
	Map<String,Object> findByContent(String token, Pager pager, String keyword, boolean onlyProperty);
	Map<String,Object>  findByContent(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) throws AccessDeniedException;
	/**
	 * 滚屏方式查询，只能全文
	 * @param token
	 * @param pager
	 * @param typeNames
	 * @param keyword
	 * @param termCondition
	 * @param onlyProperty
	 * @return
	 * @throws AccessDeniedException 
	 */
	Map<String,Object>  findByContentScroll(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) throws AccessDeniedException;
	List<String> getSuggestion(String keyword);
	
	Object findByCard(String token,String gridName, Pager pager, String typeName, List<EcmFormItem> conditions);
}
