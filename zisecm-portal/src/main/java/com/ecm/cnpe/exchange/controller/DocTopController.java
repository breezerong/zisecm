package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class DocTopController extends ControllerAbstract  {
	
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/docTop/docSumNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> docSumNum(@RequestBody String argStr) throws Exception {
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
		
		String sqlSum = "select count(*) as sumNum from ecm_document ed where ed.TYPE_NAME='项目' "
				+ "and ed.NAME in (select eg.NAME from ecm_group eg "
				+ "where id in (select egu.group_id from ecm_group_user egu "
				+ "where egu.USER_ID in (select id from ecm_user "
				+ "where login_name='"
				+  getLoginName +"')))";
		String sqlList="";
		String sqlThreePlanNum="",sqlIEDNum="",sqlICMNum="",sqlreceivedNum="";
		sqlList="select ("+
		sqlSum+") as sumNum, (";
		if(getLCompany.equals("CNPE")==false) {
			whereSql+=" and (C_COMPANY='"+getLCompany+"'";
			//分包商项目信息
			sqlThreePlanNum = "select count(*) as ThreePlanNum from ecm_document "
					+ "where TYPE_NAME='计划任务' and("+whereSql+" or C_TO='"+getLCompany+"'))";;
			sqlIEDNum = "select count(*) as IEDNum from ecm_document "
					+ "where TYPE_NAME='IED' and("+whereSql+"))";
			sqlICMNum = "select count(*) as ICMNum from ecm_document "
					+ "where TYPE_NAME='ICM' and("+whereSql+")) ";
				
			//分包商基本信息
			String sqldcNum = "select count(*) as dcNum from "
					+ "(select a.C_IS_RELEASED,a.C_PROJECT_NAME,b.TO_NAME "
					+ "from ecm_document a, exc_transfer b where a.id=b.doc_id)t where "
					+ "C_IS_RELEASED=1 and "+whereSql+")";
			sqlreceivedNum = "select count(*) as receivedNum from "
					+ "(select a.TYPE_NAME,a.C_PROJECT_NAME,a.C_ITEM_TYPE,b.STAUTS as STAUTS,b.TO_NAME "
					+ "from ecm_document a, exc_transfer b where a.id=b.doc_id)t where C_ITEM_TYPE='文函' and "
					+ "TYPE_NAME!='相关文件' and ( stauts='待接收' and("+whereSql+" or  TO_NAME='"+getLCompany+"')))";
			String sqlSubmissiondcNum="select count(*) as submissiondcNum from ecm_document "
					+ "where C_ITEM_TYPE='文函' and TYPE_NAME!='相关文件' and "
					+ "(status='' or status is null or status='新建') and "+whereSql+")";
			sqlList += ""+
					sqldcNum+") as dcNum,("+
					sqlSubmissiondcNum+") as submissiondcNum,( ";
		}else {
			//CNPE基本信息
			
			String sqldcNum = "select count(*) as dcNum from ecm_document ed where ed.C_IS_RELEASED=1 and "+whereSql+" ";
			sqlreceivedNum = "select count(*) as receivedNum from ecm_document "
					+ "where C_ITEM_TYPE='文函' and TYPE_NAME!='相关文件' and ( status='待确认')";
			String sqldeBlockingNum = "select count(*) as deBlockingNum from ecm_document "
					+ "where TYPE_NAME='设计文件' and ( C_PROCESS_STATUS='申请解锁')";
			String sqldispenseNum = "select count(*) as dispenseNum from ecm_document "
					+ "where C_ITEM_TYPE='文函' and TYPE_NAME!='相关文件' and(status='' or status='新建')";
			String sqlRejectNum = "select count(*) as dcNum from ecm_document "
					+ "where C_ITEM_TYPE='文函' and TYPE_NAME!='相关文件' and ( STATUS='驳回')";
			
			//CNPE项目信息
			sqlThreePlanNum = "select count(*) as ThreePlanNum from ecm_document "
					+ "where TYPE_NAME='计划任务' and("+whereSql+")";
			sqlIEDNum = "select count(*) as IEDNum from ecm_document "
					+ "where TYPE_NAME='IED' and("+whereSql+")";
			sqlICMNum = "select count(*) as ICMNum from ecm_document "
					+ "where TYPE_NAME='ICM' and("+whereSql+") ";
			
			sqlList += ""+
			sqldcNum+") as dcNum,("+
			sqldeBlockingNum+") as deBlockingNum, ("+
			sqldispenseNum+") as dispenseNum, ("+
			sqlRejectNum+") as RejectNum,( ";
		}
		sqlList += ""+sqlThreePlanNum+") as ThreePlanNum,("+
					sqlIEDNum+") as IEDNum, ("+
					sqlICMNum+") as ICMNum,("+
					sqlreceivedNum+") as receivedNum from ecm_document";
		try {
			List<Map<String, Object>> numList = ecmDocument.executeSQL(sqlList);
			//基本信息
			mp.put("sumNum",numList.get(0).get("sumNum"));//项目
			mp.put("dcNum", numList.get(0).get("dcNum"));//文函
			mp.put("receivedNum",numList.get(0).get("receivedNum"));//待接收
			mp.put("deBlockingNum",numList.get(0).get("deBlockingNum"));//待解锁
			mp.put("dispenseNum",numList.get(0).get("dispenseNum"));//分发
			mp.put("RejectNum",numList.get(0).get("RejectNum"));//驳回
			mp.put("submissiondcNum",numList.get(0).get("submissiondcNum"));//待提交
			//项目信息
			mp.put("ThreePlanNum",numList.get(0).get("ThreePlanNum"));
			mp.put("IEDNum",numList.get(0).get("IEDNum"));
			mp.put("ICMNum",numList.get(0).get("ICMNum"));
			
			mp.put("code", ActionContext.SUCESS);
		}catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	}
	@RequestMapping(value = "/exchange/docTop/getDCData", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> DCData(@RequestBody String argStr) throws Exception {
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
		String getLCompany=getSession().getCurrentUser().getCompany();
		if(getLCompany.equals("CNPE")==false) {
			whereSql+=" and (C_COMPANY='"+getLCompany+"' or C_TO='"+getLCompany+"')";
		}
		String sql="select STATUS,count(*) as c from ecm_document "
				+ "where 1=1 "+whereSql+" and TYPE_NAME in('设计文件','文件传递单','FU申请','FU通知单','作废通知单','CR澄清要求申请单'," + 
						"'CR澄清要求答复单','CR澄清要求关闭单','FCR现场变更申请单','FCR现场变更答复单'," + 
						"'FCR现场变更关闭单','NCR不符合项报告单','NCR不符合项报告答复单','NCR不符合项报告关闭单'," + 
						"'DCR设计变更申请单','DCR设计变更答复单','DCR设计变更关闭单','TCR试验澄清申请单'," + 
						"'TCR试验澄清答复单','TCR试验澄清关闭单','DEN设计变更通知单','DEN设计变更关闭单'," + 
						"'图文传真','会议纪要','设计审查意见','设计审查意见答复') group by STATUS";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> result=new HashMap<>();
		for(Map<String,Object> d : data) {
			if(d.get("STATUS") !=null) {
				result.put(d.get("STATUS").toString(), d.get("c"));
			}
		}
		mp.put("code", "1");
		mp.put("data", result);
		return mp;
		
	}
}
