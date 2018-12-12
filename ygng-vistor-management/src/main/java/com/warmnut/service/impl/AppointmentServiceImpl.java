package com.warmnut.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.warmnut.bean.S_VisitRecord;
import com.warmnut.bean.S_Visitor;
import com.warmnut.bean.V_VisitorRecord;
import com.warmnut.common.bean.PageParam;
import com.warmnut.common.util.DateUtil;
import com.warmnut.common.util.PageUtil;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.PageData;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;
import com.warmnut.repository.S_VisitRecordRepository;
import com.warmnut.repository.S_VistorRepository;
import com.warmnut.repository.V_VisitorRecordRepository;
import com.warmnut.service.AppointmentService;
import com.warmnut.support.UserSupport;
import com.warmnut.vo.VistorParam;
import com.warmnut.vo.VistorRecordQueryParam;
import com.warmnut.wrapper.V_VisitorRecordWrapper;

@Service("appointmentService")
public class AppointmentServiceImpl implements AppointmentService {
	private static Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private S_VistorRepository vistorRepository;

	@Autowired
	private S_VisitRecordRepository visitRecordRepository;

	@Autowired
	private V_VisitorRecordRepository visitorRecordRepository;
	
	@Autowired
	private V_VisitorRecordWrapper visitorRecordWrapper;

	@Value("${config.faceImagePath}")
	private String faceImagePath;

	@Override
	@Transactional
	public SimpleResponse addAppointment(VistorParam param) {
		S_Visitor vistor = vistorRepository.findByCertificateTypeAndCertificateNumber(param.getCertificateType(),
				param.getCertificateNumber());
		Date date = new Date();
		if (vistor == null) {
			vistor = new S_Visitor();
			vistor.setCreateTime(date);
		}

		BeanUtils.copyProperties(param, vistor);

		MultipartFile imageFile = param.getImageFile();
		if (imageFile != null && !imageFile.isEmpty()) {
			String filePath = File.separator + System.currentTimeMillis() + imageFile.getOriginalFilename();
			try {
				imageFile.transferTo(new File(faceImagePath + filePath));
				vistor.setImagePath(filePath);
			} catch (Exception e) {
				logger.error("上传照片错误:{}", e.getMessage());
			}
		}

		vistorRepository.save(vistor);

		// 保存预约信息
		S_VisitRecord record = new S_VisitRecord();
		record.setVisitor(vistor.getId());
		record.setInterviewed(UserSupport.getUser().getId());
		record.setIntentVisitTime(DateUtil.parseTime(param.getIntentVisitTime()));
		record.setIntentLeaveTime(DateUtil.parseTime(param.getIntentLeaveTime()));
		record.setCreateTime(date);
		visitRecordRepository.save(record);
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public DataResponse getMyAppointment(Integer userId) {
		Set<Integer> ids = visitRecordRepository.findVisitorIdByInterviewed(userId);
		List<S_Visitor> findAllById = vistorRepository.findAllById(ids);
		return new DataResponse<>(YgngError.SUCCESS, findAllById);
	}

	@Override
	public DataResponse<PageData<V_VisitorRecord>> getMyAppointmentByPage(Integer userId, PageParam param) {
		Pageable page = PageUtil.getPage(param);
		Page<V_VisitorRecord> findAll;

		Specification<V_VisitorRecord> querySpecifi = new Specification<V_VisitorRecord>() {
			@Override
			public Predicate toPredicate(Root<V_VisitorRecord> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				predicates.add(criteriaBuilder.equal(root.get("interviewed"), userId));

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		findAll = visitorRecordRepository.findAll(querySpecifi, page);
		PageData<V_VisitorRecord> convertPageData = PageUtil.convertPageData(findAll);
		return new DataResponse<>(YgngError.SUCCESS, convertPageData);
	}

	@Override
	public DataResponse getAppointmentByPage(VistorRecordQueryParam param) {
		Pageable page = PageUtil.getPage(param);
		Page<V_VisitorRecord> findAll;

		Specification<V_VisitorRecord> querySpecifi = new Specification<V_VisitorRecord>() {
			@Override
			public Predicate toPredicate(Root<V_VisitorRecord> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
//				if (param.getInterviewedName() != null) {
//					predicates.add(criteriaBuilder.like(root.get("interviewed").as(String.class),
//							"%" + param.getInterviewedName() + "%"));
//				}
				if (param.getName() != null) {
					predicates.add(criteriaBuilder.like(root.get("name").as(String.class),
							"%" + param.getName() + "%"));
				}

				if (param.getCertificateNumber()!= null) {
					predicates.add(criteriaBuilder.like(root.get("certificateNumber").as(String.class),
							"%" + param.getCertificateNumber() + "%"));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		findAll = visitorRecordRepository.findAll(querySpecifi, page);
		for(V_VisitorRecord record: findAll.getContent()) {
			visitorRecordWrapper.wrap(record);
		}
		PageData<V_VisitorRecord> convertPageData = PageUtil.convertPageData(findAll);
		return new DataResponse<>(YgngError.SUCCESS, convertPageData);
	}

}
