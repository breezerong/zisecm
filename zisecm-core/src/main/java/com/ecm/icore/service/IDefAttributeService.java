package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmDefAttribute;

/**
 * 业务类型属性服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:49:09
 */
public interface IDefAttributeService{
	
	/***
	 * 获取业务类型属性定义
	 * @param typeId 类型ID
	 * @return
	 */
	List<EcmDefAttribute> getAttributes(String token, String typeId);

	

}
