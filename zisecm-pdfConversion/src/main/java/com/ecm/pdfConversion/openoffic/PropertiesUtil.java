package com.ecm.pdfConversion.openoffic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.sl.usermodel.ObjectMetaData.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class PropertiesUtil {
	final static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Map<String,Properties> propertiesMap=new ConcurrentHashMap<String,Properties>();
	/**
	 * 获取配置文件
	 */
	private static String getConfPathByOS(){
		 String path="/";
		 return path;
	}
	/**
	 * 根据配置文件名加载配置
	 * @param valueName
	 * @return
	 */
	public static String getPropertiesString(String fileName, String valueName) {
		String value=null;
		if(propertiesMap.containsKey(fileName)){
			Properties property=propertiesMap.get(fileName);
			value=property.getProperty(valueName);
		}else{
			String str=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			Properties property = new Properties();
			InputStream in=null;
			String path=str+fileName+".properties";
			log.info("开始加载系统数据:"+path);
			try{
				in =new FileInputStream(path);
				property.load(in);
				propertiesMap.put(fileName, property);
				value=property.getProperty(valueName);
			}catch (FileNotFoundException ex){ 
	            log.info("读取属性文件 "+path+"--->失败！- 原因：文件路径错误或者文件不存在"); 
	            ex.printStackTrace(); 
	        } catch (IOException ex){ 
	            log.info("装载文件"+path+"--->失败!"); 
	            ex.printStackTrace(); 
	        }finally{
	        	if(in!=null){
	        		try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	        }
		}
		
		return value;
	}
    /**
     * 根据默认配置文件加载配置
     * @param valueName 属性名
     * @return
     */
    public static String getVal(String valueName) {
        String fileName="application";
        return getPropertiesString( fileName,  valueName);
    }
    
}
