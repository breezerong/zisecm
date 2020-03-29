package com.ecm.portal.controller;

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
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.RelationService;
@Controller
public class DocTransfer extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderPathService folderPathService;
	@Autowired
	private RelationService relationService;
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
	/**
	 * 通过id查找relation中childId对应的document
	 * @return
	 */
	@RequestMapping(value = "/dc/getDocuByRelationParentId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDocumentByRelationParentId(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String conditionWhere="";
			if(args.get("condition")!=null) {
				conditionWhere=args.get("condition").toString();
			}
			String sql = "select * from (select b.*,a.id as RELATION_ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX"
					+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
					+ " and a.PARENT_ID='"+args.get("id").toString()+"' "+conditionWhere+") t order by ORDER_INDEX,CREATION_DATE";
			List<Map<String, Object>>  list = documentService.getMapList(getToken(), sql,pager);
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
			
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	
	}
	/**
	 * 创建案卷或文件
	 * @param metaData
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/newInnerDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newDocument(String metaData, MultipartFile uploadFile) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmContent en = null; 
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		if (uploadFile != null) {
			en = new EcmContent();
			en.setName(uploadFile.getOriginalFilename());
			en.setContentSize(uploadFile.getSize());
			en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
			en.setInputStream(uploadFile.getInputStream());
		}
//		String folderPath=null;
//		Object pathObj=args.get("folderPath");
//		if(pathObj!=null) {
//			folderPath=pathObj.toString();
//		}
//		EcmFolder folder= folderService.getObjectByPath(getToken(),folderPath);
		Object fid= args.get("folderId");
		String folderId="";
//		if(fid==null) {
//			folderId= folderPathService.getFolderId(getToken(), doc.getObjectValues(), "1");
//		}else {
//			folderId=fid.toString();
//		}
		EcmFolder folder= folderService.getObjectByPath(getToken(), (String)args.get("folderPath"));
		folderId=folder.getId();
//		EcmFolder folder= folderService.getObjectById(getToken(), folderId);
		doc.setFolderId(folderId);
		doc.setAclName(folder.getAclName());
//		if(doc.getSubType()!=null&&("卷".equals(doc.getSubType())||"盒".equals(doc.getSubType()))) {
//			doc.setCoding(numberService.getNumber(getToken(), doc.getAttributes()));
//		}
		
		String id ="";
		if(args.get("transferId")!=null&&!"".equals(args.get("transferId"))) {
//			EcmDocument pdoc= documentService.getObjectById(getToken(), args.get("transferId").toString());
//			doc.addAttribute("C_ARCHIVE_NUM", pdoc.getCoding());
//			doc; pdoc.getCoding();
			id = documentService.newObject(getToken(),doc,en);
			EcmRelation relation=new EcmRelation();
			relation.setParentId(args.get("transferId").toString());
			
			relation.setChildId(id);
			relation.setName("irel_transmit");
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
	
	/**
	 * 创建案卷或文件
	 * @param metaData
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/createTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newTransfer(String metaData, MultipartFile uploadFile) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmContent en = null; 
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		if (uploadFile != null) {
			en = new EcmContent();
			en.setName(uploadFile.getOriginalFilename());
			en.setContentSize(uploadFile.getSize());
			en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
			en.setInputStream(uploadFile.getInputStream());
		}
		Object fid= args.get("folderId");
		String folderId="";
//		if(fid==null) {
//			folderId= folderPathService.getFolderId(getToken(), doc.getObjectValues(), "1");
//		}else {
//			folderId=fid.toString();
//		}
		EcmFolder folder= folderService.getObjectByPath(getToken(), (String)args.get("folderPath"));
		folderId=folder.getId();
//		EcmFolder folder= folderService.getObjectById(getToken(), folderId);
		doc.setFolderId(folderId);
		doc.setAclName(folder.getAclName());
		
		String id ="";
		
		
		id= documentService.newObject(getToken(),doc,en);
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}
}
