package org.zisecm.jobs.business;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zisecm.jobs.config.ArasConfig;
import org.zisecm.jobs.config.ArasRsp;

@Service
public class ArasService {
	
	@Autowired
	private ArasConfig aras;
	
	public void run() {
		
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> params = new LinkedMultiValueMap();
		params.add("grant_type", "password");
		params.add("client_id", "IOMApp");
		params.add("scope", "Innovator");
		params.add("username", aras.getUsername());
		params.add("password", stringToMD5(aras.getPassword()));
		params.add("database", "OBOTService");
		
		String url = aras.getBaseUrl()+aras.getTokenUrl();
		System.out.println(url);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		ArasRsp data = client.postForObject(url, request, ArasRsp.class);
		System.out.println(data);
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
}
