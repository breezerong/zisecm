package com.ecm.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.PermissionContext;
import com.ecm.core.PermissionContext.ObjectPermission;
import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmAclItemMapper;
import com.ecm.core.dao.EcmAclMapper;
import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmPermit;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.util.DBUtils;
import com.ecm.icore.service.IAclService;
import com.ecm.icore.service.IEcmSession;


@Service
@Scope("prototype")
public class AclService extends EcmObjectService<EcmAcl> implements IAclService {
	private final Logger logger = LoggerFactory.getLogger(AclService.class);

	public AclService()
	{
		serviceCode = ServiceContext.ACL_CODE;
		//前端控制，服务不控制
		systemPermission = 0;
		logger.info("ServiceCode:"+serviceCode+",systemPermission:"+systemPermission);
	}
	
	/**
	 * 权限数据访问
	 */
	@Autowired
	private EcmAclMapper ecmAcl;
	
	/**
	 * 权限数据访问
	 */
	@Autowired
	private EcmAclItemMapper ecmAclItem;


	@Override
	public List<EcmAcl> getAllObject(String token) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+1,systemPermission);
		return null;
	}
	
	@Override
	public List<EcmAcl> getAcls(String tocken, Pager pager , String name) {
		String sql = "select * from ecm_acl";
		if(!EcmStringUtils.isEmpty(name))
		{
			name = DBUtils.getString(name);
			if(name.indexOf("%")>-1) {
				sql += " where name like '"+name+"'";
			}else {
				sql += " where name = '"+name+"'";
			}
		}
		List<EcmAcl> list = ecmAcl.searchToEntity(sql, pager);
		return list;
	}

	@Override
	public EcmAcl getObjectById(String token, String id) throws  AccessDeniedException  {
		// TODO Auto-generated method stub
		//super.hasPermission(token,serviceCode+1,systemPermission);
		this.getSession(token);
		return (EcmAcl) ecmAcl.selectByPrimaryKey(id);
	}
	
	@Override
	public EcmAcl getObjectByName(String token, String name) throws AccessDeniedException {
		this.getSession(token);
		String sql = "select * from ecm_acl where name='"+DBUtils.getString(name)+"'";
		List<EcmAcl> list = ecmAcl.searchToEntity(sql);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean updateObject(String token, Object obj) throws  AccessDeniedException, NoPermissionException, EcmException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+3,systemPermission);
		EcmAcl ecmObj =(EcmAcl)obj;
		ecmObj.setModifier(getSession(token).getCurrentUser().getUserName());
		ecmObj.setModifiedDate(new Date());
		return ecmAcl.updateByPrimaryKey(ecmObj)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+4,systemPermission);
		getSession(token);
		
		ecmAclItem.deleteByParentId(((EcmAcl)obj).getId());
		return ecmAcl.deleteByPrimaryKey(((EcmAcl)obj).getId())>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String newObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+2,systemPermission);
		EcmAcl ecmObj =(EcmAcl)obj;
		ecmObj.createId();
		ecmObj.setCreator(getSession(token).getCurrentUser().getUserName());
		ecmObj.setCreationDate(new Date());
		ecmObj.setModifier(getSession(token).getCurrentUser().getUserName());
		ecmObj.setModifiedDate(new Date());
		ecmAcl.insert(ecmObj);
		// 添加everyone权限，读取内容
		grant( token,  ecmObj.getId(), PermissionContext.EVERYONE, PermissionContext.USER_TARGET_TYPE,  ObjectPermission.READ, null);
		// 添加owner权限，授权
		grant( token,  ecmObj.getId(), PermissionContext.OWNER, PermissionContext.USER_TARGET_TYPE,  ObjectPermission.PEMISSION, null);
		return ecmObj.getId();
	}
	
	@Override
	public void grantUser(String token, String aclId,String targetName,int permission,Date expireDate) throws AccessDeniedException {
		grant( token,  aclId, targetName,PermissionContext.USER_TARGET_TYPE,  permission, expireDate);
	}
	
	@Override
	public void grantGroup(String token, String aclId,String targetName,int permission,Date expireDate) throws AccessDeniedException {
		grant( token,  aclId, targetName, PermissionContext.GROUP_TARGET_TYPE ,  permission, expireDate);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void grant(String token, EcmAcl acl,EcmPermit permit) throws AccessDeniedException {
		
		EcmPermit item = ecmAclItem.selectByGrantName(acl.getId(), permit.getTargetName(),permit.getTargetType());
		if(item==null) {
			permit.createId();
			ecmAclItem.insert(item);
		}
		else {
			item.setPermission(permit.getPermission());
			item.setExpireDate(permit.getExpireDate());
			ecmAclItem.updateByPrimaryKey(item);
		}
		acl.setModifier(getSession(token).getCurrentUser().getUserName());
		acl.setModifiedDate(new Date());
		ecmAcl.updateByPrimaryKey(acl);
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void grant(String token, String aclId,String targetName,int targetType, int permission,Date expireDate) throws AccessDeniedException {
		EcmPermit item = ecmAclItem.selectByGrantName(aclId, targetName,targetType);
		if(item==null) {
			item = new EcmPermit();
			item.createId();
			item.setParentId(aclId);
			item.setTargetName(targetName);
			item.setPermission(permission);
			item.setExpireDate(expireDate);
			item.setTargetType(targetType);
			ecmAclItem.insert(item);
		}
		else {
			item.setPermission(permission);
			item.setExpireDate(expireDate);
			ecmAclItem.updateByPrimaryKey(item);
		}
		EcmAcl acl = ecmAcl.selectByPrimaryKey(aclId);
		acl.setModifier(getSession(token).getCurrentUser().getUserName());
		acl.setModifiedDate(new Date());
		ecmAcl.updateByPrimaryKey(acl);
	}
	
	@Override
	public void revokeUser(String token, String aclId,String targetName) throws AccessDeniedException {
		if(PermissionContext.EVERYONE.equals(targetName)||PermissionContext.OWNER.equals(targetName)) {
			return;
		}
		revoke(token, aclId,targetName, PermissionContext.USER_TARGET_TYPE);
	}
	
	@Override
	public void revokeGroup(String token, String aclId,String targetName) throws AccessDeniedException {
		if(PermissionContext.EVERYONE.equals(targetName)||PermissionContext.OWNER.equals(targetName)) {
			return;
		}
		revoke(token, aclId,targetName, PermissionContext.GROUP_TARGET_TYPE);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	private void revoke(String token, String aclId,String targetName,int targetType) throws AccessDeniedException {
		EcmPermit item = ecmAclItem.selectByGrantName(aclId, targetName,targetType);
		if(item!=null) {
			ecmAclItem.deleteByPrimaryKey(item.getId());
			EcmAcl acl = ecmAcl.selectByPrimaryKey(aclId);
			acl.setModifier(getSession(token).getCurrentUser().getUserName());
			acl.setModifiedDate(new Date());
			ecmAcl.updateByPrimaryKey(acl);
		}
	}
	
	@Override
	public EcmAcl copy(String token, String id, String newName, String description) throws AccessDeniedException {
		EcmAcl acl = ecmAcl.selectByPrimaryKey(id);
		return copy(token, acl, newName, description);
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public EcmAcl copy(String token, EcmAcl acl, String newName, String description) throws AccessDeniedException {
		String newId=null;
		if(acl!=null) {
			String oldId = acl.getId();
			acl.createId();
			newId = acl.getId();
			acl.setCreator(getSession(token).getCurrentUser().getUserName());
			acl.setCreationDate(new Date());
			acl.setModifier(getSession(token).getCurrentUser().getUserName());
			acl.setModifiedDate(new Date());
			if(StringUtils.isEmpty(newName)||newName.equals(acl.getName())) {
				acl.setName("ecm_"+newId);
			}else {
				acl.setName(newName);
			}
			acl.setDescription(description);
			ecmAcl.insert(acl);
			List<EcmPermit> list = ecmAclItem.selectByParentId(oldId);
			for(EcmPermit item: list) {
				item.createId();
				item.setParentId(newId);
				ecmAclItem.insert(item);
			}
		}
		return acl;
	}
	@Override
	public List<EcmPermit> getPermits(String token,String id){
		return ecmAclItem.selectByParentId(id);
	}
	
	@Override
	public int getPermission(String token, String aclName) throws AccessDeniedException {
		IEcmSession session = getSession(token);
		String currentUser = session.getCurrentUser().getUserName();
		String userID = session.getCurrentUser().getUserId();
		StringBuilder sb = new StringBuilder();
		sb.append("select max(PERMISSION) from(select PERMISSION from ecm_acl_item a, ecm_acl b where ");
		sb.append("b.NAME='").append(aclName);
		sb.append("' and a.PARENT_ID = b.ID and a.TARGET_TYPE='1' and a.TARGET_NAME in('everyone'");
		sb.append(",'").append(currentUser).append("'");
		
		sb.append(")");
		sb.append(" union ");
		sb.append("select PERMISSION from ecm_acl_item a, ecm_acl b, ecm_group c, ecm_group_user d where b.NAME='");
		sb.append(aclName).append("'  and a.PARENT_ID = b.ID and a.TARGET_TYPE='2' and a.TARGET_NAME=c.NAME and c.ID=d.GROUP_ID and ");
		sb.append("d.USER_ID='").append(userID).append("') as permittemp");
		List<Map<String, Object>> list = ecmAcl.executeSQL(sb.toString());
		
		if(list.size()>0) {
			return (int)list.get(0).get("PERMISSION");
		}
		return 1;
	}

}
