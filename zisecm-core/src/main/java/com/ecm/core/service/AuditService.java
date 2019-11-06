package com.ecm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmAuditGeneralMapper;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.icore.service.IAuditService;
/**
 * 日志服务
 * @author Haihong Rong
 * @date 2019年7月20日 上午7:49:02
 */
@Service
@Scope("prototype")
public class AuditService extends EcmObjectService<EcmAuditGeneral> implements IAuditService {

	@Autowired
    private EcmAuditGeneralMapper ecmAuditGeneralMapper;
	public AuditService() {
		serviceCode = ServiceContext.AUDIT_GENERAL_CODE;
	}
    
	@Override
	public String newObject(String token, Object obj) {
		// TODO Auto-generated method stub
		ecmAuditGeneralMapper.insert((EcmAuditGeneral)obj);
		return ((EcmAuditGeneral)obj).getId();
	}

	@Override
	public boolean deleteObject(String token, Object obj) {
		return ecmAuditGeneralMapper.deleteByPrimaryKey(((EcmAuditGeneral)obj).getId())==1;
	}

	@Override
	public List<EcmAuditGeneral> getObjects(String token, String condition) {
		// TODO Auto-generated method stub
		
		 String sql ="select * from ecm_audit_general where "+condition;
		 
		return ecmAuditGeneralMapper.selectByCondition(sql);
	}

	@Override
	public EcmAuditGeneral getObjectById(String token, String id) {
		// TODO Auto-generated method stub
		return ecmAuditGeneralMapper.selectByPrimaryKey(id);
	}

}
