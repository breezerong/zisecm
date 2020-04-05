/**
 * 
 */
package com.ecm.core.bpm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecm.core.dao.EcmActivityMapper;
import com.ecm.core.entity.EcmActivity;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.IActivityService;

/**
 * @author Haihong Rong
   * 活动服务
 */
@Component
public class ActivityService extends EcmObjectService<EcmActivity>  implements IActivityService {

	@Autowired
	private EcmActivityMapper ecmActivityMapper;
	@Autowired
	private TransactionService transactionService;
	
	@Override
	public List<EcmActivity> getActivities(String token,String processId){
		
		List<EcmActivity> actList =ecmActivityMapper.selectByCondition(" PROCESS_ID='"+processId+"' order by ORDER_INDEX");
		for(EcmActivity actObj:actList) {
			actObj.setTransactions(transactionService.getTransactions(token,actObj.getId()));
		}
		return actList;
	}
	
	@Override
	public EcmActivity getObjectById(String token,String id) {
		EcmActivity actObj = ecmActivityMapper.selectByPrimaryKey(id);
		actObj.setTransactions(transactionService.getTransactions(token, actObj.getId()));
		return actObj;
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return false;
	}
}
