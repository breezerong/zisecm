package org.zisecm.jobs.business;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zisecm.jobs.config.ArasConfig;
import org.zisecm.jobs.config.ArasRsp;

import com.alibaba.fastjson.JSON;




@Service
public class ArasService {

	@Autowired
	private ArasConfig aras;


	public ArasRsp getToken() {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", aras.getGrantType());
		params.add("client_id", aras.getClientId());
		params.add("scope", aras.getScope());
		params.add("username", aras.getUsername());
		params.add("password", aras.getPassword());
		params.add("database", aras.getDatabase());
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		String url = aras.getUrl() + "/OAuthServer/connect/token";
		ArasRsp data = client.postForObject(url, requestEntity, ArasRsp.class);
		return data;
	}


	public void getObjects(ArasRsp arasRsp, String type) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = aras.getUrl() + "/server/odata/" + type;
		System.out.println();
		System.out.println(url);
		HttpEntity<String> response = client.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println(response.getHeaders());
		System.out.println(JSON.parseObject(response.getBody()));

	}


	public String getObjectById(ArasRsp arasRsp, String type, String id) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String url = aras.getUrl() + "/server/odata/" + type + "('" + id + "')";
		HttpEntity<String> response = client.exchange(url, HttpMethod.GET, entity, String.class);
		
		return response.getBody();
	}

	
	public void getCount(ArasRsp arasRsp, String type) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String url = aras.getUrl() + "/server/odata/" + type + "/$count";
		HttpEntity<String> response = client.exchange(url, HttpMethod.GET, entity, String.class);
	}

	
	public String createItem(ArasRsp arasRsp, String type, Map<String, Object> body) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
		String url = aras.getUrl() + "/server/odata/" + type;
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		return JSON.parseObject(response.getBody()).getString("id");
	}

	

	
	private String beginUploadTransaction(ArasRsp arasRsp) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", arasRsp.getToken_type()+" "+arasRsp.getAccess_token());
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
		String url = aras.getUrl()+"/Vault/odata/vault.BeginTransaction";
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		return JSON.parseObject(response.getBody()).getString("transactionId");
	}

	
	private String uploadFile(ArasRsp arasRsp, String transactionid, String filename,File file,String dataId) throws UnsupportedEncodingException {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment; filename*=utf-8''"+filename);
		 long size = file.length();
		 long end = size-1;
		 System.out.println("bytes 0-"+end+"/"+size);
		headers.set("Content-Range", "bytes 0-"+end+"/"+size);
		headers.set("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		headers.set("transactionid", transactionid);
		headers.set("Authorization", arasRsp.getToken_type()+" "+arasRsp.getAccess_token());

		
		String url = aras.getUrl()+"/Vault/odata/vault.UploadFile?fileId="+dataId;
		
		HttpEntity<byte[]> entity = new HttpEntity<>(getFileStream(file),headers);
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		
		return response.toString();
	}
	
	
	private String uploadCommit(ArasRsp arasRsp, String transactionId,String fid,String filename,long fileSize,String dataId) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String fileid =UUID.randomUUID().toString();
		String boundaryString = "batch_"+fileid;
		headers.set("Content-Type", "multipart/mixed; boundary="+boundaryString);
		headers.set("transactionid", transactionId);
		headers.set("Authorization", arasRsp.getToken_type()+" "+arasRsp.getAccess_token());
		
		StringBuffer body = new StringBuffer();
		body.append("--"+boundaryString+"\r\n");
		body.append("Content-Type: application/http\r\n");
		body.append("\r\n");
		body.append("POST "+aras.getUrl()+"/Server/odata/File HTTP/1.1\r\n");
		body.append("Content-Type: application/json\r\n");
		body.append("\r\n");
		body.append("{\"id\":\""+fid+"\",\"filename\":\""+filename+"\","
				+ "\"file_size\":"+fileSize+","
				+ "\"Located\":[{\"file_version\":1,"
				+ "\"related_id\":\""+aras.getVaultId()+"\"}]}\r\n");
		body.append("--"+boundaryString+"--");
		HttpEntity<String> entity = new HttpEntity<String>(body.toString(),headers);
		
		String url = aras.getUrl()+"/Vault/odata/vault.CommitTransaction";
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		return response.toString();
	}
	
	
	public HashMap uploadConfirming(ArasRsp arasRsp, String fileId) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", arasRsp.getToken_type()+" "+arasRsp.getAccess_token());
		String url = aras.getUrl()+"/Server/odata/File('"+fileId+"')";
		HttpEntity<?> entity = new HttpEntity<String>(headers);
		HttpEntity<HashMap> response = client.exchange(url, HttpMethod.GET, entity, HashMap.class);
		return response.getBody();
	}
	public static byte[] getFileStream(File file) {
		byte[] buffer = null;
			FileInputStream fis;
			try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
			/*
			 * if (file.exists()) { file.delete(); }
			 */
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}catch (IOException e) {
			e.printStackTrace();
			}
			return buffer;
	}

	
	
	public String createFile(ArasRsp arasRsp,File file) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		headers.setBearerAuth(arasRsp.getAccess_token());
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("comments", file.getName());
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
		String url = aras.getUrl() + "/server/odata/File";
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		return response.getBody();
		
	}

	
	private String createRelation(ArasRsp arasRsp, String itemType, String parentId, String childItemType,String childId, String relationName) {

		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.setBearerAuth(arasRsp.getAccess_token());
		StringBuffer body = new StringBuffer();
		body.append("{ \"related_id@odata.bind\": \""+childItemType+"('"+childId+"')\" }");
		HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
		String url = aras.getUrl() + "/server/odata/"+itemType+"('"+parentId+"')/"+relationName;
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		return response.getBody();

	}

	
	public String uploadFileRelationData(ArasRsp arasRsp, File file,String itemTypeName, String dataId, String relationName) {
		String fileId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		String transactionId = this.beginUploadTransaction(arasRsp);
		try {
			this.uploadFile(arasRsp, transactionId, file.getName(), file,fileId);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.uploadCommit(arasRsp, transactionId, fileId, file.getName(), file.length(), fileId);		
		String result2=this.createRelation(arasRsp, itemTypeName,  dataId, "file", fileId, relationName);
		return result2;
	}

	


	

}
