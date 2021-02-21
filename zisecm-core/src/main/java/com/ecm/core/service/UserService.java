package com.ecm.core.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.SecureUtils;
import com.ecm.core.PermissionContext;
import com.ecm.core.PermissionContext.ObjectPermission;
import com.ecm.core.PermissionContext.SystemPermission;
import com.ecm.core.ServiceContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.dao.EcmGroupItemMapper;
import com.ecm.core.dao.EcmGroupMapper;
import com.ecm.core.dao.EcmGroupUserMapper;
import com.ecm.core.dao.EcmUserMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.db.DBGeneralUtils;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmGroupItem;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.icore.service.IEcmSession;
import com.ecm.icore.service.IUserService;

/**
 * @ClassName UserServiceImpl
 * @Description TODO(用户服务类),暂未处理嵌套组
 * @author Haihong Rong
 * @date 2018年7月4日 上午9:56:51
 *
 */
@Service
@Scope("prototype")
public class UserService extends EcmObjectService<EcmUser> implements IUserService {

	@Autowired
	private EcmUserMapper ecmUserMapper;

	@Autowired
	private EcmGroupMapper ecmGroupMapper;

	@Autowired
	private EcmGroupItemMapper ecmGroupItemMapper;

	@Autowired
	private EcmGroupUserMapper ecmGroupUserMapper;
	
	@Autowired
	private EcmDocumentMapper ecmDocumentMapper;

	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserService() {
		serviceCode = ServiceContext.USER_CODE;
		systemPermission = PermissionContext.SystemPermission.USER_GROUP_CONFIGE;
		logger.info("ServiceCode:" + serviceCode + ",systemPermission:" + systemPermission);
	}

	@Override
	public LoginUser authentication(EcmUser ecmUser) throws Exception {
		
		String loginPassword = ecmUser.getPassword();
		ecmUser = ecmUserMapper.selectByLoginName(ecmUser.getLoginName());
		if (ecmUser == null) {
			throw new Exception("User :" + ecmUser.getLoginName() + " is not exists.");
		}
		
		String password = ecmUser.getPassword();
		//logger.info(ecmUser.getLoginName()+" pwd:"+ password);
		if (password.length() < 16) {
			password = SecureUtils.md5Encode(password);
		}
		if(loginPassword.length()>32) {
			loginPassword = SecureUtils.decryptRSA(SecureUtils.PRIVATE_KEY, loginPassword);
		}
		//logger.info(ecmUser.getLoginName()+" login pwd:"+ loginPassword);
		if (loginPassword.length() < 16) {
			loginPassword = SecureUtils.md5Encode(loginPassword);
		}
		if (!password.equals(loginPassword)) {
			throw new Exception("Password wrong.");
		}
		if (!ecmUser.getIsActived()) {
			throw new Exception("The status of user is inactive.");
		}

		return getLoginUser(ecmUser);
	}

	private LoginUser getLoginUser(EcmUser ecmUser) {
		String ownerCompany = null;
		try {
			ownerCompany = CacheManagerOper.getEcmParameters().get("OwnerCompany").getValue();
		} catch (Exception ex) {

		}
		// 设置登录用户信息
		LoginUser cuser = new LoginUser();
		cuser.setUserId(ecmUser.getId());
		cuser.setUserName(ecmUser.getName());
		cuser.setLoginName(ecmUser.getLoginName());
		if (ownerCompany != null) {
			cuser.setUserType(ownerCompany.equals(ecmUser.getCompanyName()) ? 1 : 2);
		}
		cuser.setClientPermission(ecmUser.getClientPermission());
		cuser.setSystemPermission(ecmUser.getSystemPermission());
		cuser.setLoginTime(new Date());
		cuser.setCompany(ecmUser.getCompanyName());
		if(!StringUtils.isEmpty(ecmUser.getDepartmentName())) {
			cuser.setDepartment(ecmUser.getDepartmentName());
		}else {
			cuser.setDepartment(ecmUser.getGroupName());
		}
		cuser.setMyProjects(this.getMyProject(ecmUser.getLoginName()));
		bindUserRoles(cuser);
		bindUserComapny(cuser);
		return cuser;
	}
	
