package com.ecm.core.dao;

import java.util.List;
import java.util.Map;
import com.ecm.core.entity.ExcTransfer;
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
public interface ExcTransferMapper {

	int deleteByPrimaryKey(String id);

	int insert(ExcTransfer record);

	int insertSelective(ExcTransfer record);

    ExcTransfer selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(ExcTransfer record);

    int updateByPrimaryKey(ExcTransfer record);

	List<ExcTransfer> selectAll();
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<ExcTransfer> selectByCondition(@Param(value="condition")String condition);
    
}
