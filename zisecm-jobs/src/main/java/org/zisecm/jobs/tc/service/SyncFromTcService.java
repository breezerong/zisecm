package org.zisecm.jobs.tc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.bean.conf.AttrBean;
import org.zisecm.jobs.bean.conf.ConfBean;
import org.zisecm.jobs.bean.conf.ConfigBean;
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.tools.SyncTcTools;
import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.strong.TC_Project;
@Service
public class SyncFromTcService {
	private static Logger logger=Logger.getLogger(SyncFromTcService.class);
	@Autowired
	private DocumentService documentService;
	@Autowired
	private AuthService authService;
	
	@Autowired
	private Environment env;
	public String getTcData() throws Exception {
		String userName = env.getProperty("ecm.username");
		IEcmSession ecmSession = authService.login("jobs", userName, env.getProperty("ecm.password"));
		Session session=SyncTcTools.getSession(env);
	    logger.info("-----------begin setCRData------------");

	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String start = df.format(new Date());
	    logger.info("-----------开始时间：" + start + "------------");
	    System.out.println("CR开始时间：" + start);
	    
	    
	    CreateItemsOutput createItemOutput = null;
	    DataManagementService dmService = 
	    DataManagementService.getService(session.getConnection());
	    try
	    {
	      DataManagement dataManagement = new DataManagement(session);
	      Query query = new Query(session);
	      ConfigBean cf= Operator.getConfig();
	      for(ConfBean conf:cf.getConfs()) {
	    	  if(conf.isIschildType()) {
	    		  continue;
	    	  }
	    	  ModelObject[] findChangeRqRevision = query.queryFilesRevision(conf.getTcTableName(),"1");
	    	  if (findChangeRqRevision == null) {
			    	 throw new Exception("数据在TC中已经不存在符合条件的数据：" );
			  }
	    	  List<ConfBean> tcConfBeans= Operator.getConfBeanByTcTableName(conf.getTcTableName(), "tc");
	    	  List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
	    	  
	    	  for(ModelObject obj:findChangeRqRevision) {
	    		  for(ConfBean tcConfBean:tcConfBeans) {
	    			  Map<String,Object> row=new HashMap<String, Object>();
	    			  List<AttrBean> attrBeans= tcConfBean.getAttributes();
	    			  row.put("TYPE_NAME", tcConfBean.getSourceTypeName());
	    			  for(AttrBean attrBean:attrBeans) {
	    				  String value= obj.getPropertyObject(attrBean.getTargetName()).getStringValue();
	    				  row.put(attrBean.getSourceName(), value);
	    			  }
	    			  dataList.add(row);
	    			//保存数据至设计分包系统
	    			  EcmDocument doc=new EcmDocument();
	    			  doc.setAttributes(row);
	    			  String docId= documentService.newObject(ecmSession.getToken(), doc, null);
	    			  //检查数据是否有字表
	    			  obj.getPropertyObject("cn9FileList").getStringArrayValue();
	    			  //创建字表数据
	    			  //下载电子文件保存至子表
	    		  }
	    		 
	    	  }
	    	  
		      
	    	  
	      }
    
	    
	     
	      
	      
	      
	    }catch(Exception ex) {
	    	ex.printStackTrace();
	    }
		return null;
		
	}
}
