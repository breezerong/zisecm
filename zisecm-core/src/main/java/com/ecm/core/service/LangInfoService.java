package com.ecm.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecm.core.dao.EcmLangInfoMapper;
import com.ecm.core.dao.EcmLangItemMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.db.DBGeneralUtils;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.EcmLangItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
@Service
public class LangInfoService extends EcmObjectService<EcmLangInfo> {

	@Autowired
	private EcmLangInfoMapper ecmLangInfoMapper;
	@Autowired
	private EcmLangItemMapper ecmLangItemMapper;
	
	@Override
	public List<EcmLangInfo> getAllObject(String token) {
		List<EcmLangInfo> list = ecmLangInfoMapper.selectAll();
		List<EcmLangItem> listItem = ecmLangItemMapper.selectAll();
		if(list != null) {
			for(EcmLangInfo lang : list) {
				addItem(lang, listItem);
			}
		}
		return list;
	}
	
	@Override
	public EcmLangInfo getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		EcmLangInfo item =  ecmLangInfoMapper.selectByPrimaryKey(id);
		if(item != null) {
			item.setLangItems(getLangItem(token,item.getMessageKey()));
		}
		return item;
	}
	
	public List<EcmLangInfo> getObjectByCondition(String token,String condition,Pager pager) throws SqlDeniedException, EcmException {
		if(StringUtils.isEmpty(condition)) {
			condition = "1=1";
		}
		DBGeneralUtils.conditionValidate(condition);
		List<EcmLangInfo> list = ecmLangInfoMapper.selectByCondition(condition, pager);
		if(list != null) {
			for(EcmLangInfo lang : list) {
				lang.setLangItems(getLangItem(token,lang.getMessageKey()));
			}
		}
		return list;
	}
	
	public EcmLangInfo getObjectByMsgKey(String token,String msgKey) {
		// TODO Auto-generated method stub
		EcmLangInfo item =  ecmLangInfoMapper.selectByMsgKey(msgKey);
		if(item != null) {
			item.setLangItems(getLangItem(token,item.getMessageKey()));
		}
		return item;
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmLangInfoMapper.updateByPrimaryKey((EcmLangInfo) obj)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		ecmLangItemMapper.deleteByMessageKey(((EcmLangInfo)obj).getMessageKey());
		return ecmLangInfoMapper.deleteByPrimaryKey(((EcmLangInfo)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		EcmLangInfo info = (EcmLangInfo) obj;
		info.createId();
		ecmLangInfoMapper.insert(info);
		return info.getId();
	}
	
	private void addItem(EcmLangInfo lang, List<EcmLangItem> listItem) {
		for(EcmLangItem item: listItem) {
			if(item.getMessageKey().equals(lang.getMessageKey())) {
				lang.getLangItems().add(item);
			}
		}
	}
	/**
	 * 获取语言标签
	 * @param token
	 * @param msgKey 语言标签Key
	 * @return
	 */
	public List<EcmLangItem> getLangItem(String token,String msgKey){
		return ecmLangItemMapper.selectByMessageKey(msgKey);
	}
	/**
	 * 更新语言标签值
	 * @param token
	 * @param item
	 * @return
	 */
	public boolean updateLangItem(String token, EcmLangItem item) {
		return ecmLangItemMapper.updateByPrimaryKey(item)>0;
	}
	/**
	 * 新建语言标签
	 * @param token
	 * @param item
	 * @return
	 */
	public boolean newLangItem(String token, EcmLangItem item) {
		item.createId();
		return ecmLangItemMapper.insertSelective(item)>0;
	}
	/**
	 * 删除语言标签
	 * @param token
	 * @param id
	 * @return
	 */
	public boolean deleteLangItem(String token, String id) {
		return ecmLangItemMapper.deleteByPrimaryKey(id)>0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		EcmLangInfo obj = getObjectById(token,id);
		ecmLangItemMapper.deleteByMessageKey(obj.getMessageKey());
		return ecmLangInfoMapper.deleteByPrimaryKey(id)>0;
	}
	
}
