package org.zisecm.jobs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class HttpTools {
	private static Logger logger=Logger.getLogger(HttpTools.class);
	public static void downloadFile(String token,String localDir,String id,String ext,String baseUrl) {
		HttpGet httpGet = null;
	    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	    	// 这里我设置了超时时间的配置，也可以不设
	    	RequestConfig timeoutConfig = RequestConfig.custom()
	                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
	                    .setSocketTimeout(5000).build();
			// download_url为下载文件接口地址，fileId是我自己文件接口定义的文件标识
	        // 本例的文件下载接口是直接返回的文件流
	    	String downloadUrl=baseUrl+"/dc/getContent?id="+id+"&token="+token+"&action=download";
	        httpGet = new HttpGet(downloadUrl);
	        httpGet.setConfig(timeoutConfig);

	        HttpResponse downLoadResponse = httpClient.execute(httpGet);
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
	        }
	    } catch (Exception e) {
	    	logger.error("", e);
	    } finally {
	        if (httpGet != null) {
	            httpGet.releaseConnection();
	        }
	    }

	}
}
