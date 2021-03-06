package com.ecm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmLanguageMapper;
import com.ecm.core.entity.EcmLanguage;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
@Service
public class LanguageService extends EcmObjectService<EcmLanguage> {

	@Autowired
	private EcmLanguageMapper ecmLanguageMapper;
	
	@Override
	public List<EcmLanguage> getAllObject(String token) {
		return ecmLanguageMapper.selectAll();
	}
	
	@Override
	public EcmLanguage getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmLanguage) ecmLanguageMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmLanguageMapper.updateByPrimaryKey((EcmLanguage) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmLanguageMapper.deleteByPrimaryKey(((EcmLanguage)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmLanguage)obj).createId();
		ecmLanguageMapper.insert((EcmLanguage)obj);
		return ((EcmLanguage)obj).getId();
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return ecmLanguageMapper.deleteByPrimaryKey(id)>0;
	}
	
	
}
