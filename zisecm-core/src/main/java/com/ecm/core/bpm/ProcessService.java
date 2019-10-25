package com.ecm.core.bpm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecm.core.dao.EcmProcessMapper;
import com.ecm.core.entity.EcmProcess;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.IProcessService;
/**
 * 流程服务
 * @author Haihong Rong
 *
 */
@Component
@Scope("prototype")
public class ProcessService extends EcmObjectService<EcmProcess> implements IProcessService {
	
	@Autowired
	private EcmProcessMapper ecmProcessMapper;
	@Autowired
	private ActivityService activityService;
	
	@Override
	public List<EcmProcess> getAllObject(String token){
		return ecmProcessMapper.selectByCondition(" 1=1");
	}
	
	@Override
	public EcmProcess getObjectById(String token,String id) throws EcmException{
		// TODO Auto-generated method stub
		EcmProcess obj= ecmProcessMapper.selectByPrimaryKey(id);
		obj.setActivities(activityService.getActivities(token, obj.getId()));
		return obj;
	}
	
	@Override
	public EcmProcess getObjectByName(String token,String name) throws EcmException{
		// TODO Auto-generated method stub
		List<EcmProcess> list = ecmProcessMapper.selectByCondition(" NAME='"+name+"'");
		if(list.size()>0) {
			EcmProcess obj = list.get(0);
			obj.setActivities(activityService.getActivities(token, obj.getId()));
			return obj;
		}
		return null;
	}
	
}
