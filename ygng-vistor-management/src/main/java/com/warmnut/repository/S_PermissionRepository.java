package com.warmnut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.warmnut.bean.S_Permission;

public interface S_PermissionRepository extends JpaRepository<S_Permission, Integer> {
	
	
	@Query(value = "select sp from S_Permission sp ,S_RolePermission srp ,S_User sa where sp.id = srp.permissionId "
			+ "and srp.roleId = sa.roleId and sa.id = ?1")
	List<S_Permission> findByAdminId(Integer adminId);
	
	
	@Query(value = "select sp from S_Permission sp ,S_RolePermission srp  where sp.id = srp.permissionId "
			+ "and srp.roleId = ?1")
	List<S_Permission> findByRoleId(Integer roleId);
	
	@Query(value = "select sp.id from S_Permission sp ,S_RolePermission srp  where sp.id = srp.permissionId "
			+ "and srp.roleId = ?1")
	Integer[] findIdsByRoleId(Integer roleId);
	
	@Query(value = "select GROUP_CONCAT(sp.name) from S_Permission sp ,S_Role_Permission srp  where sp.id = srp.permission_Id "
			+ "and srp.role_Id = ?1", nativeQuery = true)
	String findPermissionStringByRoleId(Integer roleId);

}
