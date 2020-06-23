package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmUiRelation;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-22
 */
@Mapper
public interface EcmUiRelationMapper {

	int deleteByPrimaryKey(String id);

	int insert(EcmUiRelation record);

	int insertSelective(EcmUiRelation record);

    EcmUiRelation selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(EcmUiRelation record);

    int updateByPrimaryKey(EcmUiRelation record);

	List<EcmUiRelation> selectAll();
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<EcmUiRelation> selectByCondition(@Param(value="condition")String condition);
    
}
