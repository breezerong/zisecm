package com.ecm.portal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.service.IFolderService;
@Service
@Scope("prototype")
public class DcFolderService extends EcmObjectService<EcmFolder> implements IFolderService {
	@Autowired
	private EcmFolderMapper ecmFolderMapper;
	@Override
	public long getFolderCount(String token, String folderId) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 根据parentPath查询路径，待权限
	 * @param token
	 * @param path
	 * @return
	 * @throws AccessDeniedException
	 */
	public List<EcmFolder> getFoldersByParentPathWithPermission(String token,String path) throws AccessDeniedException{
		EcmFolder folder = getObjectByPath(token,path);
		List<EcmFolder> list = new ArrayList<EcmFolder>();
		
		String userName = this.getSession(token).getCurrentUser().getUserName();
		String userId = this.getSession(token).getCurrentUser().getUserId();
		if(folder != null) {
			String sql=" parent_id ='"+folder.getId()+"'";
			if(this.getSession(token).getCurrentUser().getSystemPermission()<5) {
				sql += " and exists" + 
					"(select 1 from ecm_acl b,ecm_acl_item c where ACL_NAME =b.NAME and b.ID =c.PARENT_ID and " + 
					"((c.TARGET_NAME='"+userName+"' or c.TARGET_NAME='everyone' ) " + 
					"or (c.TARGET_TYPE=2 and " + 
					"exists(select 1 from ecm_group d,ecm_group_user e where d.NAME=c.TARGET_NAME and d.ID =e.GROUP_ID and e.USER_ID='"+userId+"' )" + 
					")) and c.PERMISSION>1)";
			}
			list = ecmFolderMapper.selectByCondition(sql);
		}
		
		
		return list;
	}
	
	@Override
	public List<EcmFolder> getFoldersByParentPath(String token,String path){
		
		return null;
	}

	@Override
	public List<EcmFolder> getFoldersByParentId(String token, String parentId) throws AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
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
	public EcmFolder getObjectByName(String token, String name, String parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean copyFolders(String token, String sourceId, String targetId, boolean includeSource)
			throws AccessDeniedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String revokeUser(String token, String id, String targetName, boolean newAcl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String revokeUser(String token, EcmFolder folder, String targetName, boolean newAcl)
			throws NoPermissionException, AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String revokeGroup(String token, String id, String targetName, boolean newAcl) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String revokeGroup(String token, EcmFolder folder, String targetName, boolean newAcl)
			throws NoPermissionException, AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPermit(String token, String id) throws AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPermit(String token, EcmFolder folder) throws AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String grantUser(String token, EcmFolder folder, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grantUser(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grantGroup(String token, String id, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grantGroup(String token, EcmFolder folder, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EcmFolder getObjectByPaths(String token, String folderPaths) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EcmFolder> getFoldersByParentPaths(String token, String paths) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
