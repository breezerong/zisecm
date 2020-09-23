package com.ecm.cnpe.exchange.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.RelationService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.util.CustomInfo;

@Controller
public class PlanDashController extends ControllerAbstract{
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderPathService folderPathService;
	@Autowired
	private RelationService relationService;
	
	@RequestMapping(value = "/dc/getCNPETPLANNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getCNPETPLANNum(@RequestBody String argStr) throws Exception {							//获取三级计划
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划任务' and sub_type='Activity' "+whereSql;	
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
	
	@RequestMapping(value = "/dc/getSubIEDDash", method = RequestMethod.POST) 		//获取IED图表信息
	@ResponseBody
	public Map<String, Object> getSubIEDDash(@RequestBody String argStr) throws Exception {
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
		String sql="select STATUS ,count(*) as c from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND ((STATUS in('审核中','已驳回','变更中','已变更') or (status ='已生效' and c_is_released='1' and is_current='1'))) AND C_COMPANY='"+company+"' group by STATUS";
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
		String sql="select STATUS ,count(*) as c from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND ((STATUS in('审核中','已驳回','变更中','已变更')) or (status='已生效'and c_is_released='1' and is_current='1')) group by STATUS";
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company = userObj.getCompany();
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND status='已生效' and c_is_released='1' and is_current='1' and c_company = '"+company+"'";
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
	
	@RequestMapping(value = "/dc/getCNPEIEDNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getSubIEDNum(@RequestBody String argStr) throws Exception {				//获取CNPE IED
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND status='已生效' and c_is_released='1' and is_current='1' ";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company = userObj.getCompany();
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' and c_is_released='1' and is_current='1' and c_company='"+company+"'"+whereSql+"AND STATUS = '已生效'";
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND STATUS = '新建'";
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
	
	@RequestMapping(value = "/dc/getCNPEIcmNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getIcmNum(@RequestBody String argStr) throws Exception {							//获取当前ICM总数
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='ICM' "+whereSql;
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
	
	@RequestMapping(value = "/dc/getSubIcmNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getSUBIcmNum(@RequestBody String argStr) throws Exception {							//获取当前ICM总数
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
		String sql="select count(*) as count from ecm_document WHERE (C_CODE5='N' OR C_CODE6='N') and  TYPE_NAME='ICM'"+whereSql;
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
	@RequestMapping(value = "/dc/getSubDCNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getSubDCNum(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		LoginUser userObj=null;
		userObj=getSession().getCurrentUser();
		String company= userObj.getCompany();
		//String name = userObj.getLoginName();
		String sql = "select count(*) as dcNum from ecm_document ed where ed.C_IS_RELEASED=1 and"
				+ " (C_ITEM_TYPE='文函')  and"+whereSql+"and c_company='"+company+"'";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			//System.out.println(d.get("count").toString()+"取到的数据是：");
			result.put("num", d.get("dcNum"));
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
		/*String sql="select count(*) as count from "
			     + "(select a.C_COMPANY,a.C_IS_RELEASED,a.C_PROJECT_NAME,b.TO_NAME "
			     + "from ecm_document a, exc_transfer b where a.id=b.doc_id)t where "
			     + "C_IS_RELEASED=1  and("+whereSql+" or  TO_NAME='"+getLCompany+"')";/*/
		String companys = CustomInfo.OwnerCompany;
		String sql = "select count(*) as dcNum from ecm_document ed where ed.C_IS_RELEASED=1 and"
				+ " (C_ITEM_TYPE='文函' )and "+whereSql+" ";
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			//System.out.println(d.get("count").toString()+"取到的数据是：");
			result.put("num", d.get("dcNum"));
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划任务' and sub_type='Activity' "+whereSql;
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
		String sql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划' "+whereSql;
		List<Map<String, Object>> data= documentService.getMapList(getToken(), sql);
		Map<String,Object> d = new HashMap<String,Object>();
		Map<String,Object> result=new HashMap<String, Object>();
		for(int i=0;i<data.size();i++) {
			d = data.get(i);
			result.put("num", d.get("count"));
		}
		mp.put("code", "1");
		mp.put("data", result);
	return mp;
		}
	
	@RequestMapping(value = "/dc/getSubPlanNum", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getSubPlanNum(@RequestBody String argStr) throws Exception {							//获取当前文函总数
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
		String Tlansql="select count(*) as count from ecm_document WHERE TYPE_NAME='计划任务' "+whereSql+"and sub_type='Activity' ";
		String Iedsql="select count(*) as count from ecm_document WHERE TYPE_NAME='IED' "+whereSql+"AND status='已生效' and c_is_released='1' and is_current='1' and c_company ='"+company+"'";
		String Icmsql="select count(*) as count from ecm_document WHERE TYPE_NAME='ICM'"+whereSql+"and (C_CODE5='N' OR C_CODE6='N')";
		String sqlList = "select ("+
				Projectsql+") as projectNum, ("+
				Plansql+") as planNum,("+
				Tlansql+") as thereplanNum,("+
				Iedsql+") as iedNum,  ("+
				Icmsql+") as icmNum";
		try{
			List<Map<String, Object>> numList = ecmDocument.executeSQL(sqlList);
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
	
	
	@RequestMapping(value = "/dc/newPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newDocumentOrSubDoc(String metaData, MultipartFile uploadFile) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmContent en = null; 
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		String name =args.get("C_PROJECT_NAME").toString();
		String projectName = args.get("NAME").toString();
		String sql="select * from ecm_Document where Type_Name='计划' and C_PROJECT_NAME='"+name+"' and Name='"+projectName+"'";
		System.out.println(sql);
		List<Map<String,Object>> res = documentService.getMapList(getToken(), sql);
		if(res.size()>0) {
			mp.put("code", 2);
			return mp;
		}
		
		if (uploadFile != null) {
			en = new EcmContent();
			en.setName(uploadFile.getOriginalFilename());
			en.setContentSize(uploadFile.getSize());
			en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
			en.setInputStream(uploadFile.getInputStream());
		}
		Object fid= args.get("folderId");
		String folderId="";
		if(fid==null) {
			folderId= folderPathService.getFolderId(getToken(), doc.getAttributes(), "3");
		}else {
			folderId=fid.toString();
		}
		doc.setStatus("新建");
		EcmFolder folder= folderService.getObjectById(getToken(), folderId);
		doc.setFolderId(folderId);
		doc.setAclName(folder.getAclName());

		
		String id ="";
		
		if(args.get("parentDocId")!=null&&!"".equals(args.get("parentDocId"))) {
			String relationName="irel_children";
			relationName=args.get("relationName")!=null
					&&!"".equals(args.get("relationName").toString())
					?args.get("relationName").toString():"irel_children";
			EcmDocument pdoc=documentService.getObjectById(getToken(), args.get("parentDocId").toString());
			doc.addAttribute("C_PROJECT_NAME", pdoc.getAttributeValue("C_PROJECT_NAME")!=null?
					pdoc.getAttributeValue("C_PROJECT_NAME").toString():"");
			id = documentService.newObject(getToken(),doc,en);
			EcmRelation relation=new EcmRelation();
			relation.setParentId(args.get("parentDocId").toString());
			
			relation.setChildId(id);
			relation.setName(relationName);
			try {
				relationService.newObject(getToken(), relation);
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message",e.getMessage());
				return mp;
			}
		}else {
			id= documentService.newObject(getToken(),doc,en);
		}
		
		
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}
	
		}
