package ${package.Service};

import ${package.Entity}.${entity};
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} {
	public List<${entity}> getAllObject(String token);
	
	public List<${entity}> selectByCondition(String condition);
	
	public ${entity} getObjectById(String id);
	
	public String newObject(${entity} obj);
	
	public boolean updateObject(${entity} obj);
	
	public boolean deleteObject(${entity} obj);
	
	public List<${entity}> selectAll();
}
</#if>
