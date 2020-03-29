package com.ecm.core.db;

import org.apache.commons.lang.StringUtils;

import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.SqlDeniedException;

public class DBGeneralUtils {
	public  static void conditionValidate(String condition) throws SqlDeniedException, EcmException {
		if(StringUtils.isEmpty(condition)) {
			throw new EcmException("Condition is null.");
		}
		condition = condition.toLowerCase();
		if(condition.indexOf("delete ")>-1) {
			throw new SqlDeniedException("Condition cannot include delete.");
		}
		if(condition.indexOf("update ")>-1) {
			throw new SqlDeniedException("Condition cannot include update.");
		}
		
	}
}
