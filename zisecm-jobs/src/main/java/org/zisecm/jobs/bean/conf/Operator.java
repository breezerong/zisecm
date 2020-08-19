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
	 * 往TC发送数据
	 * @param data
	 * @return
	 */
	public static Map<String,Object> OperationContractorData(Map<String,Object> data,String targetSystem){
		ConfBean conf=getConfBeanBySourceTypeName(data.get("TYPE_NAME").toString(),targetSystem);
		Map<String,Object> newData=new HashMap<String, Object>();
		Map<String,Object> rowData=new HashMap<String, Object>();
		newData.put("tcTable", conf.getTcTableName());
		newData.put("tcType", conf.getTcTypeName());
		for(AttrBean attr:conf.getAttributes()) {
			String sName= attr.getSourceName();
			String tName= attr.getTargetName();
			String defaultValue=attr.getDefaultValue();
			rowData.put(tName, data.get(sName)==null?defaultValue:data.get(sName));
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
