package com.ecm.portal.archive.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.GroupService;
import com.ecm.core.service.UserService;

@Service
public class DepartmentServiceEx {
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupServer;
	
	public String updateDeparement(String token) {
		Pager pager =new Pager();
		pager.setPageIndex(0);
		pager.setPageSize(1000);
		List<EcmGroup> list = groupServer.getGroups(token, "root", "1", pager, null);
		if(list.size()<1) {
			return "获取分公司ID失败";
		}
		int updateUser =0;
		try {
			String hbId = list.get(0).getId();
			String hbName = list.get(0).getName();
			//获取部门
			list = groupServer.getGroups(token, hbId, "1", pager, null);
			for(EcmGroup branch: list) {
				String deptName = branch.getName();
				//获取科室
				List<EcmGroup> listChild = groupServer.getGroups(token, branch.getId(), "1", pager, null);
				for(EcmGroup child: listChild) {
					List<EcmUser> ulist = groupServer.getAllUser(token, child.getId());
					for(EcmUser user: ulist) {
						updateUser ++;
						//科室用户组为部门名称
						if(!deptName.equals(user.getGroupName())){
							user.setGroupName(deptName);
							user.setGroupId(branch.getId());
							userService.updateObject(token, user);
						}
					}
				}
				//更新部门用户部门名称
				List<EcmUser> ulist = groupServer.getAllUser(token, branch.getId());
				for(EcmUser user: ulist) {
					updateUser ++;
					if(!deptName.equals(user.getGroupName())){
						user.setGroupName(deptName);
						user.setGroupId(branch.getId());
						userService.updateObject(token, user);
					}
				}
			}
			//直接在公司的用户部门名称为公司名称
			List<EcmUser> ulist = groupServer.getAllUser(token, hbId);
			for(EcmUser user: ulist) {
				updateUser ++;
				if(!hbName.equals(user.getGroupName())){
					user.setGroupName(hbName);
					user.setGroupId(hbId);
					userService.updateObject(token, user);
				}
			}
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "更新用户："+updateUser;
	}
	
}
