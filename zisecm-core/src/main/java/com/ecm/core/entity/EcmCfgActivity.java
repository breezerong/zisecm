package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ecm.core.cache.manager.CacheManagerOper;

/**
 * 活动配置
 * @author Haihong Rong
 * Date:2020年4月13日 下午3:02:16
 */
public class EcmCfgActivity extends EcmObject {
    
	/**
	 * 流程定义ID
	 */
    private String processId;

    /**
     * 流程定义名称
     */
    private String processName;

    /**
     * 活动名称
     */
    private String activityName;
    
	/**
	 * 角色名称，空不需要选择，所有*
	 */
    private String roleName;

    /**
     * 是否多选
     */
    private Boolean isMulti;
/**
 * 通过活动名称
 */
    private String nextActivity;
/**
 * 回退活动名称
 */
    private String rejectActivity;
/**
 * 通过标签名
 */
    private String nextActivityLabel;
/**
 * 回退标签名
 */
    private String rejectActivityLabel;
/**
 * 是否启用代理
 */
    private Boolean enableDelegate;
/**
 * 是否启用重做
 */
    private Boolean enableRepeat;
/**
 * 是否允许编辑
 */
    private Boolean enableEdit;
/**
 * 组件名称
 */
    private String componentName;
    
    /**
     * 组件
     */
    private EcmComponent component;
    
    /**
     * 选人活动，流程启动start，多个用英文分号分隔
     */
    private String selectActivities;
    /**
     * 查询条件
     */
    private String formCondition;// FORM_CONDITION
    
    /**
     * 表单参数  FORM_PARAMETER
     */
    private String formParameter; 
    
    
    public String getFormCondition() {
		return formCondition;
	}

	public void setFormCondition(String formCondition) {
		this.formCondition = formCondition;
	}

	/**
     * 表单属性名称
     */
    private String formAttribute;
    
    public String getSelectActivities() {
		return selectActivities;
	}

	public void setSelectActivities(String selectActivities) {
		this.selectActivities = selectActivities;
		if(!StringUtils.isEmpty(selectActivities)) {
			String[] strs = selectActivities.split(";");
			for(String s: strs) {
				selectActivityList.add(s);
			}
		}
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<String> getSelectActivityList() {
		return selectActivityList;
	}

	/**
     * 排序序号
     */
    private int orderIndex = 1;
    
    /**
     * 选人活动列表
     */
    private List<String> selectActivityList = new ArrayList<String>();


    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Boolean getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(Boolean isMulti) {
        this.isMulti = isMulti;
    }

    public String getNextActivity() {
        return nextActivity;
    }

    public void setNextActivity(String nextActivity) {
        this.nextActivity = nextActivity == null ? null : nextActivity.trim();
    }

    public String getRejectActivity() {
        return rejectActivity;
    }

    public void setRejectActivity(String rejectActivity) {
        this.rejectActivity = rejectActivity == null ? null : rejectActivity.trim();
    }

    public String getNextActivityLabel() {
        return nextActivityLabel;
    }

    public void setNextActivityLabel(String nextActivityLabel) {
        this.nextActivityLabel = nextActivityLabel == null ? null : nextActivityLabel.trim();
    }

    public String getRejectActivityLabel() {
        return rejectActivityLabel;
    }

    public void setRejectActivityLabel(String rejectActivityLabel) {
        this.rejectActivityLabel = rejectActivityLabel == null ? null : rejectActivityLabel.trim();
    }

    public Boolean getEnableDelegate() {
        return enableDelegate;
    }

    public void setEnableDelegate(Boolean enableDelegate) {
        this.enableDelegate = enableDelegate;
    }

    public Boolean getEnableRepeat() {
        return enableRepeat;
    }

    public void setEnableRepeat(Boolean enableRepeat) {
        this.enableRepeat = enableRepeat;
    }

    public Boolean getEnableEdit() {
        return enableEdit;
    }

    public void setEnableEdit(Boolean enableEdit) {
        this.enableEdit = enableEdit;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName == null ? null : componentName.trim();
        if(!StringUtils.isEmpty(this.componentName)) {
        	component = CacheManagerOper.getEcmComponents().get(this.componentName);
        }
    }

	public EcmComponent getComponent() {
		return component;
	}

	public void setComponent(EcmComponent component) {
		this.component = component;
	}

	public String getFormAttribute() {
		return formAttribute;
	}

	public void setFormAttribute(String formAttribute) {
		this.formAttribute = formAttribute;
	}

	public String getFormParameter() {
		return formParameter;
	}

	public void setFormParameter(String formParameter) {
		this.formParameter = formParameter;
	}
    
}