package com.ecm.cnpe.exchange.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.service.ProjectService;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.EcmService;
import com.ecm.icore.service.IEcmSession;

@Service
public class ProjectServiceImpl extends EcmService implements ProjectService{

	@Autowired
	private DocumentService documentService;
	
	@Override
	public List<ProjectEntity> getMyProject(String token) {
		List<ProjectEntity> result = new ArrayList<ProjectEntity>();
		try {
			StringBuffer sql = new StringBuffer();
			IEcmSession session = this.getSession(token);
			
			sql.append("select ed.ID,ed.CODING,ed.NAME from ecm_document ed where ed.TYPE_NAME='项目' and "
					+ "ed.NAME in (select eg.NAME from ecm_group eg "
					+ "where id in (select egu.group_id from ecm_group_user egu where egu.USER_ID in (select id from ecm_user where login_name='"+session.getCurrentUser().getLoginName()+"')))");
				
			
			List<Map<String, Object>> qList = documentService.getMapList(token, sql.toString());
			for (Map<String, Object> map : qList) {
				ProjectEntity item = new ProjectEntity();
				item.setCode(map.get("CODING").toString());
				item.setName(map.get("NAME").toString());
				result.add(item);
			}
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (EcmException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getMyProjectCondition(String prefix, String toke) {
		if(StringUtils.isEmpty(prefix)) {
			
		}else {
			
		}
		return null;
	}

	
	
}