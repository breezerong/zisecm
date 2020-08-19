package com.ecm.cnpe.exchange.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
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
	public boolean IEDOption(String token,EcmDocument mainDoc) throws EcmException, SqlDeniedException{						//从数据库中取旧版该文件的所有隶属文件
	List<EcmDocument> temp = new ArrayList<EcmDocument>();
	EcmDocument tempE1 = new EcmDocument();
	String coding = mainDoc.getCoding();
	String condition = "TYPE_NAME='IED' AND CODING= '" + coding +"'"+"AND IS_CURRENT=1 AND STATUS = '变更中'";
	temp = documentService.getObjects(token, condition);
	System.out.println(temp.size());
	for(int i = 0;i < temp.size();i++) {
		tempE1 = temp.get(i);
	}   
		tempE1.addAttribute("IS_CURRENT", 0);
		tempE1.addAttribute("STATUS", "已变更");
		try {				//修改当前版本
			LoginUser user;
			user = documentService.getCurrentUser(token);
			Date d1 = new Date();
			mainDoc.addAttribute("C_IS_RELEASED",1);
			mainDoc.addAttribute("IS_CURRENT",1);
			mainDoc.addAttribute("C_ITEM_DATE", d1);
			mainDoc.addAttribute("C_RECEIVE_DATE", d1);
			System.out.println(tempE1.getAttributeValue("STATUS"));
			mainDoc.addAttribute("C_RECEIVER",user.getUserName() );
			documentService.updateObject(token,tempE1, null);
			documentService.updateObject(token, mainDoc, null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	return true;
	}
}
	
