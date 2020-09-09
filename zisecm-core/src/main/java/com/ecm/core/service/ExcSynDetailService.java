package com.ecm.core.service;

import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.entity.Pager;
import com.ecm.core.dao.ExcSynDetailMapper;
import com.ecm.icore.service.IExcSynDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
@Service
public class ExcSynDetailService extends EcmService implements IExcSynDetailService {
	@Autowired
	private ExcSynDetailMapper mapper;

	@Override
	public List<ExcSynDetail> selectByCondition(String condition) {
		return mapper.selectByCondition(condition);
	}
	
	@Override
	public List<ExcSynDetail> getAllObject(String token) {
		return mapper.selectAll();
	}

	@Override
	public ExcSynDetail getObjectById(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public String newObject(ExcSynDetail obj) {
		obj.setId(UUID.randomUUID().toString().replace("-", ""));
		if(mapper.insertSelective(obj)>0) {
			return obj.getId();
		}else {
			return null;
		}
		
	}

	@Override
	public boolean updateObject(ExcSynDetail obj) {
		if(StringUtils.isEmpty(obj.getId())) {
			return false;
		}
		if(mapper.selectByPrimaryKey(obj.getId())!=null) {
			return mapper.updateByPrimaryKeySelective(obj)>0;
		}else {
			return false;
		}
	}

	@Override
	public boolean deleteObject(ExcSynDetail obj) {
		if(StringUtils.isEmpty(obj.getId())) {
			return false;
		}
		if(mapper.selectByPrimaryKey(obj.getId())!=null) {
			return mapper.deleteByPrimaryKey(obj.getId())>0;
		}else {
			return false;
		}
	}

	@Override
	public List<ExcSynDetail> selectAll() {
		return mapper.selectAll();
	}
	
	public List<Map<String, Object>> getExcSynDetails(String sqlStr){
		return mapper.executeSQL(sqlStr);
	}
	
	public List<Map<String, Object>> getExcSynDetails(Pager pager,String sqlStr){
		return mapper.executeSQL(pager, sqlStr);
	}
}
