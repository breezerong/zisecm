package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmRelationType;
import com.ecm.core.exception.EcmException;
/**
 * 管理关系定义接口
 * @author Haihong Rong
 * @date 2019年8月27日 下午11:50:05
 */
public interface IRelationTypeService {

	/**
	 * 根据父类型获取关系定义
	 * @param typeName 类型名称
	 * @return
	 * @throws EcmException
	 */
	List<EcmRelationType> getRelationByParentType(String typeName) throws EcmException;

	/**
	 * 根据子类型获取关系定义
	 * @param typeName 类型名称
	 * @return
	 * @throws EcmException
	 */
	List<EcmRelationType> getRelationByChildType(String typeName) throws EcmException;

}
