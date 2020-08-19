package org.zisecm.jobs.tc.ws.PLMServerLOT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.axis.MessageContext;
import org.apache.log4j.Logger;

import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;
import org.zisecm.jobs.tc.clientx.Session;
import com.teamcenter.rac.aif.common.AIFTreeCellRenderer;
import com.teamcenter.rac.aif.kernel.AIFComponentContext;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.Type;
import com.teamcenter.soa.client.model.strong.Folder;
import com.teamcenter.soa.client.model.strong.TC_Project;
import com.teamcenter.soa.client.model.strong.User;
import com.teamcenter.soa.client.model.strong.WorkspaceObject;

public class PLMServerLOTImpl implements PLMServerLOTType {

	
	protected static Logger logger = Logger.getLogger(PLMServerLOTImpl.class);
	  public static Session session;
	  
	  
	  public boolean loginTC()
	  {
//	    LoginInfo loginInfo = new LoginInfo("http://10.30.2.132:9080/tc", "dcproxy", "dcproxy", "SoaAppX");

		  LoginInfo loginInfo = new LoginInfo("http://192.168.2.130:7001/tc", "infodba", "infodba", "SoaAppX");

	    session = new Session(loginInfo.getServerHost());
	    System.out.println(loginInfo.getServerHost() + "---------");
	    try
	    {
	      session.login(loginInfo.getUserID(), loginInfo.getPassword(), 
	        loginInfo.getDiscriminator());
	    } catch (Exception e) {
	      e.printStackTrace();

	      return false;
	    }

	    return true;
	  }
	
	public ReturnVal setFileData(FileDataMessageBody fileDataRequest) {
		 ReturnVal returnVal = new ReturnVal();
		    if (session == null)
		    {
		      boolean flag = loginTC();
		      if (!flag) {
		        returnVal.setReturnCode("INT_2");
		        returnVal.setReturnMessage("登陆失败");
		        return returnVal;
		      }

		    }

		    logger.info("-----------begin setCRData------------");

		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String start = df.format(new Date());
		    logger.info("-----------开始时间：" + start + "------------");
		    System.out.println("CR开始时间：" + start);

		    CreateItemsOutput createItemOutput = null;
		    DataManagementService dmService = 
		      DataManagementService.getService(Session.getConnection());
		    try
		    {
		      DataManagement dataManagement = new DataManagement(session);
		      Query query = new Query(session);
		      Workflow workflow = new Workflow(session);

		      if ((fileDataRequest.getProjectId() == null) || (fileDataRequest.getProjectId().equals(""))) {
		        returnVal.setReturnCode("INT_2");
		        returnVal.setReturnMessage("工程代号需要填写！");
		        return returnVal;
		      }
		      
		      if ((fileDataRequest.getLetterSendNo() == null) || (fileDataRequest.getLetterSendNo().equals(""))) {
		        returnVal.setReturnCode("INT_2");
		        returnVal.setReturnMessage("编号需要填写！");
		        return returnVal;
		      }
		      if ((fileDataRequest.getRevision() == null) || (fileDataRequest.getRevision().equals(""))) {
		        returnVal.setReturnCode("INT_2");
		        returnVal.setReturnMessage("版本需要填写！");
		        return returnVal;
		      }

		      TC_Project project = query.queryProjectExist(fileDataRequest.getProjectId());
		      if (project == null) {
		        returnVal.setReturnCode("INT_1");
		        returnVal.setReturnMessage("项目号" + fileDataRequest.getProjectId() + "不存在！");
		        return returnVal;
		      }

		      ModelObject findChangeRqRevision = query.queryFileRevision(fileDataRequest.getProjectId(), fileDataRequest.getLetterSendNo());
		      
		      
		      if ((fileDataRequest.getSendLotDate() != null) && (!fileDataRequest.getSendLotNo().equals(""))) {
		          if ((fileDataRequest.getSendLotDate() == null) || (fileDataRequest.getSendLotDate().equals(""))) {
		            returnVal.setReturnCode("INT_2");
		            returnVal.setReturnMessage("发送供应商日期需要填写！");
		            return returnVal;
		          }

		          if (findChangeRqRevision == null) {
		            returnVal.setReturnCode("INT_2");
		            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的CR单：" + fileDataRequest.getProjectId() + "|" + fileDataRequest.getRevision());
		            return returnVal;
		          }

		          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
		          String closeDateStr = fileDataRequest.getSendLotDate();
		          if (!closeDateStr.equals("")) {
		            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
		            Date closeDate = dateFormat.parse(closeDateStr);

		            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
		          }
		          String[] values = { fileDataRequest.getSendLotNo(), closeDateStr };
		          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
		          if (!setValueReturn.equals("")) {
		            returnVal.setReturnCode("INT_2");
		            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
		            return returnVal;
		          }

		          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

		          returnVal.setReturnCode("INT_0");
		          returnVal.setReturnMessage("");
		          return returnVal;
		        }
		      
		      
		      
		      
		      /*
		      if ((setCRDataRequest.getCloseStatus() != null) && (!setCRDataRequest.getCloseStatus().equals(""))) {
		        if ((setCRDataRequest.getCloseDate() == null) || (setCRDataRequest.getCloseDate().equals(""))) {
		          returnVal.setReturnCode("INT_2");
		          returnVal.setReturnMessage("关闭日期需要填写！");
		          return returnVal;
		        }
			
		        if (findChangeRqRevision == null) {
		          returnVal.setReturnCode("INT_2");
		          returnVal.setReturnMessage("更新关闭状态时在TC中没有找到符合条件的CR单：" + fileDataRequest.getProjectId() + "|" +  fileDataRequest.getLetterSendNo() + "|" );
		          return returnVal;
		        }
		        */
		        /*
		        String[] names = { "cn9CloseStatus", "cn9CloseShigongDate" };
		        String closeDateStr = setCRDataRequest.getCloseDate();
		        if (!closeDateStr.equals("")) {
		          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		          SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		          SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
		          Date closeDate = dateFormat.parse(closeDateStr);

		          closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
		        }
		        String[] values = { setCRDataRequest.getCloseStatus(), closeDateStr };
		        String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
		        if (!setValueReturn.equals("")) {
		          returnVal.setReturnCode("INT_2");
		          returnVal.setReturnMessage("更新CR单：" + setValueReturn);
		          return returnVal;
		        }

		        dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

		        returnVal.setReturnCode("INT_0");
		        returnVal.setReturnMessage("");
		        return returnVal;
		        
		      }
*/
		      if (findChangeRqRevision != null) {
		        returnVal.setReturnCode("INT_2");
		        returnVal.setReturnMessage("文件传递单数据在TC中已经存在符合条件的文件传递单：" + fileDataRequest.getProjectId() + "|" +  fileDataRequest.getLetterSendNo() + "|" );
		        return returnVal;
		      }

		      createItemOutput = dataManagement.createFileRevision(fileDataRequest);
		      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

		      dataManagement.removeAndAssignProject(createItemOutput.item, project);
		      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

		      if (fileDataRequest.getAttachments() != null) {
		        dataManagement.addChangeRqRevisionAttachements(createItemOutput.itemRev, fileDataRequest.getAttachments());
		      }

		      String docManagerUserId = dataManagement.getUserFromPref(fileDataRequest.getProjectId());
		      User user = null;
		      if (!docManagerUserId.equals("")) {
		        user = query.queryUser(docManagerUserId);
		        if (user != null) {
		          dataManagement.changeOwner(createItemOutput.item, user);
		          dataManagement.changeOwner(createItemOutput.itemRev, user);
		        }
		      }

		     // boolean isReply = setCRDataRequest.getIsReply().equals("Y");
		      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
		      String itemRevType = createItemOutput.itemRev.get_object_type();

		      String processTemplateName = "LDM-001-收文流程";

		      if (!processTemplateName.equals(""))
		      {
		        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
		      }

		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		      logger.error(e);

		      if (createItemOutput != null) {
		        try
		        {
		          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

		          String ret = "";
		          String[] msgs = serviceData.getPartialError(0).getMessages();

		          for (int i = 0; i < msgs.length; i++) {
		            ret = ret + msgs[i] + " ";
		          }

		          System.out.println(ret);
		          logger.info(ret);
		        }
		        catch (Exception e2) {
		          e2.printStackTrace();
		          logger.error(e2);
		        }
		      }

		      returnVal.setReturnCode("INT_2");
		      returnVal.setReturnMessage("异常:" + e.getMessage());
		      return returnVal;
		    }

		    returnVal.setReturnCode("INT_0");
		    returnVal.setReturnMessage("");

		    String end = df.format(new Date());
		    System.out.println("文件传递单结束时间：" + end);
		    logger.info("-----------结束时间：" + end + "------------");
		    logger.info("-----------end setCRData------------");
		    return returnVal;
	}
	
	public ReturnVal setCRData(CRDataMessageBody crDataRequest) {
		ReturnVal returnVal = new ReturnVal();
	    if (session == null)
	    {
	      boolean flag = loginTC();
	      if (!flag) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("登陆失败");
	        return returnVal;
	      }

	    }

	    logger.info("-----------begin setCRData------------");

	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String start = df.format(new Date());
	    logger.info("-----------开始时间：" + start + "------------");
	    System.out.println("CR开始时间：" + start);

