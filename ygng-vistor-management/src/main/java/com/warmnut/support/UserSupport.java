package com.warmnut.support;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.warmnut.bean.S_User;

public class UserSupport {

	/**
	 * 获取登录用户
	 * 
	 * @return
	 */
	public static S_User getUser() {
		S_User user = null;
		try {
			user = (S_User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {

		}
		return user;
	}

	/**
	 * 修改session中的用户信息
	 * @param user
	 */
	public static void updateUser(UserDetails user) {

		try {
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
					authentication.getCredentials());
			auth.setDetails(authentication.getDetails());
			context.setAuthentication(auth);
		} catch (Exception e) {

		}

	}

	public static boolean hasPermission(S_User user, String permission) {
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		for (GrantedAuthority autority : authorities) {
			boolean equals = autority.getAuthority().equals(permission);
			if (equals) {
				return true;
			}
		}
		return false;
	}

}
