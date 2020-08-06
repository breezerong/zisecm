package com.ecm.core.dao;

import java.util.List;
import java.util.Map;
import com.ecm.core.entity.ExcSynBatch;
import com.ecm.core.entity.Pager;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
@Mapper
public interface ExcSynBatchMapper {

	int deleteByPrimaryKey(String id);

	int insert(ExcSynBatch record);

	int insertSelective(ExcSynBatch record);

    ExcSynBatch selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(ExcSynBatch record);

    int updateByPrimaryKey(ExcSynBatch record);

	List<ExcSynBatch> selectAll();
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<ExcSynBatch> selectByCondition(Pager pager, @Param(value="condition")String condition);
	
	List<ExcSynBatch> getByCondition(@Param(value="condition")String condition);
    
}
