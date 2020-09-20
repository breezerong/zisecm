/**
 * 
 */
package com.ecm.core.cache.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecm.core.entity.EcmAction;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmCardSearch;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.entity.EcmComponent;
import com.ecm.core.entity.EcmDefType;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.EcmMenu;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.entity.EcmStore;
import com.ecm.core.entity.EcmSuggestion;
import com.ecm.core.entity.EcmSystemEvent;

/**
 * @ClassName  CacheManagerOper   
 * @Description TODO(缓存统一入口类)   
 * @author Haihong Rong
 * @date 2018年6月26日 上午11:20:54  
 *
 */
public class CacheManagerOper {
	
	//系统参数缓存
	private static final Map<String,EcmParameter> ECM_PARAMETER_CACHE = new HashMap<String,EcmParameter>();
	//列表配置缓存
	private static final Map<String,EcmGridView> ECM_GRID_VIEW_CACHE = new HashMap<String,EcmGridView>();
	//事件配置缓存对象
	private static final Map<String,EcmAction> ECM_ACTION_CACHE = new HashMap<String,EcmAction>();
	//表单配置缓存对象
	private static final Map<String,EcmForm> ECM_FORM_CACHE = new HashMap<String,EcmForm>();
	
	//表单配置缓存对象
    private static final Map<String,EcmFormItem> ECM_FORMITEM_CACHE = new HashMap<String,EcmFormItem>();
		
	//菜单配置缓存对象
	private static final Map<String,EcmMenu> ECM_MENU = new HashMap<String,EcmMenu>();
	
	//卡片搜索缓存对象
	private static final Map<String,EcmCardSearch> ECM_CARD_SEARCH = new HashMap<String,EcmCardSearch>();
	//组件配置缓存对象
	private static final Map<String,EcmComponent> ECM_COMPONENT = new HashMap<String,EcmComponent>();	
	//语言缓存对象
	private static final Map<String,String> LANGUAGE_CACHE = new HashMap<String,String>();	
	
	//语言缓存对象
	private static final Map<String,EcmLangInfo> LANGUAGE_INFO = new HashMap<String,EcmLangInfo>();
	
	//存储缓存对象
	private static final Map<String,EcmStore> STORE_CACHE = new HashMap<String,EcmStore>();	
	
	//事件缓存对象
    private static final Map<String,EcmSystemEvent> SYSTEM_EVENT = new HashMap<String,EcmSystemEvent>();
	
	//文档缓存对象
	private static final Map<String,EcmAttribute> DOC_ATTR_CACHE = new HashMap<String,EcmAttribute>();
	
	//文档类型缓存对象
     private static final Map<String,EcmDefType> DOC_TYPE_CACHE = new HashMap<String,EcmDefType>();
	
	//建议缓存对象
	private static List<EcmSuggestion> SUGGESTION_CACHE;	
	
	//缓存活动配置对象
	private static final Map<String,EcmCfgActivity> CFG_ACTIVITY = new HashMap<String,EcmCfgActivity>();
	
	//客制化缓存
	private static final Map<String,Object> CUSTOM_CACHE = new HashMap<String,Object>();
	
	
	private static Boolean FinishLoadCacheTag=false;
	/**
	 * @Title getEcmParameters   
	 * @Description TODO(获取系统参数缓存全部数据)   
	 * @return Map<String,EcmParameter>          
	 * @author Haihong Rong
	 * @date 2018年6月26日 下午2:24:20 
	 * @modify Haihong Rong 2018年6月26日 下午2:24:20
	 */
	public static Map<String,EcmParameter> getEcmParameters() {
		return ECM_PARAMETER_CACHE;
	}
	
	/**
	 * 
	 * @Title getEcmGridViews   
	 * @Description TODO(获取列表配置缓存全部数据)   
	 * @return Map<String,EcmGridView>          
	 * @author Haihong Rong
	 * @date 2018年6月26日 下午4:32:56 
	 * @modify Haihong Rong 2018年6月26日 下午4:32:56
	 */
	public static Map<String,EcmGridView> getEcmGridViews() {
		return ECM_GRID_VIEW_CACHE;
	}
	
	/**
	 * 
	 * @Title getEcmActions   
	 * @Description TODO(获取事件配置全部属性)   
	 * @return Map<String,EcmAction>          
	 * @author Haihong Rong
	 * @date 2018年6月26日 下午4:34:54 
	 * @modify Haihong Rong 2018年6月26日 下午4:34:54
	 */
	public static Map<String,EcmAction> getEcmActions() {
		return ECM_ACTION_CACHE;
	}
	
