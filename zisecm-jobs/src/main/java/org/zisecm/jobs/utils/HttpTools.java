package org.zisecm.jobs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSONObject;

public class HttpTools {
	private static Logger logger=Logger.getLogger(HttpTools.class);
	public static void downloadFile(Environment env,String localDir,String id,String ext,String baseUrl) {
		HttpPost httpPost = null;
	    try {
	    	CloseableHttpClient httpClient = HttpClients.createDefault();
	    	// 这里我设置了超时时间的配置，也可以不设
	    	RequestConfig timeoutConfig = RequestConfig.custom()
	                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
	                    .setSocketTimeout(5000).build();
	    	String token=login(env.getProperty("ecm.username"), env.getProperty("ecm.password"),
	    			baseUrl+"/userLogin", httpClient);
			// download_url为下载文件接口地址，fileId是我自己文件接口定义的文件标识
	        // 本例的文件下载接口是直接返回的文件流
	    	String downloadUrl=baseUrl+"/dc/getContent?id="+id+"&token="+token+"&action=download&format="+ext;
	    	httpPost = new HttpPost(downloadUrl);
	    	httpPost.setConfig(timeoutConfig);
	    	httpPost.addHeader("token",token);
	    	
	        HttpResponse downLoadResponse = httpClient.execute(httpPost);
	        StatusLine statusLine = downLoadResponse.getStatusLine();
	        // 响应码
	        int statusCode = statusLine.getStatusCode();

	        // 请求成功
	        if (statusCode == 200) {
	        	// 获取接口返回的文件流
	            HttpEntity entity = downLoadResponse.getEntity();
	            InputStream input = entity.getContent();
	            // 本例是储存到本地文件系统，fileRealName为你想存的文件名称
	            
	            File dest = new File(localDir+"/"+id+"."+ext);
	            OutputStream output = new FileOutputStream(dest);
	            int len = 0;
	            byte[] ch = new byte[1024];
	            while ((len = input.read(ch)) != -1) {
	                output.write(ch, 0, len);
	            }
	            output.flush();
	            if(output!=null) {
	            	output.close();
	            }
	        }
	    } catch (Exception e) {
	    	logger.error("", e);
	    } finally {
	        if (httpPost != null) {
	        	httpPost.releaseConnection();
	        }
	    }

	}
	
	public static String login(String userName,String password,String baseUrl,CloseableHttpClient httpClient) throws Exception {
		HttpPost httpPost=null;

	    try {
	    	
			// download_url为下载文件接口地址，fileId是我自己文件接口定义的文件标识
	        // 本例的文件下载接口是直接返回的文件流
	    	httpPost = new HttpPost(baseUrl);
	    	RequestConfig timeoutConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
	    	httpPost.setConfig(timeoutConfig);
	    	String json="{\"username\":\""+userName+"\",\"password\":\""+password+"\"}";
	    	httpPost.setHeader("Content-Type","application/json;charset=UTF-8");
	    	StringEntity strEntity=new StringEntity(json);
	    	strEntity.setContentType("text/json");
	    	httpPost.setEntity(strEntity);
	    	
	        HttpResponse downLoadResponse = httpClient.execute(httpPost);
	        StatusLine statusLine = downLoadResponse.getStatusLine();
	        // 响应码
	        int statusCode = statusLine.getStatusCode();

	        // 请求成功
	        if (statusCode == 200) {
	        	// 获取接口返回的文件流
	            HttpEntity responseEntity = downLoadResponse.getEntity();
	            String reponseEntityStr=EntityUtils.toString(responseEntity,"UTF-8");
	            Map<String,Object> obj=JSONObject.parseObject(reponseEntityStr);
	            Object tokenObj=obj.get("token");
	            if(tokenObj!=null) {
	            	return tokenObj.toString();
	            }
	        }
	    } catch (Exception e) {
	    	logger.error("", e);
	    } finally {
	        if (httpPost != null) {
	        	httpPost.releaseConnection();
	        }
	    }
	    throw new Exception("认证失败");
	
	}
	
}
