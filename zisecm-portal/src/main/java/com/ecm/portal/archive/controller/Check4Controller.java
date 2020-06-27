package com.ecm.portal.archive.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmQuery;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.QueryService;
import com.ecm.core.service.RelationService;
import com.ecm.portal.archive.entity.EcmCheck4;
import com.ecm.portal.archive.services.ArchiveFolderService;
import com.ecm.portal.archive.services.ArchiveRelationService;
import com.ecm.portal.archive.services.Check4Service;
import com.ecm.portal.archive.services.DepartmentServiceEx;
import com.ecm.portal.archive.services.ImportService;
import com.ecm.portal.archive.services.ToolsService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class Check4Controller extends ControllerAbstract{


	
	@Autowired
	private QueryService queryService;
	
	@Autowired
	private Check4Service check4Service;
	
	
	
	
	@RequestMapping(value = "/record/getArchiveClassic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getArchiveClassic() throws AccessDeniedException{
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmQuery q = queryService.getObjectByName(getToken(), "档案类目");
			List<Map<String, Object>> list = queryService.executeSQL(getToken(), q.getSqlString());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
	}
	
	@RequestMapping(value = "/record/getCheck4Data", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCheck4Data(@RequestBody String argStr) throws AccessDeniedException{
		Map<String, Object> mp = new HashMap<String, Object>();
		String msg;
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmCheck4> list = check4Service.startCheck4(getToken(), args.get("classic").toString(), args.get("startDate").toString(), args.get("endDate").toString());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
	}

}
	
