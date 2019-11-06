package com.ecm.core.bpm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecm.core.DocumentContext;
import com.ecm.core.entity.EcmWorkflow;
import com.ecm.core.entity.EcmWorkitem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.bpm.IBPMServerEvent;

@Component
@Scope("prototype")
public class BPMServerEvent implements IBPMServerEvent {
	
	@Autowired
	private DocumentService documentService;
	
	@Override
	public void startWorkflow(String token, EcmWorkflow workflow) throws AccessDeniedException {
		//修改文档状态
		documentService.updateStatus(token, workflow.getDocId(), DocumentContext.STATUS_WORKFLOW);
	    //documentService.updateStatus(workflow.getFormId(), DocumentContext.STATUS_WORKFLOW);
	}

	@Override
	public void complateWorkflow(String token,EcmWorkflow workflow) throws AccessDeniedException {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list= documentService.getObjectMap(token, "ID='"+workflow.getFormId()+"'");
		//表单批准人
		String approver = "";
		if(list.size()>0&&list.get(0).get("C_APPROVER")!=null) {	
			approver = list.get(0).get("C_APPROVER").toString();
		}
		//修改文档状态
		documentService.updateStatus(token, workflow.getDocId(), DocumentContext.STATUS_APPROVED);
		
		if(approver!=null&&approver.length()>0) {
			String sql = "update ecm_document set C_APPROVER='"+approver+"' where ID='"+workflow.getDocId()+"'";
			try {
				documentService.executeSQL(token, sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//documentService.updateStatus(formId, DocumentContext.STATUS_APPROVED);
	}

	@Override
	public void doAutoMethod(String token,EcmWorkitem workitem) {
		// TODO Auto-generated method stub
		
	}
}
