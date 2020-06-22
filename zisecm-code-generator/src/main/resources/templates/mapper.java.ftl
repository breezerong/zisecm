package ${package.Mapper};

import java.util.List;
import java.util.Map;
import ${package.Entity}.${entity};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} {

	int deleteByPrimaryKey(String id);

	int insert(${entity} record);

	int insertSelective(${entity} record);

    ${entity} selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(${entity} record);

    int updateByPrimaryKey(${entity} record);

	List<${entity}> selectAll();
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<${entity}> selectByCondition(@Param(value="condition")String condition);
    
}
</#if>
