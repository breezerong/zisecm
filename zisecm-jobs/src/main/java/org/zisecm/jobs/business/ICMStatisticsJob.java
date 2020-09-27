package org.zisecm.jobs.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.EcmException;
import com.ecm.icore.service.IEcmSession;

@Service
public class ICMStatisticsJob extends BaseJob {

	
	public void run () {
		IEcmSession session = this.login();
		
		List<Map<String, Object>> list = this.getICMObjects(session);
		if(list.size()>0) {
			for (Map<String, Object> obj : list) {
				EcmDocument doc = getDocumentService().getObjectById(session.getToken(), obj.get("ID").toString());
				String fristVersionStatus = obj.get("fristVersionStatus")==null?null:obj.get("fristVersionStatus").toString();
				String finalVersionOpenStatus = obj.get("finalVersionOpenStatus")==null?null:obj.get("finalVersionOpenStatus").toString();
				String fristVersionCloseStatus = obj.get("fristVersionCloseStatus")==null?null:obj.get("fristVersionCloseStatus").toString();
				String finalVersionCloseStatus = obj.get("finalVersionCloseStatus")==null?null:obj.get("finalVersionCloseStatus").toString();
				
				doc.addAttribute("C_ITEM_STATUS6", fristVersionStatus);
				doc.addAttribute("C_ITEM_STATUS7", finalVersionOpenStatus);
				doc.addAttribute("C_ITEM_STATUS9", fristVersionCloseStatus);
				doc.addAttribute("C_ITEM_STATUS10", finalVersionCloseStatus);
				
				Map<String, Object> cdd = null;
				Map<String, Object> yjd = null;
				cdd =getSubObj(obj,"接口信息传递单",session.getToken());
				if(cdd!=null) {
					Date replyPlanDate = cdd.get("C_REPLY_PLAN_DATE")==null?null:getDate(cdd.get("C_REPLY_PLAN_DATE").toString());
					yjd = getSubObj(obj,"接口信息意见单",session.getToken());
					if(yjd==null ) {
						if(replyPlanDate!=null) {
							Date now = new Date();
							if(replyPlanDate.compareTo(now)>=0) {
								doc.addAttribute("C_ITEM_STATUS8", "到期未回复");
							}else {
								doc.addAttribute("C_ITEM_STATUS8", "未到期");
							}
						}
					}else {						
						if(replyPlanDate!=null) {							
							Date repeatDate = getDate(yjd.get("CREATION_DATE").toString());
							if(repeatDate.compareTo(replyPlanDate)>=0) {
								doc.addAttribute("C_ITEM_STATUS8", "延期已回复");
							}else {
								doc.addAttribute("C_ITEM_STATUS8", "按期已回复");
							}
						}
					}
				}else {
					doc.addAttribute("C_ITEM_STATUS8", null);
				}
				System.out.println("===================================================================");
				
				try {
					getDocumentService().creatOrUpdateObject(session.getToken(), doc, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	private Date getDate(String source) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Map<String, Object> getSubObj(Map<String, Object> icmObj,String typeName,String token) {
		Map<String, Object> result = null;
		StringBuffer sql = new StringBuffer("select * from ecm_document where ");
		sql.append("TYPE_NAME='"+typeName+"' ");
		
		if(icmObj.get("C_CODE1")==null) {
			return null;
		}
		
		for (int i = 1; i <= 6; i++) {
			sql.append("and C_CODE"+i+"='"+icmObj.get("C_CODE"+i).toString()+"' ");
		}
		 try {
			 List<Map<String, Object>>  list = this.getDocumentService().getMapList(token, sql.toString());
			 if(list!=null && list.size()>0) {
				 return list.get(0);
			 }
		} catch (EcmException e) {
			e.printStackTrace();
		}
		 return result;
	}
	
	
	private List<Map<String, Object>> getICMObjects(IEcmSession session){
		List<Map<String, Object>> list = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ID,TYPE_NAME,C_CODE1,C_CODE2,C_CODE3,C_CODE4,C_CODE5,C_CODE6,");
			sql.append("'fristVersionStatus' =case  when C_EX1_DATE is null and C_EX6_DATE<getdate()  then '到期未打开' " +
					"when C_EX1_DATE is not null and  C_EX1_DATE >C_EX6_DATE  then '延期已打开' " +
					"when C_EX1_DATE is not null and  C_EX1_DATE<C_EX6_DATE then '按期已打开' " +
					"when C_EX1_DATE is null and  C_EX6_DATE>=getdate() then '未到期' end,");
			sql.append("'finalVersionOpenStatus' =case when C_EX3_DATE is null and  C_EX7_DATE<getdate() then '到期未打开' " + 
					"when C_EX3_DATE is not null and  C_EX3_DATE >C_EX7_DATE then '到期未打开' " + 
					"when C_EX3_DATE is not null and C_EX3_DATE <C_EX7_DATE then '按期已打开' " + 
					"when C_EX3_DATE is null and  C_EX7_DATE>=getdate() then '未到期' end,");
			sql.append("'fristVersionCloseStatus' =case when C_EX2_DATE is null and C_ITEM3_DATE<getdate()  then '到期未关闭' " + 
					"when C_EX2_DATE is null and C_ITEM3_DATE>=getdate()  then '未到期' " + 
					"when C_EX2_DATE is not null and  C_EX2_DATE >C_ITEM3_DATE then '到期未关闭' " + 
					"when C_EX2_DATE is not null and C_EX2_DATE <C_ITEM3_DATE then '按期已关闭 ' end,");
			sql.append("'finalVersionCloseStatus' =case when C_EX4_DATE is null and C_ITEM6_DATE<getdate()  then '到期未关闭' " + 
					"when C_EX4_DATE is null and C_ITEM6_DATE>=getdate()  then '未到期' " + 
					"when C_EX4_DATE is not null and  C_EX4_DATE >C_ITEM6_DATE then '到期未关闭' " + 
					"when C_EX4_DATE is not null and C_EX4_DATE <C_ITEM6_DATE then '按期已关闭' end ");
			sql.append("from ecm_document where TYPE_NAME ='ICM'");
			list = this.getDocumentService().getMapList(session.getToken(), sql.toString());
		} catch (EcmException e) {
			list = new ArrayList<Map<String, Object>>();
			e.printStackTrace();
		} 
		return list;
	}
	
}
