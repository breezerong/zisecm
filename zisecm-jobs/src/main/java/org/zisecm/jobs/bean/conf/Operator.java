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

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.DocumentService;

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
	 * 向远端传数据
	 * @param sourceTypeName
	 * @param tagertSystemName
	 * @return
	 */
	public static ConfBean getConfBeanBySourceTypeName(String sourceTypeName,String tagertSystemName) {
		
		for(ConfBean conf:cf.getConfs()) {
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
			
			for(ConfBean conf:cf.getConfs()) {
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
		for(ConfBean conf:cf.getConfs()) {
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
		for(ConfBean conf:cf.getConfs()) {
			if(conf.getTcTypeName()!=null
					&&tcTableName.equals(conf.getTcTableName())
					&&systemName.equals(conf.getTargetSystem())) {
				confBeans.add(conf);
			}
		}
		
		return confBeans;
			
	}
	/**
	 * 往TC发送数据
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static Map<String,Object> OperationContractorData(String token,DocumentService docService,
			Map<String,Object> data,String typeName,String targetSystem) throws Exception{
		
		if(typeName==null||"".equals(typeName)) {
			typeName=data.get("TYPE_NAME").toString();
		}
		ConfBean conf=getConfBeanBySourceTypeName(typeName,targetSystem);
		Map<String,Object> newData=new HashMap<String, Object>();
		Map<String,Object> rowData=new HashMap<String, Object>();
		newData.put("tcTable", conf.getTcTableName());
		newData.put("tcType", conf.getTcTypeName());
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
		newData.put("data", rowData);
		return newData;
		
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
        System.out.println(cf.getConfs().size());
	}

}
