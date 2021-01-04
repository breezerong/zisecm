package com.ecm.core.util;

import java.util.Map;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmSystemEvent;

public class AuditUtils {
	public static boolean isEnabled(String eventName) {
		Map<String, EcmSystemEvent>  map =CacheManagerOper.getEcmSystemEvents();
		if(map!=null) {
			EcmSystemEvent event = map.get(eventName);
			if(event !=null ) {
				return event.getEnabled();
			}
		}
		if(eventName != null && !eventName.startsWith("ecm")) {
			return true;
		}
		return false;
	}
}
