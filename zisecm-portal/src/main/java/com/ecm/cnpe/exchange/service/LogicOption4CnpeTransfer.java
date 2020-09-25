package com.ecm.cnpe.exchange.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;

@Service
@Scope("prototype")
public class LogicOption4CnpeTransfer extends DocumentService{
	
	@Autowired
	private ContentService contentService;
	/**
	 * 文件传递单规则处理
	 * @param List<String> transferIds 传递单ID
	 * @return 是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean transferOption(String token,List<String> transferIds) throws Exception {
		for(int i=0;transferIds!=null&&i<transferIds.size();i++) {
			String transferID=transferIds.get(i).toString();
			List<Map<String,Object>> designDocMaps= getChildsByParentID(token, transferID, "设计文件");
			for(int j=0;designDocMaps!=null&&j<designDocMaps.size();j++) {
				EcmDocument newDesignDoc=new EcmDocument();
				
				newDesignDoc.setAttributes(designDocMaps.get(j));
				boolean isUpgrad= upgradDesignDocument(token,newDesignDoc);
				if(isUpgrad) {
					EcmDocument oldIED= getIEDByDoc(token,newDesignDoc);
					upgradIED(token,oldIED,newDesignDoc);
				}
			}
		}
		return true;
		
	}
	/**
	 * 通过传递单对象处理数据
	 * @param token
	 * @param transferDoc
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean transferOption(String token,ExcSynDetailService detailService,
			EcmDocument transferDoc,boolean isDispense) throws Exception {
		List<Map<String,Object>> designDocMaps= getChildsByParentID(token, transferDoc.getId(), "设计文件");
		
		for(int j=0;designDocMaps!=null&&j<designDocMaps.size();j++) {
			EcmDocument newDesignDoc=new EcmDocument();
			
			newDesignDoc.setAttributes(designDocMaps.get(j));
			boolean isUpgrad= upgradDesignDocument(token,newDesignDoc);
			if(isUpgrad) {
				OptionLogger.logger(token, detailService, newDesignDoc, "升版", 
						newDesignDoc.getAttributeValue("C_COMPANY")!=null?newDesignDoc.getAttributeValue("C_COMPANY").toString():"");
				EcmDocument oldIED= getIEDByDoc(token,newDesignDoc);
				if(oldIED==null) {
					if(isDispense) {
						return true;
					}else {
						throw new Exception("此文件\""+newDesignDoc.getCoding()+"\"无对应IED!");
					}
				}
				String iedId= upgradIED(token,oldIED,newDesignDoc);
				EcmDocument newIed=getObjectById(token, iedId);
				OptionLogger.logger(token, detailService, newIed, "升版", 
						newIed.getAttributeValue("C_COMPANY")!=null?newIed.getAttributeValue("C_COMPANY").toString():"");
			}
		}
		return true;
		
	}
	/**
	 * 验证IED是否存在
	 * @param token
	 * @param transferDoc
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean transferSubValidate(String token,EcmDocument transferDoc) throws Exception {
		List<Map<String,Object>> designDocMaps= getChildsByParentID(token, transferDoc.getId(), "设计文件");
		
		for(int j=0;designDocMaps!=null&&j<designDocMaps.size();j++) {
			EcmDocument newDesignDoc=new EcmDocument();
			newDesignDoc.setAttributes(designDocMaps.get(j));
			EcmDocument oldIED= getIEDByDoc(token,newDesignDoc);
			if(oldIED==null) {
				throw new Exception("此文件\""+newDesignDoc.getCoding()+"\"无对应IED!");
			}
		}
		return true;
		
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
	 * 通过新设计文件编码查询IED
	 * @param token
	 * @param doc
	 * @return
	 */
	private EcmDocument getIEDByDoc(String token,EcmDocument doc) {
		String condition=" CODING='"+doc.getCoding()+"' and IS_CURRENT=1 and TYPE_NAME='IED'";
		List<Map<String, Object>> result= getObjectMap(token, condition);
		if(result!=null&&result.size()>0) {
			EcmDocument oldIED = new EcmDocument();
			oldIED.setAttributes(result.get(0));
			return oldIED;
		}
		return null;
	}
	/**
	 * 升版IED
	 * @param token
	 * @param oldIED
	 * @param doc 设计文件
	 * @return
	 * @throws Exception
	 */
	public String upgradIED(String token,EcmDocument oldIED,EcmDocument doc) throws Exception {
		Date now = new Date();
//		oldIED.setModifiedDate(new Date());
//		oldIED.setModifier(getCurrentUser(token).getUserName());
//		oldIED.setCurrent(false);
//		updateObject(token, oldIED, null);
//		//复制一条新IED并将其current改为true,版本改为新版本
//		oldIED.setCurrent(true);
//		oldIED.setRevision(doc.getRevision());
//		oldIED.addAttribute("C_SEND_DATE", doc.getAttributeValue("C_ITEM_DATE"));
//		oldIED.addAttribute("C_REF_CODING", doc.getAttributeValue("CODING"));
//		oldIED.addAttribute("C_ITEM_STATUS2", "Y");
//		String newDocId= newObject(token, oldIED, null);
//		contentService.copyContent(token, oldIED.getId(), newDocId, 1);
//		
		Map<String,Object> attrMap=new HashMap<String, Object>();
		String isA = doc.getRevision();
		String status2 = "Y";
		if(!isA.equals("A")) {
			status2="N";
		}
		attrMap.put("REVISION",doc.getRevision());
		attrMap.put("C_SEND_DATE", doc.getAttributeValue("C_ITEM_DATE"));
		attrMap.put("C_REF_CODING", doc.getAttributeValue("C_REF_CODING"));
		attrMap.put("C_REVIEW1_DATE", now);
		attrMap.put("C_ITEM_STATUS2", status2);
		attrMap.put("TITLE", doc.getAttributeValue("TITLE"));
		EcmDocument checkInDoc= checkIn(token, oldIED.getId(),attrMap, null, true);
		
		
		return checkInDoc.getId();
		
	}
	/**
	 * 升版文档通过新文档更改旧文档current标记
	 * @param token
	 * @param newDoc
	 * @return
	 * @throws Exception 
	 */
	public boolean upgradDesignDocument(String token,EcmDocument newDoc) throws Exception {
		String coding =newDoc.getCoding();
		String condition=" CODING='"+coding+"' and REVISION!='"+newDoc.getRevision()+"' and IS_CURRENT=1 and TYPE_NAME='设计文件'";
		List<Map<String, Object>> result= getObjectMap(token, condition);
		if(result!=null&&result.size()>0) {
			EcmDocument oldDoc = new EcmDocument();
			oldDoc.setAttributes(result.get(0));
//			oldDoc.setCurrent(false);
//			oldDoc.setModifiedDate(new Date());
//			oldDoc.setModifier(getCurrentUser(token).getUserName());
//			updateObject(token, oldDoc, null);
//			newDoc.setSystemVersion(oldDoc.getSystemVersion()+1);
//			newDoc.addAttribute("", value);
//			newDoc.setCurrent(true);
//			newDoc.setModifiedDate(new Date());
//			newDoc.setModifier(getCurrentUser(token).getUserName());
//			updateObject(token, newDoc, null);
//			checkIn(token, oldDoc.getId(),newDoc.getAttributes(), null, false);
			newRevisionTo(token, newDoc.getId(),  null,oldDoc.getId(), true);
		}else {
			newDoc.setCurrent(true);
			newDoc.setModifiedDate(new Date());
			newDoc.setModifier(getCurrentUser(token).getUserName());
			newDoc.addAttribute("C_IS_RELEASED", 1);
			updateObject(token, newDoc, null);
		}
		
		return true;
	}
	
	
	
}
