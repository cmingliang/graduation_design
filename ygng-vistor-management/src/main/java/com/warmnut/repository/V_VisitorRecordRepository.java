package com.warmnut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.warmnut.bean.V_VisitorRecord;

public interface V_VisitorRecordRepository extends JpaRepository<V_VisitorRecord, Integer>,JpaSpecificationExecutor<V_VisitorRecord>{

}
