package com.ecm.core.service;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.AuditContext;
import com.ecm.core.PermissionContext.ObjectPermission;
import com.ecm.core.PermissionContext.SystemPermission;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.dao.EcmLifeCycleItemMapper;
import com.ecm.core.dao.EcmLifeCycleMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.db.SqlUtils;
import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormClassification;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmLifeCycle;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.MessageException;
import com.ecm.core.exception.NoOnlyPolicyException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.exception.UniquenessException;
import com.ecm.core.sync.SyncPublicNetUtil;
import com.ecm.icore.service.IDocumentService;
import com.ecm.icore.service.IEcmSession;
import com.ecm.icore.service.ILifeCycleEvent;

@Service
@Scope("prototype")
public class DocumentService extends EcmObjectService<EcmDocument> implements IDocumentService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

	private String baseColumns = "ID,FOLDER_ID,CREATION_DATE, CREATOR, MODIFIER,OWNER_NAME,"
			+ "MODIFIED_DATE,REVISION,ACL_NAME,FORMAT_NAME,CONTENT_SIZE,ATTACHMENT_COUNT,"
			+ "IS_CURRENT,IS_HIDDEN,SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,TYPE_NAME,C_ITEM_TYPE,LIFECYCLE_NAME,LIFECYCLE_STATUS,LIFECYCLE_DIR,STATUS";
	private String filterColumns = ",ID,CREATION_DATE,CREATOR,MODIFIER,OWNER_NAME,"
			+ "MODIFIED_DATE,FORMAT_NAME,CONTENT_SIZE,"
			+ "SYSTEM_VERSION,VERSION_ID,LOCK_OWNER,LOCK_DATE,LOCK_CLIENT,";

	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private ContentService contentServices;

	@Autowired
	private AclService aclService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private QueueItemService queueItemService;
	@Autowired
	private EcmLifeCycleItemMapper itemMapper;
	@Autowired
	private EcmLifeCycleMapper lifeCycleMapper;
	@Autowired
	private EcmLifeCycleItemMapper lifeCycleItemMapper;
	
	@Autowired
	private GridViewService gridViewService;
	@Autowired
	private GridViewItemService gridViewItemService;
	@Autowired
	private NumberService numberservice;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 通过配置子句查询对象
	 * @param token
	 * @param gridName
	 * @param parentId
	 * @param pager
	 * @param condition
	 * @param orderBy
	 * @return
	 * @throws MessageException 
	 */
	public List<Map<String, Object>> getObjectsConfigclause(String token, Pager pager,String configName,
			Map<String,Object> params) throws MessageException {
		String currentUser="";
		try {
			currentUser = getSession(token).getCurrentUser().getUserName();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String clauseSql = CacheManagerOper.getEcmParameters().get(configName).getValue();
		if(clauseSql==null) {
			throw new MessageException(configName+"没有配置，请先配置查询语句,如果已配置请刷新缓存！");
		}
		if(clauseSql.contains("@currentuser")) {
			clauseSql=clauseSql.replace("@currentuser", currentUser);
		}
		
		if(clauseSql.contains("@condition")) {
			if(params!=null&&params.containsKey("condition")) {
				clauseSql=clauseSql.replace("@condition", params.get("condition").toString());
			}else {
				clauseSql=clauseSql.replace("@condition", "");
			}
		}
		
		if(clauseSql.contains("@")) {
			Set<String> items= params.keySet();
			Iterator<String> keys= items.iterator();
			
			while(keys.hasNext()) {
				String key=keys.next();
				
				Object obj= params.get(key);
				if(obj==null) {
					clauseSql=clauseSql.replaceAll("@"+key, "");
				}else {
					clauseSql=clauseSql.replaceAll("@"+key, obj.toString());
				}
				
			}
		}
		List<Map<String, Object>> list=null;
		if(pager==null) {
			list = ecmDocument.executeSQL(clauseSql);
		}else {
			list = ecmDocument.executeSQL(pager, clauseSql);
		}
		// TODO Auto-generated method stub
		return list;
	}
	/**
	 * getObjects的升级版
	 * @param token
	 * @param gridName
	 * @param folderId
	 * @param pager
	 * @param condition
	 * @param orderBy
	 * @return
	 */
	public List<Map<String, Object>> getObjectsByObj(String token, Object gridName, Object folderId, Pager pager,
			Object condition, Object orderBy) {
		
		
		return getObjects(token,gridName==null?"":gridName.toString(),folderId==null?"":folderId.toString(),
				pager,condition==null?"":condition.toString(),orderBy==null?"":orderBy.toString());
	}
	@Override
	public List<EcmDocument> getObjects(String token, String condition) throws EcmException, SqlDeniedException {
		// TODO Auto-generated method stub
		return ecmDocument.getObjectsByCondition(condition);
	}
	
	
	public List<EcmDocument> getObjectsAllColumn(String token, String condition){
		String sql = "select "+getDocumentAllColumns()+" from ecm_document where "+condition;

		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		List<EcmDocument> data=new ArrayList<>();
		for(int i=0;list!=null&&i<list.size();i++) {
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(list.get(i));
			data.add(doc);
		}
		return data;
	}
	
	public List<EcmDocument> getObjects(String token,Pager page, String condition) throws EcmException, SqlDeniedException {
		// TODO Auto-generated method stub
		return ecmDocument.getObjectsByCondition(page,condition);
	}
	
	private EcmGridView getGridView(String token,String gridName) {
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		if(gv==null || gv.getGridViewItems()==null) {
			gv = gridViewService.getObjectByName(token, gridName);
			List<EcmGridViewItem> itemlist =gridViewItemService.getEcmCustomGridViewInfo(token, gv.getId());
			if(itemlist==null) {
				itemlist = new ArrayList<EcmGridViewItem>();
			}
			gv.setGridViewItems(itemlist);
		}
		return gv;
	}
	
	@Override
	public List<Map<String, Object>> getObjects(String token, String gridName, String folderId, Pager pager,
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
		
		EcmGridView gv = getGridView(token,gridName);
		String gvCondition=gv.getCondition();
		
		String sql = "select * from (select " + baseColumns + getGridColumn(gv, gridName) + " from ecm_document where "
				+ gvCondition;
		if (!StringUtils.isEmpty(folderId)) {
			sql += " and folder_id='" + folderId + "'";
		}
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " and (" + condition + ")";
		}
		sql+=") t";
		if (!EcmStringUtils.isEmpty(orderBy)) {
			sql += " order by " + orderBy;
		} else {
			sql += " " + gv.getOrderBy();
		}
		
		sql=SqlUtils.replaceSql(sql, userObj);
		logger.info("[DocumentService.getObjects=>SQL]");
		logger.info(sql);
		List<Map<String, Object>> list = ecmDocument.executeSQL(pager, sql);
		return list;
	}
	
	
	public List<Map<String, Object>> getObjectsByConditon(String token,String gridName,String folderId,Pager pager,String condition,String orderBy){
		EcmGridView gv = new EcmGridView();
		StringBuilder attrNames = new StringBuilder(",");
		if (gridName.contains("_CUSTOM")) {
			String customId = gridName.replace("_CUSTOM", "");
			List<Map<String, Object>> list = ecmDocument.executeSQL("select C_COMMENT from ecm_document where id ='"+customId+"'");
			attrNames.append(list.get(0).get("C_COMMENT"));
			attrNames.deleteCharAt(attrNames.length()-1);
		}else {
			gv = CacheManagerOper.getEcmGridViews().get(gridName);
		}
		String currentUser="";
		try {
			currentUser = getSession(token).getCurrentUser().getUserName();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(condition!=null&&condition.contains("@currentuser")) {
    		
			condition=condition.replaceAll("@currentuser", currentUser);
    	}
		String sql = "";
		if (gridName.contains("_CUSTOM")) {
			sql = "select " + baseColumns + attrNames.toString() + " from ecm_document where 1=1";
		}else {
			sql = "select " + baseColumns + getGridColumn(gv, gridName) + " from ecm_document where 1=1";
		}
		if (!StringUtils.isEmpty(folderId)) {
			sql += " and folder_id='" + folderId + "'";
		}
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " and (" + condition + ")";
		}
		if (!EcmStringUtils.isEmpty(orderBy)) {
			sql += " order by " + orderBy;
		} else {
			sql += " order by ID desc";
		}
		List<Map<String, Object>> list = ecmDocument.executeSQL(pager, sql);
		// TODO Auto-generated method stub
		return list;
		
	}
	

	@Override
	public List<Map<String, Object>> getObjectMap(String token, String condition) {

		String sql = "select "+getDocumentAllColumns()+" from ecm_document where " + condition;

		return ecmDocument.executeSQL(sql);
	}

	@Override
	public EcmDocument getObjectById(String token, String id) {

		String sql = "select "+getDocumentAllColumns()+" from ecm_document where ID='" + id + "'";

		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);

		if (list.size() > 0) {
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(list.get(0));
			return doc;
		}
		return null;
	}

	@Override
	public Map<String, Object> getObjectMapById(String token, String id) {

		String sql = "select * from ecm_document where ID='" + id + "'";

		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);

		if (list.size() > 0)
			return list.get(0);
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

	@Override
	public long getObjectCount(String token, String gridName, String folderId, String condition) {
		String sql = "select count(*) as objcount from ecm_document where 1=1 ";
		if (!EcmStringUtils.isEmpty(gridName)) {
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
			if (gv == null)
				return 0;
			sql += " and " + gv.getCondition();
		}
		if (!StringUtils.isEmpty(folderId)) {
			sql += " and folder_id='" + folderId + "'";
		}
		if (!EcmStringUtils.isEmpty(condition)) {
			sql += " and (" + condition + ")";
		}
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);

		return Long.parseLong(list.get(0).get("objcount").toString());
	}

	@Override
	public boolean updateStatus(String token, String id, String status) throws AccessDeniedException {
		if (StringUtils.isEmpty(id)) {
			return false;
		}
		String sql = "update ecm_document set STATUS='" + status + "' where ID='" + id + "'";
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
	public boolean updateStatus(String token, String id, String status, String comment) throws AccessDeniedException {
		if (StringUtils.isEmpty(id)) {
			return false;
		}
		comment = comment.replace("'", "''");
		String sql = "update ecm_document set STATUS='" + status + "',C_COMMENT='" + comment + "' where ID='" + id
				+ "'";
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
	public boolean deleteObject(String token, Object obj) throws NoPermissionException, AccessDeniedException, EcmException {
		String id = (String) obj;
		EcmDocument doc = ecmDocument.selectByPrimaryKey(id);
		if(doc == null) {
			return true;
		}
		if (getPermit(token, id) < ObjectPermission.DELETE) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no delete permission:" + id);
		}

		List<EcmContent> contents= contentServices.getObjects(token, " PARENT_ID='"+id+"'");
		
		for(int i=0;contents!=null&&i<contents.size();i++) {
			EcmContent c=contents.get(i);
			String storePath = CacheManagerOper.getEcmStores().get(c.getStoreName()).getStorePath();
			File f= new File(storePath + c.getFilePath());
			if (f.isFile() && f.exists()) {
				f.delete();
			}
			contentServices.deleteObjectById(token, c.getId());
		}
		
//		contentServices.deleteObject(token, id);
		boolean ret = ecmDocument.deleteByPrimaryKey(id) > 0;
		String msg  = doc.getCoding() != null ? doc.getCoding() : "";
		msg += " ; " + (doc.getRevision() != null ? doc.getRevision() : "");
		msg += " ; " + (doc.getTitle() != null ? doc.getTitle() : "");
		msg += " ; " + (doc.getName() != null ? doc.getName() : "");
		newAudit(token, null, AuditContext.DELETE, id, null, doc.getTypeName() + "," + msg);
		addFullIndexSearchQueue(token, id);
		return ret;
	}

	@Override
	public String newObject(String token, Object obj) throws EcmException {
		if (obj != null && obj instanceof EcmDocument) {
			try {
				return newObject(token, ((EcmDocument) obj).getAttributes());
			} catch (Exception e) {
				throw new EcmException(e.getMessage());
			}
		}
		return null;
	}
	
	@Override
	public String saveAsNew(String token, String id, Map<String, Object> attrValues, boolean includeContent) throws Exception {
		EcmDocument doc = getObjectById(token, id);
		if(doc != null) {
			doc.createId();
			doc.setAclName(null);
			doc.setVersionId(doc.getId());
			doc.setContentSize((long) 0);
			doc.setFormatName("");
			
			EcmContent content = null;
			//包含电子文件复制，后续实现
			if(includeContent) {
				
			}
			if( attrValues != null) {
				for(String attr : attrValues.keySet()) {
					doc.addAttribute(attr, attrValues.get(attr));
				}
			}
			doc.setCreationDate(new Date());
			doc.setCreator(getCurrentUser(token).getUserName());
			doc.setModifiedDate(new Date());
			doc.setModifier(getCurrentUser(token).getUserName());
			this.newObject(token, doc, content);
			return doc.getId();
		}
		return null;
	}
	/**
	 * 创建或修改文件属性或内容
	 * @param token
	 * @param doc 文件对象
	 * @param content 内容对象
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public String creatOrUpdateObject(String token, EcmDocument doc, EcmContent content) throws Exception {
		if(null==doc.getId()||"".equals(doc.getId())) {
			doc.createId();
			doc.setCurrent(true);
			newObject(token, doc.getAttributes());
		}else {
			updateObject(token, doc.getAttributes());
		}
		
		logger.info("doc id:{}", doc.getId());
		
		if (content != null) {
			if (StringUtils.isEmpty(content.getFormatName())) {
				throw new Exception("Format cannot be empty.");
			}
			
			logger.info("content id:{}", content.getId());
			content.setContentType(1);
			content.setParentId(doc.getId());
			logger.info("content parent id:{}", content.getParentId());
			if (StringUtils.isEmpty(content.getStoreName())) {
				String typeName=doc.getTypeName();
				if(typeName==null||"".equals(typeName)) {
					EcmDocument docx= getObjectById(token, doc.getId());
					typeName=docx.getTypeName();
				}
				content.setStoreName(CacheManagerOper.getEcmDefTypes().get(typeName).getStoreName());
			}
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
			
			EcmContent oldContent= getContent(token, doc.getId());
			if(oldContent!=null) {
				content.setId(oldContent.getId());
				content.setFilePath(oldContent.getFilePath());
				content.setDataTicket(oldContent.getDataTicket());
				contentServices.updateObject(token, content);
			}else {
				content.createId();
				contentServices.newObject(token, content);
			}
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
			updateObject(token, doc.getAttributes());
		}
		
		
		return doc.getId();
	}
	
	
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String newObject(String token, EcmDocument doc, EcmContent content) throws Exception {
		doc.createId();
		logger.info("doc id:{}", doc.getId());
//		doc.setCurrent(true);
		if (content != null) {
			if (StringUtils.isEmpty(content.getFormatName())) {
				throw new Exception("Format cannot be empty.");
			}
			content.createId();
			logger.info("content id:{}", content.getId());
			content.setContentType(1);
			content.setParentId(doc.getId());
			logger.info("content parent id:{}", content.getParentId());
			if (StringUtils.isEmpty(content.getStoreName())) {
				content.setStoreName(CacheManagerOper.getEcmDefTypes().get(doc.getTypeName()).getStoreName());
			}
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
		}
		String id = newObject(token, doc.getAttributes());
		if (content != null) {
			contentServices.newObject(token, content);
		}
		if(id!=null){
			return doc.getId();
		}else {
			return "";
		}
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String newObject(String token, Map<String, Object> args) throws EcmException, AccessDeniedException, NoPermissionException, UniquenessException {
		// TODO Auto-generated method stub
		String id = null;
		if(validate(token,args))
		{
			String typeName = args.get("TYPE_NAME").toString();
			String msg  = args.get("CODING") != null ? args.get("CODING").toString() : "";
			msg += " ; " + (args.get("REVISION") != null ? args.get("REVISION").toString() : "");
			msg += " ; " + (args.get("TITLE") != null ? args.get("TITLE").toString() : "");
			msg += " ; " + (args.get("NAME") != null ? args.get("NAME").toString() : "");
			id = createDocument(token, args);
			addFullIndexSearchQueue(token, id);
			// 添加新建日志
			newAudit(token, null, AuditContext.CREATE, id, null, typeName + "," + msg);
		}
		return id;
	}

	/**
	 * 创建文档
	 * 
	 * @param token
	 * @param args
	 * @return
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 * @throws Exception
	 */
	private String createDocument(String token, Map<String, Object> args) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		String typeName = args.get("TYPE_NAME").toString();
		String name = args.get("NAME") != null ? args.get("NAME").toString() : "";
		String sql = "insert into ecm_document (%s) values(%s)";
		String fieldStr = "";
		String valueStr = "";
		String id = args.get("ID") != null ? args.get("ID").toString() : "";
		if (StringUtils.isEmpty(id)) {
			id = newUUID();
		}
		fieldStr += "ID,";
		valueStr = "'" + id + "',";

		boolean hasCreationDate = false;
	
		for (Object key : args.keySet().toArray()) {
			if (key.toString().equalsIgnoreCase("ID")
					||key.toString().equalsIgnoreCase("transferId")
					||key.toString().equalsIgnoreCase("folderPath")
					||key.toString().equalsIgnoreCase("folderId")
					//||key.toString().equalsIgnoreCase("CREATION_DATE")
					||key.toString().equalsIgnoreCase("CREATOR")
					// ||key.toString().equalsIgnoreCase("VERSION_ID")
					||key.toString().equalsIgnoreCase("owner_name")
					||key.toString().equalsIgnoreCase("parentDocId")
					||key.toString().equalsIgnoreCase("relationName")) {
				continue;
			}
			
			if (args.get(key) == null) {
				continue;
			}
			EcmAttribute en = CacheManagerOper.getEcmAttributes().get(key);
			// EcmFormItem en = getFormItem(frm.getEcmFormItems(),key.toString());
			if (en == null) {
				throw new EcmException("Type :" + typeName + " ," + key.toString() + " is not exists.");
			}
			switch (en.getFieldType()) {
			case 2:// 日期
			{
				String date = args.get(key).toString();
				if(args.get(key) instanceof Date) {
					date = DateUtils.DateToStr((Date)args.get(key),"yyyy-MM-dd HH:mm:ss");
					date = "'"+date+"'";
				}else {
					if("{now}".equals(date)) {
						date=DateUtils.DateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
						date = "'"+date+"'";
					}else {
						
						date = date.replace(".", "-");
						date = date.replace("/", "-");
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(date);
					}
					
				}
				if (date == null || date.length() < 5)
				{
					continue;
				}else {
					if(key.toString().equalsIgnoreCase("CREATION_DATE")) {
						hasCreationDate = true;
					}
				}
				fieldStr += key.toString() + ",";
				valueStr += " " + date + " ,";
				break;
			}
			case 3:// 布尔
			{
				fieldStr += key.toString() + ",";
				if (args.get(key).toString().equalsIgnoreCase("true")||args.get(key).toString().equalsIgnoreCase("1")){
					valueStr += "1,";
				} else {
					valueStr += "0,";
				}
				break;
			}
			case 4:
			case 5:
			case 6:
			case 7:
			case 8: 
			{
				valueStr += args.get(key).toString() + ",";
				fieldStr += key.toString() + ",";
				break;
			}
			default:// 字符串
			{
				
				fieldStr += key.toString() + ",";
				
				if(key.toString().equalsIgnoreCase("VERSION_ID") 
						&& ((String) args.get(key)==null 
						|| ((String)args.get(key)).length()==0))
				{
					valueStr += "'" + id + "',";
				}else {
					Object datavalue = args.get(key);
					if(datavalue!=null) {
						String val=DBFactory.getDBConn().getDBUtils().getString(datavalue.toString());
						if(val.equalsIgnoreCase("@sequence")) {
							try {
								val=numberservice.getNumber(token, args);
							} catch (Exception e) {
								e.printStackTrace();
								throw new EcmException(e.getMessage());
							}
						}
						valueStr += "'" +val  + "',";
					}
				}
				
				break;
			}
			}
		}
		String ownerName = args.get("OWNER_NAME") != null && args.get("OWNER_NAME").toString().length() > 0
				? args.get("OWNER_NAME").toString()
				: getSession(token).getCurrentUser().getUserName();
		if(!hasCreationDate) {
			fieldStr += "CREATION_DATE,";
			valueStr += DBFactory.getDBConn().getDBUtils().getDBDateNow() + ",";
		}
		if(args.get("VERSION_ID") == null) {
			fieldStr += "VERSION_ID,";
			valueStr += "'" + id + "',";
		}
		fieldStr += "CREATOR,OWNER_NAME";
		valueStr +=  "'" +getSession(token).getCurrentUser().getUserName() 
				+ "','" + ownerName + "'";
		// get acl name from folder when Acl Name is empty
		if (StringUtils.isEmpty((String) args.get("ACL_NAME"))) {
			if (!StringUtils.isEmpty((String) args.get("FOLDER_ID"))) {
				EcmFolder fld = folderService.getObjectById(token, args.get("FOLDER_ID").toString());
				if (fld != null && !StringUtils.isEmpty(fld.getAclName())) {
					fieldStr += ",ACL_NAME";
					valueStr += ",'" + fld.getAclName() + "'";
				}
			}
		}
		sql = String.format(sql, fieldStr, valueStr);
		ecmDocument.executeSQL(sql);
		return id;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateObject(String token, EcmDocument doc, EcmContent content) throws NoPermissionException, AccessDeniedException, EcmException {
		if (content != null) {
			if (getPermit(token, doc.getId()) < ObjectPermission.WRITE_CONTENT) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no update permission:" + doc.getId());
			}
			if (StringUtils.isEmpty(content.getFormatName())) {
				throw new EcmException("Format cannot be empty.");
			}
			content.setContentType(1);
			if (StringUtils.isEmpty(content.getStoreName())) {
				content.setStoreName(CacheManagerOper.getEcmDefTypes().get(doc.getTypeName()).getStoreName());
			}
			content.setParentId(doc.getId());
			EcmContent primary = contentServices.getPrimaryContent(token, doc.getId());
			if (primary != null) {
				content.setId(primary.getId());
				content.setStoreName(primary.getStoreName());
				String filePath = primary.getFilePath();
				if(!content.getFormatName().equals(primary.getFormatName())) {
					filePath = filePath.replace("."+primary.getFormatName(), "."+content.getFormatName());
				}
				content.setFilePath(filePath);
				contentServices.updateObject(token, content);
			}
			else {
				contentServices.newObject(token, content);
			}
			
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
		}
		this.updateObject(token, doc.getAttributes());
		addFullIndexSearchQueue(token, doc.getId());
	}

	@Override
	public void updateObject(String token, Map<String, Object> args) throws NoPermissionException, AccessDeniedException, EcmException {

		// TODO Auto-generated method stub
		String id = args.get("ID").toString();
		if (getPermit(token, id) < ObjectPermission.WRITE_ATTRIBUTE) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no update permission:" + id);
		}
		// String typeName = args.get("TYPE_NAME").toString();
		String sql = "update ecm_document set ";
		boolean isFirst = true;
		String filter = filterColumns.replace("FORMAT_NAME,CONTENT_SIZE,", "");

		for (Object key : args.keySet().toArray()) {
			EcmAttribute en = CacheManagerOper.getEcmAttributes().get(key);
			String attrName = key.toString();
			// filter system columns

			if (filter.indexOf("," + attrName + ",") > -1) {
				continue;
			}
			if (en == null) {
				continue;
//				throw new EcmException("Object :" + id + " ," + key.toString() + " is not exists.");
			}
			switch (en.getFieldType()) {
			case 2:
				if (!isFirst) {
					sql += ",";
				} else {
					isFirst = false;
				}
				if (args.get(key) == null) {
					sql += " " + key.toString() + "=" + DBFactory.getDBConn().getDBUtils().getDBNullDate();
				} else {
					String date = "";
					if (args.get(key) instanceof Long) {
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(new Date((long)args.get(key)));
					}
					else if (args.get(key) instanceof Timestamp) {
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(((Timestamp) args.get(key)));
					}else if(args.get(key) instanceof Date) {
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(((Date) args.get(key)));
					}else {
						date = (String) args.get(key);
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(date);
					}
					if (date == null || date.length() < 1)
						continue;
					sql += " " + key.toString() + "= " + date + " ";
				}
				break;
			case 3:
				if (args.get(key) == null) {
					continue;
				}
				if (!isFirst) {
					sql += ",";
				} else {
					isFirst = false;
				}
				if (args.get(key).toString().equalsIgnoreCase("true")||args.get(key).toString().equalsIgnoreCase("1")) {
					sql += " " + key.toString() + "=1";
				} else {
					sql += " " + key.toString() + "=0";
				}
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				if (args.get(key) == null) {
					continue;
				}
				if (!isFirst) {
					sql += ",";
				} else {
					isFirst = false;
				}
				sql += " " + key.toString() + "=" + args.get(key).toString();
				break;
			default:
				if (args.get(key) == null) {
					continue;
				}
				if (!isFirst) {
					sql += ",";
				} else {
					isFirst = false;
				}
				sql += " " + key.toString() + "='" + DBFactory.getDBConn().getDBUtils().getString((String) args.get(key)) + "'";
				break;
			}
		}
		if (!isFirst) {
			sql += ",";
		} else {
			isFirst = false;
		}
		sql += " MODIFIED_DATE=" + DBFactory.getDBConn().getDBUtils().getDBDateNow();
		sql += ", MODIFIER='" + getSession(token).getCurrentUser().getUserName() + "'";
		sql += ", LOCK_OWNER='', LOCK_DATE=" + DBFactory.getDBConn().getDBUtils().getDBNullDate() + ", LOCK_CLIENT=''";
		sql += " where ID='" + id + "'";
		ecmDocument.executeSQL(sql);
		newAudit(token, null, AuditContext.UPDATE, id, null, null);
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

	public List<Map<String, Object>> getMapList(String token, String sql, Pager pager) throws EcmException {
		// TODO Auto-generated method stub
		return ecmDocument.executeSQL(pager, sql);
	}

	private EcmFormItem getFormItem(List<EcmFormItem> list, String attrName) {
		for (EcmFormItem en : list) {
			if (en.getAttrName().equalsIgnoreCase(attrName)) {
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
		// 超级用户返回最大权限
		if (getSession(token).getCurrentUser().getSystemPermission() >= SystemPermission.SUPER_USER) {
			return 9;
		}
		String currentUser = getSession(token).getCurrentUser().getUserName();
		String userID = getSession(token).getCurrentUser().getUserId();
		String aclName = doc.getAclName();
		
		// C_STRING1 知悉范围名称
		String scopeKnowledgeName = (String) doc.getAttributes().get("ITEM_SCOPE"); 
		
		//判断当前用户是否在文档的知悉范围中
		List<EcmGroup> glist = userService.getUserGroupsById(token, userID);
		int knowledgePermission=1; //无权限
		if(inGroup(glist, scopeKnowledgeName)) {
			knowledgePermission =  ObjectPermission.READ;
		}
		
		
		// 没有设置ACL
		if (StringUtils.isEmpty(aclName)) {
			// Owner 返回最大权限
			if (currentUser.equals(doc.getOwnerName())) {
				return 9;
			} else {
				return ObjectPermission.READ;
			}
		} else {
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if (acl == null) {
				// Owner 返回最大权限
				if (currentUser.equals(doc.getOwnerName())) {
					return 9;
				} else {
					return ObjectPermission.READ;
				}
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("select max(PERMISSION) as PERMISSION from(select PERMISSION from ecm_acl_item a, ecm_acl b where ");
				sb.append("b.NAME='").append(aclName);
				sb.append("' and a.PARENT_ID = b.ID and a.TARGET_TYPE='1' and (a.expire_date is null or a.expire_date>")
				.append(DBFactory.getDBConn().getDBUtils().getDBDateNow())
				.append(") and a.TARGET_NAME in('everyone'");
				sb.append(",'").append(currentUser).append("'");
				if (currentUser.equals(doc.getOwnerName())) {
					sb.append(",'owner'");
				}
				sb.append(")");
				sb.append(" union ");
				sb.append(
						"select PERMISSION from ecm_acl_item a, ecm_acl b, ecm_group c, ecm_group_user d where b.NAME='");
				sb.append(aclName).append(
						"'  and a.PARENT_ID = b.ID and a.TARGET_TYPE='2' and a.TARGET_NAME=c.NAME and c.ID=d.GROUP_ID and ");
				sb.append(" (a.expire_date is null or a.expire_date>")
						.append(DBFactory.getDBConn().getDBUtils().getDBDateNow()) 
						.append(") and ");
				sb.append("d.USER_ID='").append(userID).append("') as permittemp");
				List<Map<String, Object>> list = ecmDocument.executeSQL(sb.toString());

				if (list.size() > 0) {
					 int permissionNum = (int) list.get(0).get("PERMISSION");
					if(permissionNum > knowledgePermission) {
						return (int) list.get(0).get("PERMISSION");
					}else {
						return knowledgePermission;
					}
					
				}
			}
		}
		return 1;
	}

	/**
	 *      根据密级判断是否可以查看内容
	 * @param token
	 * @param doc
	 * @return
	 * @throws AccessDeniedException
	 */
	private boolean isReadableBySecurityLevel(String token, EcmDocument doc) throws AccessDeniedException {
		boolean flag = false;
		//用户ID 和用户IP 必须同时满足密级权限才可以查看
		boolean idFlag = false;
		boolean ipFlag = false;
		//1.核心涉密人员 2.重要涉密人员 3.一般涉密人员 4.非涉密人员 5.核心涉密人员 6.普通商密人员
		String loginName =  getSession(token).getCurrentUser().getLoginName();
		int userSecurityLevel = 0;
		userSecurityLevel = SyncPublicNetUtil.getSecurityLevelByUserIdUrl(loginName); 
		//实时URL 没有查到数据则通过保存的JSON文件去查询
		if(userSecurityLevel==0) {
			SyncPublicNetUtil syncUtil = new SyncPublicNetUtil();
			userSecurityLevel = syncUtil.getSecurityLevelByUserJson(token, loginName);
		}
		//1.NPIC普通商密 2.内部 3.秘密 4.机密 5.绝密 6.NPIC核心商密
		String userIp = getSession(token).getCurrentUser().getLoginIp();
		int ipSecurityLevel = 0;
		ipSecurityLevel	 = SyncPublicNetUtil.getSecurityLevelByIpUrl(userIp); 
		//实时URL 没有查到数据则通过保存的JSON文件去查询
		if(ipSecurityLevel==0) {
			SyncPublicNetUtil syncUtil = new SyncPublicNetUtil();
			ipSecurityLevel = syncUtil.getSecurityLevelByIpJson(token, userIp);
		}
		String  securityLevel = doc.getSecurityLevel();

		if(securityLevel!=null && userSecurityLevel!=0 && ipSecurityLevel!=0) {
			if(securityLevel.equals("非密") || securityLevel.equals("内部公开")) {
				flag = true; 
			}
			
			if (securityLevel.equals("普通商密") || securityLevel.equals("秘密")){
				if(userSecurityLevel==1 || userSecurityLevel==2 || userSecurityLevel==3 || userSecurityLevel==5 || userSecurityLevel==6) {
					idFlag = true; 
				}
				if(ipSecurityLevel==1 || ipSecurityLevel==3 || ipSecurityLevel==4 || ipSecurityLevel==5 || ipSecurityLevel==7) {
					ipFlag = true; 
				}
				if(idFlag && ipFlag) {
					flag = true; 
				}
			}
			if (securityLevel.equals("核心商密") || securityLevel.equals("机密")){
				if(userSecurityLevel==1 || userSecurityLevel==2 || userSecurityLevel==5) {
					idFlag = true; 
				}
				if(ipSecurityLevel==4 || ipSecurityLevel==5 || ipSecurityLevel==7 ) {
					ipFlag = true; 
				}
				if(idFlag && ipFlag) {
					flag = true; 
				}
			}
		}
		return flag;
	}
	
	
	@Override
	public String grantGroup(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmDocument doc = getObjectById(token, id);
		return grantGroup(token, doc, targetName, permission, expireDate, newAcl);

	}
	public String grantGroup(String token, String id, String targetName, int permission, Date expireDate)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmDocument doc = getObjectById(token, id);
		boolean newAcl=needNewAcl(token, id);
		return grantGroup(token, doc, targetName, permission, expireDate, newAcl);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String grantGroup(String token, EcmDocument doc, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		String aclName = "";
		if (doc != null) {

			if (getPermit(token, doc.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + doc.getId());
			}
			aclName = doc.getAclName();
			if (!StringUtils.isEmpty(aclName)) {
				EcmAcl acl = aclService.getObjectByName(token, aclName);
				if (newAcl) {
					acl = aclService.copy(token, acl, null, doc.getId());
					updateAclName(token, doc.getId(), acl.getName());
				}
				aclName = acl.getName();
				aclService.grantGroup(token, acl.getId(), targetName, permission, expireDate);
			} else {
				EcmAcl acl = new EcmAcl();
				acl.createId();
				acl.setName("ecm_" + acl.getId());
				aclService.newObject(token, acl);
				aclService.grantGroup(token, acl.getId(), targetName, permission, expireDate);
				updateAclName(token, doc.getId(), acl.getName());
				aclName = acl.getName();
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
		return aclName;
	}

	@Override
	public String grantUser(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmDocument doc = getObjectById(token, id);
		return grantUser(token, doc, targetName, permission, expireDate, newAcl);
	}
	
	public String grantUser(String token, String id, String targetName, int permission, Date expireDate)
			throws EcmException, AccessDeniedException, NoPermissionException {
		EcmDocument doc = getObjectById(token, id);
		boolean newAcl=needNewAcl(token,id);
		return grantUser(token, doc, targetName, permission, expireDate, newAcl);
	}
	
	private boolean needNewAcl(String token,String docId) {
		try {
			EcmDocument doc = this.getObjectById(token, docId);
			String aclName = doc.getAclName();
			if (StringUtils.isEmpty(aclName) || !aclName.startsWith("ecm_")) {
				return true;
			}
			String sql = "select count(*) as aclCount from ecm_document where ACL_NAME='" + aclName + "'";
			List<Map<String, Object>> list = this.getMapList(token, sql);
			if (list != null && list.size() > 0) {
				return Integer.parseInt(list.get(0).get("aclCount").toString()) > 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String grantUser(String token, EcmDocument doc, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException {
		String aclName = "";
		if (doc != null) {
			if (getPermit(token, doc.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + doc.getId());
			}
			aclName = doc.getAclName();
			if (!StringUtils.isEmpty(aclName)) {
				EcmAcl acl = aclService.getObjectByName(token, aclName);
				if (acl != null) {
					if (newAcl) {
						acl = aclService.copy(token, acl, null, doc.getId());
						updateAclName(token, doc.getId(), acl.getName());
					}
					aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
					aclName = acl.getName();
				} else {
					acl = new EcmAcl();
					acl.createId();
					acl.setName("ecm_" + acl.getId());
					aclService.newObject(token, acl);
					updateAclName(token, doc.getId(), acl.getName());
					aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
					aclName = acl.getName();
				}
			} else {
				EcmAcl acl = new EcmAcl();
				acl.createId();
				acl.setName("ecm_" + acl.getId());
				aclService.newObject(token, acl);
				aclService.grantUser(token, acl.getId(), targetName, permission, expireDate);
				updateAclName(token, doc.getId(), acl.getName());
				aclName = acl.getName();
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
		return aclName;
	}

	@Override
	public String revokeUser(String token, String id, String targetName, boolean newAcl) throws Exception {
		EcmDocument doc = getObjectById(token, id);
		return revokeUser(token, doc, targetName, newAcl);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String revokeUser(String token, EcmDocument doc, String targetName, boolean newAcl) throws NoPermissionException, AccessDeniedException {
		String aclName = "";
		if (doc != null) {
			if (getPermit(token, doc.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + doc.getId());
			}
			aclName = doc.getAclName();
			if (StringUtils.isEmpty(aclName)) {
				return "";
			}
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if (acl != null) {
				if (newAcl) {
					acl = aclService.copy(token, acl, null, doc.getId());
					updateAclName(token, doc.getId(), acl.getName());
					aclName = acl.getName();
				}
				aclService.revokeUser(token, acl.getId(), targetName);
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
		return aclName;
	}

	@Override
	public String revokeGroup(String token, String id, String targetName, boolean newAcl) throws Exception {
		EcmDocument doc = getObjectById(token, id);
		return revokeGroup(token, doc, targetName, newAcl);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String revokeGroup(String token, EcmDocument doc, String targetName, boolean newAcl) throws NoPermissionException, AccessDeniedException {
		String aclName = "";
		if (doc != null) {
			if (getPermit(token, doc.getId()) < ObjectPermission.PEMISSION) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + doc.getId());
			}
			aclName = doc.getAclName();
			if (StringUtils.isEmpty(aclName)) {
				return "";
			}
			EcmAcl acl = aclService.getObjectByName(token, aclName);
			if (acl != null) {
				if (newAcl) {
					acl = aclService.copy(token, acl, null, doc.getId());
					updateAclName(token, doc.getId(), acl.getName());
					aclName = acl.getName();
				}
				aclService.revokeGroup(token, acl.getId(), targetName);
			}
			newAudit(token, null, AuditContext.CHANGE_PERMIT, doc.getId(), null, aclName);
		}
		return aclName;
	}

	@Override
	public boolean unlock(String token, String id) throws Exception {
		EcmDocument doc = this.getObjectById(token, id);
		return this.unlock(token, doc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean unlock(String token, EcmDocument doc) throws NoPermissionException, EcmException, AccessDeniedException {
		if (doc == null) {
			throw new EcmException("Document is null.");
		}
		IEcmSession session = getSession(token);
		if (StringUtils.isEmpty(doc.getLockOwner())) {
			return true;
		}
		// Document can unlock by lock owner or supper user.
		if (session.getCurrentUser().getSystemPermission() >= SystemPermission.SUPER_USER
				|| session.getCurrentUser().getUserName().equals(doc.getLockOwner())) {
			if (getPermit(token, doc.getId()) < ObjectPermission.WRITE_ATTRIBUTE) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + doc.getId());
			}
			String sql = "update ecm_document set LOCK_OWNER='',LOCK_DATE=" + DBFactory.getDBConn().getDBUtils().getDBNullDate() + " where ID='"
					+ doc.getId() + "'";
			List<Map<String, Object>> result = ecmDocument.executeSQL(sql);
			newAudit(token, null, AuditContext.UNLOCK, doc.getId(), null, null);
			return true;
		} else {
			throw new EcmException("Document:" + doc.getId() + " is clocked by:" + doc.getLockOwner());
		}
	}

	@Override
	public boolean lock(String token, String id) throws Exception {
		EcmDocument doc = this.getObjectById(token, id);
		return this.lock(token, doc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean lock(String token, EcmDocument doc) throws NoPermissionException, EcmException, AccessDeniedException {
		if (doc == null) {
			throw new EcmException("Document is null.");
		}
		IEcmSession session = getSession(token);
		if (session.getCurrentUser().getUserName().equals(doc.getLockOwner())) {
			return true;
		}
		if (StringUtils.isEmpty(doc.getLockOwner())) {
			String id = doc.getId();

			if (StringUtils.isEmpty(id)) {
				return false;
			}
			if (getPermit(token, id) < ObjectPermission.WRITE_ATTRIBUTE) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + id);
			}
			String sql = "update ecm_document set LOCK_OWNER='" + session.getCurrentUser().getUserName()
					+ "',LOCK_DATE=" + DBFactory.getDBConn().getDBUtils().getDBDateNow() + " where ID='" + id + "'";
			List<Map<String, Object>> result = ecmDocument.executeSQL(sql);
			newAudit(token, null, AuditContext.LOCK, doc.getId(), null, null);
			return true;
		} else {
			throw new EcmException("Document is locked by :" + doc.getLockOwner());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addRendition(String token, String id, EcmContent content) 
			throws NoPermissionException, AccessDeniedException, EcmException {
		if (getPermit(token, id) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
					+ " has no add rendition permission:" + id);
		}
		EcmContent primary = contentServices.getPrimaryContent(token, id);
		if (primary == null) {
			throw new EcmException("Primary Content is null.");
		}
		EcmDocument doc = getObjectById(token, id);
		if (doc != null && content != null) {
			if (StringUtils.isEmpty(content.getName())) {
				throw new EcmException("Name cannot be empty.");
			}
			if (StringUtils.isEmpty(content.getFormatName())) {
				throw new EcmException("Format cannot be empty.");
			}
			EcmContent existCont = contentServices.getObject(token, id, 2, content.getFormatName());
			if (existCont != null) {
				existCont.setName(content.getName());
				existCont.setContentSize(content.getContentSize());
				existCont.setInputStream(content.getInputStream());
				contentServices.updateObject(token, existCont);
			} else {
				content.createId();
				content.setParentId(id);
				content.setContentType(2);
				if (StringUtils.isEmpty(content.getStoreName())) {
				    content.setStoreName(CacheManagerOper.getEcmDefTypes().get(doc.getTypeName()).getStoreName());
				}
				
				contentServices.newObject(token, content);
			}
			updateModifyInfo(token, id);
			newAudit(token, null, AuditContext.ADD_REDITION, id, null, content.getName());
			return true;
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeRendition(String token, String id, String formatName) throws NoPermissionException, AccessDeniedException {
		if (getPermit(token, id) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
					+ " has no add rendition permission:" + id);
		}
		EcmDocument doc = getObjectById(token, id);
		if (doc != null) {
			EcmContent existCont = contentServices.getObject(token, id, 2, formatName);
			if (existCont != null) {
				contentServices.deleteObject(token, existCont);
				updateModifyInfo(token, id);
				newAudit(token, null, AuditContext.REMOVE_REDITION, id, null, existCont.getName());
			}
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeRendition(String token, String contentId) throws NoPermissionException, AccessDeniedException {
		EcmContent existCont = contentServices.getObjectById(token, contentId);
		if (existCont != null) {
			String docId = existCont.getParentId();
			if (getPermit(token, docId) < ObjectPermission.WRITE_CONTENT) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no add rendition permission:" + docId);
			}
			EcmDocument doc = getObjectById(token, docId);
			if (doc != null) {
				if (existCont.getContentType() == 1) {
					// 主格式处理
				}
				contentServices.deleteObject(token, existCont);
				updateModifyInfo(token, docId);
				newAudit(token, null, AuditContext.REMOVE_REDITION, docId, null, existCont.getName());
			}
		}
		return true;
	}

	@Override
	public List<EcmContent> getAllRendition(String token, String id) throws NoPermissionException, AccessDeniedException {
		if (getPermit(token, id) < ObjectPermission.READ) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no read permission:" + id);
		}
		return contentServices.getObjects(token, id, 2);
	}

	private void updateAclName(String token, String id, String aclName) throws AccessDeniedException {
		String sql = "update ecm_document set ACL_NAME='" + aclName + "', ";
		sql += " MODIFIED_DATE=" + DBFactory.getDBConn().getDBUtils().getDBDateNow();
		sql += ", MODIFIER='" + getSession(token).getCurrentUser().getUserName() + "'";
		sql += " where ID='" + id + "'";
		ecmDocument.executeSQL(sql);
	}

	private void updateModifyInfo(String token, String id) throws AccessDeniedException {
		String sql = "update ecm_document set";
		sql += " MODIFIED_DATE=" + DBFactory.getDBConn().getDBUtils().getDBDateNow();
		sql += ", MODIFIER='" + getSession(token).getCurrentUser().getUserName() + "'";
		sql += " where ID='" + id + "'";
		ecmDocument.executeSQL(sql);
	}

	@Override
	public EcmContent getContent(String token, String id) throws NoPermissionException, AccessDeniedException {
		if (getPermit(token, id) < ObjectPermission.READ) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no read permission:" + id);
		}
		List<EcmContent> list = contentServices.getObjects(token, id, 1);
		{
			if (list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public InputStream getContentStream(String token, EcmContent en,String id) throws NoPermissionException, AccessDeniedException {
		
		InputStream iStream = null;
		if (getPermit(token, id) < ObjectPermission.READ) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no read permission:" + id);
		}
		EcmDocument doc = getObjectById(token, id);
		
		//cd 项目参数
		if(CacheManagerOper.getEcmParameters().get("CD_SECURITY_LEVEL")!= null) {
			if(CacheManagerOper.getEcmParameters().get("CD_SECURITY_LEVEL").getValue().equals("1")) {
				if(isReadableBySecurityLevel(token,doc)) {
					throw new NoPermissionException(
							"User " + getSession(token).getCurrentUser().getUserName() + " SecurityLevel  has no read   permission:" + id);
				}
			}
		}
		try {
			iStream = contentServices.getContentStream(token, en);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iStream;
	}
	
	
	
	
	@Override
	public boolean checkOut(String token, String id) throws Exception {
		EcmDocument doc = this.getObjectById(token, id);
		return this.checkOut(token, doc);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean checkOut(String token, EcmDocument doc) throws NoPermissionException, EcmException, AccessDeniedException {
		if (doc == null) {
			throw new EcmException("Document is null.");
		}
		if (getPermit(token, doc.getId()) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
					+ " has no write content permission:" + doc.getId());
		}
		IEcmSession session = getSession(token);
		if (session.getCurrentUser().getUserName().equals(doc.getLockOwner())) {
			return true;
		}
		if (StringUtils.isEmpty(doc.getLockOwner())) {
			String id = doc.getId();

			if (StringUtils.isEmpty(id)) {
				return false;
			}
			if (getPermit(token, id) < ObjectPermission.WRITE_ATTRIBUTE) {
				throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
						+ " has no right to change permission:" + id);
			}
			String appName = session.getCurrentUser().getAppName();

			if (StringUtils.isEmpty(appName)) {
				appName = ActionContext.APP_CORE;
			}
			String sql = "update ecm_document set LOCK_OWNER='" + session.getCurrentUser().getUserName()
					+ "',LOCK_DATE=" + DBFactory.getDBConn().getDBUtils().getDBDateNow() + ", LOCK_CLIENT='" + appName + "' where ID='" + id + "'";
			List<Map<String, Object>> result = ecmDocument.executeSQL(sql);
			newAudit(token, null, AuditContext.LOCK, doc.getId(), null, null);
			return true;
		} else {
			throw new EcmException("Document is locked by :" + doc.getLockOwner());
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void newRevisionTo(String token, String docId, Map<String, Object> attrMap, String toId, boolean isCurrent) throws Exception {
		EcmDocument doc = this.getObjectById(token, docId);
		if (doc == null) {
			throw new EcmException("Document is not exits:" + docId);
		}
		EcmDocument toDoc = this.getObjectById(token, toId);
		if (toDoc == null) {
			throw new EcmException("Document is not exits:" + toId);
		}
		if (getPermit(token, docId) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no read permission:" + docId);
		}
		if (getPermit(token, toId) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no read permission:" + toId);
		}
		
		if(attrMap != null) {
			for(String key: attrMap.keySet()) {
				doc.getAttributes().put(key, attrMap.get(key));
			}
		}
		doc.addAttribute("VERSION_ID", toDoc.getAttributeValue("VERSION_ID"));
		doc.addAttribute("SYSTEM_VERSION", toDoc.getSystemVersion()+1);
		doc.setCurrent(isCurrent);
		doc.addAttribute("C_IS_RELEASED", 1);
		this.updateObject(token, doc, null);
		if(isCurrent) {
			toDoc.setCurrent(false);
			this.updateObject(token, toDoc, null);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public EcmDocument checkIn(String token, String docId,Map<String, Object> attrMap, EcmContent content, boolean isCurrent) throws Exception {
		EcmDocument doc = this.getObjectById(token, docId);
		if (doc == null) {
			throw new EcmException("Document is not exits:" + docId);
		}
		if (getPermit(token, docId) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no read permission:" + docId);
		}
		IEcmSession session = getSession(token);
		if(attrMap != null) {
			for(String key: attrMap.keySet()) {
				doc.getAttributes().put(key, attrMap.get(key));
			}
		}
		doc.createId();
		doc.setCreationDate(new Date());
		doc.setCreator(session.getCurrentUser().getUserName());
		doc.setModifiedDate(new Date());
		doc.setModifier(session.getCurrentUser().getUserName());
		doc.setLockOwner("");
		doc.setLockDate(null);
		doc.setLockClient(null);
		// system version add 1
		doc.setSystemVersion(doc.getSystemVersion() + 1);

		if (isCurrent) {
			doc.setCurrent(true);
		}
		if (content != null) {
			if (StringUtils.isEmpty(content.getFormatName())) {
				throw new EcmException("Format cannot be empty.");
			}
			content.createId();
			content.setContentType(1);
			content.setParentId(doc.getId());
			if (StringUtils.isEmpty(content.getStoreName())) {
				content.setStoreName(CacheManagerOper.getEcmStores().get(doc.getTypeName()).getName());
			}
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
		}
		newObject(token, doc.getAttributes());
		// 如果上传了文件，设置文件为上传文件
		if (content != null) {
			contentServices.newObject(token, content);
		} else {
			// 从上一版复制电子文件
			contentServices.copyContent(token, docId, doc.getId());
		}
		// 更新上一版信息
		String sql = "update ecm_document set LOCK_OWNER='',LOCK_DATE=" + DBFactory.getDBConn().getDBUtils().getDBNullDate() + ", LOCK_CLIENT=''";
		if (isCurrent) {
			sql += ",is_current=0";
		}
		//sql +=" ,TITLE= '"+attrMap.get("TITLE")+"'";
		sql += " where ID='" + docId + "'";
		ecmDocument.executeSQL(sql);
		return doc;
	}

	

	//@Override
	@Transactional(rollbackFor = Exception.class)
	public String checkInUpgradeContent(String token, String docId, EcmContent content) throws Exception {
		EcmDocument doc = this.getObjectById(token, docId);
		if (doc == null) {
			throw new EcmException("Document is not exits:" + docId);
		}
		if (getPermit(token, docId) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no read permission:" + docId);
		}
		if (content != null) {
			if (StringUtils.isEmpty(content.getFormatName())) {
				throw new EcmException("Format cannot be empty.");
			}
			content.createId();
			content.setContentType(1);
			content.setParentId(doc.getId());
			

			if (StringUtils.isEmpty(content.getStoreName())) {
				String typeName=doc.getTypeName();
				if(typeName==null||"".equals(typeName)) {
					EcmDocument docx= getObjectById(token, doc.getId());
					typeName=docx.getTypeName();
				}
				content.setStoreName(CacheManagerOper.getEcmDefTypes().get(typeName).getStoreName());
			}
			doc.setFormatName(content.getFormatName());
			doc.setContentSize(content.getContentSize());
			List<EcmContent> contentList = contentServices.getContents(token,docId,1);
			if(contentList!=null&&contentList.size()>0) {
				EcmContent oldObj = contentList.get(0);
				String oldObjId =  oldObj.getId();
				content.setOldId(oldObjId);
			}
			contentServices.newObject(token, content);
		}
	
		return doc.getId();
	}
	
	@Override
	public List<EcmDocument> getAllVersions(String token, String id) throws EcmException, AccessDeniedException, NoPermissionException {
		if (getPermit(token, id) < ObjectPermission.BROWSER) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no browse permission:" + id);
		}
		String sql = "select a.* from ecm_document a, ecm_docuemnt b where a.VERSION_ID=b.VERSION_ID and b.ID='" + id
				+ "' order by a.SYSTEM_VERSION DESC";
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		List<EcmDocument> docs = new ArrayList<EcmDocument>();
		for (Map<String, Object> map : list) {
			EcmDocument doc = new EcmDocument();
			doc.setAttributes(map);
			docs.add(doc);
		}
		return docs;
	}

	@Override
	public List<Map<String, Object>> getAllVersionsMap(String token, String id) throws NoPermissionException, AccessDeniedException {
		if (getPermit(token, id) < ObjectPermission.BROWSER) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no browse permission:" + id);
		}
		String sql = "select a.* from ecm_document a, ecm_document b where a.VERSION_ID=b.VERSION_ID and b.ID='" + id
				+ "' order by a.SYSTEM_VERSION DESC";
		List<Map<String, Object>> list = ecmDocument.executeSQL(sql);
		return list;
	}

	@Override
	public void queue(String token, String id, String name, String eventName, String message)
			throws EcmException, AccessDeniedException {
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
	 * 
	 * @param token
	 * @param docID
	 * @throws EcmException
	 * @throws AccessDeniedException
	 */
	private void addFullIndexSearchQueue(String token, String docID) throws EcmException, AccessDeniedException {
		EcmParameter parmIndexServer = (CacheManagerOper.getEcmParameters().get("IndexServer"));
		if (parmIndexServer != null && !StringUtils.isEmpty(parmIndexServer.getValue())) {
			queue(token, docID, "ecm_full_index", "ecm_full_index", null);
		}

	}

	/**
	 * 附加生命周期
	 * 
	 * @param token
	 * @param id
	 * @param lifecycelName 生命周期名称
	 * @return
	 * @throws NoPermissionException
	 * @throws AccessDeniedException
	 */
	@Override
	public boolean attachLifeCycle(String token, String id, String lifecycelName)
			throws AccessDeniedException, NoPermissionException {
		// 必须有修改文件属性权限
		if (getPermit(token, id) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no update permission:" + id);
		}
		
		EcmDocument docu= ecmDocument.selectByPrimaryKey(id);
		EcmLifeCycle lifeCycle= lifeCycleMapper.selectByName(lifecycelName);
		String startStatus= lifeCycle.getStartStatus();
		String lifecycleName=lifeCycle.getName();
		docu.setLifecycleName(lifecycleName);
		docu.setLifecycleStatus(startStatus);
		docu.setLifecycleDir(1);
		ecmDocument.updateByPrimaryKeySelective(docu);
		// 写日志
		newAudit(token, null, AuditContext.LIFE_CYCLE, id, null, "attach");
		return false;
	}

	/**
	 * 取消生命周期
	 * 
	 * @param token
	 * @param id
	 * @return
	 * @throws AccessDeniedException
	 * @throws NoPermissionException
	 */
	@Override
	public boolean detachLifeCycle(String token, String id) throws NoPermissionException, AccessDeniedException {
		// 必须有修改文件属性权限
		if (getPermit(token, id) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no update permission:" + id);
		}
		EcmDocument docu= ecmDocument.selectByPrimaryKey(id);
		docu.setLifecycleName("");
		docu.setLifecycleStatus("");
		docu.setLifecycleDir(1);
		ecmDocument.updateByPrimaryKeySelective(docu);
		
		// 写日志
		newAudit(token, null, AuditContext.LIFE_CYCLE, id, null, "detach");
		return false;
	}

	/**
	 * 生命周期前进
	 * 
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean promote(String token, String id) throws Exception {
		// 必须有修改文件属性权限
		if (getPermit(token, id) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no update permission:" + id);
		}
		EcmDocument doc= ecmDocument.selectByPrimaryKey(id);
		String lifecycleName=doc.getLifecycleName();
		String lifecycleStatus=doc.getLifecycleStatus();
		if(executeEvent(lifecycleName,lifecycleStatus,"next")) {
			String sql="select a.c_nextname from ecm_lifecycleitem a,ecm_lifecycle b where a.lifecycle_id"
					+ "=b.id and a.c_name='"+lifecycleStatus+"' and b.c_name='"+lifecycleName+"'";
			List<Map<String,String>> result= lifeCycleItemMapper.selectEcmLifeCycleBySql(sql);
			String nextName= result.get(0).get("c_nextname");
			doc.setLifecycleName(nextName);
			doc.setLifecycleDir(2);
			ecmDocument.updateByPrimaryKeySelective(doc);
			return true;
		}
		
		
		
		// 写日志
		newAudit(token, null, AuditContext.LIFE_CYCLE, id, null, "promote");
		return false;
	}

	/**
	 * 生命周期后退
	 * 
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean demote(String token, String id) throws Exception {
		// 必须有修改文件属性权限
		if (getPermit(token, id) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no update permission:" + id);
		}
		
		EcmDocument doc= ecmDocument.selectByPrimaryKey(id);
		String lifecycleName=doc.getLifecycleName();
		String lifecycleStatus=doc.getLifecycleStatus();
		if(executeEvent(lifecycleName,lifecycleStatus,"previous")) {
			String sql="select a.c_previousname from ecm_lifecycleitem a,ecm_lifecycle b where a.lifecycle_id"
					+ "=b.id and a.c_name='"+lifecycleStatus+"' and b.c_name='"+lifecycleName+"'";
			List<Map<String,String>> result= lifeCycleItemMapper.selectEcmLifeCycleBySql(sql);
			String nextName= result.get(0).get("c_nextname");
			doc.setLifecycleName(nextName);
			doc.setLifecycleDir(0);
			ecmDocument.updateByPrimaryKeySelective(doc);
			return true;
		}
		
		
		
		// 写日志
		newAudit(token, null, AuditContext.LIFE_CYCLE, id, null, "demote");
		return false;
	}
	
	/**
	 * 执行事件
	 * @param lifecycleName
	 * @param itemName
	 * @return
	 * @throws Exception
	 */
	private boolean executeEvent(String lifecycleName, String itemName,String flag) throws Exception {
		if(lifecycleName==null||itemName==null
				||"".equals(lifecycleName)||"".equals(itemName)) {
			throw new Exception("lifecycleName or lifecycleStatus is null");
		}
		if("next".equals(flag)) {
			String eventName= itemMapper.getNextImpleClassByName(lifecycleName, itemName);
			if(eventName==null||"".equals(eventName)) {
				Class<?> claz=Class.forName(eventName);
				ILifeCycleEvent event=(ILifeCycleEvent) claz.newInstance();
				if(event.execute()) {
					return true;
				}
			}
		}else if("previous".equals(flag)) {
			String eventName= itemMapper.getPreviousImpleClassByName(lifecycleName, itemName);
			if(eventName==null||"".equals(eventName)) {
				Class<?> claz=Class.forName(eventName);
				ILifeCycleEvent event=(ILifeCycleEvent) claz.newInstance();
				if(event.execute()) {
					return true;
				}
			}
		}
		
		
		return false;
	}
	/**
	 * 挂载文件
	 * @param token
	 * @param doc
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean mountFile(String token, String docId, EcmContent content) throws Exception {
		
		if (getPermit(token, docId) < ObjectPermission.WRITE_CONTENT) {
			throw new NoPermissionException("User " + getSession(token).getCurrentUser().getUserName()
					+ " has no add mount files permission:" + docId);
		}
		EcmContent primary = contentServices.getPrimaryContent(token, docId);
		EcmDocument doc = getObjectById(token, docId);
		if(doc!=null&&content!=null) {
			if (primary != null) {
				primary.setFormatName(null);
				primary.setName(content.getName());
				
				primary.setInputStream(content.getInputStream());
				contentServices.updateObject(token, primary);
				doc.setFormatName(primary.getFormatName());
				doc.setContentSize(primary.getContentSize());
			} else {
				content.createId();
				content.setParentId(docId);
				if (StringUtils.isEmpty(content.getStoreName())) {
				    content.setStoreName(CacheManagerOper.getEcmDefTypes().get(doc.getTypeName()).getStoreName());
				   }
				contentServices.newObject(token, content);
				doc.setFormatName(content.getFormatName());
				doc.setContentSize(content.getContentSize());
			}
			this.updateObject(token, doc,null);
			newAudit(token, null, AuditContext.MOUNT_FILE, docId, null, content.getName());
			return true;
			
		}else {
			return false;
		}
		
		
	}
	/**
	 * 获取文档所有列
	 * @return
	 */
	private String getDocumentAllColumns() {
		String cols = "";
		for(Entry<String, EcmAttribute> attr :CacheManagerOper.getEcmAttributes().entrySet()) {
			if(cols.length()==0) {
				cols = attr.getValue().getName();
			}else {
				cols += ","+attr.getValue().getName();
			}
		}
		return cols;
	}
	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		if (getPermit(token, id) < ObjectPermission.DELETE) {
			throw new NoPermissionException(
					"User " + getSession(token).getCurrentUser().getUserName() + " has no delete permission:" + id);
		}
		EcmDocument doc = ecmDocument.selectByPrimaryKey(id);
		contentServices.deleteObject(token, id);
		boolean ret = ecmDocument.deleteByPrimaryKey(id) > 0;
		String msg  = doc.getCoding() != null ? doc.getCoding() : "";
		msg += " ; " + (doc.getRevision() != null ? doc.getRevision() : "");
		msg += " ; " + (doc.getTitle() != null ? doc.getTitle() : "");
		msg += " ; " + (doc.getName() != null ? doc.getName() : "");
		newAudit(token, null, AuditContext.DELETE, id, null, doc.getTypeName() + "," + msg);
		addFullIndexSearchQueue(token, id);
		return ret;
	}
	
	
	/**
	 * 验证数据唯一（主文件或子文件）
	 * @param token
	 * @param data
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws UniquenessException 
	 */
	public boolean validateOnlyOne(String token,Map<String,Object> data) throws  EcmException, AccessDeniedException, UniquenessException {
		
		Object pid=data.get("parentDocId");
		if(pid!=null&&!"".equals(pid.toString())) {
			return validateChildOnly(token,data);
		}
		return validate(token,data);
		
	}
	
	/**
	 * 验证子文件数据唯一
	 * @param token
	 * @param data
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws UniquenessException 
	 */
	public boolean validateChildOnly(String token,Map<String,Object> data) throws  EcmException, AccessDeniedException, UniquenessException {
		String typeName=data.get("TYPE_NAME").toString();
		String parentID=data.get("parentDocId").toString();
		String sql="select * from ( " + 
				"select a.*,b.PARENT_ID from ecm_document a,ecm_relation b "
				+ "where a.id=b.CHILD_ID and a.TYPE_NAME='"+typeName+"' and b.parent_id='"+parentID+"'" + 
				") t where  ";
		
//		if(!StringUtils.isEmpty(parentID)) {
//			
//		}
		String condition=getConditionByConfig(token,data);
		if(condition == null) {
			return true;
		}
		sql+=condition;
		List<Map<String, Object>> result= this.getMapList(token, sql);
		if(result==null||result.size()==0) {
			return true;
		}else {
			throw new UniquenessException("对象已存在");
		}
		
	}
	
	/**
	 * 验证数据唯一
	 * @param token
	 * @param condition
	 * @return
	 * @throws AccessDeniedException 
	 * @throws UniquenessException 
	 */
	public boolean validate(String token,Map<String,Object> data) throws AccessDeniedException, UniquenessException {
		String condition=getConditionByConfig(token,data);
		if(condition == null) {
			return true;
		}
		List<Map<String,Object>> result= this.getObjectMap(token, condition);
		if(result!=null&&result.size()>0) {
			//return false;
			throw new UniquenessException("对象已存在");
		}
		return true;
	}
	/**
	 * 获取判断唯一的规则
	 * @param token
	 * @param data
	 * @return
	 * @throws NoOnlyPolicyException
	 * @throws AccessDeniedException 
	 */
	private String getConditionByConfig(String token,Map<String,Object> data) throws AccessDeniedException {
		String typeName=data.get("TYPE_NAME").toString();
		String condition=" TYPE_NAME='唯一性规则' and SUB_TYPE='"+typeName+"'";
		List<Map<String, Object>> onlyPolicys= this.getObjectMap(token, condition);
		if(onlyPolicys==null||onlyPolicys.size()==0) {
			logger.error("此类型“"+typeName+"”没有唯一性规则！");
			return null;
		}
		String docCondition=" TYPE_NAME='"+typeName+"'";
		if(data.get("ID")!=null) {
			docCondition+=" and ID !='"+data.get("ID").toString()+"'";
		}
		String whereSql="";
		boolean allNull = true;
		if(onlyPolicys!=null&&onlyPolicys.size()>0) {
			Map<String,Object> onlyPolicy= (Map<String,Object>)onlyPolicys.get(0);
			Object policy= onlyPolicy.get("C_COMMENT");
			if(policy!=null) {
				List<EcmFormClassification> list;
				String rulesStr=policy.toString();
				String[] rules= rulesStr.split(";");
				
				for(int i=0;i<rules.length;i++) {
					String rule = rules[i];
					rule=rule.substring(rule.indexOf("{")+1,rule.indexOf("}"));
					
					try {
						
						EcmForm frm = CacheManagerOper.getEcmForms().get(typeName + "_EDIT");
						if (frm == null) {
							frm = CacheManagerOper.getEcmForms().get(typeName + "_1");
						}
						list = frm.getFormClassifications(getSession(token), "zh-cn");
						
					} catch (Exception ex) {
						EcmForm frm = CacheManagerOper.getEcmForms().get(typeName + "_NEW");
						if (frm == null) {
							frm = CacheManagerOper.getEcmForms().get(typeName + "_1");
						}
						list = frm.getFormClassifications(getSession(token), "zh-cn");

					}
					EcmFormItem itemRuls=null;
					try {
						for(EcmFormClassification ecmClassification:list) {
							List<EcmFormItem> formItems= ecmClassification.getEcmFormItems();
							for(EcmFormItem item:formItems) {
								if(rule.toUpperCase().equals(item.getAttrName().toUpperCase())) {
									itemRuls=item;
									throw new Exception();
								}
								
							}
						}
					}catch (Exception e) {
						// TODO: handle exception
					}
					if(itemRuls.getIsRepeat()) {
						if(data.get(rule)==null || StringUtils.isEmpty(data.get(rule).toString())) {
							continue;
						}
						allNull = false;
						String[] ruleDatas= data.get(rule).toString().split(";");
						String subCondition="";
						for(int x=0;x<ruleDatas.length;x++) {
							String ruleData=ruleDatas[x];
							if(StringUtils.isEmpty(ruleData)) {
								continue;
							}
							if(subCondition.length()>1) {
								subCondition+=" or "+ rule+" like '%"+ruleData+"%' ";
							}else {
								subCondition+=rule+" like '%"+ruleData+"%'";
							}
							
						}
						if(whereSql.length()>1) {
							if(!StringUtils.isEmpty(subCondition)) {
								whereSql+=" and ("+subCondition+") ";
							}
						}else {
							if(!StringUtils.isEmpty(subCondition)) {
								whereSql+="("+subCondition+") ";
							}
						}
						
					}else {
						if(data.get(rule)!=null && !StringUtils.isEmpty(data.get(rule).toString())) {
							if(whereSql.length()>1) {
								whereSql+= " and "+ rule+"='"+data.get(rule).toString()+"'";
							}else {
								whereSql+=rule+"='"+data.get(rule).toString()+"'";
							}
							allNull = false;
						}
					}
					
				}
			}
		}
		if(allNull) {
			return null;
		}
		if(!"".equals(whereSql)) {
			docCondition+=" and ("+whereSql+")";
		}
		return docCondition;
		
	}
	
	/**
	 * 是否包含角色
	 * @param glist
	 * @param roleName 单值，多值，正则表达式如：“*计划人员”
	 * @return
	 */
	public boolean inGroup(List<EcmGroup> glist,String roleName) {
		if(StringUtils.isEmpty(roleName)) {
			return true;
		}
		for(EcmGroup g: glist) {
			if(roleName.indexOf(";")>-1) {
				String[] roleNames = roleName.split(";");
				for(String role:roleNames) {
					if(g.getName().equalsIgnoreCase(role)) {
						return true;
					}
				}
			}else if(roleName.indexOf("*")>-1){//正则表达式
				roleName = "."+roleName+".*";
				 boolean isMatch = Pattern.matches(roleName, g.getName());
				 if(isMatch) {
					 return true;
				 }
				
			}else if(g.getName().equalsIgnoreCase(roleName)) {
				return true;
			}
		}
		return false;
	}
}
