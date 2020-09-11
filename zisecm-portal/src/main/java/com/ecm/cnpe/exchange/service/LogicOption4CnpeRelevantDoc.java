package com.ecm.cnpe.exchange.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.RelationService;
@Service
@Scope("prototype")
public class LogicOption4CnpeRelevantDoc extends DocumentService {
	@Autowired
	private RelationService relationService;
	/**
	 * 通过文函ID处理相关文件
	 * @param token
	 * @param mainDocIds 例如FU申请单的ID集合
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean relevantOption(String token,List<String> mainDocIds,boolean isDispense) throws Exception {
		for(int i=0;i<mainDocIds.size();i++) {
			String mainId= mainDocIds.get(i);
			EcmDocument mainDoc=getObjectById(token, mainId);
			List<Map<String,Object>> relevantMaps= getChildsByParentID(token,mainId,"相关文件");
			for(int j=0;relevantMaps!=null&&j<relevantMaps.size();j++) {
				Map<String,Object> relevantMap= relevantMaps.get(j);
				EcmDocument relevantDoc=new EcmDocument();
				relevantDoc.setAttributes(relevantMap);
				execDesignDocByRelevant(token,relevantDoc,mainDoc,isDispense);
				execIEDByRelevant(token,relevantDoc,mainDoc);
			}
		}
		return true;
		
	}
	/**
	 * 通过文函主文件处理相关文件
	 * @param token
	 * @param mainDoc
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean relevantOption(String token,EcmDocument mainDoc,boolean isDispense) throws Exception{
		List<Map<String,Object>> relevantMaps= getChildsByParentID(token,mainDoc.getId(),"相关文件");
		for(int j=0;relevantMaps!=null&&j<relevantMaps.size();j++) {
			Map<String,Object> relevantMap= relevantMaps.get(j);
			EcmDocument relevantDoc=new EcmDocument();
			relevantDoc.setAttributes(relevantMap);
			execDesignDocByRelevant(token,relevantDoc,mainDoc,isDispense);
			execIEDByRelevant(token,relevantDoc,mainDoc);
		}
		return true;
		
	}
	
	/**
	 * 通过文函主文件验证设计文件和IED是否存在
	 * @param token
	 * @param mainDoc
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean relevantValidateOption(String token,EcmDocument mainDoc) throws Exception{
		List<Map<String,Object>> relevantMaps= getChildsByParentID(token,mainDoc.getId(),"相关文件");
		for(int j=0;relevantMaps!=null&&j<relevantMaps.size();j++) {
			Map<String,Object> relevantMap= relevantMaps.get(j);
			EcmDocument relevantDoc=new EcmDocument();
			relevantDoc.setAttributes(relevantMap);
			validateDesignDoc(token,relevantDoc);
			validateIEDByRelevant(token,relevantDoc);
		}
		return true;
		
	}
	/**
	 * 验证IED是否存在
	 * @param token
	 * @param relevantDoc
	 * @return
	 * @throws Exception 
	 */
	private boolean validateIEDByRelevant(String token,EcmDocument relevantDoc) throws Exception {
		String condition=" CODING='"+relevantDoc.getCoding()+"' and C_IS_RELEASED=1 and REVISION='"+relevantDoc.getRevision()+"' and TYPE_NAME='IED'";
		List<Map<String, Object>> designDocList = getObjectMap(token,condition);
		if(designDocList!=null&&designDocList.size()>0) {
			return true;
		}
		
		throw new Exception("IED"+relevantDoc.getCoding()+"_"+relevantDoc.getRevision()+"不存在");
		
	}
	
	/**
	 * 设计文件是否存在
	 * @param token
	 * @param relevantDoc
	 * @return
	 * @throws Exception 
	 */
	private boolean validateDesignDoc(String token,EcmDocument relevantDoc) throws Exception{
		String condition=" CODING='"+relevantDoc.getCoding()+"' and C_IS_RELEASED=1 and REVISION='"+relevantDoc.getRevision()+"' and TYPE_NAME='设计文件'";
		List<Map<String, Object>> designDocList = getObjectMap(token,condition);
		if(designDocList!=null&&designDocList.size()>0) {
			return true;
		}
		
		throw new Exception("设计文件"+relevantDoc.getCoding()+"_"+relevantDoc.getRevision()+"不存在");
		
	}
	
