package com.ecm.icore.service;

import com.ecm.core.entity.EcmParameter;

/**
 * @ClassName  IEcmParameterService   
 * @Description TODO(系统参数控件接口)   
 * @author yaozhigang
 * @date 2018年7月3日 下午2:54:21  
 *
 */
public interface IParameterService {
	public EcmParameter queryEcmParameter(String token, String name);
}
