package com.ecm.portal.archive.controller;

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
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.portal.archive.common.Constants;
import com.ecm.portal.controller.ControllerAbstract;
@Controller
public class TransferController extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderPathService folderPathService;
	@Autowired
	private NumberService numberservice;
	/**
	 * 获取配置参数
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/getParameters", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getParameters(@RequestBody String argStr) {
		String[] keys=argStr.split(",");
		Map<String,Object> dataMap=new HashMap<String,Object>();
		for(String key : keys) {
			String params = CacheManagerOper.getEcmParameters().get(key).getValue();
			if(params!=null) {
				String[] vals=params.split(",");
				dataMap.put(key, vals);
			}
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("data", dataMap);
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
	}
	
	@RequestMapping(value = "/dc/getOneParameterValue", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getOneParameterValue(@RequestBody String argStr) {
		
		String params = CacheManagerOper.getEcmParameters().get(argStr).getValue();
		Map<String, Object> mp = new HashMap<String, Object>();
		if(params!=null) {
			String[] vals=params.split(",");
			mp.put("data", vals);
		}
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
	}
	/**
	 * 新建移交单
	 * @param doc
	 * @return
	 */
	@RequestMapping(value = "/dc/newTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newTransfer(@RequestBody EcmDocument doc) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			
			EcmFolder folder= folderService.getObjectByPath(getToken(), doc.getFolderPath());
			doc.setFolderId(folder.getId());
			doc.setAclName(folder.getAclName());
			doc.setCoding(numberservice.getNumber(getToken(), doc.getAttributes()));
			String id = documentService.newObject(getToken(),doc);
			
			mp.put("code", ActionContext.SUCESS);
			mp.put("id", id);
			return mp;
		}catch (Exception e) {
			// TODO: handle exception
			mp.put("code", ActionContext.FAILURE);
			return mp;
		}
	}
	/**
	 * 获取移交单
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/getTransfer", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getTransfers(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			Object pathObj=args.get("folderPath");
			String folderPath=null;
			if(pathObj!=null) {
				folderPath=pathObj.toString();
			}
			EcmFolder folder= folderService.getObjectByPath(getToken(),folderPath);
			List<Map<String, Object>> list = documentService.getObjects(getToken(),
					args.get("gridName").toString(), folder.getId(),
					pager,
					args.get("condition").toString(), args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	/**
	 * 获取设计文件
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/getDesignFiles", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDesignFiles(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			
			List<Map<String, Object>> list = documentService.getObjects(getToken(),
					args.get("gridName").toString(),null,
					pager,
					args.get("condition").toString(), args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	/**
	 * 接收或退回
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/updatestatus", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> nextStep(@RequestBody String argStr) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String status=args.get("status").toString();
		String transferIds=args.get("ids").toString();
		
		List<String> list = JSONUtils.stringToArray(transferIds);
		String strWhere="'"+ String.join("','", list)+"'";
		String sql1="select child_id from ecm_relation where parent_id in("+strWhere+") "
				+ " and name='irel_children' and (DESCRIPTION!='复用'  or DESCRIPTION is null) ";
//		String sql1="select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
//				+ "and b.parent_id in("+strWhere+") and b.name='irel_children' and (b.DESCRIPTION!='复用'  or b.DESCRIPTION is null) " ;//第一层级
		List<Map<String,Object>> childrenId= documentService.getMapList(getToken(), sql1);
		for(int i=0;childrenId!=null&&i<childrenId.size();i++) {
			Map<String,Object> first= childrenId.get(i);
			String childidStr=(String) first.get("child_id");
			EcmDocument doc= documentService.getObjectById(getToken(), childidStr);
			
			String sql2="select child_id from ecm_relation where parent_id ='"+childidStr+"' "
					+ " and name='irel_children' and (DESCRIPTION!='复用'  or DESCRIPTION is null) ";
//			String sql2="select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
//					+ "and b.parent_id ='"+childidStr+"' and b.name='irel_children' and (b.DESCRIPTION!='复用'  or b.DESCRIPTION is null) " ;//第二层
			List<Map<String,Object>> innerFileIds= documentService.getMapList(getToken(), sql2);
			for(int j=0;innerFileIds!=null&&j<innerFileIds.size();j++) {
				Map<String,Object> m= innerFileIds.get(j);
				String innerfileId= m.get("child_id").toString();
				EcmDocument innerDoc= documentService.getObjectById(getToken(), innerfileId);
				Map<String,Object> attr=innerDoc.getAttributes();
				if(status!=null&&status.equals(Constants.ARRANGE)) {
					String folderPathId= folderPathService.getArrangeFolderId(getToken(),attr);
					innerDoc.setFolderId(folderPathId);
					innerDoc.setStatus(Constants.ARRANGE);
					if(doc.getTypeName().equals("卷盒")&&j==0) {
						doc.setFolderId(folderPathId);
						doc.setStatus(Constants.ARRANGE);
						documentService.updateObject(getToken(), doc, null);
					}
					documentService.updateObject(getToken(), innerDoc, null);
				}else if(status!=null&&status.equals(Constants.PRODUCE)) {
					String folderPathId= folderPathService.getDeliveryFolderId(getToken(),attr);
					innerDoc.setFolderId(folderPathId);
					innerDoc.setAclName("acl_all_write");
					innerDoc.setStatus(Constants.PRODUCE);
					if(doc.getTypeName().equals("卷盒")&&j==0) {
						doc.setFolderId(folderPathId);
						doc.setStatus(Constants.PRODUCE);
						documentService.updateObject(getToken(), doc, null);
					}
					documentService.updateObject(getToken(), innerDoc, null);
				}
			}
		
			if(doc.getTypeName().equals("卷盒")) {
				continue;
			}else {
				Map<String,Object> attr=doc.getAttributes();
				if(status!=null&&status.equals(Constants.ARRANGE)) {
					String folderPathId= folderPathService.getArrangeFolderId(getToken(),attr);
					doc.setFolderId(folderPathId);
					doc.setStatus(Constants.ARRANGE);
					documentService.updateObject(getToken(), doc, null);
				}else if(status!=null&&status.equals(Constants.PRODUCE)) {
					String folderPathId= folderPathService.getDeliveryFolderId(getToken(),attr);
					doc.setFolderId(folderPathId);
					doc.setAclName("acl_all_write");
					doc.setStatus(Constants.PRODUCE);
					documentService.updateObject(getToken(), doc, null);
				}
			}
			
		}
		for (String id : list) {
			EcmDocument transf= documentService.getObjectById(getToken(), id);
			if(status!=null&&status.equals(Constants.ARRANGE)) {
				transf.addAttribute("LIFECYCLE_DIR", 2);
				transf.setStatus(Constants.ARRANGE);
			}else {
				transf.addAttribute("LIFECYCLE_DIR", 0);
				transf.setAclName("acl_all_write");
				transf.setStatus(Constants.PRODUCE);
			}
			
			documentService.updateObject(getToken(), transf, null);
		}
		
		mp.put("code", ActionContext.SUCESS);
		return mp;
	
		
	
		
	}
	
	/**
	 * 接收或退回
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/updateBackupStatus", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> updateBackupStatus(@RequestBody String argStr) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String status=args.get("status").toString();
		String backupOrderIds=args.get("ids").toString();
		
		List<String> list = JSONUtils.stringToArray(backupOrderIds);
		
		for (String id : list) {
			EcmDocument transf= documentService.getObjectById(getToken(), id);
			transf.setStatus(status);
			
			documentService.updateObject(getToken(), transf, null);
		}
		
		mp.put("code", ActionContext.SUCESS);
		return mp;
	
		
	}
	
	/**
	 * 归档
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/archived", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> archived(@RequestBody String argStr) throws Exception{
		Map<String, Object> mp = new HashMap<String, Object>();

		List<String> list = JSONUtils.stringToArray(argStr);
//		String strWhere="'"+argStr+"'";//"'"+ String.join("','", list)+"'";
		String strWhere="'"+ String.join("','", list)+"'";
		String sql="select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id in("+strWhere+") and b.name='irel_children' and (b.DESCRIPTION!='复用'  or b.DESCRIPTION is null) " + 
				"union " + 
				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.PARENT_ID "
				+ "and b.PARENT_ID in(" + 
				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id in("+strWhere+")) and b.name='irel_children' and (b.DESCRIPTION!='复用'  or b.DESCRIPTION is null) ";
//				+" union "
//				+ " select child_id from ecm_relation where child_id in("+strWhere+") and name='irel_children' and (DESCRIPTION!='复用'  or DESCRIPTION is null) ";
		List<Map<String,Object>> childrenId= documentService.getMapList(getToken(), sql);
		for(Map<String,Object> childId : childrenId) {
			String childidStr=(String) childId.get("child_id");
			try {
				EcmDocument doc=new EcmDocument();
				doc.setId(childidStr);
				doc.setStatus(Constants.ARCHIVED);
				doc.setAclName("acl_pre_archive");
				documentService.updateObject(getToken(), doc, null);
				
			}catch(NullPointerException nu) {
				continue;
			}catch (Exception e) {
				// TODO: handle exception
				mp.put("message", e.getMessage());
				mp.put("code", ActionContext.FAILURE);
				return mp;
			}
			
		}
		
		for (String id : list) {
//		if(!"".equals(argStr)) {
			documentService.updateStatus(getToken(), id, Constants.ARCHIVED);
//		}
			
		}
		
		mp.put("code", ActionContext.SUCESS);
		return mp;
	
		
	}
}
