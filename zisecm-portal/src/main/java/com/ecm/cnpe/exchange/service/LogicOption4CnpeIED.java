package com.ecm.cnpe.exchange.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.exception.EcmException;

@Service
public class LogicOption4CnpeIED {
	/**
	 * IED接收处理
	 * @param token
	 * @param mainDoc
	 * @return
	 * @throws EcmException
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean IEDOption(String token,EcmDocument mainDoc) throws EcmException{
		return true;
		
	}
}
