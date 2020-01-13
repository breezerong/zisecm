package org.zisecm.extinterface.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zisecm.extinterface.model.Result;
import org.zisecm.extinterface.security.SecurityUtils;

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.RelationService;
import com.ecm.core.service.UserService;
import com.ecm.icore.service.IEcmSession;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@RestController
@Api(value = "测试接口", tags = "DataOperatorController", description = "测试接口相关")

@RequestMapping("/rest")
public class DataOperatorController {
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private DocumentService documentService;
	@Autowired
    private UserService userService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderPathService folderPathService;
	@Autowired
	private Environment env;
	@Autowired
	private AuthService authService;
	@ApiOperation(value = "创建文档", authorizations = {@Authorization(value="basicAuth")})
	@GetMapping("/greeting")
	@ResponseBody
    public String greeting() {
        return "Hello,World!";
    }
	
	/**
	 * 创建案卷或文件
	 * @param metaData json格式的字符串{TITLE:XXXXX,CODING:XXXX,TYPE_NAME:文书文件}
	 * @param uploadFile 文件
	 * @return 返回一个json {id:刚创建的文件ID,code:1代表成功,0代表失败,message:返回信息，成功或异常信息}
	 * @throws Exception
	 */
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "metaData", value = "metaData json格式的字符串{'TITLE':'XXXXX','CODING':'XXXX','TYPE_NAME':'文书文件'}", 
				required = true, dataType = "String",paramType="form")
		}	
	)
//    @ApiOperation(value = "创建文档", notes = "创建文档")
//	@ApiResponses({
//	    @ApiResponse(code=1,message="成功",responseContainer="Map"),
//	    @ApiResponse(code=0,message="失败")
//	})
	@PostMapping(value = "/createDocument",consumes="multipart/*",headers="content-type=multipart/form-data")
	@ApiOperation(value = "创建文档", authorizations = {@Authorization(value="basicAuth")})
	@ResponseBody
	public Result createDocument(
			@RequestParam(name="metaData",required=true) String metaData, 
			@RequestParam(name="uploadFile")MultipartFile uploadFile) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmContent en = null; 
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		if (uploadFile != null) {
			en = new EcmContent();
			en.setName(uploadFile.getOriginalFilename());
			en.setContentSize(uploadFile.getSize());
			en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
			en.setInputStream(uploadFile.getInputStream());
		}
		
		User user= SecurityUtils.getUser();
		
		EcmUser ecmuser= userService.getObjectByLoginName("", user.getUsername());
		IEcmSession ecmSession = null;
		String token="";
		try {
			ecmSession = authService.login("extinterface", ecmuser.getLoginName(), ecmuser.getPassword());
			token=ecmSession.getToken();
			String folderId= folderPathService.getFolderByPath(token, env.getProperty("ecm.restpath"));
			
			EcmFolder folder= folderService.getObjectById(token, folderId);
			doc.setFolderId(folderId);
			doc.setAclName(folder.getAclName());
			
			String id ="";
			if(args.get("transferId")!=null&&!"".equals(args.get("transferId"))) {
				id = documentService.newObject(token,doc,en);
				EcmRelation relation=new EcmRelation();
				relation.setParentId(args.get("transferId").toString());
				
				relation.setChildId(id);
				relation.setName("irel_children");
				try {
					relationService.newObject(token, relation);
				} catch (EcmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mp.put("code", ActionContext.FAILURE);
					mp.put("message",e.getMessage());
					Result rs=new Result(ActionContext.FAILURE, e.getMessage(), null);
					return rs;
				}
			}else {
				id= documentService.newObject(token,doc,en);
			}
			
			
			mp.put("code", ActionContext.SUCESS);
			mp.put("id", id);
//			return mp;
			Result rs=new Result(ActionContext.SUCESS, "操作成功", null);
			return rs;
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mp.put("message", e.getMessage());
			mp.put("code", ActionContext.FAILURE);
//			return mp;
			Result rs=new Result(ActionContext.FAILURE, e.getMessage(), null);
			return rs;
		}finally {
			if(ecmSession!=null) {
				authService.logout(ecmSession.getToken());
			}
		}
		
		
		
	}
	
}