	    CreateItemsOutput createItemOutput = null;
	    DataManagementService dmService = 
	      DataManagementService.getService(Session.getConnection());
	    try
	    {
	      DataManagement dataManagement = new DataManagement(session);
	      Query query = new Query(session);
	      Workflow workflow = new Workflow(session);

	      if ((crDataRequest.getProjectid() == null) || (crDataRequest.getProjectid().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("工程代号需要填写！");
	        return returnVal;
	      }
	      if ((crDataRequest.getFileID() == null) || (crDataRequest.getFileID().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("编号需要填写！");
	        return returnVal;
	      }
	      if ((crDataRequest.getRevision() == null) || (crDataRequest.getRevision().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("版本需要填写！");
	        return returnVal;
	      }

	      TC_Project project = query.queryProjectExist(crDataRequest.getProjectid());
	      if (project == null) {
	        returnVal.setReturnCode("INT_1");
	        returnVal.setReturnMessage("项目号" + crDataRequest.getProjectid() + "不存在！");
	        return returnVal;
	      }
	      String fileid="";
			if(!"".equals(crDataRequest.getFileID()) || crDataRequest.getFileID()!=null ){
				fileid=crDataRequest.getFileID().substring(2);
			}
	      ModelObject findChangeRqRevision = query.queryChangeRqRevision(crDataRequest.getProjectid(), crDataRequest.getCorrespLetterRecNo(), crDataRequest.getRevision());
	      if ((crDataRequest.getCloseStatus() != null) && (!crDataRequest.getCloseStatus().equals(""))) {
	          if ((crDataRequest.getCloseDate() == null) || (crDataRequest.getCloseDate().equals(""))) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("关闭日期需要填写！");
	            return returnVal;
	          }

	          if (findChangeRqRevision == null) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新关闭状态时在TC中没有找到符合条件的CR单：" + crDataRequest.getProjectid() + "|" + crDataRequest.getFileID() + "|" + crDataRequest.getRevision());
	            return returnVal;
	          }

	          String[] names = { "cn9CloseStatus", "cn9CloseShigongDate" };
	          String closeDateStr = crDataRequest.getCloseDate();
	          if (!closeDateStr.equals("")) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = dateFormat.parse(closeDateStr);

	            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
	          }
	          String[] values = { crDataRequest.getCloseStatus(), closeDateStr };
	          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	        }
	      
	      
	      if ((crDataRequest.getSendLotData() != null) && (!crDataRequest.getSendLotNo().equals(""))) {
	          if ((crDataRequest.getSendLotData() == null) || (crDataRequest.getSendLotData().equals(""))) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("发送供应商日期需要填写！");
	            return returnVal;
	          }

	          if (findChangeRqRevision == null) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的CR单：" + crDataRequest.getProjectid() + "|" + crDataRequest.getFileID() + "|" + crDataRequest.getRevision());
	            return returnVal;
	          }

	          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
	          String closeDateStr = crDataRequest.getSendLotData();
	          if (!closeDateStr.equals("")) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = dateFormat.parse(closeDateStr);

	            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
	          }
	          String[] values = { crDataRequest.getSendLotNo(), closeDateStr };
	          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	        }

	      if (findChangeRqRevision != null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("CR数据在TC中已经存在符合条件的CR单：" + crDataRequest.getProjectid() + "|" + crDataRequest.getFileID() + "|" + crDataRequest.getRevision());
	        return returnVal;
	      }

	      createItemOutput = dataManagement.createChangeRqRevision(crDataRequest);
	      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

	      dataManagement.removeAndAssignProject(createItemOutput.item, project);
	      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

	      if (crDataRequest.getAttachments() != null) {
	        dataManagement.addChangeRqRevisionAttachements(createItemOutput.itemRev, crDataRequest.getAttachments());
	      }

	      String docManagerUserId = dataManagement.getUserFromPref(crDataRequest.getProjectid());
	      User user = null;
	      if (!docManagerUserId.equals("")) {
	        user = query.queryUser(docManagerUserId);
	        if (user != null) {
	          dataManagement.changeOwner(createItemOutput.item, user);
	          dataManagement.changeOwner(createItemOutput.itemRev, user);
	        }

	      }

	      boolean isReply = crDataRequest.getIsReply().equals("Y");
	      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
	      String itemRevType = createItemOutput.itemRev.get_object_type();

	      String processTemplateName = dataManagement.getProcessTemplateNameFromPref(itemRevType, isReply);

	      if (!processTemplateName.equals(""))
	      {
	        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
	      }

	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error(e);

	      if (createItemOutput != null) {
	        try
	        {
	          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

	          String ret = "";
	          String[] msgs = serviceData.getPartialError(0).getMessages();

	          for (int i = 0; i < msgs.length; i++) {
	            ret = ret + msgs[i] + " ";
	          }

	          System.out.println(ret);
	          logger.info(ret);
	        }
	        catch (Exception e2) {
	          e2.printStackTrace();
	          logger.error(e2);
	        }
	      }

	      returnVal.setReturnCode("INT_2");
	      returnVal.setReturnMessage("异常:" + e.getMessage());
	      return returnVal;
	    }

	    returnVal.setReturnCode("INT_0");
	    returnVal.setReturnMessage("");

	    String end = df.format(new Date());
	    System.out.println("CR结束时间：" + end);
	    logger.info("-----------结束时间：" + end + "------------");
	    logger.info("-----------end setCRData------------");
	    return returnVal;
	}

	public void getICMACP1000Data(List<ICMACPMessageBody> iCMACPMessageBody) {
		// TODO Auto-generated method stub
		if (session == null)
	    {
	      boolean flag = loginTC();
	    }
		  CreateItemsOutput createItemOutput = null;
		    DataManagementService dmService = 
		     DataManagementService.getService(Session.getConnection());
		    DataManagement dataManagement = new DataManagement(session);
		    Query query = new Query(session);
		
	}

	public ReturnVal setFaxData(FaxDataMessageBody faxDataRequest) {
		ReturnVal returnVal = new ReturnVal();
	    if (session == null)
	    {
	      boolean flag = loginTC();
	      if (!flag) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("登陆失败");
	        return returnVal;
	      }

	    }

	    logger.info("-----------begin setFaxData------------");

	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String start = df.format(new Date());
	    logger.info("-----------开始时间：" + start + "------------");
	    System.out.println("Fax开始时间：" + start);

	    CreateItemsOutput createItemOutput = null;
	    DataManagementService dmService = 
	      DataManagementService.getService(Session.getConnection());
	    try
	    {
	      DataManagement dataManagement = new DataManagement(session);
	      Query query = new Query(session);
	      Workflow workflow = new Workflow(session);

	      if ((faxDataRequest.getProjectId() == null) || (faxDataRequest.getProjectId().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("工程代号需要填写！");
	        return returnVal;
	      }
	      if ((faxDataRequest.getLetterSendNo() == null) || (faxDataRequest.getLetterSendNo().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("编号需要填写！");
	        return returnVal;
	      }
	      if ((faxDataRequest.getRevision() == null) || (faxDataRequest.getRevision().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("版本需要填写！");
	        return returnVal;
	      }
			
	      TC_Project project = query.queryProjectExist(faxDataRequest.getProjectId());
	      if (project == null) {
	        returnVal.setReturnCode("INT_1");
	        returnVal.setReturnMessage("项目号" + faxDataRequest.getProjectId() + "不存在！");
	        return returnVal;
	      }

	      ModelObject faxRqRevision = query.queryFaxRevision(faxDataRequest.getProjectId(), faxDataRequest.getLetterSendNo());

	      if ((faxDataRequest.getSendLotDate() != null) && (!faxDataRequest.getSendLotNo().equals(""))) {
	          if ((faxDataRequest.getSendLotDate() == null) || (faxDataRequest.getSendLotDate().equals(""))) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("发送供应商日期需要填写！");
	            return returnVal;
	          }

	          if (faxRqRevision == null) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的CR单：" + faxDataRequest.getProjectId() + "|" + faxDataRequest.getRevision());
	            return returnVal;
	          }

	          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
	          String closeDateStr = faxDataRequest.getSendLotDate();
	          if (!closeDateStr.equals("")) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = dateFormat.parse(closeDateStr);

	            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
	          }
	          String[] values = { faxDataRequest.getSendLotNo(), closeDateStr };
	          String setValueReturn = dataManagement.setObjectProperties(faxRqRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { faxRqRevision });

	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	        }

	       

	      if (faxRqRevision != null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("FAX数据在TC中已经存在符合条件的FAX单：" + faxDataRequest.getProjectId() + "|" + faxDataRequest.getLetterSendNo());
	        return returnVal;
	      }

	      createItemOutput = dataManagement.createFaxRevision(faxDataRequest);
	      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

	      dataManagement.removeAndAssignProject(createItemOutput.item, project);
	      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

	      if (faxDataRequest.getFaxAttachment() != null) {
	        dataManagement.addFaxRevisionAttachements(createItemOutput.itemRev, faxDataRequest.getFaxAttachment());
	      }

	      String docManagerUserId = dataManagement.getUserFromPref(faxDataRequest.getProjectId());
	      User user = null;
	      if (!docManagerUserId.equals("")) {
	        user = query.queryUser(docManagerUserId);
	        if (user != null) {
	          dataManagement.changeOwner(createItemOutput.item, user);
	          dataManagement.changeOwner(createItemOutput.itemRev, user);
	        }

	      }

	      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
	      String itemRevType = createItemOutput.itemRev.get_object_type();

	      String processTemplateName = "LDM-001-收文流程(FCR\\CR\\TA\\NCR)";

	      if (!processTemplateName.equals(""))
	      {
	        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
	      }

	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error(e);

	      if (createItemOutput != null) {
	        try
	        {
	          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

	          String ret = "";
	          String[] msgs = serviceData.getPartialError(0).getMessages();

	          for (int i = 0; i < msgs.length; i++) {
	            ret = ret + msgs[i] + " ";
	          }

	          System.out.println(ret);
	          logger.info(ret);
	        }
	        catch (Exception e2) {
	          e2.printStackTrace();
	          logger.error(e2);
	        }
	      }

	      returnVal.setReturnCode("INT_2");
	      returnVal.setReturnMessage("异常:" + e.getMessage());
	      return returnVal;
	    }

	    returnVal.setReturnCode("INT_0");
	    returnVal.setReturnMessage("");

	    String end = df.format(new Date());
	    System.out.println("文件传递单结束时间：" + end);
	    logger.info("-----------结束时间：" + end + "------------");
	    logger.info("-----------end setCRData------------");
	    return returnVal;
	}

	public ReturnVal setTAData(TADataMessageBody taDataRequest) {
		ReturnVal returnVal = new ReturnVal();
	    if (session == null)
	    {
	      boolean flag = loginTC();
	      if (!flag) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("登陆失败");
	        return returnVal;
	      }

	    }

	    logger.info("-----------begin setCRData------------");

	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String start = df.format(new Date());
	    logger.info("-----------开始时间：" + start + "------------");
	    System.out.println("CR开始时间：" + start);

	    CreateItemsOutput createItemOutput = null;
	    DataManagementService dmService = 
	      DataManagementService.getService(Session.getConnection());
	    try
	    {
	      DataManagement dataManagement = new DataManagement(session);
	      Query query = new Query(session);
	      Workflow workflow = new Workflow(session);

	      if ((taDataRequest.getProjectid() == null) || (taDataRequest.getProjectid().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("工程代号需要填写！");
	        return returnVal;
	      }
	      if ((taDataRequest.getFileID() == null) || (taDataRequest.getFileID().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("编号需要填写！");
	        return returnVal;
	      }
	      if ((taDataRequest.getRevision() == null) || (taDataRequest.getRevision().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("版本需要填写！");
	        return returnVal;
	      }

	      TC_Project project = query.queryProjectExist(taDataRequest.getProjectid());
	      if (project == null) {
	        returnVal.setReturnCode("INT_1");
	        returnVal.setReturnMessage("项目号" + taDataRequest.getProjectid() + "不存在！");
	        return returnVal;
	      }

	      String fileid="";
			if(!"".equals(taDataRequest.getFileID()) || taDataRequest.getFileID()!=null ){
				fileid=taDataRequest.getFileID().substring(2);
			}
	      ModelObject findChangeRqRevision = query.queryTARevision(taDataRequest.getProjectid(), taDataRequest.getCorrespLetterRecNo(), taDataRequest.getRevision());
	      
	      if ((taDataRequest.getCloseStatus() != null) && (!taDataRequest.getCloseStatus().equals(""))) {
	          if ((taDataRequest.getCloseDate() == null) || (taDataRequest.getCloseDate().equals(""))) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("关闭日期需要填写！");
	            return returnVal;
	          }

	          if (findChangeRqRevision == null) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新关闭状态时在TC中没有找到符合条件的CR单：" + taDataRequest.getProjectid() + "|" + fileid + "|" + taDataRequest.getRevision());
	            return returnVal;
	          }

	          String[] names = { "cn9CloseStatus", "cn9CloseShigongDate" };
	          String closeDateStr = taDataRequest.getCloseDate();
	          if (!closeDateStr.equals("")) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = dateFormat.parse(closeDateStr);

	            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
	          }
	          String[] values = { taDataRequest.getCloseStatus(), closeDateStr };
	          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	        }
	      
	      if ((taDataRequest.getSendLotDate() != null) && (!taDataRequest.getSendLotNo().equals(""))) {
	          if ((taDataRequest.getSendLotDate() == null) || (taDataRequest.getSendLotDate().equals(""))) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("发送供应商日期需要填写！");
	            return returnVal;
	          }

	          if (findChangeRqRevision == null) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的CR单：" + taDataRequest.getProjectid() + "|" + fileid + "|" + taDataRequest.getRevision());
	            return returnVal;
	          }

	          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
	          String closeDateStr = taDataRequest.getSendLotDate();
	          if (!closeDateStr.equals("")) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = dateFormat.parse(closeDateStr);

	            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
	          }
	          String[] values = { taDataRequest.getSendLotNo(), closeDateStr };
	          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	        }

	      if (findChangeRqRevision != null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("CR数据在TC中已经存在符合条件的CR单：" + taDataRequest.getProjectid() + "|" + fileid + "|" + taDataRequest.getRevision());
	        return returnVal;
	      }

	      createItemOutput = dataManagement.createTARevision(taDataRequest);
	      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

	      dataManagement.removeAndAssignProject(createItemOutput.item, project);
	      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

	      if (taDataRequest.getAttachments() != null) {
	        dataManagement.addTARevisionAttachements(createItemOutput.itemRev, taDataRequest.getAttachments());
	      }

	      String docManagerUserId = dataManagement.getUserFromPref(taDataRequest.getProjectid());
	      User user = null;
	      if (!docManagerUserId.equals("")) {
	        user = query.queryUser(docManagerUserId);
	        if (user != null) {
	          dataManagement.changeOwner(createItemOutput.item, user);
	          dataManagement.changeOwner(createItemOutput.itemRev, user);
	        }

	      }

	      //boolean isReply = taDataRequest.getIsReply().equals("Y");
	      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
	      String itemRevType = createItemOutput.itemRev.get_object_type();

	      String processTemplateName = "LDM-001-收文流程(FCR\\CR\\TA\\NCR)";

	      if (!processTemplateName.equals(""))
	      {
	        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
	      }

	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error(e);

	      if (createItemOutput != null) {
	        try
	        {
	          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

	          String ret = "";
	          String[] msgs = serviceData.getPartialError(0).getMessages();

	          for (int i = 0; i < msgs.length; i++) {
	            ret = ret + msgs[i] + " ";
	          }

	          System.out.println(ret);
	          logger.info(ret);
	        }
	        catch (Exception e2) {
	          e2.printStackTrace();
	          logger.error(e2);
	        }
	      }

	      returnVal.setReturnCode("INT_2");
	      returnVal.setReturnMessage("异常:" + e.getMessage());
	      return returnVal;
	    }

	    returnVal.setReturnCode("INT_0");
	    returnVal.setReturnMessage("");

	    String end = df.format(new Date());
	    System.out.println("TA结束时间：" + end);
	    logger.info("-----------结束时间：" + end + "------------");
	    logger.info("-----------end setTAData------------");
	    return returnVal;
	}

	public ReturnVal setDesignReviewF(
			DesignReviewFDataMessageBody designReviewFDataRequest) {
		
		ReturnVal returnVal = new ReturnVal();
	    if (session == null)
	    {
	      boolean flag = loginTC();
	      if (!flag) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("登陆失败");
	        return returnVal;
	      }

	    }

	    logger.info("-----------begin setCRData------------");

	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String start = df.format(new Date());
	    logger.info("-----------开始时间：" + start + "------------");
	    System.out.println("CR开始时间：" + start);

	    CreateItemsOutput createItemOutput = null;
	    DataManagementService dmService = 
	      DataManagementService.getService(Session.getConnection());
	    try
	    {
	      DataManagement dataManagement = new DataManagement(session);
	      Query query = new Query(session);
	      Workflow workflow = new Workflow(session);

	      if ((designReviewFDataRequest.getProjectId() == null) || (designReviewFDataRequest.getProjectId().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("工程代号需要填写！");
	        return returnVal;
	      }
	      if ((designReviewFDataRequest.getSendRecvLettDate() == null) || (designReviewFDataRequest.getSendRecvLettDate().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("编号需要填写！");
	        return returnVal;
	      }
	      if ((designReviewFDataRequest.getRevision() == null) || (designReviewFDataRequest.getRevision().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("版本需要填写！");
	        return returnVal;
	      }

	      TC_Project project = query.queryProjectExist(designReviewFDataRequest.getProjectId());
	      if (project == null) {
	        returnVal.setReturnCode("INT_1");
	        returnVal.setReturnMessage("项目号" + designReviewFDataRequest.getProjectId() + "不存在！");
	        return returnVal;
	      }

	      ModelObject findChangeRqRevision = query.queryDesignRFRevision(designReviewFDataRequest.getProjectId(), designReviewFDataRequest.getLetterSendNo());
	      
	      
	      if ((designReviewFDataRequest.getSendLotDate() != null) && (!designReviewFDataRequest.getSendLotNo().equals(""))) {
	          if ((designReviewFDataRequest.getSendLotDate() == null) || (designReviewFDataRequest.getSendLotDate().equals(""))) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("发送供应商日期需要填写！");
	            return returnVal;
	          }

	          if (findChangeRqRevision == null) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的设计审查意见答复单：" + designReviewFDataRequest.getProjectId() + "|" + designReviewFDataRequest.getLetterSendNo() + "|" + designReviewFDataRequest.getRevision());
	            return returnVal;
	          }

	          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
	          String closeDateStr = designReviewFDataRequest.getSendLotDate();
	          if (!closeDateStr.equals("")) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = dateFormat.parse(closeDateStr);

	            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
	          }
	          String[] values = { designReviewFDataRequest.getSendLotNo(), closeDateStr };
	          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新设计审查意见答复单：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	        }

	      if (findChangeRqRevision != null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("设计审查意见答复单数据在TC中已经存在符合条件的审查意见答复单：" + designReviewFDataRequest.getProjectId() + "|" + designReviewFDataRequest.getLetterSendNo() + "|" + designReviewFDataRequest.getRevision());
	        return returnVal;
	      }

	      createItemOutput = dataManagement.createDesignReviewFRevision(designReviewFDataRequest);
	      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

	      dataManagement.removeAndAssignProject(createItemOutput.item, project);
	      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

	      if (designReviewFDataRequest.getDesignrfAttachments() != null) {
	        dataManagement.addDesignRFRevisionAttachements(createItemOutput.itemRev, designReviewFDataRequest.getDesignrfAttachments());
	      }

	      String docManagerUserId = dataManagement.getUserFromPref(designReviewFDataRequest.getProjectId());
	      User user = null;
	      if (!docManagerUserId.equals("")) {
	        user = query.queryUser(docManagerUserId);
	        if (user != null) {
	          dataManagement.changeOwner(createItemOutput.item, user);
	          dataManagement.changeOwner(createItemOutput.itemRev, user);
	        }

	      }

	     
	      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
	      String itemRevType = createItemOutput.itemRev.get_object_type();

	      String processTemplateName = "LDM-001-收文流程";

	      if (!processTemplateName.equals(""))
	      {
	        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error(e);

	      if (createItemOutput != null) {
	        try
	        {
	          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

	          String ret = "";
	          String[] msgs = serviceData.getPartialError(0).getMessages();

	          for (int i = 0; i < msgs.length; i++) {
	            ret = ret + msgs[i] + " ";
	          }

	          System.out.println(ret);
	          logger.info(ret);
	        }
	        catch (Exception e2) {
	          e2.printStackTrace();
	          logger.error(e2);
	        }
	      }

	      returnVal.setReturnCode("INT_2");
	      returnVal.setReturnMessage("异常:" + e.getMessage());
	      return returnVal;
	    }

	    returnVal.setReturnCode("INT_0");
	    returnVal.setReturnMessage("");

	    String end = df.format(new Date());
	    System.out.println("TA结束时间：" + end);
	    logger.info("-----------结束时间：" + end + "------------");
	    logger.info("-----------end setTAData------------");
	    return returnVal;
	}
	public ReturnVal setIITFData(IITFDataMessageBody iitfDataRequest) {
		ReturnVal returnVal = new ReturnVal();
	    if (session == null)
	    {
	      boolean flag = loginTC();
	      if (!flag) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("登陆失败");
	        return returnVal;
	      }

	    }

	    logger.info("-----------begin setCRData------------");

	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String start = df.format(new Date());
	    logger.info("-----------开始时间：" + start + "------------");
	    System.out.println("IITF开始时间：" + start);

	    CreateItemsOutput createItemOutput = null;
	    DataManagementService dmService = 
	      DataManagementService.getService(Session.getConnection());
	    try
	    {
	      DataManagement dataManagement = new DataManagement(session);
	      Query query = new Query(session);
	      Workflow workflow = new Workflow(session);

	      if ((iitfDataRequest.getProjectid() == null) || (iitfDataRequest.getProjectid().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("工程代号需要填写！");
	        return returnVal;
	      }
	      if ((iitfDataRequest.getOurDocNum() == null) || (iitfDataRequest.getOurDocNum().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("编号需要填写！");
	        return returnVal;
	      }
	      if ((iitfDataRequest.getRevisionID() == null) || (iitfDataRequest.getRevisionID().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("版本需要填写！");
	        return returnVal;
	      }

	      TC_Project project = query.queryProjectExist(iitfDataRequest.getProjectid());
	      if (project == null) {
	        returnVal.setReturnCode("INT_1");
	        returnVal.setReturnMessage("项目号" + iitfDataRequest.getProjectid() + "不存在！");
	        return returnVal;
	      }
	      
	      ModelObject findChangeRqRevision = query.queryIITFRevision(iitfDataRequest.getProjectid(), iitfDataRequest.getOurDocNum());
	      if ((iitfDataRequest.getSendLotDate() != null) && (!iitfDataRequest.getSendLotNo().equals(""))) {
	          if ((iitfDataRequest.getSendLotDate() == null) || (iitfDataRequest.getSendLotDate().equals(""))) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("发送供应商日期需要填写！");
	            return returnVal;
	          }

	          if (findChangeRqRevision == null) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的IITF：" + iitfDataRequest.getProjectid() + "|" + iitfDataRequest.getOurDocNum() + "|" + iitfDataRequest.getRevisionID());
	            return returnVal;
	          }

	          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
	          String closeDateStr = iitfDataRequest.getSendLotDate();
	          if (!closeDateStr.equals("")) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = dateFormat.parse(closeDateStr);

	            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
	          }
	          String[] values = { iitfDataRequest.getSendLotNo(), closeDateStr };
	          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新IITF：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	        }
	      
	      
	      
	      
	      String[] str=iitfDataRequest.getOurDocNum().split("-");
	      
	      ModelObject object= query.queryICMRevision(iitfDataRequest.getProjectid(), iitfDataRequest.getOneParty(), iitfDataRequest.getTwoParty(), iitfDataRequest.getIntfcType(), iitfDataRequest.getSerNum(), iitfDataRequest.getReleaseParty(), iitfDataRequest.getRecvParty(),str[3]);
	      
	      
	      
	      if (findChangeRqRevision != null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("IITF数据在TC中已经存在符合条件的IITF：" + iitfDataRequest.getProjectid() + "|" + iitfDataRequest.getSendRecvLettDate() + "|" + iitfDataRequest.getRevisionID());
	        return returnVal;
	      }
	      
	      if (object == null) {
		        returnVal.setReturnCode("INT_2");
		        returnVal.setReturnMessage("接口手册版本数据在TC中已经不存在符合条件的：" + iitfDataRequest.getProjectid() + "|" + iitfDataRequest.getOneParty() + "|" + iitfDataRequest.getTwoParty());
		        return returnVal;
		     }

	      createItemOutput = dataManagement.createIITFRevision(iitfDataRequest);
	      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

	      dataManagement.removeAndAssignProject(createItemOutput.item, project);
	      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

	      if (iitfDataRequest.getAttachments() != null) {
	        dataManagement.addIITFRevisionAttachements(createItemOutput.itemRev, iitfDataRequest.getAttachments());
	      }

	      String docManagerUserId = dataManagement.getUserFromPref(iitfDataRequest.getProjectid());
	      User user = null;
	      if (!docManagerUserId.equals("")) {
	        user = query.queryUser(docManagerUserId);
	        if (user != null) {
	          dataManagement.changeOwner(createItemOutput.item, user);
	          dataManagement.changeOwner(createItemOutput.itemRev, user);
	        }

	      }
	      
	     String prefin= iitfDataRequest.getPreOrFin();
	     String preorfin=""; 
	     if("FIN".equals(prefin)){
	    	  preorfin="CN9FINIITF";
	      }else if("PRE".equals(prefin)){
	    	  preorfin="CN9PREIITF";
	      }
	     boolean flag=dataManagement.addRelation(object,createItemOutput.itemRev , preorfin);
	      
	      if(!flag){
	    	  returnVal.setReturnCode("INT_2");
	          returnVal.setReturnMessage("异常:添加关系出错" );
	          return returnVal;
	      }
	     
	      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
	      String itemRevType = createItemOutput.itemRev.get_object_type();

	      String processTemplateName = "P006-IITF接收处理流程";

	      if (!processTemplateName.equals(""))
	      {
	        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
	      }
	      
	      //TCComponent com=(TCComponent)object;
	     
	      if("FIN".equals(iitfDataRequest.getPreOrFin())){
	    	  String[] names = { "cn9FinStatus", "cn9FINOpenDate" };
	    	  
	    	 // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = new Date();

	           String finopenDate = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
		      String[] values = { "打开", finopenDate };
	    	  
		      String setValueReturn = dataManagement.setObjectProperties(object, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新IITF：" + setValueReturn);
	            return returnVal;
	          }
	      }else if("PRE".equals(iitfDataRequest.getPreOrFin())){
	    	  String[] names = { "cn9PreStatus", "cn9PreOpenDate" };
	    	  
		    	 // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
		            Date closeDate = new Date();

		           String finopenDate = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
			      String[] values = { "打开", finopenDate };
		    	  
			      String setValueReturn = dataManagement.setObjectProperties(object, names, values);
		          if (!setValueReturn.equals("")) {
		            returnVal.setReturnCode("INT_2");
		            returnVal.setReturnMessage("更新IITF：" + setValueReturn);
		            return returnVal;
		          }
	      }
	      
	      
	      
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error(e);

	      if (createItemOutput != null) {
	        try
	        {
	          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

	          String ret = "";
	          String[] msgs = serviceData.getPartialError(0).getMessages();

	          for (int i = 0; i < msgs.length; i++) {
	            ret = ret + msgs[i] + " ";
	          }

	          System.out.println(ret);
	          logger.info(ret);
	        }
	        catch (Exception e2) {
	          e2.printStackTrace();
	          logger.error(e2);
	        }
	      }

	      returnVal.setReturnCode("INT_2");
	      returnVal.setReturnMessage("异常:" + e.getMessage());
	      return returnVal;
	    }

	   
	    returnVal.setReturnCode("INT_0");
	    returnVal.setReturnMessage("");

	    String end = df.format(new Date());
	    System.out.println("TA结束时间：" + end);
	    logger.info("-----------结束时间：" + end + "------------");
	    logger.info("-----------end setTAData------------");
	    return returnVal;
	}

