package com.ecm.cnpe.exchange.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.DocumentService;


@Service
public class LogicOption4CnpeIED {
	/**
	 * IED接收处理
	 * @param token
	 * @param mainDoc
	 * @return
	 * @throws EcmException
	 */
	@Autowired
	private DocumentService documentService;
	
	@Transactional(rollbackFor = Exception.class)
	public boolean IEDOption(String token,EcmDocument mainDoc) throws EcmException{
		try {
			LoginUser user;
			user = documentService.getCurrentUser(token);
			Date d1 = new Date();
			mainDoc.addAttribute("C_IS_RELEASED",1);
			mainDoc.addAttribute("IS_CURRENT",1);
			mainDoc.addAttribute("C_ITEM_DATE", d1);
			mainDoc.addAttribute("C_RECEIVE_DATE", d1);
			mainDoc.addAttribute("C_RECEIVER",user.getUserName() );
			documentService.updateObject(token, mainDoc, null);
			System.out.println("已经做接收处理"+user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return true;
		
		
	}
}
