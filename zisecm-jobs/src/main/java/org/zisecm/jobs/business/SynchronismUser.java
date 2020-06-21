package org.zisecm.jobs.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmGroupItem;
import com.ecm.core.entity.EcmGroupUser;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.EcmGroupItemService;
import com.ecm.core.service.EcmGroupUserService;
import com.ecm.core.service.GroupService;
import com.ecm.core.service.UserService;
import com.ecm.icore.service.IEcmSession;

@Service
public class SynchronismUser {
	@Autowired
	private Environment env;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private AuthService authService;
	@Autowired
	private EcmGroupUserService ecmGroupUserService;
	@Autowired
	private EcmGroupItemService ecmGroupItemService;
	@Autowired
	private DeptUpdate deptUpdate;
	/**
	 * 同步部门
	 */
	public void synchronismDept() {
		String oaSql="SELECT UG_UserGrpID,UG_UserGrpCode,UG_UserGrpName,UG_UserGrpParentID,UG_IsDel FROM LZ_MISDepartment WHERE UG_UserGrpID!='root'";
	
		List<Map<String,String>> depts= getOaData(oaSql,new String[] {"UG_UserGrpID","UG_UserGrpCode","UG_UserGrpName","UG_UserGrpParentID","UG_IsDel"});
		String workflowSpecialUserName = env.getProperty("ecm.username");
		IEcmSession ecmSession = null;
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
		
			for(Map<String,String> dept:depts) {
				String deptisDel=dept.get("UG_IsDel").toString();
				String deptId=dept.get("UG_UserGrpID").toString();
				String deptCode=dept.get("UG_UserGrpCode").toString();
				String deptName=dept.get("UG_UserGrpName").toString();
				String deptParentID=dept.get("UG_UserGrpParentID").toString();
				
				
				EcmGroup g= groupService.getGroup(ecmSession.getToken(), deptId);
				if(g==null) {
					if("1".equals(deptisDel)) {
						//不做处理
						continue;
					}else {
						//创建组
						EcmGroup ng=new EcmGroup();
						ng.setId(deptId);
						ng.setCoding(deptCode);
						ng.setName(deptName);
						ng.setParentId(deptParentID);
						groupService.newGroup(ecmSession.getToken(), ng);
					}
				}else{
					if("1".equals(deptisDel)) {
						//删除
						groupService.deleteGroup(ecmSession.getToken(), deptId);
						
					}else if(!deptCode.equals(g.getCoding())||
							!deptName.equals(g.getName())||
							!deptParentID.equals(g.getParentId())) {
						//修改
						g.setCoding(deptCode);
						g.setName(deptName);
						g.setParentId(deptParentID);
						groupService.updateGroup(ecmSession.getToken(), g);
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(ecmSession!=null) {
				authService.logout(ecmSession.getToken());
			}
		}
	
	
	}
	/**
	 * 同步用户
	 */
	public void synchronismUsers() {
		String oaSql="SELECT a.SU_UserID,a.SU_UserCode,a.SU_UserName,a.SU_LoginName,a.SU_IsDel,c.UG_UserGrpName FROM LZ_MISUser a,LZ_MISDepartmentUser b,LZ_MISDepartment c " + 
				"WHERE a.SU_UserID=b.SU_UserID AND b.UG_UserGrpID=c.UG_UserGrpID";
		List<Map<String,String>> oaUserData= getOaData(oaSql,new String[] {"SU_UserID","SU_UserCode","SU_UserName","SU_LoginName","SU_IsDel","UG_UserGrpName"});
		String workflowSpecialUserName = env.getProperty("ecm.username");
		IEcmSession ecmSession = null;
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
		
			for(Map<String,String> row:oaUserData) {
				try {
					String uId=row.get("SU_UserID");
					String code=row.get("SU_UserCode");
					String name=row.get("SU_UserName");
					String loginName=row.get("SU_LoginName");
					String isdel=row.get("SU_IsDel");
					String groupName=row.get("UG_UserGrpName");
					EcmUser user= userService.getObjectById(ecmSession.getToken(), uId);
					if(user==null) {
						if("1".equals(isdel)) {
							continue;
						}else {
							user=new EcmUser();
							String validataName=" name ='"+name+"'";
							long countUser= userService.getUserCount(ecmSession.getToken(), false, validataName);
							if(countUser>0) {
								name=name+loginName.substring(loginName.length()-1, loginName.length());
							}
							
							user.setId(uId);
							user.setLdapCn(code);
							user.setName(name);
							user.setPassword("ecm1234");
							user.setLoginName(loginName);
							user.setIsActived(true);
							//
							user.setEmail(loginName+"@cnpe.cc");
							user.setGroupName(groupName);
							userService.newObject(ecmSession.getToken(), user);
						}
					}else {
						if("1".equals(isdel)) {
							user.setIsActived(false);
							userService.updateObject(ecmSession.getToken(), user);
						}else if(!code.equals(user.getLdapCn())
								||!loginName.equals(user.getLoginName())
								||!true==user.getIsActived()
								||!groupName.equals(user.getGroupName())) {
							
							user.setLdapCn(code);
							user.setLoginName(loginName);
							user.setIsActived(true);
							user.setEmail(loginName+"@cnpe.cc");
							user.setGroupName(groupName);
							userService.updateObject(ecmSession.getToken(), user);
						}
					}
				} catch (EcmException | AccessDeniedException | NoPermissionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(ecmSession!=null) {
				authService.logout(ecmSession.getToken());
			}
		}
	
	}
	/**
	 * 同步用户组关系
	 * @throws EcmException
	 */
	public void synchronismUserGrop() throws EcmException {
		String sql="select UG_UserGrpID,SU_UserID from LZ_MISDepartmentUser";
		List<Map<String,String>> relationDatas= getOaData(sql,new String[] {"UG_UserGrpID","SU_UserID"});
		for(Map<String,String> row:relationDatas ) {
			String groupId= row.get("UG_UserGrpID");
			String userId=row.get("SU_UserID");
			String searchSql="select * from ecm_group_user where GROUP_ID='"+groupId+"' AND USER_ID='"+userId+"'";
			List<Map<String,Object>> docData= ecmGroupUserService.getListMap(searchSql);
			if(docData==null||docData.size()==0||docData.get(0)==null) {
				//创建
				EcmGroupUser egu=new EcmGroupUser();
				egu.setGroupId(groupId);
				egu.setUserId(userId);
				egu.createId();
				ecmGroupUserService.newObj(egu);
				EcmGroupItem egi=new EcmGroupItem();
				egi.setParentId(groupId);
				egi.setChildId(userId);
				egi.setItemType("2");
				egi.createId();
				ecmGroupItemService.newObj(egi);
			}
		}
	}
	
	
	
	/**
	 * 链接OA数据库取数据
	 * @param sql
	 * @param columns
	 * @return
	 */
	public List<Map<String,String>> getOaData(String sql,String[] columns) {
		PreparedStatement ps=null;
		Connection ct=null;
		ResultSet rs=null;
		try{
//			加载驱动（作用是把需要的驱动程序加入内存）		
			Class.forName(env.getProperty("oa.db.driverclass"));
			
//			得到连接（数据源，用户名，密码）
			ct=DriverManager.getConnection(env.getProperty("oa.db.url"),
					env.getProperty("oa.db.username"),
					env.getProperty("oa.db.password"));

//			创建Statemet  和 preparedStatemet (区别)
			/*ps=ct.prepareStatement("select * from family");//主要用于取出数据*/
			
			ps=ct.prepareStatement(sql);
			
//			执行:增加，删除，修改，使用executeUdate
//				查询，使用executeQuery
			rs=ps.executeQuery();
			List<Map<String,String>> results=new ArrayList<>();
			
			while(rs.next()) {
				Map<String,String> row=new HashMap<String, String>();
				for(String c:columns) {
					row.put(c, rs.getString(c));
				}
				results.add(row);
				
			}
			return results;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)
				ps.close();
				if(ct!=null)
				ct.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void run() {
		String workflowSpecialUserName = env.getProperty("ecm.username");
		IEcmSession ecmSession = null;
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
			this.synchronismDept();
			this.synchronismUsers();
			this.synchronismUserGrop();
			
			this.deptUpdate.updateDeparement(ecmSession.getToken());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(ecmSession!=null) {
				authService.logout(ecmSession.getToken());
			}
		}
	}
}
