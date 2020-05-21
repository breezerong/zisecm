package com.ecm.flowable.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.common.Strings;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ecm.common.util.DateUtils;
import com.ecm.core.PermissionContext;
import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.GroupService;
import com.ecm.core.service.UserService;
import com.ecm.flowable.service.CustomWorkflowService;
import com.ecm.icore.service.IEcmSession;
import com.ecm.portal.service.ServiceDocMail;

@Component(value = "StartBJSPExecutorListener")
public class StartBJSPExecutorListener implements ExecutionListener, JavaDelegate, TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CustomWorkflowService customWorkflowService;

	@Autowired
	private HistoryService historyService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private AuthService authService;

	@Autowired
	private ServiceDocMail serviceDocMail;
	@Autowired
	private Environment env;
	@Autowired
	private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;

	/**
	 * 监听 for executionListener
	 */
	@Override
	public void notify(DelegateExecution execution) throws FlowableException {
		if (execution.getVariable("processInstanceID") == null) {
			execution.setVariable("processInstanceID", execution.getProcessInstanceId());
			execution.setVariable("processName", execution.getProcessDefinitionId().split(":")[0]);
		}

		setPassedTaskCount(execution);
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
			loadProcessBusinessLogicToVariable(ecmSession, execution);
			sendMailOfProcessEnd(ecmSession,execution);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ecmSession != null) {
				authService.logout(workflowSpecialUserName);
			}
		}
		System.out.println("DelegateExecution_notify");
	}



	/**
	 * JavaDelegate 方法 for serviceTask
	 */
	@Override
	public void execute(DelegateExecution arg0) throws FlowableException {
		if (arg0.getVariable("processInstanceID") == null) {
			arg0.setVariable("processInstanceID", arg0.getProcessInstanceId());
			arg0.setVariable("processName", arg0.getProcessDefinitionId().split(":")[0]);
		}

		System.out.println("serviceTask_execute");
	}

	/**
	 * TaskListener 方法 for taskListener
	 */
	@Override
	public void notify(DelegateTask task) throws FlowableException {
		if ("create".equals(task.getEventName())) {
			/////////////////////// 任务到达发送邮件//////////////
			String assignee = task.getAssignee();// ecm_user.Name

			IEcmSession ecmSession = null;
			String workflowSpecialUserName = env.getProperty("ecm.username");
			try {
				ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
				if (task.getAssignee() != null) {// 普通任务
					EcmUser user = userService.getObjectByName(ecmSession.getToken(), assignee);
					// TODO 如果代理人不为空，就执行代理
					if (!Strings.isEmpty(user.getDelegateUser())
							&& DateUtils.compareDate(new Date(), user.getDelegateStart()) >= 0
							&& DateUtils.compareDate(new Date(), user.getDelegateEnd()) < 0) {
						customWorkflowService.delegateTask(task.getId(), user.getDelegateUser());
						;
					}
					String email = user.getEmail();
					if (email != null && !"".equals(email)) {
						// serviceDocMail.sendTaskMail(email);
					}

					EcmAuditWorkitem audit = new EcmAuditWorkitem();
					audit.createId();
					audit.setCreateTime(task.getCreateTime());
					audit.setDocId("");
					audit.setFormId("");
					audit.setTaskName(task.getName());
					audit.setAssignee(task.getAssignee());
					audit.setProcessInstanceId(task.getProcessInstanceId());
					audit.setTaskId(task.getId());
					ecmAuditWorkitemMapper.insert(audit);
				} else {// 候选用户
					Set<IdentityLink> candidates = task.getCandidates();
					String taskUserIds = "";
					for (Iterator iterator = candidates.iterator(); iterator.hasNext();) {
						IdentityLink identityLink = (IdentityLink) iterator.next();
						EcmUser user = userService.getObjectByName(ecmSession.getToken(), identityLink.getUserId());
						String email = user.getEmail();
						if (email != null && !"".equals(email)) {
							// serviceDocMail.sendTaskMail(email);
						}
						taskUserIds = identityLink.getUserId() + ";" + taskUserIds;
					}
					// 创建流程日志
					EcmAuditWorkitem audit = new EcmAuditWorkitem();
					audit.createId();
					audit.setCreateTime(task.getCreateTime());
					audit.setDocId("");
					audit.setFormId("");
					audit.setTaskName(task.getName());
					audit.setAssignee(taskUserIds);
					audit.setProcessInstanceId(task.getProcessInstanceId());
					audit.setTaskId(task.getId());
					ecmAuditWorkitemMapper.insert(audit);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ecmSession != null) {
					authService.logout(workflowSpecialUserName);
				}
			}
		} else if ("complete".equals(task.getEventName())) {
			if (task.getVariable("processInstanceID") == null) {
				task.setVariable("processInstanceID", task.getProcessInstanceId());
				task.setVariable("processName", task.getProcessDefinitionId().split(":")[0]);
			}
			IEcmSession ecmSession = null;
			String workflowSpecialUserName = env.getProperty("ecm.username");
			try {
				ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
				loadProcessBusinessLogicToVariable(ecmSession, task);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (ecmSession != null) {
					authService.logout(workflowSpecialUserName);
				}
			}

		}
		System.out.println("taskListener_notify");

	}

	/**
	 * @param ecmSession
	 * @param userName
	 * @return
	 */
	private Object getApprover(IEcmSession ecmSession, String userName) {
		String[] user = userName.split(";");
		Object result = null;
		if (user.length == 1) {// 只有一个用户或一个用户组
			if (userService.getObjectByName(ecmSession.getToken(), userName) != null) {
				result = userName;
			} else {// 如果是用户组
				String groupId = groupService.getGroupByName(ecmSession.getToken(), userName).getId();
				List<EcmUser> userList = groupService.getUsers(ecmSession.getToken(), groupId);
				List<String> userNameList = new ArrayList<String>();
				for (int i = 0; i < userList.size(); i++) {
					userNameList.add(userList.get(i).getName());
				}
				result = userNameList;
			}
		} else {// 多个用户
			result = Arrays.asList(user);
		}
		return result;
	}

	private Integer geVariable(DelegateExecution execution, String variableName) {
		Object value = execution.getVariableLocal(variableName);
		return (Integer) (value != null ? value : 0);
	}

	private DelegateExecution getMultiInstanceRootExecution(DelegateExecution executionEntity) {
		DelegateExecution multiInstanceRootExecution = null;
		DelegateExecution currentExecution = executionEntity;
		while (currentExecution != null && multiInstanceRootExecution == null && currentExecution.getParent() != null) {
			if (currentExecution.isMultiInstanceRoot()) {
				multiInstanceRootExecution = currentExecution;
			} else {
				currentExecution = currentExecution.getParent();
			}
		}
		return multiInstanceRootExecution;
	}

	private Map<String, Object> loadProcessBusinessLogicToVariable(IEcmSession ecmSession,
			org.flowable.variable.api.delegate.VariableScope arg0) {
		Map<String, Object> varMap = arg0.getVariables();
		String formId = varMap.get("formId").toString();
		try {
			EcmDocument ecmObject = documentService.getObjectById(ecmSession.getToken(), formId);

			String process_name = arg0.getVariable("processName").toString();
			switch (process_name) {
			case "BianJiaoShenPi":
				// runtimeService.getVariables("1995ac1d-1259-11ea-9171-00505622af9b")
				varMap.put("bianxie", getApprover(ecmSession, runtimeService
						.getVariable(arg0.getVariable("processInstanceID").toString(), "startUser").toString()));
				// varMap.put("taskUser_owner_leader",
				// ecmObject.getAttributes().get("C_REVIEWER1"));
				varMap.put("jiaohe",
						getApprover(ecmSession, ecmObject.getAttributes().get("C_REVIEWER1").toString()));
				varMap.put("shenhe",
						getApprover(ecmSession, ecmObject.getAttributes().get("C_REVIEWER2").toString()));
				varMap.put("pizhun",
						getApprover(ecmSession, ecmObject.getAttributes().get("C_REVIEWER3").toString()));
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
	 * @param execution
	 */
	private void setPassedTaskCount(DelegateExecution execution) {
		// 设置通过拒绝参数
		DelegateExecution miRootExecution = getMultiInstanceRootExecution(execution);
		if (miRootExecution != null) {
			if ("通过".equals(execution.getVariable("outcome"))) {
				int nrOfPassedInstances = geVariable(miRootExecution, "nrOfPassedInstances");
				miRootExecution.setVariableLocal("nrOfPassedInstances", ++nrOfPassedInstances);
			} else {
				int nrOfRejectedInstances = geVariable(miRootExecution, "nrOfRejectedInstances");
				miRootExecution.setVariableLocal("nrOfRejectedInstances", ++nrOfRejectedInstances);
			}
		}
	}
	/**
	 * @param execution
	 * @param ecmSession
	 */
	private void sendMailOfProcessEnd(IEcmSession ecmSession,DelegateExecution execution) {
		// 流程结束发送邮件
		if ("endevent1".equals(execution.getCurrentActivityId())) {
			HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(execution.getProcessInstanceId()).singleResult();
			String startUserId = hi.getStartUserId();
			EcmUser user = userService.getObjectByName(ecmSession.getToken(), startUserId);
			String email = user.getEmail();
			if (email != null && !"".equals(email)) {
				// erviceDocMail.sendEndMail(email);
			}
		}
	}

}
