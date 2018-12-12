package com.warmnut.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.warmnut.bean.S_User;
import com.warmnut.repository.S_UserRepository;

/**
 * 用户的缓存层
 * @author qy
 *
 */
@Component
public class UserCache {
	@Autowired
	private S_UserRepository userRepository;
	
	@Cacheable(value="management_user",key="#userId")
	public S_User findById(Integer userId) {
		S_User user = userRepository.findById(userId).get();
		return user;
		
	}
	
	@CachePut(value="management_user",key="#user.id")
	public S_User save(S_User user) {
		userRepository.save(user);
		return user;		
	}
	
	@CacheEvict(value = "management_user",key="#userId")
	public void remove(Integer userId) {
		userRepository.deleteById(userId);			
	}
	
	
	
	

}
