package com.ecm.core.service;

import com.ecm.core.dao.ExcSynBatchMapper;
import com.ecm.core.entity.ExcSynBatch;
import com.ecm.core.entity.Pager;
import com.ecm.icore.service.IExcSynBatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import java.util.List;
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
public class ExcSynBatchService  implements IExcSynBatchService {
	
	@Autowired
	private ExcSynBatchMapper mapper;

	@Override
	public List<ExcSynBatch> selectByCondition(Pager pager, String condition) {
		return mapper.selectByCondition(pager, condition);
	}
	
	
	@Override
	public List<ExcSynBatch> getByCondition(String condition) {
		return mapper.getByCondition( condition);
	}
	
	@Override
	public List<ExcSynBatch> getAllObject(String token) {
		return mapper.selectAll();
	}

	@Override
	public ExcSynBatch getObjectById(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public String newObject(ExcSynBatch obj) {
		obj.setId(UUID.randomUUID().toString().replace("-", ""));
		if(mapper.insertSelective(obj)>0) {
			return obj.getId();
		}else {
			return null;
		}
		
	}

	@Override
	public boolean updateObject(ExcSynBatch obj) {
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
	public boolean deleteObject(ExcSynBatch obj) {
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
	public List<ExcSynBatch> selectAll() {
		return mapper.selectAll();
	}
}
