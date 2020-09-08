package org.zisecm.jobs.tc.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.tools.SyncTcTools;
import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;

import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.strong.TC_Project;
@Service
public class SyncFromTcService {
	private static Logger logger=Logger.getLogger(SyncFromTcService.class);
	@Autowired
	private Environment env;
	public String getTcData(Map<String,Object> data,Map<String,Object> tcData) throws Exception {
		Map<String,Object> tcdt=(Map<String, Object>) tcData.get("data");
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
	      Workflow workflow = new Workflow(session);
	     
	      DataEntity dt=(DataEntity)tcdt.get("projectId");
	      String projectId=dt.getAttrValue().toString();
	      TC_Project project = query.queryProjectExist(projectId);
	      

	      ModelObject findChangeRqRevision = query.queryFileRevision(projectId,data.get("CODING").toString());
	      
	      if (findChangeRqRevision != null) {
	    	 throw new Exception("文件传递单数据在TC中已经存在符合条件的文件传递单：" + data.get("C_PROJECT_NAME").toString() + "|" +  data.get("CODING").toString() + "|" );
	      }
	    }catch(Exception ex) {
	    	ex.printStackTrace();
	    }
		return null;
		
	}
}
