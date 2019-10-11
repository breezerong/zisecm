/**
 * 
 */
package com.ecm.icore.service;

import com.ecm.core.entity.EcmGridView;
import com.ecm.core.exception.EcmException;

/**
 * @ClassName  EcmGridViewService   
 * @Description TODO(列表服务接口)   
 * @author yaozhigang
 * @date 2018年6月29日 下午2:17:33  
 *
 */
public interface IGridViewService {
	public EcmGridView queryGridView(String token,String gridName);

	EcmGridView getObjectByName(String token, String name);

	String copy(String token, EcmGridView obj);
}
