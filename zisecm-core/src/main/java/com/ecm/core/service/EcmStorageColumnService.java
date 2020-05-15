package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmStorageColumnMapper;
import com.ecm.core.entity.EcmStorageColumn;

@Service
@Scope("prototype")
public class EcmStorageColumnService {
	@Autowired
	private EcmStorageColumnMapper storageColumnDao;
	public boolean createOrUpdateStorageColumn(String token,EcmStorageColumn storageColumn) {
		int c=0;
		if(storageColumn.getId()!=null&&!"".equals(storageColumn.getId())) {
			c= storageColumnDao.updateByPrimaryKeySelective(storageColumn);
		}else {
			storageColumn.createId();
			c= storageColumnDao.insertSelective(storageColumn);
		}
		
		if(c==1) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	public List<Map<String,Object>> getAllStorageColumn(String token) {
		String sql="select ID, CODING, PARENT_CODING, TOTAL_LENGTH, "
				+ "REMAIN_LENGTH, ARCHIVE_COUNT, DESCRIPTION," + 
				" STAUTS from ecm_storage_column";
		return storageColumnDao.executeSQL(sql);
		
	}
	
	public List<Map<String,Object>> getColumnByStorageRoom(String token,String storageRoomCoding) {
		String sql="select ID, CODING, PARENT_CODING, TOTAL_LENGTH, REMAIN_LENGTH,"
				+ " ARCHIVE_COUNT, DESCRIPTION," + 
				" STAUTS from ecm_storage_column where PARENT_CODING='"+storageRoomCoding+"' order by CODING asc";
		return storageColumnDao.executeSQL(sql);
		
	}
	
	public List<Map<String,Object>> getColumnById(String token,String id) {
		String sql="select ID, CODING, PARENT_CODING, TOTAL_LENGTH, REMAIN_LENGTH,"
				+ " ARCHIVE_COUNT, DESCRIPTION," + 
				" STAUTS from ecm_storage_column where ID='"+id+"' order by CODING asc";
		return storageColumnDao.executeSQL(sql);
		
	}
	
	public boolean deleteStorageColumn(String token,String id) {
		int c= storageColumnDao.deleteByPrimaryKey(id);
		if(c==1) {
			return true;
		}
		return false;
	}
}
