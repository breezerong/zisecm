package com.ecm.core.search;

import com.ecm.core.cache.manager.CacheManagerOper;
/**
 * 查询基本配置
 * @author Haihong Rong
 * @date 2019年11月24日 上午11:51:13
 */
public class SearchClient {

	private boolean inited = false;
	
	private static SearchClient current = new SearchClient();
	
	private String releaseStatus = "利用";
	
	private String notInTypeName = null;
	
	private SearchClient() {
		initConfig();
	}
	
	public static SearchClient getInstance() {
		return current;
	}
	
	public void refreshCache() {
		inited = false;
	}
	
	 private synchronized void initConfig() {
	    	if(!inited) {
	    		try {
	    			releaseStatus = (CacheManagerOper.getEcmParameters().get("ReleaseStatus").getValue());
	    		}catch(Exception ex) {
	    			
	    		}
	    		
	    		try {
	    			notInTypeName = (CacheManagerOper.getEcmParameters().get("NotInTypeName").getValue());
	    		}catch(Exception ex) {
	    			
	    		}
	    	}
	 }
	 
	 

	public String getReleaseStatus() {
		return releaseStatus;
	}
	
	public String getNotInTypeName() {
		return notInTypeName;
	}


}
