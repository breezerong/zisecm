package com.ecm.flowable.identity;

import java.util.ArrayList;
import java.util.List;

import org.flowable.idm.api.Group;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.flowable.idm.engine.impl.persistence.entity.data.GroupDataManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecm.core.entity.EcmGroup;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.GroupService;
import com.ecm.flowable.session.FlowableSession;;;

public class CustomGroupEntityManager extends GroupEntityManagerImpl {
	
	@Autowired
	private GroupService groupService;

	public CustomGroupEntityManager(IdmEngineConfiguration idmEngineConfiguration, GroupDataManager groupDataManager) {
		super(idmEngineConfiguration, groupDataManager);
	}

	@Override
	public GroupEntity findById(String entityId) {
		EcmGroup group=null;
		try {
			group = groupService.getGroup( new FlowableSession().getToken(), entityId);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		if (group.getId() == null) {
			return null;
		}
		GroupEntity groupEntity = new GroupEntityImpl();
		groupEntity.setId(group.getId());
		groupEntity.setName(group.getName());
		return groupEntity;
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query) {
//		groupService.get.getGroups(token, parentId, type, pager, condition)
//		List<SysRole> sysRoles = groupService.get.getBaseMapper().getRolesByFlowableGroupQueryImpl(query);
//		if (sysRoles == null || sysRoles.size() == 0) {
//			return new ArrayList<>();
//		}
//		List<Group> groups = new ArrayList<>();
//		for (SysRole sysRole : sysRoles) {
//			Group group = new GroupEntityImpl();
//			group.setId(sysRole.getRoleId());
//			group.setName(sysRole.getRoleName());
//			groups.add(group);
//		}
//		return groups;
		return new ArrayList<>();
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		return 1;
	}
}
