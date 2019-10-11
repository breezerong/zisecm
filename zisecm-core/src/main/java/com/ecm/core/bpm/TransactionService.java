package com.ecm.core.bpm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecm.core.dao.EcmTransactionMapper;
import com.ecm.core.entity.EcmTransaction;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.ITransactionService;
/**
 * 转移服务
 * @author Haihong Rong
 *
 */
@Component
@Scope("prototype")
public class TransactionService extends EcmObjectService<EcmTransaction>  implements ITransactionService {
	@Autowired
	private EcmTransactionMapper ecmDefTransactionMapper;
	
	@Override
	public List<EcmTransaction> getTransactions(String token, String acitivtyId){
		 return ecmDefTransactionMapper.selectByCondition(" FROM_ID='"+acitivtyId +"' order by ORDER_INDEX");
	}
}
