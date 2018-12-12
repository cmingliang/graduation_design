package com.warmnut.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.vo.AuthParam;
import com.warmnut.vo.AuthSettingParam;
import com.warmnut.vo.AuthUserListParam;
import com.warmnut.vo.VisitorAuthParam;

public interface AuthService {
	/**
	 * 添加授权记录
	 * @param param
	 * @return
	 */
	public SimpleResponse auth(AuthParam param);
	
	/**
	 * 设置访客授权的具体权限
	 * @param param
	 * @return
	 */
	public SimpleResponse authVisitor(@RequestBody VisitorAuthParam param);
	
	/**
	 * 获取访客授权的设置
	 * @return
	 */
	public DataResponse getAuthVisitor();
	
	/**
	 * 紧急设置
	 * @param param
	 * @return
	 */
	public SimpleResponse authSetting( AuthSettingParam param);
	
	
	/**
	 * 获取紧急设置
	 * @return
	 */
	public DataResponse getAuthSetting();
	
	/**
	 * 获取已经授权的用户
	 * @param deviceId
	 * @return
	 */
	public SimpleResponse getAuthUser(AuthUserListParam param);
	

}
