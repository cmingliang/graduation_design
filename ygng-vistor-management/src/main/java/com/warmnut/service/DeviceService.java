package com.warmnut.service;

import com.warmnut.bean.S_Device;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.vo.DeviceListParam;

public interface DeviceService {
	
	/**
	 * 获取设备列表
	 * @param param
	 * @return
	 */
	DataResponse getDeviceList(DeviceListParam param);
	
	/**
	 * 添加设备
	 * @param device
	 * @return
	 */
	public SimpleResponse addDevice(S_Device device);
	
	/**
	 * 更新设备
	 * @param device
	 * @return
	 */
	public SimpleResponse updateDevice(S_Device device);
	
	/**
	 * 删除设备
	 * @param id
	 * @return
	 */
	public SimpleResponse deleteDevice(Integer id);

}
