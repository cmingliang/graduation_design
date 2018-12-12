package com.warmnut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.service.AuthService;
import com.warmnut.vo.AuthParam;
import com.warmnut.vo.AuthSettingParam;
import com.warmnut.vo.AuthUserListParam;
import com.warmnut.vo.VisitorAuthParam;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 设备授权
 * @author qy
 *
 */
@ApiIgnore
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 给对应的用户设置对应设备的权限
	 * @param param
	 * @return
	 */
	@PostMapping
	public SimpleResponse auth(@RequestBody AuthParam param) {
		
		return authService.auth(param);
	}
	

	/**
	 * 获取设备的授权人员
	 * @param param
	 * @return
	 */
	@GetMapping
	public SimpleResponse getAuthUser(AuthUserListParam param) {
		
		return authService.getAuthUser(param);
	}
	
	/**
	 * 访客授权
	 * @param param
	 * @return
	 */
	@PostMapping("/visitor")
	public SimpleResponse authVisitor(@RequestBody VisitorAuthParam param) {
		
		return authService.authVisitor(param);
	}
	
	/**
	 * 获取访客授权的设置
	 * @return
	 */
	@GetMapping("/visitor")
	public DataResponse getAuthVisitor() {
		
		return authService.getAuthVisitor();
	}
	
	/**
	 * 紧急设置
	 * @param param
	 * @return
	 */
	@PostMapping("/setting")
	public SimpleResponse authSetting(@RequestBody AuthSettingParam param) {
		
		return authService.authSetting(param);
	}
	
	/**
	 * 获取紧急设置
	 * @return
	 */
	@GetMapping("/setting")
	public DataResponse getAuthSetting() {
		
		return authService.getAuthSetting();
	}
	

}
