package org.zisecm.jobs.bean.conf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.tc.tools.SyncTcTools;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.DocumentService;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.soa.client.model.ModelObject;

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
	public static RelationShip OperationContractorSubData(String token,DocumentService docService,
			Map<String,Object> data,RelationShip relationShip) throws Exception{
		relationShip.setData(null);
		ConfBean subTableConfig= getSubBeanById(relationShip.getRefformId());
		Map<String,Object> rowData=new HashMap<String, Object>();
		for(AttrBean attr:subTableConfig.getAttributes()) {
			String sName= attr.getSourceName();
			String tName= attr.getTargetName();
			String defaultValue=attr.getDefaultValue();
			if("projectId".equals(tName)) {
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
	public static ConfBean OperationContractorData(String token,DocumentService docService,
			Map<String,Object> data,ConfBean conf) throws Exception{
		conf.setData(null);
		Map<String,Object> rowData=new HashMap<String, Object>();
		for(AttrBean attr:conf.getAttributes()) {
			String sName= attr.getSourceName();
			String tName= attr.getTargetName();
			String defaultValue=attr.getDefaultValue();
			if("projectId".equals(tName)) {
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
			}else {
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
	 */
	public static Map<String,Object> OperationTcData(DataManagementService dmService,ModelObject obj,ConfBean cfb){
		List<Map<String,Object>> newData=new ArrayList<Map<String,Object>>();
		Map<String,Object> row=new HashMap<String, Object>();
		row.put("TYPE_NAME", cfb.getSourceTypeName());
		for(AttrBean attrBean:cfb.getAttributes()) {
			String tName=attrBean.getTargetName();
			String sName=attrBean.getSourceName();
			String val= SyncTcTools.getProperty(dmService, obj, tName);
			row.put(sName, val);
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
