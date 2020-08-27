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
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class PlanDashController extends ControllerAbstract{
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private DocumentService documentService;
	
	
	
	@RequestMapping(value = "/dc/getCNPETPLANNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getCNPETPLANNum(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划任务' "+whereSql;	
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
		}
	
	
	
	
	
	@RequestMapping(value = "/dc/getIEDDash", method = RequestMethod.POST) 		//获取IED图表信息
	@ResponseBody
	public Map<String, Object> getIEDDash(@RequestBody String argStr) throws Exception {
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
		String sql="select STATUS ,count(*) as c from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND STATUS in('审核中','已生效','已驳回','变更中','已变更','已生效') group by STATUS";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> d = new HashMap();
		Map<String,Object> result=new HashMap<>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			result.put(d.get("STATUS").toString(), d.get("c"));
		}
		mp.put("code", "1");
		mp.put("data", result);
		return mp;	
	}
	@RequestMapping(value = "/dc/getIEDNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getIEDNum(@RequestBody String argStr) throws Exception {				//获取当前权限内所有IED总数
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND STATUS in('审核中','已生效','已驳回','变更中','已变更','已生效')";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	}
	
	@RequestMapping(value = "/dc/getPublishedIED", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getPubNum(@RequestBody String argStr) throws Exception {				//获取当前权限内所有IED总数
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND STATUS = '已生效'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	}
	
	@RequestMapping(value = "/dc/getPendingSubmitIED", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getPendingNum(@RequestBody String argStr) throws Exception {				//获取当前权限内所有IED总数
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND STATUS = '待提交'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	}
	@RequestMapping(value = "/dc/getPendingIED", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getPending(@RequestBody String argStr) throws Exception {				//获取当前权限内所有IED总数
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND STATUS = '审核中'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	}
	
	
	@RequestMapping(value = "/dc/getRejectIED", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getRejectIED(@RequestBody String argStr) throws Exception {				//获取当前权限内所有IED总数
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND STATUS = '已驳回'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	}
	
	@RequestMapping(value = "/dc/getIcmNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getIcmNum(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		String sql="select count(*) as count from ecm_document WHERE C_ITEM_TYPE='文函' and C_IS_RELEASED = 1 "+whereSql;
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	
		}
	
	
	
	@RequestMapping(value = "/dc/getDCNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getDCNum(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		String sql="select count(*) as count from ecm_document WHERE C_ITEM_TYPE='文函' and TYPE_NAME!='相关文件' "+whereSql;
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	
		}
	@RequestMapping(value = "/dc/getTPLANNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getTPLANNum(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company= userObj.getCompany();
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划任务' "+whereSql+"and c_company ='"+company+"'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
		}
	
	@RequestMapping(value = "/dc/getPlanNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getPlanNum(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company= userObj.getCompany();
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划' "+"and C_TO='"+company+"'"+whereSql;
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
		}
	
	
	
	
	@RequestMapping(value = "/dc/getProjectNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getProjectNum(@RequestBody String argStr) throws Exception {							//获取当前项目总数
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String name= userObj.getLoginName();
		String sql="select count(*) as count from ecm_document ed where ed.TYPE_NAME='项目' and ed.NAME in (select eg.NAME from ecm_group eg where id in (select egu.group_id from ecm_group_user egu where egu.USER_ID in "
				+ "(select id from ecm_user where login_name='"+ name + "')))";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
		}
	@RequestMapping(value = "/dc/getSubIcm", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getSubIcm(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company= userObj.getCompany();
		String name = userObj.getLoginName();
		
		String Projectsql="select count(*) as count from ecm_document ed where ed.TYPE_NAME='项目' and ed.NAME in (select eg.NAME from ecm_group eg where id in (select egu.group_id from ecm_group_user egu where egu.USER_ID in "
				+ "(select id from ecm_user where login_name='"+ name + "')))";
		String Plansql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划' "+whereSql;
		String Tlansql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划任务' "+whereSql+"and c_company ='"+company+"'";
		String Iedsql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' and C_IS_RELEASED ='1' and IS_CURRENT ='1' "+whereSql+"AND STATUS = '已生效'"+"AND C_COMPANY='"+company+"'";
		String Icmsql="select count(*) as count from ecm_document WHERE TYPE_NAME='ICM'"+whereSql+"AND C_COMPANY='"+company+"'";
		String sqlList = "select ("+
				Projectsql+") as projectNum, ("+
				Plansql+") as planNum,("+
				Tlansql+") as thereplanNum,("+
				Iedsql+") as iedNum,  ("+
				Icmsql+") as icmNum";
		try{
			List<Map<String, Object>> numList = ecmDocument.executeSQL(sqlList);
			System.out.println(Iedsql);
			mp.put("projectNum",numList.get(0).get("projectNum"));
			mp.put("planNum", numList.get(0).get("planNum"));
			mp.put("thereplanNum",numList.get(0).get("thereplanNum"));
			mp.put("iedNum",numList.get(0).get("iedNum"));
			mp.put("dcNum",numList.get(0).get("dcNum"));
			mp.put("icmNum",numList.get(0).get("icmNum"));
			mp.put("feedbackicmNum",numList.get(0).get("feedbackicmNum"));
			System.out.println(sqlList);
		}catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
	return mp;
		}
	
	
	
	@RequestMapping(value = "/dc/getSubPublishedIED", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getSubPubNum(@RequestBody String argStr) throws Exception {				//获取当前权限内所有IED总数
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company = userObj.getCompany();
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' AND C_IS_RELEASED='1' AND IS_CURRENT='1' AND C_COMPANY='"+company+"'"+whereSql+"AND STATUS = '已生效'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		System.out.println(sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			System.out.println(d.get("count").toString());
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
	}
	
	
		}
