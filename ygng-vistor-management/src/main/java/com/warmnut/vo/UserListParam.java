package com.warmnut.vo;

import com.warmnut.common.bean.PageParam;

/**
 * 获取用户列表的参数
 * @author qy
 *
 */
public class UserListParam extends PageParam{
	private Integer departmentId;
	private Integer isRoot;
	
	public Integer getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(Integer isRoot) {
		this.isRoot = isRoot;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	
	
	

}
