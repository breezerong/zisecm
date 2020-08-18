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
public class PlanDashController extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	@RequestMapping(value = "/dc/getIEDDash", method = RequestMethod.POST) 
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
	public Map<String, Object> getIEDNum(@RequestBody String argStr) throws Exception {
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
	
}