	/**
	 * 通过文函主文件删除相关文件关系
	 * @param token
	 * @param mainDoc
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean relevantRejectOption(String token,EcmDocument mainDoc) throws Exception{
		List<Map<String,Object>> relevantMaps= getChildsByParentID(token,mainDoc.getId(),"相关文件");
		for(int j=0;relevantMaps!=null&&j<relevantMaps.size();j++) {
			Map<String,Object> relevantMap= relevantMaps.get(j);
			EcmDocument relevantDoc=new EcmDocument();
			relevantDoc.setAttributes(relevantMap);
//			execDesignDocByRelevant(token,relevantDoc);
//			execIEDByRelevant(token,relevantDoc);
//			relationService.deleteByChildIdAndRelationName(token, relevantDoc.getId(), "相关文件");
//			relationService.deleteByCidPidAndRelationName(token, parentId, relevantDoc.getId(), "相关文件");
			
			deleteDesignDocByRelevant(token,relevantDoc,mainDoc.getId());
			deleteIEDByRelevant(token,relevantDoc,mainDoc.getId());
		}
		return true;
		
	}
	
	/**
	 * 通过相关文件删除与IED建立关联
	 * @param token
	 * @param relevantDoc
	 * @return
	 * @throws Exception 
	 */
	private boolean deleteIEDByRelevant(String token,EcmDocument relevantDoc,String mainId) throws Exception {
		String condition=" CODING='"+relevantDoc.getCoding()+"' and REVISION='"+relevantDoc.getRevision()+"' and TYPE_NAME='IED'";
		List<Map<String, Object>> designDocList = getObjectMap(token,condition);
		if(designDocList!=null&&designDocList.size()>0) {
			EcmDocument designDoc=new EcmDocument();
			designDoc.setAttributes(designDocList.get(0));
//			EcmRelation relation=new EcmRelation();
//			relation.setParentId(designDoc.getId());
//			relation.setChildId(relevantDoc.getId());
//			relation.setName("相关文件");
//			relationService.newObject(token, relation);
			
//			relationService.deleteByCidPidAndRelationName(token, designDoc.getId(), relevantDoc.getId(), "相关文件");
			relationService.deleteByCidPidAndRelationName(token, designDoc.getId(), mainId, "相关文件");
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * 通过相关文件的信息删除与设计文件关联
	 * @param token
	 * @param relevantDoc
	 * @return
	 * @throws Exception 
	 */
	private boolean deleteDesignDocByRelevant(String token,EcmDocument relevantDoc,String mainId) throws Exception{
		String condition=" CODING='"+relevantDoc.getCoding()+"' and REVISION='"+relevantDoc.getRevision()+"' and TYPE_NAME='设计文件'";
		List<Map<String, Object>> designDocList = getObjectMap(token,condition);
		if(designDocList!=null&&designDocList.size()>0) {
			EcmDocument designDoc=new EcmDocument();
			designDoc.setAttributes(designDocList.get(0));
//			EcmRelation relation=new EcmRelation();
//			relation.setParentId(designDoc.getId());
//			relation.setChildId(relevantDoc.getId());
//			relation.setName("相关文件");
//			relationService.newObject(token, relation);
			
//			relationService.deleteByCidPidAndRelationName(token, designDoc.getId(), relevantDoc.getId(), "相关文件");
			relationService.deleteByCidPidAndRelationName(token, designDoc.getId(), mainId, "相关文件");
			return true;
		}
		
		return false;
		
	}
	
	
	/**
	 * 通过parentId 获取
	 * @param token
	 * @param parentId 
	 * @param relationName 关系名称 如果为null系统将查出所有关系的子文件
	 * @return
	 * @throws EcmException
	 */
	public List<Map<String,Object>> getChildsByParentID(String token,String parentId,String relationName) throws EcmException{
		List<Map<String, Object>>  list=new ArrayList<Map<String,Object>>();
		if(!"".equals(parentId)) {
			String sql = "select b.*,a.id as RELATION_ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX"
					+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
					+ " and a.PARENT_ID='"+parentId+"' and a.NAME='"+relationName+"' order by a.ORDER_INDEX,b.CREATION_DATE";
			if(relationName==null||"".equals(relationName)) {
				sql = "select b.*,a.id as RELATION_ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX"
						+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
						+ " and a.PARENT_ID='"+parentId+"' order by a.ORDER_INDEX,b.CREATION_DATE";
			}
			list = getMapList(token, sql);
			
		}
		return list;
	}

	/**
	 * 通过相关文件的信息与设计文件关联
	 * @param token
	 * @param relevantDoc
	 * @return
	 * @throws Exception 
	 */
	private boolean execDesignDocByRelevant(String token,EcmDocument relevantDoc,EcmDocument mainDoc,boolean isDispense) throws Exception{
		String condition=" CODING='"+relevantDoc.getCoding()+"' and C_IS_RELEASED=1 and REVISION='"+relevantDoc.getRevision()+"' and TYPE_NAME='设计文件'";
		List<Map<String, Object>> designDocList = getObjectMap(token,condition);
		if(designDocList!=null&&designDocList.size()>0) {
			EcmDocument designDoc=new EcmDocument();
			designDoc.setAttributes(designDocList.get(0));
			EcmRelation relation=new EcmRelation();
			relation.setParentId(designDoc.getId());
//			relation.setChildId(relevantDoc.getId());
			relation.setChildId(mainDoc.getId());
			relation.setName("相关文件");
			relationService.newObject(token, relation);
			return true;
		}
		if(!isDispense) {
			throw new Exception("设计文件"+relevantDoc.getCoding()+"_"+relevantDoc.getRevision()+"不存在");
		}
		return true;
		
		
	}
	/**
	 * 通过相关文件与IED建立关联
	 * @param token
	 * @param relevantDoc
	 * @return
	 * @throws EcmException 
	 */
	private boolean execIEDByRelevant(String token,EcmDocument relevantDoc,EcmDocument mainDoc) throws EcmException {
		String condition=" CODING='"+relevantDoc.getCoding()+"' and REVISION='"+relevantDoc.getRevision()+"' and C_IS_RELEASED=1 and TYPE_NAME='IED'";
		List<Map<String, Object>> designDocList = getObjectMap(token,condition);
		if(designDocList!=null&&designDocList.size()>0) {
			EcmDocument designDoc=new EcmDocument();
			designDoc.setAttributes(designDocList.get(0));
			EcmRelation relation=new EcmRelation();
			relation.setParentId(designDoc.getId());
//			relation.setChildId(relevantDoc.getId());
			relation.setChildId(mainDoc.getId());
			
			relation.setName("相关文件");
			relationService.newObject(token, relation);
			return true;
		}
		
		return false;
		
	}
	
}
