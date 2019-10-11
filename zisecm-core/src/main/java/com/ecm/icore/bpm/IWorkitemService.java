package com.ecm.icore.bpm;

import java.util.List;

import com.ecm.core.entity.EcmActivity;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;

public interface IWorkitemService {

	List<EcmActivity> getNextActivities(String token, EcmActivity actObj, String docId, String formId);

	boolean startNextActivities(String token, List<EcmActivity> actList, String workflowId, String docId, String formId, String fromUser)
			throws EcmException;
	
	boolean openTask(String token, String queueId) throws EcmException, AccessDeniedException;

	boolean openTask(String token, EcmQueueItem queueItem) throws EcmException, AccessDeniedException;

	boolean completeTask(String token, EcmQueueItem queueItem, String result, String message) throws EcmException, AccessDeniedException;

	boolean completeTask(String token, String queueId, String result, String message) throws EcmException, AccessDeniedException;

}
