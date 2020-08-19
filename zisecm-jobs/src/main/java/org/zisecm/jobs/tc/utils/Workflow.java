/**
 * 
 */
package org.zisecm.jobs.tc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zisecm.jobs.tc.clientx.Session;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentTask;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.internal.strong.core._2010_09.DataManagement.DatasetReferenceFileInfo;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2007_01.DataManagement.VecStruct;
import com.teamcenter.services.strong.workflow.WorkflowService;
import com.teamcenter.services.strong.workflow._2007_06.Workflow.ReleaseStatusInput;
import com.teamcenter.services.strong.workflow._2007_06.Workflow.ReleaseStatusOption;
import com.teamcenter.services.strong.workflow._2007_06.Workflow.SetReleaseStatusResponse;
import com.teamcenter.services.strong.workflow._2008_06.Workflow.AttachmentInfo;
import com.teamcenter.services.strong.workflow._2008_06.Workflow.ContextData;
import com.teamcenter.services.strong.workflow._2008_06.Workflow.CreateSignoffInfo;
import com.teamcenter.services.strong.workflow._2008_06.Workflow.CreateSignoffs;
import com.teamcenter.services.strong.workflow._2008_06.Workflow.InstanceInfo;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.EPMTask;
import com.teamcenter.soa.client.model.strong.Group;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.soa.client.model.strong.Job;
import com.teamcenter.soa.client.model.strong.User;
import com.teamcenter.soa.client.model.strong.WorkspaceObject;
import com.teamcenter.soa.exceptions.NotLoadedException;

/**
 * @author xiaolei Creation Date 2013-6-4
 */
public class Workflow {
	public WorkflowService wfService;
	public DataManagementService dmService = null;
	DataManagement dataManagement;

	Session session;

	public Workflow(Session session) {
		this.session = session;
		wfService = WorkflowService.getService(session.getConnection());
		dmService = DataManagementService.getService(session.getConnection());
		dataManagement = new DataManagement(session);
	}

