package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;
@Controller
public class DCReportController  extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/dc/getNoReceiveData", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getNoReceiveData(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		String condition=args.get("condition")!=null?args.get("condition").toString():"";
		
		if(condition!=null&&condition.contains("@company")) {
			
			condition=condition.replaceAll("@company", getSession().getCurrentUser().getCompany());
	    }
		if(condition!=null&&condition.contains("@project")) {
			List<String> projectList= getSession().getCurrentUser().getMyProjects();
			
			if(projectList==null||projectList.size()==0) {
				condition=condition.replaceAll("'@project'", "''");
				condition=condition.replaceAll("@project", "''");
			}else {
				String whereProject=" (";
				for(int i=0;i<projectList.size();i++) {
					String project=projectList.get(i);
					if(i==0) {
						whereProject+="C_PROJECT_NAME ='"+project+"'";
					}else {
						whereProject+=" or C_PROJECT_NAME ='"+project+"'";
					}
					
				}
				whereProject+=")";
				if(condition.contains("C_PROJECT_NAME in(@project)")) {
					condition=condition.replaceAll("C_PROJECT_NAME in(@project)", whereProject);
				} 
				if(condition.contains("C_PROJECT_NAME in (@project)")) {
					condition=condition.replaceAll("C_PROJECT_NAME in (@project)", whereProject);
				} 
				if(condition.contains("C_PROJECT_NAME in (@project) ")) {
					condition=condition.replaceAll("C_PROJECT_NAME in (@project) ", whereProject);
				} 
				if(condition.contains("C_PROJECT_NAME='@project'")) {
					condition=condition.replaceAll("C_PROJECT_NAME='@project'", whereProject);
				}
				if(condition.contains("C_PROJECT_NAME ='@project'")) {
					condition=condition.replaceAll("C_PROJECT_NAME ='@project'", whereProject);
				}
				if(condition.contains("C_PROJECT_NAME = '@project'")) {
					condition=condition.replaceAll("C_PROJECT_NAME = '@project'", whereProject);
				}
				if(condition.contains("C_PROJECT_NAME = '@project' ")) {
					condition=condition.replaceAll("C_PROJECT_NAME = '@project' ", whereProject);
				}
			}
			
		}
		
		String sql="select * from (" + 
				"select a.*,b.id as rid from ecm_document a " + 
				"left join ecm_document b on a.CODING=b.C_OTHER_CODING " + 
				"where a.TYPE_NAME='图文传真' and a.C_ITEM_STATUS1='是' and a.C_ITEM1_DATE<=dateadd(day, -1, getdate()) " + 
				") t where rid is null "+condition;
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql,pager);
		mp.put("pager", pager);
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", data);
		return mp;
	}
	
	@RequestMapping(value = "/dc/getDCReportData", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDCReportData(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String projectName =args.get("projectName")!=null?args.get("projectName").toString():"";
		String startDate=args.get("startDate")!=null?args.get("startDate").toString():"";
		String endData=args.get("endDate")!=null?args.get("endDate").toString():"";
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
		if(startDate!=null&&!"".equals(startDate)) {
			whereSql+=" and c_item_date >='"+startDate+"'";
		}
		if(endData!=null&&!"".equals(endData)) {
			whereSql+=" and c_item_date <='"+endData+"'";
		}
//		String sql="select TYPE_NAME,count(*) as c from ecm_document "
//				+ "where 1=1 "+whereSql+" and TYPE_NAME in('传递单', '图纸文件', 'FU申请', 'FU通知', '作废通知', '接口意见', '接口传递', "
//				+ "'接口手册', 'CR澄清要求', 'CR答复', 'FCR现场变更申请', 'FCR现场变更答复', 'NCR不符合项报告', "
//				+ "'NCR不符合项报告答复', '设计变更通知DEN', '图文传真', '会议纪要', '设计审查意见', '设计审查意见答复') group by TYPE_NAME";
		
		String sql="select TYPE_NAME,count(*) as c from ecm_document "
				+ "where 1=1 "+whereSql+" and TYPE_NAME in('文件传递单','FU申请','FU通知单','作废通知单','CR澄清要求申请单'," + 
						"'CR澄清要求答复单','CR澄清要求关闭单','FCR现场变更申请单','FCR现场变更答复单'," + 
						"'FCR现场变更关闭单','NCR不符合项报告单','NCR不符合项报告答复单','NCR不符合项报告关闭单'," + 
						"'DCR设计变更申请单','DCR设计变更答复单','DCR设计变更关闭单','TCR试验澄清申请单'," + 
						"'TCR试验澄清答复单','TCR试验澄清关闭单','DEN设计变更通知单','DEN设计变更关闭单'," + 
						"'图文传真','会议纪要','设计审查意见','设计审查意见答复') group by TYPE_NAME";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> result=new HashMap<>();
		for(Map<String,Object> d : data) {
			result.put(d.get("TYPE_NAME").toString(), d.get("c"));
		}
		mp.put("code", "1");
		mp.put("data", result);
		return mp;
	}
	
	@RequestMapping(value = "/dc/getDesignData", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDesignData(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String projectName =args.get("projectName")!=null?args.get("projectName").toString():"";
		String startDate=args.get("startDate")!=null?args.get("startDate").toString():"";
		String endData=args.get("endDate")!=null?args.get("endDate").toString():"";
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
		if(startDate!=null&&!"".equals(startDate)) {
			whereSql+=" and c_item_date >='"+startDate+"'";
		}
		if(endData!=null&&!"".equals(endData)) {
			whereSql+=" and c_item_date <='"+endData+"'";
		}
//		String sql="select TYPE_NAME,count(*) as c from ecm_document "
//				+ "where 1=1 "+whereSql+" and TYPE_NAME in('传递单', '图纸文件', 'FU申请', 'FU通知', '作废通知', '接口意见', '接口传递', "
//				+ "'接口手册', 'CR澄清要求', 'CR答复', 'FCR现场变更申请', 'FCR现场变更答复', 'NCR不符合项报告', "
//				+ "'NCR不符合项报告答复', '设计变更通知DEN', '图文传真', '会议纪要', '设计审查意见', '设计审查意见答复') group by TYPE_NAME";
		
		String sql="select count(*) as c from ecm_document "
				+ "where 1=1 "+whereSql+" and TYPE_NAME ='设计文件' and c_is_released=1";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> result=new HashMap<>();
		for(Map<String,Object> d : data) {
			result.put("DEnum", d.get("c"));
		}
		mp.put("code", "1");
		mp.put("data", result);
		return mp;
	}
	
	@RequestMapping(value = "/dc/getSubDesignData", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getSubDesignData(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String projectName =args.get("projectName")!=null?args.get("projectName").toString():"";
		String startDate=args.get("startDate")!=null?args.get("startDate").toString():"";
		String endData=args.get("endDate")!=null?args.get("endDate").toString():"";
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
		if(startDate!=null&&!"".equals(startDate)) {
			whereSql+=" and c_item_date >='"+startDate+"'";
		}
		if(endData!=null&&!"".equals(endData)) {
			whereSql+=" and c_item_date <='"+endData+"'";
		}
//		String sql="select TYPE_NAME,count(*) as c from ecm_document "
//				+ "where 1=1 "+whereSql+" and TYPE_NAME in('传递单', '图纸文件', 'FU申请', 'FU通知', '作废通知', '接口意见', '接口传递', "
//				+ "'接口手册', 'CR澄清要求', 'CR答复', 'FCR现场变更申请', 'FCR现场变更答复', 'NCR不符合项报告', "
//				+ "'NCR不符合项报告答复', '设计变更通知DEN', '图文传真', '会议纪要', '设计审查意见', '设计审查意见答复') group by TYPE_NAME";
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company= userObj.getCompany();
		String sql="select count(*) as c from ecm_document "
				+ "where 1=1 "+whereSql+" and TYPE_NAME ='设计文件' and c_is_released=1 and c_company ='"+company+"'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> result=new HashMap<>();
		for(Map<String,Object> d : data) {
			result.put("DEnum", d.get("c"));
		}
		mp.put("code", "1");
		mp.put("data", result);
		return mp;
	}
	
	
}
