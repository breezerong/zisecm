package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.ActionContext;
import com.ecm.core.AuditContext;
import com.ecm.core.entity.ChartBean;

@Service
public class ReportService {
	
	@Autowired
	private QueryService queryService;
	/**
	 * 获取最近一年用户登录情况，按月显示
	 * @return
	 */
	public ChartBean getLastYearUserLoginData(String token) {
		String sql ="select mygroup, count(*) as mycount from (SELECT DATE_FORMAT(EXCUTE_DATE, '%Y年%m月') as mygroup FROM ecm_audit_general WHERE ACTION_NAME='"+
				AuditContext.LOGIN+"' and DATE_SUB(CURDATE(), INTERVAL 1 year) < Date(EXCUTE_DATE))a  group by mygroup order by mygroup";
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		ChartBean bean = new ChartBean();
		for(Map<String, Object> vals:list) {
			bean.getxAxisData().add(vals.get("mygroup").toString());
			bean.getyAxisData().add(Long.parseLong(vals.get("mycount").toString()));
		}
		return bean;
	}
	
	/**
	 * 获取项目图纸情况，按项目显示
	 * @return
	 */
	public ChartBean getDrawingPorjectData(String token) {
		String sql ="select C_PROJECT, count(*) as mycount from ecm_document where `TYPE_NAME`='图纸' group by `C_PROJECT` order by `C_PROJECT`";
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		ChartBean bean = new ChartBean();
		for(Map<String, Object> vals:list) {
			bean.getxAxisData().add(vals.get("C_PROJECT")==null?"":vals.get("C_PROJECT").toString());
			bean.getyAxisData().add(Long.parseLong(vals.get("mycount").toString()));
		}
		return bean;
	}
	
	/**
	 * 获取项目文档情况，按月显示
	 * @param projectName 项目名称
	 * @return
	 */
	public ChartBean getDrawingDateData(String token, String projectName) {
		String sql ="select mygroup, count(*) as mycount from (SELECT DATE_FORMAT(CREATION_DATE, '%Y年%m月') as mygroup FROM ecm_document WHERE C_PROJECT='"+projectName+"')a  group by mygroup order by mygroup";
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		ChartBean bean = new ChartBean();
		for(Map<String, Object> vals:list) {
			bean.getxAxisData().add(vals.get("mygroup").toString());
			bean.getyAxisData().add(Long.parseLong(vals.get("mycount").toString()));
		}
		return bean;
	}
	
	/**
	 * 根据项目名称获取项目数据，按状态展示
	 * @param projectName 项目名称
	 * @return
	 */
	public ChartBean getDrawingStatusData(String token, String projectName) {
		String sql ="select `STATUS`, count(*) as mycount from ecm_document where `TYPE_NAME`='图纸' and C_PROJECT='"+projectName+"' group by `STATUS` order by `STATUS`";
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		ChartBean bean = new ChartBean();
		for(Map<String, Object> vals:list) {
			bean.getxAxisData().add(vals.get("STATUS").toString());
			bean.getyAxisData().add(Long.parseLong(vals.get("mycount").toString()));
		}
		return bean;
	}
	
	/**
	 * 按类型获取文件数量，按类型显示
	 * @return
	 */
	public ChartBean getDocumentTypeData(String token) {
		String sql ="select TYPE_NAME, count(*) as mycount from ecm_document  group by `TYPE_NAME` order by `TYPE_NAME`";
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		ChartBean bean = new ChartBean();
		for(Map<String, Object> vals:list) {
			bean.getxAxisData().add(vals.get("TYPE_NAME").toString());
			bean.getyAxisData().add(Long.parseLong(vals.get("mycount").toString()));
		}
		return bean;
	}
	
	/**
	 * 获取最近一年文档情况，按月显示
	 * @return
	 */
	public ChartBean getLastYearDocData(String token) {
		String sql ="select mygroup, count(*) as mycount from (SELECT DATE_FORMAT(CREATION_DATE, '%Y年%m月') as mygroup FROM ecm_document WHERE DATE_SUB(CURDATE(), INTERVAL 1 year) < Date(CREATION_DATE))a  group by mygroup order by mygroup";
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		ChartBean bean = new ChartBean();
		for(Map<String, Object> vals:list) {
			bean.getxAxisData().add(vals.get("mygroup").toString());
			bean.getyAxisData().add(Long.parseLong(vals.get("mycount").toString()));
		}
		return bean;
	}
	/**
	 * 根据条件获取文档数
	 * @param condition
	 * @return
	 */
	public long getDocumentCount(String token, String condition)
	{
		String sql = "select count(*) as mycount from ecm_document where "+condition;
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		if(list.size()>0) {
			return Long.parseLong(list.get(0).get("mycount").toString());
		}
		return 0;
	}
	
	/**
	 * 获取表对象数
	 * @param condition
	 * @return
	 */
	public long getTableCount(String token, String tableName,String condition)
	{
		String sql = "select count(*) as mycount from "+tableName;
		if(condition!=null&&condition.length()>0) {
			sql += " where "+condition;
		}
		List<Map<String, Object>> list =queryService.executeSQL(token, sql);
		if(list.size()>0) {
			return Long.parseLong(list.get(0).get("mycount").toString());
		}
		return 0;
	}

}
