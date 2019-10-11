package com.ecm.icore.bpm;

import com.ecm.core.entity.EcmProcess;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;

public interface IWorkflowService {


	boolean startWorkflow(String token, EcmProcess process, String name, String description, String docId, String formId)
			throws EcmException, AccessDeniedException;

	boolean startWorkflow(String token, String processId, String name, String description, String docId, String formId) throws EcmException, AccessDeniedException;

}
