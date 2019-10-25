package com.ecm.core.bpm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.ScriptUtils;
import com.ecm.core.EcmContext;
import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.dao.EcmQueueItemMapper;
import com.ecm.core.dao.EcmWorkflowMapper;
import com.ecm.core.dao.EcmWorkitemMapper;
import com.ecm.core.entity.EcmActivity;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.entity.EcmTransaction;
import com.ecm.core.entity.EcmWorkflow;
import com.ecm.core.entity.EcmWorkitem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.IWorkitemService;
/**
 * 任务服务
 * @author Haihong Rong
 *
 */
@Component
@Scope("prototype")
public class WorkitemService extends EcmObjectService<EcmWorkitem> implements IWorkitemService {
	
	@Autowired
	private EcmWorkitemMapper ecmWorkitemMapper;
	
	@Autowired
	private EcmWorkflowMapper ecmWorkflowMapper;
	
	@Autowired
	private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
	
	@Autowired
	private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;
	
	@Autowired
	private EcmQueueItemMapper ecmQueueItemMapper;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private BPMServerEvent serverEvent;
	
	@Override
	public boolean completeTask(String token, String queueId,String result,String message) throws EcmException, AccessDeniedException {
		EcmQueueItem queueItem = ecmQueueItemMapper.selectByPrimaryKey(queueId);
		return completeTask(token, queueItem,result,message);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean completeTask(String token, EcmQueueItem queueItem,String result,String message) throws EcmException, AccessDeniedException {
		if(queueItem.getStatus()>=3) {
			throw new EcmException("任务："+queueItem.getId()+" 已经被:"+queueItem.getDqueueUser()+" 完成。");
		}
		String userName = getSession(token).getCurrentUser().getUserName();
		EcmWorkitem workitem = ecmWorkitemMapper.selectByPrimaryKey(queueItem.getWorkitemId());
		EcmWorkflow workflow= ecmWorkflowMapper.selectByPrimaryKey(workitem.getWorkflowId());
		EcmActivity acttivity = activityService.getObjectById(token, workitem.getActivityId());
		String docId = workflow.getDocId();
		String formId = workflow.getFormId();
		queueItem.setStatus(3);
		queueItem.setDqueueUser(userName);
		queueItem.setDelectFlag(true);
		ecmQueueItemMapper.updateByPrimaryKey(queueItem);
		String cond = " WORKITEM_ID='"+workitem.getId()+"' and STATUS=3";
		int count = ecmQueueItemMapper.getCountByCondition(cond);
		cond = " WORKITEM_ID='"+workitem.getId()+"' and STATUS<3";
		int notCompleteCount = ecmQueueItemMapper.getCountByCondition(cond);
		//根据完成模式，完成其他任务。
		//判断是否都完成
		boolean complatedAll = false;
		if(acttivity.getRoleCount()>0&&acttivity.getRoleCount()<=count){
			if(notCompleteCount>0) {
				//完成其他任务
				String sql ="update ecm_queue_item set STATUS=3 where STATUS<3 and WORKITEM_ID='"+workitem.getId()+"'";
				ecmQueueItemMapper.executeSQL(sql);
			}
			complatedAll = true;
		}
		else if(notCompleteCount==0){
			complatedAll = true;
		}
		//如果全部完成，开始下一任务
		if(complatedAll) {
			List<EcmActivity> actList =getNextActivities(token, acttivity, docId, formId);
			//流程结束
			if(actList.size()==1&&actList.get(0).getItemType()==99) {
				String sql = " WORKFLOW_ID='"+workitem.getWorkflowId()+"'";
				List<EcmAuditWorkflow> list = ecmAuditWorkflowMapper.selectByCondition(sql);
				if(list.size()>0){
					EcmAuditWorkflow awf = list.get(0);
					awf.setCompleteDate(new Date());
					ecmAuditWorkflowMapper.updateByPrimaryKey(awf);
				}
				serverEvent.complateWorkflow(token, workflow);
				
				//删除流程数据/
				//ecmWorkflowMapper.deleteByPrimaryKey(workitem.getWorkflowId());
			}
			else {
				startNextActivities(token, actList,queueItem.getRouterId(), docId, formId, userName);
			}
		}
		//更新日志
		cond = " WORKFLOW_ID='"+queueItem.getRouterId()+"' and TASK_ID='"+queueItem.getId()+"'";
		List<EcmAuditWorkitem> list=  ecmAuditWorkitemMapper.selectByCondition(cond);
		EcmAuditWorkitem audit =null;
		if(list.size()>0) {
			audit =	list.get(0);
			audit.setCompleteDate(new Date());
			audit.setPerformer(userName);
			audit.setResult(result);
			audit.setMessage(message);
			ecmAuditWorkitemMapper.updateByPrimaryKey(audit);
		}
		else {
			audit =	new EcmAuditWorkitem();
			audit.createId();
			audit.setStartDate(queueItem.getSendDate());
			audit.setCompleteDate(new Date());
			audit.setDocId(docId);
			audit.setFormId(formId);
			audit.setTaskName(workitem.getName());
			audit.setPerformer(userName);
			audit.setResult(result);
			audit.setMessage(message);
			audit.setWorkflowId(workitem.getWorkflowId());
			audit.setTaskId(queueItem.getId());
			ecmAuditWorkitemMapper.insert(audit);
		}
		return true;
	}
	@Override
	public boolean openTask(String token, String queueId) throws EcmException, AccessDeniedException {
		EcmQueueItem queueItem = ecmQueueItemMapper.selectByPrimaryKey(queueId);
		return openTask(token, queueItem);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean openTask(String token, EcmQueueItem queueItem) throws EcmException, AccessDeniedException {
		if(queueItem.getStatus()==0) {
			EcmWorkitem workitem = ecmWorkitemMapper.selectByPrimaryKey(queueItem.getWorkitemId());
			EcmWorkflow workflow= ecmWorkflowMapper.selectByPrimaryKey(workitem.getWorkflowId());
			queueItem.setStatus(1);
			ecmQueueItemMapper.updateByPrimaryKey(queueItem);
			EcmAuditWorkitem audit = new EcmAuditWorkitem();
			audit.createId();
			audit.setStartDate(new Date());
			audit.setDocId(workflow.getDocId());
			audit.setFormId(workflow.getFormId());
			audit.setTaskName(workitem.getName());
			audit.setPerformer(getSession(token).getCurrentUser().getUserName());
			audit.setWorkflowId(workitem.getWorkflowId());
			audit.setTaskId(queueItem.getId());
			ecmAuditWorkitemMapper.insert(audit);
		}
		return true;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean startNextActivities(String token, List<EcmActivity> actList,String workflowId,String docId,String formId,String fromUser) throws EcmException {
		Notification ntf = new Notification();
		for(EcmActivity act:actList) {
			EcmWorkitem workitem = new EcmWorkitem();
			workitem.createId();
			workitem.setName(act.getName());
			workitem.setProcessId(act.getProcessId());
			workitem.setWorkflowId(workflowId);
			workitem.setItemType(act.getItemType());
			workitem.setActivityId(act.getId());
			ecmWorkitemMapper.insert(workitem);
			List<String> performers = getPerformers(token, act,docId,formId);
			for(String userName:performers) {
				EcmQueueItem queue = new EcmQueueItem();
				queue.createId();
				queue.setName(userName);
				queue.setTaskName(act.getName());
				queue.setWorkitemId(workitem.getId());
				queue.setRouterId(workflowId);
				queue.setEventName("ecm_start_workitem");
				queue.setSendDate(new Date());
				queue.setSendUser(fromUser);
				queue.setStatus(0);
				queue.setDelectFlag(false);
				ecmQueueItemMapper.insert(queue);
				ntf.startNotification(queue);
			}
		}
		return true;
	}
	/**
	 * 获取下一个任务列表
	 * @param actObj 当前活动
	 * @param docId 文档Id
	 * @param formId 表单Id
	 * @return
	 */
	@Override
	public List<EcmActivity> getNextActivities(String token, EcmActivity actObj,String docId,String formId){
		List<EcmActivity> list = new ArrayList<EcmActivity>();
		//只有一个节点
		if(actObj.getTransactions().size()==1) {
			String nextId = actObj.getTransactions().get(0).getToId();
			list.add(activityService.getObjectById(token, nextId));
		}
		//开始所有节点
		else if(actObj.getNextCount()==0) {
			for(EcmTransaction tObj:actObj.getTransactions()) {
				list.add(activityService.getObjectById(token, tObj.getToId()));
			}
		}
		//根据条件开始节点
		else {
			
			Map<String, Object> docValues = documentService.getObjectMapById(token, docId);
			Map<String, Object> formValues = documentService.getObjectMapById(token, formId);
			for(EcmTransaction tObj:actObj.getTransactions()) {
				if(isMatch(tObj,docValues,formValues)) {
					list.add(activityService.getObjectById(token, tObj.getToId()));
					//大于等于启动数量，直接中断
					if(list.size()>=actObj.getNextCount()) {
						break;
					}
				}
			}
		}
		return list;
	}
	/**
	 * 获取所有执行人,暂时只处理人员
	 * @param actObj
	 * @param docId
	 * @param formId
	 * @return
	 * @throws EcmException 
	 */
	public List<String> getPerformers(String token, EcmActivity actObj,String docId,String formId) throws EcmException{
		List<String>  list = new ArrayList<String>();
		String performer = actObj.getPerformer();
		switch(actObj.getPerformType()) {
		case 0:
			list.add("ECMSYSTEM");
			break;
		case 1://设置人员
			if(performer.startsWith("{")) {
				performer = performer.replace("{", "").replace("}", "").toUpperCase();
				if(performer.startsWith("DOC.")) {
					String attrName = performer.replace("DOC.", "");
					Map<String, Object> docValues = documentService.getObjectMapById(token, docId);
					String val = getValue(docValues,attrName);
					//多值处理
					String[] ps = val.split(EcmContext.SPLIT_STRING);
					for(String p:ps) {
						list.add(p);
					}
				}
				else if(performer.startsWith("FRM.")) {
					String attrName = performer.replace("FRM.", "");
					Map<String, Object> formValues = documentService.getObjectMapById(token, formId);
					String val = getValue(formValues,attrName);
					//多值处理
					String[] ps = val.split(EcmContext.SPLIT_STRING);
					for(String p:ps) {
						list.add(p);
					}
				}
			}else {
				if(performer!=null) {
					//多值处理
					String[] ps = performer.split(EcmContext.SPLIT_STRING);
					for(String p:ps) {
						list.add(p);
					}
				}
			}
			break;
		default:
			throw(new EcmException("活动执行类型："+actObj.getPerformType()+"，未实现"));
		}
		return list;
	}
	/**
	 * 是否匹配
	 * @param tranObj
	 * @param docValues
	 * @param formValues
	 * @return
	 */
	private boolean isMatch(EcmTransaction tranObj,Map<String, Object> docValues,Map<String, Object> formValues) {
		String cond = tranObj.getCondition();
		//未设置条件，直接返回成功
		if(cond==null||cond.trim().length()<1) {
			return true;
		}else {
			List<String> strs = EcmStringUtils.getStringsByRegular(cond,"\\{","\\}");
			for(String pstr:strs) {
				String upstr = pstr.toUpperCase();
				//从文档提取值
				if(upstr.startsWith("DOC.")) {
					String attrName = upstr.replace("DOC.", "");
					String val = getValue(docValues,attrName);
					cond = cond.replace("{"+pstr+"}", val);
				}
				//从表单提取值
				else if(upstr.startsWith("FRM.")) {
					String attrName = upstr.replace("FRM.", "");
					String val = getValue(formValues,attrName);
					cond = cond.replace("{"+pstr+"}", val);
				}
			}
			try {
				return ScriptUtils.executeBooleanCondition(cond);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 获取属性值
	 * @param objValues
	 * @param attrName
	 * @return
	 */
	private String getValue(Map<String, Object> objValues,String attrName) {
		if(objValues==null) {
			return null;
		}
		Object val = objValues.get(attrName);
		if(val==null) {
			return "";
		}
		return val.toString();
	}

}
