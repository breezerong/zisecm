package com.ecm.core.search;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.context.annotation.Bean;

import com.ecm.core.cache.manager.CacheManagerOper;

public class ESClient {
	
	private static boolean inited = false;
	private static String httpPolicy = "http";
	private static String[] indexServer = {"127.0.0.1"};
	private static int[] indexPort = {9200};
	private static String packageName = "zisecm_index_db";
	private static long maxSize = 50485760;
	private static String[] includeFields = "name;title;folder_id;status;creation_date;creator;type_name;modifier;modified_date;revision;coding;acl_name;format_name;content_size;attachment_count;sub_type;c_from;c_to;is_current;is_hidden;c_security_level;c_reviewer1;c_reviewer2;c_reviewer3;c_reviewer4;c_reviewer5;c_reviewer6;c_approver;c_comment;system_version;version_id;owner_name;lock_owner;lock_date;lock_client;c_page_count;c_page_index;c_order_index;c_retention;format_name".split(";");
	private static String[] facetFields = "type_name;format_name;sub_type;creator".split(";");
	private static String[] searchFields = "name;coding;title;sub_type;creator".split(";");
	private static int heightTextLen = 300;
	private static int suggestionCount = 5;
	private static String esUserName = null;
	private static String esPassword = null;
	
	public int getHeightTextLen() {
		return heightTextLen;
	}

	private static ESClient current = new ESClient();
	
	private ESClient() {
		initConfig();
	}
	
	public static ESClient getInstance() {
		return current;
	}
	/**
	 * 多服务器使用分号分割
	 * @return
	 */
    @Bean
    public RestHighLevelClient getClient() {
    	RestHighLevelClient client = null;
    	HttpHost[] hh = new HttpHost[indexServer.length];
    	for(int i=0;i<indexServer.length;i++) {
    		try {
    			hh[i] = new HttpHost(indexServer[i], indexPort[i], httpPolicy);
    		}catch (Exception e) {
				// TODO: handle exception
    			hh[i] = new HttpHost(indexServer[i], indexPort[0], httpPolicy);
			}
    		
    	}
    	if(esUserName != null) {
	    	/** 用户认证对象 */
	        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	        /** 设置账号密码 */
	        credentialsProvider.setCredentials(AuthScope.ANY,
	            new UsernamePasswordCredentials(esUserName, esPassword));
	        /** 创建rest client对象 */
	        RestClientBuilder builder = RestClient.builder(hh)
            .setHttpClientConfigCallback(new HttpClientConfigCallback() {
              @Override
              public HttpAsyncClientBuilder customizeHttpClient(
                  HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
              }
            });
        	client = new RestHighLevelClient(builder);
    	}else {
    		client = new RestHighLevelClient(RestClient.builder(hh));
    	}
        return client;
    }
    
    public boolean indexExist(RestHighLevelClient client, String index) throws Exception {
        GetIndexRequest request = new GetIndexRequest(index);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }
    public void refreshCache() {
    	inited = false;
    	initConfig();
    }
    private synchronized static void initConfig() {
    	if(!inited) {
    		try {
    			httpPolicy = CacheManagerOper.getEcmParameters().get("IndexHttpPolicy").getValue();
    		}catch(Exception ex) {
    			
    		}
    		try {
    			indexServer = CacheManagerOper.getEcmParameters().get("IndexServer").getValue().split(";");
    		}catch(Exception ex) {
    			
    		}
    		try {
    			String[] ports = CacheManagerOper.getEcmParameters().get("IndexPort").getValue().split(";");
    			indexPort = new int[ports.length];
    			for(int i=0;i<ports.length;i++) {
    				indexPort[i] = Integer.parseInt(ports[i]);
    			}
    		}catch(Exception ex) {
    			
    		}
    		try {
    			maxSize = Integer.parseInt(CacheManagerOper.getEcmParameters().get("MaxIndexSize").getValue());
    		}catch(Exception ex) {
    			
    		}
    		try {
    			heightTextLen = Integer.parseInt(CacheManagerOper.getEcmParameters().get("HeightTextLen").getValue());
    		}catch(Exception ex) {
    			
    		}
    		try {
    			suggestionCount =Integer.parseInt(CacheManagerOper.getEcmParameters().get("SuggestionCount").getValue());
    		}catch(Exception ex) {
    			
    		}
    		try {
    			packageName = CacheManagerOper.getEcmParameters().get("IndexPackageName").getValue();
    		}catch(Exception ex) {
    			
    		}	
    		try {
    			includeFields = CacheManagerOper.getEcmParameters().get("IndexFields").getValue().split(";");
    		}catch(Exception ex) {
    			
    		}
    		try {
    			facetFields = CacheManagerOper.getEcmParameters().get("FacetFields").getValue().split(";");
    		}catch(Exception ex) {
    			
    		}
    		try {
    			searchFields = CacheManagerOper.getEcmParameters().get("SearchFields").getValue().split(";");
    		}catch(Exception ex) {
    			
    		}
    		try {
    			esUserName = CacheManagerOper.getEcmParameters().get("ESUserName").getValue();
    		}catch(Exception ex) {
    			
    		}
    		try {
    			esPassword = CacheManagerOper.getEcmParameters().get("ESPassword").getValue();
    		}catch(Exception ex) {
    			
    		}
    		inited = true;
    	}
    }

	public  String getPackageName() {
		return packageName;
	}
	public  long getMaxSize() {
		return maxSize;
	}
	
	public  boolean isIndexField(String attrName) {
		for(String str : includeFields) {
			if(str.equalsIgnoreCase(attrName)) {
				return true;
			}
		}
		return false;
	}
	public  String[] getIncludeFields() {
		return includeFields;
	}

	public  String[] getFacetFields() {
		return facetFields;
	}

	public  boolean isfacetField(String attrName) {
		for(String str : facetFields) {
			if(str.equalsIgnoreCase(attrName)) {
				return true;
			}
		}
		return false;
	}

	public  int getSuggestionCount() {
		return suggestionCount;
	}

	public String[] getSearchFields() {
		return searchFields;
	}

}
