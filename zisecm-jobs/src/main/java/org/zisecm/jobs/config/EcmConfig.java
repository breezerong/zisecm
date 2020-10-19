package org.zisecm.jobs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ecm")
public class EcmConfig {
	private Database database;
	private String username = "admin";
	private String password = "Ecm.520!";
	private String localDir = "C:/download/";
	private String baseUrl = "http://localhost:8080/zisecm";
	private String tcUsername="sjxx";
	private String tcPassword = "infodba";
	private int dateDifference=-400;
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
	public String getLocalDir() {
		return localDir;
	}
	public void setLocalDir(String localDir) {
		this.localDir = localDir;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getTcUsername() {
		return tcUsername;
	}
	public void setTcUsername(String tcUsername) {
		this.tcUsername = tcUsername;
	}
	public String getTcPassword() {
		return tcPassword;
	}
	public void setTcPassword(String tcPassword) {
		this.tcPassword = tcPassword;
	}
	public int getDateDifference() {
		return dateDifference;
	}
	public void setDateDifference(int dateDifference) {
		this.dateDifference = dateDifference;
	}
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	
	class Database{
		private DataBaseType type = DataBaseType.mysql;

		public DataBaseType getType() {
			return type;
		}

		public void setType(DataBaseType type) {
			this.type = type;
		}
		
	}
	enum DataBaseType {
		mysql, oracle, sqlserver, postgresql
	}
}
