package com.ecm.core.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmSuggestion;
import com.ecm.core.entity.Pager;
import com.ecm.core.search.ESClient;
import com.ecm.core.search.SearchClient;
import com.ecm.icore.service.ISearchService;

@Component
public class DBSearchService  extends EcmService  implements ISearchService {
	
	@Autowired
	private DocumentService documentService;

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findByQuery(String token, Pager pager, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, String keyword, boolean onlyProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findByContentScroll(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSuggestion(String keyword) {
		// TODO Auto-generated method stub
		List<EcmSuggestion> list = new ArrayList<EcmSuggestion>();
		if(keyword == null || keyword.trim().length()==0) {
			return getStringList(list);
		}
		try {
			keyword = (new String(keyword.getBytes(),"utf-8")).toLowerCase().replace("\"", "");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(EcmSuggestion en:CacheManagerOper.getSuggestionCache()) {
			String name = en.getName();
			try {
				name = (new String(name.getBytes(),"utf-8")).toLowerCase().replace("\"", "");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(name.indexOf(keyword)>-1) {
				putList(list,en);
			}
			if(list.size()>=ESClient.getInstance().getSuggestionCount()) {
				return getStringList(list);
			}
		}
		return getStringList(list);
	}
	
	private List<String> getStringList(List<EcmSuggestion> list){
		List<String> valList = new ArrayList<String>();
		for(int i=0;i<list.size();i++) {
			EcmSuggestion item = list.get(i);
			valList.add(item.getName());
		}
		return valList;
	}
	
	private void putList(List<EcmSuggestion> list, EcmSuggestion en) {
		for(int i=0;i<list.size();i++) {
			EcmSuggestion item = list.get(i);
			if(en.getWeight()>item.getWeight()) {
				list.add(i, en);
				return;
			}
		}
		list.add(en);
	}

	@Override
	public Object findByCard(String token, String gridName,Pager pager, String typeName, List<EcmFormItem> conditions) {
		// TODO Auto-generated method stub
		String cond = " STATUS='"+SearchClient.getInstance().getReleaseStatus()+"' ";
		if(!StringUtils.isEmpty(SearchClient.getInstance().getNotInTypeName()))
		{
			if(SearchClient.getInstance().getNotInTypeName().indexOf("'")>=0) {
				cond += " and TYPE_NAME not in("+SearchClient.getInstance().getNotInTypeName()+")";
			}else {
				cond += " and TYPE_NAME not in('"+SearchClient.getInstance().getNotInTypeName().replace(",", "','")+"')";
			}
		}
		if(!typeName.equalsIgnoreCase("all")&&!typeName.equals("所有")) {
			cond += " and TYPE_NAME='"+typeName+"'";
		}
		for(EcmFormItem item:conditions) {
			String attrName = item.getAttrName();
			if(item.getControlType().equalsIgnoreCase("date")) {
				if(attrName.startsWith("dtstart_")) {
					attrName = attrName.replace("dtstart_", "");
					cond +=" and "+ attrName +" >= "+ DBFactory.getDBConn().getDBUtils().getDBDateString(item.getDefaultValue());
				}else if(attrName.startsWith("dtend_")){
					attrName = attrName.replace("dtend_", "");
					cond +=" and "+  attrName +" <= "+ DBFactory.getDBConn().getDBUtils().getDBDateString(item.getDefaultValue());
				}
			}else if(item.getControlType().toLowerCase().indexOf("select")>-1) {
				cond +=" and "+  attrName +"='"+ item.getDefaultValue()+"'";
			}else if(item.getControlType().equalsIgnoreCase("Integer")) {
				if(attrName.startsWith("numstart_")) {
					attrName = attrName.replace("numstart_", "");
					cond +=" and "+  attrName +" >= "+ item.getDefaultValue();
				}else if(attrName.startsWith("numend_")){
					attrName = attrName.replace("numend_", "");
					cond +=" and "+  attrName +" <= "+ item.getDefaultValue();
				}
			}
			else {
				cond +=" and "+  attrName +" like '%"+ item.getDefaultValue()+"%'";
				
			}
		}
		return documentService.getObjects(token, gridName, null, pager, cond, "CREATION_DATE DESC");
	}

}
