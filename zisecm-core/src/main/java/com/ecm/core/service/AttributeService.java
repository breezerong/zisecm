package com.ecm.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.PermissionContext;
import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmAttributeMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IAttributeService;


@Service
@Scope("prototype")
public class AttributeService extends EcmObjectService<EcmAttribute> implements IAttributeService {
	private final Logger logger = LoggerFactory.getLogger(AttributeService.class);
	
	private boolean isChanged=true;
	List<EcmAttribute> list=null;

	public AttributeService()
	{
		serviceCode = ServiceContext.DOC_ATTRIBUTE_CODE;
		systemPermission = PermissionContext.SystemPermission.SYSTEM_CONFIGE;
		logger.info("ServiceCode:"+serviceCode+",systemPermission:"+systemPermission);
	}
	

	
	/**
	 * 数据访问
	 */
	@Autowired
	private EcmAttributeMapper ecmAttribute;

	
	@Override
	public List<EcmAttribute> getDocAttributes(String token) throws EcmException {
		// TODO Auto-generated method stub
		//super.hasPermission(serviceCode+1,systemPermission);
		if(isChanged||list==null) {
			list = ecmAttribute.selectAll(DBFactory.getDBConn().getDBDocManager().getDocAttributesSql());
			isChanged = false;
		}
		return list;
	}
	/**
	 * 1：String，2：Int，3：Double,4:long，5：DateTime，6：boolean
	 * @param obj
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 */
	@Override
	public boolean newAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException {
		super.hasPermission(token, serviceCode+1,systemPermission);

		//super.hasPermission(serviceCode+1,systemPermission);
		String sql = DBFactory.getDBConn().getDBDocManager().newDocAttributeSql(obj);
		isChanged=true;
		return ecmAttribute.executeSQL(sql).size()>0;
	}
	@Override
	public boolean updateAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// ALTER TABLE zisecm.ecm_user MODIFY COLUMN `LDAP_CN` varchar(254) NULL COMMENT 'LDAP用户登录CN';
		super.hasPermission(token, serviceCode+1,systemPermission);
		String sql = DBFactory.getDBConn().getDBDocManager().updateDocAttributeSql(obj);
		isChanged=true;
		return ecmAttribute.executeSQL(sql).size()>0;
	}
	@Override
	public boolean deleteAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// ALTER TABLE zisecm.ecm_user DROP COLUMN `LDAP_NAME`;
		
		super.hasPermission(token, serviceCode+1,systemPermission);
		String sql = DBFactory.getDBConn().getDBDocManager().deleteDocAttributeSql(obj);
		isChanged=true;
		return ecmAttribute.executeSQL(sql).size()>0;
	}
	
}
