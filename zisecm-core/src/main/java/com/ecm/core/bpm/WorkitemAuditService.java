package com.ecm.core.bpm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.IWorkitemAuditService;
/**
 * 任务日志管理
 * @author Haihong Rong
 *
 */
@Component
@Scope("prototype")
public class WorkitemAuditService extends EcmObjectService<EcmAuditWorkitem> implements IWorkitemAuditService {
	@Autowired
	private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;
	
	@Override
	public List<EcmAuditWorkitem> getMyAuditWorkitem(String token) throws AccessDeniedException{
		//String sql = " PERFORMER='"+getSession(token).getCurrentUser().getUserName()+"' order by START_DATE";
		String sql = " ASSIGNEE='"+getSession(token).getCurrentUser().getUserName()+"' order by END_TIME desc";
		Pager pager = new Pager();
		pager.setPageSize(1000);
		return ecmAuditWorkitemMapper.selectByCondition(sql, pager);
	}
	
	@Override
	public List<EcmAuditWorkitem> getMyAuditWorkitem(String token, Pager pager,String condition) throws AccessDeniedException{
		String cond = " ASSIGNEE='"+getSession(token).getCurrentUser().getUserName()+"'";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		cond += " order by CREATE_TIME DESC";
		
		return ecmAuditWorkitemMapper.selectByCondition(cond,pager);
	}
	
	@Override
	public int getMyAuditWorkitemCount(String token) throws AccessDeniedException{
		String cond = " PERFORMER='"+getSession(token).getCurrentUser().getUserName()+"'";
		return ecmAuditWorkitemMapper.getCountByCondition(cond);
	}
	
	@Override
	public int getMyAuditWorkitemCount(String token, String condition) throws AccessDeniedException{
		String cond = " PERFORMER='"+getSession(token).getCurrentUser().getUserName()+"'";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		return ecmAuditWorkitemMapper.getCountByCondition(cond);
	}
	
	@Override
	public List<EcmAuditWorkitem> getObjects(String token, String condition){
		Pager pager = new Pager();
		pager.setPageSize(1000);
		return ecmAuditWorkitemMapper.selectByCondition(condition, pager);
	}
	
	public List<EcmAuditWorkitem> getObjects(String token, Pager pager, String condition){
		return ecmAuditWorkitemMapper.selectByCondition(condition, pager);
	}
}
