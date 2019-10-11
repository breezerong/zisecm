package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFormMapper;
import com.ecm.core.dao.EcmGridViewItemMapper;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.icore.service.IFormService;
import com.ecm.icore.service.IGridViewItemService;

/**
 * @ClassName  EcmFormServiceImpl   
 * @Description TODO(表单控件实现类)   
 * @author Haihong Rong
 * @date 2018年6月29日 下午2:47:08  
 *
 */
@Service
@Scope("prototype")
public class GridViewItemService extends EcmObjectService<EcmGridViewItem> implements IGridViewItemService{
	@Autowired
	private EcmGridViewItemMapper ecmGridViewItem;
	

	
	@Override
	public List<EcmGridViewItem> getAllObject(String token) {
		// TODO Auto-generated method stub
		return (List<EcmGridViewItem>) ecmGridViewItem.selectAll();
	}
	@Override
	public List<EcmGridViewItem> getByParentId(String token,String parentId){
		return (List<EcmGridViewItem>) ecmGridViewItem.selectByParentId(parentId);
	}

	@Override
	public EcmGridViewItem getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmGridViewItem) ecmGridViewItem.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmGridViewItem.updateByPrimaryKey((EcmGridViewItem) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmGridViewItem.deleteByPrimaryKey(((EcmGridViewItem)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmGridViewItem)obj).createId();
		ecmGridViewItem.insert((EcmGridViewItem)obj);
		return ((EcmGridViewItem)obj).getId();
	}
}
