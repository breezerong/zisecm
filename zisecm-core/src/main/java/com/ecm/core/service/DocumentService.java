package com.ecm.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.AuditContext;
import com.ecm.core.PermissionContext.ObjectPermission;
import com.ecm.core.PermissionContext.SystemPermission;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.util.DBUtils;
import com.ecm.icore.service.IDocumentService;
import com.ecm.icore.service.IEcmSession;


@Service
@Scope("prototype")
public class DocumentService extends EcmObjectService<EcmDocument> implements IDocumentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);
	
	private String baseColumns = "ID,FOLDER_ID,CREATION_DATE, CREATOR, MODIFIER,OWNER_NAME,"
		    +"MODIFIED_DATE,REVISION,ACL_NAME,FORMAT_NAME,CONTENT_SIZE,ATTACHMENT_COUNT,"
			+"IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT";
	private String systemColumns = ",ID,CREATION_DATE,CREATOR,MODIFIER,OWNER_NAME,"
		    +"MODIFIED_DATE,FORMAT_NAME,CONTENT_SIZE,"
			+"IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,";
	
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private AclService aclService;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private QueueItemService queueItemService;
	
	
	@Override
	public List<Map<String, Object>> getObjects(String token, String gridName, String folderId, Pager pager,
			String condition, String orderBy) {
		EcmGridView  gv = CacheManagerOper.getEcmGridViews().get(gridName);
		String sql = "select "+baseColumns +getGridColumn(  gv,  gridName)+" from ecm_document where "+gv.getCondition();
		if(!StringUtils.isEmpty(folderId))
		{
			sql += " and folder_id='"+folderId+"'";
		}
		if(!EcmStringUtils.isEmpty(condition))
		{
			sql += " and ("+condition+")";
		}
		if(!EcmStringUtils.isEmpty(orderBy))
		{
			sql += " order by "+orderBy;
		}
		else
		{
			sql += " order by ID desc";
		}
		
		List<Map<String, Object>> list = ecmDocument.executeSQL(pager,sql);
		// TODO Auto-generated method stub
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getObjectMap(String token, String condition) {
		
		String sql = "select * from ecm_document where "+condition;
		
		return ecmDocument.executeSQL(sql);
	}
	
	@Override
	public EcmDocument getObjectById(String token,String id) {

		String sql = "select * from ecm_document where ID='"+id+"'";
		
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		
		if(list.size()>0) {
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(list.get(0));
			return doc;
		}
		return null;
	}
	
	@Override
	public Map<String, Object> getObjectMapById(String token, String id) {
		
		String sql = "select * from ecm_document where ID='"+id+"'";
		
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		
		if(list.size()>0)
			return list.get(0);
		// TODO Auto-generated method stub
		return null;
	}

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

	@Override
	public long getObjectCount(String token, String gridName, String folderId, String condition) {
		String sql = "select count(*) as objcount from ecm_document where 1=1 ";
		if(!EcmStringUtils.isEmpty(gridName))
		{
			EcmGridView  gv = CacheManagerOper.getEcmGridViews().get(gridName);
			if(gv==null)
				return 0;
			sql += " and "+gv.getCondition();
		}
		if(!StringUtils.isEmpty(folderId))
		{
			sql += " and folder_id='"+folderId+"'";
		}
		if(!EcmStringUtils.isEmpty(condition))
		{
			sql += " and ("+condition+")";
		}
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		
		return (long) list.get(0).get("objcount");
	}
	
	@Override
	public boolean updateStatus(String token, String id,String status) throws AccessDeniedException {
		if(StringUtils.isEmpty(id)) {
			return false;
		}
		String sql = "update ecm_document set STATUS='"+status+"' where ID='"+id+"'";
		ecmDocument.executeSQL(sql);
		try {
			addFullIndexSearchQueue(token, id);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean updateStatus(String token, String id,String status,String comment) throws AccessDeniedException {
		if(StringUtils.isEmpty(id)) {
			return false;
		}
		comment = comment.replace("'", "''");
		String sql = "update ecm_document set STATUS='"+status+"',C_COMMENT='"+comment+"' where ID='"+id+"'";
		ecmDocument.executeSQL(sql);
		try {
			addFullIndexSearchQueue(token, id);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteObject(String token, Object obj) throws EcmException, AccessDeniedException {
		String id = (String)obj;
		if(getPermit(token, id)<ObjectPermission.DELETE) {
			throw new EcmException("User "+getSession(token).getCurrentUser().getUserName()+" has no delete permission:"+id);
		}
		EcmDocument doc = ecmDocument.selectByPrimaryKey(id);
		contentService.deleteObject(token,id);
		boolean ret = ecmDocument.deleteByPrimaryKey(id)>0;
		newAudit(token,null, AuditContext.DELETE, id, null, doc.getTypeName()+","+doc.getName());
		addFullIndexSearchQueue(token, id);
		return ret;
	}
	
	@Override
	public String newObject(String token, Object obj) throws EcmException {
		if(obj != null && obj instanceof EcmDocument) {
			try {
				return newObject(token,((EcmDocument)obj).getAttributes());
			} catch (Exception e) {
				throw new EcmException(e.getMessage());
			}
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String newObject(String token,EcmDocument doc, EcmContent content) throws Exception {
		doc.createId();
		logger.info("doc id:{}",doc.getId());
		doc.setCurrent(true);
		if(content != null) {
			if(StringUtils.isEmpty(content.getFormatName())) {
				throw new Exception("Format cannot be empty.");
			}
			content.createId();
			logger.info("content id:{}",content.getId());
			content.setContentType(1);
			content.setParentId(doc.getId());
			logger.info("content parent id:{}",content.getParentId());
			if(StringUtils.isEmpty(content.getStoreName())) {
				content.setStoreName(CacheManagerOper.getEcmDefTypes().get(doc.getTypeName()).getStoreName());
			}
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
		}
		newObject(token,doc.getAttributes());
		if(content != null) {
			contentService.newObject(token,content);
		}
		return doc.getId();
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String newObject(String token, Map<String, Object> args) throws Exception {
		// TODO Auto-generated method stub
		String typeName = args.get("TYPE_NAME").toString();
		String name = args.get("NAME")!=null?args.get("NAME").toString():"";
		String id = createDocument( token, args);
		addFullIndexSearchQueue(token, id);
		//添加新建日志
		newAudit(token,null, AuditContext.CREATE, id, null, typeName+","+name);
		return id;
	}
	
	/**
	 * 创建文档
	 * @param token
	 * @param args
	 * @return
	 * @throws Exception
	 */
	private String createDocument(String token, Map<String, Object> args) throws Exception {
		// TODO Auto-generated method stub
		String typeName = args.get("TYPE_NAME").toString();
		String name = args.get("NAME")!=null?args.get("NAME").toString():"";
		String sql = "insert into ecm_document (%s) values(%s)";
		String fieldStr = "";
		String valueStr = "";
		String id = args.get("ID")!=null?args.get("ID").toString():"";
		if(StringUtils.isEmpty(id)) {
			id = newUUID();
		}
		fieldStr+="ID,";
		valueStr = "'"+id+"',";
		for(Object key:args.keySet().toArray())
		{
			if(key.toString().equalsIgnoreCase("ID"))
			{
				continue;
			}
			if(args.get(key)==null)
			{
				continue;
			}
			EcmAttribute en = CacheManagerOper.getEcmAttributes().get(key);
			//EcmFormItem  en = getFormItem(frm.getEcmFormItems(),key.toString());
			if(en==null)
			{
				throw new Exception("Type :"+typeName+" ,"+key.toString()+" is not exists.");
			}
			switch(en.getDataType()) {
				case 5://日期
				{
					String date = args.get(key).toString();
					date= DBUtils.getDBDateString(date);
					if(date==null||date.length()<1)
						continue;
					fieldStr += key.toString()+",";
					valueStr += " "+ date+" ,";
					break;
				}
				case 6://布尔
				{
					fieldStr += key.toString()+",";
					if(args.get(key).toString().equals("true"))
					{
						valueStr += "1,";
					}
					else
					{
						valueStr += "0,";
					}
					break;
				}
				case 2:
				case 3:
				case 4:
				{
					valueStr +=  args.get(key).toString()+",";
					fieldStr += key.toString()+",";
					break;
				}
				default://字符串
				{
					fieldStr += key.toString()+",";
					valueStr += "'"+DBUtils.getString((String)args.get(key))+"',";
					break;
				}
			}
		}
		String ownerName = args.get("OWNER_NAME")!=null && args.get("OWNER_NAME").toString().length()>0?args.get("OWNER_NAME").toString():getSession(token).getCurrentUser().getUserName();
		fieldStr += "CREATION_DATE,CREATOR,VERSION_ID,OWNER_NAME";
		valueStr += DBUtils.getDBDateNow() + ",'"+getSession(token).getCurrentUser().getUserName()+"','"+id+"','"+ownerName+"'";
		//get acl name from folder when Acl Name is empty
		if(StringUtils.isEmpty((String)args.get("ACL_NAME")))
		{
			if(!StringUtils.isEmpty((String)args.get("FOLDER_ID")))
			{
				EcmFolder fld = folderService.getObjectById(token, args.get("FOLDER_ID").toString());
				if(fld!=null && !StringUtils.isEmpty(fld.getAclName())) {
					fieldStr += ",ACL_NAME";
					valueStr += ",'"+fld.getAclName()+"'";
				}
			}
		}
		sql = String.format(sql, fieldStr, valueStr);
		ecmDocument.executeSQL(sql);
		return id;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateObject(String token,EcmDocument doc,EcmContent content) throws Exception {
		if(content!=null) {
			if(getPermit(token, doc.getId())<ObjectPermission.WRITE_CONTENT) {
				throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no update permission:"+doc.getId());
			}
			if(StringUtils.isEmpty(content.getFormatName())) {
				throw new Exception("Format cannot be empty.");
			}
			content.setContentType(1);
			if(StringUtils.isEmpty(content.getStoreName())) {
				content.setStoreName(CacheManagerOper.getEcmStores().get(doc.getTypeName()).getName());
			}
			content.setParentId(doc.getId());
			EcmContent primary = contentService.getPrimaryContent(token, doc.getId());
			if(primary != null) {
				content.setId(primary.getId());
				content.setStoreName(primary.getStoreName());
				content.setFilePath(primary.getFilePath());
			}
			contentService.updateObject(token, content);
		}
		this.updateObject(token, doc.getAttributes());
		addFullIndexSearchQueue(token, doc.getId());
	}

	
	private void updateObject(String token, Map<String, Object> args) throws Exception {
		
		// TODO Auto-generated method stub
		String id = args.get("ID").toString();
		if(getPermit(token, id)<ObjectPermission.WRITE_ATTRIBUTE) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no update permission:"+id);
		}
		//String typeName = args.get("TYPE_NAME").toString();
		String sql = "update ecm_document set ";
		boolean isFirst = true;
		for(Object key:args.keySet().toArray())
		{
			EcmAttribute en = CacheManagerOper.getEcmAttributes().get(key);
			String attrName = key.toString();
			// filter system columns
			if(systemColumns.indexOf(","+attrName+",")>-1){
				continue;
			}
			if(en==null)
			{
				throw new Exception("Object :"+id+" ,"+key.toString()+" is not exists.");
			}
			switch(en.getDataType()) {
			case 5:
				if(!isFirst)
				{
					sql += ",";
				}
				else
				{
					isFirst = false;
				}
				if(args.get(key)==null)
				{
					sql += " "+ key.toString() + "="+ DBUtils.getDBNullDate();
				}
				else
				{
					String date = (String)args.get(key);
					date= DBUtils.getDBDateString(date);
					if(date==null||date.length()<1)
						continue;
					sql += " "+ key.toString() + "= "+ date+" ";
				}
				break;
			case 6:
				if(args.get(key)==null)
				{
					continue;
				}
				if(!isFirst)
				{
					sql += ",";
				}
				else
				{
					isFirst = false;
				}
				if(args.get(key).toString().equalsIgnoreCase("true"))
				{
					sql += " "+ key.toString() + "=1";
				}
				else
				{
					sql += " "+ key.toString() + "=0";
				}
			case 2:
			case 3:
			case 4:
				if(args.get(key)==null)
				{
					continue;
				}
				if(!isFirst)
				{
					sql += ",";
				}
				else
				{
					isFirst = false;
				}
				sql += " "+ key.toString() + "="+ args.get(key).toString();
				break;
			default:
				if(args.get(key)==null)
				{
					continue;
				}
				if(!isFirst)
				{
					sql += ",";
				}
				else
				{
					isFirst = false;
				}
				sql += " "+ key.toString() + "='"+ DBUtils.getString((String)args.get(key))+"'";
				break;
			}
		}
		if(!isFirst)
		{
			sql += ",";
		}
		else
		{
			isFirst = false;
		}
		sql += " MODIFIED_DATE="+DBUtils.getDBDateNow();
		sql += ", MODIFIER='"+getSession(token).getCurrentUser().getUserName()+"'";
		sql += ", LOCK_OWNER='', LOCK_DATE="+DBUtils.getDBNullDate()+", LOCK_CLIENT=''";
		sql += " where ID='"+id+"'";
		ecmDocument.executeSQL(sql);
		newAudit(token,null, AuditContext.UPDATE, id, null, null);
	}
	@Override
	public boolean executeSQL(String token, String sql) {
		ecmDocument.executeSQL(sql);
		return true;
	}
	
	
	@Override
	public List<Map<String, Object>> getMapList(String token, String sql) throws EcmException {
		// TODO Auto-generated method stub
		return ecmDocument.executeSQL(sql);
	}
	
	private EcmFormItem getFormItem(List<EcmFormItem> list,String attrName)
	{
		for(EcmFormItem en:list)
		{
			if(en.getAttrName().equalsIgnoreCase(attrName))
			{
				return en;
			}
		}
		return null;
	}

	@Override
	public int getPermit(String token, String id) throws AccessDeniedException {
		EcmDocument doc = getObjectById(token, id);
		return getPermit(token, doc);
	}
	
	@Override
	public int getPermit(String token, EcmDocument doc) throws AccessDeniedException {
		// TODO Auto-generated method stub
		//超级用户返回最大权限
		if(getSession(token).getCurrentUser().getSystemPermission()>=SystemPermission.SUPER_USER) {
			return 9;
		}
		String currentUser = getSession(token).getCurrentUser().getUserName();
		String userID = getSession(token).getCurrentUser().getUserId();
		String aclName = doc.getAclName();
		//没有设置ACL
		if(StringUtils.isEmpty(aclName)) {
			//Owner 返回最大权限
			if(currentUser.equals(doc.getOwnerName())) {
				return 9;
			}
			else {
				return ObjectPermission.READ;
			}
		}
		else {
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if(acl==null)
			{
				//Owner 返回最大权限
				if(currentUser.equals(doc.getOwnerName())) {
					return 9;
				}
				else {
					return ObjectPermission.READ;
				}
			}
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("select max(PERMISSION) from(select PERMISSION from ecm_acl_item a, ecm_acl b where ");
				sb.append("b.NAME='").append(aclName);
				sb.append("' and a.PARENT_ID = b.ID and a.TARGET_TYPE='1' and a.TARGET_NAME in('everyone'");
				sb.append(",'").append(currentUser).append("'");
				if(currentUser.equals(doc.getOwnerName())) {
					sb.append(",'owner'");
				}
				sb.append(")");
				sb.append(" union ");
				sb.append("select PERMISSION from ecm_acl_item a, ecm_acl b, ecm_group c, ecm_group_user d where b.NAME='");
				sb.append(aclName).append("'  and a.PARENT_ID = b.ID and a.TARGET_TYPE='2' and a.TARGET_NAME=c.NAME and c.ID=d.GROUP_ID and ");
				sb.append("d.USER_ID='").append(userID).append("') as permittemp");
				List<Map<String, Object>> list = ecmDocument.executeSQL(sb.toString());
				
				if(list.size()>0) {
					return (int)list.get(0).get("PERMISSION");
				}
			}
		}
		return 1;
	}
	
	@Override
	public void grantGroup(String token, String id,String targetName,int permission,Date expireDate,boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		EcmDocument doc = getObjectById(token, id);
		grantGroup( token,  doc, targetName, permission, expireDate, newAcl);

	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void grantGroup(String token, EcmDocument doc,String targetName,int permission,Date expireDate,boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		if(doc != null) {
			
			if(getPermit(token, doc.getId())<ObjectPermission.PEMISSION) {
				throw new EcmException("User "+getSession(token).getCurrentUser().getUserName()+" has no right to change permission:"+doc.getId());
			}
			String aclName = doc.getAclName();
			if(!StringUtils.isEmpty(aclName)) {
				EcmAcl acl = aclService.getObjectByName(token, aclName);
				if(newAcl) {
					acl = aclService.copy(token, acl);
					updateAclName(token, doc.getId(), acl.getName());
				}
				aclName = acl.getName();
				aclService.grantGroup(token, acl.getId(), targetName, permission, expireDate);
			}
			else {
				EcmAcl acl = new EcmAcl();
				acl.createId();
				acl.setName("ecm_"+acl.getId());
				aclService.newObject(token, acl);
				aclService.grantGroup(token, acl.getId(), targetName, permission, expireDate);
				updateAclName(token, doc.getId(), acl.getName());
				aclName = acl.getName();
			}
			newAudit(token,null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
	}
	
	@Override
	public void grantUser(String token, String id,String targetName,int permission,Date expireDate,boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		EcmDocument doc = getObjectById(token, id);
		grantUser( token,  doc, targetName, permission, expireDate, newAcl);

	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void grantUser(String token, EcmDocument doc,String targetName,int permission,Date expireDate,boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		if(doc != null) {
			if(getPermit(token, doc.getId())<ObjectPermission.PEMISSION) {
				throw new EcmException("User "+getSession(token).getCurrentUser().getUserName()+" has no right to change permission:"+doc.getId());
			}
			String aclName = doc.getAclName();
			if(!StringUtils.isEmpty(aclName)) {
				EcmAcl acl = aclService.getObjectByName(token, aclName);
				if(acl!=null) {
					if(newAcl) {
						acl = aclService.copy(token, acl);
						updateAclName(token, doc.getId(), acl.getName());
					}
					aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
					aclName = acl.getName();
				}
				else {
					acl = new EcmAcl();
					acl.createId();
					acl.setName("ecm_"+acl.getId());
					aclService.newObject(token, acl);
					updateAclName(token, doc.getId(), acl.getName());
					aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
					aclName = acl.getName();
				}
			}
			else {
				EcmAcl acl = new EcmAcl();
				acl.createId();
				acl.setName("ecm_"+acl.getId());
				aclService.newObject(token, acl);
				aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
				updateAclName(token, doc.getId(), acl.getName());
				aclName = acl.getName();
			}
			newAudit(token,null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
	}
	@Override
	public void revokeUser(String token,String id,String targetName,boolean newAcl) throws Exception {
		EcmDocument doc = getObjectById(token, id);
		revokeUser(token,doc,targetName,newAcl);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void revokeUser(String token,EcmDocument doc,String targetName,boolean newAcl) throws Exception {
		if(doc != null) {
			if(getPermit(token, doc.getId())<ObjectPermission.PEMISSION) {
				throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no right to change permission:"+doc.getId());
			}
			String aclName = doc.getAclName();
			if(StringUtils.isEmpty(aclName)) {
				return;
			}
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if(acl!=null) {
				if(newAcl) {
					acl = aclService.copy(token, acl);
					updateAclName(token, doc.getId(), acl.getName());
					aclName = acl.getName();
				}
				aclService.revokeUser(token, acl.getId(), targetName);
			}
			newAudit(token,null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
	}
	
	@Override
	public void revokeGroup(String token,String id,String targetName,boolean newAcl) throws Exception {
		EcmDocument doc = getObjectById(token, id);
		revokeGroup(token,doc,targetName,newAcl);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void revokeGroup(String token,EcmDocument doc,String targetName,boolean newAcl) throws Exception {
		if(doc != null) {
			if(getPermit(token, doc.getId())<ObjectPermission.PEMISSION) {
				throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no right to change permission:"+doc.getId());
			}
			String aclName = doc.getAclName();
			if(StringUtils.isEmpty(aclName)) {
				return;
			}
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if(acl!=null) {
				if(newAcl) {
					acl = aclService.copy(token, acl);
					updateAclName(token, doc.getId(), acl.getName());
					aclName = acl.getName();
				}
				aclService.revokeGroup(token, acl.getId(), targetName);
			}
			newAudit(token,null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
	}
	
	@Override
	public boolean unlock(String token, String id) throws Exception {
		EcmDocument doc = this.getObjectById(token, id);
		return this.unlock(token, doc);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean unlock(String token, EcmDocument doc) throws Exception {
		if(doc == null) {
			throw new Exception("Document is null.");
		}
		IEcmSession session = getSession(token);
		if(StringUtils.isEmpty(doc.getLockOwner()))
		{
			return true;
		}
		// Document can unlock by lock owner or supper user.
		if(session.getCurrentUser().getSystemPermission()>= SystemPermission.SUPER_USER
				|| session.getCurrentUser().getUserName().equals(doc.getLockOwner())) {
			if(getPermit(token,doc.getId())<ObjectPermission.WRITE_ATTRIBUTE) {
				throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no right to change permission:"+doc.getId());
			}
			String sql = "update ecm_document set LOCK_OWNER='',LOCK_DATE="+DBUtils.getDBNullDate()
			+ " where ID='"+doc.getId()+"'";
			List<Map<String,Object>> result =ecmDocument.executeSQL(sql);
			newAudit(token,null, AuditContext.UNLOCK, doc.getId(), null, null);
			return true;
		}
		else {
			throw new Exception("Document:"+doc.getId()+" is clocked by:"+doc.getLockOwner());
		}
	}
	
	@Override
	public boolean lock(String token, String id) throws Exception {
		EcmDocument doc = this.getObjectById(token, id);
		return this.lock(token, doc);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean lock(String token, EcmDocument doc) throws Exception {
		if(doc == null) {
			throw new Exception("Document is null.");
		}
		IEcmSession session = getSession(token);
		if(session.getCurrentUser().getUserName().equals(doc.getLockOwner())) {
			return true;
		}
		if(StringUtils.isEmpty(doc.getLockOwner())) {
			String id = doc.getId();
			
			if(StringUtils.isEmpty(id)) {
				return false;
			}
			if(getPermit(token,id)<ObjectPermission.WRITE_ATTRIBUTE) {
				throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no right to change permission:"+id);
			}
			String sql = "update ecm_document set LOCK_OWNER='"+session.getCurrentUser().getUserName()+"',LOCK_DATE="+DBUtils.getDBDateNow()
			+ " where ID='"+id+"'";
			List<Map<String,Object>> result =ecmDocument.executeSQL(sql);
			newAudit(token,null, AuditContext.LOCK, doc.getId(), null, null);
			return true;
		}
		else {
			throw new Exception("Document is locked by :"+doc.getLockOwner());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addRendition(String token, String id, EcmContent content) throws Exception {
		if(getPermit(token,id)<ObjectPermission.WRITE_CONTENT) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no add rendition permission:"+id);
		}
		EcmContent primary = contentService.getPrimaryContent(token, id);
		if(primary == null) {
			throw new Exception("Primary Content is null.");
		}
		EcmDocument doc = getObjectById(token, id);
		if(doc != null && content != null) {
			if(StringUtils.isEmpty(content.getName())) {
				throw new Exception("Name cannot be empty.");
			}
			if(StringUtils.isEmpty(content.getFormatName())) {
				throw new Exception("Format cannot be empty.");
			}
			EcmContent existCont = contentService.getObject(token, id, 2, content.getFormatName());
			if(existCont != null) {
				existCont.setName(content.getName());
				existCont.setInputStream(content.getInputStream());
				contentService.updateObject(token, existCont);
			}
			else {
				content.createId();
				content.setParentId(id);
				if(StringUtils.isEmpty(content.getStoreName())) {
					content.setStoreName(CacheManagerOper.getEcmStores().get(doc.getTypeName()).getName());
				}
				contentService.newObject(token, content);
			}
			updateModifyInfo(token, id);
			newAudit(token,null, AuditContext.ADD_REDITION, id, null, content.getName());
			return true;
		}
		return false;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeRendition(String token, String id, String formatName) throws Exception {
		if(getPermit(token,id)<ObjectPermission.WRITE_CONTENT) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no add rendition permission:"+id);
		}
		EcmDocument doc = getObjectById(token, id);
		if(doc != null)
		{
			EcmContent existCont = contentService.getObject(token, id, 2, formatName);
			if(existCont!=null) {
				contentService.deleteObject(token, existCont);
				updateModifyInfo(token, id);
				newAudit(token,null, AuditContext.REMOVE_REDITION, id, null, existCont.getName());
			}
		}
		return true;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeRendition(String token, String contentId) throws Exception {
		EcmContent existCont = contentService.getObjectById(token, contentId);
		if(existCont!=null) {
			String docId = existCont.getParentId();
			if(getPermit(token,docId)<ObjectPermission.WRITE_CONTENT) {
				throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no add rendition permission:"+docId);
			}
			EcmDocument doc = getObjectById(token, docId);
			if(doc != null)
			{
				if(existCont.getContentType()==1) {
					//主格式处理
				}
				contentService.deleteObject(token, existCont);
				updateModifyInfo(token, docId);
				newAudit(token,null, AuditContext.REMOVE_REDITION, docId, null, existCont.getName());
			}
		}
		return true;
	}
	
	@Override
	public List<EcmContent> getAllRendition(String token, String id) throws Exception{
		if(getPermit(token,id)<ObjectPermission.READ) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no read permission:"+id);
		}
		return contentService.getObjects(token, id, 2);
	}
	
	private void updateAclName(String token, String id, String aclName) throws AccessDeniedException {
		String sql = "update ecm_document set ACL_NAME='"+aclName+"', ";
		sql += " MODIFIED_DATE="+DBUtils.getDBDateNow();
		sql += ", MODIFIER='"+getSession(token).getCurrentUser().getUserName()+"'";
		sql += " where ID='"+id+"'";
		ecmDocument.executeSQL(sql);
	}
	
	private void updateModifyInfo(String token, String id) throws AccessDeniedException {
		String sql = "update ecm_document set";
		sql += " MODIFIED_DATE="+DBUtils.getDBDateNow();
		sql += ", MODIFIER='"+getSession(token).getCurrentUser().getUserName()+"'";
		sql += " where ID='"+id+"'";
		ecmDocument.executeSQL(sql);
	}

	@Override
	public EcmContent getContent(String token, String id) throws Exception {
		if(getPermit(token,id)<ObjectPermission.READ) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no read permission:"+id);
		}
		List<EcmContent> list = contentService.getObjects(token, id, 1);
		{
			if(list.size()>0) {
				return list.get(0);
			}
		}
		return null;
	}
	
	@Override
	public boolean checkOut(String token, String id) throws Exception {
		EcmDocument doc = this.getObjectById(token, id);
		return this.checkOut(token, doc);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean checkOut(String token, EcmDocument doc) throws Exception {
		if(doc == null) {
			throw new Exception("Document is null.");
		}
		if(getPermit(token,doc.getId())<ObjectPermission.WRITE_CONTENT) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no write content permission:"+doc.getId());
		}
		IEcmSession session = getSession(token);
		if(session.getCurrentUser().getUserName().equals(doc.getLockOwner())) {
			return true;
		}
		if(StringUtils.isEmpty(doc.getLockOwner())) {
			String id = doc.getId();
			
			if(StringUtils.isEmpty(id)) {
				return false;
			}
			if(getPermit(token,id)<ObjectPermission.WRITE_ATTRIBUTE) {
				throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no right to change permission:"+id);
			}
			String appName = session.getCurrentUser().getAppName();

			if(StringUtils.isEmpty(appName)) {
				appName = ActionContext.APP_CORE;
			}
			String sql = "update ecm_document set LOCK_OWNER='"+session.getCurrentUser().getUserName()+"',LOCK_DATE="+DBUtils.getDBDateNow()
			+ ", LOCK_CLIENT='"+appName+"' where ID='"+id+"'";
			List<Map<String,Object>> result =ecmDocument.executeSQL(sql);
			newAudit(token,null, AuditContext.LOCK, doc.getId(), null, null);
			return true;
		}else {
			throw new Exception("Document is locked by :"+doc.getLockOwner());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public EcmDocument checkIn(String token,String docId, EcmContent content, boolean isCurrent) throws Exception {
		EcmDocument doc = this.getObjectById(token, docId);
		if(doc == null) {
			throw new Exception("Document is not exits:"+docId);
		}
		if(getPermit(token,docId)<ObjectPermission.WRITE_CONTENT) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no read permission:"+docId);
		}
		IEcmSession session = getSession(token);
		doc.createId();
		doc.setCreationDate(new Date());
		doc.setCreator(session.getCurrentUser().getUserName());
		doc.setModifiedDate(new Date());
		doc.setModifier(session.getCurrentUser().getUserName());
		doc.setLockOwner("");
		doc.setLockDate(null);
		doc.setLockClient(null);
		// system version add 1
		doc.setSystemVersion(doc.getSystemVersion()+1);
		
		if(isCurrent) {
			doc.setCurrent(true);
		}
		if(content != null) {
			if(StringUtils.isEmpty(content.getFormatName())) {
				throw new Exception("Format cannot be empty.");
			}
			content.createId();
			content.setContentType(1);
			content.setParentId(doc.getId());
			if(StringUtils.isEmpty(content.getStoreName())) {
				content.setStoreName(CacheManagerOper.getEcmStores().get(doc.getTypeName()).getName());
			}
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
		}
		newObject(token,doc.getAttributes());
		//如果上传了文件，设置文件为上传文件
		if(content != null) {
			contentService.newObject(token,content);
		}
		else {
			//从上一版复制电子文件
			contentService.copyContent(token, docId, doc.getId());
		}
		// 更新上一版信息
		String sql = "update ecm_document set LOCK_OWNER='',LOCK_DATE="+DBUtils.getDBNullDate()
		+ ", LOCK_CLIENT=''";
		if(isCurrent) {
			sql += ",is_current=0";
		}
		sql += " where ID='"+docId+"'";
		ecmDocument.executeSQL(sql);
		return doc;
	}
	@Override
	public List<EcmDocument> getAllVersions(String token, String id) throws Exception{
		if(getPermit(token,id)<ObjectPermission.BROWSER) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no browse permission:"+id);
		}
		String sql = "select a.* from ecm_document a, ecm_docuemnt b where a.VERSION_ID=b.VERSION_ID and b.ID='"+id+"' order by a.SYSTEM_VERSION DESC";
		List<Map<String, Object>>  list = ecmDocument.executeSQL(sql);
		List<EcmDocument> docs = new ArrayList<EcmDocument>();
		for(Map<String, Object> map:list) {
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(map);
			docs.add(doc);
		}
		return docs;
	}
	
	@Override
	public List<Map<String, Object>> getAllVersionsMap(String token, String id) throws Exception{
		if(getPermit(token,id)<ObjectPermission.BROWSER) {
			throw new Exception("User "+getSession(token).getCurrentUser().getUserName()+" has no browse permission:"+id);
		}
		String sql = "select a.* from ecm_document a, ecm_docuemnt b where a.VERSION_ID=b.VERSION_ID and b.ID='"+id+"' order by a.SYSTEM_VERSION DESC";
		List<Map<String, Object>>  list = ecmDocument.executeSQL(sql);
		return list;
	}
	
	@Override
	public void queue(String token, String id, String name, String eventName, String message) throws EcmException, AccessDeniedException {
		EcmQueueItem queueItem = new EcmQueueItem();
		queueItem.createId();
		queueItem.setObjectId(id);
		queueItem.setName(name);
		queueItem.setEventName(eventName);
		queueItem.setMessage(message);
		queueItem.setDelectFlag(false);
		queueItem.setStatus(0);
		queueItemService.newObject(token, queueItem);
	}
	
	/**
	 * 添加全文索引队列
	 * @param token
	 * @param docID
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 */
	private void addFullIndexSearchQueue(String token, String docID) throws EcmException, AccessDeniedException {
		EcmParameter parmIndexServer = (CacheManagerOper.getEcmParameters().get("IndexServer"));
		if(parmIndexServer!=null && !StringUtils.isEmpty(parmIndexServer.getValue())) {
			queue(token, docID, "ecm_full_index", "ecm_full_index", null);
		}
		
	}
}
