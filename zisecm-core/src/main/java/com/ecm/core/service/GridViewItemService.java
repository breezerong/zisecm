package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmGridViewItemMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
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
	
	public boolean deleteByParentId(String token,String parentId){
		return ecmGridViewItem.deleteByParentId(parentId)>0;
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
	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		return ecmGridViewItem.deleteByPrimaryKey(id)>0;
	}
	
	
	public List<EcmGridViewItem> getEcmCustomGridViewInfo(String token,String id){
		
		return ecmGridViewItem.getItemByParam(id);
	}
}
