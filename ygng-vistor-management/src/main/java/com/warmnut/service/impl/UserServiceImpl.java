package com.warmnut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.warmnut.bean.S_User;
import com.warmnut.bean.V_UserDepartment;
import com.warmnut.cache.UserCache;
import com.warmnut.common.util.PageUtil;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.PageData;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;
import com.warmnut.dto.UserInfoDto;
import com.warmnut.exception.ServiceError;
import com.warmnut.org.bean.S_DepartmentMember;
import com.warmnut.org.repository.S_DepartmentMemberRepository;
import com.warmnut.repository.S_UserRepository;
import com.warmnut.repository.V_UserDepartmentRepository;
import com.warmnut.service.UserService;
import com.warmnut.vo.ModifyPasswordParam;
import com.warmnut.vo.UserAddParam;
import com.warmnut.vo.UserListDepartmentParam;
import com.warmnut.vo.UserListParam;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private S_UserRepository userRepository;
	
	@Autowired
	private UserCache userCache;

	@Autowired
	private BCryptPasswordEncoder BCryptPasswordEncoder;

	@Autowired
	private V_UserDepartmentRepository V_UserDepartmentRepository;

	@Autowired
	private S_DepartmentMemberRepository departmentMemberRepository;

	@Override
	public DataResponse<S_User> getUserInfo(Integer id) {
		S_User user = userCache.findById(id);
		return new DataResponse<S_User>(YgngError.SUCCESS, user);
	}

	@Override
	public DataResponse<UserInfoDto> getUserInfoDetail(Integer id) {
		S_User user = userCache.findById(id);

		UserInfoDto userInfo = new UserInfoDto();

		BeanUtils.copyProperties(user, userInfo);
		return new DataResponse<UserInfoDto>(YgngError.SUCCESS, userInfo);
	}

	@Override
	@Transactional
	public SimpleResponse updateUserInfo(UserInfoDto userInfo) {
		// S_User user = userRepository.findById(userInfo.getId()).get();
		// CompanyInfo companyInfo =
		// companyInfoRepository.findByUserId(userInfo.getId());
		// if (companyInfo == null) {
		// companyInfo = new CompanyInfo();
		// }
		// BeanUtils.copyProperties(userInfo, user);
		// BeanUtils.copyProperties(userInfo, companyInfo,"id");
		// Date date = new Date();
		// user.setModifyTime(date);
		// companyInfo.setModifyTime(date);
		// companyInfo.setUserId(user.getId());
		// userRepository.save(user);
		// companyInfoRepository.save(companyInfo);
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public DataResponse<List<S_User>> getAdmin() {
		// List<S_User> users = userRepository.findByType(1);
		// for (S_User user : users) {
		// userWrapper.wrap(user);
		// }
		return new DataResponse<List<S_User>>(YgngError.SUCCESS, null);
	}

	@Override
	public SimpleResponse addAdmin(S_User user) {
		user.setId(null);
		user.setCreateTime(new Date());
		user.setType(1);
		user.setPassword(BCryptPasswordEncoder.encode(user.getPassword()));
		userCache.save(user);
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public SimpleResponse modifyPassword(ModifyPasswordParam param) {
		if (BCryptPasswordEncoder.matches(param.getOldPassword(), param.getUser().getPassword())) {
			S_User user = userRepository.findById(param.getUser().getId()).get();
			String password = BCryptPasswordEncoder.encode(param.getNewPassword());
			param.getUser().setPassword(password);
			user.setPassword(password);
			userCache.save(user);
			return new SimpleResponse(YgngError.SUCCESS);
		} else {
			return new SimpleResponse(ServiceError.USER_PASSWORD_ERROR.value(),
					ServiceError.USER_PASSWORD_ERROR.getReasonPhrase());
		}

	}

	public static void main(String[] args) {
		//
		//
		System.out.println(new BCryptPasswordEncoder().matches("123456",
				"$2a$10$ICTTjIMQE60bWNwviv2XI.VJgHN4P.HuZ48/0/sNASLid./aGguM2"));
	}

	@Override
	public SimpleResponse updateAdmin(Integer id, Integer roleId) {
		S_User s_User = userRepository.findById(id).get();
		s_User.setRoleId(roleId);
		userCache.save(s_User);
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public SimpleResponse checkPhone(String phone) {
		S_User user = userRepository.findByPhone(phone);
		if (user != null) {
			return new SimpleResponse(ServiceError.REGISTER_DUPLICATE_PHONE.value(),
					ServiceError.REGISTER_DUPLICATE_PHONE.getReasonPhrase());
		}
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public DataResponse getUserInfoList(UserListParam param) {
		Pageable page = PageUtil.getPage(param);
		Page<V_UserDepartment> findAll;
		if (param.getIsRoot() == 1) {//查询全部的
			findAll = V_UserDepartmentRepository.findAll(page);
		} else {
			List<Integer> ids = departmentMemberRepository.findUserIdByDepartmentId(param.getDepartmentId());
			Specification<V_UserDepartment> querySpecifi = new Specification<V_UserDepartment>() {
				@Override
				public Predicate toPredicate(Root<V_UserDepartment> root, CriteriaQuery<?> criteriaQuery,
						CriteriaBuilder criteriaBuilder) {

					List<Predicate> predicates = new ArrayList<>();

					predicates.add(root.get("id").in(ids));

					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			findAll = V_UserDepartmentRepository.findAll(querySpecifi, page);
			
		}
		
		PageData<V_UserDepartment> convertPageData = PageUtil.convertPageData(findAll);

		return new DataResponse<>(YgngError.SUCCESS, convertPageData);
	}

	@Override
	@Transactional
	public SimpleResponse addUserInfo(UserAddParam param) {
		param.setId(null);
		S_User user = new S_User();
		BeanUtils.copyProperties(param, user);
		user.setPassword(new BCryptPasswordEncoder().encode("123456"));
		user.setCreateTime(new Date());
		userCache.save(user);
		if(param.getDepartmentIds() != null && param.getDepartmentIds().length > 0) {//保存员工和部门的映射
			S_DepartmentMember member;
			List<S_DepartmentMember> members = new ArrayList<>();
			for(int departmentId : param.getDepartmentIds()) {
				member = new S_DepartmentMember(user.getId(), departmentId);
				members.add(member);			
			}
			departmentMemberRepository.saveAll(members);
			
		}	
		
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	@Transactional
	public SimpleResponse updateUserInfo(UserAddParam param) {
		S_User user = userRepository.findById(param.getId()).get();
		user.setName(param.getName());
		user.setPhone(param.getPhone());
		user.setEmail(param.getEmail());
		user.setPosition(param.getPosition());
		user.setJobNumber(param.getJobNumber());
		userCache.save(user);
		departmentMemberRepository.deleteByMemberIds(new Integer[] {user.getId()});
		if(param.getDepartmentIds() != null && param.getDepartmentIds().length > 0) {//保存员工和部门的映射
			S_DepartmentMember member;
			List<S_DepartmentMember> members = new ArrayList<>();
			for(int departmentId : param.getDepartmentIds()) {
				member = new S_DepartmentMember(user.getId(), departmentId);
				members.add(member);			
			}
			departmentMemberRepository.saveAll(members);
			
		}	
		
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public SimpleResponse deleteUser(Integer userId) {
		userCache.remove(userId);
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public DataResponse getUserInfoByDepartments(UserListDepartmentParam param) {
		Integer[] departmentIds = param.getDepartmentIds();	
		List<S_User> findAllById = new ArrayList<>();
		if(departmentIds != null && departmentIds.length > 0) {		
			Set<Integer> userIds = departmentMemberRepository.findUserIdByDepartmentIds(departmentIds);
			findAllById = userRepository.findAllById(userIds);
		}
		return new DataResponse<>(YgngError.SUCCESS, findAllById);
	}
}
