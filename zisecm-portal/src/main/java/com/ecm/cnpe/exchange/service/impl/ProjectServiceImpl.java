package com.ecm.cnpe.exchange.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.service.ProjectService;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.EcmService;
import com.ecm.icore.service.IEcmSession;

@Service
public class ProjectServiceImpl extends EcmService implements ProjectService{

	@Override
	public List<ProjectEntity> getMyProject(String token) {
		try {
			IEcmSession session = this.getSession(token);
			
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getMyProjectCondition(String prefix, String toke) {
		if(StringUtils.isEmpty(prefix)) {
			
		}else {
			
		}
		return null;
	}

	
	
}
