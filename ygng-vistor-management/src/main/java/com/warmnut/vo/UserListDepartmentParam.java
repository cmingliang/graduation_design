package com.warmnut.vo;

import java.util.Arrays;

public class UserListDepartmentParam {
	private Integer[] departmentIds;

	public Integer[] getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(Integer[] departmentIds) {
		this.departmentIds = departmentIds;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserListDepartmentParam [departmentIds=");
		builder.append(Arrays.toString(departmentIds));
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
