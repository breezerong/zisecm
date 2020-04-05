package com.ecm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmSystemEventMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmSystemEvent;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
@Service
public class SystemEventService extends EcmObjectService<EcmSystemEvent> {

	@Autowired
	private EcmSystemEventMapper ecmSystemEventMapper;
	
	@Override
	public List<EcmSystemEvent> getAllObject(String token) {
		return ecmSystemEventMapper.selectAll();
	}
	
	@Override
	public EcmSystemEvent getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmSystemEvent) ecmSystemEventMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmSystemEventMapper.updateByPrimaryKey((EcmSystemEvent) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmSystemEventMapper.deleteByPrimaryKey(((EcmSystemEvent)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		EcmSystemEvent event = (EcmSystemEvent)obj;
		event.createId();
		ecmSystemEventMapper.insert(event);
		return event.getId();
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		return ecmSystemEventMapper.deleteByPrimaryKey(id)>0;
	}
	
	
}
