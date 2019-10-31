package com.ecm.core.service.statusflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmLifeCycleItemMapper;

@Service
public class StatusflowService {
	@Autowired
	private EcmLifeCycleItemMapper itemMapper;
	
	public boolean nextStep(String lifecycleName,String currentStatus) throws Exception {
		if(lifecycleName!=null&&currentStatus!=null
				&&!"".equals(lifecycleName)&&!"".equals(currentStatus)) {
			if(executeEvent(lifecycleName,currentStatus,"next")) {
				return true;
			}else {
				return false;
			}
		}
		
		return true;
		
	}
	/**
	 * 执行事件
	 * @param lifecycleName
	 * @param itemName
	 * @return
	 * @throws Exception
	 */
	private boolean executeEvent(String lifecycleName, String itemName,String flag) throws Exception {
		if("next".equals(flag)) {
			String eventName= itemMapper.getNextImpleClassByName(lifecycleName, itemName);
			if(eventName==null||"".equals(eventName)) {
				Class<?> claz=Class.forName(eventName);
				ILifeCycleEvent event=(ILifeCycleEvent) claz.newInstance();
				if(event.execute()) {
					return true;
				}
			}
		}else if("previous".equals(flag)) {
			String eventName= itemMapper.getPreviousImpleClassByName(lifecycleName, itemName);
			if(eventName==null||"".equals(eventName)) {
				Class<?> claz=Class.forName(eventName);
				ILifeCycleEvent event=(ILifeCycleEvent) claz.newInstance();
				if(event.execute()) {
					return true;
				}
			}
		}
		
		
		return false;
	}
}
