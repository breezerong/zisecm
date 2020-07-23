package com.ecm.cnpe.exchange.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ecm.core.entity.EcmDocument;
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
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		detailService.newObject(detail);
	}
}
