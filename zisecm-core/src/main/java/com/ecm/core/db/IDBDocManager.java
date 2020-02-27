package com.ecm.core.db;

import java.util.List;

import com.ecm.core.entity.EcmAttribute;

/**
 * Document数据表管理
 * @author Haihong Rong
 * Date:2020年2月27日 上午10:55:35
 */
public interface IDBDocManager {
	/**
	 * 获取文档属性
	 * @return
	 */
	String getDocAttributesSql();
	/**
	 * 新建文档属性
	 * @param en
	 * @return
	 */
	String newDocAttributeSql(EcmAttribute en);
	/**
	 * 更新文档属性
	 * @param en
	 * @return
	 */
	String updateDocAttributeSql(EcmAttribute en);
	/**
	 * 删除文档属性
	 * @param en
	 * @return
	 */
	String deleteDocAttributeSql(EcmAttribute en);
}
