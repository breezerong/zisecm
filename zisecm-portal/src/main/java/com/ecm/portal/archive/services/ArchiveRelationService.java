package com.ecm.portal.archive.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.dao.EcmRelationMapper;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.RelationService;
@Service
public class ArchiveRelationService  {
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private RelationService relationService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private EcmRelationMapper ecmRelation;
	
	public EcmRelation getObjectById(String parentId,String childId) {
		List<EcmRelation> list= ecmRelation.selectByCondition(" parent_id='"+parentId+"' and CHILD_ID='"+childId+"'");
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
		
	}
	
	public boolean deleteArchiveRelation(String archiveId,String relationName,List<String> fileIds) throws Exception {
		String fileWhere =org.apache.commons.lang.StringUtils.join(fileIds.toArray(), "','");
		String sql="delete from ecm_relation where name='"+relationName+"' and parent_id='"+archiveId
				+"' and CHILD_ID in('"+fileWhere+"')";
		try {
			ecmDocument.executeSQL(sql);
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
		return true;
		
	}
	public boolean autoPaper(String token,List<Map<String,Object>> files) throws Exception {
		for(Map<String,Object> f : files) {
			String archiveNum=(String)f.get("C_ARCHIVE_NUM");
			String sql="select * from ecm_document where CODING='"+archiveNum+"' and TYPE_NAME='案卷' ";
			
			List<Map<String,Object>> result= ecmDocument.executeSQL(sql);
			if(result!=null&&result.size()>0) {
				Object lockStatus= result.get(0).get("C_LOCK_STATUS");
				if(lockStatus!=null&&"已封卷".equals(lockStatus.toString())) {
					continue;
				}
				
				EcmRelation en=new EcmRelation();
				en.setParentId(result.get(0).get("ID").toString());
				en.setChildId(f.get("ID").toString());
				en.setName("irel_children");
				relationService.newObject(token, en);
			}else {
				EcmDocument archive=new EcmDocument();
				archive.setCoding(archiveNum);
				archive.setTypeName("案卷");
				archive.setFolderId(f.get("FOLDER_ID").toString());
				String id = documentService.newObject(token,archive,null);
				EcmRelation en=new EcmRelation();
				en.setParentId(id);
				en.setChildId(f.get("ID").toString());
				en.setName("irel_children");
				relationService.newObject(token, en);
			}
			
			
		}
		
		return true;
		
	}
	/**
	 * 封卷
	 * @param token
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public boolean closePage(List<Map<String,Object>> files) throws Exception {
		String sqlWhere="'";
		for(int i=0;i<files.size();i++) {
			Map<String,Object> f = files.get(i);
			
			sqlWhere+=f.get("ID");
			if(i==files.size()-1) {
				sqlWhere+="'";
			}else {
				sqlWhere+="','";
			}
			
		}
		String sql="update ecm_document set C_LOCK_STATUS='已封卷'  where ID in("+sqlWhere+")";
		ecmDocument.executeSQL(sql);
		return true;
		
	}
	/**
	 * 开卷
	 * @param token
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public boolean openPage(List<Map<String,Object>> files) throws Exception {
		String sqlWhere="'";
		for(int i=0;i<files.size();i++) {
			Map<String,Object> f = files.get(i);
			
			sqlWhere+=f.get("ID");
			if(i==files.size()-1) {
				sqlWhere+="'";
			}else {
				sqlWhere+="','";
			}
			
		}
		String sql="update ecm_document set C_LOCK_STATUS='开卷' where ID in("+sqlWhere+")";
		ecmDocument.executeSQL(sql);
		return true;
		
	}
	/**
	 * 上移
	 * @param id
	 * @return
	 * @throws EcmException 
	 */
	public boolean moveUp(String token,String parentId,String childId) throws Exception {
		String sql="select * from ecm_relation where NAME='irel_children' and parent_id='"+parentId+"' and CHILD_ID='"+childId+"'";
		List<Map<String, Object>> relations= relationService.getMapList(token, sql);
		if(relations!=null&&relations.size()>0) {
			Map<String, Object> relation=relations.get(0);
			EcmRelation rela=new EcmRelation();
			rela.setId(relation.get("ID").toString());
			int index=EcmStringUtils.toInt(relation.get("ORDER_INDEX").toString());
			if(index<=1) {
				throw new Exception("当前数据已为第一条");
			}
			rela.setOrderIndex(index-1);
			
			String currentSql="select * from ecm_relation where parent_id='"+parentId+"' and order_index="+(index-1);
			List<Map<String, Object>> currentRelations= relationService.getMapList(token, currentSql);
			if(currentRelations!=null&&currentRelations.size()>0) {
				Map<String, Object> currentRelation=currentRelations.get(0);
				EcmRelation currentRela=new EcmRelation();
				currentRela.setId(currentRelation.get("ID").toString());
				currentRela.setOrderIndex(index);
				relationService.updateObjectByPrimaryKeySelective(token, currentRela);
			}
			relationService.updateObjectByPrimaryKeySelective(token, rela);
			return true;
		}
		return false;
		
		
	}
	/**
	 * 下移
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public boolean moveDown(String token,String parentId,String childId) throws Exception {
		String sqlCount="select count(*) as CNUM from ecm_relation where NAME='irel_children' and parent_id='"+parentId+"'";
		List<Map<String, Object>> countNum= relationService.getMapList(token, sqlCount);
		if(countNum==null||countNum.size()<=0) {
			return false;
		}
		
		int total= EcmStringUtils.toInt(countNum.get(0).get("CNUM").toString());
		if(total==0) {
			return false;
		}
		
		
		String sql="select * from ecm_relation where NAME='irel_children' and parent_id='"+parentId+"' and CHILD_ID='"+childId+"'";
		List<Map<String, Object>> relations= relationService.getMapList(token, sql);
		if(relations!=null&&relations.size()>0) {
			Map<String, Object> relation=relations.get(0);
			EcmRelation rela=new EcmRelation();
			rela.setId(relation.get("ID").toString());
			int index=EcmStringUtils.toInt(relation.get("ORDER_INDEX").toString());
			if(index>=total) {
				throw new Exception("当前数据已为最后一条");
			}
			rela.setOrderIndex(index+1);
			
			String currentSql="select * from ecm_relation where parent_id='"+parentId+"' and order_index="+(index+1);
			List<Map<String, Object>> currentRelations= relationService.getMapList(token, currentSql);
			if(currentRelations!=null&&currentRelations.size()>0) {
				Map<String, Object> currentRelation=currentRelations.get(0);
				EcmRelation currentRela=new EcmRelation();
				currentRela.setId(currentRelation.get("ID").toString());
				currentRela.setOrderIndex(index);
				relationService.updateObjectByPrimaryKeySelective(token, currentRela);
			}
			relationService.updateObjectByPrimaryKeySelective(token, rela);
			return true;
		}
		return false;
		
		
	
	
	}
	
	public int getMaxOrderIndex(String token, String parentId) {
		String sql="select max(ORDER_INDEX) as MAXNUM from ecm_relation where NAME='irel_children' and parent_id='"+parentId+"'";
		List<Map<String, Object>> list;
		try {
			list = relationService.getMapList(token, sql);
			if(list!=null&&list.size()>0 && list.get(0)!=null) {
				return Integer.parseInt(list.get(0).get("MAXNUM").toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
}
