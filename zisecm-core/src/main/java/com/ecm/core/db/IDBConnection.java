package com.ecm.core.db;
/**
 * 获取数据库连接
 * @author Haihong Rong
 * Date:2020年2月27日 上午10:55:07
 */
public interface IDBConnection {
	IDBUtils getDBUtils();
	IDBDocManager getDBDocManager();
	IDBSequence getDBSequece();
}
