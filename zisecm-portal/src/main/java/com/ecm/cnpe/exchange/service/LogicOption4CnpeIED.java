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
	 * 
	 * @param token
	 * @param mainDoc
	 * @return
	 * @throws EcmException
	 */
	@Autowired
	private DocumentService documentService;

	@Transactional(rollbackFor = Exception.class)
	public boolean IEDOption(String token, EcmDocument mainDoc)
			throws EcmException, SqlDeniedException, AccessDeniedException, NoPermissionException { // 从数据库中取旧版该文件的所有隶属文件
		List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();//服务器当前版数据集，有的数据用同一种方法取不出来
		List<EcmDocument> temp1 = new ArrayList<EcmDocument>();				//服务器当前版对象集
		Map<String,Object> tempM1 = new HashMap<String,Object>();
		EcmDocument tempE1 = null;
		String coding = mainDoc.getCoding();
		String id = mainDoc.getId();
		EcmDocument tempE2 = documentService.getObjectById(token, id);
		//if(tempE2.getAttributeValue("C_EX1_DATE")!=null && tempE2.getAttributeValue("C_EX2_DATE")!=null) 
		String MainEx1=""; 
		String MainEx2=""; 								// 提交版
		if(tempE2.getAttributeValue("C_EX1_DATE")==null) {
			MainEx1="";
		}
		if(tempE2.getAttributeValue("C_EX2_DATE")==null) {
			MainEx2="";
		}
		if(tempE2.getAttributeValue("C_EX1_DATE")!=null && tempE2.getAttributeValue("C_EX2_DATE")!=null) {
		MainEx1 = tempE2.getAttributeValue("C_EX1_DATE").toString();
		MainEx2 = tempE2.getAttributeValue("C_EX2_DATE").toString();}
		String tempE1EX1="";					//服务器当前版数据
		String tempE1EX2="";
		String cond = "TYPE_NAME='IED' AND CODING= '" + coding + "'" + "AND IS_CURRENT=1 AND STATUS = '变更中'";
		String sql = "select * from ecm_document where TYPE_NAME='IED' AND CODING= '" + coding + "'" + "AND IS_CURRENT=1 AND STATUS = '变更中'";
		temp = documentService.getMapList(token, sql);			//服务器当前版本集
		temp1 = documentService.getObjects(token, cond);		//服务器当前版本对象集
		if (temp != null && temp.size() > 0) {// 有旧版本
			tempM1 = temp.get(0); // 服务器当前版
			tempE1 = temp1.get(0);
			tempE1.addAttribute("IS_CURRENT", 0);
			tempE1.addAttribute("STATUS", "已变更");
			// 修改当前版本
			if (temp.size() != 0) {
				if(tempM1.get("C_EX1_DATE")!=null) {
					tempE1EX1 = tempM1.get("C_EX1_DATE").toString();//服务器对象数据集仅作用于此，取出C_EX1/2
				}
				if(tempM1.get("C_EX1_DATE")==null||!tempM1.get("C_EX1_DATE").toString().equals(tempM1.get("C_EX2_DATE").toString())) {
	
				}
				
				
				if(tempM1.get("C_EX1_DATE")!=null) {
				tempE1EX2 = tempM1.get("C_EX2_DATE").toString();}
				if (!tempE1EX1.equals(MainEx1)) {
					tempE1.addAttribute("C_ITEM1_DATE", MainEx1);
					System.out.println(tempE1.getAttributeValue("C_ITEM1_DATE"));
				}
				if (!tempE1EX2.equals(MainEx2)) {
					tempE1.addAttribute("C_ITEM2_DATE", MainEx2);
					System.out.println(tempE1.getAttributeValue("C_ITEM2_DATE"));
				}
			}
			documentService.updateObject(token, tempE1, null);}
		
		LoginUser user;
		user = documentService.getCurrentUser(token);
		Date d1 = new Date();
		mainDoc.addAttribute("C_IS_RELEASED", 1);
		mainDoc.addAttribute("IS_CURRENT", 1);
		mainDoc.addAttribute("C_ITEM_DATE", d1);
		mainDoc.addAttribute("C_RECEIVE_DATE", d1);
		mainDoc.addAttribute("C_RECEIVER", user.getUserName());

		documentService.updateObject(token, mainDoc, null);

		return true;
	}
}
