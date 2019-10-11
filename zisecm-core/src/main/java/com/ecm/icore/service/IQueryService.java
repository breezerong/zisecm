package com.ecm.icore.service;

import java.util.List;
import java.util.Map;
/**
 * 查询服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:58:39
 */
public interface IQueryService {
	/**
	 * 执行SQL查询
	 * @param sql SQL语句
	 * @return
	 */
	List<Map<String, Object>> executeSQL(String token, String sql);
}
