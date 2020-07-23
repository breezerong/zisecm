package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ecm.cnpe.exchange.service.impl.IEDImportService;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.ExcSynBatch;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.QueryService;
import com.ecm.core.service.RelationService;
import com.ecm.portal.archive.services.ArchiveFolderService;
import com.ecm.portal.archive.services.ArchiveRelationService;
import com.ecm.portal.archive.services.ImportService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.core.service.ExcSynBatchService;

@RestController

public class ExcSynBatchController  extends ControllerAbstract {	
	@Autowired
	private ExcSynBatchService batchService;
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/ied/getBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> IEDReport(@RequestBody String argStr) throws Exception {
		
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		List<Map<String, Object> > outList = new ArrayList<Map<String, Object>>();
		Map<String, Object> mp = new HashMap<String, Object>();
		//String projectname = args.get("C_PROJECTNAME").toString();
		String name = args.get("NAME").toString();
		//System.out.println("取到的数据是"+projectname);
		List<ExcSynBatch> projList = batchService.selectAll();
		/*System.out.println("取到的List大小:"+projList.size());
		for(int i=0;i<projList.size();i++) {
			mp.put("'"+i+"'", projList);
		}*/
		mp.put("data", projList);
		return mp;
	
	}
}
