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
			list = ecmAttribute.selectAll();
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
		// ALTER TABLE zisecm.ecm_document ADD C_DOC_STATUS varchar(32) NULL COMMENT '文档状态';
		//ALTER TABLE zisecm.ecm_document ADD IS_CURRENT TINYINT(1) DEFAULT 1 NULL COMMENT '是否当前版本';
		//ALTER TABLE zisecm.ecm_document ADD IS_HIDDEN TINYINT(1) DEFAULT 0 null COMMENT '是否隐藏';
		//ALTER TABLE zisecm.ecm_folder ADD column1 INT NULL;
		//ALTER TABLE zisecm.ecm_folder ADD column2 DATETIME NULL;
		//ALTER TABLE zisecm.ecm_folder ADD column3 DOUBLE NULL;
		//super.hasPermission(serviceCode+1,systemPermission);
		String sql ="ALTER TABLE ecm_document ADD ";
		sql+=obj.getName().toUpperCase()+" ";
		//1：String，2：Int，3：Double,4:long，5：DateTime，6：boolean
		switch(obj.getDataType()) {
		case 2:
			sql += "INT ";
			break;
		case 3:
			sql += "DOUBLE ";
			break;
		case 4:
			sql += "BIGINT ";
			break;
		case 5:
			sql += "DATETIME ";
			break;
		case 6:
			sql += "TINYINT(1) ";
			break;
		default:
			sql += "varchar("+obj.getLength()+") ";
			break;
		}
		if(obj.getIsNull().equals("NO")) {
			sql += "NOT NULL ";
		}else {
			sql += "NULL ";
		}
		if(obj.getDefaultValue()!=null&& obj.getDefaultValue().length()>0) {
			if(obj.getDataType()==1||obj.getDataType()==4) {
				sql += "DEFAULT '"+obj.getDefaultValue()+"' ";
			}
			else {
				sql += "DEFAULT "+obj.getDefaultValue()+" ";
			}
		}
		if(obj.getDescription()!=null&&obj.getDescription().length()>0) {
			sql += "COMMENT '"+obj.getDescription()+"' ";
		}
		isChanged=true;
		return ecmAttribute.executeSQL(sql).size()>0;
	}
	@Override
	public boolean updateAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// ALTER TABLE zisecm.ecm_user MODIFY COLUMN `LDAP_CN` varchar(254) NULL COMMENT 'LDAP用户登录CN';
		super.hasPermission(token, serviceCode+1,systemPermission);
		String sql ="ALTER TABLE ecm_document MODIFY ";
		sql+=obj.getName().toUpperCase()+" ";
		
		switch(obj.getDataType()) {
		case 2:
			sql += "INT ";
			break;
		case 3:
			sql += "DOUBLE ";
			break;
		case 4:
			sql += "BIGINT ";
			break;
		case 5:
			sql += "DATETIME ";
			break;
		case 6:
			sql += "TINYINT(1) ";
			break;
		default:
			sql += "varchar("+obj.getLength()+") ";
			break;
		}
		if(obj.getIsNull().equals("NO")) {
			sql += "NOT NULL ";
		}else {
			sql += "NULL ";
		}
		if(obj.getDefaultValue()!=null&& obj.getDefaultValue().length()>0) {
			sql += "DEFAULT "+obj.getDefaultValue()+" ";
		}
		if(obj.getDescription()!=null&&obj.getDescription().length()>0) {
			sql += "COMMENT '"+obj.getDescription()+"' ";
		}
		isChanged=true;
		return ecmAttribute.executeSQL(sql).size()>0;
	}
	@Override
	public boolean deleteAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// ALTER TABLE zisecm.ecm_user DROP COLUMN `LDAP_NAME`;
		
		super.hasPermission(token, serviceCode+1,systemPermission);
		String sql ="ALTER TABLE zisecm.ecm_user DROP COLUMN " +obj.getName();
		isChanged=true;
		return ecmAttribute.executeSQL(sql).size()>0;
	}
	
}
