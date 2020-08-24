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
		String sqlthereplanNum = "select count(*) as thereplanNum from ecm_document where TYPE_NAME='计划任务' and "+ whereSql +"";
		String sqliedNum = "select count(*) as iedNum from ecm_document where TYPE_NAME='IED' and C_ITEM_STATUS2 = 'Y' and "+ whereSql +"";
		String sqldcNum = "select count(*) as dcNum from ecm_document ed where ed.C_IS_RELEASED=1 and "+whereSql+"";
		String sqlicmNum = "select count(*) as icmNum from ecm_document ed where TYPE_NAME='ICM' and "+ whereSql +"";
		String sqlfeedbackicmNum = "select count(*) as feedbackicmNum from ecm_document ed where TYPE_NAME='ICM' and C_PROCESS_STATUS='新建' and "+ whereSql +"";
		String sqlcomplanNum = "select count(*) as complanNum from ecm_document where TYPE_NAME='计划' and "+ whereSql +" and (C_TO='"+ getLCompany +"')";
		String sqlcomthereplanNum = "select count(*) as comthereplanNum from ecm_document where TYPE_NAME='计划任务' and "+ whereSql +" and (C_COMPANY='"+getLCompany+"')";
		String sqlcomiedNum = "select count(*) as comiedNum from ecm_document where TYPE_NAME='IED' and C_ITEM_STATUS2 = 'Y' and "+ whereSql +" and (C_COMPANY='"+getLCompany+"')";
		String sqlcomicmNum = "select count(*) as comicmNum from ecm_document ed where TYPE_NAME='ICM' and "+ whereSql +" and (C_COMPANY='"+getLCompany+"')";
		String sqlcomdcNum = "select count(*) as comdcNum from ecm_document ed where ed.C_IS_RELEASED=1 and "+whereSql+" and C_COMPANY='"+getSession().getCurrentUser().getCompany()+"'";
		
		String sqlList = "select ("+
				sqlSum+") as projectNum, ("+
				sqlplanNum+") as planNum,("+
				sqlthereplanNum+") as thereplanNum,("+
				sqliedNum+") as iedNum, ("+
				sqldcNum+") as dcNum, ("+
				sqlicmNum+") as icmNum ,("+
				sqlfeedbackicmNum+") as feedbackicmNum,("+ 
				sqlcomplanNum+") as complanNum, (" +
				sqlcomthereplanNum+") as comthereplanNum, ("+ 
				sqlcomiedNum+") as comiedNum, ("+
				sqlcomicmNum+") as comicmNum, ("+
				sqlcomdcNum+") as comdcNum from ecm_document";
		
		try{
			List<Map<String, Object>> numList = ecmDocument.executeSQL(sqlList);
			
			mp.put("projectNum",numList.get(0).get("projectNum"));
			mp.put("planNum", numList.get(0).get("planNum"));
			mp.put("thereplanNum",numList.get(0).get("thereplanNum"));
			mp.put("iedNum",numList.get(0).get("iedNum"));
			mp.put("dcNum",numList.get(0).get("dcNum"));
			mp.put("icmNum",numList.get(0).get("icmNum"));
			mp.put("feedbackicmNum",numList.get(0).get("feedbackicmNum"));
			mp.put("complanNum",numList.get(0).get("complanNum"));
			mp.put("comthereplanNum",numList.get(0).get("comthereplanNum"));
			mp.put("comiedNum",numList.get(0).get("comiedNum"));
			mp.put("comicmNum",numList.get(0).get("comicmNum"));
			mp.put("comdcNum",numList.get(0).get("comdcNum"));
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
				+ "where 1=1 "+whereSql+" and TYPE_NAME in('设计文件','文件传递单','FU申请','FU通知单','作废通知单','CR澄清要求申请单'," + 
						"'CR澄清要求答复单','CR澄清要求关闭单','FCR现场变更申请单','FCR现场变更答复单'," + 
						"'FCR现场变更关闭单','NCR不符合项报告单','NCR不符合项报告答复单','NCR不符合项报告关闭单'," + 
						"'DCR设计变更申请单','DCR设计变更答复单','DCR设计变更关闭单','TCR试验澄清申请单'," + 
						"'TCR试验澄清答复单','TCR试验澄清关闭单','DEN设计变更通知单','DEN设计变更关闭单'," + 
						"'图文传真','会议纪要','设计审查意见','设计审查意见答复') group by TYPE_NAME";
		
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
