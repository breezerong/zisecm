package org.zisecm.jobs.tc.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DatabaseConnection {

	protected static Logger logger = Logger.getLogger(DatabaseConnection.class);
	
	Connection connection = null;


	public DatabaseConnection() throws Exception {
		InputStream in = null;
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		in = classLoader.getResourceAsStream("db.properties");
		Properties p = new Properties();
		p.load(in);
		in.close();

		String host = p.getProperty("DB_SERVER_IP");
		String instance = p.getProperty("DB_INSTANCE");
		String user = p.getProperty("DB_USER_NAME");
		String pwd = p.getProperty("DB_PASSWORD");
		String port = p.getProperty("DB_PORT");

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String connectionURL = "java:oracle:thin:@" + host + ":" + port + ":"
				+ instance;
		connection = DriverManager.getConnection(connectionURL, user, pwd);
		
		//connection.setAutoCommit(false);

		System.out.println("db connection created");
		logger.info("db connection created");
	}
	

/**
 * 写入ws调用记录到中间数据库
 * @param session_id
 * @param operation_name
 * @param request_ws
 * @param remote_address
 * @throws Exception
 */
	public void insertWSHistory(String session_id,String operation_name,String request_ws,String remote_address,String log_file) throws Exception{
		
		Statement statement = connection.createStatement();
		
		String sqlString = "insert into ws_history(session_id,operation_name,request_ws,remote_address,log_file,request_date) values(";
		
		sqlString += "'" +session_id+"',";
		sqlString += "'" +operation_name+"',";
		sqlString += "'" +request_ws+"',";
		sqlString += "'" +remote_address+"',";
		sqlString += "'" +log_file+"',";
		sqlString += "sysdate)";
		
		//System.out.println(sqlString);
		//logger.info(sqlString);
		
		statement.execute(sqlString);
		statement.close();
	}
//add end
	


	public void excuteSql(String sqlStr) throws Exception {

		Statement statement = connection.createStatement();
		statement.execute(sqlStr);
		statement.close();
	}

	// add by xiaolei 20111203
	public String buildInsertSQLString(String tableName, String[] propNames,
			String[] values) throws Exception {

		// 开始构建sql字符串
		String sqlString = "Insert Into " + tableName + " (";

		for (int i = 0; i < propNames.length; i++) {
			sqlString = sqlString + propNames[i];

			if (i < propNames.length - 1) {
				sqlString = sqlString + ",";
			}
		}

		sqlString = sqlString + ") Values (";

		for (int i = 0; i < values.length; i++) {
			sqlString = sqlString + values[i];

			if (i < values.length - 1) {
				sqlString = sqlString + ",";
			}
		}

		sqlString = sqlString + ")";

		return sqlString;
	}

	public void rollBack() {
		if (connection != null) {
			try {
				connection.rollback();

				System.out.println("db connection rollback");
				logger.info("db connection rollback");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("数据库回滚失败！错误信息：" + e.getMessage());
				// MessageBox.post("数据库回滚失败！错误信息：" + e.getMessage(), "错误",
				// MessageBox.ERROR);
			}
		}

	}

	public void commit() {
		if (connection != null) {
			try {
				connection.commit();

				System.out.println("db connection commit");
				logger.info("db connection commit.");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("数据库commit失败！错误信息：" + e.getMessage());
				// MessageBox.post("数据库commit失败！错误信息：" + e.getMessage(), "错误",
				// MessageBox.ERROR);
			}
		}

	}

	/**
	 * 关闭数据连接
	 * 
	 * @param void
	 * @return void
	 */
	public void closeConnection() {

		if (connection != null) {
			try {
				connection.close();

				System.out.println("db connection closed");
				logger.info("db connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("数据库关闭失败！错误信息：" + e.getMessage());
				// MessageBox.post("数据库关闭失败！错误信息：" + e.getMessage(), "错误",
				// MessageBox.ERROR);
			}
		}
	}

}
