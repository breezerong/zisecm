package com.ecm.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.ecm.core.entity.Pager;

@Component
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class,Integer.class }) })
@SuppressWarnings("rawtypes")
public class PageInterceptor implements Interceptor {
 
	private static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);
	//private static String databaseType ="mysql";// 数据库类型，不同的数据库有不同的分页方法
	/**
	 * 拦截后要执行的方法
	 */
	public Object intercept(Invocation invocation) throws Throwable {
 
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation
				.getTarget();
		StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
		BoundSql boundSql = delegate.getBoundSql();
		Object params = boundSql.getParameterObject();
		Pager page = null;
		if (params instanceof Pager) {
			page = (Pager) params;
		} else if (params instanceof MapperMethod.ParamMap) {
			MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) params;
			for (Object key : paramMap.keySet()) {
				if (paramMap.get(key) instanceof Pager) {
					page = (Pager) paramMap.get(key);
					break;
				}
			}
		}
 
		if (page != null) {
			MappedStatement mappedStatement = (MappedStatement) ReflectUtil
					.getFieldValue(delegate, "mappedStatement");
			Connection connection = (Connection) invocation.getArgs()[0];
			String sql = boundSql.getSql();
			logger.info("intercept sql:"+sql);
			this.setTotalRecord(page, (MapperMethod.ParamMap) params,
					mappedStatement, connection);
			String pageSql = this.getPageSql(page, sql);
			logger.info("intercept pageSql:"+pageSql);
			ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
		}
		return invocation.proceed();
	}
 
	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
 
	
	private String getPageSql(Pager page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if (DBBase.isMySql()) {
			return getMysqlPageSql(page, sqlBuffer);
		} else if (DBBase.isOracle()) {
			return getOraclePageSql(page, sqlBuffer);
		} else if (DBBase.isSqlServer()) {
			return getSqlserverPageSql(page, sqlBuffer);
		} else if(DBBase.isDmSql()) {
			return getDmPageSql(page, sqlBuffer);
		}
		return sqlBuffer.toString();
	}
 
	private String getSqlserverPageSql(Pager page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Sqlserver中记录的位置是从0开始的。
		int startRowNum = page.getPageIndex() * page.getPageSize() + 1;
		int endRowNum = startRowNum + page.getPageSize();
		String sql = sqlBuffer.toString().toUpperCase();
		if(sql.indexOf(" ORDER ")>0) {
			String[] strs = sql.split(" ORDER ");
			//去掉排序里面的表别名
			String orderBy = strs[1].replace("A.", "").replace("B.", "").replace("C.", "").replace("D.", "");
			sql = "select appendRowNum.row,* from (select ROW_NUMBER() OVER (order "+orderBy+") AS row,* from ("
					+ strs[0]
					+ ") as innerTable"
					+ ")as appendRowNum where appendRowNum.row >= "
					+ startRowNum
					+ " AND appendRowNum.row < " + endRowNum;
		}else
		{
			sql = "select appendRowNum.row,* from (select ROW_NUMBER() OVER (order by (select 0)) AS row,* from ("
					+ sqlBuffer.toString()
					+ ") as innerTable"
					+ ")as appendRowNum where appendRowNum.row >= "
					+ startRowNum
					+ " AND appendRowNum.row < " + endRowNum;
		}
		return sql;
	}
 
	private String getMysqlPageSql(Pager page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
//		int offset = page.getPageIndex();
		int offset = page.getPageIndex() * page.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return sqlBuffer.toString();
	}
 
	private String getOraclePageSql(Pager page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = page.getPageIndex() * page.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
			.append(offset + page.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		return sqlBuffer.toString();
	}
	
	private String getDmPageSql(Pager page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = page.getPageIndex() * page.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
			.append(offset + page.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		return sqlBuffer.toString();
	}
 
	/**
	 * 给当前的参数对象page设置总记录数
	 * 
	 * @param page
	 *            Mapper映射语句对应的参数对象
	 * @param mappedStatement
	 *            Mapper映射语句
	 * @param connection
	 */
	private void setTotalRecord(Pager page, MapperMethod.ParamMap params,
			MappedStatement mappedStatement, Connection connection) {
		BoundSql boundSql = mappedStatement.getBoundSql(params);
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,parameterMappings, params);
		ParameterHandler parameterHandler = new DefaultParameterHandler(
				mappedStatement, params, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				page.setTotalRecord(totalRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)rs.close();
				if (pstmt != null)pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
 
	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 * 
	 * @param sql
	 * @return
	 */
	private String getCountSql(String sql) {
		if(sql.toLowerCase().indexOf(" order ")>0) {
			String[] strs =null;
			if(sql.indexOf(" order ")>0) {
				strs = sql.toLowerCase().split(" order ");
			}else if(sql.indexOf(" ORDER ")>0) {
				strs = sql.toLowerCase().split(" ORDER ");
			}else {
				strs = sql.toLowerCase().split(" order ");
			}
			if(DBBase.isOracle()) {
				return "select count(*) from (" + strs[0] + ")";
			}else {
				return "select count(*) from (" + strs[0] + ")   countRecord";
			}
		}
		return "select count(*) from (" + sql + ")   countRecord";
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}
 
}