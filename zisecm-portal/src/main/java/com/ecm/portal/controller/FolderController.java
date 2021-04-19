package com.ecm.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.PermissionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmPermit;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.AclService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderService;

/**
 * 文件夹控制器
 * 
 * @author Haihong Rong
 *
 */
@Controller
public class FolderController  extends ControllerAbstract {


	@Autowired
	private FolderService folderService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private AclService aclService;
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/folder/getFolderByConfige", method = RequestMethod.POST)
	public Map<String, Object> getFolderByConfige(@RequestBody String param) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = CacheManagerOper.getEcmParameters().get(param).getValue();
			EcmFolder folder = folderService.getObjectById(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", folder);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/admin/getFolder", method = RequestMethod.POST)
	public Map<String, Object> getFolder(@RequestBody String parentId) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmFolder> list = folderService.getFoldersByParentId(getToken(), parentId);
			
			int permit = folderService.getPermit(getToken(), parentId);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
			mp.put("permit", permit);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
			mp.put("permit", 1);
		}
		return mp;
	}
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/admin/getArchivesFolder", method = RequestMethod.POST)
	public Map<String, Object> getArchivesFolder(@RequestBody String parentId) {
		List<EcmFolder> list = null;
		try {
			list = folderService.getFoldersByParentPath(getToken(),"/档案库");
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}

	@ResponseBody
	@RequestMapping(value="/admin/getInstituteFolder", method = RequestMethod.POST)
	public Map<String, Object> getInstituteFolder(@RequestBody String parentId) {
		List<EcmFolder> list = null;
		try {
			list = folderService.getFoldersByParentPaths(getToken(),"'/院文件','/院档案'");
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/admin/getPreArchivesFolder", method = RequestMethod.POST)
	public Map<String, Object> getPreArchivesFolder(@RequestBody String parentId) {
		List<EcmFolder> list = null;
		try {
			list = folderService.getFoldersByParentPath(getToken(),"/预归档库");
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}
	
	/**
	 * 获取所有表单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/admin/getFoldersByPath", method = RequestMethod.POST)
	public Map<String, Object> getFoldersByPath(@RequestBody String folderPath) {
		List<EcmFolder> list = null;
		try {
			list = folderService.getFoldersByParentPath(getToken(),folderPath);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}

	

	/**
	 * 更新表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 */
	@RequestMapping(value = "/admin/updateFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFolder(@RequestBody EcmFolder obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			folderService.updateObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	/**
	 * 删除表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/admin/deleteFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFolder(@RequestBody EcmFolder obj) throws Exception {
		int code =1;
		String msg ="";
		if(folderService.getFolderCount(getToken(), obj.getId())>0)
		{
			code = 2;
			msg="文件夹包含文件夹，请先删除文件夹。";
		}
		else if(documentService.getObjectCount(getToken(),
				null,  obj.getId(), null)>0)
		{
			code = 3;
			msg="文件夹包含文件，请先删除文件。";
		}
		else {
			try {
				folderService.deleteObject(getToken(), obj);
			} catch (NoPermissionException e) {
				code = 4;
				msg="您没有权限删除该文件夹";
			}
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", code);
		mp.put("msg", msg);
		return mp;
	}

	/**
	 * 新建表单
	 * 
	 * @param obj
	 *            表单实体
	 * @return
	 */
	@RequestMapping(value = "/admin/newFolder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newFolder(@RequestBody EcmFolder obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = folderService.newObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data",id);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data",e.getMessage());
		}
		return mp;
	}

	@ResponseBody
	@RequestMapping(value="/folder/getFolderById", method = RequestMethod.POST)
	public Map<String, Object> getFolderById(@RequestBody String id) {
		EcmFolder folder;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			folder = folderService.getObjectById(getToken(),id);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", folder);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} //Integer.parseInt(parentId));
		return mp;
	}
	/**
	 * 批量授权
	 * @author trr
	 * @param argsStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/folder/grantPermitBatch", method = RequestMethod.POST)
	public Map<String, Object> grantPermitBatch(@RequestBody String argsStr) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String,Object> data=JSONUtils.stringToMap(argsStr);
			if(data.get("objIds")==null) {
				mp.put("code", ActionContext.FAILURE);
				mp.put("msg", "请选择要授权的对象");
				return mp;
			}
			boolean isUpdateChild=false;
			if(data.get("isUpdateChild")!=null) {
				isUpdateChild=Boolean.parseBoolean(data.get("isUpdateChild").toString());
			}
			String objIds=data.get("objIds").toString();//需要授权的对象
			String permitsStr=data.get("data").toString();//权限
			/**************************文件夹授权*********************************/
			List<String> idList=JSONUtils.stringToArray(objIds);
			List<String> permitsList=JSONUtils.stringToArray(permitsStr);
			
			for(int j=0;permitsList!=null&&j<permitsList.size();j++) {
				String permitStr= permitsList.get(j);
				Map<String,Object> permitMap =JSONUtils.stringToMap(permitStr);
				EcmPermit permit=new EcmPermit();
				permit.setTargetName(permitMap.get("targetName").toString());
				permit.setTargetType(Integer.parseInt(permitMap.get("targetType").toString()));
				permit.setPermission(Integer.parseInt(permitMap.get("permission").toString()));
				permit.setExpireDate(new Date());
				
				for(int i=0;idList!=null&&i<idList.size();i++) {
					String id= idList.get(i);
					String oldAclName = "";
					String newAclName = "";
					EcmFolder folder = folderService.getObjectById(getToken(), id);
					oldAclName = folder.getAclName();
					//文件夹ACL不为空
					if(needNewAclByAclName(oldAclName)) {
						EcmAcl acl = aclService.getObjectByName(getToken(), oldAclName);
						if (acl != null) {
							acl = aclService.copy(getToken(), acl, null, null);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						}else {
							acl = new EcmAcl();
							acl.createId();
							acl.setName("ecm_" + acl.getId());
							aclService.newObject(getToken(), acl);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						}
						
						
					}else {
						if(StringUtils.isEmpty(oldAclName)) {
							EcmAcl acl = new EcmAcl();
							acl.createId();
							acl.setName("ecm_" + acl.getId());
							aclService.newObject(getToken(), acl);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						}
						else {
							//直接在原来的基础上修改
							EcmAcl acl = aclService.getObjectByName(getToken(), oldAclName);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						}
					}
					
					if(!oldAclName.equals(newAclName)) {
						folder.setAclName(newAclName);
						folderService.updateObject(getToken(), folder);
					}
					if(isUpdateChild) {
						grantPermitDeep(oldAclName,newAclName,id, permit);
					}
					
				}
				
			}
			
			////////////////////////////end文件夹授权//////////////////////////
			
			mp.put("data", "授权成功");
			mp.put("code", ActionContext.SUCESS);
			
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("data", e.getMessage());
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} 
		return mp;
	
		
	}
	@ResponseBody
	@RequestMapping(value = "/folder/grantPermit", method = RequestMethod.POST)
	public Map<String, Object> grantPermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(permit.getTargetName())) {
				mp.put("code", ActionContext.FAILURE);
			} else {
				String aclName = "";
				String[] targetNames = permit.getTargetName().split(";");
				 
				for (String targetName : targetNames) {
					if (permit.getTargetType() == 1) {
						aclName = folderService.grantUser(getToken(), permit.getParentId(), targetName,
								permit.getPermission(), permit.getExpireDate(), needNewAcl(permit.getParentId(),0));
					} else {
						aclName = folderService.grantGroup(getToken(), permit.getParentId(), targetName,
								permit.getPermission(), permit.getExpireDate(), needNewAcl(permit.getParentId(),0));
					}
				}
				mp.put("data", aclName);
				mp.put("code", ActionContext.SUCESS);
			}
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("data", e.getMessage());
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		return mp;
	}

	@ResponseBody
	@RequestMapping(value = "/folder/revokePermit", method = RequestMethod.POST)
	public Map<String, Object> revokePermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String aclName = "";
			if (permit.getTargetType() == 1) {
				aclName = folderService.revokeUser(getToken(), permit.getParentId(), permit.getTargetName(),
						needNewAcl(permit.getParentId(),0));
			} else {
				aclName = folderService.revokeGroup(getToken(), permit.getParentId(), permit.getTargetName(),
						needNewAcl(permit.getParentId(),0));
			}
			mp.put("data", aclName);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("data", e.getMessage());
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/folder/batchGrantPermit", method = RequestMethod.POST)
	public Map<String, Object> batchGrantPermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(permit.getTargetName())) {
				mp.put("code", ActionContext.FAILURE);
				mp.put("data", "Target Name is null.");
			} else {
				String folderId = permit.getParentId();
				if(StringUtils.isEmpty(folderId)) {
					mp.put("code", ActionContext.FAILURE);
					mp.put("data", "Folder Id is null.");
				}else {
					String oldAclName = "";
					String newAclName = "";
					EcmFolder folder = folderService.getObjectById(getToken(), folderId);
					oldAclName = folder.getAclName();
					//文件夹ACL不为空
					if(needNewAclByAclName(oldAclName))
					{
						EcmAcl acl = aclService.getObjectByName(getToken(), oldAclName);
						if (acl != null) {
							acl = aclService.copy(getToken(), acl, null, null);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						} else {
							acl = new EcmAcl();
							acl.createId();
							acl.setName("ecm_" + acl.getId());
							aclService.newObject(getToken(), acl);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						}
					}
					else {
						if(StringUtils.isEmpty(oldAclName)) {
							EcmAcl acl = new EcmAcl();
							acl.createId();
							acl.setName("ecm_" + acl.getId());
							aclService.newObject(getToken(), acl);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						}
						else {
							//直接在原来的基础上修改
							EcmAcl acl = aclService.getObjectByName(getToken(), oldAclName);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}else {
								aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
							}
							newAclName = acl.getName();
						}
					}
					if(!oldAclName.equals(newAclName)) {
						folder.setAclName(newAclName);
						folderService.updateObject(getToken(), folder);
					}
					grantPermitDeep(oldAclName,newAclName,folderId, permit);
					mp.put("data", newAclName);
					mp.put("code", ActionContext.SUCESS);
				}
			}
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("data", e.getMessage());
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		return mp;
	}
	
	private void grantPermitDeep(String oldAclName,String newAclName,String folderId, EcmPermit permit) throws AccessDeniedException, EcmException, NoPermissionException {
		List<EcmFolder> folderList = folderService.getFoldersByParentId(getToken(), folderId);
		for(EcmFolder folder: folderList) {
			
			String folderAclName = folder.getAclName();
			//与以前Acl名称相同，直接设置为新Acl名称
			if(folderAclName !=null && folderAclName.equals(oldAclName)) {
				//Acl已经改变，需要更新文件夹ACL
				if(!newAclName.equals(oldAclName)) {
					folder.setAclName(newAclName);
					folderService.updateObject(getToken(), folder);
				}
			}else {
				EcmAcl acl = aclService.getObjectByName(getToken(), folderAclName);
				//设置了ACL
				if (acl != null) {
					acl = aclService.copy(getToken(), acl, null, null);
					if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
						aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
					}else {
						aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
					}
					folderAclName = acl.getName();
				} else {
					acl = new EcmAcl();
					acl.createId();
					acl.setName("ecm_" + acl.getId());
					aclService.newObject(getToken(), acl);
					if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
						aclService.grantUser(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
					}else {
						aclService.grantGroup(getToken(), acl.getId(), permit.getTargetName(), permit.getPermission(), permit.getExpireDate());
					}
					folderAclName = acl.getName();
				}
			}
			//递归更新子文件夹
			grantPermitDeep(oldAclName,newAclName,folder.getId(), permit);
		}
		//更新文件夹中文件权限
		String sql = "select ID,ACL_NAME from ecm_document where FOLDER_ID='"+folderId+"'";
		List<Map<String, Object>> docList = documentService.getMapList(getToken(), sql);
		for(Map<String, Object> docMap: docList) {
			String id = docMap.get("ID").toString();
			String aclName = (String)docMap.get("ACL_NAME");
			//旧ACl名称相同，修改ACL名称即可
			if(oldAclName.equals(aclName) && !newAclName.equals(aclName)) {
				EcmDocument doc = documentService.getObjectById(getToken(), id);
				doc.setAclName(newAclName);
				documentService.updateObject(getToken(), doc, null);
			}
			//只有ACL不同才修改
			else if(!newAclName.equals(aclName)) {
				String[] targetNames = permit.getTargetName().split(";");
				for (String targetName : targetNames) {
					if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
						documentService.grantUser(getToken(), id, targetName,
								permit.getPermission(), permit.getExpireDate(), needNewAcl(id,1));
					} else {
						documentService.grantGroup(getToken(), id, targetName,
								permit.getPermission(), permit.getExpireDate(), needNewAcl(id,1));
					}
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/folder/batchRevokePermit", method = RequestMethod.POST)
	public Map<String, Object> batchRevokePermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(permit.getTargetName())) {
				mp.put("code", ActionContext.FAILURE);
				mp.put("data", "Target Name is null.");
			} else {
				String folderId = permit.getParentId();
				if(StringUtils.isEmpty(folderId)) {
					mp.put("code", ActionContext.FAILURE);
					mp.put("data", "Folder Id is null.");
				}else {
					String oldAclName = "";
					String newAclName = "";
					EcmFolder folder = folderService.getObjectById(getToken(), folderId);
					oldAclName = folder.getAclName();
					//文件夹ACL不为空
					if(needNewAclByAclName(oldAclName))
					{
						EcmAcl acl = aclService.getObjectByName(getToken(), oldAclName);
						if (acl != null) {
							acl = aclService.copy(getToken(), acl, null, null);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.revokeUser(getToken(), acl.getId(), permit.getTargetName());
							}else {
								aclService.revokeGroup(getToken(), acl.getId(), permit.getTargetName());
							}
							newAclName = acl.getName();
						} else {
							acl = new EcmAcl();
							acl.createId();
							acl.setName("ecm_" + acl.getId());
							aclService.newObject(getToken(), acl);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.revokeUser(getToken(), acl.getId(), permit.getTargetName());
							}else {
								aclService.revokeGroup(getToken(), acl.getId(), permit.getTargetName());
							}
							newAclName = acl.getName();
						}
					}
					else {
						if(StringUtils.isEmpty(oldAclName)) {
							EcmAcl acl = new EcmAcl();
							acl.createId();
							acl.setName("ecm_" + acl.getId());
							aclService.newObject(getToken(), acl);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.revokeUser(getToken(), acl.getId(), permit.getTargetName());
							}else {
								aclService.revokeGroup(getToken(), acl.getId(), permit.getTargetName());
							}
							newAclName = acl.getName();
						}
						else {
							//直接在原来的基础上修改
							EcmAcl acl = aclService.getObjectByName(getToken(), oldAclName);
							if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
								aclService.revokeUser(getToken(), acl.getId(), permit.getTargetName());
							}else {
								aclService.revokeGroup(getToken(), acl.getId(), permit.getTargetName());
							}
							newAclName = acl.getName();
						}
					}
					revokePermitDeep(oldAclName,newAclName,folderId, permit);
					mp.put("data", newAclName);
					mp.put("code", ActionContext.SUCESS);
				}
			}
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("data", e.getMessage());
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		return mp;
	}
	
	private void revokePermitDeep(String oldAclName,String newAclName,String folderId, EcmPermit permit) throws Exception {
		List<EcmFolder> folderList = folderService.getFoldersByParentId(getToken(), folderId);
		for(EcmFolder folder: folderList) {
			
			String folderAclName = folder.getAclName();
			//与以前Acl名称相同，直接设置为新Acl名称
			if(folderAclName !=null && folderAclName.equals(oldAclName)) {
				//Acl已经改变，需要更新文件夹ACL
				if(!newAclName.equals(oldAclName)) {
					folder.setAclName(newAclName);
					folderService.updateObject(getToken(), folder);
				}
			}else {
				EcmAcl acl = aclService.getObjectByName(getToken(), folderAclName);
				//设置了ACL
				if (acl != null) {
					acl = aclService.copy(getToken(), acl, null, null);
					if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
						aclService.revokeUser(getToken(), acl.getId(), permit.getTargetName());
					}else {
						aclService.revokeGroup(getToken(), acl.getId(), permit.getTargetName());
					}
					folderAclName = acl.getName();
				} else {
					acl = new EcmAcl();
					acl.createId();
					acl.setName("ecm_" + acl.getId());
					aclService.newObject(getToken(), acl);
					if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
						aclService.revokeUser(getToken(), acl.getId(), permit.getTargetName());
					}else {
						aclService.revokeGroup(getToken(), acl.getId(), permit.getTargetName());
					}
					folderAclName = acl.getName();
				}
			}
			//递归更新子文件夹
			revokePermitDeep(oldAclName,newAclName,folder.getId(), permit);
			
			
			
		}
		//更新文件夹中文件权限
		String sql = "select ID,ACL_NAME from ecm_document where FOLDER_ID='"+folderId+"'";
		List<Map<String, Object>> docList = documentService.getMapList(getToken(), sql);
		for(Map<String, Object> docMap: docList) {
			String id = docMap.get("ID").toString();
			String aclName = (String)docMap.get("ACL_NAME");
			//旧ACl名称相同，修改ACL名称即可
			if(oldAclName.equals(aclName) && !newAclName.equals(aclName)) {
				EcmDocument doc = documentService.getObjectById(getToken(), id);
				doc.setAclName(newAclName);
				documentService.updateObject(getToken(), doc, null);
			}
			//只有ACL不同才修改
			else if(!newAclName.equals(aclName)) {
				String[] targetNames = permit.getTargetName().split(";");
				for (String targetName : targetNames) {
					if(permit.getTargetType()==PermissionContext.USER_TARGET_TYPE) {
						documentService.revokeUser(getToken(), id, targetName, needNewAcl(id,1));
					} else {
						documentService.revokeGroup(getToken(), id, targetName, needNewAcl(id,1));
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @param type 0:文件夹，1:文档
	 * @return
	 */
	private boolean needNewAcl(String id,int type) {
		try {
			String aclName = "";
			if(type==0) {
				EcmFolder folder = folderService.getObjectById(getToken(), id);
				aclName = folder.getAclName();
			}else {
				EcmDocument doc = documentService.getObjectById(getToken(), id);
				aclName = doc.getAclName();
			}
			if (StringUtils.isEmpty(aclName) || !aclName.startsWith("ecm_")) {
				return true;
			}
//			String sql = "select count(*) as ACLCOUNT from ecm_document where ACL_NAME='" + aclName + "'"
//					+"union select count(*) as ACLCOUNT from ecm_folder where ACL_NAME='"+ aclName +"'";
			String sql = "select sum(ACLCOUNT) ACLCOUNT from(select count(*) as ACLCOUNT from ecm_document where ACL_NAME='" + aclName + "'"
					+" union all select count(*) as ACLCOUNT from ecm_folder where ACL_NAME='"+ aclName +"') t";
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql);
			if (list != null && list.size() > 0) {
				return Integer.parseInt(list.get(0).get("ACLCOUNT").toString()) > 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean needNewAclByAclName(String aclName) {
		try {
			if (StringUtils.isEmpty(aclName) || !aclName.startsWith("ecm_")) {
				return true;
			}
//			String sql = "select count(*) as ACLCOUNT from ecm_document where ACL_NAME='" + aclName + "'";
			String sql = "select sum(ACLCOUNT) ACLCOUNT from(select count(*) as ACLCOUNT from ecm_document where ACL_NAME='" + aclName + "'"
					+" union all select count(*) as ACLCOUNT from ecm_folder where ACL_NAME='"+ aclName +"') t";
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql);
			if (list != null && list.size() > 0) {
				return Integer.parseInt(list.get(0).get("ACLCOUNT").toString()) > 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
