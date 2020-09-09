/**
 * 
 */
package org.zisecm.jobs.tc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zisecm.jobs.tc.clientx.Session;
import com.teamcenter.services.strong.administration.PreferenceManagementService;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.services.strong.core._2006_03.Session.PreferencesResponse;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsData;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsOutput;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsPref;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsResponse;
import com.teamcenter.services.strong.core._2007_06.DataManagement.RelationAndTypesFilter2;
import com.teamcenter.services.strong.query.SavedQueryService;
import com.teamcenter.services.strong.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
import com.teamcenter.services.strong.query._2006_03.SavedQuery.GetSavedQueriesResponse;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.QueryResults;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.SavedQueriesResponse;
import com.teamcenter.services.strong.query._2008_06.SavedQuery.QueryInput;
import com.teamcenter.soa.client.FileManagementUtility;
import com.teamcenter.soa.client.GetFileResponse;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Preferences;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.Dataset;
import com.teamcenter.soa.client.model.strong.GroupMember;
import com.teamcenter.soa.client.model.strong.ImanFile;
import com.teamcenter.soa.client.model.strong.ImanQuery;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.soa.client.model.strong.TC_Project;
import com.teamcenter.soa.client.model.strong.User;

/**
 * @author xiaolei Creation Date 2013-6-4
 */
public class Query {

	Session session;

	SavedQueryService queryService;

	public Query(Session session) {
		this.session = session;
		queryService = SavedQueryService.getService(session.getConnection());
	}

	/**
	 * 根据userid查询user
	 * 
	 * @param userId
	 * @return
	 */
	public User queryUser(String userId) {
		ImanQuery query = null;
		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("__WEB_find_user")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an '__WEB_find_user' query.");
			return null;
		}

