package com.warmnut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.warmnut.bean.S_VistorAuth;

public interface S_VistorAuthRepository extends JpaRepository<S_VistorAuth, Integer>,JpaSpecificationExecutor<S_VistorAuth>{

}
