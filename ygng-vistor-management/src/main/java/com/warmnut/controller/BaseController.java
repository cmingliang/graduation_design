package com.warmnut.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import com.warmnut.bean.S_User;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
public class BaseController {
	
	
	/**
	 * 获取登录用户
	 * @return
	 */
	protected S_User getLoginUser() {
		S_User user = null;
		try {
			
			user = (S_User) SecurityContextHolder.getContext()
	    			.getAuthentication() 
	    			.getPrincipal(); 
		}catch (Exception e) {
			
		}
		return user;
		
	}

}