	private void bindUserComapny(LoginUser cuser) {
		try {
			if (cuser == null || StringUtils.isEmpty(cuser.getCompany())) {
				return;
			}
			String sql = "select NAME,CODING,C_CODE1 from ecm_document where TYPE_NAME='公司'  and NAME='"+cuser.getCompany()+"'";
			List<Map<String,Object>> list = ecmDocumentMapper.executeSQL(sql);
			if(list != null && list.size()>0) {
				cuser.setCompanyCode((String)list.get(0).get("CODING"));
				cuser.setCompanyCode1((String)list.get(0).get("C_CODE1"));
			}
		}
		catch(Exception ex) {
			
		}
	}

	private void bindUserRoles(LoginUser cuser) {
		if (cuser == null || cuser.getRoles() == null) {
			return;
		}
		List<EcmGroup> list = getUserGroupsById(null, cuser.getUserId());
		for (EcmGroup group : list) {
			cuser.getRoles().add(group.getName());
		}
	}

//	private void searchGroups(long id, List<EcmGroup> roles, List<String> roleNames) {
//		List<EcmGroup> groups = memberService.listMemberOf(id);
//		for(EcmGroup g:groups) {
//			searchGroups(g.getID(),roles,roleNames,memberService);
//			roles.add(g);
//			roleNames.add(g.getName());
//		}
//	}

//	/**
//	 * @Title loadMenuComAction   
//	 * @Description TODO(加载组件、菜单、事件)   
//	 * @param cuser
//	 * @param roleNames          
//	 * @author Haihong Rong
//	 * @date 2018年7月4日 下午12:34:13 
//	 */
//	private void loadMenuComAction(LoginUser cuser,List<String> roleNames){
//		//查询所有菜单
//		List<EcmMenu> menus = CacheManagerOper.getEcmMenuList();
//		//查询所有组件
//		Map<String,EcmComponent> components = CacheManagerOper.getEcmComponents();
//		//查询所有事件
//		Map<String,EcmAction> actions = CacheManagerOper.getEcmActions();
//		
//		EcmMenu tempM = null;
//		EcmComponent tempC = null;
//		EcmAction tempA = null;
//		
//		for(EcmMenu em:menus) {
//			tempM = em.clone();
//			cuser.getEcmMenus().add(tempM);
//			//如果菜单项角色字段为空，则有权限
//			if(tempM.getRoleName() == null || "".equals(tempM.getRoleName().trim())) {
//				tempM.setHasPermission(true);
//				continue;
//			}//如果菜单项角色字段等于当前用户角色，则有权限
//			else if(roleNames.contains(em.getRoleName())) {
//				tempM.setHasPermission(true);
//				continue;
//			}
//		}
//		
//		for(Map.Entry<String, EcmComponent> msc:components.entrySet()) {
//			tempC = msc.getValue().clone();
//			cuser.getEcmComponents().put(tempC.getName(), tempC);
//			//如果组件项角色字段为空，则有权限
//			if(tempC.getRoleName() == null || "".equals(tempC.getRoleName().trim())) {
//				tempC.setHasPermission(true);
//				continue;
//			}//如果组件项角色字段等于当前用户角色，则有权限
//			else if(roleNames.contains(tempC.getRoleName())){
//				tempC.setHasPermission(true);
//				continue;
//			}
//		}
//		
//		for(Map.Entry<String, EcmAction> mea:actions.entrySet()) {
//			tempA = mea.getValue().clone();
//			cuser.getEcmActions().put(tempA.getName(), tempA);
//			//如果事件项角色字段为空，则有权限
//			if(tempA.getRoleName() == null || "".equals(tempA.getRoleName().trim())) {
//				tempA.setHasPermission(true);
//				continue;
//			}//如果事件项角色字段等于当前用户角色，则有权限
//			else if(roleNames.contains(tempA.getRoleName())){
//				tempA.setHasPermission(true);
//				continue;
//			}
//		}		
//	}

