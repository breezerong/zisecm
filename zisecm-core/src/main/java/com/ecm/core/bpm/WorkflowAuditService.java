package com.ecm.core.bpm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.IWorkflowAuditService;
/**
 * 工作流日志管理
 * @author Haihong Rong
 *
 */
@Component
@Scope("prototype")
public class WorkflowAuditService extends EcmObjectService<EcmAuditWorkflow> implements IWorkflowAuditService {
	@Autowired
	private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
	
	@Override
	public List<EcmAuditWorkflow> getMyAuditWorkflow(String token) throws AccessDeniedException{
		String cond = " CREATOR='"+getSession(token).getCurrentUser().getUserName()+"' order by START_DATE desc";
		return ecmAuditWorkflowMapper.selectByCondition(cond);
	}
	@Override
	public List<Map<String, Object>> getMapList(String token, String sql){
		return ecmAuditWorkflowMapper.executeSQL(sql);
	}
	@Override
	public int getMyAuditWorkflowCount(String token, String condition) throws AccessDeniedException{
		String cond = " CREATOR='"+getSession(token).getCurrentUser().getUserName()+"'";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		return ecmAuditWorkflowMapper.getCountByCondition(cond);
	}
	@Override
	public List<EcmAuditWorkflow> getMyAuditWorkflow(String token, int pageSize,int startIndex,String condition) throws AccessDeniedException{
		String cond = " CREATOR='"+getSession(token).getCurrentUser().getUserName()+"'";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		cond += " order by START_DATE desc";
		cond += " limit "+ startIndex + ","+pageSize;
		return ecmAuditWorkflowMapper.selectByCondition(cond);
	}
	
	@Override
	public int getAuditWorkflowCount(String token, String condition){
		String cond = " 1=1 ";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		return ecmAuditWorkflowMapper.getCountByCondition(cond);
	}
	@Override
	public List<EcmAuditWorkflow> getAuditWorkflow(String token, int pageSize,int startIndex,String condition){
		String cond = " 1=1 ";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		cond += " order by START_DATE desc";
		cond += " limit "+ startIndex + ","+pageSize;
		return ecmAuditWorkflowMapper.selectByCondition(cond);
	}
	
	@Override
	public List<EcmAuditWorkflow> getObjects(String token, String condition) throws EcmException {
		// TODO Auto-generated method stub
		return ecmAuditWorkflowMapper.selectByCondition(condition);
	}
}
