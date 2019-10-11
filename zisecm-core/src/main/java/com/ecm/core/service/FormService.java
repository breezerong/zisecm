package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFormMapper;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.entity.EcmForm;
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
public class FormService extends EcmObjectService<EcmForm> implements IFormService{
	@Autowired
	private EcmFormMapper ecmForm;
	
	@Override
	public EcmForm queryEcmForm(String typeName,String action) {
		 Map<String,EcmForm> forms = CacheManagerOper.getEcmForms();
		 EcmForm form = forms.get(typeName+"_"+action);
		 if(form == null) {
			 form = forms.get(typeName+"_1");
		 }
		 return form;
	}
	
	@Override
	public List<EcmForm> getAllObject(String token) {
		// TODO Auto-generated method stub
		return (List<EcmForm>) ecmForm.selectAll();
	}

	@Override
	public EcmForm getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return (EcmForm) ecmForm.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmForm.updateByPrimaryKey((com.ecm.core.entity.EcmForm) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmForm.deleteByPrimaryKey(((com.ecm.core.entity.EcmForm)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmForm)obj).createId();
		ecmForm.insert((com.ecm.core.entity.EcmForm)obj);
		return ((com.ecm.core.entity.EcmForm)obj).getId();
	}
}
