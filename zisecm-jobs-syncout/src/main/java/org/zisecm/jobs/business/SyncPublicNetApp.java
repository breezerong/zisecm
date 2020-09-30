package org.zisecm.jobs.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;

@Service
public class SyncPublicNetApp {
	@Autowired
	SyncPublicNet syncPublicNet;

	@Scheduled(cron = "0/20 * * * * ?")
	public void run() {
		try {
			if(CacheManagerOper.getFinishLoadCacheTag()) {
				
			//			System.out.println(SyncPublicNetApp.class.getName());
			syncPublicNet.exportData("删除");
			syncPublicNet.exportData("升版");
			syncPublicNet.exportData("变更");	
			syncPublicNet.exportData("提交");
			syncPublicNet.exportData("新建");
			syncPublicNet.exportData("新建问题");
			syncPublicNet.exportData("CNPE驳回");
			syncPublicNet.exportData("CNPE接收");
			syncPublicNet.exportData("分发");
			syncPublicNet.exportData("分包商驳回");
			syncPublicNet.exportData("修改");
			syncPublicNet.exportData("驳回");
			syncPublicNet.exportData("接收");
			syncPublicNet.exportData("导出用户");
			syncPublicNet.exportData("回复问题");
			syncPublicNet.exportData("延误打开反馈");
			syncPublicNet.exportData("延误打开确认");
			syncPublicNet.exportData("延误关闭反馈");
			syncPublicNet.exportData("延误关闭确认");
			syncPublicNet.exportData("延误回复反馈");
			syncPublicNet.exportData("延误回复确认");
			
			syncPublicNet.exportData("申请驳回");
			syncPublicNet.exportData("确认驳回");
			syncPublicNet.exportData("拒绝驳回");
			syncPublicNet.exportData("延误回复反馈");
			syncPublicNet.exportData("延误回复确认");
			
			syncPublicNet.exportData("计划同步");
		
			syncPublicNet.importData("");
			
		
			syncPublicNet.UpdateImportResultStatus();//done
			};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
