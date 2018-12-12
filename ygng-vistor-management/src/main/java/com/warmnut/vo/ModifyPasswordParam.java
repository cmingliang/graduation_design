package com.warmnut.vo;

import com.warmnut.bean.S_User;

/**
 * 修改密码的参数
 * @author qy
 *
 */
public class ModifyPasswordParam {
	private S_User user;
	private String oldPassword;
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public S_User getUser() {
		return user;
	}
	public void setUser(S_User user) {
		this.user = user;
	}
	
	
	
	

}
