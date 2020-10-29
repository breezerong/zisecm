package org.zisecm.jobs.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.bean.conf.ConfBean;
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.bean.conf.SubTable;
import org.zisecm.jobs.tc.service.SyncFromTcService;

@Service
public class SyncFromTc {
	@Autowired
	private SyncFromTcService syncFromTcService;
	@Scheduled(cron="${cron.downfromtc}")
	public void run() {
		try {
			List<ConfBean> cfs=Operator.getMainBeans();
			for(int i=0;i<cfs.size();i++) {
				ConfBean cfb=cfs.get(i);
				List<SubTable> downRelations=cfb.getDownrelationShips();
				if(downRelations==null||downRelations.size()==0) {
					continue;
				}
				syncFromTcService.getTcData(cfb);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
