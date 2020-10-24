package org.zisecm.jobs.business;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
import com.ecm.icore.service.IEcmSession;

@Service
public class ArasJobService extends BaseJob{
	
	@Autowired
	private ArasConfig aras;
	
	@Autowired
	private ArasService arasService;
	
	private List<String> getTypes(Element root){
		List<String> list = new ArrayList<>();
		List<Element> typeList = root.elements("mapper");
		for (Element element : typeList) {
			String typeName = element.attributeValue("type", "");
			if(typeName.trim().length()>0 && !list.contains(typeName)) {
				
				list.add(typeName);
			}
		}
		
		return list;
	}
	
	public void run() {
		Document docMapper= getDocument("aras-mapper.xml");
		if(docMapper!=null) {
			Element root = docMapper.getRootElement();
			
			IEcmSession session = this.login();
			String token = session.getToken();
			
			ArasRsp arasRsp = this.getToken();
			List<String> typeList = this.getTypes(root);
			for (String typeName : typeList) {
				this.runItem(token, root, typeName, arasRsp);
			}
			
		}

	}
	
	private void runItem(String token,Element root, String typeName,ArasRsp arasRsp) {
		List<Map<String,Object>> iedList = this.getData(token, "SELECT top 5 ed.*,en.id CONTENT_ID from ecm_document ed left join ecm_content en on ed.ID = en.PARENT_ID where  ed.ARAS_ID is null order by ed.CREATION_DATE desc");
		Map<String,String> mapper = getMapper(root, "CNPECommon");
		for (Map<String, Object> item : iedList) {
	
			Map<String, Object> record = new HashMap<>();
			Iterator<String> keys = mapper.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				String mapperKey = mapper.get(key).toString();
				Object value = item.get(key);
				if(value!=null) {
					if(key.toLowerCase().equals("is_current") || key.toLowerCase().equals("is_hidden")) {
						
						if(value.toString().equals("1")) {
							record.put(mapperKey, Boolean.TRUE);
						}else {
							record.put(mapperKey, Boolean.FALSE);
						}
					}
					if(!mapperKey.endsWith("date")) {							
						record.put(mapperKey, value);
					}else {
						if(value instanceof Date ) {
							Date nv = (Date) value;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							record.put(mapperKey, sdf.format(nv));
						}
					}
				}
				
			}
			System.out.println(JSON.toJSONString(record));
			/*
			String docId = item.get("ID").toString();
			String arasId = createItem(arasRsp,"IED",record);
			
		
			if(item.get("CONTENT_ID")!=null) {
				EcmContent en = this.getContentService().getObjectById(token, item.get("CONTENT_ID").toString());
				InputStream is = null;
				try {
					this.getContentService().getContentStream(token, en);
				} catch (Exception e) {
					e.printStackTrace();
				}
				uploadDataFile(arasRsp,arasId,en.getName(),is);
			}
			
			System.out.println("ArasId:"+arasId);
			this.update(token, docId, "ARAS_ID", arasId);
			*/
		}
	}
	
	private void uploadDataFile(ArasRsp arasRsp,String arasId, String fileName, InputStream fis) {
		//1.Begin the Upload Transaction
		String transactionId = this.beginUploadTransaction(arasRsp);
		System.out.println("transactionId:"+transactionId);

		//File file = new File("d:/home.png");
		String fileId =UUID.randomUUID().toString().replace("-", "");
		//2.Upload the File 
		String result = this.uploadFile(arasRsp, transactionId, fileName, fis);
		
		//System.out.println(result);
		//3.Commit the Upload Transaction
		long fileSize = getFileStream(fis).length;
		String aa = this.uploadCommit(arasRsp, transactionId, fileId, fileName, fileSize, arasId);
		//System.out.println(aa);
		
		//4.Confirming the Upload
		this.uploadConfirming(arasRsp, fileId);
	}
	
	public String uploadConfirming(ArasRsp arasRsp, String fileId) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		String url = aras.getUrl()+"/Server/odata/File('"+fileId+"')";
		HttpEntity<?> entity = new HttpEntity<String>(headers);
		HttpEntity<String> response = client.exchange(url, HttpMethod.GET, entity, String.class);
		return response.toString();
	}
	
	public String beginUploadTransaction(ArasRsp arasRsp) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", arasRsp.getToken_type()+" "+arasRsp.getAccess_token());
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
		String url = aras.getUrl()+"/Vault/odata/vault.BeginTransaction";
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		return JSON.parseObject(response.getBody()).getString("transactionId");
	}
	
	public String uploadCommit(ArasRsp arasRsp, String transactionId,String fid,String filename,long fileSize,String dataId) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String fileid =UUID.randomUUID().toString();
		headers.set("Content-Type", "multipart/mixed; boundary=batch_"+fileid);
		headers.set("transactionid", transactionId);
		headers.set("Authorization", arasRsp.getToken_type()+" "+arasRsp.getAccess_token());
		
		StringBuffer body = new StringBuffer();
		body.append("--<boundary string>\r\n");
		body.append("Content-Type: application/http\r\n");
		body.append("\r\n");
		body.append("POST "+aras.getUrl()+"/Server/odata/File\r\n");
		body.append("HTTP/1.1\r\n");
		body.append("Content-Type: application/json\r\n");
		body.append("\r\n");
		body.append("{\"id\":\""+fid+"\",\"filename\":\""+filename+"\","
				+ "\"file_size\":"+fileSize+","
				+ "\"Located\":[{\"file_version\":1,"
				+ "\"related_id\":\"67BBB9204FE84A8981ED8313049BA06C\"}]}\r\n");
		body.append("\r\n");
		body.append("--<boundary string>--\r\n");
		HttpEntity<String> entity = new HttpEntity<String>(body.toString(),headers);
		
		String url = aras.getUrl()+"/Vault/odata/vault.CommitTransaction";
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		return response.toString();
	}
	
	public String uploadFile(ArasRsp arasRsp, String transactionid, String filename,InputStream fis) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();	
		headers.set("Content-Disposition", "attachment; filename*=utf-8''"+filename);
		byte[] bytes = getFileStream(fis);
		long size = bytes.length;
		long end = size-1;
		System.out.println("bytes 0-"+end+"/"+size);
		headers.set("Content-Range", "bytes 0-"+end+"/"+size);
		headers.set("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		headers.set("transactionid", transactionid);
		headers.set("Authorization", arasRsp.getToken_type()+" "+arasRsp.getAccess_token());

		String fileid =UUID.randomUUID().toString().replace("-", "");
		String url = aras.getUrl()+"/Vault/odata/vault.UploadFile?fileId="+fileid;
		
		HttpEntity<byte[]> entity = new HttpEntity<>(getFileStream(fis),headers);
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		
		return response.toString();
	}
	
	public static byte[] getFileStream(InputStream fis) {
		byte[] buffer = null;
			
			try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}catch (IOException e) {
			e.printStackTrace();
			}
			return buffer;
	}
	
	public Map<String,String> getMapper(Element root,String type){
		Map<String,String> result = new HashMap<>();
		List<Element> list = root.elements("mapper");
		for (Element element : list) {
			if(element.attributeValue("type", "").equals(type)) {				
				List<Element> proList = element.elements("property");
				for (Element prop : proList) {
					result.put(prop.attributeValue("prop"), prop.attributeValue("column"));
				}
			}
		}
		return result;
	}
	
	public Document getDocument(String file) {
		String filepath = ArasJobService.class.getClassLoader().getResource(file).getFile();
		SAXReader saxReader = new SAXReader();
		Document doc= null;
		try {
			doc = saxReader.read(new File(filepath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
		
	private ArasRsp getToken() {
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
		System.out.println(JSON.toJSONString(data));
		return data;
	}


	public String createItem(ArasRsp arasRsp, String type, Map<String, Object> body) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
		String url = aras.getUrl() + "/server/odata/" + type;
		System.out.println(url);
		System.out.println(body);
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println(JSON.parseObject(response.getBody()).toJSONString());
		System.out.println(JSON.parseObject(response.getBody()).getString("id"));
		
		return JSON.parseObject(response.getBody()).getString("id");
	}
}
