package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.service.ProjectService;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.GroupService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
@RequestMapping("/exchange/project")
public class ProjectManager extends ControllerAbstract  {
	@Autowired
	private ProjectService service;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private GroupService groupService;
	
	@PostMapping("myproject")
	@ResponseBody
	public Map<String,Object> getMyProject(){
		Map<String, Object> result = new HashMap<String,Object>();
		List<ProjectEntity> rlist = null;
		try {
			rlist = service.getMyProject(this.getToken());
			result.put("code", ActionContext.SUCESS);
			result.put("data", rlist);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			result.put("code", ActionContext.SYSTEM_ERROR);
		}		
		
		return result;
	}
	
	@PostMapping("deleteProject")
	@ResponseBody
	public Map<String,Object> deleteProject(@RequestBody String argStr) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();
		List<String> list = JSONUtils.stringToArray(argStr);
		String prjs="";
		//删除文件
		for(String childId : list) {
			
			try {
				EcmDocument doc= documentService.getObjectById(getToken(), childId);
				String projName= doc.getName();
				
				String sql="C_PROJECT_NAME='"+projName+"'";
				List<Map<String,Object>> result= documentService.getObjectMap(getToken(), sql);
				if(result!=null&&result.size()>0) {
					prjs+=projName+",";
					continue;
				}
				EcmGroup group= groupService.getGroupByName(getToken(), projName);
				documentService.deleteObject(getToken(),childId);
				groupService.deleteGroup(getToken(), group.getId());
			}catch(NullPointerException nu) {
				nu.printStackTrace();
				continue;
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			
		}
		mp.put("code", ActionContext.SUCESS);
		if(!"".equals(prjs)) {
			mp.put("msg", "项目删除成功“"+prjs+"”已被使用不能删除！");
		}else {
			mp.put("msg", "项目删除成功！");
		}
		return mp;
	}
	
	@PostMapping("saveProject")
	@ResponseBody
	public Map<String,Object> saveProject(String metaData, MultipartFile uploadFile){
		
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		try {
			documentService.newObject(getToken(), doc,null);
			EcmGroup obj=new EcmGroup();
			obj.createId();
			obj.setName(doc.getName());
			obj.setGroupType("2");
			groupService.newGroup(getToken(), obj);
			
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
	}
	
}