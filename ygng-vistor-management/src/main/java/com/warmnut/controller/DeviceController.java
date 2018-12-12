package com.warmnut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.warmnut.bean.S_Device;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.service.DeviceService;
import com.warmnut.vo.DeviceListParam;

import springfox.documentation.annotations.ApiIgnore;
/**
 * 设备管理
 * @author qy
 *
 */
@ApiIgnore
@RestController
@RequestMapping("/device")
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	
	@GetMapping
	public DataResponse getDeviceList(DeviceListParam param) {
		return deviceService.getDeviceList(param);
	}
	
	@PostMapping
	public SimpleResponse addDevice(@RequestBody S_Device device) {		
		
		return deviceService.addDevice(device);
	}
	
	@PutMapping
	public SimpleResponse updateDevice(@RequestBody S_Device device) {
		return deviceService.updateDevice(device);
	}
	
	@DeleteMapping
	public SimpleResponse deleteDevice(Integer id) {
		return deviceService.deleteDevice(id);
	}

}
