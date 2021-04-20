package com.ecm.portal.archive.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.service.DocumentService;
@Service
@Scope("prototype")
public class ArchiveFolderService extends DocumentService {
	@Autowired
	private EcmDocumentMapper ecmDocument;
	private String baseColumns = "ID,FOLDER_ID,CREATION_DATE, CREATOR, MODIFIER,OWNER_NAME,"
		    +"MODIFIED_DATE,REVISION,ACL_NAME,FORMAT_NAME,CONTENT_SIZE,ATTACHMENT_COUNT,"
			+"IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,TYPE_NAME,C_ARCHIVE_NUM";
	private String systemColumns = ",ID,CREATION_DATE,CREATOR,MODIFIER,OWNER_NAME,"
		    +"MODIFIED_DATE,FORMAT_NAME,CONTENT_SIZE,"
			+"IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,";
	private String getGridColumn(EcmGridView  gv, String gridName)
	{
		String col="";
		String cols = ","+baseColumns.replace(" ", "")+",";
		if(gv!=null)
		{
			for(EcmGridViewItem item:gv.getGridViewItems())
			{
				if(cols.indexOf(","+item.getAttrName()+",")>-1) {
					continue;
				}
				col += ","+item.getAttrName();
			}
		}
		return col;
	}
	
	public List<Map<String, Object>> getOutObjects(String token, 
			String gridName, String folderId, Pager pager,
			String condition, String orderBy) {
		EcmGridView  gv = CacheManagerOper.getEcmGridViews().get(gridName);
		String sql = "select "+baseColumns +getGridColumn(  gv,  gridName)
		+" from ecm_document where 1=1 ";
		if(!StringUtils.isEmpty(folderId))
		{
			sql += " and folder_id='"+folderId+"'";
		}
		if(!EcmStringUtils.isEmpty(condition))
		{
			sql += " and ("+condition+")";
		}
		sql += " and type_name<>'卷盒'   and id not in(select b.CHILD_ID from ecm_document a,ecm_relation b,ecm_document c where b.NAME='irel_children'and a.ID=b.parent_id and c.ID=b.CHILD_ID and a.type_name<>'移交单' and c.folder_id='"+folderId+"') ";
		
		if(!EcmStringUtils.isEmpty(orderBy))
		{
			sql += " order by "+orderBy;
		}
		else
		{
			sql += " order by CODING,CREATION_DATE";
		}
		
		List<Map<String, Object>> list = ecmDocument.executeSQL(pager,sql);
		// TODO Auto-generated method stub
		return list;
	}
}
