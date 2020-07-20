package com.ecm.core.service;

import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.AuditContext;
import com.ecm.core.PermissionContext.ObjectPermission;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.ExcTransferMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.icore.service.IExcTransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
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
		if (StringUtils.isEmpty(obj.getId())) {
			obj.setId(UUID.randomUUID().toString().replace("-", ""));
		}
		if(mapper.insertSelective(obj)>0) {
			return obj.getId();
		}else {
			return null;
		}
		
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String newObject(Map<String, Object> args) throws EcmException, AccessDeniedException, NoPermissionException {
		String id = createTransfer(args);
		return id;
	}
	public String newUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private String createTransfer(Map<String, Object> args) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		String sql = "insert into exc_transfer (%s) values(%s)";
		String fieldStr = "";
		String valueStr = "";
		String id = args.get("ID") != null ? args.get("ID").toString() : "";
		if (StringUtils.isEmpty(id)) {
			id = newUUID();
		}
		for (Object key : args.keySet().toArray()) {
			String fieldName=key.toString();
			if (args.get(key) == null) {
				continue;
			}
			if(fieldName.toLowerCase().endsWith("date"))// 日期
			{
				String date = args.get(key).toString();
				if(args.get(key) instanceof Date) {
					date = DateUtils.formatDate((Date)args.get(key),"yyyy-MM-dd HH:mm:ss");
					date = "'"+date+"'";
				}else {
					date = DBFactory.getDBConn().getDBUtils().getDBDateString(date);
				}
				if (date == null || date.length() < 1)
					continue;
				fieldStr += key.toString() + ",";
				valueStr += " " + date + " ,";
				continue;
			}else if("ITEM_TYPE".equals(fieldName)) {//数字
				valueStr += args.get(key).toString() + ",";
				fieldStr += key.toString() + ",";
			}else {
				fieldStr += key.toString() + ",";
				valueStr += "'" + DBFactory.getDBConn().getDBUtils().getString((String) args.get(key)) + "',";
			}
		}
		fieldStr=fieldStr.substring(0, fieldStr.length()-1);
		valueStr=valueStr.substring(0, valueStr.length()-1);
		sql = String.format(sql, fieldStr, valueStr);
		mapper.executeSQL(sql);
		return id;
	}

	public void updateObject(Map<String, Object> args) throws NoPermissionException, AccessDeniedException, EcmException {

		String id = args.get("ID").toString();
		String sql = "update  exc_transfer set ";
		boolean isFirst = true;

		for (Object key : args.keySet().toArray()) {
			String fieldName = key.toString();
			if(fieldName.toLowerCase().endsWith("date"))// 日期
			{
				if (!isFirst) {
					sql += ",";
				} else {
					isFirst = false;
				}
				if (args.get(key) == null) {
					sql += " " + key.toString() + "=" + DBFactory.getDBConn().getDBUtils().getDBNullDate();
				} else {
					String date = "";
					if (args.get(key) instanceof Timestamp) {
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(((Timestamp) args.get(key)));
					}else if(args.get(key) instanceof Date) {
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(((Date) args.get(key)));
					}else {
						date = (String) args.get(key);
						date = DBFactory.getDBConn().getDBUtils().getDBDateString(date);
					}
					if (date == null || date.length() < 1)
						continue;
					sql += " " + key.toString() + "= " + date + " ";
				}
			}else if("ITEM_TYPE".equals(fieldName)) {//数字
				if (args.get(key) == null) {
					continue;
				}
				if (!isFirst) {
					sql += ",";
				} else {
					isFirst = false;
				}
				sql += " " + key.toString() + "=" + args.get(key).toString();
				
			}else {
				if (args.get(key) == null) {
					continue;
				}
				if (!isFirst) {
					sql += ",";
				} else {
					isFirst = false;
				}
				sql += " " + key.toString() + "='" + DBFactory.getDBConn().getDBUtils().getString((String) args.get(key)) + "'";

			}

		}
		if (!isFirst) {
			sql += ",";
		} else {
			isFirst = false;
		}
		sql += " where ID='" + id + "'";
		mapper.executeSQL(sql);
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
	
	@Override
	public List<Map<String, Object>> getObjectMap(String token, String condition) {

		String sql = "select ID, ITEM_TYPE, DOC_ID, FROM_NAME, TO_NAME, CREATION_DATE, CREATOR, REJECTER, REJECT_DATE, SENDER, SEND_DATE, RECEIVER, RECEIVE_DATE, STAUTS, COMMENT, SYN_STATUS from exc_transfer where " + condition;

		return mapper.executeSQL(sql);
	}

}