public static void main(String[] args) throws Exception {
	ReturnVal returnVal = new ReturnVal();
   PLMServerLOTImpl plm=new PLMServerLOTImpl();
	if (session == null)
    {
      boolean flag = plm.loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        //return returnVal;
      }

    }
	Query query=new Query(session);
	//BaseDAO basedao=new BaseDAO();
	 
	
	DataManagementService dmService = 
	      DataManagementService.getService(Session.getConnection());

	ModelObject model=	query.queryIITFRevision("1188", "NPAR-300056-NPMB-121A");
	
	dmService.getProperties(new ModelObject[] { model }, new String[] { "cn9Distributee","cn9ReceiveSendFlag"});
	if(model!=null){
	String[] str1=model.getPropertyObject("cn9Distributee").getStringArrayValue();
		for (int i = 0; i < str1.length; i++) {
			System.out.println(str1[i]);
		}
	}else{
		System.out.println("为空");
	}
	//parentFolder.getProperty("contents");
	
}

public ReturnVal setNCRData(NCRDataMessageBody setNCRDataRequest) {
	ReturnVal returnVal = new ReturnVal();

    if (session == null)
    {
      boolean flag = loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        return returnVal;
      }

    }

    logger.info("-----------begin setNCRData------------");

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String start = df.format(new Date());
    logger.info("-----------NCR开始时间：" + start + "------------");
    System.out.println("NCR开始时间：" + start);

    CreateItemsOutput createItemOutput = null;
    DataManagementService dmService = 
      DataManagementService.getService(Session.getConnection());
    try
    {
      DataManagement dataManagement = new DataManagement(session);
      Query query = new Query(session);
      Workflow workflow = new Workflow(session);

      if ((setNCRDataRequest.getProductCode() == null) || (setNCRDataRequest.getProductCode().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("工程代号需要填写！");
        return returnVal;
      }
      if ((setNCRDataRequest.getCode() == null) || (setNCRDataRequest.getCode().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("编号需要填写！");
        return returnVal;
      }
      if ((setNCRDataRequest.getRevision() == null) || (setNCRDataRequest.getRevision().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("版本需要填写！");
        return returnVal;
      }

      TC_Project project = query.queryProjectExist(setNCRDataRequest.getProductCode());
      if (project == null) {
        returnVal.setReturnCode("INT_1");
        returnVal.setReturnMessage("项目号" + setNCRDataRequest.getProductCode() + "不存在！");
        return returnVal;
      }

      ModelObject findNonConformaRevision = query.queryNonConformaRevision(setNCRDataRequest.getProductCode(), setNCRDataRequest.getSendChannelNo(), setNCRDataRequest.getRevision());
      
      if ((setNCRDataRequest.getCloseStatus() != null) && (!setNCRDataRequest.getCloseStatus().equals(""))) {
          if ((setNCRDataRequest.getCloseDate() == null) || (setNCRDataRequest.getCloseDate().equals(""))) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("关闭日期需要填写！");
            return returnVal;
          }

          if (findNonConformaRevision == null) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新关闭状态时在TC中没有找到符合条件的CR单：" + setNCRDataRequest.getProductCode() + "|" + setNCRDataRequest.getSendChannelNo() + "|" + setNCRDataRequest.getRevision());
            return returnVal;
          }

          String[] names = { "cn9CloseStatus", "cn9CloseShigongDate" };
          String closeDateStr = setNCRDataRequest.getCloseDate();
          if (!closeDateStr.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
            Date closeDate = dateFormat.parse(closeDateStr);

            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
          }
          String[] values = { setNCRDataRequest.getCloseStatus(), closeDateStr };
          String setValueReturn = dataManagement.setObjectProperties(findNonConformaRevision, names, values);
          
          //setObjectProperties(setNCRDataRequest, names, values);
          if (!setValueReturn.equals("")) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
            return returnVal;
          }

          dmService.refreshObjects(new ModelObject[] { findNonConformaRevision });

          returnVal.setReturnCode("INT_0");
          returnVal.setReturnMessage("");
          return returnVal;
        }
      
      if ((setNCRDataRequest.getSendLotDate() != null) && (!setNCRDataRequest.getSendLotNo().equals(""))) {
          if ((setNCRDataRequest.getSendLotDate() == null) || (setNCRDataRequest.getSendLotDate().equals(""))) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("发送供应商日期需要填写！");
            return returnVal;
          }

          if (findNonConformaRevision == null) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新关闭状态时在TC中没有找到符合条件的CR单：" + setNCRDataRequest.getProductCode() + "|" + setNCRDataRequest.getSendChannelNo() + "|" + setNCRDataRequest.getRevision());
            return returnVal;
          }

          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
          String closeDateStr = setNCRDataRequest.getSendLotDate();
          if (!closeDateStr.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
            Date closeDate = dateFormat.parse(closeDateStr);

            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
          }
          String[] values = { setNCRDataRequest.getSendLotNo(), closeDateStr };
          String setValueReturn = dataManagement.setObjectProperties(findNonConformaRevision, names, values);
          if (!setValueReturn.equals("")) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
            return returnVal;
          }

          dmService.refreshObjects(new ModelObject[] { findNonConformaRevision });

          returnVal.setReturnCode("INT_0");
          returnVal.setReturnMessage("");
          return returnVal;
        }
      
      if (findNonConformaRevision != null) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("NCR数据在TC中已经存在符合条件的NCR单：" + setNCRDataRequest.getProductCode() + "|" + setNCRDataRequest.getCode() + "|" + setNCRDataRequest.getRevision());
        return returnVal;
      }

      createItemOutput = dataManagement.createNonConformaRevision(setNCRDataRequest);
      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

      dataManagement.removeAndAssignProject(createItemOutput.item, project);
      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

      if (setNCRDataRequest.getAttachments() != null) {
        dataManagement.addNonConformaRevisionAttachements(createItemOutput.itemRev, setNCRDataRequest.getAttachments());
      }

      String docManagerUserId = dataManagement.getUserFromPref(setNCRDataRequest.getProductCode());
      User user = null;
      if (!docManagerUserId.equals("")) {
        user = query.queryUser(docManagerUserId);
        if (user != null) {
          dataManagement.changeOwner(createItemOutput.item, user);
          dataManagement.changeOwner(createItemOutput.itemRev, user);
        }

      }

      boolean isReply = true;
      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
      String itemRevType = createItemOutput.itemRev.get_object_type();

      String processTemplateName = dataManagement.getProcessTemplateNameFromPref(itemRevType, isReply);

      if (!processTemplateName.equals("")) {
        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, isReply ? user : null);
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e);

      if (createItemOutput != null) {
        try
        {
          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

          String ret = "";
          String[] msgs = serviceData.getPartialError(0).getMessages();

          for (int i = 0; i < msgs.length; i++) {
            ret = ret + msgs[i] + " ";
          }

          System.out.println(ret);
          logger.info(ret);
        }
        catch (Exception e2) {
          e2.printStackTrace();
          logger.error(e2);
        }
      }

      returnVal.setReturnCode("INT_2");
      returnVal.setReturnMessage("异常:" + e.getMessage());
      return returnVal;
    }

    returnVal.setReturnCode("INT_0");
    returnVal.setReturnMessage("");

    String end = df.format(new Date());
    System.out.println("NCR结束时间：" + end);
    logger.info("-----------NCR结束时间：" + end + "------------");
    logger.info("-----------end setNCRData------------");
    return returnVal;
}

