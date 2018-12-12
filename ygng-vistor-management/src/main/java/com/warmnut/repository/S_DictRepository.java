package com.warmnut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.warmnut.bean.S_Dict;
@Component
public interface S_DictRepository extends JpaRepository<S_Dict, Integer>{
	List<S_Dict> findByDictName(String dictName);

}
