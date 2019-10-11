package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.ServiceContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmStoreMapper;
import com.ecm.core.entity.EcmStore;
import com.ecm.icore.service.IStoreService;

/**
 * @ClassName  EcmFormServiceImpl   
 * @Description TODO(表单控件实现类)   
 * @author Haihong Rong
 * @date 2018年6月29日 下午2:47:08  
 *
 */
@Service
@Scope("prototype")
public class StoreService extends EcmObjectService<EcmStore> implements IStoreService{
	@Autowired
	private EcmStoreMapper ecmStore;
	
	public StoreService() {
		serviceCode = ServiceContext.STORE_CODE;
	}
	
	@Override
	public List<EcmStore> getAllObject(String token) {
		// TODO Auto-generated method stub
		return (List<EcmStore>) ecmStore.selectAll();
	}

	@Override
	public EcmStore getObjectById(String token, String id) {
		// TODO Auto-generated method stub
		return (EcmStore) ecmStore.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token, Object obj) {
		// TODO Auto-generated method stub
		return ecmStore.updateByPrimaryKey((EcmStore) obj)>0;
	}

	@Override
	public boolean deleteObject(String token, Object obj) {
		// TODO Auto-generated method stub
		return ecmStore.deleteByPrimaryKey(((com.ecm.core.entity.EcmForm)obj).getId())>0;
	}

	@Override
	public String newObject(String token, Object obj) {
		// TODO Auto-generated method stub
		((EcmStore)obj).createId();
		ecmStore.insert((EcmStore)obj);
		return ((EcmStore)obj).getId();
	}
}
