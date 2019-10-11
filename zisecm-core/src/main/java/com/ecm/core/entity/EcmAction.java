package com.ecm.core.entity;

public class EcmAction extends EcmSysObject {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roleName;

    private String objectPermission;

    private String parameter;

    private String parameterDes;

    private String callFunction;

    private Integer nextComponent;

    private String actionType;
    
    private boolean hasPermission;

    public boolean isHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
	}

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getObjectPermission() {
        return objectPermission;
    }

    public void setObjectPermission(String objectPermission) {
        this.objectPermission = objectPermission == null ? null : objectPermission.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getParameterDes() {
        return parameterDes;
    }

    public void setParameterDes(String parameterDes) {
        this.parameterDes = parameterDes == null ? null : parameterDes.trim();
    }

    public String getCallFunction() {
        return callFunction;
    }

    public void setCallFunction(String callFunction) {
        this.callFunction = callFunction == null ? null : callFunction.trim();
    }

    public Integer getNextComponent() {
        return nextComponent;
    }

    public void setNextComponent(Integer nextComponent) {
        this.nextComponent = nextComponent;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }
    
    public EcmAction clone() {
    	EcmAction obj =new EcmAction();
    	obj.setActionType(actionType);
    	obj.setCallFunction(callFunction);
    	obj.setDescription(getDescription());
    	obj.setHasPermission(hasPermission);
    	obj.setId(getId());
    	obj.setName(getName());
    	obj.setNextComponent(nextComponent);
    	obj.setParameter(parameter);
    	obj.setParameterDes(parameterDes);
    	obj.setRoleName(roleName);
    	return obj;
    }
}