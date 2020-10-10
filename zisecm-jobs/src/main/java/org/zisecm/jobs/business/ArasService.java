package org.zisecm.jobs.business;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

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
	
	public void run() {
		ArasRsp arasRsp = this.getToken();
		System.out.println(JSON.toJSON(arasRsp));
	}
	
	public String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
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


	private void getObjects(ArasRsp arasRsp, String type) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = aras.getUrl() + "/server/odata/" + type;
		HttpEntity<String> response = client.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println(response.getHeaders());
		System.out.println(JSON.parseObject(response.getBody()));

	}


	private void getObjectById(ArasRsp arasRsp, String type, String id) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String url = aras.getUrl() + "/server/odata/" + type + "('" + id + "')";
		HttpEntity<String> response = client.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println(response.getHeaders());
		System.out.println(JSON.parseObject(response.getBody()));

	}


	private void getCount(ArasRsp arasRsp, String type) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String url = aras.getUrl() + "/server/odata/" + type + "/$count";
		System.out.println(url);
		HttpEntity<String> response = client.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println(response.getHeaders());
		System.out.println(response.getBody());

	}


	public void createItem(ArasRsp arasRsp, String type, Map<String, Object> body) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setBearerAuth(arasRsp.getAccess_token());
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
		String url = aras.getUrl() + "/server/odata/" + type;
		System.out.println(url);
		HttpEntity<String> response = client.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println(response.getBody());
	}
}
