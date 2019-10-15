package com.ecm.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecm.core.dao.EcmQueueItemMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.icore.bpm.IQueueItemService;
/**
 * 消息队列服务
 * @author Haihong Rong
 *
 */
@Component
@Scope("prototype")
public class QueueItemService extends EcmObjectService<EcmQueueItem> implements IQueueItemService {

	@Autowired
	private EcmQueueItemMapper ecmQueueItemMapper;
	
	@Override
	public List<EcmQueueItem> getObjects(String token,String condition){
		return ecmQueueItemMapper.selectByCondition(condition);
	}
	@Override
	public List<EcmQueueItem> getTodoObjects(String token,String userName){
		String cond = " NAME='"+userName+"' and STATUS<3 and EVENT_NAME='ecm_start_workitem' order by SEND_DATE DESC";
		return getObjects(token, cond);
	}
	
	@Override
	public List<EcmQueueItem> getMyTodoObjects(String token) throws AccessDeniedException{
		String cond = " NAME='"+getSession(token).getCurrentUser().getUserName()+"' and STATUS<3  and WORKITEM_ID>0 order by SEND_DATE DESC";
		return getObjects(token, cond);
	}
	@Override
	public List<EcmQueueItem> getMyTodoObjects(String token,int pageSize,int startIndex,String condition) throws AccessDeniedException{
		String cond = " NAME='"+getSession(token).getCurrentUser().getUserName()+"' and STATUS<3  and EVENT_NAME='ecm_start_workitem' ";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		cond += " order by SEND_DATE DESC";
		cond += " limit "+ startIndex + ","+pageSize;
		return getObjects(token, cond);
	}
	@Override
	public int getMyTodoCount(String token,String condition) throws AccessDeniedException{
		String cond = " NAME='"+getSession(token).getCurrentUser().getUserName()+"' and STATUS<3  and EVENT_NAME='ecm_start_workitem' ";
		if(condition!=null&&condition.trim().length()>0) {
			cond += " and ("+condition+")";
		}
		return ecmQueueItemMapper.getCountByCondition(cond);
	}
	
	@Override
	public int getMyTodoCount(String token) throws AccessDeniedException{
		String cond = " NAME='"+getSession(token).getCurrentUser().getUserName()+"' and STATUS<3  and EVENT_NAME='ecm_start_workitem'";
		return ecmQueueItemMapper.getCountByCondition(cond);
	}
	
	@Override
	public EcmQueueItem getObjectById(String token, String id) {
		return ecmQueueItemMapper.selectByPrimaryKey(id);
	}
	@Override
	public boolean updateObject(String token, Object obj) {
		return ecmQueueItemMapper.updateByPrimaryKey((EcmQueueItem)obj)>0;
	}
	@Override
	public boolean deleteObject(String token,Object obj) {
		return ecmQueueItemMapper.deleteByPrimaryKey(((EcmQueueItem)obj).getId())>0;
	}
	
	@Override
	public String newObject(String token,Object obj) {
		EcmQueueItem item = (EcmQueueItem)obj;
		if(StringUtils.isEmpty(item.getId())) {
			item.createId();
		}
		ecmQueueItemMapper.insert(item);
		return item.getId();
	}
}
