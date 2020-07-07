package com.ecm.cnpe.exchange.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
@Service
public class DocumentService4Cnpe extends DocumentService{
	@Autowired
	private EcmDocumentMapper ecmDocument;
	
	private String baseColumns = "ID,FOLDER_ID,CREATION_DATE, CREATOR, MODIFIER,OWNER_NAME,"
			+ "MODIFIED_DATE,REVISION,ACL_NAME,FORMAT_NAME,CONTENT_SIZE,ATTACHMENT_COUNT,"
			+ "IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,"
			+ "TYPE_NAME,LIFECYCLE_NAME,LIFECYCLE_STATUS,LIFECYCLE_DIR,STATUS,STAUTS";
	private String filterColumns = ",ID,CREATION_DATE,CREATOR,MODIFIER,OWNER_NAME,"
			+ "MODIFIED_DATE,FORMAT_NAME,CONTENT_SIZE,"
			+ "IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,";

	
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
		
		String sql="select "+ baseColumns + getGridColumn(gv, gridName) +" from (" + 
				"	select a.*,b.STAUTS as STAUTS,b.TO_NAME from ecm_document a, exc_transfer b where a.id=b.doc_id" + 
				")t where "+gvCondition;
		
		
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
		
		if(sql!=null&&sql.contains("@currentuser")) {
			sql=sql.replaceAll("@currentuser", currentUser);
    	}
		if(sql!=null&&sql.contains("@company")) {
			sql=sql.replaceAll("@company", userObj.getCompany());
	    }
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
}
