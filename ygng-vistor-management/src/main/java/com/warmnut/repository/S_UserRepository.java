package com.warmnut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.warmnut.bean.S_User;

public interface S_UserRepository extends JpaRepository<S_User, Integer>,JpaSpecificationExecutor<S_User>{
	S_User findByName(String Name);
	
	S_User findByPhone(String phone);
	
	List<S_User> findByType(int type);
	
	
	
	
}
