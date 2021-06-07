package com.ecm.core.sync;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.SyncUserIdBean;
import com.ecm.core.entity.SyncUserIpBean;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SyncPublicNetUtil implements ISyncPublicNet{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	@Autowired
	private Environment env;
	
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ContentService contentServices;
	
//	@Override
//	public boolean exportData(String type) throws IOException, Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean importData(String actionName) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	public static int getSecurityLevelByUserIdUrl(String userId) {
		int secretlevel = 0;  
		 String url ="";
		 if(!StringUtils.isEmpty(userId)) {
			 try {
				 //CD_SECURITY_LEVEL_BYUSER_URL = http://139.10.18.121:6666/Enterprise.asmx/GetAllUserSecretLevel?ip=
				 if(CacheManagerOper.getEcmParameters().get("CD_SECURITY_LEVEL_BYUSER_URL")!= null) {
					 url = CacheManagerOper.getEcmParameters().get("CD_SECURITY_LEVEL_BYUSER_URL").getValue()+userId;
					 CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
					 HttpGet httpGet=new HttpGet(url);
				
					 CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
					 String result = EntityUtils.toString(response.getEntity());
					 System.out.println(result);
					 JSONObject jsonObject=new JSONObject();
					 List<SyncUserIdBean> userIdList =jsonObject.parseArray(result, SyncUserIdBean.class);
					 if(userIdList!=null && userIdList.size()>0) {
						 SyncUserIdBean idBean  = userIdList.get(0);
						 secretlevel =  Integer.parseInt(idBean.getSecretlevel()) ;
					 }
				 }
			 } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		return secretlevel;   
	}
	  
	public static int  getSecurityLevelByIpUrl(String userIp) {
		int secretlevel = 0;  
		 String url ="";
		 if(!StringUtils.isEmpty(userIp)) {
			 try {
				 //CD_SECURITY_LEVEL_BYIP_URL = http://139.10.18.121:6666/Enterprise.asmx/GetAllComputerSecretLevel?ip=
				 if(CacheManagerOper.getEcmParameters().get("CD_SECURITY_LEVEL_BYIP_URL")!= null) {
					 url = CacheManagerOper.getEcmParameters().get("CD_SECURITY_LEVEL_BYIP_URL").getValue()+userIp;
					 CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
					 HttpGet httpGet=new HttpGet(url);
				
					 CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
					 String result = EntityUtils.toString(response.getEntity());
					 System.out.println(result);
					 JSONObject jsonObject=new JSONObject();
					 SyncUserIpBean userIpList =JSONObject.parseObject(result, SyncUserIpBean.class);
					 if(userIpList!=null) {
						 secretlevel =  Integer.parseInt(userIpList.getSecretlevel());
					 }
//					 List<SyncUserIpBean> userIpList =jsonObject.parseArray(result, SyncUserIpBean.class);
//					 if(userIpList!=null && userIpList.size()>0) {
//						 SyncUserIpBean userIpBean  = userIpList.get(0);
//						 secretlevel =  Integer.parseInt(userIpBean.getSecretlevel()) ;
//					 }
				 }
				
				 
			 } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		return secretlevel;  
	}
	  
	public  int getSecurityLevelByUserJson(String token,String userId) {
		int secretlevel = 0;  
		//查询是否存在相同coding的文件
		String condition = "TYPE_NAME = 'allUserID' ";
	    List<EcmDocument> res;
		try {
			res = documentService.getObjects(token, condition);
			EcmDocument doc = null;
		    if(res != null && res.size() > 0) {
		    	doc = res.get(0);
		    }else {
		    	doc = new EcmDocument();
		    }
		    EcmContent en= (EcmContent) contentServices.getContents(token, doc.getId(), 1);
			if(en!=null) {
				InputStream iStream = contentServices.getContentStream(token, en);
				List<SyncUserIdBean> objList = JSON.parseArray(readJsonFile(iStream), SyncUserIdBean.class);
				for (int i = 0; i < objList.size(); i++) {
					SyncUserIdBean userIdBean =  objList.get(i)  ;
					if(userIdBean.getUserid().equals(userId)) {
						secretlevel = Integer.parseInt(userIdBean.getSecretlevel());
						return secretlevel;
					}
				}
			}
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SqlDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return secretlevel;
		  
	}
	public  int getSecurityLevelByIpJson(String token,String userIp) {
		int secretlevel = 0;  
		//查询是否存在相同coding的文件
		String condition = "TYPE_NAME = 'allUserIP' ";
	    List<EcmDocument> res;
		try {
			res = documentService.getObjects( token, condition);
			EcmDocument doc = null;
		    if(res != null && res.size() > 0) {
		    	doc = res.get(0);
		    }else {
		    	doc = new EcmDocument();
		    }
		    EcmContent en= (EcmContent) contentServices.getContents(token, doc.getId(), 1);
			if(en!=null) {
				InputStream iStream = contentServices.getContentStream(token, en);
				List<SyncUserIpBean> objList = JSON.parseArray(readJsonFile(iStream), SyncUserIpBean.class);
				for (int i = 0; i < objList.size(); i++) {
					SyncUserIpBean userIpBean =  objList.get(i)  ;
					if(userIpBean.getIp().equals(userIp)) {
						secretlevel = Integer.parseInt(userIpBean.getSecretlevel());
						return secretlevel;
					}
				}
			}
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SqlDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return secretlevel;
	}

	private List<SyncUserIpBean> readJsonResult(String fileName) {
		List<SyncUserIpBean> objList = JSON.parseArray(readJsonFile(fileName), SyncUserIpBean.class);
		return objList;
	}
	
	private String readJsonFile(InputStream in) {
		String jsonString = "";
		try {
			jsonString = IOUtils.toString(in, Charsets.toCharset(DEFAULT_CHARSET));
		} catch (Exception e) {
			logger.error("读取文件失败{}", e);
		}
		return jsonString;
	}
	
	
	private String readJsonFile(String filePath) {
		Path ConfPath = Paths.get("", filePath);
		String jsonString = "";
		try (InputStream in = FileUtils.openInputStream(ConfPath.toFile())) {
			jsonString = IOUtils.toString(in, Charsets.toCharset(DEFAULT_CHARSET));
		} catch (Exception e) {
			logger.error("读取文件失败{}", ConfPath.toAbsolutePath(), e);
		}
		return jsonString;
	}
	
	private  String getSyncPathUser() {
		String appPath = CacheManagerOper.getEcmParameters().get("syscjob.import.user.secretlevel").getValue();
		return appPath ;
	}

	 public static void main(String[] args) {
		 SyncPublicNetUtil sync = new SyncPublicNetUtil();
//		 ArrayList<SyncUserBean> list = new  ArrayList<SyncUserBean>() ;
//	
//		 for (int i = 0; i < 3; i++) {
//			 SyncUserBean synObj = new SyncUserBean();
//			 synObj.setKeyid(null);
//			 synObj.setIp("139.8.10."+i);
//			 synObj.setSecretlevel(String.valueOf(i));
//			 list.add(synObj);
//		}
//	  
//	    String fullPath = "D:\\work\\getip1.json";
//	    try {
//	    	sync.writeJsonFile(list,fullPath);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 List<SyncUserBean> objList = JSON.parseArray(sync.readJsonFile("D:\\work\\getip.json"), SyncUserBean.class);
//		 System.out.println();
		 
		 
	
		 
		 
			 JSONObject jsonObject=new JSONObject();
			 
			// String str = "[{\"keyid\":null,\"ip\":\"139.8.10.1\",\"secretlevel\":\"1\"},{\"keyid\":null,\"ip\":\"139.8.10.2\",\"secretlevel\":\"2\"}]";
			 String str = "[{\"code\":\"1\",\"secretlevel\":\"1\",\"message\":\"获取成功\"}]";
				
			 List<SyncUserIpBean> objList =jsonObject.parseArray(str, SyncUserIpBean.class);
			 String a= objList.get(0).getSecretlevel();
			 System.out.println(a);
	}
	 
	
	 
}