	/**
	 * 
	 * @param object
	 *            添加状态的对象
	 * @param releaseStatusName
	 *            状态名称
	 * @return 成功返回true，失败抛出错误信息异常
	 * @throws Exception
	 */
	public boolean addStatus(ModelObject object, String releaseStatusName)
			throws Exception {
		ReleaseStatusInput input = new ReleaseStatusInput();

		input.objects = new WorkspaceObject[] { (WorkspaceObject) object };

		ReleaseStatusOption option = new ReleaseStatusOption();
		option.newReleaseStatusTypeName = releaseStatusName;
		option.operation = "Append";

		input.operations = new ReleaseStatusOption[] { option };

		ReleaseStatusInput[] inputs = new ReleaseStatusInput[] { input };

		SetReleaseStatusResponse response = wfService.setReleaseStatus(inputs);

		ServiceData serviceData = response.serviceData;

		if (serviceData.sizeOfPartialErrors() == 0)
			return true;
		else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			throw new Exception(ret);
		}
	}

	/**
	 * 创建流程 add by xiaolei 2014-11-25
	 * 
	 * @param object
	 *            流程目标对象
	 * @param processTemplateName
	 *            流程模板名称
	 * @param user
	 *            初始任务指派给的用户
	 * @return
	 * @throws Exception
	 */
	
	public boolean createWorkflow(ModelObject object,
			String processTemplateName, User user) throws Exception {
		ContextData contextData = new ContextData();
		contextData.processTemplate = processTemplateName;
		contextData.processOwner = (user == null) ? "" : user.get_user_id();
		contextData.attachmentCount = 1;
		contextData.attachments = new String[] { object.getUid() };
		contextData.attachmentTypes = new int[] { 1 }; // 1 target attachment
		dmService.getProperties(new ModelObject[] { object },
				new String[] { "object_string" });
		String processName = ((ItemRevision) object).get_object_string();
		InstanceInfo info = wfService.createInstance(false, "2", processName,
				"", "", contextData);
		

		ServiceData serviceData = info.serviceData;

		if (serviceData.sizeOfPartialErrors() == 0) {
			if (serviceData.sizeOfCreatedObjects() > 0) {
				if (user != null) {
					Job job = (Job) serviceData.getCreatedObject(0);

					dmService.getProperties(new ModelObject[] { job },
							new String[] { "root_task" });
					EPMTask rootTask = job.get_root_task();
					changeProcessOwnerAndRespParty(rootTask, user);
			
				}
			}
			return true;
		} else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			throw new Exception(ret);
		}

	}
	
	/**
	 * 更新任务及子任务的owner及responsible_party
	 * add by xiaolei 2014-11-25
	 * @param task
	 * @param user
	 * @throws Exception
	 */
	public void changeProcessOwnerAndRespParty(EPMTask task,User user) throws Exception {
		dataManagement.changeOwner(task, user);
		dmService.getProperties(
				new ModelObject[] { task },
				new String[] { "responsible_party" });

		HashMap<String, VecStruct> map = new HashMap<String, VecStruct>();
		VecStruct vecStruct = new VecStruct();
		vecStruct.stringVec = new String[] { user.getUid() };
		map.put("responsible_party", vecStruct);

		ServiceData serviceData = dmService.setProperties(
				new ModelObject[] { task }, map);
		if (serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0)
					.getMessages();

			for (int j = 0; j < msgs.length; j++) {
				ret = ret + msgs[j] + " ";
			}

			System.out.println(ret);
		}
		
		dmService.getProperties(new ModelObject[] { task },
				new String[] { "child_tasks" });
		ModelObject[] childTasks = task.get_child_tasks();
		for (int i = 0; i < childTasks.length; i++) {
			changeProcessOwnerAndRespParty((EPMTask)childTasks[i], user);
		}	
	}
	
	public void createProcessAssignUser(String processTemplate, String processName, String processUser, ModelObject[] attachment, User assignUser) throws NotLoadedException {
		//DataManagementService dmService = DataManagementService.getService(AppXSession.getConnection());
		//WorkflowService wfService = WorkflowService.getService(AppXSession.getConnection());
		ContextData cd = new ContextData();
		cd.processTemplate = processTemplate;
		cd.processOwner = processUser;

		InstanceInfo ii = wfService.createInstance(true, "", processName, "", "", cd);
		Job job = (Job) ii.serviceData.getCreatedObject(0);

		dmService.getProperties(new ModelObject[] { job }, new String[] { "root_task", "all_tasks" });
		EPMTask root_task = job.get_root_task();
//		System.out.println("root_task=" + root_task);
		AttachmentInfo info = new AttachmentInfo();
		info.attachmentType = new int[] { 1 };// 1表示目标
		info.attachment = attachment;// 目标下的对象
		wfService.addAttachments(root_task, info);

		List<EPMTask> taskList = new ArrayList<EPMTask>();
		ModelObject[] tasks = job.get_all_tasks();
		for (ModelObject task : tasks) {
			EPMTask etask = (EPMTask) task;
			dmService.getProperties(new ModelObject[] { etask }, new String[] { "object_type", "object_name" });// 对象加载到客户端，再取属性值
			String task_type = etask.get_object_type();
			String task_name = etask.get_object_name();
			if (task_type.equals("EPMSelectSignoffTask")) {
				taskList.add(etask);
			}
		}

		dmService.getProperties(new ModelObject[] { assignUser }, new String[] { "user_id" });
		String user_id = assignUser.get_user_id();
		Group group = (Group) assignUser.get_default_group();
		dmService.getProperties(new ModelObject[] { group }, new String[] { "full_name" });
		String full_name = group.get_full_name();
		
		Query query=new Query(session);
		ModelObject[] groupMembers = query.queryObjectsBySavedQuery( user_id, full_name );
//		System.out.println("groupMembers.length=" + groupMembers.length);
		if (groupMembers.length > 0) {
			CreateSignoffInfo[] signoffInfos = new CreateSignoffInfo[1];
			signoffInfos[0] = new CreateSignoffInfo();
			signoffInfos[0].signoffMember = groupMembers[0];
			signoffInfos[0].signoffAction = "SOA_EPM_Review";// SOA_EPM_Review
			signoffInfos[0].origin = null;
			signoffInfos[0].originType = "SOA_EPM_SIGNOFF_ORIGIN_PROFILE";
			int size = taskList.size();
//			System.out.println("size=" + size);
			if (size > 0) {
				CreateSignoffs[] acreatesignoffs = new CreateSignoffs[size];
				// 循环遍历给每个节点指派人员
				for (int k = 0; k < size; k++) {
					acreatesignoffs[k] = new CreateSignoffs();
					acreatesignoffs[k].signoffInfo = signoffInfos;
					acreatesignoffs[k].task = taskList.get(k);
				}
				try {
					wfService.addSignoffs(acreatesignoffs);
					for (int k = 0; k < size; k++) {
						wfService.performAction(taskList.get(k), "SOA_EPM_complete_action", null, null, "SOA_EPM_completed", assignUser);
					}
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
