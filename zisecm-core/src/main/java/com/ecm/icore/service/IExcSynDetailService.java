package com.ecm.icore.service;

import com.ecm.core.entity.ExcSynDetail;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
public interface IExcSynDetailService {
	public List<ExcSynDetail> getAllObject(String token);
	
	public List<ExcSynDetail> selectByCondition(String condition);
	
	public ExcSynDetail getObjectById(String id);
	
	public String newObject(ExcSynDetail obj);
	
	public boolean updateObject(ExcSynDetail obj);
	
	public boolean deleteObject(ExcSynDetail obj);
	
	public List<ExcSynDetail> selectAll();
}