public ReturnVal setIICSData(IICSDataMessageBody iicsDataRequest) {
	ReturnVal returnVal = new ReturnVal();
    if (session == null)
    {
      boolean flag = loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        return returnVal;
      }

    }

    logger.info("-----------begin setCRData------------");

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String start = df.format(new Date());
    logger.info("-----------开始时间：" + start + "------------");
    System.out.println("IITF开始时间：" + start);

    CreateItemsOutput createItemOutput = null;
    DataManagementService dmService = 
      DataManagementService.getService(Session.getConnection());
    try
    {
      DataManagement dataManagement = new DataManagement(session);
      Query query = new Query(session);
      Workflow workflow = new Workflow(session);

      if ((iicsDataRequest.getProjectid() == null) || (iicsDataRequest.getProjectid().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("工程代号需要填写！");
        return returnVal;
      }
      if ((iicsDataRequest.getOurDocNum() == null) || (iicsDataRequest.getOurDocNum().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("编号需要填写！");
        return returnVal;
      }
      if ((iicsDataRequest.getRevisionID() == null) || (iicsDataRequest.getRevisionID().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("版本需要填写！");
        return returnVal;
      }

      TC_Project project = query.queryProjectExist(iicsDataRequest.getProjectid());
      if (project == null) {
        returnVal.setReturnCode("INT_1");
        returnVal.setReturnMessage("项目号" + iicsDataRequest.getProjectid() + "不存在！");
        return returnVal;
      }

      ModelObject findChangeRqRevision = query.queryIICSRevision(iicsDataRequest.getProjectid(), iicsDataRequest.getOurDocNum());

      if ((iicsDataRequest.getSendLotDate() != null) && (!iicsDataRequest.getSendLotNo().equals(""))) {
          if ((iicsDataRequest.getSendLotDate() == null) || (iicsDataRequest.getSendLotDate().equals(""))) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("发送供应商日期需要填写！");
            return returnVal;
          }

          if (findChangeRqRevision == null) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的IITF：" + iicsDataRequest.getProjectid() + "|" + iicsDataRequest.getOurDocNum() + "|" + iicsDataRequest.getRevisionID());
            return returnVal;
          }

          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
          String closeDateStr = iicsDataRequest.getSendLotDate();
          if (!closeDateStr.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
            Date closeDate = dateFormat.parse(closeDateStr);

            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
          }
          String[] values = { iicsDataRequest.getSendLotNo(), closeDateStr };
          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
          if (!setValueReturn.equals("")) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新IITF：" + setValueReturn);
            return returnVal;
          }

          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

          returnVal.setReturnCode("INT_0");
          returnVal.setReturnMessage("");
          return returnVal;
        }

      
      
      if (findChangeRqRevision != null) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("IITF数据在TC中已经存在符合条件的IITF：" + iicsDataRequest.getProjectid() + "|" + iicsDataRequest.getSendRecvLettDate() + "|" + iicsDataRequest.getRevisionID());
        return returnVal;
      }
      String[] str=iicsDataRequest.getOurDocNum().split("-");
      ModelObject object= query.queryICMRevision(iicsDataRequest.getProjectid(), iicsDataRequest.getOneParty(), iicsDataRequest.getTwoParty(), iicsDataRequest.getIntfcType(), iicsDataRequest.getSerNum(), iicsDataRequest.getReleaseParty(), iicsDataRequest.getRecvParty(),str[3]);

      
     
      
      
      
      if (object == null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("接口手册版本数据在TC中已经存在符合条件的：" + iicsDataRequest.getProjectid() + "|" + iicsDataRequest.getOneParty() + "|" + iicsDataRequest.getTwoParty());
	        return returnVal;
	    }
      
      dmService.refreshObjects(new ModelObject[] { object });
      
      createItemOutput = dataManagement.createIICSRevision(iicsDataRequest);
      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

      dataManagement.removeAndAssignProject(createItemOutput.item, project);
      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

      if (iicsDataRequest.getAttachments() != null) {
        dataManagement.addIITFRevisionAttachements(createItemOutput.itemRev, iicsDataRequest.getAttachments());
      }

      String docManagerUserId = dataManagement.getUserFromPref(iicsDataRequest.getProjectid());
      User user = null;
      if (!docManagerUserId.equals("")) {
        user = query.queryUser(docManagerUserId);
        if (user != null) {
          dataManagement.changeOwner(createItemOutput.item, user);
          dataManagement.changeOwner(createItemOutput.itemRev, user);
        }

      }
      String prefin= iicsDataRequest.getPreOrFin();
	     String preorfin=""; 
	     if("FIN".equals(prefin)){
	    	  preorfin="CN9FINIICS";
	      }else if("PRE".equals(prefin)){
	    	  preorfin="CN9PREIICS";
	      }
      boolean flag=dataManagement.addRelation(object,createItemOutput.itemRev, preorfin);
      
      if(!flag){
    	  returnVal.setReturnCode("INT_2");
          returnVal.setReturnMessage("异常:添加关系出错" );
          return returnVal;
      }
      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
      String itemRevType = createItemOutput.itemRev.get_object_type();

      String processTemplateName = "P007-IICS接收处理流程";

      if (!processTemplateName.equals(""))
      {
        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
      }
      
      if("FIN".equals(iicsDataRequest.getPreOrFin())){
    	  if("同意".equals(iicsDataRequest.getShenCConclu())){
    		  String[] names = { "cn9FinStatus", "cn9FINCloseDate" ,"cn9FINCloseNum"};
        	  
    	    	 // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
    	            Date closeDate = new Date();
    	            
    	           // dmService.getProperties(new ModelObject[] { object }, new String[] { "cn9FinStatus" });
    	            
    	            
    	           String finopenDate = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
    		      String[] values = { "关闭", finopenDate,iicsDataRequest.getOurDocNum()};
    	    	  
    		      String setValueReturn = dataManagement.setObjectProperties(object, names, values);
    	          if (!setValueReturn.equals("")) {
    	            returnVal.setReturnCode("INT_2");
    	            returnVal.setReturnMessage("更新IITF：" + setValueReturn);
    	            return returnVal;
    	          }
    	  }//cn9ShenCConclu
    	 
      }else if("PRE".equals(iicsDataRequest.getPreOrFin())){
    	  if("同意".equals(iicsDataRequest.getShenCConclu())){
    	  String[] names = { "cn9PreStatus", "cn9PreCloseDate","cn9PRECloseNum" };
    	  
	    	 // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	            Date closeDate = new Date();

	           String finopenDate = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
		      String[] values = { "关闭", finopenDate,iicsDataRequest.getOurDocNum() };
	    	  
		      String setValueReturn = dataManagement.setObjectProperties(object, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新IITF：" + setValueReturn);
	            return returnVal;
	          }
      }
      }
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e);

      if (createItemOutput != null) {
        try
        {
          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

          String ret = "";
          String[] msgs = serviceData.getPartialError(0).getMessages();

          for (int i = 0; i < msgs.length; i++) {
            ret = ret + msgs[i] + " ";
          }

          System.out.println(ret);
          logger.info(ret);
        }
        catch (Exception e2) {
          e2.printStackTrace();
          logger.error(e2);
        }
      }

      returnVal.setReturnCode("INT_2");
      returnVal.setReturnMessage("异常:" + e.getMessage());
      return returnVal;
    }

    returnVal.setReturnCode("INT_0");
    returnVal.setReturnMessage("");

    String end = df.format(new Date());
    System.out.println("TA结束时间：" + end);
    logger.info("-----------结束时间：" + end + "------------");
    logger.info("-----------end setTAData------------");
    return returnVal;
}



	//DataManagement dm=new DataManagement(session);
