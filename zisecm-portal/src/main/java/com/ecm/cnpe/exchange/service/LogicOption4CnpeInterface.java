package com.ecm.cnpe.exchange.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.RelationService;
@Service
@Scope("prototype")
public class LogicOption4CnpeInterface extends DocumentService{
	@Autowired
	private ContentService contentService;
	@Autowired
	private RelationService relationService;
	/**
	 * 通过接口对象处理接口对象包含（接口信息传递单，接口信息意见单）两种类型
	 * @param List<String> transferIds 传递单ID
	 * @return 是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean interfaceOption(String token,List<String> interFaceIds) throws Exception {
		for(int i=0;i<interFaceIds.size();i++) {
			
			EcmDocument interfaceDoc= getObjectById(token, interFaceIds.get(i).toString());
			EcmDocument icmDoc= getICMS(token,interfaceDoc);
			EcmRelation relation=new EcmRelation();
			relation.setChildId(interfaceDoc.getId());
			relation.setParentId(icmDoc.getId());
			
			if("接口信息传递单".equals(interfaceDoc.getTypeName())) {
				relation.setName("传递接口");
			}else {
				relation.setName("接口意见");
			}
			relationService.newObject(token, relation);
		}
		return true;
		
		
	}
	/**
	 * 通过接口对象处理接口对象包含（接口信息传递单，接口信息意见单）两种类型
	 * @param token
	 * @param interfaceDoc
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean interfaceOption(String token,EcmDocument interfaceDoc) throws Exception {
		EcmDocument icmDoc= getICMS(token,interfaceDoc);
		if(icmDoc==null) {
			throw new Exception("无对应ICM信息（“"+(interfaceDoc.getAttributeValue("C_CODE1")==null?""
					:interfaceDoc.getAttributeValue("C_CODE1").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE2")==null?""
							:interfaceDoc.getAttributeValue("C_CODE2").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE3")==null?""
							:interfaceDoc.getAttributeValue("C_CODE3").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE4")==null?""
							:interfaceDoc.getAttributeValue("C_CODE4").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE5")==null?""
							:interfaceDoc.getAttributeValue("C_CODE5").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE6")==null?""
							:interfaceDoc.getAttributeValue("C_CODE6").toString())
					+"”）");
		}
		EcmRelation relation=new EcmRelation();
		relation.setChildId(interfaceDoc.getId());
		relation.setParentId(icmDoc.getId());
		
		if("接口信息传递单".equals(interfaceDoc.getTypeName())) {
			relation.setName("传递接口");
		}else {
			relation.setName("接口意见");
		}
		relationService.newObject(token, relation);
		return true;
		
		
	}
	
	/**
	 * 通过接口对象验证接口对象包含（接口信息传递单，接口信息意见单）两种类型是否存在
	 * @param token
	 * @param interfaceDoc
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean interfaceValidateOption(String token,EcmDocument interfaceDoc) throws Exception {
		EcmDocument icmDoc= getICMS(token,interfaceDoc);
		if(icmDoc==null) {
			throw new Exception("无对应ICM信息（“"+(interfaceDoc.getAttributeValue("C_CODE1")==null?""
					:interfaceDoc.getAttributeValue("C_CODE1").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE2")==null?""
							:interfaceDoc.getAttributeValue("C_CODE2").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE3")==null?""
							:interfaceDoc.getAttributeValue("C_CODE3").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE4")==null?""
							:interfaceDoc.getAttributeValue("C_CODE4").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE5")==null?""
							:interfaceDoc.getAttributeValue("C_CODE5").toString())
					+" "+(interfaceDoc.getAttributeValue("C_CODE6")==null?""
							:interfaceDoc.getAttributeValue("C_CODE6").toString())
					+"”）");
		}
		
		return true;
		
		
	}
	
	/**
	 * 通过接口对象处理接口对象包含（接口信息传递单，接口信息意见单）两种类型
	 * @param token
	 * @param interfaceDoc
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean interfaceRejectOption(String token,EcmDocument interfaceDoc) throws Exception {
		EcmDocument icmDoc= getICMS(token,interfaceDoc);
		EcmRelation relation=new EcmRelation();
		relation.setChildId(interfaceDoc.getId());
		relation.setParentId(icmDoc.getId());
		String relationName="";
		if("接口信息传递单".equals(interfaceDoc.getTypeName())) {
			relationName="传递接口";
			
		}else {
			relationName="接口意见";
		}
		return relationService.deleteByChildIdAndRelationName(token,interfaceDoc.getId(), relationName);//deleteObject(token, obj).newObject(token, relation);
//		return true;
		
		
	}
	
	/**
	 * 获取ICM
	 * @param token
	 * @param interfaceDoc
	 * @return
	 * @throws Exception
	 */
	private EcmDocument getICMS(String token,EcmDocument interfaceDoc) throws Exception{
		String sql=" type_name='ICM' ";
		if(interfaceDoc==null) {
			throw new Exception("接口信息传递单或接口信息意见单为NULL");
		}
		
		sql+=" and C_CODE1='"+(interfaceDoc.getAttributeValue("C_CODE1")==null?""
				:interfaceDoc.getAttributeValue("C_CODE1").toString()+"'");
		sql+=" and C_CODE2='"+(interfaceDoc.getAttributeValue("C_CODE2")==null?""
				:interfaceDoc.getAttributeValue("C_CODE2").toString()+"'");
		sql+=" and C_CODE3='"+(interfaceDoc.getAttributeValue("C_CODE3")==null?""
				:interfaceDoc.getAttributeValue("C_CODE3").toString()+"'");
		sql+=" and C_CODE4='"+(interfaceDoc.getAttributeValue("C_CODE4")==null?""
				:interfaceDoc.getAttributeValue("C_CODE4").toString()+"'");
		sql+=" and C_CODE5='"+(interfaceDoc.getAttributeValue("C_CODE5")==null?""
				:interfaceDoc.getAttributeValue("C_CODE5").toString()+"'");
		sql+=" and C_CODE6='"+(interfaceDoc.getAttributeValue("C_CODE6")==null?""
				:interfaceDoc.getAttributeValue("C_CODE6").toString()+"'");
		

		
		List<Map<String,Object>> icmList= getObjectMap(token, sql);
		if(icmList!=null&&icmList.size()>0) {
			EcmDocument icmDoc=new EcmDocument();
			icmDoc.setAttributes(icmList.get(0));
			return icmDoc;
		}
		return null;
		
	}
}
