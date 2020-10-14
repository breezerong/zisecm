package org.zisecm.jobs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSONObject;

public class HttpTools {
	private static Logger logger=Logger.getLogger(HttpTools.class);
	public static void uploadFile(Environment env,String id,String fileName,
			String baseUrl,InputStream stream) throws Exception {
		HttpPost httpPost=null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			String token=login(env.getProperty("ecm.username"), env.getProperty("ecm.password"),
					baseUrl+"/userLogin", httpClient);
			String uploadUrl=baseUrl+"/dc/mountFile";
			httpPost=new HttpPost(uploadUrl);
			 // 使用表单形式提交参数
	         MultipartEntityBuilder fileBuilder = MultipartEntityBuilder.create();
	         fileBuilder.setMode(HttpMultipartMode.RFC6532);
	         StringBody postId = new StringBody(id, ContentType.create(
	                 "text/plain", Consts.UTF_8));

	         // file为提交参数名,stream为要上传的文件的文件流 inputStream，最后一个参数为上传文件名称
	         fileBuilder.addBinaryBody("file", stream, ContentType.create("multipart/form-data"), fileName);
	         fileBuilder.addPart("id", postId);
	         HttpEntity fileEntity = fileBuilder.build();
	         httpPost.addHeader("token", token);
	         httpPost.setEntity(fileEntity);

	         HttpResponse uploadResponse = httpClient.execute(httpPost);
			 StatusLine statusLine = uploadResponse.getStatusLine();
	         // 响应码
	         int statusCode = statusLine.getStatusCode();
	         // 请求成功
	         if (statusCode == 200) {
	        	 
	            HttpEntity entity = uploadResponse.getEntity();
	            logger.info("返回结果为 " + EntityUtils.toString(entity));
	         }
		}finally {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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
