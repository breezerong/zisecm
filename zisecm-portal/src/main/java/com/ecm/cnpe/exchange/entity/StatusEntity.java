package com.ecm.cnpe.exchange.entity;

import java.util.HashMap;
import java.util.Map;

public class StatusEntity {
	private static Map<String,String[]> dcStatus=new HashMap<>();
	private static Map<String,String[]> dcStatusNoConfirming=new HashMap<>();
	private static Map<String,String[]> dcStatusCnpeSend=new HashMap<>();
	
	public static Map<String,String> actions=new HashMap<>();
	public static Map<String,String> actionsCnpe=new HashMap<>();
	
	private static Map<String,String[]> iedStatus=new HashMap<>(); 
	
	private static String typeNames="文件传递单,FU通知单,作废通知单,CR澄清要求答复单,FCR现场变更答复单,"
			+ "NCR不符合项报告答复单,DCR设计变更答复单,TCR试验澄清答复单,接口信息意见单,接口信息传递单"
			+ "DEN设计变更通知单,图文传真,会议纪要,设计审查意见答复,接口延误反馈";
	private static String noConfigTypeNames="FU申请,CR澄清要求申请单,CR澄清要求关闭单,"
			+ "FCR现场变更申请单,FCR现场变更关闭单,NCR不符合项报告单,NCR不符合项报告关闭单,"
			+ "DCR设计变更申请单,DCR设计变更关闭单,TCR试验澄清申请单,TCR试验澄清关闭单,DEN设计变更关闭单,设计审查意见";
	static {
		//
		dcStatus.put("新建", new String[] {"","待确认"});
		dcStatus.put("待确认", new String[] {"驳回","已确认"});
		dcStatus.put("已确认", new String[] {"驳回","已同步"});
		dcStatus.put("已同步", new String[] {"驳回",""});
		dcStatus.put("驳回", new String[] {"","待确认"});
		//
		dcStatusNoConfirming.put("新建", new String[] {"","已确认"});
		dcStatusNoConfirming.put("已确认", new String[] {"驳回","已同步"});
		dcStatusNoConfirming.put("已同步", new String[] {"驳回",""});
		dcStatusNoConfirming.put("驳回", new String[] {"","已确认"});
		//
		dcStatusCnpeSend.put("新建", new String[] {"","待接收"});
		dcStatusCnpeSend.put("待接收", new String[] {"驳回","已接收"});
		dcStatusCnpeSend.put("已接收", new String[] {"驳回",""});
		dcStatusCnpeSend.put("驳回", new String[] {"","待接收"});
		
		//ied
		iedStatus.put("新建", new String[] {"","审核中"});
		iedStatus.put("审核中", new String[] {"已驳回","已生效"});
		iedStatus.put("已驳回", new String[] {"","审核中"});
		iedStatus.put("变更中", new String[] {"","已变更"});
		
		actionsCnpe.put("待确认", "提交");
		actionsCnpe.put("已确认", "CNPE接收");
		actionsCnpe.put("驳回","CNPE驳回");
		
		actions.put("待接收", "分发");
		actions.put("已接收", "分包商接收");
		actions.put("驳回","分包商驳回");
	}
	public static String getNextDcStatusValue(String key,String typeName,boolean isCnpeSend) {
		/**
		 * 设计文件,文件传递单,FU申请,FU通知单,作废通知单,
			CR澄清要求申请单,CR澄清要求答复单,CR澄清要求关闭单,
			FCR现场变更申请单,FCR现场变更答复单,FCR现场变更关闭单,
			NCR不符合项报告单,NCR不符合项报告答复单,NCR不符合项报告关闭单,
			DCR设计变更申请单,DCR设计变更答复单,DCR设计变更关闭单,
			TCR试验澄清申请单,TCR试验澄清答复单,TCR试验澄清关闭单,DEN设计变更通知单,
			DEN设计变更关闭单,图文传真,会议纪要,设计审查意见
		 */
		if(isCnpeSend) {
			String[] obj=dcStatusCnpeSend.get(key);
			if(obj!=null) {
				return obj[1];
			}
			return null;
			
		}
		if(noConfigTypeNames.contains(typeName)) {
			String[] obj=dcStatusNoConfirming.get(key);
			if(obj!=null) {
				return obj[1];
			}
			return null;
		}
//		String typeNames="文件传递单,FU申请,FU通知单,作废通知单,接口信息意见单,"
//				+ "接口信息传递单,NCR不符合项报告单,NCR不符合项报告答复单,"
//				+ "NCR不符合项报告关闭单,DCR设计变更申请单,DCR设计变更答复单,DCR设计变更关闭单,"
//				+ "TCR试验澄清申请单,TCR试验澄清答复单,TCR试验澄清关闭单,DEN设计变更通知单,"
//				+ "DEN设计变更通知关闭单,图文传真,会议纪要,设计审查意见,设计审查意见答复";
		
		if(typeNames.contains(typeName)) {
			String[] obj=dcStatus.get(key);
			if(obj!=null) {
				return obj[1];
			}
			return null;
		}
		
		if("IED".contains(typeName)) {
			String[] obj=iedStatus.get(key);
			if(obj!=null) {
				return obj[1];
			}
			return null;
		}
		
		return null;
		
	}
	public static String getPreviousDcStatusValue(String key,String typeName,boolean isCnpeSend) {

		
		if(isCnpeSend) {
			String[] obj=dcStatusCnpeSend.get(key);
			if(obj!=null) {
				return obj[0];
			}
			return null;
			
		}
		if(noConfigTypeNames.contains(typeName)) {
			String[] obj=dcStatusNoConfirming.get(key);
			if(obj!=null) {
				return obj[0];
			}
			return null;
		}
//		String typeNames="文件传递单,FU申请,FU通知单,作废通知单,接口信息意见单,"
//				+ "接口信息传递单,NCR不符合项报告单,NCR不符合项报告答复单,"
//				+ "NCR不符合项报告关闭单,DCR设计变更申请单,DCR设计变更答复单,DCR设计变更关闭单,"
//				+ "TCR试验澄清申请单,TCR试验澄清答复单,TCR试验澄清关闭单,DEN设计变更通知单,"
//				+ "DEN设计变更通知关闭单,图文传真,会议纪要,设计审查意见,设计审查意见答复";
		
		if(typeNames.contains(typeName)) {
			String[] obj=dcStatus.get(key);
			if(obj!=null) {
				return obj[0];
			}
			return null;
		}
		
		if("IED".contains(typeName)) {
			String[] obj=iedStatus.get(key);
			if(obj!=null) {
				return obj[0];
			}
			return null;
		}
		
		return null;
		
	
		
	}
}
