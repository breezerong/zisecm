package com.ecm.flowable.listener;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.variable.api.persistence.entity.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;
import com.ecm.portal.SpringUtil;

@Component(value="startExecutorListener")
public class StartExecutorListener  implements ExecutionListener  {
  	@Autowired
    private RuntimeService runtimeService;
	
  	@Autowired
   private DocumentService documentService;
  	@Autowired
   private AuthService authService;
  	private String token;
	 
    @Override
    public void notify(DelegateExecution delegateExecution) {
    	//查询借阅流程中的表单变量
    	//添加业务逻辑解析表单或变量，执行判断操作
    	//如果纸质节约且内部公开，设置input=纸质借阅&内部公开 否则设置 input= 否
		//DocumentService documentService= SpringUtil.getBean(DocumentService.class);
		//AuthService authService=SpringUtil.getBean(AuthService.class);

    	Map<String, Object>  varMap = delegateExecution.getVariables();
    	String formId= varMap.get("formId").toString();
    	String workflowSpecialUserName="admin";
    	IEcmSession ecmSession=null;
    	try {
    		 ecmSession=authService.login("workflow",workflowSpecialUserName,"admin");
			EcmDocument  ecmObject = documentService.getObjectById(token, formId);
 
    	
    	
	    	//初始化变量栈
	        varMap.put("borrowType", ecmObject.getSubType());
	        varMap.put("securityLevel", ecmObject.getSecurityLevel());
	        
	        
	        //是否本部门文档
	        //TODO liao
	        varMap.put("departmentDoc", ecmObject.getAttributeValue("C_CREATION_UNIT").equals(ecmObject.getAttributeValue("C_DESC1")));
	        
	        
	        //批次文件是否有商密文件
	        // TODO liao
	        String  fileTopestSecurityLevel="受限";
	
	        
	        switch (fileTopestSecurityLevel) {
			case "普通商密":
			case "核心商密":
		        varMap.put("beyondLeaderPermision", true);
				//20个图册或100个文件以上
				
				break;
			case "受限":
		        varMap.put("beyondLeaderPermision", true);
				//30个图册或150个文件以上
	
				break;
	
			default:
		        varMap.put("beyondLeaderPermision", false);
				break;
			}
	        varMap.put("beyondLeaderPermision", true);
	        varMap.put("securityLevel", ecmObject.getSecurityLevel());
	        
	        //map 需要从借阅表单ecm.document.type_name="借阅单" +  流程记录里取
	        //流程记录中记录了表单ID
	        //
	 
	    	String  process_name=delegateExecution.getProcessDefinitionId().split(":")[0];
	    	if("process_borrow".equals(process_name)) {//借阅流程
	            varMap.put("taskUser_owner", runtimeService.getVariable(delegateExecution.getProcessInstanceId(), "startUser"));
	            varMap.put("taskUser_owner_leader", ecmObject.getAttributeValue("C_REVIEWER1"));
	            varMap.put("taskUser_doc_leader", ecmObject.getAttributeValue("C_REVIEWER2"));
	            varMap.put("taskUser_leader_in_charge", ecmObject.getAttributeValue("C_REVIEWER3"));
	
	            delegateExecution.setTransientVariables(varMap);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			  if(ecmSession!=null) {
				  authService.logout(workflowSpecialUserName);
			  }
		} 
  
    }
}
