package com.ecm.cnpe.exchange.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.ecm.cnpe.exchange.entity.StatusEntity;
import com.ecm.cnpe.exchange.service.impl.SynDetailService;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.ExcSynDetailService;

public class OptionLogger {
	/**
	 * 
	 * @param token
	 * @param detailService
	 * @param doc 
	 * @param actionName
	 * @param companyName
	 * @throws AccessDeniedException
	 */
	public static void logger(String token, ExcSynDetailService detailService,EcmDocument doc,String actionName,String companyName) throws AccessDeniedException {
		ExcSynDetail detail=new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(actionName);
		
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStatus("新建");
		if(StringUtils.isEmpty(companyName)) {
			detail.setToCompany(doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}else {
			detail.setToCompany(companyName);
		}
		
		//动力院发CNPE
		if(SynDetailService.inCompany.equalsIgnoreCase(detail.getToCompany())) {
			if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.ignoreCompany)) {
				return ;
			}
		}
		//CNPE发动力院
		else if(SynDetailService.ignoreCompany.equalsIgnoreCase(detail.getToCompany())) {
			if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.inCompany)) {
				return ;
			}
		}
		
		detailService.newObject(detail);
	}
	
	/**
	 * 
	 * @param token
	 * @param detailService
	 * @param doc
	 * @param companyName
	 * @throws AccessDeniedException
	 */
	public static void logger(String token, ExcSynDetailService detailService, EcmDocument doc, String companyName) throws AccessDeniedException {
		ExcSynDetail detail = new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(StatusEntity.actionsCnpe.get(doc.getStatus()));

		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStatus("新建");
		if (StringUtils.isEmpty(companyName)) {
			detail.setToCompany(
					doc.getAttributeValue("C_COMPANY") != null ? doc.getAttributeValue("C_COMPANY").toString() : "");
		} else {
			detail.setToCompany(companyName);
		}
		//动力院发CNPE
				if(SynDetailService.inCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.ignoreCompany)) {
						return ;
					}
				}
				//CNPE发动力院
				else if(SynDetailService.ignoreCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.inCompany)) {
						return ;
					}
				}
		detailService.newObject(detail);
	}
	/**
	 * 
	 * @param token
	 * @param detailService
	 * @param user
	 * @param companyName
	 * @param ActionName
	 * @throws AccessDeniedException
	 */
	public static void loggerUser(String token, ExcSynDetailService detailService, EcmUser user, String companyName,
			String ActionName) throws AccessDeniedException {
		ExcSynDetail detail = new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(ActionName);

		detail.setFromId(user.getId());
		detail.setCreationDate(new Date());
		if (StringUtils.isEmpty(companyName)) {
			detail.setToCompany(user.getCompanyName() != null ? user.getCompanyName() : "");
		} else {
			detail.setToCompany(companyName);
		}
		//动力院发CNPE
				if(SynDetailService.inCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.ignoreCompany)) {
						return ;
					}
				}
				//CNPE发动力院
				else if(SynDetailService.ignoreCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.inCompany)) {
						return ;
					}
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
	 * @throws AccessDeniedException 
	 */
	public static void loggerGroup(String token, ExcSynDetailService detailService, EcmUser user, String groupName,
			String companyName, String ActionName) throws AccessDeniedException {
		ExcSynDetail detail = new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(ActionName);

		detail.setFromId(user.getLoginName());
		detail.setToId(groupName);
		detail.setCreationDate(new Date());
		if (StringUtils.isEmpty(companyName)) {
			detail.setToCompany(user.getCompanyName() != null ? user.getCompanyName() : "");
		} else {
			detail.setToCompany(companyName);
		}
		//动力院发CNPE
				if(SynDetailService.inCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.ignoreCompany)) {
						return ;
					}
				}
				//CNPE发动力院
				else if(SynDetailService.ignoreCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.inCompany)) {
						return ;
					}
				}
		detailService.newObject(detail);
	}
	/**
	 * 
	 * @param token
	 * @param detailService
	 * @param doc
	 * @param companyName
	 * @throws AccessDeniedException
	 */
	public static void logger(String token, ExcSynDetailService detailService, ExcTransfer doc, String companyName) throws AccessDeniedException {
		ExcSynDetail detail = new ExcSynDetail();
		detail.setAppName("DOCEX");
//		detail.setActionName(actionName);
		detail.setActionName(StatusEntity.actions.get(doc.getStatus()));
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStatus("新建");
		if (StringUtils.isEmpty(companyName)) {
			detail.setToCompany(
					doc.getAttributeValue("C_COMPANY") != null ? doc.getAttributeValue("C_COMPANY").toString() : "");
		} else {
			detail.setToCompany(companyName);
		}
		//动力院发CNPE
				if(SynDetailService.inCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.ignoreCompany)) {
						return ;
					}
				}
				//CNPE发动力院
				else if(SynDetailService.ignoreCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.inCompany)) {
						return ;
					}
				}
		detailService.newObject(detail);
	}
	/**
	 * 
	 * @param token
	 * @param detailService
	 * @param doc
	 * @param actionName
	 * @param companyName
	 * @throws AccessDeniedException
	 */
	public static void logger(String token, ExcSynDetailService detailService, ExcTransfer doc, String actionName,
			String companyName) throws AccessDeniedException {
		ExcSynDetail detail = new ExcSynDetail();
		detail.setAppName("DOCEX");
		detail.setActionName(actionName);
//		detail.setActionName(StatusEntity.actions.get(doc.getStatus()));
		detail.setFromId(doc.getId());
		detail.setCreationDate(new Date());
		detail.setStatus(doc.getStatus());
		if (StringUtils.isEmpty(companyName)) {
			detail.setToCompany(
					doc.getAttributeValue("C_COMPANY") != null ? doc.getAttributeValue("C_COMPANY").toString() : "");
		} else {
			detail.setToCompany(companyName);
		}
		//动力院发CNPE
				if(SynDetailService.inCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.ignoreCompany)) {
						return ;
					}
				}
				//CNPE发动力院
				else if(SynDetailService.ignoreCompany.equalsIgnoreCase(detail.getToCompany())) {
					if(detailService.getSession(token).getCurrentUser().getCompany().equals(SynDetailService.inCompany)) {
						return ;
					}
				}
		detailService.newObject(detail);
	}
}
