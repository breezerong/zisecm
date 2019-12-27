package org.zisecm.jobs.business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.UserService;
import com.ecm.icore.service.IEcmSession;
@Service
public class BackupService {
	 	@Autowired
	    private DocumentService documentService;
	 	@Autowired
	    private ContentService contentService;
	 	
	    @Autowired
		private AuthService authService;
	    @Autowired
		private Environment env;
	    @Autowired
	    private UserService userService;
	    @Scheduled(cron = "0 0/30 * * * ?")
	    public void backup() {
	    	System.out.println("-----"+new Date()+"-------job开始---------");
	    	IEcmSession ecmSession = null;
			String workflowSpecialUserName = env.getProperty("ecm.username");
			try {
				ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
				List<Map<String,Object>> objs= documentService.getObjectMap(ecmSession.getToken(), "TYPE_NAME='备份批次'  and STATUS='制作中'");
				for(int i=0;i<objs.size();i++) {
					Map<String,Object> obj=objs.get(i);
					float totalSize=Float.parseFloat(obj.get("C_PAGE_COUNT").toString()) ;
					float sumsize=0;
					String coding=obj.get("CODING").toString();
					String condition= obj.get("C_DESC1").toString();//查询条件
					String path= obj.get("TITLE").toString();//路径
					List<Map<String, Object>> boxes= 
							documentService.getObjectMap(ecmSession.getToken(), " TYPE_NAME='卷盒'  and "+condition+" order by coding");
					int recordNum=1;
					String recordNumStr=String.format("%04d", recordNum);
					String recordCoding=coding+"-"+recordNumStr;
					EcmDocument ecmDoc=new EcmDocument();
					ecmDoc.setCoding(recordCoding);
					ecmDoc.addAttribute("C_BATCH_CODE", coding);
					
					ecmDoc.setName(obj.get("NAME").toString());
					ecmDoc.setTitle(obj.get("TITLE").toString()+"/"+obj.get("CODING")+"/"+recordNumStr);
					ecmDoc.setStatus("制作中");
					ecmDoc.setTypeName("备份记录");
					String ecmDocId= documentService.newObject(ecmSession.getToken(), ecmDoc);
					for(int j=0;j<boxes.size();j++) {
						try {
							Map<String, Object> box= boxes.get(j);
							float f= getBoxsize(box.get("ID").toString(),ecmSession.getToken());
							if(totalSize-sumsize>f) {
								sumsize+=f;
								//查询盒中所有的content
								
								String sql="select b.CODING,a.* from ecm_content a,ecm_document b,( " + 
										"select a.CHILD_ID from ecm_relation a ,( " + 
										"select CHILD_ID from ecm_relation where PARENT_ID='"+box.get("ID").toString()+"' " + 
										")b where a.PARENT_ID=b.CHILD_ID " + 
										"union " + 
										"select CHILD_ID from ecm_relation where PARENT_ID='"+box.get("ID").toString()+"' " + 
										")t where a.parent_id=b.id and a.parent_id=t.CHILD_ID and b.id=t.CHILD_ID " ;
								List<Map<String, Object>> contents= documentService.getMapList(ecmSession.getToken(), sql);
								//开始导出文件
								for(Map<String,Object> c:contents) {
									InputStream fis = null;
									String fullPath = CacheManagerOper.getEcmStores().get(c.get("STORE_NAME").toString()).getStorePath();
									File fil = new File(fullPath+c.get("FILE_PATH").toString());
									fis = new BufferedInputStream(new FileInputStream(fil));
									byte[] data=new byte[ fis.available()];
									
									
									File outFile=new File(obj.get("TITLE").toString()
											+"/"+obj.get("CODING")+"/"+recordNumStr+"/"+c.get("CODING")+"."+c.get("FORMAT_NAME"));

									if (!outFile.exists()) {
										try {
											boolean fc= outFile.createNewFile();
											
									    } catch (IOException e) {
									        // TODO Auto-generated catch block
									    	File dir=new File(obj.get("TITLE").toString()
													+"/"+obj.get("CODING")+"/"+recordNumStr);
											dir.mkdirs();
											outFile.createNewFile();
//									        e.printStackTrace();
									    }

							        }
									OutputStream out=new BufferedOutputStream(new FileOutputStream(outFile));
									out.write(data);
									out.flush();
									out.close();
									fis.close();
									
								}
								
								
							}else {
								//创建一条新的记录
								recordNum++;
								sumsize=0;
								EcmDocument backupBach= documentService.getObjectById(ecmSession.getToken(), ecmDocId);
								backupBach.setStatus("已完成");
								documentService.updateObject(ecmSession.getToken(), backupBach,null);
								recordNumStr=String.format("%04d", recordNum);
								recordCoding=coding+"-"+recordNumStr;
								EcmDocument ecmDocNew=new EcmDocument();
								ecmDocNew.setCoding(recordCoding);
								ecmDocNew.addAttribute("C_BATCH_CODE", coding);
								
								ecmDocNew.setName(obj.get("NAME").toString());
								ecmDocNew.setTitle(obj.get("TITLE").toString()+"/"+obj.get("CODING")+"/"+recordNumStr);
								ecmDocNew.setStatus("制作中");
								ecmDocNew.setTypeName("备份记录");
								ecmDocId= documentService.newObject(ecmSession.getToken(), ecmDocNew);
								j--;
							}
						}catch (Exception e) {
							// TODO: handle exception
							continue;
						}
					}
					
					documentService.updateStatus(ecmSession.getToken(), obj.get("ID").toString(), "已完成");
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(ecmSession!=null) {
					authService.logout(ecmSession.getToken());
				}
			}
			System.out.println("-----------"+new Date()+"--------job end-----------------");
	    }
	    
	    public float getBoxsize(String boxId,String token) {
	    	String sql="select sum(CONTENT_SIZE) as boxsize from ecm_content where PARENT_ID in("+
					"(select a.CHILD_ID from ecm_relation a ,("+
					"select CHILD_ID from ecm_relation where PARENT_ID='"+boxId+"'"+
					")b where a.PARENT_ID=b.CHILD_ID"+
					" union "+
					"select CHILD_ID from ecm_relation where PARENT_ID='"+boxId+"'))";
			try {
					List<Map<String,Object>> objs= documentService.getMapList(token, sql);
					if(objs!=null&&objs.size()>0&&objs.get(0)!=null) {
						Map<String,Object> boxObj= objs.get(0);
						
						float f= Float.parseFloat(boxObj.get("boxsize").toString());
						f=f/1024/1024;
						return f;
					}
				}catch (Exception e) {
					// TODO: handle exception
					return 0;
				}
			return 0;
	    }
	    
}
