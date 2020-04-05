package com.ecm.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmAuditGeneralMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.db.DBGeneralUtils;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
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
	
	public List<EcmAuditGeneral> getObjectByCondition(String token,String condition,Pager pager) throws SqlDeniedException, EcmException {
		if(StringUtils.isEmpty(condition)) {
			condition = "1=1";
		}
		DBGeneralUtils.conditionValidate(condition);
		List<EcmAuditGeneral> list = ecmAuditGeneralMapper.selectByCondition(condition, pager);
		return list;
	}

	@Override
	public boolean deleteObject(String token, Object obj) {
		return ecmAuditGeneralMapper.deleteByPrimaryKey(((EcmAuditGeneral)obj).getId())==1;
	}

	@Override
	public List<EcmAuditGeneral> getObjects(String token, String condition) throws SqlDeniedException, EcmException {
		// TODO Auto-generated method stub
		DBGeneralUtils.conditionValidate(condition);
		String sql ="select * from ecm_audit_general where "+condition;
		 
		return ecmAuditGeneralMapper.selectByCondition(sql);
	}

	@Override
	public EcmAuditGeneral getObjectById(String token, String id) {
		// TODO Auto-generated method stub
		return ecmAuditGeneralMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		return ecmAuditGeneralMapper.deleteByPrimaryKey(id)>0;
	}

}
