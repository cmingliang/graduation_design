package com.warmnut.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="S_Role_Permission")
public class S_RolePermission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer roleId;
	private Integer permissionId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	
	 @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof S_RolePermission) {
        	S_RolePermission vo = (S_RolePermission) obj;
 
            // 比较每个属性的值 一致时才返回true
            if (vo.roleId.equals(this.roleId) && vo.permissionId.equals(this.permissionId))
                return true;
        }
        return false;
    }
	 
    /**
     * 重写hashcode 方法，返回的hashCode不一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return roleId.hashCode() * permissionId.hashCode();
    }
    
   

	
	
}