//	 PLMServerLOTImpl plm=new PLMServerLOTImpl();
//	 boolean flag = plm.loginTC();
//	 DataManagementService dmService = 
//	      DataManagementService.getService(Session.getConnection());
//	Query quer=new Query(session);
//	ModelObject object;
//	try {
//		object = quer.queryICMRevision("1516", "NB", "HRT", "04", "01", "N", "B");
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	 
//	 dmService.getProperties(new ModelObject[] {object }, new String[] { "cn91stParty" });
//	 try {
//		System.out.println(object.getPropertyDisplayableValue("cn91stParty"));
//	} catch (NotLoadedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	  ModelObject findChangeRqRevision = quer.queryIITFRevision("1516", "YDNC-300043-YPMB-101A");
//	 Relationship ship=new Relationship();
//	 ship.clientId="AppX-Test";
//	 ship.primaryObject=object;
//	 ship.secondaryObject=findChangeRqRevision;
//	 ship.relationType="CN9FINIICS";
//	 CreateRelationsResponse response=dmService.createRelations(new Relationship[]{ship});


public ReturnVal setAp1000IICSData(Ap1000IICSDataMessageBody iicsDataRequest) {
	ReturnVal returnVal = new ReturnVal();
    if (session == null)
    {
      boolean flag = loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        return returnVal;
      }

    }

    logger.info("-----------begin setCRData------------");

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String start = df.format(new Date());
    logger.info("-----------开始时间：" + start + "------------");
    System.out.println("IITF开始时间：" + start);

    CreateItemsOutput createItemOutput = null;
    DataManagementService dmService = 
      DataManagementService.getService(Session.getConnection());
    try
    {
      DataManagement dataManagement = new DataManagement(session);
      Query query = new Query(session);
      Workflow workflow = new Workflow(session);

      if ((iicsDataRequest.getProjectid() == null) || (iicsDataRequest.getProjectid().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("工程代号需要填写！");
        return returnVal;
      }
      if ((iicsDataRequest.getOurDocNum() == null) || (iicsDataRequest.getOurDocNum().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("编号需要填写！");
        return returnVal;
      }
      if ((iicsDataRequest.getRevisionID() == null) || (iicsDataRequest.getRevisionID().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("版本需要填写！");
        return returnVal;
      }

      TC_Project project = query.queryProjectExist(iicsDataRequest.getProjectid());
      if (project == null) {
        returnVal.setReturnCode("INT_1");
        returnVal.setReturnMessage("项目号" + iicsDataRequest.getProjectid() + "不存在！");
        return returnVal;
      }

      ModelObject findChangeRqRevision = query.queryAP1000IICSRevision(iicsDataRequest.getProjectid(), iicsDataRequest.getOurDocNum());

      
      if ((iicsDataRequest.getSendLotDate() != null) && (!iicsDataRequest.getSendLotNo().equals(""))) {
          if ((iicsDataRequest.getSendLotDate() == null) || (iicsDataRequest.getSendLotDate().equals(""))) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("发送供应商日期需要填写！");
            return returnVal;
          }

          if (findChangeRqRevision == null) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的IICS：" + iicsDataRequest.getProjectid() + "|" + iicsDataRequest.getOurDocNum() + "|" + iicsDataRequest.getRevisionID());
            return returnVal;
          }

          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
          String closeDateStr = iicsDataRequest.getSendLotDate();
          if (!closeDateStr.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
            Date closeDate = dateFormat.parse(closeDateStr);

            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
          }
          String[] values = { iicsDataRequest.getSendLotNo(), closeDateStr };
          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
          if (!setValueReturn.equals("")) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新IICS：" + setValueReturn);
            return returnVal;
          }

          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

          returnVal.setReturnCode("INT_0");
          returnVal.setReturnMessage("");
          return returnVal;
        }
      
      

      if (findChangeRqRevision != null) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("IITF数据在TC中已经存在符合条件的IITF：" + iicsDataRequest.getProjectid() + "|" + iicsDataRequest.getSendRecvLettDate() + "|" + iicsDataRequest.getRevisionID());
        return returnVal;
      }
      ModelObject object= query.queryAP1000ICMRevision(iicsDataRequest.getProjectid(), iicsDataRequest.getIntfcIdent());

      
      
      if (object == null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("接口手册版本数据在TC中已经存在符合条件的：" + iicsDataRequest.getProjectid() + "|" + iicsDataRequest.getOneParty() + "|" + iicsDataRequest.getTwoParty());
	        return returnVal;
	      }
      createItemOutput = dataManagement.createIICSRevision(iicsDataRequest);
      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

      dataManagement.removeAndAssignProject(createItemOutput.item, project);
      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

      if (iicsDataRequest.getAttachments() != null) {
        dataManagement.addAp1000IITFRevisionAttachements(createItemOutput.itemRev, iicsDataRequest.getAttachments());
      }

      String docManagerUserId = dataManagement.getUserFromPref(iicsDataRequest.getProjectid());
      User user = null;
      if (!docManagerUserId.equals("")) {
        user = query.queryUser(docManagerUserId);
        if (user != null) {
          dataManagement.changeOwner(createItemOutput.item, user);
          dataManagement.changeOwner(createItemOutput.itemRev, user);
        }

      }
      String prefin= iicsDataRequest.getPreOrFin();
	     String preorfin=""; 
	     if("FIN".equals(prefin)){
	    	  preorfin="CN9FINIITF";
	      }else if("PRE".equals(prefin)){
	    	  preorfin="CN9PREIITF";
	      }
      boolean flag=dataManagement.addRelation(createItemOutput.itemRev,object, preorfin);
      
      if(!flag){
    	  returnVal.setReturnCode("INT_2");
          returnVal.setReturnMessage("异常:添加关系出错" );
          return returnVal;
      }
      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
      String itemRevType = createItemOutput.itemRev.get_object_type();

      String processTemplateName = "P007-IICS接收处理流程";

      if (!processTemplateName.equals(""))
      {
        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e);

      if (createItemOutput != null) {
        try
        {
          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

          String ret = "";
          String[] msgs = serviceData.getPartialError(0).getMessages();

          for (int i = 0; i < msgs.length; i++) {
            ret = ret + msgs[i] + " ";
          }

          System.out.println(ret);
          logger.info(ret);
        }
        catch (Exception e2) {
          e2.printStackTrace();
          logger.error(e2);
        }
      }

      returnVal.setReturnCode("INT_2");
      returnVal.setReturnMessage("异常:" + e.getMessage());
      return returnVal;
    }

    returnVal.setReturnCode("INT_0");
    returnVal.setReturnMessage("");

    String end = df.format(new Date());
    System.out.println("TA结束时间：" + end);
    logger.info("-----------结束时间：" + end + "------------");
    logger.info("-----------end setTAData------------");
    return returnVal;
}

