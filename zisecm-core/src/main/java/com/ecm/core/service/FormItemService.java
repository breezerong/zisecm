package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFormItemMapper;
import com.ecm.core.dao.EcmFormMapper;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.icore.service.IFormItemService;
import com.ecm.icore.service.IFormService;

/**
 * @ClassName  EcmFormServiceImpl   
 * @Description TODO(表单控件实现类)   
 * @author Haihong Rong
 * @date 2018年6月29日 下午2:47:08  
 *
 */
@Service
@Scope("prototype")
public class FormItemService extends EcmObjectService<EcmFormItem> implements IFormItemService{
	@Autowired
	private EcmFormItemMapper formItemMapper;
	
	
	
	@Override
	public List<EcmFormItem> getAllObject(String token) {
		// TODO Auto-generated method stub
		return (List<EcmFormItem>) formItemMapper.selectAll();
	}

	@Override
	public EcmFormItem getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmFormItem) formItemMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return formItemMapper.updateByPrimaryKey((com.ecm.core.entity.EcmFormItem) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return formItemMapper.deleteByPrimaryKey(((com.ecm.core.entity.EcmFormItem)obj).getId())>0;
	}

	@Override
	public String newObject(String token, Object obj) {
		// TODO Auto-generated method stub
		((EcmFormItem)obj).createId();
		formItemMapper.insert((com.ecm.core.entity.EcmFormItem)obj);
		return ((com.ecm.core.entity.EcmFormItem)obj).getId();
	}

	@Override
	public List<EcmFormItem> getFormItems(String token, String id) {
		// TODO Auto-generated method stub
		return formItemMapper.selectByParentId(id);
	}
}
