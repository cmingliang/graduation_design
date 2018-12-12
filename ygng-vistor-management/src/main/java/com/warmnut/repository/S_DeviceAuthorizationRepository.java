package com.warmnut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.warmnut.bean.S_DeviceAuthorization;

public interface S_DeviceAuthorizationRepository extends JpaRepository<S_DeviceAuthorization, Integer>,JpaSpecificationExecutor<S_DeviceAuthorization>{

	
	List<S_DeviceAuthorization> findByDeviceId(Integer deviceId);
	
	@Query(value = "select sda.userId from S_DeviceAuthorization sda  where sda.deviceId = ?1 ")
	Integer[] findUserIdsByDeviceId(Integer devices);
}
