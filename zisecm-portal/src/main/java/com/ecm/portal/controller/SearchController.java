package com.ecm.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.ChartBean;
import com.ecm.core.entity.EcmCardSearch;
import com.ecm.core.entity.EcmCardSearchItem;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.CardSearchService;
import com.ecm.core.service.ReportService;
import com.ecm.core.service.SearchService;
/**
 * 查询服务
 * @author Haihong Rong
 * @date 2019年10月2日 上午8:51:18
 */
@Controller
public class SearchController extends ControllerAbstract{

	@Autowired
	private SearchService searchService;
	
	@Autowired
	private CardSearchService cardSearchService;
	
	/**
	 * 关键字查询
	 * @return
	 */
	@RequestMapping("/search/searchByKeyword")
	@ResponseBody
	public Map<String, Object> searchByKeyword(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String keyword = args.get("keyword").toString();
		boolean onlyProperty = args.get("attrOnly")== null?false:args.get("attrOnly").toString().equals("true");
		List<String> typeNames = args.get("typeNames")== null?null:JSONUtils.stringToArray(args.get("typeNames").toString());
		String termStr = (String)args.get("terms");
		Map<String, List<String>> termCondition = new HashMap<String, List<String>>() ;
		List<String> temp = null;
		if(!StringUtils.isEmpty(termStr)) {
			temp =JSONUtils.stringToArray(termStr);
			for(String str:temp) {
				Map<String, Object> condStr = JSONUtils.stringObjectToMap(str);
				String fieldName = condStr.get("fieldName").toString();
				List<String> vals = (List<String>)condStr.get("values");
				//List<String> values = JSONUtils.stringToArray(temp.get(1));
				termCondition.put(fieldName, vals);
			}
		}
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		pager.setScrollId((String)args.get("scrollId"));
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", searchService.findByContentScroll(getToken(), pager, typeNames, keyword,termCondition, onlyProperty));
			mp.put("total", pager.getTotal());
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	@RequestMapping("/search/searchByCard")
	@ResponseBody
	public Map<String, Object> searchByCard(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String typeName = (String)args.get("typeName");
		String gridName = args.get("gridName").toString();
		List<EcmFormItem> conditions = JSONArray.parseArray(args.get("conditions").toString(), EcmFormItem.class);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", searchService.findByCard(getToken(), gridName, pager, typeName, conditions));
			mp.put("total", pager.getTotal());
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/search/getSuggestion", method = RequestMethod.POST)
	public Map<String, Object> getSuggestion(@RequestBody String keyword) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<String> list = searchService.getSuggestion(getToken(),keyword);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/search/getCardSearchs", method = RequestMethod.POST)
	public Map<String, Object> getCardSearchs(@RequestBody String lang) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmCardSearch> list = cardSearchService.getEnabledCards(getToken(), lang.replace("\"", ""), false,false);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/search/getCardSearch", method = RequestMethod.POST)
	public Map<String, Object> getCardSearch(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String id = args.get("id").toString();
			String lang = args.get("lang").toString();
			EcmCardSearch en = cardSearchService.getCard(getToken(), id, lang, false);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", en);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/search/getCardSearchAll", method = RequestMethod.POST)
	public Map<String, Object> getCardSearchAll(@RequestBody String lang) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmCardSearch> list = cardSearchService.getEnabledCards(getToken(), lang.replace("\"", ""), false,true);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/search/getCardSearchItem", method = RequestMethod.POST)
	public Map<String, Object> getCardSearchItem(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String id = args.get("id").toString();
			String lang = args.get("lang").toString();
			List<EcmFormItem> list = cardSearchService.getFormItems(getToken(), lang, id);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
}
