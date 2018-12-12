package com.warmnut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.warmnut.bean.S_Visitor;

public interface S_VistorRepository extends JpaRepository<S_Visitor, Integer>,JpaSpecificationExecutor<S_Visitor>{
	/**
	 * 根据证件类型和证件号码查找访客信息
	 * @param certificateType
	 * @param certificateNumber
	 * @return
	 */
	S_Visitor findByCertificateTypeAndCertificateNumber(Integer certificateType,String certificateNumber);
}