	@Override
	public List<EcmUser> getUsers(String token, Pager pager, boolean noGroup, String condition) {
		String sql = "select * from ecm_user";
		
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " where (" + condition + ")";
		}
		if (noGroup) {
			if (sql.indexOf(" where ") > 0) {
				sql += " and (GROUP_NAME='' or GROUP_NAME is null)";
			} else {
				sql += " where GROUP_NAME='' or GROUP_NAME is null";
			}
		}
		List<EcmUser> list = ecmUserMapper.searchToEntity(pager, sql);
		return list;
	}
	
	public List<Map<String,Object>> getUserList(String token, Pager pager, boolean noGroup, String condition) {
		
		String sql="SELECT * FROM (" + 
				"	select id,name,description,LOGIN_NAME as loginName,phone,CREATION_DATE,CREATOR,email,MODIFIER,"
				+ "MODIFIED_DATE,IS_ACTIVED as isActived,GROUP_NAME as groupName,PASSWORD as password,GROUP_ID,LOGIN_TYPE,LDAP_CN,CLIENT_PERMISSION,"
				+ "SYSTEM_PERMISSION,LDAP_NAME,EXTEND_ID,EXTEND_GROUP_ID,SIGN_IMAGE,"
				+ "DELEGATE_USER as delegateUser,DELEGATE_START as delegateStart,DELEGATE_END as delegateEnd,COMPANY_NAME as companyName,"
				+ "projectName= STUFF((select projectName+',' from (" + 
				"select a.*,d.NAME as projectName from ecm_user a,ecm_group_user b,ecm_group c,ecm_document d" + 
				" where a.ID=b.USER_ID and b.GROUP_ID=c.ID and c.NAME=d.NAME and d.TYPE_NAME='项目'" + 
				") x where x.id=t.id for xml path('')), 1, 0, '') from(" + 
				"select a.*,d.NAME as projectName from ecm_user a,ecm_group_user b,ecm_group c,ecm_document d " + 
				"where a.ID=b.USER_ID and b.GROUP_ID=c.ID and c.NAME=d.NAME and d.TYPE_NAME='项目'" + 
				")t GROUP BY ID,NAME,DESCRIPTION,LOGIN_NAME,PHONE,CREATION_DATE,CREATOR,EMAIL,MODIFIER,MODIFIED_DATE,IS_ACTIVED,GROUP_NAME,PASSWORD,GROUP_ID,LOGIN_TYPE,LDAP_CN,CLIENT_PERMISSION,SYSTEM_PERMISSION,LDAP_NAME,EXTEND_ID,EXTEND_GROUP_ID,SIGN_IMAGE,DELEGATE_USER,DELEGATE_START,DELEGATE_END,COMPANY_NAME" + 
				") ta where 1=1 ";
		
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " and" + condition;
		}
		List<Map<String,Object>> list = ecmUserMapper.searchToMap(pager, sql);
		return list;
	}



	
	

	@Override
	public List<EcmUser> getGroupUsers(String token, String groupId) {
		String sql = "select a.* from ecm_user a, ecm_group_item b";

		sql += " where a.ID=b.CHILD_ID and b.ITEM_TYPE='2' and b.PARENT_ID='" + groupId;

		sql += "' order by NAME";

		List<EcmUser> list = ecmUserMapper.searchToEntity(sql);
		return list;
	}

	@Override
	public List<EcmUser> updateUserDepartment(String token, String userId, String groupId, String deptName)
			throws EcmException, AccessDeniedException, NoPermissionException {
		super.hasPermission(token, serviceCode + ObjectPermission.WRITE_ATTRIBUTE, systemPermission);
		String sql = "update ecm_user set GROUP_NAME='" + deptName + "',GROUP_ID='" + String.valueOf(groupId)
				+ "',MODIFIER='" + getSession(token).getCurrentUser().getUserName()
				+ "',MODIFIED_DATE=CURRENT_TIMESTAMP where ID='" + userId + "'";
		List<EcmUser> list = ecmUserMapper.searchToEntity(sql);
		return list;
	}

	@Override
	public boolean moveUserDepartment(String token, EcmUser en)
			throws EcmException, AccessDeniedException, NoPermissionException {
		super.hasPermission(token, serviceCode + ObjectPermission.WRITE_ATTRIBUTE, systemPermission);
		EcmUser oldEn = ecmUserMapper.selectByLoginName(en.getLoginName());
		EcmGroup g = ecmGroupMapper.selectByPrimaryKey(oldEn.getGroupId());
		String oldGroupId = oldEn.getGroupId();
		en.setGroupName(g.getName());
		ecmUserMapper.updateByPrimaryKey(en);
		String sqlStr = "update ecm_group_item set parent_id='" + en.getGroupId() + "' where parent_id='"
				+ String.valueOf(oldGroupId) + "' and child_id='" + en.getId() + "' and ITEM_TYPE='2'";
		ecmGroupItemMapper.executeSql(sqlStr);

		sqlStr = "delete from ecm_group_user where group_id='" + en.getGroupId() + "' and user_id='" + en.getId() + "'";
		ecmGroupUserMapper.executeSql(sqlStr);
		return true;
	}

	/**
	 * 
	 * @Title:获取当前用户项目
	 * @author Haihong Rong
	 * @date: 2020年7月8日 上午11:18:01
	 * @Description:
	 * @param loginName
	 * @return
	 */
	private List<String> getMyProject(String loginName) {
		List<String> result = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ed.ID,ed.CODING,ed.NAME from ecm_document ed where ed.TYPE_NAME='项目' and "
				+ "ed.NAME in (select eg.NAME from ecm_group eg "
				+ "where id in (select egu.group_id from ecm_group_user egu where egu.USER_ID in (select id from ecm_user where login_name='"
				+ loginName + "')))");
		List<Map<String, Object>> qList = ecmGroupUserMapper.executeSql(sql.toString());
		for (Map<String, Object> map : qList) {
			result.add(map.get("NAME").toString());
		}
		return result;
	}

	@Override
	public List<EcmUser> getObjects(String token, String condition) {
		String sql = "select * from ecm_user where " + condition;
		return ecmUserMapper.searchToEntity(sql);
	}

	@Override
	public EcmUser getObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmUser en = ecmUserMapper.selectByPrimaryKey(id);
		if (en == null) {
			return null;
		}
