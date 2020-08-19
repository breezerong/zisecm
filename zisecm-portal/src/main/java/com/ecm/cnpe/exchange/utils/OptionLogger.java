package com.ecm.cnpe.exchange.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ecm.cnpe.exchange.entity.StatusEntity;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.service.ExcSynDetailService;

public class OptionLogger {
	public static void logger(ExcSynDetailService detailService,EcmDocument doc,String actionName,String companyName) {
		ExcSynDetail detail=new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(actionName);
		
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStauts(doc.getStatus());
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		detailService.newObject(detail);
	}
	
	public static void logger(ExcSynDetailService detailService,EcmDocument doc,String companyName) {
		ExcSynDetail detail=new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(StatusEntity.actionsCnpe.get(doc.getStatus()));
		
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStauts(doc.getStatus());
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		detailService.newObject(detail);
	}
	public static void loggerUser(ExcSynDetailService detailService,EcmUser user,
			String companyName,String ActionName) {
		ExcSynDetail detail=new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(ActionName);
		
		detail.setFromId(user.getId());
		detail.setCreationDate(new Date());
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(user.getCompanyName()!=null?user.getCompanyName():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		detailService.newObject(detail);
	}
	/**
	 * 
	 * @param detailService
	 * @param user
	 * @param groupName
	 * @param companyName
	 * @param ActionName
	 */
	public static void loggerGroup(ExcSynDetailService detailService,EcmUser user,String groupName,
			String companyName,String ActionName) {
		ExcSynDetail detail=new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(ActionName);
		
		detail.setFromId(user.getLoginName());
		detail.setToId(groupName);
		detail.setCreationDate(new Date());
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(user.getCompanyName()!=null?user.getCompanyName():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		detailService.newObject(detail);
	}
	public static void logger(ExcSynDetailService detailService,ExcTransfer doc,String companyName) {
		ExcSynDetail detail=new ExcSynDetail();
		detail.setAppName("DOCEX");
//		detail.setActionName(actionName);
		detail.setActionName(StatusEntity.actions.get(doc.getStauts()));
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStauts(doc.getStauts());
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		detailService.newObject(detail);
	}
	
	public static void logger(ExcSynDetailService detailService,ExcTransfer doc,String actionName,String companyName) {
		ExcSynDetail detail=new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(actionName);
//		detail.setActionName(StatusEntity.actions.get(doc.getStauts()));
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStauts(doc.getStauts());
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		detailService.newObject(detail);
	}
}
