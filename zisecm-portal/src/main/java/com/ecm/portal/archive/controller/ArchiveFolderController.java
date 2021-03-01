package com.ecm.portal.archive.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring5.processor.SpringUErrorsTagProcessor;

import com.alibaba.fastjson.JSONArray;
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
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.NoTakeNumberRuleException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.RelationService;
import com.ecm.icore.service.IEcmSession;
import com.ecm.portal.archive.common.ChildrenObjAction;
import com.ecm.portal.archive.common.Constants;
import com.ecm.portal.archive.services.ArchiveFolderService;
import com.ecm.portal.archive.services.ArchiveRelationService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class ArchiveFolderController extends ControllerAbstract{
	private static final Logger logger = LoggerFactory.getLogger(ArchiveFolderController.class);
//	@Autowired
//	private EcmFolderMapper ecmFolder;
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ArchiveFolderService archiveFolderService;
	@Autowired
	private ArchiveRelationService archiveRelationService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderPathService folderPathService;
	@Autowired
	private NumberService numberService;
	
//	public Map<String,Object> getInnerFile(@RequestBody String coding){
//		Map<String, Object> mp = new HashMap<String, Object>();
//		
//	}
	/**
	 * 添加复用
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/addReuseToVolume", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReuseToVolume(@RequestBody String argStr) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String pid=args.get("id").toString();
		String cids=args.get("cids").toString();
		List<String> designIds=JSONUtils.stringToArray(cids);
		for(int i=0;designIds!=null&&i<designIds.size();i++) {
			String id=designIds.get(i).toString();
			EcmRelation relation=new EcmRelation();
			relation.setParentId(pid);
			relation.setChildId(id);
			relation.setName("irel_children");
			relation.setDescription("复用");
			try {
				relationService.newObject(getToken(), relation);
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	/**
	 * 创建案卷或文件
	 * @param metaData
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/newArchiveOrDocument", method = RequestMethod.POST)
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
		if(fid==null) {
			folderId= folderPathService.getFolderId(getToken(), doc.getAttributes(), "1");
		}else {
			folderId=fid.toString();
		}
		EcmFolder folder= folderService.getObjectById(getToken(), folderId);
		doc.setFolderId(folderId);
		doc.setAclName(folder.getAclName());
//		if(doc.getSubType()!=null&&("卷".equals(doc.getSubType())||"盒".equals(doc.getSubType()))) {
//			doc.setCoding(numberService.getNumber(getToken(), doc.getAttributes()));
//		}
		
		String id ="";
		if(args.get("transferId")!=null&&!"".equals(args.get("transferId"))) {
			EcmDocument pdoc= documentService.getObjectById(getToken(), args.get("transferId").toString());
			if(pdoc!=null && "案卷".equals(pdoc.getAttributeValue("C_ITEM_TYPE"))) {
				doc.addAttribute("IS_CHILD", 1);
			}
			try {
				id = documentService.newObject(getToken(),doc,en);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message",e.getMessage());
				return mp;
			}
			EcmRelation relation=new EcmRelation();
			relation.setParentId(args.get("transferId").toString());
			
			relation.setChildId(id);
			relation.setName("irel_children");
			try {
				relationService.newObject(getToken(), relation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message",e.getMessage());
				return mp;
			}
		}else {
			try {
				id= documentService.newObject(getToken(),doc,en);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message",e.getMessage());
				return mp;
			}
		}
		
		
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}
	/**
	 * 获取文件夹下文件
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/getInnerFolderDocuments", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocuments(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
					args.get("gridName").toString(), args.get("folderId").toString(),
					pager,
					args.get("condition").toString(), (String)args.get("orderBy"));
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		
		return mp;
	}
	
	/**
	 * 获取未组卷或未装盒文件
	 * @param argStr
	 * @return
	 * @throws AccessDeniedException
	 */
	@RequestMapping(value = "/dc/getOutDocuments", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getOutDocuments(@RequestBody String argStr) throws AccessDeniedException{
		
		
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		List<Map<String, Object>> list = archiveFolderService.getOutObjects(getToken(),
				args.get("gridName").toString(), args.get("folderId").toString(),
				pager,
				args.get("condition").toString(), args.get("orderBy").toString());
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("data", list);
		mp.put("pager", pager);
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
	}
	/**
	 * 通过id查找relation中childId对应的document
	 * @return
	 */
	@RequestMapping(value = "/dc/getAllDocuByRelationParentId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getAllDocumentByRelationParentId(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String sql = "select b.*,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID"
					+ " from ecm_relation a, ecm_document b where a.name='irel_children' and  (a.DESCRIPTION!='复用' or a.DESCRIPTION is null) and  a.CHILD_ID=b.ID "
					+ " and a.PARENT_ID='"+args.get("id").toString()+"' order by b.CREATION_DATE";
			List<Map<String, Object>>  list = documentService.getMapList(getToken(), sql);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
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
			List<Map<String, Object>>  list=new ArrayList<Map<String,Object>>();
			if(!"".equals(args.get("id").toString())) {
				String sql = "select b.*,a.id as RELATION_ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX"
						+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
						+ " and a.PARENT_ID='"+args.get("id").toString()+"' "+conditionWhere+" order by a.ORDER_INDEX,b.CREATION_DATE";
				list = documentService.getMapList(getToken(), sql,pager);
				
			}
			
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
	 * 通过id查找relation中childId对应的document
	 * @return
	 */
	@RequestMapping(value = "/dc/getAllDocusByRelationParentId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getAllDocuByRelationParentId(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String conditionWhere="";
			if(args.get("condition")!=null) {
				conditionWhere=args.get("condition").toString();
			}
			List<Map<String, Object>>  list=new ArrayList<Map<String,Object>>();
			if(!"".equals(args.get("id").toString())) {
				String sql = "select b.*,a.id as RELATION_ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX"
						+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
						+ " and a.PARENT_ID='"+args.get("id").toString()+"' "+conditionWhere+" order by a.ORDER_INDEX,b.CREATION_DATE";
				list = documentService.getMapList(getToken(), sql);
				
			}
			
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	
	}
	/**
	 * 上移
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/moveUp", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> moveUp(@RequestBody String argStr) throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String parentId= args.get("parentId").toString();
		String childId=args.get("childId").toString();
		try {
			boolean isok= archiveRelationService.moveUp(getToken(), parentId, childId);
			if(isok) {
				mp.put("code", ActionContext.SUCESS);
				return mp;
			}
		}catch (Exception e) {
			mp.put("code", ActionContext.FAILURE);
			// TODO: handle exception
			if(e.getMessage().equals("当前数据已为第一条")) {
				
				mp.put("message", e.getMessage());
				
			}else {
				mp.put("message", "移动失败请联系管理员！");
			}
			e.printStackTrace();
			return mp;
		}
		return mp;
		

		
	}
	/**
	 * 下移
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/moveDown", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> moveDown(@RequestBody String argStr) throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String parentId= args.get("parentId").toString();
		String childId=args.get("childId").toString();
		try {
			boolean isok= archiveRelationService.moveDown(getToken(), parentId, childId);
			if(isok) {
				mp.put("code", ActionContext.SUCESS);
				return mp;
			}
		}catch (Exception e) {
			mp.put("code", ActionContext.FAILURE);
			// TODO: handle exception
			if(e.getMessage().equals("当前数据已为最后一条")) {
				
				mp.put("message", e.getMessage());
				
			}else {
				mp.put("message", "移动失败请联系管理员！");
			}
			e.printStackTrace();
			return mp;
		}
		return mp;
	}
	/**
	 * 上架
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/putInStorage", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String,Object> putInStorage(@RequestBody String argStr){
		
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			for(int i=0;list!=null&&i<list.size();i++) {
				String boxId=list.get(i);
				EcmDocument box= documentService.getObjectById(getToken(), boxId);
				String sql1="select child_id from ecm_relation where parent_id='"+boxId+"' "
						+ " and name='irel_children' and (DESCRIPTION!='复用'  or DESCRIPTION is null) ";
				//第一层级
				List<Map<String,Object>> childrenId= documentService.getMapList(getToken(), sql1);
				for(int j=0;childrenId!=null&&j<childrenId.size();j++) {
					Map<String,Object> first= childrenId.get(j);
					String childidStr=(String) first.get("child_id");
					EcmDocument doc= documentService.getObjectById(getToken(), childidStr);
					
					Map<String,Object> attr=doc.getAttributes();
					String folderPathId= folderPathService.getReleaseFolderId(getToken(),attr);
					doc.setFolderId(folderPathId);
					doc.setStatus(Constants.USING);
					doc.addAttribute("C_STORE_STATUS", "在库");
					doc.addAttribute("C_INSTORE_DATE", new Date());
					doc.addAttribute("C_INSTORE_USER", this.getSession().getCurrentUser().getUserName());
					String docSsecurity=doc.getSecurityLevel();
					if(docSsecurity!=null) {
						/**
						 * 内部公开：acl_release_public
							受限：acl_release_control
							普通商密、核心商密：acl_release_scuret
						 * 
						 */
						if("受限".equals(docSsecurity)) {
							doc.setAclName("acl_release_control");
						}else if("普通商密".equals(docSsecurity)) {
							doc.setAclName("acl_release_scuret");
						}else if("核心商密".equals(docSsecurity)) {
							doc.setAclName("acl_release_scuret");
						}else {
							doc.setAclName("acl_release_public");
						}
					}else {
						doc.setAclName("acl_release_public");
					}
					documentService.updateObject(getToken(), doc, null);
					if(j==0) {
						box.setFolderId(folderPathId);
						box.setStatus(Constants.USING);
						box.addAttribute("C_INSTORE_DATE", new Date());
						box.addAttribute("C_INSTORE_USER", this.getSession().getCurrentUser().getUserName());
						String boxSsecurity=box.getSecurityLevel();
						if(boxSsecurity!=null) {
							/**
							 * 内部公开：acl_release_public
								受限：acl_release_control
								普通商密、核心商密：acl_release_scuret
							 * 
							 */
							if("受限".equals(boxSsecurity)) {
								box.setAclName("acl_release_control");
							}else if("普通商密".equals(boxSsecurity)) {
								box.setAclName("acl_release_scuret");
							}else if("核心商密".equals(boxSsecurity)) {
								box.setAclName("acl_release_scuret");
							}else {
								box.setAclName("acl_release_public");
							}
						}else {
							box.setAclName("acl_release_public");
						}
						documentService.updateObject(getToken(), box, null);
					}
					
					String sql2="select child_id from ecm_relation where parent_id='"+childidStr+"' "
							+ " and name='irel_children' and (DESCRIPTION!='复用'  or DESCRIPTION is null) ";
//					String sql2="select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
//							+ "and b.parent_id ='"+childidStr+"' and b.name='irel_children' and (b.DESCRIPTION!='复用'  or b.DESCRIPTION is null) " ;//第二层
					List<Map<String,Object>> innerFileIds= documentService.getMapList(getToken(), sql2);
					for(int x=0;innerFileIds!=null&&x<innerFileIds.size();x++) {
						Map<String,Object> m= innerFileIds.get(x);
						String innerfileId= m.get("child_id").toString();
						EcmDocument innerDoc= documentService.getObjectById(getToken(), innerfileId);
						Map<String,Object> attrInner=innerDoc.getAttributes();

						String innerPathId= folderPathService.getReleaseFolderId(getToken(),attrInner);
						innerDoc.setFolderId(innerPathId);
						innerDoc.setStatus(Constants.USING);
						innerDoc.addAttribute("C_STORE_STATUS", "在库");
						innerDoc.addAttribute("C_INSTORE_DATE", new Date());
						innerDoc.addAttribute("C_INSTORE_USER", this.getSession().getCurrentUser().getUserName());
						String security=innerDoc.getSecurityLevel();
						if(security!=null) {
							/**
							 * 内部公开：acl_release_public
								受限：acl_release_control
								普通商密、核心商密：acl_release_scuret
							 * 
							 */
							if("受限".equals(security)) {
								innerDoc.setAclName("acl_release_control");
							}else if("普通商密".equals(security)) {
								innerDoc.setAclName("acl_release_scuret");
							}else if("核心商密".equals(security)) {
								innerDoc.setAclName("acl_release_scuret");
							}else {
								innerDoc.setAclName("acl_release_public");
							}
						}else {
							innerDoc.setAclName("acl_release_public");
						}
						documentService.updateObject(getToken(), innerDoc, null);
					
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		
		/////////////////////////////////////////////////////////
		
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
		
		
	}
	
	
	/**
	 * 提取信息
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/fetchInformation", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String,Object> fetchInformation(@RequestBody String argStr){

		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			for(int i=0;list!=null&&i<list.size();i++) {
				String boxId=list.get(i);
				EcmDocument doc = null;
				doc = documentService.getObjectById(getToken(), boxId);
				
				
				///////////////////////////////////////////////
				
				if(!"盒".equals(doc.getSubType())&&(doc.getCoding()==null||"".equals(doc.getCoding()))) {
					mp.put("code", ActionContext.FAILURE);
					mp.put("message", "请先取号！");
					return mp;
				}else {
					if(doc.getSubType()!=null&&"盒".equals(doc.getSubType())) {
						String sql="select child_id from ecm_relation where parent_id='"+boxId+"' "
								+ "and name='irel_children' and (DESCRIPTION!='复用'  or DESCRIPTION is null) order by ORDER_INDEX";
						List<String> archiveNumbers=new ArrayList<String>();
						
						List<Map<String,Object>> volumeIds= documentService.getMapList(getToken(), sql);
						for(int j=0;volumeIds!=null&&j<volumeIds.size();j++) {
							String volumeId=volumeIds.get(j).get("child_id").toString();
							EcmDocument volumeDoc=documentService.getObjectById(getToken(), volumeId);
							archiveNumbers.add(
								volumeDoc.getAttributeValue("C_ARCHIVE_NUM").toString());
							
							if(!"图册".equals(volumeDoc.getTypeName())) {
								continue;
							}
							
							String sqlSumPage="select sum(C_PAGE_COUNT) as pageCount from ecm_document "
									+ "where id in(select child_id from ecm_relation where parent_id='"+volumeId+"' "
											+ " and name='irel_children' and (DESCRIPTION!='复用' or DESCRIPTION is null))";
							List<Map<String, Object>> pages= documentService.getMapList(getToken(),sqlSumPage);
							if(pages!=null&&pages.size()>0&&pages.get(0)!=null) {
								volumeDoc.addAttribute("C_PAGE_COUNT", pages.get(0).get("pageCount"));
							}
							String minDocDate=ChildrenObjAction.getMinDocDate(getToken(), volumeId,"C_DOC_DATE", documentService);
							if(minDocDate!=null) {
								volumeDoc.addAttribute("C_DOC_DATE", minDocDate);
							}
							String maxDocDate=ChildrenObjAction.getMaxDocDate(getToken(), volumeId,"C_DOC_DATE", documentService);
							if(maxDocDate!=null) {
								volumeDoc.addAttribute("C_END_DATE", maxDocDate);
							}
							
							String maxRetention= ChildrenObjAction.getRetention(getToken(), volumeId, documentService);
							if(maxRetention!=null) {
								volumeDoc.addAttribute("C_RETENTION", maxRetention);
							}else {
								volumeDoc.addAttribute("C_RETENTION", "10年");
							}
							String maxSecurity=ChildrenObjAction.getVolumeMaxSecurity(getToken(), volumeId, documentService);
							if(maxSecurity==null) {
								volumeDoc.addAttribute("C_SECURITY_LEVEL", "内部公开");
							}else {
								volumeDoc.addAttribute("C_SECURITY_LEVEL", maxSecurity);
							}
							documentService.updateObject(getToken(), volumeDoc, null);
						}
						
						Collections.sort(archiveNumbers,new Comparator () {
							@Override
				             public int compare(Object o1, Object o2) {
				            	 return o1.toString().compareTo(o2.toString());
				             }
						});
						
						if(archiveNumbers.size()>1) {

							String boxCode="";
							int flag=0;
							int flagIndex=0;
							for(int m=0;m<archiveNumbers.size();m++) {
								String archiveNum= archiveNumbers.get(m);
								String sq= archiveNum.substring(archiveNum.lastIndexOf("-")+1);
								int sqindex=Integer.parseInt(sq);
								if(m==0) {
									flag=sqindex;
								}else {
									if(sqindex==flag+1) {
										flag=sqindex;
										if(m==archiveNumbers.size()-1) {
											String startCode= archiveNumbers.get(flagIndex);
											String endCode=archiveNumbers.get(m);
											boxCode=boxCode+startCode+"~"+endCode.substring(endCode.lastIndexOf("-")+1)+";";
											
										}
									}else {
										
										String startCode= archiveNumbers.get(flagIndex);
										String endCode=archiveNumbers.get(m-1);
										if(flagIndex==m-1) {
											boxCode=boxCode+startCode+";";
										}else {
											
											boxCode=boxCode+startCode+"~"+endCode.substring(endCode.lastIndexOf("-")+1)+";";
										}
										if(m==archiveNumbers.size()-1) {
											String lastCode=archiveNumbers.get(m);
											boxCode=boxCode+lastCode+";";
										}
										flagIndex=m;
										String c= archiveNumbers.get(m);
										c= c.substring(c.lastIndexOf("-")+1);
										int cInt=Integer.parseInt(c);
										flag=cInt;
									}
								}
							}
							doc.addAttribute("CODING", boxCode);
						
							
						}else if(archiveNumbers.size()==1){
							String startCode= archiveNumbers.get(0);
							
							doc.addAttribute("CODING", startCode);
						}
						
					}

					String sqlSumPage="select sum(C_PAGE_COUNT) as pageCount from ecm_document "
							+ "where id in(select child_id from ecm_relation where parent_id='"+boxId+"' "
									+ " and name='irel_children' and (DESCRIPTION!='复用' or DESCRIPTION is null))";
					List<Map<String, Object>> pages= documentService.getMapList(getToken(),sqlSumPage);
					if(pages!=null&&pages.size()>0&&pages.get(0)!=null) {
						doc.addAttribute("C_PAGE_COUNT", pages.get(0).get("pageCount"));
					}else {
						doc.addAttribute("C_PAGE_COUNT", "0");
					}
					
					String minDocDate=ChildrenObjAction.getMinDocDate(getToken(), boxId, "C_DOC_DATE",documentService);
					if(minDocDate!=null) {
						doc.addAttribute("C_DOC_DATE", minDocDate);
					}
					String maxDocDate=ChildrenObjAction.getMaxDocDate(getToken(), boxId,"C_DOC_DATE", documentService);
					if(maxDocDate!=null) {
						doc.addAttribute("C_END_DATE", maxDocDate);
					}
					
					String maxRetention= ChildrenObjAction.getRetention(getToken(), boxId, documentService);
					if(maxRetention!=null) {
						doc.addAttribute("C_RETENTION", maxRetention);
					}else {
						doc.addAttribute("C_RETENTION", "10年");
					}
					String maxSecurity=ChildrenObjAction.getVolumeMaxSecurity(getToken(), boxId, documentService);
					if(maxSecurity==null) {
						doc.addAttribute("C_SECURITY_LEVEL", "内部公开");
					}else {
						doc.addAttribute("C_SECURITY_LEVEL", maxSecurity);
					}
					documentService.updateObject(getToken(), doc, null);
				}
				
				//////////////////////////////////////////////
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
	
		
	}
	
	/**
	 * 按规则取号
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/takeNumbersByPolicy", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String,Object> takeNumbersByPolicy(@RequestBody String argStr){
		Map<String,Object> attr = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String code;
		try {
			code = numberService.getNumber(getToken(), attr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", "取号失败，"+e.getMessage());
			return mp;
		}
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", code);
		mp.put("message", "取号成功");
		return mp;
	}
		
	/**
	 * 取号
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/takeNumbers", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String,Object> takeNumbers(@RequestBody String argStr){
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			for(int i=0;list!=null&&i<list.size();i++) {
				String boxId=list.get(i);
//				String boxId=argStr;
				EcmDocument doc = null;
				doc = documentService.getObjectById(getToken(), boxId);
				if(doc.getTypeName()!=null&&"卷盒".equals(doc.getTypeName())) {
					if (doc.getSubType() != null && "卷".equals(doc.getSubType())
							&&(doc.getCoding()==null||"".equals(doc.getCoding()))/* ||"盒".equals(doc.getSubType()) */) {
						
						while(true) {
							String coding= numberService.getNumber(getToken(), doc.getAttributes());
							String validataSql="select coding from ecm_document where coding='"+coding+"'";
							List<Map<String,Object>> result= documentService.getMapList(getToken(),validataSql);
							if(result==null||result.size()==0) {
								doc.setCoding(coding);
								List<Map<String,Object>> childrenIds= ChildrenObjAction.getChildrenObjById(getToken(), boxId, documentService);
								for(Map<String,Object> childId : childrenIds) {
									String childidStr=(String) childId.get("child_id");
									EcmDocument childDoc= documentService.getObjectById(getToken(), childidStr);
									childDoc.addAttribute("C_ARCHIVE_NUM", coding);
									
									try {
										documentService.updateObject(getToken(), childDoc,null);
									}catch(NullPointerException nu) {
										continue;
									}catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
										throw e;
									}
									
								}
								documentService.updateObject(getToken(), doc,null);
								break;
							}
						}
						
					}else if(doc.getSubType() != null && "盒".equals(doc.getSubType())) {
						List<Map<String,Object>> childrenIds= ChildrenObjAction.getChildrenObjectById(getToken(), boxId, documentService);
						for(Map<String,Object> childId : childrenIds) {
							String childidStr=(String) childId.get("child_id");
							EcmDocument childDoc= documentService.getObjectById(getToken(), childidStr);
							if(childDoc.getAttributeValue("C_ARCHIVE_NUM")!=null&&
									!"".equals(childDoc.getAttributeValue("C_ARCHIVE_NUM").toString())
								) 
							{
								continue;
							}
							while(true) {
								Object archiveNumObj= childDoc.getAttributeValue("C_ARCHIVE_NUM");
								if(archiveNumObj!=null&&!"".equals(archiveNumObj.toString())) {
									break;
								}
								Map<String,Object> childAttrs=childDoc.getAttributes();
								String newArchiveNumStr= numberService.getNumber(getToken(),childAttrs);
								if(newArchiveNumStr==null||"".equals(newArchiveNumStr)) {
									throw new Exception("取号失败，请检查数据！");
								}
								String validataSql="select coding from ecm_document where C_ARCHIVE_NUM='"+newArchiveNumStr+"'";
								List<Map<String,Object>> result= documentService.getMapList(getToken(),validataSql);
								if(result==null||result.size()==0) {
									childDoc.addAttribute("C_ARCHIVE_NUM", newArchiveNumStr);
									documentService.updateObject(getToken(), childDoc,null);
									break;
								}
								
							}
							
						}
					}
				}else {
					String params = CacheManagerOper.getEcmParameters().get("takeNumberType").getValue();
					if(params!=null&&params.contains(doc.getTypeName())) {
						while(true) {
							Object archiveNumObj= doc.getAttributeValue("C_ARCHIVE_NUM");
							if(archiveNumObj!=null&&!"".equals(archiveNumObj.toString())) {
								break;
							}
							Map<String,Object> docAttrs=doc.getAttributes();
							String newArchiveNumStr= numberService.getNumber(getToken(),docAttrs);
							String validataSql="select coding from ecm_document where C_ARCHIVE_NUM='"+newArchiveNumStr+"'";
							List<Map<String,Object>> result= documentService.getMapList(getToken(),validataSql);
							if(result==null||result.size()==0) {
								doc.addAttribute("C_ARCHIVE_NUM", newArchiveNumStr);
								documentService.updateObject(getToken(), doc,null);
								break;
							}
							
						}
					}
				}
				
			
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
			return mp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", "取号失败,详细信息："+e.getMessage());
			return mp;
		}
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
	}
	/**
	 * 通过relationId删除子文件和关系
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/delDocumentByRelationId", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> delDocumentByRelationId(@RequestBody String argStr) throws Exception {
		List<String> list = JSONUtils.stringToArray(argStr);
//		String strWhere="'"+argStr+"'";//.replaceAll("\"", "'");//"'"+ String.join("','", list)+"'";
		String strWhere="'"+ String.join("','", list)+"'";
		String sql="select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.id in("+strWhere+") and b.name='irel_children' and (b.DESCRIPTION!='复用' or b.DESCRIPTION is null) "; 
				
		List<Map<String,Object>> childrenId= documentService.getMapList(getToken(), sql);
		for(Map<String,Object> childId : childrenId) {
			String childidStr=(String) childId.get("child_id");
			try {
				documentService.deleteObject(getToken(),childidStr);
			}catch(NullPointerException nu) {
				nu.printStackTrace();
				continue;
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			
		}
		
		for (String id : list) {
			relationService.deleteObject(getToken(),id);
		}
		
		

		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	/**
	 * 删除文件和关系
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/delDocumentAndRelation", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> delDocument(@RequestBody String argStr) throws Exception {
		long start = System.currentTimeMillis();
		List<String> list = JSONUtils.stringToArray(argStr);
//		String strWhere="'"+argStr+"'";//.replaceAll("\"", "'");//"'"+ String.join("','", list)+"'";
		String strWhere="'"+ String.join("','", list)+"'";
		String sql="select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id in("+strWhere+") and b.name='irel_children' " + 
				"union " + 
				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.PARENT_ID "
				+ "and b.PARENT_ID in(" + 
				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id in("+strWhere+")) and b.name='irel_children' "
				+" union "
				+ " select parent_id from ecm_relation where parent_id in("+strWhere+") and name='irel_children' ";
		
		List<Map<String,Object>> childrenId= documentService.getMapList(getToken(), sql);
		if(childrenId != null) {
			for(Map<String,Object> childId : childrenId) {
				String childidStr=(String) childId.get("child_id");
				try {
					documentService.deleteObject(getToken(),childidStr);
					logger.info("delete document:"+childidStr);
				}catch(NullPointerException nu) {
					nu.printStackTrace();
					continue;
				}catch (Exception e) {
					// TODO: handle exception
					throw e;
				}
				
			}
		}
		
		logger.info("delDocumentAndRelation sql1:"+sql);
		long cost = System.currentTimeMillis() - start;
		logger.info("delDocumentAndRelation:"+cost);
		start = System.currentTimeMillis();
//		String sqlAll="select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
//				+ "and b.parent_id in("+strWhere+") " + 
//				"union " + 
//				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.PARENT_ID "
//				+ "and b.PARENT_ID in(" + 
//				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
//				+ "and b.parent_id in("+strWhere+")) "
//				+" union "
//				+ " select child_id from ecm_relation where child_id in("+strWhere+")";
//		
//		String strSql="select id from ecm_relation where child_id in("+sqlAll+")";
//		logger.info("delDocumentAndRelation sqlAll:"+sqlAll);
//		List<Map<String,Object>> relationIds=relationService.getMapList(getToken(), strSql);
//		if(relationIds!=null) {
//			for(Map<String,Object> rMap:relationIds) {
//				
//				relationService.deleteObject(getToken(),rMap.get("id").toString());
//				logger.info("delete relation:"+rMap.get("id").toString());
//			}
//		}
//		String valiSql="select count(*) as num from ecm_document where id in("+strWhere+") ";
//		logger.info("delDocumentAndRelation valiSql:"+valiSql);
//		List<Map<String,Object>> haveData= documentService.getMapList(getToken(), valiSql);
//		if(haveData!=null&&haveData.size()>0&& Integer.parseInt(String.valueOf(haveData.get(0).get("num")))>0) {
//			for (String id : list) {
//				documentService.deleteObject(getToken(),id);
//				logger.info("delete delivery document:"+id);
//			}
//		}
		
		for (String id : list) {
			documentService.deleteObject(getToken(),id);
			logger.info("delete delivery document:"+id);
		}
		cost = System.currentTimeMillis() - start;
		logger.info("delete delivery :"+cost);
		start = System.currentTimeMillis();
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	
	/**
	 * 通过配置获取文件夹
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/folder/getFolderByConfigeGC", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> getFolderByConfigeGC(@RequestBody String param) {
		long start0 = System.currentTimeMillis();
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String,Object> params= JSONUtils.stringToMap(param);
		String folderConfig=params.get("folderConfig").toString();
		Object conditionObj=params.get("condition");
		try {
			String id ="";
			try {
				id= CacheManagerOper.getEcmParameters().get(folderConfig).getValue();
			}catch (NullPointerException e) {
				// TODO: handle exception
				id=param;
			}
//			long start = System.currentTimeMillis();
//			logger.info("Start get root folder.");
			List<EcmFolder> folders=folderService.getFoldersByParentId(getToken(), id);
//			long cost = System.currentTimeMillis() - start;
//			start = System.currentTimeMillis();
//			logger.info("End get root folder:"+cost);
//			List<EcmFolder> resultData=new ArrayList<>();
//			for(EcmFolder f:folders) {
//				EcmGridView gv = CacheManagerOper.getEcmGridViews().get(f.getGridView());
//				String gvCondition=gv.getCondition();
//				String sql="select count(*) as num from ecm_document where "
//				+gvCondition+(conditionObj==null?"":conditionObj.toString())+" and FOLDER_ID in( " + 
//						"select id from ecm_folder where FOLDER_PATH like '"+f.getFolderPath()+"%' " + 
//						")";
//				logger.info("SQL:"+sql);
//				List<Map<String,Object>> numberData= documentService.getMapList(getToken(), sql);
//				if(numberData!=null&&numberData.size()>0&&numberData.get(0)!=null) {
//					String name=f.getName();
//					f.setName(name+"("+numberData.get(0).get("num").toString()+")");
//					
//				}
//				resultData.add(f);
//				cost = System.currentTimeMillis() - start;
//				start = System.currentTimeMillis();
//				logger.info("End get child folder document count:"+cost);
//			}
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", folders);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		long cost = System.currentTimeMillis() - start0;
		logger.info("End get all root folder:"+cost);
		return mp;
	}
	
	/**
	 * 通过配置获取文件夹ArchiveGc
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/folder/getArchiveFolderByConfigeArchiveGc", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> getFolderByConfigeArchiveGc(@RequestBody String param) {
		long start0 = System.currentTimeMillis();
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String,Object> params=JSONUtils.stringToMap(param);
		String folderId=params.get("folderId").toString();
		String noCount=params.get("noCount").toString();
		Object conditionObj=params.get("condition");
		String condition="";
		if(conditionObj!=null) {
			condition=conditionObj.toString();
		}
		try {
			String id ="";
			try {
				id= CacheManagerOper.getEcmParameters().get(folderId).getValue();
			}catch (NullPointerException e) {
				// TODO: handle exception
				id=folderId;
			}
			long start = System.currentTimeMillis();
			
			logger.info("Start get child folder.");
			List<EcmFolder> folders=folderService.getFoldersByParentId(getToken(), id);
			long cost = System.currentTimeMillis() - start;
			start = System.currentTimeMillis();
			logger.info("End get child folder:"+cost);
			List<EcmFolder> resultData=new ArrayList<>();
			for(EcmFolder f:folders) {
				EcmGridView gv = CacheManagerOper.getEcmGridViews().get(f.getGridView());
				String gvCondition=gv.getCondition();
//				String whereSql="";
//				if(!"".equals(condition)) {
//					whereSql=" and ("+condition+")";
//				}
				if(noCount==null) {
					String sql="select count(*) as num from ecm_document where "+gvCondition+condition+" and FOLDER_ID in( " + 
							"select id from ecm_folder where FOLDER_PATH like '"+f.getFolderPath()+"%' " + 
							")";
					logger.info("child count sql:"+sql);
					List<Map<String,Object>> numberData= documentService.getMapList(getToken(), sql);
					if(numberData!=null&&numberData.size()>0&&numberData.get(0)!=null) {
						String name=f.getName();
						f.setName(name+"("+numberData.get(0).get("num").toString()+")");
						
					}
				}
				resultData.add(f);
				cost = System.currentTimeMillis() - start;
				start = System.currentTimeMillis();
				logger.info("End get child count:"+cost);
			}
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", resultData);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		long cost = System.currentTimeMillis() - start0;
		logger.info("End get all child folder:"+cost);
		return mp;
	}
	
	/**
	 * 通过配置获取文件夹
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/folder/getArchiveFolderByConfige", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> getFolderByConfige(@RequestBody String param) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String,Object> params=JSONUtils.stringToMap(param);
			String folderId= params.get("folderId").toString();
			String condition=params.get("condition")==null?"":params.get("condition").toString();
			String id ="";
			try {
				id= CacheManagerOper.getEcmParameters().get(folderId).getValue();
			}catch (NullPointerException e) {
				// TODO: handle exception
				id=folderId;
			}
			List<EcmFolder> folders=folderService.getFoldersByParentId(getToken(), id);
			List<EcmFolder> resultData=new ArrayList<>();
			for(EcmFolder f:folders) {
				EcmGridView gv = CacheManagerOper.getEcmGridViews().get(f.getGridView());
				String gvCondition=gv.getCondition();
				String sql="select count(*) as num from ecm_document where 1=1 "//+gvCondition
						+" "+("".equals(condition)?"":condition)+" and FOLDER_ID in( " + 
						"select id from ecm_folder where FOLDER_PATH like '"+f.getFolderPath()+"%' " + 
						")";
				List<Map<String,Object>> numberData= documentService.getMapList(getToken(), sql);
				if(numberData!=null&&numberData.size()>0&&numberData.get(0)!=null) {
					String name=f.getName();
					f.setName(name+"("+numberData.get(0).get("num").toString()+")");
					
				}
				resultData.add(f);
			}
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", resultData);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	/**
	 * 添加文件至档案
	 * @param param
	 * @return
	 * @throws AccessDeniedException 
	 */
	@ResponseBody
	@RequestMapping(value="/dc/addToArchive", method = RequestMethod.POST)
	public Map<String, Object> addToArchive(@RequestBody String param) throws AccessDeniedException {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(param);
		String fileIdStr= (String)args.get("fileIds");
		String archiveId=(String)args.get("archiveId");
		List<String> fileIds=JSONUtils.stringToArray(fileIdStr);
		
		for(int i=0;i<fileIds.size();i++) {
			
			EcmRelation en=new EcmRelation();
			en.setParentId(archiveId);
			en.setChildId(fileIds.get(i));
			en.setName("irel_children");
			en.setOrderIndex(archiveRelationService.getMaxOrderIndex(getToken(),archiveId)+1);
			EcmDocument archiveDoc= documentService.getObjectById(getToken(), archiveId);
			String coding= archiveDoc.getCoding();
			EcmDocument childDoc=documentService.getObjectById(getToken(), fileIds.get(i));
			if(coding!=null&&!"".equals(coding)) {
				childDoc.addAttribute("C_ARCHIVE_NUM", coding);;
			}
			try {
				relationService.newObject(getToken(), en);
				documentService.updateObject(getToken(), childDoc, null);
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message",e.getMessage());
				return mp;
			} catch (NoPermissionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "您没有权限进行此操作！");
				return mp;
			}
		}
		
		mp.put("code", ActionContext.SUCESS);
		return mp;
	
	
	}
	
	/**
	 * 文件移除卷盒
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/removeFromArchive", method = RequestMethod.POST)
	public Map<String, Object> removeFromArchive(@RequestBody String param) {

		Map<String, Object> args = JSONUtils.stringToMap(param);
		String fileIdStr= (String)args.get("fileIds");
		String archiveId=(String)args.get("archiveId");
		List<String> fileIds=JSONUtils.stringToArray(fileIdStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			for(int i=0;fileIds!=null&&i<fileIds.size();i++) {
				EcmRelation relation=archiveRelationService.getObjectById(archiveId, fileIds.get(i));
				if(!"复用".equals(relation.getDescription())) {
					EcmDocument childDoc=documentService.getObjectById(getToken(), fileIds.get(i));
					childDoc.addAttribute("C_ARCHIVE_NUM", "");
					documentService.updateObject(getToken(), childDoc, null);
				}
			}
			
			archiveRelationService.deleteArchiveRelation(archiveId, "irel_children", fileIds);
			
			mp.put("code", ActionContext.SUCESS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	
	}
	
	/**
	 * 自动组卷
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/autoPaper", method = RequestMethod.POST)
	public Map<String, Object> autoPaper(@RequestBody String param) {

		Map<String, Object> args = JSONUtils.stringToMap(param);
		String fileStrs= (String)args.get("files");
		List<String> files= JSONUtils.stringToArray(fileStrs);
		List<Map<String,Object>> fi=new ArrayList<Map<String,Object>>();
		for(String file : files) {
			Map<String,Object> f= JSONUtils.stringObjectToMap(file);
			fi.add(f);
		}
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			archiveRelationService.autoPaper(getToken(), fi);
			
			mp.put("code", ActionContext.SUCESS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	
	}
	/**
	 * 封卷
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/closePage", method = RequestMethod.POST)
	public Map<String, Object> closePage(@RequestBody String param) {

		Map<String, Object> args = JSONUtils.stringToMap(param);
		String fileStrs= (String)args.get("files");
		List<String> files= JSONUtils.stringToArray(fileStrs);
		List<Map<String,Object>> fi=new ArrayList<Map<String,Object>>();
		for(String file : files) {
			Map<String,Object> f= JSONUtils.stringObjectToMap(file);
			fi.add(f);
		}
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			archiveRelationService.closePage(fi);
			
			mp.put("code", ActionContext.SUCESS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	
	}
	
	/**
	 * 开卷
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/openPage", method = RequestMethod.POST)
	public Map<String, Object> openPage(@RequestBody String param) {

		Map<String, Object> args = JSONUtils.stringToMap(param);
		String fileStrs= (String)args.get("files");
		List<String> files= JSONUtils.stringToArray(fileStrs);
		List<Map<String,Object>> fi=new ArrayList<Map<String,Object>>();
		for(String file : files) {
			Map<String,Object> f= JSONUtils.stringObjectToMap(file);
			fi.add(f);
		}
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			archiveRelationService.openPage(fi);
			
			mp.put("code", ActionContext.SUCESS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	
	}
	/**
	 * 获取文件对象
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/getArchiveObj", method = RequestMethod.POST)
	public Map<String, Object> getArchiveObj(@RequestBody String param) {
		Map<String, Object> args = JSONUtils.stringToMap(param);
		String itemInfo = args.get("itemInfo").toString();
		String lang = args.get("lang").toString();
		EcmDocument en = null;
		List<EcmFormItem> list = null;
		try {
			en = documentService.getObjectById(getToken(),itemInfo);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", en);
		return mp;
	}
	
	/**
	 * 获取文件对象
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dc/getArchiveObjs", method = RequestMethod.POST)
	public Map<String, Object> getArchiveObjs(@RequestBody String param) {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(param);
		Object idsObj= args.get("ids");
		if(idsObj==null) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", "请选择一条或多条文件Id");
			return mp;
		}
		String idsStr = args.get("ids").toString();
		List<String> idsList=JSONUtils.stringToArray(idsStr);
		String ids= String.join("','", idsList.toArray(new String[idsList.size()]));
		String lang = args.get("lang").toString();
		List<EcmDocument> ens = null;
		try {
			ens = documentService.getObjects(getToken(), " id in('"+ids+"')");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", ens);
		return mp;
	}
	
	/**
	 * 自动装盒
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/autoPutInBox", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> autoPutInBox(@RequestBody String argStr){
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		IEcmSession session = null;
		boolean flag=false;
		try {
			session = getSession();
			for(String id :list) {
				EcmDocument drawingDoc= documentService.getObjectById(getToken(), id);
				EcmDocument box=null;
				String boxId="";
//				if(!"图册".equals(drawingDoc.getTypeName())) {
//					continue;
//				}
				if("商务文件".equals(drawingDoc.getTypeName())) {
					if(drawingDoc.getAttributeValue("C_PROJECT")==null
							||"".equals(drawingDoc.getAttributeValue("C_PROJECT").toString())) {
						continue;
					}
					String sql="select * from ecm_document where TYPE_NAME='卷盒' "
							+ "and C_ARC_CLASSIC='合同管理' and NAME='"+drawingDoc.getAttributeValue("C_PROJECT").toString()+"'";
					List<Map<String,Object>> boxDatas= documentService.getMapList(getToken(), sql);
					if(boxDatas!=null&&boxDatas.size()>0) {
						Map<String,Object> boxData= boxDatas.get(0);
						if(boxData!=null&&boxData.get("STATUS")!=null&&
								!"".equals(boxData.get("STATUS").toString())) {
							if(Constants.ARRANGE.equals(boxData.get("STATUS").toString())) {
								box= documentService.getObjectById(getToken(), boxData.get("ID").toString());
								boxId= box.getId();
							}else {
								flag=true;
								continue;
							}
						}
					}else {
						box=new EcmDocument();
						box.setTypeName("卷盒");
						box.setSubType("盒");
						box.setFolderId(drawingDoc.getFolderId());
//						box.setTitle(drawingDoc.getName());
						if("商务文件".equals(drawingDoc.getTypeName())) {
							box.setName(drawingDoc.getAttributeValue("C_PROJECT")!=null
									?drawingDoc.getAttributeValue("C_PROJECT").toString():"");
						}else {
							box.setName(drawingDoc.getName());
						}
						box.addAttribute("STATUS", Constants.ARRANGE);
						box.addAttribute("C_DOC_DATE", drawingDoc.getAttributeValue("C_DOC_DATE"));
						box.setAclName(drawingDoc.getAclName());
						box.setRevision(drawingDoc.getRevision());
						box.setCreator(session.getCurrentUser().getUserName());
						box.setCreationDate(new Date());
						box.addAttribute("C_ARC_CLASSIC", drawingDoc.getAttributeValue("C_ARC_CLASSIC"));
						box.addAttribute("C_ARCHIVE_NUM", drawingDoc.getAttributeValue("C_ARCHIVE_NUM"));
						boxId= documentService.newObject(getToken(), box.getAttributes());
					}
				}else {
					box=new EcmDocument();
					box.setTypeName("卷盒");
					box.setSubType("盒");
					box.setFolderId(drawingDoc.getFolderId());
//					box.setTitle(drawingDoc.getName());
					box.setName(drawingDoc.getName());
					
					box.addAttribute("STATUS", Constants.ARRANGE);
					box.addAttribute("C_DOC_DATE", drawingDoc.getAttributeValue("C_DOC_DATE"));
					box.setAclName(drawingDoc.getAclName());
					box.setRevision(drawingDoc.getRevision());
					box.setCreator(session.getCurrentUser().getUserName());
					box.setCreationDate(new Date());
					box.addAttribute("C_ARC_CLASSIC", drawingDoc.getAttributeValue("C_ARC_CLASSIC"));
					box.addAttribute("C_ARCHIVE_NUM", drawingDoc.getAttributeValue("C_ARCHIVE_NUM"));
					boxId= documentService.newObject(getToken(), box.getAttributes());
				}
				
				
				
				EcmRelation relation=new EcmRelation();
				relation.setParentId(boxId);
				relation.setChildId(id);
				relation.setName("irel_children");
				
				relation.setCreator(session.getCurrentUser().getUserName());
				relation.setCreationDate(new Date());
				relation.setOrderIndex(1);
				relationService.newObject(getToken(), relation);
			}
			mp.put("code", ActionContext.SUCESS);
			if(flag) {
				mp.put("message", "装盒成功但是所选数据中有已经上架的盒子数据，请先将盒子下架！");
			}else {
				mp.put("message", "装盒成功");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", "装盒失败");
			
		}
		
		return mp;
		
	}
	
	@RequestMapping(value = "/record/autoArchive", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> autoArchive(@RequestBody String argStr){
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
			try {
				for(String id: list) {
					if(!StringUtils.isEmpty(id)) {
						EcmDocument doc = documentService.getObjectById(getToken(), id);
						String arcNum = (String)doc.getAttributes().get("C_ARCHIVE_NUM");
						if(!StringUtils.isEmpty(arcNum)) {
							String sql =" select ID from ecm_document where TYPE_NAME='卷盒' and FOLDER_ID='"+doc.getFolderId()+"' and CODING='"+arcNum+"'";
							List<Map<String,Object>> boxDatas= documentService.getMapList(getToken(), sql);
							if(boxDatas.size()>0) {
								String boxId = boxDatas.get(0).get("ID").toString();
								EcmRelation relation=new EcmRelation();
								relation.setParentId(boxId);
								relation.setChildId(id);
								relation.setName("irel_children");
								
								relation.setCreator(getSession().getCurrentUser().getUserName());
								relation.setCreationDate(new Date());
								relation.setOrderIndex(archiveRelationService.getMaxOrderIndex(getToken(),boxId)+1);
								relationService.newObject(getToken(), relation);
							}
						}
					}
				}
				mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
			}
			

		return mp;
	}
	
}
