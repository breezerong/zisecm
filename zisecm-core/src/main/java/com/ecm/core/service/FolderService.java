package com.ecm.core.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmFolder;
import com.ecm.icore.service.IFolderService;
@Service
@Scope("prototype")
public class FolderService extends EcmObjectService<EcmFolder> implements IFolderService{
	
	

	@Autowired
	private EcmFolderMapper ecmFolderMapper;
	

	@Override
	public long getFolderCount(String token, String folderId) {
		String sql = "select count(*) as itemcount from ecm_folder where parent_id='"+folderId+"'";
		
		List<Map<String, Object>> list = ecmFolderMapper.searchToMap(sql);
		
		return (long) list.get(0).get("itemcount");
	}

}
