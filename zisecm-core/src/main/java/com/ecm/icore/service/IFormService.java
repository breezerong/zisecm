package com.ecm.icore.service;

import com.ecm.core.entity.EcmForm;

/**
 * @ClassName  EcmFormService   
 * @Description TODO(表单控件接口)   
 * @author Haihong Rong
 * @date 2018年6月29日 下午2:54:21  
 *
 */
public interface IFormService {
	public EcmForm queryEcmForm(String typeName,String action);
}
