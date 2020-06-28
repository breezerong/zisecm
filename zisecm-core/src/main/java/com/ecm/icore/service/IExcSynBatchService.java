package com.ecm.icore.service;

import com.ecm.core.entity.ExcSynBatch;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
public interface IExcSynBatchService {
	public List<ExcSynBatch> getAllObject(String token);
	
	public List<ExcSynBatch> selectByCondition(String condition);
	
	public ExcSynBatch getObjectById(String id);
	
	public String newObject(ExcSynBatch obj);
	
	public boolean updateObject(ExcSynBatch obj);
	
	public boolean deleteObject(ExcSynBatch obj);
	
	public List<ExcSynBatch> selectAll();
}
