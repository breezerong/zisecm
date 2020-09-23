package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.service.ProjectService;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class HomeDocController extends ControllerAbstract {
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/homeTop/homeSumNum", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> icmSumNum(@RequestBody String argStr) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String projectName =args.get("projectName")!=null?args.get("projectName").toString():"";
		String getLoginName=getSession().getCurrentUser().getLoginName();
		String whereSql="";
		
		if(projectName!=null&&!"".equals(projectName)) {
			if("@project".equals(projectName)) {
				LoginUser userObj=null;
				try {
					userObj=getSession().getCurrentUser();
					List<String> projectList= userObj.getMyProjects();
					whereSql+=" (";
					for(int i=0;i<projectList.size();i++) {
						String project=projectList.get(i);
						if(i==0) {
							whereSql+="C_PROJECT_NAME ='"+project+"'";
						}else {
							whereSql+=" or C_PROJECT_NAME ='"+project+"'";
						}
						
					}
					whereSql+=")";
				} catch (AccessDeniedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				whereSql+=" C_PROJECT_NAME in("+projectName+")";
			}
			
		}
		else {
			LoginUser userObj=null;
			userObj=getSession().getCurrentUser();
			List<String> projectList= userObj.getMyProjects();
			whereSql+="(";
			for(int i=0;i<projectList.size();i++) {
				String project=projectList.get(i);
				if(i==0) {
					whereSql+="C_PROJECT_NAME ='"+project+"'";
				}else {
					whereSql+=" or C_PROJECT_NAME ='"+project+"'";
				}
				
			}
			whereSql+=")";
		}
		
		String getLCompany=getSession().getCurrentUser().getCompany();
		
		//and C_COMPANY='"+getSession().getCurrentUser().getCompany()+"'
		
		String sqlSum = "select count(*) as projectNum from ecm_document ed "
				+ "where ed.TYPE_NAME='项目' "
				+ "and ed.NAME in (select eg.NAME from ecm_group eg "
				+ "where id in (select egu.group_id from ecm_group_user egu "
				+ "where egu.USER_ID in (select id from ecm_user "
				+ "where login_name='"
				+ getLoginName + "')))";
		String sqlplanNum = "select count(*) as planNum from ecm_document where TYPE_NAME='计划' and "+ whereSql +"";
		String sqlthereplanNum = "select count(*) as thereplanNum from ecm_document where TYPE_NAME='计划任务' and sub_type='Activity' and "+ whereSql +"";
		String sqliedNum = "select count(*) as iedNum from ecm_document where TYPE_NAME='IED' and STATUS = '已生效' and C_IS_RELEASED='1' AND IS_CURRENT='1' and "+ whereSql +"";
		String sqldcNum = "select count(*) as dcNum from ecm_document ed where ed.C_IS_RELEASED=1 and"
				+ " (C_ITEM_TYPE='文函' )and "+whereSql+" ";
		String sqlicmNum = "select count(*) as icmNum from ecm_document ed where TYPE_NAME='ICM' and "+ whereSql +"";
		String sqlfeedbackicmNum = "select count(*) as feedbackicmNum from ecm_document ed where TYPE_NAME='ICM' AND C_IS_RELEASED=1 AND C_PROCESS_STATUS in ('新建') and "+ whereSql +"";
		String sqlDelayConfirm="select count(*) as feedbackicmNum from ecm_document ed where type_name='icm' and C_PROCESS_STATUS='新建' and"+whereSql;
		String sqlDelayNum="select count(*) as feedbackicmNum from ecm_document ed where (C_PROCESS_STATUS in ('新建','已确认')   ) and (TYPE_NAME='接口信息传递单' or TYPE_NAME='接口信息意见单') and"+whereSql;
		String sqlDelayReply="select count(*) as feedbackicmNum from ecm_document ed where (C_PROCESS_STATUS not in ('新建','已确认') or C_PROCESS_STATUS is null) and (TYPE_NAME='接口信息传递单' or TYPE_NAME='接口信息意见单') and " 
		+ whereSql; 
		String sqlDelayReplyConfirm="select count(*) as feedbackicmNum from ecm_document ed where (C_PROCESS_STATUS in ('新建') ) and (TYPE_NAME='接口信息传递单' or TYPE_NAME='接口信息意见单')";
		String sqlcomplanNum = "select count(*) as complanNum from ecm_document where TYPE_NAME='计划' and "+ whereSql +" and (C_TO='"+ getLCompany +"')";
		String sqlcomthereplanNum = "select count(*) as comthereplanNum from ecm_document where TYPE_NAME='计划任务' and sub_type='Activity' and "+ whereSql; 
		String sqlcomiedNum = "select count(*) as comiedNum from ecm_document where TYPE_NAME='IED' and C_ITEM_STATUS2 = 'Y' and "+ whereSql +" and (C_COMPANY='"+getLCompany+"')";
		String sqlcomicmNum =  "select count(*) as comicmNum from ecm_document ed where TYPE_NAME='ICM' and "+ whereSql +" and (C_COMPANY='"+getLCompany+"')";
		String sqlcomdcNum = "select count(*) as comdcNum from "
				+ "(select a.C_item_type,a.TYPE_NAME,a.C_COMPANY,a.C_IS_RELEASED,a.C_PROJECT_NAME,b.TO_NAME "
				+ "from ecm_document a, exc_transfer b where a.id=b.doc_id)t where "
				+ "C_IS_RELEASED=1 and (C_ITEM_TYPE='文函' or TYPE_NAME='设计文件')  and("+whereSql+" or  TO_NAME='"+getLCompany+"')";
		String sqlDelayCloseConfirm="select count(*) as count from ecm_document where (C_PROCESS_STATUS in ('新建') ) and (TYPE_NAME='ICM') and ("+whereSql+")";
		String sqlDRK = "select count(*) as feedbackicmNum from ecm_document ed where (TYPE_NAME='接口信息传递单' or TYPE_NAME='接口信息意见单') and C_IS_RELEASED='1' and C_PROCESS_STATUS ='新建' and "+ whereSql +"";
		
		String sqlList = "select ("+
				sqlSum+") as projectNum, ("+
				sqlplanNum+") as planNum,("+
				sqlthereplanNum+") as thereplanNum,("+
				sqliedNum+") as iedNum ,("+
				sqldcNum+") as dcNum, ("+
				sqlicmNum+") as icmNum ,("+
				sqlfeedbackicmNum+") as feedbackicmNum,("+ 
				sqlcomplanNum+") as complanNum, (" +
				sqlcomthereplanNum+") as comthereplanNum, ("+ 
				sqlcomiedNum+") as comiedNum, ("+
				sqlcomicmNum+") as comicmNum,("+sqlcomdcNum+") as comdcNum,("
				+sqlDelayConfirm+") as delayConfirmNum,("
				+sqlDelayNum+") as delayNum,("
				+ sqlDelayReply+") as delayReplyNum,("
				+sqlDelayReplyConfirm+") as delayReplyConfirm,("+sqlDelayCloseConfirm+") as delayCloseNum,("
				+sqlDRK+")as delay";
		
		try{
			System.out.println(sqlcomdcNum);
			List<Map<String, Object>> numList = ecmDocument.executeSQL(sqlList);
			
			mp.put("projectNum",numList.get(0).get("projectNum"));
			mp.put("planNum", numList.get(0).get("planNum"));
			mp.put("thereplanNum",numList.get(0).get("ThreeplanNum"));
			mp.put("iedNum",numList.get(0).get("iedNum"));
			mp.put("dcNum",numList.get(0).get("dcNum"));
			mp.put("icmNum",numList.get(0).get("icmNum"));
			mp.put("feedbackicmNum",numList.get(0).get("feedbackicmNum"));
			mp.put("complanNum",numList.get(0).get("complanNum"));
			mp.put("comthereplanNum",numList.get(0).get("comthreelanNum"));
			mp.put("comiedNum",numList.get(0).get("comiedNum"));
			mp.put("comicmNum",numList.get(0).get("comicmNum"));
			mp.put("comdcNum",numList.get(0).get("comdcNum"));
			mp.put("delayConfirmNum",numList.get(0).get("delayConfirmNum"));
			mp.put("delayNum",numList.get(0).get("delayNum"));
			mp.put("delayReplyNum",numList.get(0).get("delayReplyNum"));
			mp.put("delayReplyConfirm",numList.get(0).get("delayReplyConfirm"));
			mp.put("delayCloseNum",numList.get(0).get("delayCloseNum"));
			mp.put("delay", numList.get(0).get("delay"));
			mp.put("code", ActionContext.SUCESS);
		}catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	}
	
	@RequestMapping(value = "/exchange/homeProject/getDCData", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	
	public Map<String, Object> getDCData(@RequestBody String argStr) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String projectName =args.get("projectName")!=null?args.get("projectName").toString():"";
		String whereSql="";
		
		if(projectName!=null&&!"".equals(projectName)) {
			if("@project".equals(projectName)) {
				LoginUser userObj=null;
				try {
					userObj=getSession().getCurrentUser();
					List<String> projectList= userObj.getMyProjects();
					whereSql+=" and (";
					for(int i=0;i<projectList.size();i++) {
						String project=projectList.get(i);
						if(i==0) {
							whereSql+="C_PROJECT_NAME ='"+project+"'";
						}else {
							whereSql+=" or C_PROJECT_NAME ='"+project+"'";
						}
						
					}
					whereSql+=")";
				} catch (AccessDeniedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				whereSql+=" and C_PROJECT_NAME in("+projectName+")";
			}
			
		}
		else {
			LoginUser userObj=null;
			userObj=getSession().getCurrentUser();
			List<String> projectList= userObj.getMyProjects();
			whereSql+=" and (";
			for(int i=0;i<projectList.size();i++) {
				String project=projectList.get(i);
				if(i==0) {
					whereSql+="C_PROJECT_NAME ='"+project+"'";
				}else {
					whereSql+=" or C_PROJECT_NAME ='"+project+"'";
				}
				
			}
			whereSql+=")";
		}
		
		String sql="select TYPE_NAME,count(*) as c from ecm_document "
				+ "where 1=1 "+whereSql+" and (C_ITEM_TYPE='文函' or TYPE_NAME='设计文件') and c_is_released='1' group by TYPE_NAME";
		
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> result=new HashMap<>();
		
		for(Map<String,Object> d : data) {
			if(d.get("TYPE_NAME") !=null) {
				result.put(d.get("TYPE_NAME").toString(), d.get("c"));
			}
		}
		mp.put("code", "1");
		mp.put("data", result);
		
		return mp;
	}
}
