package org.zisecm.jobs.bean.conf;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.tools.SyncTcTools;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.DocumentService;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.query.SavedQueryService;
import com.teamcenter.services.strong.query._2006_03.SavedQuery.GetSavedQueriesResponse;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.QueryResults;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.SavedQueriesResponse;
import com.teamcenter.services.strong.query._2008_06.SavedQuery;
import com.teamcenter.services.strong.query._2008_06.SavedQuery.QueryInput;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.ImanQuery;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.soa.exceptions.NotLoadedException;

public class Operator {
	private static ConfigBean cf;
	static {
		try {
			JAXBContext context = JAXBContext.newInstance(ConfigBean.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			String filePath=Operator.class.getResource("/").getPath()+"/Config.xml";
			cf = (ConfigBean) unmarshaller.unmarshal(new File(filePath));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ConfigBean getConfig() {
		return cf;
		
	}
	/**
	 * 获取主表配置
	 * @return
	 */
	public static List<ConfBean> getMainBeans(){
		return cf.getMainconfs();
	}
	/**
	 * 获取子表配置
	 * @return
	 */
	public static List<ConfBean> getSubBeans(){
		return cf.getSubconfs();
	}
	/**
	 * 通过Id获取主表配置
	 * @param id
	 * @return
	 */
	public static ConfBean getMainBeanById(String id) {
		for(ConfBean conf:cf.getMainconfs()) {
			if(id.equals(conf.getId())) {
				return conf;
			}
		}
		return null;
	}
	/**
	 * 通过Id获取子文件confbean
	 * @param id
	 * @return
	 */
	public static ConfBean getSubBeanById(String id) {
		for(ConfBean conf:cf.getSubconfs()) {
			if(id.equals(conf.getId())) {
				return conf;
			}
		}
		return null;
	}
	/**
	 * 向远端传数据
	 * @param sourceTypeName
	 * @param tagertSystemName
	 * @return
	 */
	public static ConfBean getConfBeanBySourceTypeName(String sourceTypeName,String tagertSystemName) {
		
		for(ConfBean conf:cf.getMainconfs()) {
			if(conf.getSourceTypeName()!=null
					&&sourceTypeName.equals(conf.getSourceTypeName())
					&&tagertSystemName.equals(conf.getTargetSystem())) {
				return conf;
			}
		}
		return null;
		
	}
	/**
	 * 根据设计分包系统类型找到对应配置
	 * @param sourceTypeName
	 * @return
	 */
	public static ConfBean getConfBeanBySourceTypeName(String sourceTypeName) {
			
			for(ConfBean conf:cf.getMainconfs()) {
				if(conf.getSourceTypeName()!=null
						&&sourceTypeName.equals(conf.getSourceTypeName())) {
					return conf;
				}
			}
			return null;
			
		}
	/**
	 * 拿数据
	 * @param targetTypeName
	 * @param systemName
	 * @return
	 */
	public static List<ConfBean> getConfBeanByTargetName(String targetTypeName,String systemName) {
		List<ConfBean> confBeans=new ArrayList<ConfBean>();
		for(ConfBean conf:cf.getMainconfs()) {
			if(conf.getTcTypeName()!=null
					&&targetTypeName.equals(conf.getTcTypeName())
					&&systemName.equals(conf.getTargetSystem())) {
				confBeans.add(conf);
			}
		}
		
		return confBeans;
			
	}
	/**
	 * 获取配置通过TCTableName
	 * @param tcTableName
	 * @param systemName
	 * @return
	 */
	public static List<ConfBean> getConfBeanByTcTableName(String tcTableName,String systemName) {
		List<ConfBean> confBeans=new ArrayList<ConfBean>();
		for(ConfBean conf:cf.getMainconfs()) {
			if(conf.getTcTypeName()!=null
					&&tcTableName.equals(conf.getTcTableName())
					&&systemName.equals(conf.getTargetSystem())) {
				confBeans.add(conf);
			}
		}
		
		return confBeans;
			
	}
	
	
	
	/**
	 * 构造往TC发送子文件数据
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static RelationShip OperationContractorSubData(String token,Session session,
			DataManagementService dmService,
			DocumentService docService,
			Map<String,Object> data,RelationShip relationShip) throws Exception{
		relationShip.setData(null);
		ConfBean subTableConfig= getSubBeanById(relationShip.getRefformId());
		Map<String,Object> rowData=new HashMap<String, Object>();
		for(AttrBean attr:subTableConfig.getAttributes()) {
			String sName= attr.getSourceName();
			String tName= attr.getTargetName();
			String defaultValue=attr.getDefaultValue();
			if(tName==null||"".equals(tName)) {
				continue;
			}else  if("projectId".equals(tName)) {
				List<EcmDocument> projects= docService.getObjects(token, "NAME='"+data.get(sName).toString()+"'");
				EcmDocument project=null;
				if(projects!=null&&projects.size()>0) {
					project= projects.get(0);
				}else {
					throw new Exception("系统中午对应的项目："+data.get(sName).toString());
				}
				DataEntity dt=new DataEntity();
				dt.setAttrName(tName);
				dt.setAttrValue(project.getCoding());
				dt.setDataType(attr.getDataType());
				rowData.put(tName, dt);
			}else if(attr.getSearchConf()!=null) {
				SearchConf searchConf=attr.getSearchConf();
				ItemRevision itemRev=queryItemRevision(token,session, searchConf, data,docService);
				String value=null;
				try {
					value=SyncTcTools.getProperty(dmService, itemRev, searchConf.getReturnProperty(),attr.getDataType());
				}catch (Exception e) {
					// TODO: handle exception
					value=null;
				}
				DataEntity dt=new DataEntity();
				dt.setAttrName(tName);
				dt.setAttrValue(value);
				dt.setDataType(attr.getDataType());
				rowData.put(tName, dt);
			}else {
				DataEntity dt=new DataEntity();
				dt.setAttrName(tName);
				dt.setAttrValue((sName.equals("")||data.get(sName)==null)?defaultValue:data.get(sName));
				dt.setDataType(attr.getDataType());
				rowData.put(tName, dt);
			}
			
		}
		relationShip.setData(rowData);
		return relationShip;
		
	}
	/**
	 * 查询单条tc数据
	 * @param session
	 * @param ship
	 * @return
	 * @throws Exception 
	 */
	public static ItemRevision queryItemRevision(String token,Session session, SearchConf searchConf,
			Map<String,Object> data,DocumentService documentService) throws Exception {
		List<Condition> conditions= searchConf.getConditions();
		if(conditions==null||conditions.size()==0) {
			throw new Exception("请确认是否有配置conditions");
		}
		
		ImanQuery query = null;
		SavedQuery queryService = SavedQueryService.getService(session.getConnection());

		try {
			GetSavedQueriesResponse savedQueries = ((com.teamcenter.services.strong.query._2006_03.SavedQuery) queryService)
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
//				if (savedQueries.queries[i].name.equals("Item Revision...")) {
				if (savedQueries.queries[i].name.equals(searchConf.getName())) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an 'Item Revision...' query.");
			return null;
		}

		try {
			List<String> entries=new ArrayList<String>();
			List<String> values=new ArrayList<String>();
			
			for(int i=0;i<conditions.size();i++) {
				Condition c= conditions.get(i);
				entries.add(c.getName());
				String val="";
				
				if("projectId".equals(c.getValue())) {
					List<EcmDocument> projects= documentService.getObjects(token, "NAME='"+data.get("C_PROJECT_NAME").toString()+"'");
					EcmDocument project=null;
					if(projects!=null&&projects.size()>0) {
						project= projects.get(0);
					}else {
						throw new Exception("系统中午对应的项目："+data.get("C_PROJECT_NAME").toString());
					}
					val=project.getCoding();
				}else {
					if(data.get(c.getValue())==null) {
						val=c.getValue();
					}else {
						val=data.get(c.getValue()).toString();
					}
				}
				
				values.add(val);
			}
			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
//			savedQueryInput[0].entries = new String[] { "零组件 ID", "版本" };
//			savedQueryInput[0].values = new String[] { itemId, revId };
			savedQueryInput[0].entries = entries.toArray(new String[0]);
			savedQueryInput[0].values = values.toArray(new String[0]);
			SavedQueriesResponse response = queryService.executeSavedQueries(savedQueryInput);
			QueryResults found = response.arrayOfResults[0];

			// add by Sean 20151012
			if (found.objectUIDS.length == 0) {
				return null;
				// add end
			} else {
				DataManagementService dmService = DataManagementService.getService(session.getConnection());
				ServiceData serviceData = dmService.loadObjects(found.objectUIDS);
				if (serviceData.sizeOfPartialErrors() == 0) {
					return (ItemRevision) serviceData.getPlainObject(0);
				}
			}
			// add end

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 替换SQL中的标记
	 * @param data 数据
	 * @param sql 查询
	 * @return
	 * @throws Exception
	 */
	public static String operationSql(Map<String,Object> data,String sql) throws Exception {
		while(sql.indexOf("#{")>-1) {
			String tempColumn="";
			tempColumn=sql.substring(sql.indexOf("#{")+2,sql.indexOf("}"));
			Object obj=data.get(tempColumn);
			if(obj==null) {
				throw new Exception("配置中#{"+tempColumn+"}无对应信息");
			}
					
			String tempValue=data.get(tempColumn).toString();
			sql=sql.replace("#{"+tempColumn+"}", tempValue);
		}
		return sql;
	}
	
	/**
	 * 构造往TC发送主文件数据
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static ConfBean OperationContractorData(String token,Session session,DocumentService docService,
			DataManagementService dmService,
			Map<String,Object> data,ConfBean conf) throws Exception{
		conf.setData(null);
		Map<String,Object> rowData=new HashMap<String, Object>();
		for(AttrBean attr:conf.getAttributes()) {
			String sName= attr.getSourceName();
			String tName= attr.getTargetName();
			String defaultValue=attr.getDefaultValue();
			if("".equals(tName)) {
				continue;
			}else if("projectId".equals(tName)) {
				List<EcmDocument> projects= docService.getObjects(token, "NAME='"+data.get(sName).toString()+"'");
				EcmDocument project=null;
				if(projects!=null&&projects.size()>0) {
					project= projects.get(0);
				}else {
					throw new Exception("系统中午对应的项目："+data.get(sName).toString());
				}
				DataEntity dt=new DataEntity();
				dt.setAttrName(tName);
				dt.setAttrValue(project.getCoding());
				dt.setDataType(attr.getDataType());
				rowData.put(tName, dt);
			}else if(attr.getSearchConf()!=null) {
				SearchConf searchConf=attr.getSearchConf();
				ItemRevision itemRev=queryItemRevision(token,session, searchConf, data,docService);
				String value=null;
				try {
					value=SyncTcTools.getProperty(dmService, itemRev, searchConf.getReturnProperty(),attr.getDataType());
				}catch (Exception e) {
					// TODO: handle exception
					value=null;
				}
				DataEntity dt=new DataEntity();
				dt.setAttrName(tName);
				dt.setAttrValue(value);
				dt.setDataType(attr.getDataType());
				rowData.put(tName, dt);
			}
			else {
				DataEntity dt=new DataEntity();
				dt.setAttrName(tName);
				dt.setAttrValue((sName.equals("")||data.get(sName)==null)?defaultValue:data.get(sName));
				dt.setDataType(attr.getDataType());
				rowData.put(tName, dt);
			}
			
		}
		conf.setData(rowData);
		return conf;
		
	}
	/**
	 * 来自TC数据
	 * @param data
	 * @return
	 * @throws NotLoadedException 
	 * @throws SqlDeniedException 
	 * @throws EcmException 
	 */
	public static Map<String,Object> OperationTcData(String token,DocumentService documentService,DataManagementService dmService,ModelObject obj,ConfBean cfb) throws NotLoadedException, EcmException, SqlDeniedException{
		List<Map<String,Object>> newData=new ArrayList<Map<String,Object>>();
		Map<String,Object> row=new HashMap<String, Object>();
		
		DataEntity dtType=new DataEntity();
		dtType.setAttrName("TYPE_NAME");
		dtType.setAttrValue(cfb.getSourceTypeName());
		dtType.setDataType("String");
		row.put("TYPE_NAME", dtType);
		for(AttrBean attrBean:cfb.getAttributes()) {
			DataEntity dt=new DataEntity();
			String tName=attrBean.getTargetName();
			String sName=attrBean.getSourceName();
			String val="";
			if(sName==null||"".equals(sName)) {
				continue;
				
			}
			if("C_PAGE_COUNT".equals(sName)) {
				try {
					val= SyncTcTools.getProperty(dmService, obj, tName,attrBean.getDataType());
					String[] temp=val.split("\\+");
					val=temp[0];
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					val=null;
				}
				dt.setAttrName(sName);
				dt.setAttrValue(val);
				dt.setDataType(attrBean.getDataType());
			}else if("projectId".equals(tName)) {
				val=SyncTcTools.getProjectId(dmService, obj);
				if(val!=null&&!"".equals(val)) {
					List<EcmDocument> projects=documentService.getObjects(token, " type_name='项目' and CODING='"+val+"'");
					if(projects!=null&&projects.size()>0) {
						val=projects.get(0).getName();
						
						dt.setAttrName(sName);
						dt.setAttrValue(val);
						dt.setDataType(attrBean.getDataType());
					}
				}
			}else if(tName==null||"".equals(tName)){
				val= attrBean.getDefaultValue();
				dt.setAttrName(sName);
				dt.setAttrValue(val);
				dt.setDataType(attrBean.getDataType());
			}else {
				try {
					val= SyncTcTools.getProperty(dmService, obj, tName,attrBean.getDataType());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					val=null;
				}
				dt.setAttrName(sName);
				dt.setAttrValue(val);
				dt.setDataType(attrBean.getDataType());
			}
			
			row.put(sName, dt);
		}
		
		
		return row;
		
	}
	
	/**
	 * 来自TC数据
	 * @param data
	 * @return
	 */
	public static List<Map<String,Object>> OperationTcData(Map<String,Object> data,String systemName){
		List<ConfBean> confs=getConfBeanByTargetName(data.get("TYPE_NAME").toString(),systemName);
		List<Map<String,Object>> newData=new ArrayList<Map<String,Object>>();
		for(ConfBean conf:confs) {
			Map<String,Object> row=new HashMap<String, Object>();
			row.put("TYPE_NAME", conf.getSourceTypeName());
			for(AttrBean attrBean:conf.getAttributes()) {
				String tName=attrBean.getTargetName();
				String sName=attrBean.getSourceName();
				row.put(sName, data.get(tName));
			}
			newData.add(row);
		}
		
		return newData;
		
	}
	
	public static void main(String[] args) throws JAXBException {
		// TODO Auto-generated method stub
		JAXBContext context = JAXBContext.newInstance(ConfigBean.class);
        
        Unmarshaller unmarshaller = context.createUnmarshaller();
        String filePath=Operator.class.getResource("/").getPath()+"/Config.xml";
        ConfigBean cf = (ConfigBean) unmarshaller.unmarshal(new File(filePath));
        System.out.println(cf.getMainconfs().size());
	}

}
