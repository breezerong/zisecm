package com.ecm.core.service;

import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.dao.ExcTransferMapper;
import com.ecm.icore.service.IExcTransferService;

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
public class ExcTransferServiceImpl  implements IExcTransferService {
	@Autowired
	private ExcTransferMapper mapper;

	@Override
	public List<ExcTransfer> selectByCondition(String condition) {
		return mapper.selectByCondition(condition);
	}
	
	@Override
	public List<ExcTransfer> getAllObject(String token) {
		return mapper.selectAll();
	}

	@Override
	public ExcTransfer getObjectById(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public String newObject(ExcTransfer obj) {
		obj.setId(UUID.randomUUID().toString().replace("-", ""));
		if(mapper.insertSelective(obj)>0) {
			return obj.getId();
		}else {
			return null;
		}
		
	}

	@Override
	public boolean updateObject(ExcTransfer obj) {
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
	public boolean deleteObject(ExcTransfer obj) {
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
	public List<ExcTransfer> selectAll() {
		return mapper.selectAll();
	}
}
