package com.ecm.portal.common.dataexchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.task.api.history.HistoricTaskInstance;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmUser;

public class CSWordTest {
	public static void main(String[] args) {
		System.out.println("Started.");
		
		try {
//			String reviewerName = "testlgx";		
//			String reviewerTime = "2021-01-08";
//			String reviewerNameImg =  " "C:\\excdata\\sign\\黄璐璐.jpg";";
//		
//			String approverName="testlgx";		
//			String approverTime="2021-01-09";
//			String approverNameImg =  "C:\\excdata\\sign\\黄璐璐.jpg";
			
			List<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
			
				ArrayList<String> array = new ArrayList<String>();
				array.add("校对");
				array.add("testlgx");
				array.add("C:\\excdata\\sign\\黄璐璐.jpg");
				array.add("2021-01-09");
				resultList.add(array);
				
				ArrayList<String> array1 = new ArrayList<String>();
				array1.add("工种负责人审核");
				array1.add("testlgx");
				array1.add("C:\\excdata\\sign\\lgx.jpg");
				array1.add("2021-02-11");
				resultList.add(array1);
		
			
			EcmDocument document= new EcmDocument();
			document.setTypeName("图纸文件审批单");
			
			String TemplatePath ="D:\\work\\数据交换测试.docx";  
			WordUtils util = new WordUtils();
			
			util.setTemplatePath(TemplatePath);
		//	util.exchangeData(resultList,document,0); 

			System.out.println(TemplatePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
