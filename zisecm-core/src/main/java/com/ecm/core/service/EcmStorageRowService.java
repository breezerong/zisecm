package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmStorageRowMapper;
import com.ecm.core.entity.EcmStorageRoom;
import com.ecm.core.entity.EcmStorageRow;
import com.ecm.core.entity.Pager;

@Service
@Scope("prototype")
public class EcmStorageRowService {
	@Autowired
	private EcmStorageRowMapper storageRowDao;
	public boolean createOrUpdateStorageRow(String token,EcmStorageRow storageRow) {
		int c=0;
		if(storageRow.getId()!=null&&!"".equals(storageRow.getId())) {
			c=storageRowDao.updateByPrimaryKeySelective(storageRow);
		}else {
			storageRow.createId();
			c= storageRowDao.insertSelective(storageRow);
			
		}
		if(c==1) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public List<Map<String,Object>> getAllStorageRows(String token,String column) {
		String sql="select ID, CODING, PARENT_CODING, TOTAL_LENGTH, REMAIN_LENGTH,"
				+ " ARCHIVE_COUNT, ITEM_COUNT, DESCRIPTION," + 
				" STAUTS from ecm_storage_row where PARENT_CODING='"+column+"'";
		return storageRowDao.executeSQL(sql);
		
	}
	
	public List<Map<String,Object>> getAllStorageRows(String token,Pager pager,String column) {
		String sql="select ID, CODING, PARENT_CODING, TOTAL_LENGTH, REMAIN_LENGTH,"
				+ " ARCHIVE_COUNT, ITEM_COUNT, DESCRIPTION," + 
				" STAUTS from ecm_storage_row where PARENT_CODING='"+column+"'";
		return storageRowDao.executeSQL(pager,sql);
		
	}
	
	public List<Map<String,Object>> getRowById(String token,String id) {
		String sql="select ID, CODING, PARENT_CODING, TOTAL_LENGTH, REMAIN_LENGTH,"
				+ " ARCHIVE_COUNT, ITEM_COUNT, DESCRIPTION," + 
				" STAUTS from ecm_storage_row where ID='"+id+"'";
		return storageRowDao.executeSQL(sql);
		
	}
	
	public boolean deleteStorageRow(String token,String id) {
		int c= storageRowDao.deleteByPrimaryKey(id);
		if(c==1) {
			return true;
		}
		return false;
	}
}
