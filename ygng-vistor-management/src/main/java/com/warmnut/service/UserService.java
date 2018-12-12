package com.warmnut.service;

import java.util.List;

import com.warmnut.bean.S_User;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.dto.UserInfoDto;
import com.warmnut.vo.ModifyPasswordParam;
import com.warmnut.vo.UserAddParam;
import com.warmnut.vo.UserListDepartmentParam;
import com.warmnut.vo.UserListParam;

public interface UserService {
	
	/**
	 * 获取用户信息
	 * @return
	 */
	public DataResponse<S_User> getUserInfo(Integer id);
	
	/**
	 * 获取用户列表,分页
	 * @param param
	 * @return
	 */
	public DataResponse getUserInfoList( UserListParam param);
	
	/**
	 * 根据部门id获取用户列表
	 * @param departmentIds
	 * @return
	 */
	public DataResponse getUserInfoByDepartments(UserListDepartmentParam param);
	/**
	 * 添加用户
	 * @param param
	 * @return
	 */
	public SimpleResponse addUserInfo( UserAddParam param) ;
	
	public SimpleResponse deleteUser( Integer userId);
	
	/**
	 * 更新用户
	 * @param param
	 * @return
	 */
	public SimpleResponse updateUserInfo( UserAddParam param) ;
	

	/**
	 * 获取用户详情
	 * @return
	 */
	public DataResponse<UserInfoDto> getUserInfoDetail(Integer id);
	

	/**
	 * 更新用户信息
	 * @return
	 */
	public SimpleResponse updateUserInfo(UserInfoDto userInfo);	
	
	
	public DataResponse<List<S_User>> getAdmin();
	
	
	/**添加管理员
	 * 
	 * @param user
	 * @return
	 */
	public SimpleResponse addAdmin(S_User user);
	public SimpleResponse updateAdmin(Integer id,Integer roleId);
	
	public SimpleResponse modifyPassword(ModifyPasswordParam param);
	
	public SimpleResponse checkPhone(String phone);

}