public ReturnVal setAp1000IITFData(Ap1000IITFDataMessageBody iitfDataRequest) {
	ReturnVal returnVal = new ReturnVal();
    if (session == null)
    {
      boolean flag = loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        return returnVal;
      }

    }

    logger.info("-----------begin setCRData------------");

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String start = df.format(new Date());
    logger.info("-----------开始时间：" + start + "------------");
    System.out.println("IITF开始时间：" + start);

    CreateItemsOutput createItemOutput = null;
    DataManagementService dmService = 
      DataManagementService.getService(Session.getConnection());
    try
    {
      DataManagement dataManagement = new DataManagement(session);
      Query query = new Query(session);
      Workflow workflow = new Workflow(session);

      if ((iitfDataRequest.getProjectid() == null) || (iitfDataRequest.getProjectid().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("工程代号需要填写！");
        return returnVal;
      }
      if ((iitfDataRequest.getOurDocNum() == null) || (iitfDataRequest.getOurDocNum().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("编号需要填写！");
        return returnVal;
      }
      if ((iitfDataRequest.getRevisionID() == null) || (iitfDataRequest.getRevisionID().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("版本需要填写！");
        return returnVal;
      }

      TC_Project project = query.queryProjectExist(iitfDataRequest.getProjectid());
      if (project == null) {
        returnVal.setReturnCode("INT_1");
        returnVal.setReturnMessage("项目号" + iitfDataRequest.getProjectid() + "不存在！");
        return returnVal;
      }
      
      ModelObject findChangeRqRevision = query.queryAP1000IITFRevision(iitfDataRequest.getProjectid(), iitfDataRequest.getOurDocNum());
     
      if ((iitfDataRequest.getSendLotDate() != null) && (!iitfDataRequest.getSendLotNo().equals(""))) {
          if ((iitfDataRequest.getSendLotDate() == null) || (iitfDataRequest.getSendLotDate().equals(""))) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("发送供应商日期需要填写！");
            return returnVal;
          }

          if (findChangeRqRevision == null) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新发送供应商在TC中没有找到符合条件的IICS：" + iitfDataRequest.getProjectid() + "|" + iitfDataRequest.getOurDocNum() + "|" + iitfDataRequest.getRevisionID());
            return returnVal;
          }

          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
          String closeDateStr = iitfDataRequest.getSendLotDate();
          if (!closeDateStr.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
            Date closeDate = dateFormat.parse(closeDateStr);

            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
          }
          String[] values = { iitfDataRequest.getSendLotNo(), closeDateStr };
          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
          if (!setValueReturn.equals("")) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新IICS：" + setValueReturn);
            return returnVal;
          }

          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

          returnVal.setReturnCode("INT_0");
          returnVal.setReturnMessage("");
          return returnVal;
        }
      
      
      
      ModelObject object= query.queryAP1000ICMRevision(iitfDataRequest.getProjectid(), iitfDataRequest.getIntfcIdent());

      if (findChangeRqRevision != null) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("IITF数据在TC中已经存在符合条件的IITF：" + iitfDataRequest.getProjectid() + "|" + iitfDataRequest.getSendRecvLettDate() + "|" + iitfDataRequest.getRevisionID());
        return returnVal;
      }
      
      if (object == null) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("接口手册版本数据在TC中已经存在符合条件的：" + iitfDataRequest.getProjectid() + "|" + iitfDataRequest.getOneParty() + "|" + iitfDataRequest.getTwoParty());
	        return returnVal;
	      }

      createItemOutput = dataManagement.createAp1000IITFRevision(iitfDataRequest);
      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

      dataManagement.removeAndAssignProject(createItemOutput.item, project);
      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

      if (iitfDataRequest.getAttachments() != null) {
        dataManagement.addAp1000IITFRevisionAttachements(createItemOutput.itemRev, iitfDataRequest.getAttachments());
      }

      String docManagerUserId = dataManagement.getUserFromPref(iitfDataRequest.getProjectid());
      User user = null;
      if (!docManagerUserId.equals("")) {
        user = query.queryUser(docManagerUserId);
        if (user != null) {
          dataManagement.changeOwner(createItemOutput.item, user);
          dataManagement.changeOwner(createItemOutput.itemRev, user);
        }

      }
      String prefin= iitfDataRequest.getPreOrFin();
	     String preorfin=""; 
	     if("FIN".equals(prefin)){
	    	  preorfin="CN9FINIITF";
	      }else if("PRE".equals(prefin)){
	    	  preorfin="CN9PREIITF";
	      }
      boolean flag=dataManagement.addRelation(object,createItemOutput.itemRev, preorfin);
      
      if(!flag){
    	  returnVal.setReturnCode("INT_2");
          returnVal.setReturnMessage("异常:添加关系出错" );
          return returnVal;
      }
      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
      String itemRevType = createItemOutput.itemRev.get_object_type();

      String processTemplateName = "P006-IITF接收处理流程";

      if (!processTemplateName.equals(""))
      {
        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
      }
      
     
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e);

      if (createItemOutput != null) {
        try
        {
          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

          String ret = "";
          String[] msgs = serviceData.getPartialError(0).getMessages();

          for (int i = 0; i < msgs.length; i++) {
            ret = ret + msgs[i] + " ";
          }

          System.out.println(ret);
          logger.info(ret);
        }
        catch (Exception e2) {
          e2.printStackTrace();
          logger.error(e2);
        }
      }

      returnVal.setReturnCode("INT_2");
      returnVal.setReturnMessage("异常:" + e.getMessage());
      return returnVal;
    }

   
    returnVal.setReturnCode("INT_0");
    returnVal.setReturnMessage("");

    String end = df.format(new Date());
    System.out.println("TA结束时间：" + end);
    logger.info("-----------结束时间：" + end + "------------");
    logger.info("-----------end setTAData------------");
    return returnVal;
}

