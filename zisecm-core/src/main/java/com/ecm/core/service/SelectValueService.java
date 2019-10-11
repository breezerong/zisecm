package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFormMapper;
import com.ecm.core.dao.EcmGridViewItemMapper;
import com.ecm.core.dao.EcmSelectValueMapper;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.EcmSelectValue;
import com.ecm.icore.service.IFormService;
import com.ecm.icore.service.IGridViewItemService;

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
}
