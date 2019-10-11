package com.ecm.core.entity;

public class EcmComponent extends EcmSysObject{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roleName;

    private String url;

    private String parameter;

    private String parameterDes;
    
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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
    
    public EcmComponent clone() {
    	EcmComponent obj =new EcmComponent();
    	obj.setDescription(getDescription());
    	obj.setHasPermission(hasPermission);
    	obj.setId("");
    	obj.setName(getName());
    	obj.setParameter(parameter);
    	obj.setParameterDes(parameterDes);
    	obj.setRoleName(roleName);
    	obj.setUrl(url);
    	return obj;
    }
}