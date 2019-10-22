package com.ecm.core.bpm;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.dao.EcmWorkflowMapper;
import com.ecm.core.entity.EcmActivity;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.entity.EcmProcess;
import com.ecm.core.entity.EcmWorkflow;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.IWorkflowService;
@Component
@Scope("prototype")
public class WorkflowService extends EcmObjectService<EcmWorkflow> implements IWorkflowService {

	@Autowired
	private EcmWorkflowMapper ecmWorkflowMapper;
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private WorkitemService workitemService;
	
	@Autowired
	private BPMServerEvent serverEvent;
	
	@Autowired
	private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
	/**
	 * 根据流程定义ID发起流程
	 * @param processId
	 * @param name
	 * @param description
	 * @param docId
	 * @param formId
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 */
	@Override
	public boolean startWorkflow(String token, String processId,String name,String description,String docId,String formId) throws EcmException, AccessDeniedException {
		
		return startWorkflow(token, processService.getObjectById(token,processId), name, description, docId, formId);
	}
	/**
	 * 根据流程定义发起流程
	 * @param process
	 * @param name
	 * @param description
	 * @param docId
	 * @param formId
	 * @return
	 * @throws EcmException 
	 * @throws AccessDeniedException 
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean startWorkflow(String token, EcmProcess process,String name,String description,String docId,String formId) throws EcmException, AccessDeniedException {
		String userName = getSession(token).getCurrentUser().getUserName();
		//根据模板创建流程实例
		EcmWorkflow wfEn = new EcmWorkflow();
		wfEn.createId();
		wfEn.setName(name);
		wfEn.setCreator(userName);
		wfEn.setCreationDate(new Date());
		wfEn.setDocId(docId);
		wfEn.setFormId(formId);
		wfEn.setProcessId(process.getId());
		wfEn.setDescription(description);
		wfEn.setStatus(1);
		ecmWorkflowMapper.insert(wfEn);
		String wfId = wfEn.getId();
		//获取第一个活动，开始活动的下一个,可能多个。
		EcmActivity startAct =  getStartArctivity(process);
		
		List<EcmActivity> actList = workitemService.getNextActivities(token, startAct, docId, formId);
		//根据活动创建任务实例。
		workitemService.startNextActivities(token, actList, wfId, docId, formId, userName);
		
		//调用流程开始事件
		serverEvent.startWorkflow(token, wfEn);
		
		//创建流程日志
		EcmAuditWorkflow audit = new EcmAuditWorkflow();
		audit.createId();
		audit.setWorkflowId(wfId);
		audit.setWorkflowName(name);
		audit.setProcessName(process.getName());
		audit.setCreator(userName);
		audit.setStartDate(new Date());
		audit.setDocId(docId);
		audit.setFormId(formId);
		ecmAuditWorkflowMapper.insert(audit);
		return true;
	}
	
	private EcmActivity getStartArctivity(EcmProcess process) {
		for(EcmActivity act:process.getActivities()) {
			//类型，0：开始，1：人工任务，2：自动任务,99：结束
			if(act.getItemType()==0) {
				return act;
			}
		}
		return null;
	}
}
