package com.warmnut.service;

import com.warmnut.bean.V_VisitorRecord;
import com.warmnut.common.bean.PageParam;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.PageData;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.vo.VistorParam;
import com.warmnut.vo.VistorRecordQueryParam;

public interface AppointmentService {
	/**
	 * 新增预约信息
	 * @param param
	 * @return
	 */
	public SimpleResponse addAppointment(VistorParam param);
	
	/**
	 * 获取预约记录
	 * @param userId
	 * @return
	 */
	public DataResponse getMyAppointment(Integer userId);
	
	/**
	 * 获取预约记录,支持分页
	 * @param userId
	 * @param param
	 * @return
	 */
	public DataResponse<PageData<V_VisitorRecord>> getMyAppointmentByPage(Integer userId,PageParam param);
	
	/**
	 * 按条件查询预约记录
	 * @param param
	 * @return
	 */
	public DataResponse getAppointmentByPage(VistorRecordQueryParam param);

}
