package com.ecm.icore.bpm;

import com.ecm.core.entity.EcmQueueItem;
/**
 * 提醒事件
 * @author Haihong Rong
 * @date 2019年7月17日 上午10:54:22
 */
public interface INotification {
	/**
	 * 提醒
	 * @param workitem
	 */
	void startNotification(EcmQueueItem workitem);
}
