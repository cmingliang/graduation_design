package com.warmnut.security;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.warmnut.bean.S_Permission;
import com.warmnut.bean.S_User;
import com.warmnut.repository.S_PermissionRepository;
import com.warmnut.repository.S_UserRepository;

/**
 * 表单登录，社交登录返回user对象服务
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private S_UserRepository userRepository;

	@Autowired
	private S_PermissionRepository permissionRepository;
	
	@Override
	public UserDetails loadUserByUsername(String Name) throws UsernameNotFoundException {
		logger.info("表单登录用户名为:" + Name);
		S_User admin = userRepository.findByPhone(Name);
		if (admin != null) {
			logger.info(admin.toString());
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			List<S_Permission> permissions = permissionRepository.findByAdminId(admin.getId());
			for (S_Permission permission : permissions) {
				GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(),
						permission.getMethod(),permission.getAuthority());
				grantedAuthorities.add(grantedAuthority);
			}
			admin.setAuthorities(grantedAuthorities);
			return admin;
		} else {			
			throw new UsernameNotFoundException("Name " + Name + " not found");
		}
	}

}
