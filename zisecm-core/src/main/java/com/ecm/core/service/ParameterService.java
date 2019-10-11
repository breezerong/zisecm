package com.ecm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmParameterMapper;
import com.ecm.core.entity.EcmParameter;
import com.ecm.icore.service.IParameterService;

/**
 * @ClassName     
 * @Description TODO(参数设置)   
 * @author Haihong Rong
 * @date 2018年7月3日 下午2:47:08  
 *
 */
@Service
@Scope("prototype")
public class ParameterService extends EcmObjectService<EcmParameter> implements IParameterService{
	
	@Autowired
	private EcmParameterMapper ecmParameter;
	
	@Override
	public EcmParameter queryEcmParameter(String token, String name) {
		return CacheManagerOper.getEcmParameters().get(name);
	}
	
	@Override
	public List<EcmParameter> getAllObject(String token) {
		// TODO Auto-generated method stub
		return (List<EcmParameter>) ecmParameter.selectAll();
	}

	@Override
	public EcmParameter getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmParameter) ecmParameter.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmParameter.updateByPrimaryKey((EcmParameter) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmParameter.deleteByPrimaryKey(((EcmParameter)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmParameter)obj).createId();
		ecmParameter.insert((EcmParameter)obj);
		return ((EcmParameter)obj).getId();
	}
}
