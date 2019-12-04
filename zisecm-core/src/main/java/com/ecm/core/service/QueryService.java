package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmQueryMapper;
import com.ecm.core.entity.EcmQuery;
import com.ecm.icore.service.IQueryService;
/**
 * 查询服务
 * @author Haihong Rong
 * @date 2019年7月22日 上午6:38:39
 */
@Service
public class QueryService extends EcmObjectService<EcmQuery> implements IQueryService {

	@Autowired
	private EcmQueryMapper ecmQuery;
	
	@Override
	public List<Map<String, Object>> executeSQL(String token, String sql) {
		// TODO Auto-generated method stub
		return ecmQuery.executeSQL(sql);
	}

	@Override
	public List<EcmQuery> getAllObject(String token) {
		// TODO Auto-generated method stub
		return (List<EcmQuery>) ecmQuery.selectAll();
	}

	@Override
	public EcmQuery getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmQuery) ecmQuery.selectByPrimaryKey(id);
	}
	
	@Override
	public EcmQuery getObjectByName(String token,String name) {
		// TODO Auto-generated method stub
		return (EcmQuery) ecmQuery.selectByName(name);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmQuery.updateByPrimaryKey((EcmQuery) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmQuery.deleteByPrimaryKey(((EcmQuery)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmQuery)obj).createId();
		ecmQuery.insert((EcmQuery)obj);
		return ((EcmQuery)obj).getId();
	}
}
