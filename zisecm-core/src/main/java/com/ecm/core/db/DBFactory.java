package com.ecm.core.db;

import org.springframework.stereotype.Component;

import com.ecm.core.db.dm.DmSqlConnection;
import com.ecm.core.db.mysql.MySqlConnection;
import com.ecm.core.db.oracle.OracleConnection;
import com.ecm.core.db.postgresql.PostgreSqlConnection;
import com.ecm.core.db.sqlserver.SqlServerConnection;

/**
 * 数据库工厂
 * @author Haihong Rong
 * Date:2020年2月27日 上午11:10:47
 */
@Component
public class DBFactory {
	private static IDBConnection conn;
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static IDBConnection getDBConn() {
		if(conn==null) {
			initConn();
		}
		return conn;
	}
	
	private static synchronized void initConn()  {
		if(DBBase.isMySql()) {
			conn = new MySqlConnection();
		}else if(DBBase.isSqlServer()) {
			conn = new SqlServerConnection();
		}else if(DBBase.isOracle()) {
			conn = new OracleConnection();
		}else if(DBBase.isPostgreSql()) {
			conn = new PostgreSqlConnection();
		}else if(DBBase.isDmSql()) {
			conn = new DmSqlConnection();
		}
	}
}
