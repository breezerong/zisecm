package com.ecm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmSelectValueMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmSelectValue;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;

/**
 * @Description TODO(选择项)   
 * @author Haihong Rong
 * @date 2018年6月29日 下午2:47:08  
 *
 */
@Service
@Scope("prototype")
public class SelectValueService extends EcmObjectService<EcmSelectValue>{
	@Autowired
	private EcmSelectValueMapper ecmSelectValue;
	

	
	@Override
	public List<EcmSelectValue> getAllObject(String token) {
		// TODO Auto-generated method stub
		return (List<EcmSelectValue>) ecmSelectValue.selectAll();
	}

	@Override
	public EcmSelectValue getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmSelectValue) ecmSelectValue.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmSelectValue.updateByPrimaryKey((EcmSelectValue) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmSelectValue.deleteByPrimaryKey(((EcmSelectValue)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmSelectValue)obj).createId();
		ecmSelectValue.insert((EcmSelectValue)obj);
		return ((EcmSelectValue)obj).getId();
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		return ecmSelectValue.deleteByPrimaryKey(id)>0;
	}
}
