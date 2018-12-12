package com.warmnut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.warmnut.bean.S_DeviceAuthorization;
import com.warmnut.bean.S_DeviceSetting;
import com.warmnut.bean.S_VistorAuth;
import com.warmnut.bean.V_UserDepartment;
import com.warmnut.common.util.PageUtil;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.PageData;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;
import com.warmnut.repository.S_DeviceAuthorizationRepository;
import com.warmnut.repository.S_DeviceSettingRepository;
import com.warmnut.repository.S_VistorAuthRepository;
import com.warmnut.repository.V_UserDepartmentRepository;
import com.warmnut.service.AuthService;
import com.warmnut.vo.AuthParam;
import com.warmnut.vo.AuthSettingParam;
import com.warmnut.vo.AuthUserListParam;
import com.warmnut.vo.VisitorAuthParam;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	@Autowired
	private V_UserDepartmentRepository V_UserDepartmentRepository;
	@Autowired
	private S_DeviceAuthorizationRepository deviceAuthorizationRepository;
	@Autowired
	private S_VistorAuthRepository vistorAuthRepository;
	
	@Autowired
	private S_DeviceSettingRepository deviceSettingRepository;

	@Override
	public SimpleResponse auth(AuthParam param) {
		Integer[] devices = param.getDevices();
		Integer[] userIds = param.getUserIds();
		Date date = new Date();
		S_DeviceAuthorization deviceAuthorization;
		List<S_DeviceAuthorization> list = new ArrayList<>();
		for(Integer device:devices) {//遍历授权数据
			for(Integer userId:userIds) {
				deviceAuthorization = new S_DeviceAuthorization(device,userId,S_DeviceAuthorization.DEFAULT_AUTHTYPE,date);
				list.add(deviceAuthorization);
			}			
		}
		deviceAuthorizationRepository.saveAll(list);		
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public SimpleResponse getAuthUser(AuthUserListParam param) {
		Integer deviceId = param.getDeviceId();
		Integer[] ids = deviceAuthorizationRepository.findUserIdsByDeviceId(deviceId);
		
		Pageable page = PageUtil.getPage(param);
		Page<V_UserDepartment> findAll;		
		
		Specification<V_UserDepartment> querySpecifi = new Specification<V_UserDepartment>() {
			@Override
			public Predicate toPredicate(Root<V_UserDepartment> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if(ids == null || ids.length ==0) {
					predicates.add(root.get("id").in(new Integer[] {0}));
				}else {
					predicates.add(root.get("id").in(ids));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		
		findAll = V_UserDepartmentRepository.findAll(querySpecifi, page);
		PageData<V_UserDepartment> convertPageData = PageUtil.convertPageData(findAll);

		return new DataResponse<>(YgngError.SUCCESS, convertPageData);
	}

	@Override
	public SimpleResponse authVisitor(VisitorAuthParam param) {
		List<S_VistorAuth> findAll = vistorAuthRepository.findAll();
		S_VistorAuth auth;
		if(findAll != null && findAll.size() > 0) {
			auth = findAll.get(0);
		}else {
			auth = new S_VistorAuth();
			auth.setCreateTime(new Date());
		}
		auth.setType(param.getType());
		if(S_VistorAuth.TYPE_USERDEFINED == param.getType()) {
			auth.setDevices(param.getDevices());	
		}else {
			auth.setDevices(null);
		}
		vistorAuthRepository.save(auth);
		return  new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public DataResponse getAuthVisitor() {
		List<S_VistorAuth> findAll = vistorAuthRepository.findAll();
		S_VistorAuth auth = null;
		if(findAll != null && findAll.size() > 0) {
			auth = findAll.get(0);
		}
		return new DataResponse<>(YgngError.SUCCESS, auth);
	}

	@Override
	public SimpleResponse authSetting(AuthSettingParam param) {
		S_DeviceSetting setting = deviceSettingRepository.findByType(S_DeviceSetting.EMERGENT);
		Date date = new Date();
		if(setting == null) {
			setting = new S_DeviceSetting();
			setting.setCreateTime(date);
		}
		setting.setType(S_DeviceSetting.EMERGENT);
		if(S_DeviceSetting.EMERGENT_CLOSE == param.getStatus()) {
			setting.setDevices(null);
		}else {
			setting.setDevices(param.getDevices());
		}
		setting.setStatus(param.getStatus());
		setting.setModifyTime(date);
		deviceSettingRepository.save(setting);
		return  new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public DataResponse getAuthSetting() {
		S_DeviceSetting setting = deviceSettingRepository.findByType(S_DeviceSetting.EMERGENT);
		return new DataResponse<>(YgngError.SUCCESS, setting);
	}

}
