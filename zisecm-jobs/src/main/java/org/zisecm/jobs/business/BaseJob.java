package org.zisecm.jobs.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.zisecm.jobs.config.EcmConfig;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;

public abstract class BaseJob {
	@Autowired
	private AuthService authService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private EcmConfig ecmConfig;
	
	public IEcmSession login() {
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = ecmConfig.getUsername();		
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, ecmConfig.getPassword());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ecmSession;
	}
	
	protected List<Map<String,Object>> getData(String token,String sql) {
		List<Map<String,Object>> result = null;
		try {
			result = this.documentService.getMapList(token, sql);
		} catch (EcmException e) {
			e.printStackTrace();
			result = new ArrayList<>();
		}
		
		return result;
	}
	
	protected void update(String token,String id,String field,Object value) {
		EcmDocument doc = this.documentService.getObjectById(token, id);
		doc.addAttribute(field, value);
		try {
			this.documentService.creatOrUpdateObject(token, doc, null);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	protected DocumentService getDocumentService() {
		return documentService;
	}
}
