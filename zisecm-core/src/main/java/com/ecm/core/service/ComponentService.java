package com.ecm.core.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.entity.EcmComponent;
import com.ecm.icore.service.IMenuItemService;

/**
 * 
 * @author Haihong Rong
 * @date 2019年10月17日 下午1:39:37
 */
@Service
@Scope("prototype")
public class ComponentService extends EcmObjectService<EcmComponent> implements IMenuItemService{
	@Autowired
	private EcmComponentMapper ecmComponentMapper;
	
	@Override
	public List<EcmComponent> getObjects(String token,String condition) {
		// TODO Auto-generated method stub
		return ecmComponentMapper.selectByCondition(condition);
	}
	
	@Override
	public List<EcmComponent> getAllObject(String token) {
		// TODO Auto-generated method stub
		return ecmComponentMapper.selectAll();
	}

	@Override
	public EcmComponent getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return ecmComponentMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmComponentMapper.updateByPrimaryKey((EcmComponent) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmComponentMapper.deleteByPrimaryKey(((EcmComponent)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmComponent)obj).createId();
		ecmComponentMapper.insert((EcmComponent)obj);
		return ((EcmComponent)obj).getId();
	}
}