public ReturnVal setFuData(FUDataMessageBody fuDataRequest) {
	ReturnVal returnVal = new ReturnVal();
    if (session == null)
    {
      boolean flag = loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        return returnVal;
      }

    }

    logger.info("-----------begin setCRData------------");

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String start = df.format(new Date());
    logger.info("-----------开始时间：" + start + "------------");
    System.out.println("IITF开始时间：" + start);

    CreateItemsOutput createItemOutput = null;
    DataManagementService dmService = 
      DataManagementService.getService(Session.getConnection());
    try
    {
      DataManagement dataManagement = new DataManagement(session);
      Query query = new Query(session);
      Workflow workflow = new Workflow(session);

      if ((fuDataRequest.getProjectId() == null) || (fuDataRequest.getProjectId().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("工程代号需要填写！");
        return returnVal;
      }
      if ((fuDataRequest.getLetterSendNo() == null) || (fuDataRequest.getLetterSendNo().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("编号需要填写！");
        return returnVal;
      }
      TC_Project project = query.queryProjectExist(fuDataRequest.getProjectId());
      if (project == null) {
        returnVal.setReturnCode("INT_1");
        returnVal.setReturnMessage("项目号" + fuDataRequest.getProjectId() + "不存在！");
        return returnVal;
      }
      
      ModelObject findChangeRqRevision = query.queryFURevision(fuDataRequest.getProjectId(), fuDataRequest.getLetterSendNo());
     // ModelObject object= query.queryAP1000ICMRevision(iitfDataRequest.getProjectid(), iitfDataRequest.getIntfcIdent());

      if (findChangeRqRevision != null) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("FU数据在TC中存在符合条件的FU：" + fuDataRequest.getProjectId() + "|" + fuDataRequest.getLetterSendNo() );
        return returnVal;
      }
      
      
      createItemOutput = dataManagement.createFURevision(fuDataRequest);
      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

      dataManagement.removeAndAssignProject(createItemOutput.item, project);
      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);

      if (fuDataRequest.getAttachments() != null) {
        dataManagement.addFURevisionAttachements(createItemOutput.itemRev, fuDataRequest.getAttachments());
      }

      String docManagerUserId = dataManagement.getUserFromPref(fuDataRequest.getProjectId());
      User user = null;
      if (!docManagerUserId.equals("")) {
        user = query.queryUser(docManagerUserId);
        if (user != null) {
          dataManagement.changeOwner(createItemOutput.item, user);
          dataManagement.changeOwner(createItemOutput.itemRev, user);
        }

      }
      
      
      String processTemplateName = "LDM-001-收文流程";

      if (!processTemplateName.equals(""))
      {
        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
      }
     
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e);

      if (createItemOutput != null) {
        try
        {
          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

          String ret = "";
          String[] msgs = serviceData.getPartialError(0).getMessages();

          for (int i = 0; i < msgs.length; i++) {
            ret = ret + msgs[i] + " ";
          }

          System.out.println(ret);
          logger.info(ret);
        }
        catch (Exception e2) {
          e2.printStackTrace();
          logger.error(e2);
        }
      }

      returnVal.setReturnCode("INT_2");
      returnVal.setReturnMessage("异常:" + e.getMessage());
      return returnVal;
    }

   
    returnVal.setReturnCode("INT_0");
    returnVal.setReturnMessage("");

    String end = df.format(new Date());
    System.out.println("TA结束时间：" + end);
    logger.info("-----------结束时间：" + end + "------------");
    logger.info("-----------end setTAData------------");
    return returnVal;
    
}


