package com.ecm.portal.archive.common;

import java.util.List;
import java.util.Map;

import com.ecm.core.exception.EcmException;
import com.ecm.icore.service.IEcmObjectService;

public class ChildrenObjAction {
	/**
	 * 查询出多个文件下的子文件id
	 * @param token
	 * @param ids String 格式:'aaaa','bbbb','ccccc','dddd'
	 * @param service
	 * @return
	 * @throws EcmException
	 */
	public static List<Map<String,Object>> getChildrenObjByIds(String token,String ids,IEcmObjectService<?> service) throws EcmException{
		String sql="select child_id from (select b.child_id,b.order_index from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id in("+ids+") and b.name='irel_children' and (b.DESCRIPTION!='复用' or b.DESCRIPTION is null) " + 
				"union " + 
				"select b.child_id,b.order_index from ecm_document a,ecm_relation b where a.id=b.PARENT_ID "
				+ "and b.PARENT_ID in(" + 
				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id in("+ids+")) and b.name='irel_children' and (b.DESCRIPTION!='复用' or b.DESCRIPTION is null)) t order by t.order_index ";
		List<Map<String,Object>> childrenIds= service.getMapList(token, sql);
		return childrenIds;
	}
	/**
	 * 只获取下一级文件对象
	 * @param token
	 * @param id
	 * @param service
	 * @return
	 * @throws EcmException 
	 */
	public static List<Map<String,Object>> getChildrenObjectById(String token,String id,IEcmObjectService<?> service) throws EcmException{
		String sql="select child_id from ecm_relation where parent_id='"+id+"' and name='irel_children' and (DESCRIPTION!='复用' or DESCRIPTION is null) order by order_index";
		List<Map<String,Object>> childrenIds= service.getMapList(token, sql);
		return childrenIds;
	}
	
	/**
	 * 取单个盒或卷中的文件Id
	 * @param token
	 * @param id String
	 * @param service
	 * @return
	 * @throws EcmException
	 */
	public static List<Map<String,Object>> getChildrenObjById(String token,String id,IEcmObjectService<?> service) throws EcmException{
		String sql="select child_id from( select b.child_id,b.order_index from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id ='"+id+"' and b.name='irel_children' and (b.DESCRIPTION!='复用' or b.DESCRIPTION is null) " + 
				" union " + 
				"select b.child_id,b.order_index from ecm_document a,ecm_relation b where a.id=b.PARENT_ID "
				+ "and b.PARENT_ID in(" + 
				"select b.child_id from ecm_document a,ecm_relation b where a.id=b.parent_id "
				+ "and b.parent_id ='"+id+"') and b.name='irel_children' and (b.DESCRIPTION!='复用' or b.DESCRIPTION is null)) t order by t.order_index ";
		List<Map<String,Object>> childrenIds= service.getMapList(token, sql);
		return childrenIds;
	}
	/**
	 * 获取最高机密
	 * @param token
	 * @param id
	 * @param service
	 * @return
	 * @throws EcmException
	 */
	public static String getVolumeMaxSecurity(String token,String id,IEcmObjectService<?> service) throws EcmException{
		String sql="select min(securite) as SECURITYINDEX from (" + 
				"select case when C_SECURITY_LEVEL='核心商密' then 1 " + 
				"						when C_SECURITY_LEVEL='普通商密' then 2 " + 
				"						when C_SECURITY_LEVEL='受限'	then 3 " + 
				"						when C_SECURITY_LEVEL='内部公开'  then 4 else 4 END as securite " + 
				"from ecm_document where id in(select child_id from ecm_relation where parent_id='"+id+"' and name='irel_children' and (DESCRIPTION!='复用' or DESCRIPTION is null))) t";
		List<Map<String,Object>> securityIndex= service.getMapList(token, sql);
		if(securityIndex!=null&&securityIndex.size()>0) {
			return Security.getSecurity(securityIndex.get(0).get("SECURITYINDEX").toString());
		}
		return null;
	}
	
	/**
	 * 获取最大保管期限
	 * @param token
	 * @param id
	 * @param service
	 * @return
	 * @throws EcmException
	 */
	public static String getRetention(String token,String id,IEcmObjectService<?> service) throws EcmException {
		String sql="select max(retention) as RETENTION from (" + 
				"select case when C_RETENTION='10年' then 10" + 
				"						when C_RETENTION='30年' then 30" + 
				"						when C_RETENTION='永久'	then 999 else 10 END as retention " + 
				
				"from ecm_document where id in(select child_id from ecm_relation where PARENT_ID='"+id+"' and name='irel_children' and  (DESCRIPTION!='复用' or DESCRIPTION is null)) ) t";
		List<Map<String,Object>> retention= service.getMapList(token, sql);
		if(retention!=null&&retention.size()>0&&retention.get(0)!=null) {
			return Retention.getRetention(retention.get(0).get("RETENTION").toString());
		}
		return null;
	}
	
	public static String getMinDocDate(String token,String id,String column,IEcmObjectService<?> service) throws EcmException {
		String sql="select  min(b.sdate) as MINSDATE from ecm_relation a, ("
				+"select id,case when "+column+" is null then C_ARCHIVE_DATE else "+column+" end as SDATE from ecm_document" 
				+") b where a.child_id=b.id and a.parent_id='"+id+"'";
		List<Map<String,Object>> retention= service.getMapList(token, sql);
		if(retention!=null&&retention.size()>0&&retention.get(0)!=null) {
			
			return retention.get(0).get("MINSDATE").toString();
		}
		return null;
	}
	
	public static String getMaxDocDate(String token,String id,String column,IEcmObjectService<?> service) throws EcmException {
		String sql="select  max(b.sdate) as MAXSDATE from ecm_relation a, ("
				+"select id,case when "+column+" is null then C_ARCHIVE_DATE else "+column+" end as SDATE from ecm_document" 
				+") b where a.child_id=b.id and a.parent_id='"+id+"'";
		List<Map<String,Object>> retention= service.getMapList(token, sql);
		if(retention!=null&&retention.size()>0&&retention.get(0)!=null) {
			
			return retention.get(0).get("MAXSDATE").toString();
		}
		return null;
	}
	
}
