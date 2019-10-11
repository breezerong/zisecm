package com.ecm.icore.bpm;

import java.util.List;

import com.ecm.core.entity.EcmActivity;
/**
 * 活动定义服务
 * @author Haihong Rong
 * @date 2019年7月17日 上午10:53:30
 */
public interface IActivityService {

	/**
	 * 获取流程所有活动
	 * @param processId
	 * @return
	 */
	List<EcmActivity> getActivities(String token,String processId);

}
