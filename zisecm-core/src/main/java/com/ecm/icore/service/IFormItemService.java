package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmFormItem;

/**
 * @ClassName  EcmFormService   
 * @Description TODO(表单控件接口)   
 * @author Haihong Rong
 * @date 2018年6月29日 下午2:54:21  
 *
 */
public interface IFormItemService {
	List<EcmFormItem> getFormItems(String token,String id);
}
