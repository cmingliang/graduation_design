package com.warmnut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.warmnut.bean.V_UserDepartment;

public interface V_UserDepartmentRepository extends JpaRepository<V_UserDepartment, Integer> ,JpaSpecificationExecutor<V_UserDepartment>{
	

	
}