		try {
			String[] entries = new String[1];
			entries[0] = "用户 ID";
			String[] values = new String[1];
			values[0] = userId;
			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 0) {
				return null;
			} else {
				return (User) found.objects[0];
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ModelObject[] queryObjectsBySavedQuery(String userId,
			String full_name) {
		ImanQuery query = null;
		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("SearchGroupMembersById")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}
		if (query == null) {
			System.out.println("There is not an 'Item Revision...' query.");
			return null;
		}

		try {

			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
			savedQueryInput[0].entries = new String[] { "ID", "名称" };
			savedQueryInput[0].values = new String[] { userId, full_name };

			SavedQueriesResponse response = queryService
					.executeSavedQueries(savedQueryInput);

			QueryResults found = response.arrayOfResults[0];

			if (found.objectUIDS.length == 0)
				return null;
			else {
				DataManagementService dmService = DataManagementService
						.getService(session.getConnection());
				ServiceData serviceData = dmService
						.loadObjects(found.objectUIDS);
				ModelObject[] foundObjs = new ModelObject[serviceData
						.sizeOfPlainObjects()];
				// if (serviceData.sizeOfPartialErrors() == 0) {
				// serviceData.getPlainObject(0);
				// }
				return foundObjs;
			}

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ModelObject queryItem(String itemId) {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("Item ID")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an 'Item ID' query.");
			return null;
		}

		try {
			String[] entries = new String[1];
			entries[0] = "零组件 ID";
			String[] values = new String[1];
			values[0] = itemId;
			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 0) {
				return null;
			} else {
				return found.objects[0];
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ModelObject queryItemRevision(String itemId, String revId) {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("Item Revision...")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an 'Item Revision...' query.");
			return null;
		}

		try {
			/*
			 * //String[] entries = new String[]{"Item ID","Revision"}; String[]
			 * entries = new String[]{"零组件 ID","版本"};
			 * 
			 * String[] values = new String[]{itemId,revId};
			 * 
			 * int limit = 25;
			 * 
			 * ExecuteSavedQueryResponse found =
			 * queryService.executeSavedQuery(query, entries, values, limit);
			 * 
			 * if (found.objects.length == 0) { return null; } else { return
			 * found.objects[0]; }
			 */

			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
			savedQueryInput[0].entries = new String[] { "零组件 ID", "版本" };
			savedQueryInput[0].values = new String[] { itemId, revId };

			SavedQueriesResponse response = queryService
					.executeSavedQueries(savedQueryInput);

			QueryResults found = response.arrayOfResults[0];

			if (found.objectUIDS.length == 0)
				return null;
			else {
				DataManagementService dmService = DataManagementService
						.getService(session.getConnection());
				ServiceData serviceData = dmService
						.loadObjects(found.objectUIDS);
				if (serviceData.sizeOfPartialErrors() == 0) {
					return serviceData.getPlainObject(0);
				}
			}

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ModelObject queryDVMRevision(String itemId, String revId) {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("1 计划类 - 供货商图纸文件")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an 'Item Revision...' query.");
			return null;
		}

		try {
			/*
			 * //String[] entries = new String[]{"Item ID","Revision"}; String[]
			 * entries = new String[]{"零组件 ID","版本"};
			 * 
			 * String[] values = new String[]{itemId,revId};
			 * 
			 * int limit = 25;
			 * 
			 * ExecuteSavedQueryResponse found =
			 * queryService.executeSavedQuery(query, entries, values, limit);
			 * 
			 * if (found.objects.length == 0) { return null; } else { return
			 * found.objects[0]; }
			 */

			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
			savedQueryInput[0].entries = new String[] { "外部编码", "版次" };
			savedQueryInput[0].values = new String[] { itemId, revId };

			SavedQueriesResponse response = queryService
					.executeSavedQueries(savedQueryInput);

			QueryResults found = response.arrayOfResults[0];
			if (found.objectUIDS.length == 0)
				return null;
			else {
				DataManagementService dmService = DataManagementService
						.getService(session.getConnection());
				ServiceData serviceData = dmService
						.loadObjects(found.objectUIDS);
				if (serviceData.sizeOfPartialErrors() == 0) {
					return serviceData.getPlainObject(0);
				}
			}

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param projectName
	 *            项目名称
	 * @return 项目管理员用户名称
	 */
	public String queryProjectTeamAdminUserName(String projectName) {
		ImanQuery query = null;
		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("__WEB_group_members")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an '__WEB_group_members' query.");
			return null;
		}

		try {
			String[] entries = new String[2];
			entries[0] = "组";
			entries[1] = "角色";
			String[] values = new String[2];
			values[0] = projectName;
			values[1] = "Project Team Administrator";
			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 0) {
				return "";
			}

			GroupMember groupMember = (GroupMember) found.objects[0];
			return groupMember.get_user_name();

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * 根据项目查找项目对象
	 * 
	 * @param projectId
	 * @return
	 */
	public TC_Project queryProjectExist(String projectId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("Projects...")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an 'Projects...' query.");
			// return false;
			throw new Exception("There is not an 'Projects...' query.");
		}

		try {
			String[] entries = new String[1];
			entries[0] = "项目 ID";
			String[] values = new String[1];
			values[0] = projectId;
			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length > 0) {
				return (TC_Project) found.objects[0];
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找CR版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryChangeRqRevision(String projectId, String fileId,
			String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_ChangeRqRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_ChangeRqRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_ChangeRqRevision' query.");
		}

		try {
			String[] entries = new String[3];
			entries[0] = "项目 ID";
			entries[1] = "cn9CorrespLetterRecNo";
			entries[2] = "cn9Rev";

			String[] values = new String[3];
			values[0] = projectId;
			values[1] = fileId;
			values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找CR版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryChangeRqRevisionByLetterNo2(String projectId,
			String fileId, String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_FIND_CR_BY_LetterNO")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_ChangeRqRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_ChangeRqRevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目号";
			entries[1] = "答复单渠道号";
			// entries[2] = "cn9Rev";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;
			// values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找CR版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryTARevision(String projectId, String fileId,
			String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_TARevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_TARevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_TARevision' query.");
		}

		try {
			String[] entries = new String[3];
			entries[0] = "项目号";
			entries[1] = "TA申请单渠道号";
			entries[2] = "版次";

			String[] values = new String[3];
			values[0] = projectId;
			values[1] = fileId;
			values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryPerson(String name) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_find_user_by_person")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_TARevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_TARevision' query.");
		}

		try {
			String[] entries = new String[1];
			entries[0] = "名称";

			String[] values = new String[1];
			values[0] = name;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {

				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryTARevisionByLetterNo(String projectId,
			String fileId, String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_TARevision_By_LetterNo")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_TARevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_TARevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目号";
			entries[1] = "TA答复单发文号";
			// entries[2] = "版次";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;
			// values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryFaxRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_FIND_FAXRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_FIND_FAXRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_FIND_FAXRevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目号";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryDesignRFRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("3 过程文件类 - 审查意见答复单")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_FIND_FAXRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_FIND_FAXRevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryIITFRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("2 接口类 - ACP1000接口信息传递单(IITF)")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '2 接口类 - ACP1000接口信息传递单(IITF)' query.");
			// return false;
			throw new Exception(
					"There is not an '2 接口类 - ACP1000接口信息传递单(IITF)' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryAP1000IICSRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("2 接口类 - AP1000接口信息意见单(IICS)")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '2 接口类 - AP1000接口信息意见单(IICS)' query.");
			// return false;
			throw new Exception(
					"There is not an '2 接口类 - AP1000接口信息意见单(IICS)' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryIICSRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("2 接口类 - ACP1000接口信息意见单(IICS)")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '2 接口类 - ACP1000接口信息意见单(IICS)' query.");
			// return false;
			throw new Exception(
					"There is not an '2 接口类 - ACP1000接口信息意见单(IICS)' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryAP1000IITFRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("2 接口类 - AP1000接口信息传递单(IITF)")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '2 接口类 - AP1000接口信息传递单(IITF)' query.");
			// return false;
			throw new Exception(
					"There is not an '2 接口类 - AP1000接口信息传递单(IITF)' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryDesignRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_DesignReviewF_Find_LetterNo")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_DesignReviewF_Find_LetterNo' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_DesignReviewF_Find_LetterNo' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId);
				throw new Exception("异常：在系统中存在多个符合条件的CR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找CR版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryFileRevision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("__CNPE_Find_File1")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an '__CNPE_Find_File' query.");
			// return false;
			throw new Exception("There is not an '__CNPE_Find_File' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目号";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----");
				throw new Exception("异常：在系统中存在多个符合条件的FILE单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryFile2Revision(String projectId, String fileId)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("__CNPE_Find_File2")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an '__CNPE_Find_File' query.");
			// return false;
			throw new Exception("There is not an '__CNPE_Find_File' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目号";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----");
				throw new Exception("异常：在系统中存在多个符合条件的FILE单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找ACP1000外部接口版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryICMRevision(String projectId, String one,
			String two, String type, String num, String offer, String recv,
			String lot) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("2 接口类 - ACP1000外部接口")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an '2 接口类 - ACP1000外部接口' query.");
			// return false;
			throw new Exception("There is not an '2 接口类 - ACP1000外部接口' query.");
		}

		try {
			String[] entries = new String[8];
			entries[0] = "项目ID";
			entries[1] = "第一方";
			entries[2] = "第二方";
			entries[3] = "接口类型";
			entries[4] = "流水号";
			entries[5] = "发布方";
			entries[6] = "接收方";
			entries[7] = "LOT包号";

			String[] values = new String[8];
			values[0] = projectId;
			values[1] = one;
			values[2] = two;
			values[3] = type;
			values[4] = num;
			values[5] = offer;
			values[6] = recv;
			values[7] = lot;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				System.out.println(".........");
				return found.objects[0];

			} else if (found.objects.length > 1) {
				// System.out.println(projectId + "-----" + fileId + "-----");
				// throw new Exception("异常：在系统中存在多个符合条件的FILE单！");
				System.out.println("....");
				System.out.println(found.objects.length);
				if (one.indexOf("*") != -1) {
					System.out.println("....");
					for (int i = 0; i < found.objects.length; i++) {
						DataManagementService dmService = DataManagementService
								.getService(session.getConnection());
						// ServiceData serviceData =
						// dmService.loadObjects(found.objects);
						// ModelObject[] foundObjs = new ModelObject[serviceData
						// .sizeOfPlainObjects()];
						dmService.getProperties(found.objects, new String[] {
								"cn91stParty", "cn92ndParty" });

						String str1 = found.objects[i]
								.getPropertyDisplayableValue("cn91stParty");
						if (one.equals(str1)) {
							return found.objects[i];
						}
					}
				} else if (two.indexOf("*") != -1) {
					for (int i = 0; i < found.objects.length; i++) {
						DataManagementService dmService = DataManagementService
								.getService(session.getConnection());
						// ServiceData serviceData =
						// dmService.loadObjects(found.objects);
						// ModelObject[] foundObjs = new ModelObject[serviceData
						// .sizeOfPlainObjects()];
						dmService.getProperties(found.objects, new String[] {
								"cn91stParty", "cn92ndParty" });

						String str1 = found.objects[i]
								.getPropertyDisplayableValue("cn91stParty");
						if (two.equals(str1)) {
							return found.objects[i];
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject[] queryICMListRevision(String projectId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("__2 接口类 - ACP1000外部接口(接受方不为B)")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an '2 接口类 - ACP1000外部接口' query.");
			// return false;
			throw new Exception("There is not an '2 接口类 - ACP1000外部接口' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "接收方";
			

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = "B";
			

			int limit = 0;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			return found.objects;
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		
	}

	/**
	 * 根据项目号、编号、版本在系统中查找ACP1000外部接口版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryAP1000ICMRevision(String projectId,
			String intfcIdent) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("2 接口类 - AP1000外部接口")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an '2 接口类 - AP1000外部接口' query.");
			// return false;
			throw new Exception("There is not an '2 接口类 - AP1000外部接口' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "接口号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = intfcIdent;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				// System.out.println(projectId + "-----" + fileId + "-----");
				throw new Exception("异常：在系统中存在多个符合条件的FILE单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找FCR版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryFieldCRRevision(String projectId, String fileId,
			String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_FieldCRRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_FieldCRRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_FieldCRRevision' query.");
		}

		try {
			String[] entries = new String[3];
			entries[0] = "项目 ID";
			entries[1] = "cn9FileID";
			entries[2] = "cn9Rev";

			String[] values = new String[3];
			values[0] = projectId;
			values[1] = fileId;
			values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的FCR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryFieldCRRevisionByLetterNo(String projectId,
			String fileId, String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_FieldCRRevision_By_LetterNo")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_FieldCRRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_FieldCRRevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目 ID";
			entries[1] = "答复单渠道号";
			// entries[2] = "cn9Rev";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;
			// values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的FCR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找NCR版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryNonConformaRevision(String projectId,
			String fileId, String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_NonConformaRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_NonConformaRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_NonConformaRevision' query.");
		}

		try {
			String[] entries = new String[3];
			entries[0] = "项目 ID";
			entries[1] = "cn9CorrespLetterRecNo";
			entries[2] = "cn9Rev";

			String[] values = new String[3];
			values[0] = projectId;
			values[1] = fileId;
			values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的NCR单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找DEN版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @param revId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryDENRevision(String projectId, String fileId,
			String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_DesignEnvNotiRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_DesignEnvNotiRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_DesignEnvNotiRevision' query.");
		}

		try {
			String[] entries = new String[3];
			entries[0] = "项目 ID";
			entries[1] = "cn9FileID";
			entries[2] = "cn9Rev";

			String[] values = new String[3];
			values[0] = projectId;
			values[1] = fileId;
			values[2] = revId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的DEN单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	public ModelObject queryDENRevision2(String projectId, String fileId,
			String revId) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_DesignEnvNotiRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_DesignEnvNotiRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_DesignEnvNotiRevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目 ID";
			entries[1] = "发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = fileId;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + fileId + "-----"
						+ revId);
				throw new Exception("异常：在系统中存在多个符合条件的DEN单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、版本在系统中查找FU申请单版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryFURevision(String projectId, String letterSendNo)
			throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_FUReqRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_FUReqRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_FUReqRevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目 ID";
			entries[1] = "cn9LetterSendNo";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = letterSendNo;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + projectId + "-----"
						+ letterSendNo);
				throw new Exception("异常：在系统中存在多个符合条件的FU申请单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * 根据项目号、编号、在系统中查找FU通知单版本
	 * 
	 * @param projectId
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public ModelObject queryFUNotifyRevision(String projectId,
			String letterSendNo) throws Exception {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_FUNotifyRevision")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_FUReqRevision' query.");
			// return false;
			throw new Exception(
					"There is not an '__CNPE_Find_FUReqRevision' query.");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "项目ID";
			entries[1] = "我方发文号";

			String[] values = new String[2];
			values[0] = projectId;
			values[1] = letterSendNo;

			int limit = 25;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 1) {
				return found.objects[0];
			} else if (found.objects.length > 1) {
				System.out.println(projectId + "-----" + projectId + "-----"
						+ letterSendNo);
				throw new Exception("异常：在系统中存在多个符合条件的FU申请单！");
			}
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		return null;
	}

	// add by xiaolei 20141219 for queryEquipItemRevision
	// modify by xiaolei 20150320
	// 修改逻辑，先查询符合条件的IED,然后再查找设备
	/*
	 * public Equipment[] queryEquipItemRevisions(String projectId, String
	 * lastUpdateTime) throws Exception { ImanQuery query = null;
	 * 
	 * try { GetSavedQueriesResponse savedQueries = queryService
	 * .getSavedQueries();
	 * 
	 * if (savedQueries.queries.length == 0) {
	 * System.out.println("There are no saved queries in the system."); //
	 * return false; throw new
	 * Exception("There are no saved queries in the system."); }
	 * 
	 * for (int i = 0; i < savedQueries.queries.length; i++) { if
	 * (savedQueries.queries[i].name
	 * .equals("__CNPE_Find_EquipItem_By_Project_Date_LOT")) { query =
	 * savedQueries.queries[i].query; break; } } } catch (Exception e) {
	 * System.out.println("GetSavedQueries service request failed.");
	 * System.out.println(e.getMessage()); // return false; throw new
	 * Exception("GetSavedQueries service request failed:" + e.getMessage()); }
	 * 
	 * if (query == null) { System.out
	 * .println("There is not an '__CNPE_Find_EquipItem_By_Project_Date_LOT' query."
	 * ); // return false; throw new Exception(
	 * "There is not an '__CNPE_Find_EquipItem_By_Project_Date_LOT' query."); }
	 * 
	 * try { String[] entries = new String[2]; entries[0] = "项目 ID"; entries[1]
	 * = "上次修改日期";
	 * 
	 * String[] values = new String[2]; values[0] = projectId; values[1] =
	 * lastUpdateTime;
	 * 
	 * int limit = 1000;
	 * 
	 * ExecuteSavedQueryResponse found = queryService.executeSavedQuery( query,
	 * entries, values, limit);
	 * 
	 * if (found.objects.length > 0) {
	 * System.out.println("---------------------------------" +
	 * found.objects.length); DataManagementService dmService =
	 * DataManagementService .getService(session.getConnection());
	 * 
	 * String[] props = new String[] { "item_id", "cn9DevNum", "cn9DevName",
	 * "cn9SpecAModel", "cn9PrimMaterial", "cn9Quantity", "cn9UnitOfMeasure",
	 * "cn9UnitWeight", "cn9OverallWeight", "cn9DesignTemp",
	 * "cn9NewDesignPress", "cn9SafeClass", "cn9VerifyClass",
	 * "cn9DesignSpecClass", "cn9QualityClass", "cn9OwningModule",
	 * "cn9AntiQuakeClass", "cn9OwingGroup", "cn9System", "cn9PlantCode",
	 * "cn9AreaCode", "cn9LayerCode", "cn9ConveNum", "cn9RelatedSpecNo",
	 * "cn9RelatedSpecVer" }; String[] props2 = new String[] { "cn9Class_PN",
	 * "cn9ManuStyle", "cn9JointStyle", "cn9ManuStandard", "cn9MetricDiam1",
	 * "cn9MetricDiam2", "cn9MetricDiam1PressClass", "cn9MetricDiam2PressClass",
	 * "cn9WallThick", "cn9JointWallThick" };
	 * dmService.getProperties(found.objects, props);
	 * 
	 * //add by xiaolei 20141231 begin
	 * //设备/材料的规格书编号和版次信息填写完整，并且发出对应的规格书文件的传递单已经经设总批准。 List<ModelObject>
	 * equipmentList = new ArrayList<ModelObject>();
	 * 
	 * List<String> iedIntNumList = new ArrayList<String>(); //add by xiaolei
	 * 20150213
	 * 
	 * for(int i=0;i<found.objects.length;i++){ String cn9RelatedSpecNo =
	 * found.objects[i] .getPropertyObject("cn9RelatedSpecNo")
	 * .getStringValue(); String cn9RelatedSpecVer = found.objects[i]
	 * .getPropertyObject("cn9RelatedSpecVer") .getStringValue();
	 * 
	 * ModelObject iedRevision = queryItemRevision(cn9RelatedSpecNo,
	 * cn9RelatedSpecVer);
	 * 
	 * try{ if(iedRevision!=null){ //modify by xiaolei 20150319
	 * //dmService.getProperties(new ModelObject[]{iedRevision}, new
	 * String[]{"cn9SendSpotNum","cn9IntNum",""}); dmService.getProperties(new
	 * ModelObject[]{iedRevision}, new
	 * String[]{"cn9SendPurchaseChannCodeNew","cn9IntNum"
	 * ,"cn9SendPurchaseDateNew"});
	 * 
	 * //modify by xiaolei 20150319 //String cn9SendSpotNum =
	 * iedRevision.getPropertyObject("cn9SendSpotNum").getStringValue(); String
	 * cn9SendPurchaseChannCodeNew =
	 * iedRevision.getPropertyObject("cn9SendPurchaseChannCodeNew"
	 * ).getStringValue();
	 * 
	 * Calendar cn9SendPurchaseDateNew =
	 * iedRevision.getPropertyObject("cn9SendPurchaseDateNew"
	 * ).getCalendarValue();
	 * 
	 * String cn9IntNum =
	 * iedRevision.getPropertyObject("cn9IntNum").getStringValue(); //add by
	 * xiaolei 20150213
	 * 
	 * if(!cn9SendPurchaseChannCodeNew.equals("") &&
	 * cn9SendPurchaseDateNew!=null){ DateFormat dateFormat =
	 * DateFormat.getDateInstance(DateFormat.DEFAULT); //add by xiaolei 20150319
	 * 
	 * 
	 * 
	 * if(dateFormat.parse(lastUpdateTime).before(cn9SendPurchaseDateNew.getTime(
	 * ))){ //add by xiaolei 20150319 equipmentList.add(found.objects[i]);
	 * iedIntNumList.add(cn9IntNum); //add by xiaolei 20150213 } } } }catch
	 * (Exception e) {
	 * 
	 * } }
	 * 
	 * //add end
	 * 
	 * 
	 * //Equipment[] equipments = new Equipment[found.objects.length];
	 * Equipment[] equipments = new Equipment[equipmentList.size()];
	 * 
	 * for (int i = 0; i < equipments.length; i++) { equipments[i] = new
	 * Equipment();
	 * 
	 * String itemRevisionType = equipmentList.get(i).getTypeObject()
	 * .getName();
	 * 
	 * System.out.println("---------1-----------------" + itemRevisionType);
	 * System.out.println("---------2-----------------" +
	 * equipmentList.get(i).getPropertyObject("item_id") .getStringValue());
	 * 
	 * equipments[i].setEquipID(equipmentList.get(i)
	 * .getPropertyObject("cn9DevNum").getStringValue());
	 * equipments[i].setEquipName(equipmentList.get(i)
	 * .getPropertyObject("cn9DevName").getStringValue());
	 * equipments[i].setSpecAModel(equipmentList.get(i)
	 * .getPropertyObject("cn9SpecAModel") .getStringValue());
	 * equipments[i].setPrimMaterial(equipmentList.get(i)
	 * .getPropertyObject("cn9PrimMaterial") .getStringValue());
	 * equipments[i].setQuantity(equipmentList.get(i)
	 * .getPropertyObject("cn9Quantity").getDoubleValue());
	 * equipments[i].setUnitOfMeasure(equipmentList.get(i)
	 * .getPropertyObject("cn9UnitOfMeasure") .getStringValue());
	 * equipments[i].setUnitWeight(equipmentList.get(i)
	 * .getPropertyObject("cn9UnitWeight") .getDoubleValue());
	 * equipments[i].setOverallWeight(equipmentList.get(i)
	 * .getPropertyObject("cn9OverallWeight") .getDoubleValue());
	 * equipments[i].setDesignTemp(equipmentList.get(i)
	 * .getPropertyObject("cn9DesignTemp") .getStringValue());
	 * equipments[i].setDesignPress(equipmentList.get(i)
	 * .getPropertyObject("cn9NewDesignPress") .getStringValue()); equipments[i]
	 * .setSafeClass(equipmentList.get(i).getPropertyObject(
	 * "cn9SafeClass").getStringValue());
	 * equipments[i].setVerifyClass(equipmentList.get(i)
	 * .getPropertyObject("cn9VerifyClass") .getStringValue());
	 * equipments[i].setDesignSpecClass(equipmentList.get(i)
	 * .getPropertyObject("cn9DesignSpecClass") .getStringValue());
	 * equipments[i].setQualityClass(equipmentList.get(i)
	 * .getPropertyObject("cn9QualityClass") .getStringValue());
	 * equipments[i].setOwningModule(equipmentList.get(i)
	 * .getPropertyObject("cn9OwningModule") .getStringValue());
	 * equipments[i].setAntiQuakeClass(equipmentList.get(i)
	 * .getPropertyObject("cn9AntiQuakeClass") .getStringValue());
	 * equipments[i].setUnitNo(equipmentList.get(i).getPropertyObject(
	 * "cn9OwingGroup").getStringValue());
	 * equipments[i].setSysCode(equipmentList.get(i)
	 * .getPropertyObject("cn9System").getStringValue()); equipments[i]
	 * .setFactoryCode(equipmentList.get(i).getPropertyObject(
	 * "cn9PlantCode").getStringValue());
	 * equipments[i].setAreaCode(equipmentList.get(i)
	 * .getPropertyObject("cn9AreaCode").getStringValue()); equipments[i]
	 * .setLayerCode(equipmentList.get(i).getPropertyObject(
	 * "cn9LayerCode").getStringValue());
	 * equipments[i].setRoomCode(equipmentList.get(i)
	 * .getPropertyObject("cn9ConveNum").getStringValue());
	 * 
	 * equipments[i].setRelatedSpecNo(equipmentList.get(i)
	 * .getPropertyObject("cn9RelatedSpecNo") .getStringValue());
	 * equipments[i].setRelatedSpecVer(equipmentList.get(i)
	 * .getPropertyObject("cn9RelatedSpecVer") .getStringValue()); //add by
	 * xiaolei 20150212 equipments[i].setRelatedSpecIntNo(iedIntNumList.get(i));
	 * 
	 * if (itemRevisionType.equals("CN9SpecMatItemRevision")) {
	 * dmService.getProperties( new ModelObject[] { equipmentList.get(i) },
	 * props2); equipments[i].setClass_PN(equipmentList.get(i)
	 * .getPropertyObject("cn9Class_PN") .getStringValue());
	 * equipments[i].setManuStyle(equipmentList.get(i)
	 * .getPropertyObject("cn9ManuStyle") .getStringValue());
	 * equipments[i].setJointStyle(equipmentList.get(i)
	 * .getPropertyObject("cn9JointStyle") .getStringValue());
	 * equipments[i].setManuStandard(equipmentList.get(i)
	 * .getPropertyObject("cn9ManuStandard") .getStringValue());
	 * equipments[i].setMetricDiam1(equipmentList.get(i)
	 * .getPropertyObject("cn9MetricDiam1") .getStringValue());
	 * equipments[i].setMetricDiam2(equipmentList.get(i)
	 * .getPropertyObject("cn9MetricDiam2") .getStringValue());
	 * equipments[i].setMetricDiam1PressClass(equipmentList.get(i)
	 * .getPropertyObject("cn9MetricDiam1PressClass") .getStringValue());
	 * equipments[i].setMetricDiam2PressClass(equipmentList.get(i)
	 * .getPropertyObject("cn9MetricDiam2PressClass") .getStringValue());
	 * equipments[i].setWallThick(equipmentList.get(i)
	 * .getPropertyObject("cn9WallThick") .getStringValue());
	 * equipments[i].setJointWallThick(equipmentList.get(i)
	 * .getPropertyObject("cn9JointWallThick") .getStringValue()); } else {
	 * equipments[i].setClass_PN(""); equipments[i].setManuStyle("");
	 * equipments[i].setJointStyle(""); equipments[i].setManuStandard("");
	 * equipments[i].setMetricDiam1(""); equipments[i].setMetricDiam2("");
	 * equipments[i].setMetricDiam1PressClass("");
	 * equipments[i].setMetricDiam2PressClass("");
	 * equipments[i].setWallThick(""); equipments[i].setJointWallThick(""); }
	 * 
	 * } return equipments; } else return null; } catch (Exception e) {
	 * System.out.println("ExecuteSavedQuery service request failed.");
	 * System.out.println(e.getMessage()); throw e; } }
	 */
	// modify by xiaolei 20150320 begin

	/*
	 * public List<Attachment> getAttachment(ItemRevision itemRev, String
	 * relationTypeName, String fileId, String revId) throws Exception {
	 * DataManagementService dmServ = DataManagementService.getService(session
	 * .getConnection()); SessionService sessionService =
	 * SessionService.getService(session .getConnection());
	 * PreferenceManagementService prefService = PreferenceManagementService
	 * .getService(session.getConnection()); prefService.refreshPreferences();
	 * 
	 * 
	 * PreferencesResponse responsePref = sessionService.getPreferences( "site",
	 * new String[] { "CNPE_webservice_ftp_config" }); List<String> ftpConfigs =
	 * null; ServiceData serviceData = responsePref.serviceData; if
	 * (serviceData.sizeOfPartialErrors() == 0) { Preferences prefs =
	 * responsePref.preferences;
	 * 
	 * ftpConfigs = prefs.getPreference("CNPE_webservice_ftp_config");
	 * 
	 * if (ftpConfigs == null) { throw new
	 * Exception("CNPE_webservice_ftp_config配置错误！"); } } else { String ret = "";
	 * String[] msgs = serviceData.getPartialError(0).getMessages();
	 * 
	 * for (int i = 0; i < msgs.length; i++) { ret = ret + msgs[i] + " "; }
	 * 
	 * System.out.println(ret); throw new Exception(ret); }
	 * 
	 * FtpUpload ftp = new FtpUpload(ftpConfigs.get(0),
	 * Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),
	 * ftpConfigs.get(3));
	 * 
	 * ArrayList<Attachment> attachments = new ArrayList<Attachment>();
	 * 
	 * ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
	 * pref.expItemRev = true; pref.info = new RelationAndTypesFilter2[1];
	 * pref.info[0] = new RelationAndTypesFilter2(); pref.info[0].relationName =
	 * relationTypeName;
	 * 
	 * ExpandGRMRelationsResponse response = dmServ
	 * .expandGRMRelationsForPrimary(new ModelObject[] { itemRev }, pref);
	 * 
	 * if (response.serviceData.sizeOfPartialErrors() > 0) { String ret = "";
	 * String[] msgs = response.serviceData.getPartialError(0) .getMessages();
	 * 
	 * for (int i = 0; i < msgs.length; i++) { ret = ret + msgs[i] + " "; }
	 * 
	 * System.out.println(ret); throw new Exception(ret); }
	 * 
	 * // 所有导出文件放在系统temp目录下的WSOutPutFile目录中 File exportDir = new
	 * File(System.getProperty("java.io.tmpdir"), "LOTOutPutFile"); if
	 * (!exportDir.exists()) exportDir.mkdir(); if (!exportDir.isDirectory()) {
	 * exportDir.delete(); exportDir.mkdir(); }
	 * 
	 * // 每个导出的对象有单独的目录 String tmpDirName = fileId + "_" + revId + "_" + (new
	 * Date()).getTime(); File tmpExportDir = new File(exportDir, tmpDirName);
	 * 
	 * if (!tmpExportDir.exists()) tmpExportDir.mkdir(); if
	 * (!tmpExportDir.isDirectory()) { tmpExportDir.delete();
	 * tmpExportDir.mkdir(); } //System.out.println(
	 * System.getProperty("java.library.path"
	 * )+"-------------------------------------------------");
	 * FileManagementUtility fMSFileManagement = null; try { fMSFileManagement =
	 * new FileManagementUtility( session.getConnection());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } // 1 从数据集中导出文件
	 * ExpandGRMRelationsOutput[] outputs = response.output;
	 * 
	 * ModelObject[] relationObjects = null;
	 * 
	 * if (outputs[0].otherSideObjData.length > 0) { ExpandGRMRelationsData data
	 * = outputs[0].otherSideObjData[0];
	 * 
	 * if (data.otherSideObjects.length > 0) { relationObjects =
	 * data.otherSideObjects; dmServ.getProperties(relationObjects, new String[]
	 * { "object_name", "ref_list" }); for (int i = 0; i <
	 * relationObjects.length; i++) {
	 * 
	 * if (!(relationObjects[i] instanceof Dataset)) { continue; } //add by
	 * xiaolei 20150319 begin //如果数据集文件类型不是PDF则不传输 String objectType =
	 * relationObjects[i].getTypeObject().getName();
	 * if(!objectType.equals("PDF")) continue; //add end
	 * 
	 * ModelObject dataset = relationObjects[i];
	 * 
	 * String objectName = relationObjects[i].getPropertyObject(
	 * "object_name").getStringValue();
	 * 
	 * ModelObject[] imanfiles = dataset.getPropertyObject(
	 * "ref_list").getModelObjectArrayValue();
	 * 
	 * ImanFile zIFile = null;
	 * 
	 * for (int j = 0; j < imanfiles.length; j++) { if (imanfiles[j] instanceof
	 * ImanFile) { zIFile = (ImanFile) imanfiles[j]; break; } }
	 * 
	 * if (zIFile == null) { System.err.println("No named reference found for "
	 * + objectName); continue; }
	 * 
	 * GetFileResponse getFileResp = fMSFileManagement .getFiles(new ImanFile[]
	 * { zIFile }); if (getFileResp.sizeOfPartialErrors() > 0) {
	 * System.err.println("get files error for " + objectName); continue; }
	 * 
	 * dmServ.getProperties(new ModelObject[] { zIFile }, new String[] {
	 * "file_name","original_file_name" }); String fileName =
	 * zIFile.get_file_name(); String originalFileName =
	 * zIFile.get_original_file_name(); String fullFileName =
	 * tmpExportDir.getAbsolutePath() + File.separator + fileName; File
	 * fileToCopy[] = getFileResp.getFiles(); File fullFile = new
	 * File(fullFileName); if (fullFile.exists()) fullFile.delete();
	 * fileToCopy[0].renameTo(fullFile);
	 * 
	 * System.out.println("----------" + fullFileName + "-----------");
	 * 
	 * long fileSize = getFileSizes(fullFile);
	 * 
	 * // 2 将文件上传到ftp String ftpDir = ftpConfigs.get(4) + tmpDirName + "/";
	 * ftp.upload(ftpDir, fullFileName);
	 * 
	 * // 3 组合包 Attachment attachment = new Attachment();
	 * 
	 * attachment.setFileID(originalFileName);
	 * attachment.setFileName(originalFileName); attachment.setFilePath(ftpDir +
	 * fileName); attachment.setFileSize((int) fileSize);
	 * 
	 * attachments.add(attachment);
	 * 
	 * } } } fMSFileManagement.term(); return attachments; }
	 */
	public long getFileSizes(File f) {// 取得文件大小
		long s = 0;
		try {
			if (f.exists()) {
				FileInputStream fis = null;
				fis = new FileInputStream(f);
				s = fis.available();
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取文件大小出错:" + f.getAbsolutePath());
		}
		return s;
	}

	// add by xiaolei 20150320
	public ModelObject[] queryEquipItemRevision(String projectId,
			String cn9RelatedSpecNo, String cn9RelatedSpecVer) {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name
						.equals("__CNPE_Find_EquipItem_By_Project_Date_LOT")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out
					.println("There is not an '__CNPE_Find_EquipItem_By_Project_Date_LOT' query.");
			return null;
		}

		try {

			String[] entries = new String[] { "项目 ID", "cn9RelatedSpecNo",
					"cn9RelatedSpecVer" };

			String[] values = new String[] { projectId, cn9RelatedSpecNo,
					cn9RelatedSpecVer };

			int limit = 0;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			if (found.objects.length == 0) {
				return null;
			} else {
				return found.objects;
			}

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ModelObject queryItemRevisionByItemId(String itemId) {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("1 成品文件统一查询")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an 'Item Revision...' query.");
			return null;
		}

		try {
			/*
			 * //String[] entries = new String[]{"Item ID","Revision"}; String[]
			 * entries = new String[]{"零组件 ID","版本"};
			 * 
			 * String[] values = new String[]{itemId,revId};
			 * 
			 * int limit = 25;
			 * 
			 * ExecuteSavedQueryResponse found =
			 * queryService.executeSavedQuery(query, entries, values, limit);
			 * 
			 * if (found.objects.length == 0) { return null; } else { return
			 * found.objects[0]; }
			 */

			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
			savedQueryInput[0].entries = new String[] { "外部编码" };
			savedQueryInput[0].values = new String[] { itemId };

			SavedQueriesResponse response = queryService
					.executeSavedQueries(savedQueryInput);

			QueryResults found = response.arrayOfResults[0];

			if (found.objectUIDS.length == 0)
				return null;
			else {
				DataManagementService dmService = DataManagementService
						.getService(session.getConnection());
				ServiceData serviceData = dmService
						.loadObjects(found.objectUIDS);
				if (serviceData.sizeOfPartialErrors() == 0) {
					return serviceData.getPlainObject(0);
				}
			}

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ModelObject queryItemRevisionByNeiItemId(String itemId) {
		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("1 成品文件统一查询")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an 'Item Revision...' query.");
			return null;
		}

		try {
			/*
			 * //String[] entries = new String[]{"Item ID","Revision"}; String[]
			 * entries = new String[]{"零组件 ID","版本"};
			 * 
			 * String[] values = new String[]{itemId,revId};
			 * 
			 * int limit = 25;
			 * 
			 * ExecuteSavedQueryResponse found =
			 * queryService.executeSavedQuery(query, entries, values, limit);
			 * 
			 * if (found.objects.length == 0) { return null; } else { return
			 * found.objects[0]; }
			 */

			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
			savedQueryInput[0].entries = new String[] { "内部编码" };
			savedQueryInput[0].values = new String[] { itemId };

			SavedQueriesResponse response = queryService
					.executeSavedQueries(savedQueryInput);

			QueryResults found = response.arrayOfResults[0];

			if (found.objectUIDS.length == 0)
				return null;
			else {
				DataManagementService dmService = DataManagementService
						.getService(session.getConnection());
				ServiceData serviceData = dmService
						.loadObjects(found.objectUIDS);
				if (serviceData.sizeOfPartialErrors() == 0) {
					return serviceData.getPlainObject(0);
				}
			}

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}
	/**
	 * 根据表名和导出标记查询数据
	 * @param typeName
	 * @param isExport
	 * @return
	 * @throws Exception
	 */
	public ModelObject[] queryFilesRevision(String typeName,String isExport)
			throws Exception {

		ImanQuery query = null;

		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("查询需要同步至设计分包的Item")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an '查询需要同步至设计分包的Item");
			// return false;
			throw new Exception("There is not an '查询需要同步至设计分包的Item");
		}

		try {
			String[] entries = new String[2];
			entries[0] = "类型";
			entries[1] = "是否已导出";
			

			String[] values = new String[2];
			values[0] = typeName;
			values[1] = isExport;
			

			int limit = 0;

			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(
					query, entries, values, limit);

			return found.objects;
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
		
	
	}

	
	public static void main(String[] args) {

	}
}
