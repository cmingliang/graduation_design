package com.warmnut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.warmnut.bean.S_Device;

public interface S_DeviceRepository extends JpaRepository<S_Device, Integer>,JpaSpecificationExecutor<S_Device>{

}
