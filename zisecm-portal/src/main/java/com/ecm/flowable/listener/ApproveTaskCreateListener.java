package com.ecm.flowable.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.variable.api.persistence.entity.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component(value="approveTaskCreateListener")
public class ApproveTaskCreateListener implements TaskListener {

	
  	@Autowired
   private DocumentService documentService;
  	@Autowired
   private AuthService authService;
  	private String token;
	 
    @Override
    public void notify(DelegateTask delegateTask) {
    	//查询借阅流程中的表单变量
    	//添加业务逻辑解析表单或变量，执行判断操作
    	//如果纸质节约且内部公开，设置input=纸质借阅&内部公开 否则设置 input= 否
//    	DocumentService documentService= SpringUtil.getBean(DocumentService.class);
//    	AuthService authService=SpringUtil.getBean(AuthService.class);

    	Map<String, VariableInstance>  varMap = delegateTask.getVariableInstances();
    	String formId= varMap.get("formId").getTextValue();
    	String userName=null;
    	try {
    		IEcmSession ecmSession=authService.login("workflow","admin","admin");
    		userName=ecmSession.getCurrentUser().getUserName();
			token = ecmSession.getToken();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	EcmDocument  ecmObject = documentService.getObjectById(token, formId);
       	//初始化变量栈
    	HashMap<String, Object> map = new HashMap<>();
        map.put("borrowType", ecmObject.getSubType());
        map.put("securityLevel", ecmObject.getSecurityLevel());
        map.put("departmentDoc", true);
        map.put("needDownload", true);
        map.put("beyondLeaderPermision", true);
        map.put("securityLevel", ecmObject.getSecurityLevel());
        map.put("Task_review", "reject");
        map.put("taskUser_owner", "Admin");
        map.put("taskUser_owner_leader", "Admin");
        map.put("taskUser_doc_leader", "Admin");
        map.put("taskUser_leader_in_charge", "Admin");
        
        //map 需要从借阅表单ecm.document.type_name="借阅单" +  流程记录里取
        //流程记录中记录了表单ID
        //
 
    	String  process_name=delegateTask.getProcessDefinitionId().split(":")[0];
    	if("process_borrow".equals(process_name)) {//借阅流程
    			delegateTask.setTransientVariables(map);
    	}
    	
    }
}
