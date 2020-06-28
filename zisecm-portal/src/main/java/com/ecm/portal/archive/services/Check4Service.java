package com.ecm.portal.archive.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.QueryService;
import com.ecm.portal.archive.entity.EcmCheck4;

@Service
public class Check4Service {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private QueryService queryService;

	public List<EcmCheck4> startCheck4(String token, String calssic, String startDate, String endDate)
			throws EcmException {
		List<EcmCheck4> list = new ArrayList<EcmCheck4>();
		String sql = "Select ID,CODING,NAME,REVISION,C_ARCHIVE_NUM,C_ARCHIVE_NUM,FORMAT_NAME,CONTENT_SIZE,C_RESOURCE,C_MD5,C_VIRUS_CHECK from ecm_document where STATUS = '利用'";
		if (!StringUtils.isEmpty(calssic)) {
			sql += " and C_ARC_CLASSIC='" + calssic + "'";
		}
		if (!StringUtils.isEmpty(startDate)) {
			sql += " and C_DOC_DATE>='" + startDate + "'";
		}
		if (!StringUtils.isEmpty(endDate)) {
			sql += " and C_DOC_DATE<='" + endDate + "'";
		}

		List<Map<String, Object>> listDoc = queryService.executeSQL(token, sql);
		if (listDoc != null) {
			for (Map<String, Object> m : listDoc) {
				try {
					EcmCheck4 en = new EcmCheck4();
					en.setId(m.get("ID").toString());
					en.setCoding((String) m.get("CODING"));
					en.setTitle((String) m.get("NAME"));
					en.setRevision((String) m.get("REVISION"));
					en.setArchiveCoding((String) m.get("C_ARCHIVE_NUM"));
					// 来源
					en.setTrueSource((String) m.get("C_RESOURCE"));
					String archNum = (String) m.get("C_ARCHIVE_NUM");
					String title = (String) m.get("NAME");
					if (StringUtils.isEmpty(archNum) || StringUtils.isEmpty(title)) {
						en.setTrueMetaData("未通过");
						en.setTotalResult("未通过");
					}
					String md5 = (String) m.get("C_MD5");
					if (!checkMetaData(token, en.getId())) {
						en.setIntegrityMetaData("未通过");
						en.setTotalResult("未通过");
					}
					long contentSize = 0;
					if(m.get("CONTENT_SIZE")!=null) {
						contentSize = Long.parseLong(m.get("CONTENT_SIZE").toString());
					}
					if(contentSize>0) {
						EcmContent content = contentService.getObject(token, en.getId(), 1, (String) m.get("FORMAT_NAME"));
						if (content != null) {
							String filePath = CacheManagerOper.getEcmStores().get(content.getStoreName()).getStorePath();
							filePath += content.getFilePath();
							File f = new File(filePath);
							if (contentSize == f.length()) {
								en.setTrueContent("通过");
								if (!StringUtils.isEmpty(md5) && !checkMd5(filePath, md5)) {
									en.setTrueContent("未通过");
									en.setTotalResult("未通过");
									en.setUseContent("未通过");
								}else if(StringUtils.isEmpty(md5)) {
									en.setTrueContent("无MD5");
								}
							} else {
								en.setTrueContent("未通过");
								en.setTotalResult("未通过");
								en.setUseContent("未通过");
							}
						} else {
							en.setTrueContent("未通过");
							en.setTotalResult("未通过");
							en.setUseContent("未通过");
						}
					}else {
						en.setTrueContent("无电子文件");
						en.setUseContent("无电子文件");
					}
					en.setIntegrityContent((String) m.get("FORMAT_NAME"));
					en.setSecurityVirus((String) m.get("C_VIRUS_CHECK"));
					if ("否".equals(en.getSecurityVirus())) {
						en.setTotalResult("未通过");
					}
	
					list.add(en);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private boolean checkMd5(String file, String md5) {
		FileInputStream is;
		try {
			is = new FileInputStream(file);
			String str = DigestUtils.md5DigestAsHex(is);
			is.close();
			return str.equalsIgnoreCase(md5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean checkMetaData(String token, String id) {
		EcmDocument doc = documentService.getObjectById(token, id);
		String typeName = doc.getTypeName();
		EcmForm form = CacheManagerOper.getEcmForms().get(doc.getTypeName() + "_NEW");
		for (EcmFormItem item : form.getEcmFormItems()) {
			if (item.getRequired()) {
				Object o = doc.getAttributes().get(item.getAttrName());
				if (o == null) {
					return false;
				} else if (o.toString().trim().length() == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
