package com.ecm.portal.common.dataexchange;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


public class ConfigurationUtils {
	
	private static HeadConfiguration headCfg;
	
	public static HeadConfiguration getHeadCfg(Object session) throws Exception
	{
		if(headCfg==null)
		{
			//loadCfg(session);
		}
		return headCfg;
	}
	
	public static HeadBean getHeadBean(String name)
	{
		if(headCfg!=null)
		{
			for(HeadBean bean:headCfg.getHeadBeans())
			{
				if(bean.getName().equals(name))
					return bean;
			}
		}
		return null;
	}
	
	public static synchronized void loadCfg(Object session)
	{
//		IDfSysObject obj;
//		try {
//			obj = (IDfSysObject)session.getObjectByPath("/Configuration/System/参数配置/公共/HeadConfiguration.xml");
//			String tempFile = obj.getFile("");
//			JAXBContext content=JAXBContext.newInstance(HeadConfiguration.class);
//			Unmarshaller unmar= content.createUnmarshaller();
//			headCfg= (HeadConfiguration) unmar.unmarshal(new File(tempFile));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
	//	}
	}

}