public ReturnVal setDesignData(DesignDataMessageBody designDataMessage) {
	ReturnVal returnVal = new ReturnVal();
    if (session == null)
    {
      boolean flag = loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        return returnVal;
      }

    }

    logger.info("-----------begin setCRData------------");

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String start = df.format(new Date());
    logger.info("-----------开始时间：" + start + "------------");
    System.out.println("IITF开始时间：" + start);

    CreateItemsOutput createItemOutput = null;
    DataManagementService dmService = 
      DataManagementService.getService(Session.getConnection());
    try
    {
      DataManagement dataManagement = new DataManagement(session);
      Query query = new Query(session);
     // Workflow workflow = new Workflow(session);

      if ((designDataMessage.getProjectId() == null) || (designDataMessage.getProjectId().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("工程代号需要填写！");
        return returnVal;
      }
      if ((designDataMessage.getLetterSendNo() == null) || (designDataMessage.getLetterSendNo().equals(""))) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("编号需要填写！");
        return returnVal;
      }
      TC_Project project = query.queryProjectExist(designDataMessage.getProjectId());
      if (project == null) {
        returnVal.setReturnCode("INT_1");
        returnVal.setReturnMessage("项目号" + designDataMessage.getProjectId() + "不存在！");
        return returnVal;
      }
      
      ModelObject findChangeRqRevision = query.queryDesignRevision(designDataMessage.getProjectId(), designDataMessage.getLetterSendNo());
     // ModelObject object= query.queryAP1000ICMRevision(iitfDataRequest.getProjectid(), iitfDataRequest.getIntfcIdent());

      if (findChangeRqRevision == null) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("IITF数据在TC中不存在符合条件的IITF：" + designDataMessage.getProjectId() + "|" + designDataMessage.getLetterSendNo() );
        return returnVal;
      }
      if ((designDataMessage.getSendLotDate() != null) && (!designDataMessage.getSendLotNo().equals(""))) {
          if ((designDataMessage.getSendLotDate() == null) || (designDataMessage.getSendLotDate().equals(""))) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("发送供应商日期需要填写！");
            return returnVal;
          }


          String[] names = { "cn9LotSendNo", "cn9LotSendDate" };
          String closeDateStr = designDataMessage.getSendLotDate();
          if (!closeDateStr.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
            Date closeDate = dateFormat.parse(closeDateStr);

            closeDateStr = dateFormat2.format(closeDate) + "T" + dateFormat3.format(closeDate);
          }
          String[] values = { designDataMessage.getSendLotNo(), closeDateStr };
          String setValueReturn = dataManagement.setObjectProperties(findChangeRqRevision, names, values);
          if (!setValueReturn.equals("")) {
            returnVal.setReturnCode("INT_2");
            returnVal.setReturnMessage("更新CR单：" + setValueReturn);
            return returnVal;
          }

          dmService.refreshObjects(new ModelObject[] { findChangeRqRevision });

          returnVal.setReturnCode("INT_0");
          returnVal.setReturnMessage("");
          return returnVal;
        }
     
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e);

      if (createItemOutput != null) {
        try
        {
          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

          String ret = "";
          String[] msgs = serviceData.getPartialError(0).getMessages();

          for (int i = 0; i < msgs.length; i++) {
            ret = ret + msgs[i] + " ";
          }

          System.out.println(ret);
          logger.info(ret);
        }
        catch (Exception e2) {
          e2.printStackTrace();
          logger.error(e2);
        }
      }

      returnVal.setReturnCode("INT_2");
      returnVal.setReturnMessage("异常:" + e.getMessage());
      return returnVal;
    }

   
    returnVal.setReturnCode("INT_0");
    returnVal.setReturnMessage("");

    String end = df.format(new Date());
    System.out.println("TA结束时间：" + end);
    logger.info("-----------结束时间：" + end + "------------");
    logger.info("-----------end setTAData------------");
    return returnVal;
}

public ReturnVal setResultDare(ResultMessage resultMessage) {
	ReturnVal returnVal = new ReturnVal();
    if (session == null)
    {
      boolean flag = loginTC();
      if (!flag) {
        returnVal.setReturnCode("INT_2");
        returnVal.setReturnMessage("登陆失败");
        return returnVal;
      }

    }
    
    logger.info("-----------begin setCRData------------");

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String start = df.format(new Date());
    logger.info("-----------开始时间：" + start + "------------");
    System.out.println("Result开始时间：" + start);

    CreateItemsOutput createItemOutput = null;
    DataManagementService dmService = 
      DataManagementService.getService(Session.getConnection());
   try {
	   DataManagement dataManagement = new DataManagement(session);
	    Query query = new Query(session);
	    
	    if ((resultMessage.getProjectId() == null) || (resultMessage.getProjectId().equals(""))) {
	        returnVal.setReturnCode("INT_2");
	        returnVal.setReturnMessage("工程代号需要填写！");
	        return returnVal;
	      }
	    
	    TC_Project project = query.queryProjectExist(resultMessage.getProjectId());
	      if (project == null) {
	        returnVal.setReturnCode("INT_1");
	        returnVal.setReturnMessage("项目号" + resultMessage.getProjectId() + "不存在！");
	        return returnVal;
	      }
	      ModelObject findRevision=null;
	      if("CN9FileTFRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryFile2Revision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9FaxRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryFaxRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9IICSRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryIICSRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9IITFRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryIITFRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9IICSAP1000Revision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryAP1000IITFRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9IITFAP1000Revision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryAP1000IITFRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9TARevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryTARevisionByLetterNo(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo(),"A");
	      }else if("CN9ChangeRqRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryChangeRqRevisionByLetterNo2(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo(),"A");
	      }else if("CN9DesignEnvNotiRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryDENRevision2(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo(),"A");
	      }else if("CN9DesignReviewFRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryDesignRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9FUNotifyRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryFUNotifyRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo());
	      }else if("CN9NonConformaRevision".equals(resultMessage.getType())){
	    	  findRevision=  query.queryNonConformaRevision(resultMessage.getProjectId(), resultMessage.getCorrespLetterRecNo(),resultMessage.getRev());
	      }
	      else{
	    	  returnVal.setReturnCode("INT_1");
		        returnVal.setReturnMessage("没有找到退回的对象");
		        return returnVal;
	      }
	      
	      if(findRevision!=null){
	    	  String[] names = { "cn9ResultContent", "cn9ResultUserId" };
	          String content =resultMessage.getCotnent();
	          String userId=resultMessage.getUserid();
	          String[] values = { content, userId };
	          String setValueReturn = dataManagement.setObjectProperties(findRevision, names, values);
	          if (!setValueReturn.equals("")) {
	            returnVal.setReturnCode("INT_2");
	            returnVal.setReturnMessage("更新IICS：" + setValueReturn);
	            return returnVal;
	          }

	          dmService.refreshObjects(new ModelObject[] { findRevision });
	          
	          String docManagerUserId = dataManagement.getUserFrom(resultMessage.getProjectId());
	          
	          System.out.println("docManagerUserId:"+docManagerUserId);
	          User user=(User)query.queryPerson(docManagerUserId);
	          System.out.println("user.get_home_folder():"+user.get_home_folder());
	      		Folder parentFolder =user.get_home_folder();
	      		dmService.getProperties(new ModelObject[] { parentFolder}, new String[] { "contents" });
	      	//System.out.println(parentFolder.get_current_name());
	      		WorkspaceObject[] wobejcts=	parentFolder.get_contents();
	      		boolean flag=false;
	      		for (int i = 0; i < wobejcts.length; i++) {
	      		ModelObject model= wobejcts[i];
	      		dmService.getProperties(new ModelObject[] { model}, new String[] { "current_name" });
	      		System.out.println(model.getPropertyDisplayableValue("current_name"));
	      		if("接口退回文件".equals(model.getPropertyDisplayableValue("current_name")))
	      		{
	      			//Folder parent=(Folder)model;
	      		  dataManagement.addRelation(model,findRevision, "contents");
	      		flag=true;
	      		break;
	      		}
	      	}
	      		if(!flag){
	      			 returnVal.setReturnCode("INT_1");
	   	          returnVal.setReturnMessage("用户没有退回的文件夹");
	   	          return returnVal;
	      		}
	          
	          
	          returnVal.setReturnCode("INT_0");
	          returnVal.setReturnMessage("");
	          return returnVal;
	      }else{
	    	  returnVal.setReturnCode("INT_1");
		        returnVal.setReturnMessage("没有找到退回的对象");
		        return returnVal;
	      }
	      
	      
	      
	} catch (Exception e) {
		// TODO: handle exception
	}
    
	return null;
}





 
}
