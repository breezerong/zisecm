package com.ecm.core.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.util.DBUtils;
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
	@Override
	public EcmFolder getObjectById(String token,String id) {
		return ecmFolderMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public String newObject(String token,Object obj) {
		EcmFolder folder =(EcmFolder)obj;
		folder.createId();
		folder.setFolderPath(getFullPath(token,folder,folder.getName()));
		ecmFolderMapper.insert(folder);
		return folder.getId();
	}
	
	@Override
	public EcmFolder getObjectByName(String token,String name, String parentId) {
		String cond = " NAME='"+DBUtils.getString(name)+"' and PARENT_ID='"+DBUtils.getString(parentId)+"'";
		List<EcmFolder> list = ecmFolderMapper.selectByCondition(cond);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateObject(String token,Object folder) {
		EcmFolder newFolder = (EcmFolder)folder;
		EcmFolder en = getObjectById( token,newFolder.getId());
		boolean needUpdateChild = false;
		if(en != null) {
			//如果文件夹名修改，需要修改全路径
			if(!en.getName().equals(newFolder.getName())) {
				newFolder.setFolderPath(getFullPath(token,newFolder,newFolder.getName()));
				needUpdateChild = true;
			}
		}
		ecmFolderMapper.updateByPrimaryKey(newFolder);
		if(needUpdateChild) {
			updateChildFolderPath( token, newFolder.getId());
		}
		return true;
	}
	/**
	 * 递归更新子文件夹Path
	 * @param token
	 * @param parentId
	 */
	private void updateChildFolderPath(String token,String parentId) {
		 List<EcmFolder> list = getFoldersByParentId( token, parentId);
		 for(EcmFolder folder: list) {
			 folder.setFolderPath(getFullPath(token,folder,folder.getName()));
			 ecmFolderMapper.updateByPrimaryKey(folder);
			 updateChildFolderPath( token, folder.getId());
		 }
	}
	
	/**
	 * 获取全路径
	 * @param token
	 * @param folder
	 * @param path
	 * @return
	 */
	private String getFullPath(String token,EcmFolder folder,String path) {
		if(StringUtils.isEmpty(folder.getParentId())||folder.getParentId().equals("0")) {
			return "/"+path;
		}
		EcmFolder en = getObjectById( token,folder.getParentId());
		if(en!=null) {
			path =en.getName()+"/"+path;
			path = getFullPath(token, en, path);
		}
		return path;
	}
	
	@Override
	public boolean deleteObject(String token,Object folder) {
		EcmFolder fld = (EcmFolder)folder;
		return ecmFolderMapper.deleteByPrimaryKey(fld.getId())>0;
	}
	@Override
	public EcmFolder getObjectByPath(String token,String folderPath) {
		String cond = " FOLDER_PATH='"+DBUtils.getString(folderPath)+"'";
		List<EcmFolder> list = ecmFolderMapper.selectByCondition(cond);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<EcmFolder> getFoldersByParentId(String token,String parentId){
		return ecmFolderMapper.selectByParentId(parentId);
	}
	@Override
	public List<EcmFolder> getFoldersByParentPath(String token,String path){
		EcmFolder folder = getObjectByPath(token,path);
		List<EcmFolder> list = new ArrayList<EcmFolder>();
		if(folder != null) {
			list = ecmFolderMapper.selectByParentId(folder.getId());
		}
		return list;
	}
	@Override
	public boolean copyFolders(String token, String sourceId, String targetId,boolean includeSource) {
		if(includeSource) {
			EcmFolder folder = getObjectById( token,sourceId);
			folder.createId();
			folder.setParentId(targetId);
			ecmFolderMapper.insert(folder);
			copyChildFolders(token,sourceId, folder.getId());
		}else {
			copyChildFolders(token,sourceId, targetId);
		}
		return true;
	}
	
	private void copyChildFolders(String token, String parentId, String targetId) {
		List<EcmFolder> list = this.getFoldersByParentId(token, parentId);
		for(EcmFolder folder: list) {
			String id = folder.getId();
			folder.createId();
			folder.setParentId(targetId);
			ecmFolderMapper.insert(folder);
			copyChildFolders(token, id,  folder.getId());
		}
	}
	
	
}
