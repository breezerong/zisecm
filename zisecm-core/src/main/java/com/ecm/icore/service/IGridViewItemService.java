package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmGridViewItem;
/**
 * 列表项服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:57:47
 */
public interface IGridViewItemService {

	List<EcmGridViewItem> getByParentId(String token, String parentId);

}
