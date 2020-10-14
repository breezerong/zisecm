package org.zisecm.jobs.business;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class ArasService extends BaseJob{
	
	@Autowired
	private ArasConfig aras;
	
	public void run() {
		Document docMapper= getDocument("aras-mapper.xml");
		if(docMapper!=null) {
			Element root = docMapper.getRootElement();
			
			IEcmSession session = this.login();
			String token = session.getToken();
			
			ArasRsp arasRsp = this.getToken();
			
			List<Map<String,Object>> iedList = this.getData(token, "SELECT * from ecm_document where TYPE_NAME='IED' and ARAS_ID is null ");
			Map<String,String> mapper = getMapper(root, "IED");
			for (Map<String, Object> item : iedList) {
				Map<String, Object> record = new HashMap<>();
				Iterator<String> keys = mapper.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					String mapperKey = mapper.get(key).toString();
					Object value = item.get(key);
					if(value!=null) {
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
				
				String docId = item.get("ID").toString();
				String arasId = createItem(arasRsp,"IED",record);
				System.out.println("ArasId:"+arasId);
				this.update(token, docId, "ARAS_ID", arasId);
			}
		}

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
		String filepath = ArasService.class.getClassLoader().getResource(file).getFile();
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
