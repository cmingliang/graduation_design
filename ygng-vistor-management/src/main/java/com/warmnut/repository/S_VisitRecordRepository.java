package com.warmnut.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.warmnut.bean.S_VisitRecord;

public interface S_VisitRecordRepository extends JpaRepository<S_VisitRecord, Integer>,JpaSpecificationExecutor<S_VisitRecord>{


	@Query(value="select visitor from S_VisitRecord e where e.interviewed = ?1")
	Set<Integer> findVisitorIdByInterviewed(Integer interviewedId);
	
}
