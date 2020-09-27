package org.zisecm.jobs.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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
	
	public IEcmSession login() {
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");		
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ecmSession;
	}
	
	protected DocumentService getDocumentService() {
		return documentService;
	}
}
