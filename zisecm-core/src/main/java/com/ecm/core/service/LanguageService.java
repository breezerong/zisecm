package com.ecm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmLanguageMapper;
import com.ecm.core.entity.EcmLanguage;
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
	
	
}
