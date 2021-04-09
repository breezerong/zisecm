package com.ecm.portal.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.FolderService;
import com.ecm.portal.service.DcFolderService;

@Controller
public class DocFolderController extends ControllerAbstract {
	
	@Autowired
	private DcFolderService dcFolderService;
	
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/getFoldersByPath", method = RequestMethod.POST)
	public Map<String, Object> getFoldersByPath(@RequestBody String folderPath) {
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			List<EcmFolder> list = null;
			list = dcFolderService.getFoldersByParentPathWithPermission(getToken(),folderPath);
		
			if(list!=null&&list.size()>3) {
				LoginUser user = this.getSession().getCurrentUser();
				List<EcmFolder> tempList=list;
				int index = IntStream.range(0, list.size())
			     .filter(i -> tempList.get(i).getName().equals("部门文件 - "+user.getDepartment()))
			     .findFirst().orElse(-1);
				
//				int index= list.indexOf("部门文件 - "+user.getDepartment());
				if(index>2) {
					Collections.swap(list,index,2);
				}
				
			}
			
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
}
