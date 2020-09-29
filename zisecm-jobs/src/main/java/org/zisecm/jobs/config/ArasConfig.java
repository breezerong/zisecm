package org.zisecm.jobs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aras")
public class ArasConfig {
	private String baseUrl="http://121.40.103.187/InnovatorServer";
	private String username="admin";
	private String password="innovator";
	
	private String serverDiscoveryUrl = "/Server/OAuthServerDiscovery.aspx";
	
	private String tokenUrl = "/oauthserver/connect/token";

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerDiscoveryUrl() {
		return serverDiscoveryUrl;
	}

	public void setServerDiscoveryUrl(String serverDiscoveryUrl) {
		this.serverDiscoveryUrl = serverDiscoveryUrl;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

}
