package com.ecm.icore.bpm;

import java.util.List;

import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.exception.AccessDeniedException;
/**
 * 队列服务
 * @author Haihong Rong
 *
 */
public interface IQueueItemService {

	/**
	 * 获取用户待办任务列表
	 * @param userName
	 * @return
	 */
	List<EcmQueueItem> getTodoObjects(String token, String userName);

	/**
	 * 获取当前用户待办任务列表
	 * @return
	 * @throws AccessDeniedException 
	 */
	List<EcmQueueItem> getMyTodoObjects(String token) throws AccessDeniedException;

	/**
	 * 获取当前用户待办数
	 * @return
	 * @throws AccessDeniedException 
	 */
	int getMyTodoCount(String token) throws AccessDeniedException;
	/**
	 * 根据条件获取当前用户待办数
	 * @param condition
	 * @return
	 * @throws AccessDeniedException 
	 */
	int getMyTodoCount(String token, String condition) throws AccessDeniedException;
	/**
	 * 获取当前用户待办分页数据
	 * @param pageSize 每页数目
	 * @param startIndex 当前页
	 * @param condition 条件
	 * @return
	 * @throws AccessDeniedException 
	 */
	List<EcmQueueItem> getMyTodoObjects(String token, int pageSize, int startIndex, String condition) throws AccessDeniedException;

}
