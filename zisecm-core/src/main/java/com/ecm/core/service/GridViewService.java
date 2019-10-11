package com.ecm.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmGridViewItemMapper;
import com.ecm.core.dao.EcmGridViewMapper;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.exception.EcmException;
import com.ecm.icore.service.IGridViewService;

/**
 * @ClassName  EcmGridViewServiceImpl   
 * @Description TODO(列表接口实现类)   
 * @author Haihong Rong
 * @date 2018年6月29日 下午2:18:01  
 *
 */
@Service
@Scope("prototype")
public class GridViewService extends EcmObjectService<EcmGridView> implements IGridViewService {
	
	@Autowired
	private EcmGridViewMapper ecmGridView;
	
	@Autowired
	private EcmGridViewItemMapper ecmGridViewItem;
	
	@Override
	public EcmGridView queryGridView(String token,String gridName) {
		return CacheManagerOper.getEcmGridViews().get(gridName);
	}

	@Override
	public List<EcmGridView> getAllObject(String token) throws EcmException {
		// TODO Auto-generated method stub
		return ecmGridView.selectAll();
	}

	@Override
	public EcmGridView getObjectById(String token, String id) throws EcmException {
		// TODO Auto-generated method stub
		return ecmGridView.selectByPrimaryKey(id);
	}
	
	@Override
	public EcmGridView getObjectByName(String token, String name) {
		// TODO Auto-generated method stub
		String cond = "NAME='"+name+"'";
		return ecmGridView.selectByCondition(cond).get(0);
	}

	@Override
	public boolean updateObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		return ecmGridView.updateByPrimaryKey((EcmGridView)obj)>0;
	}

	@Override
	public boolean deleteObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		return ecmGridView.deleteByPrimaryKey(((EcmGridView)obj).getId())>0;
	}

	@Override
	
	public String newObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		((EcmGridView)obj).createId();
		String id = ((EcmGridView)obj).getId();
		ecmGridView.insert((EcmGridView)obj);
		return id;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String copy(String token, EcmGridView obj) {
		 String name = obj.getName()+" Copy";
		 String fromId = obj.getId();
		 obj.setName(name);
		 obj.createId();
		 ecmGridView.insert(obj);
		 obj = getObjectByName(token,name);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 if(obj!=null)
		 {
			 List<EcmGridViewItem> items = ecmGridViewItem.selectByParentId(fromId);
			 for(EcmGridViewItem en:items)
			 {
				 en.createId();
				 en.setParentId(obj.getId());
				 ecmGridViewItem.insert(en);
			 }
			 return obj.getId();
		 }
		 return null;
	}
		 
		 
	
}
