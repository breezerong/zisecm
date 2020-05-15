package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmStorageRoomMapper;
import com.ecm.core.entity.EcmStorageRoom;
import com.ecm.icore.service.IStoreService;
@Service
@Scope("prototype")
public class EcmStorageRoomService  extends EcmObjectService<EcmStorageRoom> implements IStoreService {
	@Autowired
	private EcmStorageRoomMapper storageRoomDao;
	public boolean createOrUpdateStorageRoom(String token,EcmStorageRoom storageRoom) {
		int c=-1;
		if(storageRoom.getId()!=null&&!"".equals(storageRoom.getId())) {
			c=storageRoomDao.updateByPrimaryKeySelective(storageRoom);
		}else {
			storageRoom.createId();
			c= storageRoomDao.insertSelective(storageRoom);
		}
		
		if(c==1) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	public boolean deleteStorageRoom(String token,String id) {
		int c= storageRoomDao.deleteByPrimaryKey(id);
		if(c==1) {
			return true;
		}
		return false;
	}
	
	public List<Map<String,Object>> getAllStorageRoom(String token) {
		String sql="select ID, CODING, ROOM_TYPE, ROOM_AREA, ROOM_FUNCTION, "
				+ "COLUMN_COUNT, DESCRIPTION, STAUTS from ecm_storage_room order by CODING asc";
		return storageRoomDao.executeSQL(sql);
		
	}
	
	public List<Map<String,Object>> getRoomById(String token,String roomId) {
		String sql="select ID, CODING, ROOM_TYPE, ROOM_AREA, ROOM_FUNCTION, "
				+ "COLUMN_COUNT, DESCRIPTION, STAUTS from ecm_storage_room where ID='"+roomId+"' order by CODING asc";
		return storageRoomDao.executeSQL(sql);
		
	}
	
}
