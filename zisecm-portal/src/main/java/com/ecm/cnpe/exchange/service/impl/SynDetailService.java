package com.ecm.cnpe.exchange.service.impl;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.cnpe.exchange.service.ISynDetailService;
import com.ecm.core.dao.ExcSynDetailMapper;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.EcmObjectService;
import com.ecm.portal.util.CustomInfo;
/**
 * 
 * @Title:同步日志服务
 * @author Haihong Rong
 * @date:   2020年7月5日 上午10:57:43 
 * @Description:
 */
@Service
public class SynDetailService extends EcmObjectService<ExcSynDetail> implements ISynDetailService {

	@Autowired
	private ExcSynDetailMapper excSysnDetailMapper;
	
	public static String ignoreCompany = "中国核动力研究设计院";
	public static String inCompany = CustomInfo.OwnerCompany;
	
	@Override
	public String newObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		
		ExcSynDetail en = (ExcSynDetail)obj;
		
		String toCompany = en.getToCompany();
		//动力院发CNPE
		if(inCompany.equalsIgnoreCase(toCompany)) {
			if(getSession(token).getCurrentUser().getCompany().equals(ignoreCompany)) {
				return null;
			}
		}
		//CNPE发动力院
		else if(ignoreCompany.equalsIgnoreCase(toCompany)) {
			if(getSession(token).getCurrentUser().getCompany().equals(inCompany)) {
				return null;
			}
		}
		
		if(StringUtils.isEmpty(en.getId())) {
			en.createId();
		}
		if(excSysnDetailMapper.insert(en)>0) {
			return en.getId();
		}
		return null;
	}
	
	@Override
	public String newObject(String token,String appName,String actionName,String fromId,String toCompany) throws EcmException, AccessDeniedException, NoPermissionException {
		
		
		//动力院发CNPE
		if(inCompany.equalsIgnoreCase(toCompany)) {
			if(getSession(token).getCurrentUser().getCompany().equals(ignoreCompany)) {
				return null;
			}
		}
		//CNPE发动力院
		else if(ignoreCompany.equalsIgnoreCase(toCompany)) {
			if(getSession(token).getCurrentUser().getCompany().equals(inCompany)) {
				return null;
			}
		}
		ExcSynDetail en = new ExcSynDetail();
		en.createId();
		en.setActionName(actionName);
		en.setCreationDate(new Date());
		en.setFromId(fromId);
		en.setStatus("新建");
		en.setAppName(appName);
		return this.newObject(token, en);
	}
}
