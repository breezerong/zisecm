package org.zisecm.jobs.bean.conf;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Test {
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
	
	public void setA(StringBuffer x) {
		x.append("123");
	}
	public void setB(String b) {
		b=b+"sss";
	}
	public static void main(String[] args) {
//		ConfigBean c= getConfig();
//		c.getMainconfs();
		StringBuffer x=new StringBuffer("1");
		String b="1";
		Test t=new Test();
		t.setA(x);
		t.setB(b);
		System.out.println(b);
		System.out.println(x.toString());
		
	}
}
