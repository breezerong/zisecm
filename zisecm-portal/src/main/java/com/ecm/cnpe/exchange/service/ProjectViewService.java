package com.ecm.cnpe.exchange.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.entity.Pager;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;

@Service
@Scope("prototype")
public class ProjectViewService extends DocumentService {
	public List<Map<String,Object>> getDesignInProject(String token,String condition,Pager pager) throws EcmException{
		String sql ="select * from ecm_document a,(" + 
				"select a.CHILD_ID from ecm_relation a,ecm_document b where b.id=a.PARENT_ID " + 
				"and (b.status is not null and b.status!='' and b.status!='新建') and  b.TYPE_NAME='文件传递单' " + 
				")b where a.id=b.CHILD_ID and a.type_name='设计文件' "+condition;
		return this.getMapList(token, sql, pager);
		
	}
	
	public List<Map<String,Object>> getDesignInProject(String token,String condition) throws EcmException{
		String sql ="select * from ecm_document a,(" + 
				"select a.CHILD_ID from ecm_relation a,ecm_document b where b.id=a.PARENT_ID " + 
				"and (b.status is not null and b.status!='' and b.status!='新建') and  b.TYPE_NAME='文件传递单' " + 
				")b where a.id=b.CHILD_ID and a.type_name='设计文件' "+condition;
		return this.getMapList(token, sql);
		
	}
}
