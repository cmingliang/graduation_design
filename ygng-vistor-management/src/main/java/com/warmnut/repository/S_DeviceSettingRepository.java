package com.warmnut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.warmnut.bean.S_DeviceSetting;

public interface S_DeviceSettingRepository extends JpaRepository<S_DeviceSetting, Integer>,JpaSpecificationExecutor<S_DeviceSetting>{

	S_DeviceSetting findByType(String type);
}
