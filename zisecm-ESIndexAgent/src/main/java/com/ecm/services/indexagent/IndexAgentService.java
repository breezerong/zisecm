package com.ecm.services.indexagent;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ecm.common.util.PasswordUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.exception.EcmException;
import com.ecm.core.search.ESClient;
import com.ecm.core.service.AuthService;


@Service
public class IndexAgentService{

	private final Logger logger = LoggerFactory.getLogger(IndexAgentService.class);
	private String token;
	private boolean isRunning = false;
	private boolean stopNow=false;
	
	@Value("${ecm.index.runtype}")
	private String  runType;

	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private IndexService indexService;
	
	/**
	 * 启动服务
	 * @throws EcmException
	 */
	@Scheduled(cron = "0/10 * * * * *")
	public void run(){
		if(!cacheLoaded()) {
			return;
		}
		if("0".endsWith(runType))
		{
			return;
		}
		if(stopNow || isRunning) {
			return;
		}
		
		if(token==null || authService.login(token)==null) {
			String user = null;
			try {
				user = CacheManagerOper.getEcmParameters().get("IndexUser").getValue();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return;
			}
			
			String password = CacheManagerOper.getEcmParameters().get("IndexPassword").getValue();
			
			try {
				token = authService.login(ActionContext.APP_INDEX_AGENT,user,password).getToken();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
			isRunning = true;
			RestHighLevelClient indexClient = ESClient.getInstance().getClient();
			try {
				indexService.indexDocumentByIdsFile(token,indexClient);
				if("1".endsWith(runType))
				{
					System.out.println("Reindex start......");
					indexService.reindexAll(token, indexClient);				
					System.out.println("Reindex completed......");
					runType = "2";
				}else {
					try {
						indexService.indexFromQueue(token, indexClient);					
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}finally {
				try {
					indexClient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		isRunning = false;
	}
	
	private boolean cacheLoaded() {
		if(CacheManagerOper.getEcmAttributes()==null ||CacheManagerOper.getEcmAttributes().size()==0)
		{
			return false;
		}
		return true;
	}
}
