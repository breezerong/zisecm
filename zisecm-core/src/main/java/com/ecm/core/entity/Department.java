package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName  Department   
 * @Description TODO(部门类)   
 * @author yaozhigang
 * @date 2018年7月2日 上午11:10:38  
 *
 */
public class Department  extends EcmSysObject implements Comparable<Department>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//显示名称
	private String displayName;
	//父对象ID
	private String parentID;
	//子部门数目
	private Integer childCount;
	//子部门列表
	private List<Department> departments = new ArrayList<Department>();
	
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the parentID
	 */
	public String getParentID() {
		return parentID;
	}
	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	/**
	 * @return the childCount
	 */
	public Integer getChildCount() {
		return departments.size();
	}
	/**
	 * @param childCount the childCount to set
	 */
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	/**
	 * @return the departments
	 */
	public List<Department> getDepartments() {
		Collections.sort(departments);
		return departments;
	}
	/**
	 * @param departments the departments to set
	 */
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	@Override
	public String toString() {
		return "Department [id=" + getId() + ", name=" + getName() + ", displayName=" + displayName + ", parentID=" + parentID
				+ ", childCount=" + childCount + ", departments=" + departments + "]";
	}

	@Override
	public int compareTo(Department d) {
		return d.getName().compareTo(this.getName());
	}
}
