package com.ecm.cnpe.exchange.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.db.SqlUtils;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcTransferServiceImpl;
@Service
public class DocumentService4Cnpe extends DocumentService{
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private ExcTransferServiceImpl excTransferService;
	
	private List<String> TRS;  
	
	private String baseColumns = "SYN_APP,TRSID,ID,FOLDER_ID,CREATION_DATE, CREATOR, MODIFIER,OWNER_NAME,"
			+ "MODIFIED_DATE,REVISION,ACL_NAME,FORMAT_NAME,CONTENT_SIZE,ATTACHMENT_COUNT,"
			+ "IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,"
			+ "TYPE_NAME,LIFECYCLE_NAME,LIFECYCLE_STATUS,LIFECYCLE_DIR,STATUS,STATUS,C_COMPANY";
	private String filterColumns = ",ID,CREATION_DATE,CREATOR,MODIFIER,OWNER_NAME,"
			+ "MODIFIED_DATE,FORMAT_NAME,CONTENT_SIZE,"
			+ "IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,";

	
	
	public void getTRS(List<String> TRSID) {
		this.TRS=TRSID;
	}
	
	
	public List<Map<String, Object>> getObjects4Cnpe(String token, String gridName, String folderId, Pager pager,
			String condition, String orderBy) {
		String currentUser="";
		LoginUser userObj=null;
		try {
			userObj=getSession(token).getCurrentUser();
			currentUser = userObj.getUserName();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		String gvCondition=gv.getCondition();
		
		String columns="a.ID,a.NAME,a.TITLE,a.FOLDER_ID,a.STATUS,a.CREATION_DATE,a.CREATOR,a.TYPE_NAME,a.MODIFIER,a.MODIFIED_DATE," + 
				"a.REVISION,a.CODING,a.C_ITEM_DATE,a.ACL_NAME,a.FORMAT_NAME,a.CONTENT_SIZE,a.ATTACHMENT_COUNT,a.SUB_TYPE," + 
				"a.IS_CURRENT,a.IS_HIDDEN,a.C_DRAFTER,a.C_SECURITY_LEVEL,a.C_REVIEWER1,a.C_REVIEWER2,a.C_REVIEWER3,a.C_APPROVER,a.C_COMMENT,a.SYSTEM_VERSION," + 
				"a.VERSION_ID,a.OWNER_NAME,a.LOCK_OWNER,a.LOCK_DATE,a.LOCK_CLIENT,a.C_PAGE_COUNT,a.C_PAGE_INDEX,a.C_ORDER_INDEX,a.C_RETENTION," + 
				"a.C_ARCHIVE_NUM,a.LIFECYCLE_NAME,a.LIFECYCLE_STATUS,a.LIFECYCLE_DIR,a.C_ARC_CLASSIC,a.C_PROJECT_CODE,a.C_RECEIVE_DATE," + 
				"a.C_COUNT1,a.C_COUNT2,a.C_COUNT3,a.C_COUNT4,a.C_SYS_CODE,a.C_SYS_NAME,a.C_DESIGN_UNIT,a.C_PROJECT_NAME,a.C_WBS_CODING,a.C_IN_CODING," + 
				"a.C_OTHER_CODING,a.C_REF_CODING,a.C_TITLE,a.C_LANGUAGE,a.C_FROM,a.C_TO,a.C_PAGE_SIZE,a.C_DISPL_CODE,a.C_DISPL_NAME,a.C_ITEM_STATUS," + 
				"a.C_ITEM_STATUS1,a.C_ITEM_STATUS2,a.C_TYPE1,a.C_UNIT,a.C_INST_BUILDING,a.C_INST_LEVEL,a.C_INST_AREA_ZONE,a.C_INST_POSITION," + 
				"a.C_ISLAND_TYPE,a.C_BUILDING,a.C_ZONE,a.C_LEVEL,a.C_AREA,a.C_ROOM,a.C_MODULE,a.C_EQUIPMENT,a.C_CONTENT,a.C_COMMENT1," + 
				"a.C_COMMENT2,a.C_COMMENT3,a.C_COMMENT4,a.C_COMMENT5,a.C_COMMENT6,a.C_IDENTIFY,a.C_CODE1,a.C_CODE2,a.C_CODE3,a.C_CODE4,a.C_CODE5,a.C_CODE6," + 
				"a.C_STRING1,a.C_STRING2,a.C_STRING3,a.C_STRING4,a.C_STRING5,a.C_STRING6,a.C_RESPONSIBLE,a.C_COMPANY," + 
				"a.C_DEPARTMENT,a.C_SECTION,a.C_DRAFTER1,a.C_CHECKER,a.C_REVIEWER4,a.C_REVIEWER5,a.C_REVIEWER6,a.C_SENDER," + 
				"a.C_RECEIVER,a.C_EX1_STRING,a.C_EX2_STRING,a.C_EX3_STRING," + 
				"a.C_EX8_STRING,a.CREATION_UNIT,a.SYN_APP as SYN_APP,a.SYN_ID,a.SYN_STATUS,a.CUSTOM_TYPE,a.C_DOUBLE1,a.C_DOUBLE2,a.C_ITEM1_DATE," + 
				"a.C_ITEM2_DATE,a.C_ITEM3_DATE,a.C_ITEM4_DATE,a.C_ITEM5_DATE,a.C_ITEM6_DATE,a.C_ITEM7_DATE,a.C_DRAFT_DATE," + 
				"a.C_DRAFT1_DATE,a.C_CHECK_DATE,a.C_REVIEW1_DATE,a.C_REVIEW2_DATE,a.C_REVIEW3_DATE,a.C_REVIEW4_DATE,a.C_REVIEW5_DATE,a.C_REVIEW6_DATE,a.C_APPROVE_DATE," + 
				"a.C_SEND_DATE,a.C_EX1_DATE,a.C_EX2_DATE,a.C_EX3_DATE,a.C_EX4_DATE,a.C_EX5_DATE,a.C_EX6_DATE,a.C_EX7_DATE,a.C_EX8_DATE,a.C_IS_RELEASED,a.C_DOC_DATE," + 
				"a.C_STORE_STATUS,a.C_ITEM_TYPE,a.C_DESC1,a.C_DESC2,a.C_DESC3,a.C_FROM_CODING,a.C_IMPORT_NAME,a.C_BATCH_CODE,a.C_PROCESS_STATUS,a.C_OTHER_COIDNG," + 
				"a.C_ITEM_STATUS3,a.C_ITEM_STATUS4,a.C_ITEM_STATUS5";
		
		String sql="select "+ baseColumns + getGridColumn(gv, gridName) +" from (" + 

				"select "+columns+",b.ID as TRSID,b.STATUS as STATUS,b.TO_NAME,b.COMMENT as C_REJECT_COMMENT,b.REJECTER as C_REJECTOR,b.CONFIRMER as C_EX4_STRING,b.REJECT_DATE as C_REJECT_DATE,b.APPLICANT as C_EX5_STRING,b.COMMENT1 AS C_EX6_STRING,b.STATUS1 AS C_EX7_STRING,b.APPLY_DATE AS C_ITEM8_DATE,b.ITEM_TYPE from ecm_document a, exc_transfer b where a.id=b.doc_id" + 

				")t where "+gvCondition;		//把B表的ID也取出来备用
		
		
		if (!StringUtils.isEmpty(folderId)) {
			sql += " and folder_id='" + folderId + "'";
		}
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " and (" + condition + ")";
		}
		if (!EcmStringUtils.isEmpty(orderBy)) {
			sql += " order by " + orderBy;
		} else {
			sql += " " + gv.getOrderBy();
		}
		
		sql=SqlUtils.replaceSql(sql, userObj);
		System.out.println(sql);
		List<Map<String, Object>> list = ecmDocument.executeSQL(pager, sql);
		// TODO Auto-generated method stub
		return list;
	}
	/**
	 * 
	 * @param token
	 * @param docids 格式如下：'ID1','ID2'
	 * @return
	 */
	public List<Map<String, Object>> getExcTransferByDocId(String token,
			String docids){
		LoginUser userObj=null;
		try {
			userObj=getSession(token).getCurrentUser();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String sql="select a.ID from exc_transfer a,ecm_document b where a.doc_id=b.ID "
				+ "and TO_NAME='"+userObj.getCompany()+"' and b.ID in("+docids+") ";
		
		
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		// TODO Auto-generated method stub
		return list;
	}
	
	/**
	 * 
	 * @param token
	 * @param docId
	 * @return
	 */
	public ExcTransfer getOneExcTransferByDocId(String token,String docId,String trsid){
		LoginUser userObj=null;
		try {
			userObj=getSession(token).getCurrentUser();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String sql="select a.ID from exc_transfer a,ecm_document b where a.doc_id=b.ID "
				+" and b.ID ='"+docId+"' and a.id='"+trsid+"'";
		
		System.out.println(sql);
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		if(list!=null&&list.size()>0) {
			ExcTransfer exc=new ExcTransfer();
			exc.setAttributes(list.get(0));
			return exc;
		}
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getGridColumn(EcmGridView gv, String gridName) {
		String col = "";
		String cols = "," + baseColumns.replace(" ", "") + ",";
		if (gv != null) {
			for (EcmGridViewItem item : gv.getGridViewItems()) {
				if (cols.indexOf("," + item.getAttrName() + ",") > -1) {
					continue;
				}
				col += "," + item.getAttrName();
			}
		}
		return col;
	}
	@Transactional(rollbackFor=Exception.class)
	public boolean handleComplete(String token,List<String> docIds) throws NoPermissionException, AccessDeniedException, EcmException {
		int i = 0;
		for(String docId:docIds) {
			String tempTRS = TRS.get(i);
			EcmDocument doc= this.getObjectById(token, docId);
			Object cRefCodingObj= doc.getAttributeValue("C_REF_CODING");//渠道号
			if(cRefCodingObj!=null&&!"".equals(cRefCodingObj.toString())) {
				doc.addAttribute("C_REF_CODING", cRefCodingObj.toString()+"-作废");
				this.updateObject(token, doc,null);
				
			}else {
				Object obj= doc.getAttributeValue("CODING");
				String coding="";
				if(obj!=null) {
					coding=obj.toString();
				}
				doc.addAttribute("CODING", coding+"-作废");
				this.updateObject(token, doc,null);
			}
			ExcTransfer excTransfer= getOneExcTransferByDocId(token, docId,tempTRS);
			System.out.println(excTransfer);
			excTransfer.setStatus("已作废");
			excTransferService.updateObject(excTransfer);
			i++;
			
//			String sql="update ecm_document set status='已作废' where id='"+docId+"'";
//		ecmDocument.executeSQL(sql);
			
		}
		return true;
		
	}
	
}
