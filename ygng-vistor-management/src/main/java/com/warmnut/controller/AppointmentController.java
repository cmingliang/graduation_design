package com.warmnut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warmnut.bean.S_User;
import com.warmnut.bean.V_VisitorRecord;
import com.warmnut.common.bean.PageParam;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.PageData;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.service.AppointmentService;
import com.warmnut.vo.VistorParam;
import com.warmnut.vo.VistorRecordQueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 预约相关的控制器
 * @author qy
 *
 */
@Api( tags= "预约相关接口")
@RestController
@RequestMapping("/appointment")
public class AppointmentController extends BaseController{
	@Autowired
	private AppointmentService appointmentService;
	/**
	 * 新增预约信息
	 * @param param
	 * @return
	 */
	@ApiOperation(value="新增预约信息")
	@PostMapping
	public SimpleResponse addAppointment(@RequestBody VistorParam param) {
		
		return appointmentService.addAppointment(param);		
	}
	
	@GetMapping
	@ApiOperation(value="获取自己的预约信息,一次拉取全部信息")
	public DataResponse getMyAppointment() {
		S_User loginUser = getLoginUser();
		return appointmentService.getMyAppointment(loginUser.getId());
	}
	
	@GetMapping("/page")
	@ApiOperation(value="获取自己的预约信息,分页")
	public DataResponse<PageData<V_VisitorRecord>> getMyAppointmentByPage(PageParam param) {
		S_User loginUser = getLoginUser();
		return appointmentService.getMyAppointmentByPage(loginUser.getId(),param);
	}
	
	@GetMapping("/all/page")
	@ApiOperation(value="获取所有的预约信息,支持分页")
	public DataResponse getAppointmentByPage(VistorRecordQueryParam param) {
//		S_User loginUser = getLoginUser();
		return appointmentService.getAppointmentByPage(param);
	}
}
