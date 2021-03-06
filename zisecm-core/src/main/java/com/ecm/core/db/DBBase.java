package com.ecm.core.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;

/**
 * 数据库基本信息
 * @author Haihong Rong
 *
 */
@Component
public class DBBase {

	
	private static String className;
	
	private static String dbType;

	public static String DB_ORACLE = "oracle";
	public static String DB_SQLSERVER = "sqlserver";
	public static String DB_MYSQL = "mysql";
	public static String DB_POSTGRESQL = "postgresql";
	public static String DB_DM = "dm";
	
	@Value("${spring.datasource.driver-class-name}")
	public void setClassName(String name) {
		DBBase.className = name;
	}
	
	/**
	 * 获取数据库类型
	 * @return
	 */
	public static String getDbType() {
		if(dbType==null) {
			if(!StringUtils.isEmpty(className)) {
				if(className.toLowerCase().indexOf(DB_MYSQL)>-1) {
					dbType = DB_MYSQL;
				}else if(className.toLowerCase().indexOf(DB_SQLSERVER)>-1) {
					dbType = DB_SQLSERVER;
				}else if(className.toLowerCase().indexOf(DB_ORACLE)>-1) {
					dbType = DB_ORACLE;
				}else if(className.toLowerCase().indexOf(DB_POSTGRESQL)>-1) {
					dbType = DB_POSTGRESQL;
				}
				else if(className.toLowerCase().indexOf(":" + DB_DM + ":")>-1) {
					dbType = DB_DM;
				}else {
					dbType = DB_ORACLE;
				}
			}else {
				return DB_MYSQL;
			}
		}
		return dbType;
	}
	/**
	 * 是否MySql
	 * @return
	 */
	public static boolean isMySql() {
		return getDbType().equals(DB_MYSQL);
	}
	
	/**
	 * 是否SqlServer
	 * @return
	 */
	public static boolean isSqlServer() {
		return getDbType().equals(DB_SQLSERVER);
	}
	
	/**
	 * 是否Oracle
	 * @return
	 */
	public static boolean isOracle() {
		return getDbType().equals(DB_ORACLE);
	}
	/**
	 * 是否PostgreSql
	 * @return
	 */
	public static boolean isPostgreSql() {
		return getDbType().equals(DB_POSTGRESQL);
	}
	
	public static boolean isDmSql() {
		return getDbType().equals(DB_DM);
	}
}
