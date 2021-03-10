package com.ecm.core.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.AuditContext;
import com.ecm.core.PermissionContext.ObjectPermission;
import com.ecm.core.PermissionContext.SystemPermission;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.dao.EcmQueryMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IFolderService;
@Service
@Scope("prototype")
public class FolderService extends EcmObjectService<EcmFolder> implements IFolderService{
	
	

	@Autowired
	private EcmFolderMapper ecmFolderMapper;
	
	@Autowired
	private AclService aclService;
	
	@Autowired
	private EcmQueryMapper ecmQuery;

	@Override
	public long getFolderCount(String token, String folderId) {
		String sql = "select count(*) as itemcount from ecm_folder where parent_id='"+folderId+"'";
		
		List<Map<String, Object>> list = ecmFolderMapper.searchToMap(sql);
		
		return  Long.parseLong(list.get(0).get("itemcount").toString());
	}
	@Override
	public EcmFolder getObjectById(String token,String id) {
		return ecmFolderMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public String newObject(String token,Object obj) throws AccessDeniedException {
		EcmFolder folder =(EcmFolder)obj;
		folder.createId();
		String parentId = folder.getParentId();
		//设置继承字段
		if(!StringUtils.isEmpty(parentId)&&!parentId.equals("0")){
			EcmFolder parentFolder = null;
			if(StringUtils.isEmpty(folder.getAclName())){
				if(parentFolder==null) {
					parentFolder = this.getObjectById(token, parentId);
				
				}
				if(parentFolder !=null) {
					folder.setAclName(parentFolder.getAclName());
				}
			}
			if(StringUtils.isEmpty(folder.getGridView())){
				if(parentFolder==null) {
					parentFolder = this.getObjectById(token, parentId);
				
				}
				if(parentFolder !=null) {
					folder.setGridView(parentFolder.getGridView());
				}
			}
			if(StringUtils.isEmpty(folder.getTypeName())){
				if(parentFolder==null) {
					parentFolder = this.getObjectById(token, parentId);
				}
				if(parentFolder !=null) {
					folder.setTypeName(parentFolder.getTypeName());
				}
			}
		}
		folder.setFolderPath(getFullPath(token,folder,folder.getName()));
		folder.setCreator(this.getCurrentUser(token).getUserName());
		folder.setCreationDate(new Date());
		ecmFolderMapper.insert(folder);
		return folder.getId();
	}
	
	@Override
	public EcmFolder getObjectByName(String token,String name, String parentId) {
		String cond = " NAME='"+DBFactory.getDBConn().getDBUtils().getString(name)+"' and PARENT_ID='"+DBFactory.getDBConn().getDBUtils().getString(parentId)+"'";
		List<EcmFolder> list = ecmFolderMapper.selectByCondition(cond);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateObject(String token,Object folder) throws AccessDeniedException {
		EcmFolder newFolder = (EcmFolder)folder;
		EcmFolder en = getObjectById( token,newFolder.getId());
		boolean needUpdateChild = false;
		if(en != null) {
			//如果文件夹名或文件夹路径修改，需要修改全路径
			if(!en.getName().equals(newFolder.getName())||!en.getFolderPath().equals(newFolder.getFolderPath())) {
				newFolder.setFolderPath(getFullPath(token,newFolder,newFolder.getName()));
				needUpdateChild = true;
			}
		}
		newFolder.setModifiedDate(new Date());
		newFolder.setModifier(this.getCurrentUser(token).getUserName());
		ecmFolderMapper.updateByPrimaryKey(newFolder);
		if(needUpdateChild) {
			updateChildFolderPath( token, newFolder.getId());
		}
		return true;
	}
	/**
	 * 递归更新子文件夹Path
	 * @param token
	 * @param parentId
	 * @throws AccessDeniedException 
	 */
	private void updateChildFolderPath(String token,String parentId) throws AccessDeniedException {
		 List<EcmFolder> list = getFoldersByParentId( token, parentId);
		 for(EcmFolder folder: list) {
			 folder.setFolderPath(getFullPath(token,folder,folder.getName()));
			 ecmFolderMapper.updateByPrimaryKey(folder);
			 updateChildFolderPath( token, folder.getId());
		 }
	}
	
	/**
	 * 获取全路径
	 * @param token
	 * @param folder
	 * @param path
	 * @return
	 */
	public String getFullPath(String token,EcmFolder folder,String path) {
		if(StringUtils.isEmpty(folder.getParentId())||folder.getParentId().equals("0")) {
			return "/"+path;
		}
		EcmFolder en = getObjectById( token,folder.getParentId());
		if(en!=null) {
			path =en.getName()+"/"+path;
			path = getFullPath(token, en, path);
		}
		return path;
	}
	
	@Override
	public boolean deleteObject(String token,Object folder) throws NoPermissionException, AccessDeniedException, EcmException {
		EcmFolder fld = (EcmFolder)folder;
		if (getPermit(token, fld.getId()) < ObjectPermission.DELETE) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no delete permission:" + fld.getId());
		}
	
		return ecmFolderMapper.deleteByPrimaryKey(fld.getId())>0;
	}
	
	@Override
	public EcmFolder getObjectByPath(String token,String folderPath) {
		String cond = " FOLDER_PATH='"+DBFactory.getDBConn().getDBUtils().getString(folderPath)+"'";
		List<EcmFolder> list = ecmFolderMapper.selectByCondition(cond);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public EcmFolder getObjectByPaths(String token,String folderPaths) {
		String cond = " FOLDER_PATH in("+folderPaths+")";
		List<EcmFolder> list = ecmFolderMapper.selectByCondition(cond);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
//	public List<EcmFolder> getFoldersByPath(String token,String path){
//		String cond = " FOLDER_PATH like '"+path+"%'";
//		return ecmFolderMapper.selectByParentId(cond);
//	}
	
	@Override
	public List<EcmFolder> getFoldersByParentId(String token,String parentId) throws AccessDeniedException{
		String userId = this.getSession(token).getCurrentUser().getUserId();
		String userName = this.getSession(token).getCurrentUser().getUserName();
		String condition = "PARENT_ID='"+parentId.replace("'", "")+"'";
		if(this.getSession(token).getCurrentUser().getSystemPermission()<5) {
			condition += " and exists" + 
				"(select 1 from ecm_acl b,ecm_acl_item c where ACL_NAME =b.NAME and b.ID =c.PARENT_ID and " + 
				"((c.TARGET_NAME='"+userName+"' or c.TARGET_NAME='everyone' ) " + 
				"or (c.TARGET_TYPE=2 and " + 
				"exists(select 1 from ecm_group d,ecm_group_user e where d.NAME=c.TARGET_NAME and d.ID =e.GROUP_ID and e.USER_ID='"+userId+"' )" + 
				")) and c.PERMISSION>1)";
		}
		return ecmFolderMapper.selectByCondition(condition);
	}
	@Override
	public List<EcmFolder> getFoldersByParentPath(String token,String path){
		EcmFolder folder = getObjectByPath(token,path);
		List<EcmFolder> list = new ArrayList<EcmFolder>();
		if(folder != null) {
			list = ecmFolderMapper.selectByParentId(folder.getId());
		}
		return list;
	}
	
	@Override
	public List<EcmFolder> getFoldersByParentPaths(String token,String paths){
		EcmFolder folder = getObjectByPaths(token,paths);
		List<EcmFolder> list = new ArrayList<EcmFolder>();
		if(folder != null) {
			list = ecmFolderMapper.selectByParentId(folder.getId());
		}
		return list;
	}
	@Override
	public boolean copyFolders(String token, String sourceId, String targetId,boolean includeSource) throws AccessDeniedException {
		if(includeSource) {
			EcmFolder folder = getObjectById( token,sourceId);
			folder.createId();
			folder.setParentId(targetId);
			this.newObject(token, folder);
			copyChildFolders(token,sourceId, folder.getId());
		}else {
			copyChildFolders(token,sourceId, targetId);
		}
		return true;
	}
	
	private void copyChildFolders(String token, String parentId, String targetId) throws AccessDeniedException {
		List<EcmFolder> list = this.getFoldersByParentId(token, parentId);
		for(EcmFolder folder: list) {
			String id = folder.getId();
			folder.createId();
			folder.setParentId(targetId);
			this.newObject(token, folder);
			copyChildFolders(token, id,  folder.getId());
		}
	}
	
	@Override
	public String grantUser(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmFolder folder = getObjectById(token, id);
		return grantUser(token, folder, targetName, permission, expireDate, newAcl);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String grantUser(String token, EcmFolder folder, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		String aclName = "";
		if (folder != null) {
			if (getPermit(token, folder.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + folder.getId());
			}
			aclName = folder.getAclName();
			if (!StringUtils.isEmpty(aclName)) {
				EcmAcl acl = aclService.getObjectByName(token, aclName);
				if (acl != null) {
					if (newAcl) {
						acl = aclService.copy(token, acl, null, folder.getId());
						updateAclName(token, folder.getId(), acl.getName());
					}
					aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
					aclName = acl.getName();
				} else {
					acl = new EcmAcl();
					acl.createId();
					acl.setName("ecm_" + acl.getId());
					aclService.newObject(token, acl);
					updateAclName(token, folder.getId(), acl.getName());
					aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
					aclName = acl.getName();
				}
			} else {
				EcmAcl acl = new EcmAcl();
				acl.createId();
				acl.setName("ecm_" + acl.getId());
				aclService.newObject(token, acl);
				aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
				updateAclName(token, folder.getId(), acl.getName());
				aclName = acl.getName();
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, folder.getId(), null, aclName);
		}
		return aclName;
	}
	
	@Override
	public String grantGroup(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmFolder folder = getObjectById(token, id);
		return grantGroup(token, folder, targetName, permission, expireDate, newAcl);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String grantGroup(String token, EcmFolder folder, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		String aclName = "";
		if (folder != null) {

			if (getPermit(token, folder.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + folder.getId());
			}
			aclName = folder.getAclName();
			if (!StringUtils.isEmpty(aclName)) {
				EcmAcl acl = aclService.getObjectByName(token, aclName);
				if (newAcl) {
					acl = aclService.copy(token, acl, null, folder.getId());
					updateAclName(token, folder.getId(), acl.getName());
				}
				aclName = acl.getName();
				aclService.grantGroup(token, acl.getId(), targetName, permission, expireDate);
			} else {
				EcmAcl acl = new EcmAcl();
				acl.createId();
				acl.setName("ecm_" + acl.getId());
				aclService.newObject(token, acl);
				aclService.grantGroup(token, acl.getId(), targetName, permission, expireDate);
				updateAclName(token, folder.getId(), acl.getName());
				aclName = acl.getName();
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, folder.getId(), null, aclName);
		}
		return aclName;
	}


	@Override
	public String revokeUser(String token, String id, String targetName, boolean newAcl) throws Exception {
		EcmFolder folder = getObjectById(token, id);
		return revokeUser(token, folder, targetName, newAcl);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String revokeUser(String token, EcmFolder folder, String targetName, boolean newAcl) throws NoPermissionException, AccessDeniedException {
		String aclName = "";
		if (folder != null) {
			if (getPermit(token, folder.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + folder.getId());
			}
			aclName = folder.getAclName();
			if (StringUtils.isEmpty(aclName)) {
				return "";
			}
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if (acl != null) {
				if (newAcl) {
					acl = aclService.copy(token, acl, null, folder.getId());
					updateAclName(token, folder.getId(), acl.getName());
					aclName = acl.getName();
				}
				aclService.revokeUser(token, acl.getId(), targetName);
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, folder.getId(), null, aclName);
		}
		return aclName;
	}

	@Override
	public String revokeGroup(String token, String id, String targetName, boolean newAcl) throws Exception {
		EcmFolder folder = getObjectById(token, id);
		return revokeGroup(token, folder, targetName, newAcl);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String revokeGroup(String token, EcmFolder folder, String targetName, boolean newAcl) throws NoPermissionException, AccessDeniedException {
		String aclName = "";
		if (folder != null) {
			if (getPermit(token, folder.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + folder.getId());
			}
			aclName = folder.getAclName();
			if (StringUtils.isEmpty(aclName)) {
				return "";
			}
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if (acl != null) {
				if (newAcl) {
					acl = aclService.copy(token, acl, null, folder.getId());
					updateAclName(token, folder.getId(), acl.getName());
					aclName = acl.getName();
				}
				aclService.revokeGroup(token, acl.getId(), targetName);
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, folder.getId(), null, aclName);
		}
		return aclName;
	}
	
	@Override
	public int getPermit(String token, String id) throws AccessDeniedException {
		EcmFolder folder = getObjectById(token, id);
		return getPermit(token, folder);
	}

	@Override
	public int getPermit(String token, EcmFolder folder) throws AccessDeniedException {
		// TODO Auto-generated method stub
		// 超级用户返回最大权限
		if (getSession(token).getCurrentUser().getSystemPermission() >= SystemPermission.SUPER_USER) {
			return 9;
		}
		String currentUser = getSession(token).getCurrentUser().getUserName();
		String userID = getSession(token).getCurrentUser().getUserId();
		String aclName = folder.getAclName();
		// 没有设置ACL
		if (StringUtils.isEmpty(aclName)) {
			// Owner 返回最大权限
			if (currentUser.equals(folder.getCreator())) {
				return 9;
			} else {
				return ObjectPermission.READ;
			}
		} else {
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if (acl == null) {
				// Owner 返回最大权限
				if (currentUser.equals(folder.getCreator())) {
					return 9;
				} else {
					return ObjectPermission.READ;
				}
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("select max(PERMISSION) as PERMISSION from(select PERMISSION from ecm_acl_item a, ecm_acl b where ");
				sb.append("b.NAME='").append(aclName);
				sb.append("' and a.PARENT_ID = b.ID and a.TARGET_TYPE='1' and a.TARGET_NAME in('everyone'");
				sb.append(",'").append(currentUser).append("'");
				if (currentUser.equals(folder.getCreator())) {
					sb.append(",'owner'");
				}
				sb.append(")");
				sb.append(" union ");
				sb.append(
						"select PERMISSION from ecm_acl_item a, ecm_acl b, ecm_group c, ecm_group_user d where b.NAME='");
				sb.append(aclName).append(
						"'  and a.PARENT_ID = b.ID and a.TARGET_TYPE='2' and a.TARGET_NAME=c.NAME and c.ID=d.GROUP_ID and ");
				sb.append("d.USER_ID='").append(userID).append("') as permittemp");
				List<Map<String, Object>> list = ecmQuery.executeSQL(sb.toString());

				if (list.size() > 0) {
					return (int) list.get(0).get("PERMISSION");
				}
			}
		}
		return 1;
	}
	
	private void updateAclName(String token, String id, String aclName) throws AccessDeniedException {
		String sql = "update ecm_folder set ACL_NAME='" + aclName + "', ";
		sql += " MODIFIED_DATE=" + DBFactory.getDBConn().getDBUtils().getDBDateNow();
		sql += ", MODIFIER='" + getSession(token).getCurrentUser().getUserName() + "'";
		sql += " where ID='" + id + "'";
		ecmQuery.executeSQL(sql);
	}
	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		return ecmFolderMapper.deleteByPrimaryKey(id)>0;
	}
	
}