//		EcmUser user = (EcmUser) en;
//		IEcmSession session = getSession(token);
//		if (!session.getCurrentUser().getUserName().equals(user.getName())) {
//			super.hasPermission(token, serviceCode + ObjectPermission.WRITE_ATTRIBUTE, systemPermission);
//		}
		return en;
	}

	@Override
	public boolean updateObject(String token, Object en)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmUser user = (EcmUser) en;
		if (user.getPassword().length() < 30) {
			user.setPassword(SecureUtils.md5Encode(user.getPassword()));
		}					 
		IEcmSession session = getSession(token);
		if (!session.getCurrentUser().getUserName().equals(user.getName())) {
			super.hasPermission(token, serviceCode + ObjectPermission.WRITE_ATTRIBUTE, systemPermission);
		}
		return ecmUserMapper.updateByPrimaryKeySelective(user) > 0;
	}

	@Override
	public boolean deleteObject(String token, Object en)
			throws EcmException, AccessDeniedException, NoPermissionException {
		super.hasPermission(token, serviceCode + ObjectPermission.DELETE, systemPermission);
		ecmUserMapper.deleteByPrimaryKey(((EcmUser) en).getId());
		String sqlStr = "delete from ecm_group_item where child_id='" + ((EcmUser) en).getId() + "'";
		ecmGroupItemMapper.executeSql(sqlStr);
		return true;
	}

	@Override
	public String newObject(String token, Object en) throws EcmException, AccessDeniedException, NoPermissionException {
		super.hasPermission(token, serviceCode + ObjectPermission.WRITE_ATTRIBUTE, systemPermission);
		((EcmUser) en).setPassword(SecureUtils.shaEncode(((EcmUser) en).getPassword()));
		EcmUser u = (EcmUser) en;
		if (u.getId() == null || "".equals(u.getId())) {
			((EcmUser) en).createId();
		}

		ecmUserMapper.insert((EcmUser) en);
		String userId = ((EcmUser) en).getId();
		EcmUser newEn = ecmUserMapper.selectByLoginName(((EcmUser) en).getLoginName());
		if (((EcmUser) en).getGroupId() != null && ((EcmUser) en).getGroupId().length() > 0) {
			EcmGroupItem record = new EcmGroupItem();
			record.createId();
			record.setParentId(((EcmUser) en).getGroupId());
			record.setChildId(newEn.getId());
			record.setItemType("2");
			ecmGroupItemMapper.insert(record);
		}
		return userId;
	}

	@Override
	public String newObject(String token, Object en, InputStream instream, String fileName) throws Exception {
		super.hasPermission(token, serviceCode + ObjectPermission.WRITE_CONTENT, systemPermission);
		EcmUser user = (EcmUser) en;
		if (user.getId() == null || "".equals(user.getId())) {
			user.createId();
		}
		if (instream != null) {
			String fullPath = getFullPath(user, fileName);
			File file = new File(fullPath);
			BufferedOutputStream fis = new BufferedOutputStream(new FileOutputStream(file));
			try {
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = instream.read(buffer)) != -1) {
					fis.write(buffer, 0, bytesRead);
				}
			} finally {
				fis.close();
			}
		}
		ecmUserMapper.insert(user);
		return user.getId();
	}

	private String getFullPath(EcmUser en, String fileName) throws Exception {
		String storeName = CacheManagerOper.getEcmParameters().get("SignImageStore").getValue();
		String storePath = CacheManagerOper.getEcmStores().get(storeName).getStorePath();
		String fullPath = storePath;
		String os = System.getProperty("os.name").toLowerCase();
		String splitStr = os.startsWith("win") ? "\\" : "/";
		if (!fullPath.endsWith(splitStr)) {
			fullPath += splitStr;
		}
		fullPath += "sign";
		fullPath += splitStr;
		fileName = fullPath + en.getName() + "." + FileUtils.getExtention(fileName);
		en.setSignImage(fileName);
		Path path = Paths.get(fileName);
		if (!Files.isWritable(path)) {
			Files.createDirectories(Paths.get(fullPath));
		}
		return fileName;
	}

	@Override
	public boolean updateSignImage(String token, String id, InputStream instream, String fileName)
			throws EcmException, Exception {

		if (instream != null) {
			EcmUser user = getObjectById(token, id.replace("\"", ""));
			if (!getSession(token).getCurrentUser().getUserName().equals(user.getName())
					&& getSession(token).getCurrentUser().getClientPermission() < 4) {
				super.hasPermission(token, serviceCode + ObjectPermission.WRITE_CONTENT, systemPermission);
			}
			if (user != null) {
				String fullPath = getFullPath(user, fileName);
				File file = new File(fullPath);
				BufferedOutputStream fis = new BufferedOutputStream(new FileOutputStream(file));
				try {
					byte[] buffer = new byte[8 * 1024];
					int bytesRead;
					while ((bytesRead = instream.read(buffer)) != -1) {
						fis.write(buffer, 0, bytesRead);
					}
				} finally {
					fis.close();
				}
				user.setSignImage(fullPath);
				ecmUserMapper.updateByPrimaryKey(user);
			}
		} else {
			throw new EcmException("User Id:" + id + " isnot exists.");
		}
		return true;
	}

	@Override
	public List<EcmUser> getRoleUsers(String token, Pager pager, String noGroup, String groupId, String condition) {
		String sql = "select ID, NAME, DESCRIPTION, LOGIN_NAME, PHONE, CREATION_DATE, CREATOR, EMAIL, MODIFIER,"
				+ "MODIFIED_DATE, IS_ACTIVED, GROUP_NAME,COMPANY_NAME, DEPARTMENT_NAME, PASSWORD,CLIENT_PERMISSION,SYSTEM_PERMISSION from ecm_user a ";

		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " where (" + condition + ")";
		}
		if (!EcmStringUtils.isEmpty(noGroup) && "1".equals(noGroup)) {
			if (sql.indexOf(" where ") > 0) {
				if (!EcmStringUtils.isEmpty(groupId)) {
					sql += " and not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				} else {
					sql += " and not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and ITEM_TYPE=2)";
				}
			} else {
				if (!EcmStringUtils.isEmpty(groupId)) {
					sql += " where not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				} else {
					sql += " where not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID  and ITEM_TYPE=2)";
				}
			}
		} else {
			if (!EcmStringUtils.isEmpty(groupId)) {
				if (sql.indexOf(" where ") > 0) {
					sql += " and exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				} else {
					sql += " where exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				}
			}
		}
		sql += " order by NAME ";// limit "+ startIndex + ","+pageSize;
		List<EcmUser> list = ecmUserMapper.searchToEntity(pager, sql);
		return list;
	}

	@Override
	public List<EcmUser> getRoleAllUsers(String token, Pager pager, String groupId, String condition) {
		String sql = "select a.ID, a.NAME, a.DESCRIPTION, a.LOGIN_NAME, a.PHONE, a.CREATION_DATE, a.CREATOR, a.EMAIL, a.MODIFIER,"
				+ "a.MODIFIED_DATE, a.IS_ACTIVED, a.GROUP_NAME,a.COMPANY_NAME,a.DEPARTMENT_NAME, a.PASSWORD,a.CLIENT_PERMISSION,a.SYSTEM_PERMISSION from ecm_user a, ecm_group_user b "
				+ " where a.ID = b.USER_ID and b.GROUP_ID='" + DBFactory.getDBConn().getDBUtils().getString(groupId)
				+ "'";
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " and (" + condition + ")";
		}

		sql += " order by NAME ";// limit "+ startIndex + ","+pageSize;
		List<EcmUser> list = ecmUserMapper.searchToEntity(pager, sql);
		return list;
	}

	@Override
	public List<EcmUser> getUsersByGroupName(String token, Pager pager, String noGroup, String groupName,
			String condition) {
		String groupId = "";
		if (groupName.startsWith("leaderManage_auto")) {
			try {
				groupName = "leaderManage_" + getSession(token).getCurrentUser().getDepartment();
				EcmGroup groupObj = ecmGroupMapper.selectByName(groupName);
				groupId = groupObj == null ? "000000000000000000" : groupObj.getId();
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			}
		} else {
			EcmGroup groupObj = ecmGroupMapper.selectByName(groupName);
			groupId = groupObj == null ? null : groupObj.getId();
		}

		return getRoleUsers(token, pager, noGroup, groupId, condition);
	}

	@Override
	public long getRoleUserCount(String token, boolean noRole, String groupId, String condition) {
		String sql = "select count(*) as objcount from ecm_user a ";
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " where (" + condition + ")";
		}
		if (noRole) {
			if (sql.indexOf(" where ") > 0) {
				if (!EcmStringUtils.isEmpty(groupId)) {
					sql += " and not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				} else {
					sql += " and not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and ITEM_TYPE=2)";
				}
			} else {
				if (!EcmStringUtils.isEmpty(groupId)) {
					sql += " where not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				} else {
					sql += " where not exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID  and ITEM_TYPE=2)";
				}
			}
		} else {
			if (!EcmStringUtils.isEmpty(groupId)) {
				if (sql.indexOf(" where ") > 0) {
					sql += " and exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				} else {
					sql += " where exists(select 1 from ecm_group_item b where a.ID=b.CHILD_ID and b.PARENT_ID='"
							+ groupId + "' and ITEM_TYPE=2)";
				}
			}
		}
		List<Map<String, Object>> list = ecmUserMapper.searchToMap(sql);

		return (long) list.get(0).get("objcount");
	}

	@Override
	public long getUserCount(String token, boolean noGroup, String condition) {
		String sql = "select count(*) as objcount from ecm_user ";
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " where (" + condition + ")";
		}
		if (noGroup) {
			if (sql.indexOf(" where ") > 0) {
				sql += " and (GROUP_ID='' or GROUP_ID is null )";
			} else {
				sql += " where (GROUP_ID='' or GROUP_ID is null )";
			}
		}
		List<Map<String, Object>> list = ecmUserMapper.searchToMap(sql);

		return (long) list.get(0).get("objcount");
	}

	@Override
	public EcmUser getObjectByName(String token, String userName) {
		// TODO Auto-generated method stub
		String condition = "NAME='" + DBFactory.getDBConn().getDBUtils().getString(userName) + "'";
		List<EcmUser> list = getObjects(token, condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public EcmUser getObjectByLoginName(String token, String loginName) {
		// TODO Auto-generated method stub
		String condition = "LOGIN_NAME='" + DBFactory.getDBConn().getDBUtils().getString(loginName) + "'";
		List<EcmUser> list = getObjects(token, condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean updatePassword(String token, String userName, String password, String newPassword)
			throws EcmException, AccessDeniedException, NoPermissionException {

		EcmUser user = getObjectByName(token, userName);
		if (user == null) {
			return false;
		}
		String userPassword = user.getPassword();
		if (userPassword.length() > 16) {
			password = SecureUtils.md5Encode(password);
		}
		if (!password.equals(userPassword)) {
			throw new EcmException("Password wrong.");
		}
		String sql = "update ecm_user set PASSWORD='" + SecureUtils.md5Encode(newPassword) + "' where ID='"
				+ user.getId() + "'";
		List<Map<String, Object>> list = ecmUserMapper.searchToMap(sql);
		return list.size() > 0;
	}

	@Override
	public LoginUser authenticationSSO(EcmUser ecmUser) throws Exception {

		EcmUser loginUser = ecmUserMapper.selectByLoginName(ecmUser.getLoginName());
		if (loginUser == null) {
			throw new Exception("User :" + ecmUser.getLoginName() + " is not exists.");
		}
		if (loginUser.getSystemPermission() < SystemPermission.SUPER_USER) {
			logger.debug("User :" + ecmUser.getLoginName() + " login by sso");
		} 
		if (!loginUser.getIsActived()) {
			throw new Exception("The status of user is inactive.");
		}

		return getLoginUser(loginUser);
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		super.hasPermission(token, serviceCode + ObjectPermission.DELETE, systemPermission);
		ecmUserMapper.deleteByPrimaryKey(id);
		String sqlStr = "delete from ecm_group_item where child_id='" + DBFactory.getDBConn().getDBUtils().getString(id)
				+ "'";
		ecmGroupItemMapper.executeSql(sqlStr);
		return true;
	}

	@Override
	public List<EcmGroup> getUserGroupsById(String token, String userId) {

		String sql = "select a.* from ecm_group a, ecm_group_user b " + " where a.ID = b.GROUP_ID and b.USER_ID='"
				+ DBFactory.getDBConn().getDBUtils().getString(userId) + "'";

		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql);
		return list;
	}

	@Override
	public List<EcmGroup> getUserGroupsByName(String token, String userName) {

		String sql = "select a.* from ecm_group a, ecm_group_user b, ecm_user c where "
				+ " a.ID = b.GROUP_ID and b.USER_ID=c.ID and c.NAME='"
				+ DBFactory.getDBConn().getDBUtils().getString(userName) + "'";

		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql);
		return list;
	}

	@Override
	public boolean lockUser(String loginName) {

		String sql = "update ecm_user set IS_ACTIVED=0 where LOGIN_NAME='"
				+ DBFactory.getDBConn().getDBUtils().getString(loginName) + "'";

		return ecmUserMapper.searchToEntity(sql) != null;
	}
}
