package com.ecm.core.dao;

import java.util.List;
import java.util.Map;
import com.ecm.core.entity.ExcSynDetail;
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
public interface ExcSynDetailMapper {

	int deleteByPrimaryKey(String id);

	int insert(ExcSynDetail record);

	int insertSelective(ExcSynDetail record);

    ExcSynDetail selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(ExcSynDetail record);

    int updateByPrimaryKey(ExcSynDetail record);

	List<ExcSynDetail> selectAll();
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<ExcSynDetail> selectByCondition(@Param(value="condition")String condition);
    
}
