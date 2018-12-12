package com.warmnut.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="S_Role")
public class S_Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	@Transient
	private Integer[] permission;
	@Transient
	private String permissionStr;
	@JsonIgnore
	private Date createTime;
	@JsonIgnore
	private Date modifyTime;
	
	private int changeable;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getPermissionStr() {
		return permissionStr;
	}
	public void setPermissionStr(String permissionStr) {
		this.permissionStr = permissionStr;
	}
	public Integer[] getPermission() {
		return permission;
	}
	public void setPermission(Integer[] permission) {
		this.permission = permission;
	}
	public int getChangeable() {
		return changeable;
	}
	public void setChangeable(int changeable) {
		this.changeable = changeable;
	}
	
	
	
	


}
