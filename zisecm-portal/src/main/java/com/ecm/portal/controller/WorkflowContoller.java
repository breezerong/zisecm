//package com.ecm.portal.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ecm.common.util.JSONUtils;
//import com.ecm.core.ActionContext;
//import com.ecm.core.EcmContext;
////import com.ecm.core.bpm.ProcessEngineExt;
//import com.ecm.core.bpm.ProcessService;
//import com.ecm.core.bpm.WorkflowAuditService;
//import com.ecm.core.bpm.WorkflowService;
//import com.ecm.core.bpm.WorkitemAuditService;
//import com.ecm.core.bpm.WorkitemService;
//import com.ecm.core.entity.EcmActivity;
//import com.ecm.core.entity.EcmAuditWorkflow;
//import com.ecm.core.entity.EcmAuditWorkitem;
//import com.ecm.core.entity.EcmProcess;
//import com.ecm.core.entity.EcmQueueItem;
//import com.ecm.core.exception.AccessDeniedException;
//import com.ecm.core.exception.EcmException;
//import com.ecm.core.exception.NoPermissionException;
//import com.ecm.core.service.QueueItemService;
//
//@Controller
//public class WorkflowContoller extends ControllerAbstract{
//
////	@Autowired
////	private ProcessEngineExt processEngine;
//	
//	@RequestMapping("/workflow/getProcess")
//	@ResponseBody
//	public Map<String, Object> getGetProcess() {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
////			ProcessService service = processEngine.getProcessService();
////			List<EcmProcess> list = service.getAllObject(getToken());
////			mp.put("data", list);
////			mp.put("code", ActionContext.SUCESS);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getWfActivities")
//	@ResponseBody
//	public Map<String, Object> getWfActivities(@RequestBody String wfId) {
//		wfId = wfId.replace("\"", "");
//		WorkflowAuditService wfs = processEngine.getWorkflowAuditService();
//		List<EcmAuditWorkflow> acts;
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			acts = wfs.getObjects(getToken(),"WORKFLOW_ID='"+wfId+"'");
//			List<EcmActivity> list = new ArrayList<EcmActivity>();
//			if(acts.size()>0) {
//				EcmAuditWorkflow act = acts.get(0);
//				String name = act.getProcessName();
//				ProcessService service = processEngine.getProcessService();
//				EcmProcess  ep = service.getObjectByName(getToken(),name);
//				for(EcmActivity dact:ep.getActivities()) {
//					if(dact.getItemType()==1) {
//						list.add(dact);
//					}
//				}
//			}
//			mp.put("data", list);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (EcmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		} catch (AccessDeniedException e) {
//			mp.put("code", ActionContext.TIME_OUT);
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getQueueActivities")
//	@ResponseBody
//	public Map<String, Object> getQueueActivities(@RequestBody String queueId) {
//		queueId = queueId.replace("\"", "");
//		List<EcmAuditWorkflow> acts;
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			QueueItemService qs = processEngine.getQueueItemService();
//			EcmQueueItem q = qs.getObjectById(getToken(),queueId);
//			WorkflowAuditService wfs = processEngine.getWorkflowAuditService();
//			acts = wfs.getObjects(getToken(),"WORKFLOW_ID='"+q.getRouterId()+"'");
//			List<EcmActivity> list = new ArrayList<EcmActivity>();
//			if(acts.size()>0) {
//				EcmAuditWorkflow act = acts.get(0);
//				String name = act.getProcessName();
//				ProcessService service = processEngine.getProcessService();
//				EcmProcess  ep = service.getObjectByName(getToken(),name);
//				for(EcmActivity dact:ep.getActivities()) {
//					if(dact.getItemType()==1) {
//						list.add(dact);
//					}
//				}
//			}
//			mp.put("data", list);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (EcmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		}
//		catch (AccessDeniedException e) {
//			mp.put("code", ActionContext.TIME_OUT);
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getItemActivities")
//	@ResponseBody
//	public Map<String, Object> getItemActivities(@RequestBody String itemId) {
//		itemId = itemId.replace("\"", "");
//		List<EcmAuditWorkflow> acts;
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			WorkitemAuditService ws = processEngine.getWorkitemAuditService();
//			EcmAuditWorkitem  item = ws.getObjectById(getToken(),itemId);
//			WorkflowAuditService wfs = processEngine.getWorkflowAuditService();
//			acts = wfs.getObjects(getToken(),"WORKFLOW_ID='"+item.getWorkflowId()+"'");
//			List<EcmActivity> list = new ArrayList<EcmActivity>();
//			if(acts.size()>0) {
//				EcmAuditWorkflow act = acts.get(0);
//				String name = act.getProcessName();
//				ProcessService service = processEngine.getProcessService();
//				EcmProcess  ep = service.getObjectByName(getToken(),name);
//				for(EcmActivity dact:ep.getActivities()) {
//					if(dact.getItemType()==1) {
//						list.add(dact);
//					}
//				}
//			}
//			mp.put("data", list);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (EcmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			mp.put("code", ActionContext.TIME_OUT);
//			mp.put("message", e.getMessage());
//		} catch (NoPermissionException e) {
//			// TODO Auto-generated catch block
//			mp.put("code", ActionContext.NO_PERMSSION);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/startWrokflow")
//	@ResponseBody
//	public Map<String, Object> startWorkflow(@RequestBody String argStr) {
//		WorkflowService wfs = processEngine.getWorkflowService();
//		Map<String, Object> args = JSONUtils.stringObjectToMap(argStr);
//		String processId = args.get("processId").toString();
//		String workflowName = args.get("name").toString();
//		String description = args.get("description").toString();
//		String formId =args.get("formId").toString();
//		String docIdStr= args.get("docId").toString();
//		//如果是纯数字没有引号处理
//		if(docIdStr.indexOf("\"")<0) {
//			docIdStr = docIdStr.replace("[", "[\"").replace("]", "\"]").replace(",", "\",\"");
//		}
//		List<String> docIds= JSONObject.parseArray(docIdStr, String.class);
//		Map<String, Object> mp = new HashMap<String, Object>();
//		for(String id : docIds) {
//			
//			try {
//				wfs.startWorkflow(getToken(),processId, workflowName, description, id.trim(), formId.trim());
//				mp.put("code", ActionContext.SUCESS);
//			} catch (EcmException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				mp.put("code", ActionContext.FAILURE);
//				mp.put("message", e.getMessage());
//			} catch (AccessDeniedException e) {
//				// TODO Auto-generated catch block
//				mp.put("code", ActionContext.TIME_OUT);
//				mp.put("message", e.getMessage());
//			}
//		}
//		
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getMyTodoTask")
//	@ResponseBody
//	public Map<String, Object> getMyTodoTask(@RequestBody String argStr) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			Map<String, Object> args = JSONUtils.stringToMap(argStr);
//			int pageSize = Integer.parseInt(args.get("pageSize").toString());
//			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
//			String condition = args.get("condition").toString();
//			QueueItemService service = processEngine.getQueueItemService();
//			List<EcmQueueItem> list = service.getMyTodoObjects(getToken(),pageSize,pageIndex,condition);
//			mp.put("data", list);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getMyAllTodoCount")
//	@ResponseBody
//	public Map<String, Object> getMyAllTodoCount() {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			QueueItemService service = processEngine.getQueueItemService();
//			int count = service.getMyTodoCount(getToken());
//			mp.put("data", count);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			mp.put("code", ActionContext.TIME_OUT);
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getMyTodoCount")
//	@ResponseBody
//	public Map<String, Object> getMyTodoCount(@RequestBody String argStr) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			Map<String, Object> args = JSONUtils.stringToMap(argStr);
//			String condition = args.get("condition").toString();
//			QueueItemService service = processEngine.getQueueItemService();
//			int count = service.getMyTodoCount(condition);
//			mp.put("data", count);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			mp.put("code", ActionContext.TIME_OUT);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/completeTask")
//	@ResponseBody
//	public Map<String, Object> completeTask(@RequestBody String argStr) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			WorkitemService service = processEngine.getWorkitemService();
//			Map<String, Object> args = JSONUtils.stringObjectToMap(argStr);
//			Object obj = args.get("taskId");
//			String result = args.get("result").toString();
//			String message = args.get("message").toString();
//			if(obj instanceof  ArrayList ) {
//				List<String> ids = (ArrayList)obj;
//				for(String id:ids) {
//					service.completeTask(getToken(),id, result, message);
//				}
//			}
//			else {
//				String taskId = args.get("taskId").toString();
//				service.completeTask(getToken(),taskId, result, message);
//			}
//			mp.put("code", ActionContext.SUCESS);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getMyDoneTask")
//	@ResponseBody
//	public Map<String, Object> getMyDoneTask(@RequestBody String argStr) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			Map<String, Object> args = JSONUtils.stringToMap(argStr);
//			int pageSize = Integer.parseInt(args.get("pageSize").toString());
//			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
//			String condition = args.get("condition").toString();
//			WorkitemAuditService service = processEngine.getWorkitemAuditService();
//			List<EcmAuditWorkitem> list = service.getMyAuditWorkitem(getToken(),pageSize,pageIndex,condition);
//			mp.put("data", list);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getMyDoneCount")
//	@ResponseBody
//	public Map<String, Object> getMyDoneTaskCount(@RequestBody String argStr) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			Map<String, Object> args = JSONUtils.stringToMap(argStr);
//			String condition = args.get("condition").toString();
//			WorkitemAuditService service = processEngine.getWorkitemAuditService();
//			int count = service.getMyAuditWorkitemCount(condition);
//			mp.put("data", count);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			mp.put("code", ActionContext.TIME_OUT);
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getMyWorkflow")
//	@ResponseBody
//	public Map<String, Object> getMyWorkflow(@RequestBody String argStr) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			Map<String, Object> args = JSONUtils.stringToMap(argStr);
//			int pageSize = Integer.parseInt(args.get("pageSize").toString());
//			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
//			String condition = args.get("condition").toString();
//			WorkflowAuditService service = processEngine.getWorkflowAuditService();
//			QueueItemService qservice = processEngine.getQueueItemService();
//			List<EcmAuditWorkflow> list = service.getMyAuditWorkflow(getToken(),pageSize,pageIndex,condition);
//			for(EcmAuditWorkflow wf:list) {
//				if(wf.getCompleteDate()==null) {
//					List<EcmQueueItem> qlist = qservice.getObjects(getToken(), "ROUTER_ID='"+wf.getWorkflowId()+"' and STATUS<3 order by SEND_DATE");
//					String u = "";
//					for(EcmQueueItem q:qlist) {
//						if(u.length()==0) {
//							u = q.getName();
//						}
//						else {
//							u+=EcmContext.SPLIT_STRING+q.getName();
//						}
//					}
//					wf.setCurrentUser(u);
//				}
//			}
//			mp.put("data", list);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			mp.put("code", ActionContext.TIME_OUT);
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getMyWorkflowCount")
//	@ResponseBody
//	public Map<String, Object> getMyWorkflowCount(@RequestBody String argStr) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			Map<String, Object> args = JSONUtils.stringToMap(argStr);
//			String condition = args.get("condition").toString();
//			WorkflowAuditService service = processEngine.getWorkflowAuditService();
//			int count = service.getMyAuditWorkflowCount(getToken(),condition);
//			mp.put("data", count);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//	
//	@RequestMapping("/workflow/getWorkflowTask")
//	@ResponseBody
//	public Map<String, Object> getWorkflowTask(@RequestBody String id) {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		try {
//			id = id.replace("\"", "");
//			WorkitemAuditService service = processEngine.getWorkitemAuditService();
//			QueueItemService qservice = processEngine.getQueueItemService();
//			List<EcmAuditWorkitem> list = service.getObjects(getToken()," WORKFLOW_ID='"+id + "' order by START_DATE,START_DATE DESC");
//			List<EcmQueueItem> qlist = qservice.getObjects(getToken(), "ROUTER_ID='"+id+"' and STATUS<3 order by SEND_DATE");
//			for(EcmQueueItem q:qlist) {
//				if(list==null) {
//					list = new ArrayList<EcmAuditWorkitem>();
//				}
//				EcmAuditWorkitem wi = new EcmAuditWorkitem();
//				wi.setId("");
//				wi.setPerformer(q.getName());
//				wi.setStartDate(q.getSendDate());
//				wi.setTaskName(q.getTaskName());
//				wi.setWorkflowId(id);
//				wi.setTaskId(q.getId());
//				list.add(wi);
//			}
//			mp.put("data", list);
//			mp.put("code", ActionContext.SUCESS);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//			mp.put("message", e.getMessage());
//		}
//		return mp;
//	}
//}