	/**
	 * 
	 * @Title getEcmForms   
	 * @Description TODO(表单配置缓存对象)   
	 * @return Map<String,EcmForm>          
	 * @author Haihong Rong
	 * @date 2018年6月26日 下午5:07:54 
	 * @modify Haihong Rong 2018年6月26日 下午5:07:54
	 */
	public static Map<String,EcmForm> getEcmForms() {
		return ECM_FORM_CACHE;
	}
	
	public static Map<String,EcmFormItem> getEcmFormItems() {
		return ECM_FORMITEM_CACHE;
	}

	/**
	 * 
	 * @Title getEcmMenus 
	 * @Description TODO(菜单配置缓存对象)   
	 * @return Map<String,EcmMenu>          
	 * @author Haihong Rong
	 * @date 2018年6月27日 下午5:07:54 
	 * @modify Haihong Rong 2018年6月27日 下午5:07:54
	 */
	public static Map<String,EcmMenu> getEcmMenus() {
		return ECM_MENU;
	}

	/**
	 * @Title getEcmCardSearchs   
	 * @Description TODO(获取卡片搜索缓存对象)   
	 * @return Map<String,EcmCardSearch>          
	 * @author Haihong Rong
	 * @date 2018年6月27日 上午10:02:17 
	 * @modify Haihong Rong 2018年6月27日 上午10:02:17
	 */
	public static Map<String,EcmCardSearch> getEcmCardSearchs() {
		return ECM_CARD_SEARCH;
	}
	
	
	/**
	 * 
	 * @Title getEcmComponents 
	 * @Description TODO(组件配置缓存对象)   
	 * @return Map<String,EcmComponent>          
	 * @author Haihong Rong
	 * @date 2018年6月27日 下午5:07:54 
	 * @modify Haihong Rong 2018年6月27日 下午5:07:54
	 */
	public static Map<String,EcmComponent> getEcmComponents() {
		return ECM_COMPONENT;
	}
	
	/**
	 * 
	 * @Title getLanguageCaches   
	 * @Description TODO(语言标签配置缓存对象)   
	 * @return Map<String,String>          
	 * @author Haihong Rong
	 * @date 2018年6月27日 下午5:39:53 
	 * @modify Haihong Rong 2018年6月27日 下午5:39:53
	 */
	public static Map<String,String> getLanguages() {
		return LANGUAGE_CACHE;
	}
	
	/**
	 * 语言标签缓存
	 * @return
	 */
	public static Map<String,EcmLangInfo> getLangInfos() {
		return LANGUAGE_INFO;
	}
	/**
	 * 
	 * @Title getStores   
	 * @Description TODO(存储配置缓存对象)   
	 * @return Map<String,String>          
	 * @author Haihong Rong
	 * @date 2019年6月27日 下午5:39:53 
	 * @modify Haihong Rong 2019年6月27日 下午5:39:53
	 */
	public static Map<String,EcmStore> getEcmStores() {
		return STORE_CACHE;
	}
	
	public static Map<String,EcmSystemEvent> getEcmSystemEvents() {
		return SYSTEM_EVENT;
	}
	
	public static Map<String,EcmAttribute> getEcmAttributes() {
		return DOC_ATTR_CACHE;
	}
	
	public static Map<String,EcmDefType> getEcmDefTypes() {
		return DOC_TYPE_CACHE;
	}

	public static List<EcmSuggestion> getSuggestionCache() {
		return SUGGESTION_CACHE;
	}
	/**
	 * 活动界面配置，Key：流程定义ID+活动名
	 * @return
	 */
	public static Map<String,EcmCfgActivity> getCfgActivityCache() {
		return CFG_ACTIVITY;
	}
	
	public static Boolean getFinishLoadCacheTag() {
		return FinishLoadCacheTag;
	}
	public static  void setFinishLoadCacheTag(Boolean FinishLoadCacheTagVar) {
		 FinishLoadCacheTag=FinishLoadCacheTagVar;
	}
	

	public static void setSuggestionCache(List<EcmSuggestion> list) {
		SUGGESTION_CACHE = list;
	}

	public static Map<String,Object> getCustomCache() {
		return CUSTOM_CACHE;
	}
	
}
