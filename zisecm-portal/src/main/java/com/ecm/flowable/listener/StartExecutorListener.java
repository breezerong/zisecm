package com.ecm.flowable.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.flowable.engine.HistoryService;
import javax.sql.DataSource;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.ecm.core.PermissionContext;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.UserService;
import com.ecm.icore.service.IEcmSession;
import com.ecm.portal.service.ServiceDocMail;
import com.ecm.portal.test.flowable.TODOApplication;

@Component(value = "startExecutorListener")
public class StartExecutorListener implements ExecutionListener, JavaDelegate, TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocumentService documentService;

	@Autowired
	private AuthService authService;

	@Autowired
	private ServiceDocMail serviceDocMail;
	@Autowired
	private Environment env;

	/**
	 * 监听 for executionListener
	 */
	@Override
	public void notify(DelegateExecution arg0) {
		if (arg0.getVariable("processInstanceID") == null) {
			arg0.setVariable("processInstanceID", arg0.getProcessInstanceId());
			arg0.setVariable("processName", arg0.getProcessDefinitionId().split(":")[0]);
		}
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("spring.datasource.username");
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("spring.datasource.password"));
			extracted(ecmSession, arg0);
//			System.out.println("========name======="+arg0.getEventName()+"===isEnded==="+arg0.isEnded());
			//流程结束发送邮件
			if("end".equals(arg0.getEventName())) {
				try {
					HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery()
					        .processInstanceId(arg0.getProcessInstanceId())
					        .singleResult();
					String startUserId = hi.getStartUserId();
//					System.out.println("userId===="+startUserId);
					EcmUser user= userService.getObjectByName(ecmSession.getToken(), startUserId);
					String email= user.getEmail();
					if(email!=null&&!"".equals(email)) {
						serviceDocMail.sendEndMail(email);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//end
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (ecmSession != null) {
				authService.logout(workflowSpecialUserName);
			}
		}
		System.out.println("DelegateExecution_notify");
	}

	private Map<String, Object> extracted(IEcmSession ecmSession,
			org.flowable.variable.api.delegate.VariableScope arg0) {
		// 查询借阅流程中的表单变量
		// 添加业务逻辑解析表单或变量，执行判断操作
		// 如果纸质节约且内部公开，设置input=纸质借阅&内部公开 否则设置 input= 否
		// DocumentService documentService= SpringUtil.getBean(DocumentService.class);
		// AuthService authService=SpringUtil.getBean(AuthService.class);

		Map<String, Object> varMap = arg0.getVariables();
		String formId = varMap.get("formId").toString();
		try {
			EcmDocument ecmObject = documentService.getObjectById(ecmSession.getToken(), formId);

			// 初始化变量栈
			varMap.put("borrowType", ecmObject.getSubType());

			// 是否本部门文档
			varMap.put("departmentDoc",
					ecmObject.getAttributes().get("C_CREATION_UNIT").equals(ecmObject.getAttributes().get("C_DESC1")));

			// 批次文件是否有商密文件
			String fileTopestSecurityLevel = varMap.get("fileTopestSecurityLevel") == null ? ""
					: varMap.get("fileTopestSecurityLevel").toString();

			// 图纸或文件借阅数量
			int drawingNumber = varMap.get("drawingNumber") == null ? 0
					: Integer.valueOf(varMap.get("drawingNumber").toString());
			int fileNumber = varMap.get("fileNumber") == null ? 0
					: Integer.valueOf(varMap.get("fileNumber").toString());

			varMap.put("beyondLeaderPermision", false);
			switch (fileTopestSecurityLevel) {
			case "普通商密":
			case "核心商密":
				// 20个图册或100个文件以上
				if (drawingNumber > 20 || fileNumber > 100) {
					varMap.put("beyondLeaderPermision", true);
				}
				break;

			case "受限":
				// 30个图册或150个文件以上
				if (drawingNumber > 30 || fileNumber > 150) {
					varMap.put("beyondLeaderPermision", true);
				}
				break;

			default:
				varMap.put("beyondLeaderPermision", false);
				break;
			}

			varMap.put("securityLevel", fileTopestSecurityLevel);
			String process_name = arg0.getVariable("processName").toString();
			switch (process_name) {
			case "process_borrow":
				// runtimeService.getVariables("1995ac1d-1259-11ea-9171-00505622af9b")
				varMap.put("taskUser_owner",
						runtimeService.getVariable(arg0.getVariable("processInstanceID").toString(), "startUser"));
				varMap.put("taskUser_owner_leader", ecmObject.getAttributes().get("C_REVIEWER1"));
				varMap.put("taskUser_doc_leader", ecmObject.getAttributes().get("C_REVIEWER2"));
				varMap.put("taskUser_leader_in_charge", ecmObject.getAttributes().get("C_REVIEWER3"));
				break;

			default:
				break;
			}
			arg0.setTransientVariables(varMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return varMap;
	}

	/**
	 * JavaDelegate 方法 for serviceTask
	 */
	@Override
	public void execute(DelegateExecution arg0) {
		if (arg0.getVariable("processInstanceID") == null) {
			arg0.setVariable("processInstanceID", arg0.getProcessInstanceId());
			arg0.setVariable("processName", arg0.getProcessDefinitionId().split(":")[0]);
		}
		
//		System.out.println("===isEnded==="+arg0.isEnded()+"===flowElement===="+arg0.getCurrentFlowElement()+"=====eventName==="+arg0.getEventName());
		
		
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("spring.datasource.username");
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("spring.datasource.password"));
			Map<String, Object> varMap = extracted(ecmSession, arg0);
			String processName = varMap.get("processName").toString();
			if ("process_borrow".equals(processName)) {
				String flowElementId = arg0.getCurrentFlowElement().getId();
				String formId = varMap.get("formId").toString();
				List<Map<String, Object>> childList = null;
				String sql = "select a.ID as RELATE_ID ,b.ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX,b.NAME,b.CODING,b.C_SECURITY_LEVEL,b.REVISION,b.TITLE,b.CREATOR,b.TYPE_NAME,b.SUB_TYPE,b.CREATION_DATE,b.C_ARCHIVE_DATE,b.C_ARCHIVE_UNIT"
						+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID " + " and a.PARENT_ID='"
						+ formId + "'  and a.NAME='irel_borrow' ";
				childList = documentService.getMapList(ecmSession.getToken(), sql);
				Map<String, Object> formObj = documentService.getObjectMapById(ecmSession.getToken(), formId);
				Date grantDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formObj.get("C_END_DATE").toString());
				
				if ("automaticAuthorization".equals(flowElementId)) {
					String userName=varMap.get("startUser").toString();
					int permit=PermissionContext.ObjectPermission.READ;
					if ("在线浏览".equals(varMap.get("borrowType").toString())){
						permit=PermissionContext.ObjectPermission.READ;
					}else if("下载".equals(varMap.get("borrowType").toString())) {
						permit=PermissionContext.ObjectPermission.DOWNLOAD;
					}
					for (int i = 0; i < childList.size(); i++) {
						EcmDocument docObj = documentService.getObjectById(ecmSession.getToken(),
								childList.get(i).get("CHILD_ID").toString());
						documentService.grantUser(ecmSession.getToken(), docObj, userName,permit, grantDate, true);
						grantRelationPermit(ecmSession,docObj, "irel_children", userName, grantDate, permit);
					}
					documentService.updateStatus(ecmSession.getToken(), formId, "已完成");
				} else if ("updateStatus".equals(flowElementId)) {
					for (int i = 0; i < childList.size(); i++) {
						documentService.updateStatus(ecmSession.getToken(), formId, "待出库");
						documentService.updateStatus(ecmSession.getToken(), childList.get(i).get("CHILD_ID").toString(), "待出库");
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (ecmSession != null) {
				authService.logout(workflowSpecialUserName);
			}
		}
		System.out.println("DelegateExecution_execute");
	}

	private void grantRelationPermit(IEcmSession ecmSession,EcmDocument docObj, String relationName, String userName, Date grantDate, int permit)
			throws EcmException, AccessDeniedException, NoPermissionException, ParseException {
		if("图册".equals(docObj.getTypeName())) {
			String sqlSub = "select a.ID as RELATE_ID ,b.ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX,b.NAME,b.CODING,b.C_SECURITY_LEVEL,b.REVISION,b.TITLE,b.CREATOR,b.TYPE_NAME,b.SUB_TYPE,b.CREATION_DATE,b.C_ARCHIVE_DATE,b.C_ARCHIVE_UNIT"
					+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID " + " and a.PARENT_ID='"
					+ docObj.getId() + "' and a.NAME='"+ relationName+"' ";
			List<Map<String, Object>>  childSubList = documentService.getMapList(ecmSession.getToken(), sqlSub);
			for (int j = 0; j < childSubList.size(); j++) {
				EcmDocument docObjSub = documentService.getObjectById(ecmSession.getToken(),
						childSubList.get(j).get("CHILD_ID").toString());
				documentService.grantUser(ecmSession.getToken(), docObjSub, userName,permit,
				grantDate,
				true);
			}
		}
	}

	/**
	 * TaskListener 方法 for taskListener
	 */
	@Override
	public void notify(DelegateTask arg0) {
		if("create".equals(arg0.getEventName())){
			///////////////////////任务到达发送邮件//////////////
			String assignee=arg0.getAssignee();//ecm_user.Name
			IEcmSession ecmSession = null;
			String workflowSpecialUserName = env.getProperty("spring.datasource.username");
			
			try {
				ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("spring.datasource.password"));
				EcmUser user= userService.getObjectByName(ecmSession.getToken(), assignee);
				String email= user.getEmail();
				if(email!=null&&!"".equals(email)) {
					serviceDocMail.sendTaskMail(email);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (ecmSession != null) {
					authService.logout(workflowSpecialUserName);
				}
			}
			////////////////////end/////////////////
//			arg0.getName();
			//发邮件
//			TODOApplication.getNeedTOChange();
		}else {
			if (arg0.getVariable("processInstanceID") == null) {
				arg0.setVariable("processInstanceID", arg0.getProcessInstanceId());
				arg0.setVariable("processName", arg0.getProcessDefinitionId().split(":")[0]);
			}
			IEcmSession ecmSession = null;
			String workflowSpecialUserName = env.getProperty("spring.datasource.username");
			try {
				ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("spring.datasource.password"));
				extracted(ecmSession, arg0);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (ecmSession != null) {
					authService.logout(workflowSpecialUserName);
				}
			}

		}
		System.out.println("DelegateTask_notify");

	}
}
