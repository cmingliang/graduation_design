package com.warmnut.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import com.warmnut.bean.S_User;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;
import com.warmnut.dto.UserInfoDto;
import com.warmnut.log.BussinessLog;
import com.warmnut.security_core.validate.code.sms.SmsCodeProcessor;
import com.warmnut.service.UserService;
import com.warmnut.vo.ModifyPasswordParam;
import com.warmnut.vo.UserAddParam;
import com.warmnut.vo.UserListDepartmentParam;
import com.warmnut.vo.UserListParam;
/**
 * 用户相关控制器
 * @author qy
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 发送短信验证码使用
	 */
	@Autowired
	private SmsCodeProcessor smsValidateCodeProcessor;
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	@GetMapping
	public DataResponse getUserInfoList( UserListParam param) {
		return userService.getUserInfoList(param);
	}
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	@PostMapping("/all")
	public DataResponse getUserInfoByDepartments(@RequestBody UserListDepartmentParam param) {
		System.out.println(param);
		return userService.getUserInfoByDepartments(param);
	}
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/test")
	public DataResponse getUserInfoByDepartment(@RequestParam(value="param[]") String[] param) {
		System.out.println(param);
		return new DataResponse();
	}
	
	/**
	 * 添加用户信息	 * 
	 * @return
	 */
	@PostMapping
	public SimpleResponse addUserInfo( @RequestBody UserAddParam param) {
		return userService.addUserInfo(param);
	}
	
	/**
	 * 更新用户信息	 * 
	 * @return
	 */
	@PutMapping
	public SimpleResponse updateUserInfo(@RequestBody UserAddParam param) {
		return userService.updateUserInfo(param);
	}
	
	
	/**
	 * 添加用户信息	 * 
	 * @return
	 */
	@DeleteMapping
	public SimpleResponse deleteUser( Integer userId) {
		return userService.deleteUser(userId);
	}
	
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public DataResponse<S_User> getUserInfo(@PathVariable Integer id) {
		return userService.getUserInfo(id);
	}
	
	
	
	
	
	@GetMapping("/me")
	public DataResponse<Authentication> getMe() {
		Authentication authentication = SecurityContextHolder.getContext()
    			.getAuthentication(); 
		return new DataResponse<Authentication>(YgngError.SUCCESS,authentication);
	}
	
	@GetMapping("/admin")
	public DataResponse<List<S_User>> getAdmin() {		
		return userService.getAdmin();
	}
	
	@PostMapping("/admin")
	@BussinessLog(value = "创建管理员")
	public SimpleResponse addAdmin(S_User user) {
		return userService.addAdmin(user);
	}
	
	@PutMapping("/admin")
	@BussinessLog(value = "更新管理员")
	public SimpleResponse updateAdmin(Integer id,Integer roleId) {
		return userService.updateAdmin(id, roleId);
	}
	
	/**
	 * 获取用户详细信息,包括公司信息
	 * @param id
	 * @return
	 */
	@GetMapping("detail/{id}")
	public DataResponse<UserInfoDto> getUserInfoDetail(@PathVariable Integer id) {
		return userService.getUserInfoDetail(id);
	}
	
	
	
	@PutMapping("/modifyPassword")
	@BussinessLog(value = "修改密码")
	public SimpleResponse modifyPassword(ModifyPasswordParam param) {
		S_User userDetails = (S_User) SecurityContextHolder.getContext()
    			.getAuthentication().getPrincipal(); 
		param.setUser(userDetails);
		return userService.modifyPassword(param);
	}
	

	
	
	
	
	
	@GetMapping("/checkCode")
	public SimpleResponse getCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		smsValidateCodeProcessor.create(new ServletWebRequest(request, response));
		
		return new SimpleResponse(YgngError.SUCCESS);
	}
	
	
	/**
	 * 注册检查手机号
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/checkPhone")
	public SimpleResponse checkPhone(String phone) throws Exception {		
		
		return userService.checkPhone(phone);
	}
	
	
	
	

}
