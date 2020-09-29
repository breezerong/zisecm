package org.zisecm.jobs.business;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.config.EcmConfig;
import org.zisecm.jobs.entity.WsEntity;
import org.zisecm.jobs.entity.WsService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnpe.p6.activityservice.Activity;
import com.cnpe.p6.projectservice.Project;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.ExcSynBatch;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynBatchService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.icore.service.IEcmSession;

/**
 * 
 * @Title:
 * @author Chen Shuo
 * @date:  2020年7月27日 下午4:13:44
 * @Description
 */
@Service
public class SyncWbs {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ExcSynBatchService excSynBatchService;
	
	@Autowired
	private ExcSynDetailService excSynDetailService;
	
	@Autowired
	private P6SyncService p6Service;

	@Autowired
	private EcmConfig ecmConfig;
	
	@Scheduled(cron = "${cron.syncwbs}")
	public void run () {
		
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = ecmConfig.getUsername();		
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, ecmConfig.getPassword());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		List<ExcSynBatch> synBatchList = excSynBatchService.getByCondition(" APP_NAME='P6' and STATUS='新建'");
		for (ExcSynBatch excSynBatch : synBatchList) {
			Map<String,Object> syncProjectInfo = documentService.getObjectMapById(ecmSession.getToken(), excSynBatch.getBatchNum());
			String projectId = syncProjectInfo.get("CODING").toString();
			String projectName = syncProjectInfo.get("C_PROJECT_NAME").toString();
			int newCount = 0;
			int updateCount = 0;
			int failCount = 0;
			
			Project project = p6Service.getProjectInfo(projectId);
			excSynBatch.setExecuteDate(new Date());
			if(project==null) {
				excSynBatch.setStatus("错误");
				excSynBatch.setNewCount(newCount);
				excSynBatch.setUpdateCount(updateCount);
				excSynBatch.setFailCount(1);
				excSynBatchService.updateObject(excSynBatch);
				ExcSynDetail detail =  new ExcSynDetail();
				detail.setBatchNum(excSynBatch.getBatchNum());
				detail.setAppName("P6");
				detail.setActionName("同步");
				detail.setStatus("错误");
				detail.setErrorMessage("项目编号 "+projectId+" 在P6中不存在");
				excSynDetailService.newObject(detail);
			}else {
				List<Activity> actList = p6Service.getActivityList(projectId);
				List<Map<String,Object>> wbsList = p6Service.getWbsList(projectId, actList);
				
				if(actList!=null && actList.size()>0) {
					for (Activity activity : actList) {
						String condition ="TYPE_NAME='计划任务' AND SUB_TYPE='Activity' AND SYN_ID='"+activity.getId()+"'";
						List<EcmDocument> list = null;
						try {
							list = documentService.getObjects(ecmSession.getToken(), condition);
						} catch (EcmException | SqlDeniedException e) {
							e.printStackTrace();
						}
						EcmDocument actDocObj = null;
						if(list== null || list.size()<1) {
							actDocObj = new EcmDocument();
							actDocObj.setTypeName("计划任务");
							actDocObj.setName(activity.getName());
							actDocObj.setSubType("Activity");
							
							actDocObj.addAttribute("SYN_ID", activity.getId());
							actDocObj.addAttribute("C_TYPE1", "99");
							
							actDocObj.addAttribute("C_PROJECT_CODE", projectId);
							actDocObj.addAttribute("C_PROJECT_NAME", projectName);
							XMLGregorianCalendar datevalue = activity.getStartDate();
							if(this.getDateObj(datevalue) !=null) {
								actDocObj.addAttribute("C_ITEM1_DATE", this.getDateObj(datevalue));
							}
							
							datevalue = activity.getFinishDate();
							if(this.getDateObj(datevalue) !=null) {
								actDocObj.addAttribute("C_ITEM2_DATE", this.getDateObj(datevalue));
							}
							
							actDocObj.addAttribute("C_WBS_CODING", activity.getWBSPath());
							
							try {
								documentService.creatOrUpdateObject(ecmSession.getToken(), actDocObj, null);
								newCount++;
							} catch (Exception e) {
								e.printStackTrace();
								failCount++;
							}
						}else {
							actDocObj = list.get(0);
							actDocObj.addAttribute("C_PROJECT_CODE", projectId);
							actDocObj.addAttribute("C_PROJECT_NAME", projectName);
							XMLGregorianCalendar datevalue = activity.getStartDate();
							if(this.getDateObj(datevalue) !=null) {
								actDocObj.addAttribute("C_ITEM1_DATE", this.getDateObj(datevalue));
							}
							
							datevalue = activity.getFinishDate();
							if(this.getDateObj(datevalue) !=null) {
								actDocObj.addAttribute("C_ITEM2_DATE", this.getDateObj(datevalue));
							}
							
							actDocObj.addAttribute("C_WBS_CODING", activity.getWBSPath());
							try {
								documentService.creatOrUpdateObject(ecmSession.getToken(), actDocObj, null);
								updateCount++;
							} catch (Exception e) {
								e.printStackTrace();
								failCount++;
							}
						}
					}
				}
				
				if(wbsList!=null && wbsList.size()>0) {

					for (Map<String, Object> wbs : wbsList) {
						String condition ="TYPE_NAME='计划任务' AND SUB_TYPE='WBS' AND SYN_ID='"+wbs.get("OBJECT_ID").toString()+"'";
						List<EcmDocument> list = null;
						try {
							list = documentService.getObjects(ecmSession.getToken(), condition);
						} catch (EcmException | SqlDeniedException e) {
							e.printStackTrace();
						}
						EcmDocument wbsDocObj = null;
						if(list== null || list.size()<1) {
							wbsDocObj = new EcmDocument();
							wbsDocObj.setName(wbs.get("NAME").toString());
							String parent = wbs.get("PARENT_OBJECT_ID").toString().equals(project.getWBSObjectId().getValue()+"")?"":wbs.get("PARENT_OBJECT_ID").toString();
							wbsDocObj.addAttribute("C_IN_CODING", parent);
							wbsDocObj.setTypeName("计划任务");
							wbsDocObj.setSubType("WBS");
							
							String wbsCode = wbs.get("CODE").toString();
							wbsDocObj.addAttribute("C_WBS_CODING", wbsCode);
							wbsDocObj.addAttribute("C_PROJECT_CODE", projectId);
							wbsDocObj.addAttribute("C_PROJECT_NAME", projectName);
							wbsDocObj.addAttribute("SYN_ID", wbs.get("OBJECT_ID").toString());
							
							Date startDate = getActDate(actList, wbsCode,1);
							Date finishDate = getActDate(actList, wbsCode,2);
							if(startDate!=null) {
								wbsDocObj.addAttribute("C_ITEM1_DATE", startDate);
							}
							if(finishDate!=null) {
								wbsDocObj.addAttribute("C_ITEM2_DATE", finishDate);
							}
							
							try {
								documentService.creatOrUpdateObject(ecmSession.getToken(), wbsDocObj, null);
								newCount++;
							} catch (Exception e) {
								e.printStackTrace();
								failCount++;
							}
						}else {
							wbsDocObj = list.get(0);
							
							String wbsCode = wbs.get("CODE").toString();
							wbsDocObj.addAttribute("C_WBS_CODING", wbsCode);
							wbsDocObj.addAttribute("C_PROJECT_CODE", projectId);
							wbsDocObj.addAttribute("C_PROJECT_NAME", projectName);
							wbsDocObj.addAttribute("SYN_ID", wbs.get("OBJECT_ID").toString());
							
							Date startDate = getActDate(actList, wbsCode,1);
							Date finishDate = getActDate(actList, wbsCode,2);
							if(startDate!=null) {
								wbsDocObj.addAttribute("C_ITEM1_DATE", startDate);
							}
							if(finishDate!=null) {
								wbsDocObj.addAttribute("C_ITEM2_DATE", finishDate);
							}
							
							try {
								documentService.creatOrUpdateObject(ecmSession.getToken(), wbsDocObj, null);
								updateCount++;
							} catch (Exception e) {
								e.printStackTrace();
								failCount++;
							}
						}
					}
				}
				
				excSynBatch.setStatus("已同步");
				excSynBatch.setNewCount(newCount);
				excSynBatch.setUpdateCount(updateCount);
				excSynBatch.setFailCount(failCount);
				excSynBatchService.updateObject(excSynBatch);
				
				System.out.println(actList.size());
				System.out.println(wbsList.size());
			}
		}
		
		
	}
	
	/**
	 * 
	 * @Title:
	 * @author lsyl
	 * @date:  2020年9月7日 上午9:38:10
	 * @Description
	 * @param actList
	 * @param wbs
	 * @param type 1 startDate 2 finishDate
	 * @return
	 */
	private Date getActDate(List<Activity> actList,String wbs,int type) {
		Date date = null;
		for (Activity activity : actList) {
			if(activity.getWBSPath().equals(wbs)) {
				
				if(type==1) {
					Date cdate = this.getDateObj(activity.getBaseline1StartDate().getValue());
					if(date == null) {
						date = cdate;
					}else if(cdate.compareTo(date)<0) {
						date = cdate;
					}
				}
				if(type==2) {
					Date cdate = this.getDateObj(activity.getBaseline1FinishDate().getValue());
					if(date == null) {						
						date = cdate;
					}else if(date.compareTo(cdate)<0) {
						date = cdate;
					}
				}
			}
		}
		return date;
	}
	
	/**
	 * 
	 * @Title:读取服务定义文件
	 * @author Chen Shuo
	 * @date:  2020年7月29日 上午9:06:23
	 * @Description
	 * @return
	 */
	private static JSONObject loadMapperFile() {
		Resource rs = new ClassPathResource("webservice.json");
		String text;
		JSONObject jso=null;
		InputStream inputStream;
		try {
			inputStream = rs.getInputStream();
			text = IOUtils.toString(inputStream, "UTF-8");
			jso = JSON.parseObject(text);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return jso;
	}
	
	private static WsService loadService(JSONObject root,String serviceName) {
		JSONObject serviceObj = root.getJSONObject(serviceName);
		WsService ws = new WsService(serviceName);
		
		serviceObj.keySet().iterator().forEachRemaining(entityKey -> {			
			ws.getList().add(new WsEntity(entityKey, getServiceDefault(serviceObj, entityKey), getServiceMapper(serviceObj, entityKey).getInnerMap()));
		});
		System.out.println(JSON.toJSONString(ws));
		return ws;
	}
	
	/**
	 * 
	 * @Title: 获取服务对象中的映射内容
	 * @author Chen Shuo
	 * @date:  2020年7月29日 上午9:07:09
	 * @Description
	 * @param serviceObj
	 * @param entityKey
	 * @return
	 */
	private static JSONObject getServiceMapper(JSONObject serviceObj,String entityKey) {
		JSONObject obj = null;
		try {
			obj = serviceObj.getJSONObject(entityKey).getJSONObject("mapper");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	/**
	 * 
	 * @Title: 获取服务对象中的默认值
	 * @author Chen Shuo
	 * @date:  2020年7月29日 上午9:07:09
	 * @Description
	 * @param serviceObj
	 * @param entityKey
	 * @return
	 */
	private static Map<String, Object> getServiceDefault(JSONObject serviceObj,String mappername) {
		JSONObject obj = null;
		try {
			obj = serviceObj.getJSONObject(mappername).getJSONObject("default");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj.getInnerMap();
	}
	
	private void syncWbs(List<Map<String,Object>> wbsObjectList) {
		JSONObject root = loadMapperFile();
		if(root==null) {
			return;
		}
	}
	
	private String getDate(XMLGregorianCalendar value) {
		if(value == null) {
			return "";
		}
		return value.toString().replace("T", " ");
		
	}
	
	private Date getDateObj(XMLGregorianCalendar value) {
		if(value == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(value.toString().replace("T", " "));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
