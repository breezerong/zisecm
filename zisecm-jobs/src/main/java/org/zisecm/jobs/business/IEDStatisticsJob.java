package org.zisecm.jobs.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.EcmException;
import com.ecm.icore.service.IEcmSession;

@Service
public class IEDStatisticsJob extends BaseJob {

	
	public void run () {
		IEcmSession session = this.login();
		String token = session.getToken();
		List<Map<String,Object>> list = this.getRecord(session);
		if(list.size()>0) {
			for (Map<String,Object> docObj : list) {
				String id = docObj.get("ID").toString();
				EcmDocument doc = this.getDocumentService().getObjectById(token, id);
				double recordCount = Double.valueOf(docObj.get("record_count").toString()).doubleValue();
				double revisionCount = Double.valueOf(docObj.get("revision_count").toString()).doubleValue();
				DecimalFormat df = new DecimalFormat("#.0000");
				if(revisionCount<1) {
					double result = 0;
					doc.addAttribute("C_DOUBLE1", Double.valueOf(df.format(result)));
				}else {
					double percent = revisionCount/recordCount;
					doc.addAttribute("C_DOUBLE1", Double.valueOf(df.format(percent)));
				}
				try {
					this.getDocumentService().creatOrUpdateObject(token, doc, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	
	}
	
	private List<Map<String,Object>> getRecord(IEcmSession session){
		List<Map<String, Object>> list = null;
		String sql = "SELECT * from " + 
				"(select ID,CODING,(select count(*) from ecm_document  where TYPE_NAME ='IED' and C_FROM_CODING =ed.CODING and IS_CURRENT =1 ) as record_count, " + 
				"(select count(REVISION ) from ecm_document  where TYPE_NAME ='IED' and C_FROM_CODING =ed.CODING and IS_CURRENT =1 ) as revision_count " + 
				"from ecm_document ed where ed.TYPE_NAME ='IED' and ed.IS_CURRENT =1 and ed.SUB_TYPE='图册') f " + 
				"where record_count >0";
		try {
			list = this.getDocumentService().getMapList(session.getToken(), sql);
		} catch (EcmException e) {
			e.printStackTrace();
			list = new ArrayList<Map<String,Object>>();
		}
		return list;
	}


	
}
