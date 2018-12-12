package com.warmnut.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.warmnut.bean.S_Device;
import com.warmnut.common.util.PageUtil;
import com.warmnut.common.vo.DataResponse;
import com.warmnut.common.vo.PageData;
import com.warmnut.common.vo.SimpleResponse;
import com.warmnut.common.vo.YgngError;
import com.warmnut.repository.S_DeviceRepository;
import com.warmnut.service.DeviceService;
import com.warmnut.vo.DeviceListParam;
import com.warmnut.wrapper.DeviceWrapper;

/**
 * 设备操作实现类
 * @author qy
 *
 */
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService{
	
	@Autowired
	private S_DeviceRepository deviceRepository;
	
	@Autowired
	private DeviceWrapper deviceWrapper;

	@Override
	public DataResponse getDeviceList(DeviceListParam param) {
		if(param.getStart() != null) {
			Pageable page = PageUtil.getPage(param);
			Page<S_Device> findAll = deviceRepository.findAll(page);
			PageData<S_Device> convertPageData = PageUtil.convertPageData(findAll);
			deviceWrapper.wrap(convertPageData.getContent());
			return new DataResponse<>(YgngError.SUCCESS, convertPageData);
		}else {
			List<S_Device> findAll = deviceRepository.findAll();
			return new DataResponse<>(YgngError.SUCCESS, findAll);
		}
		
	}

	@Override
	public SimpleResponse addDevice(S_Device device) {
		device.setId(null);
		device.setCreateTime(new Date());
		deviceRepository.save(device);
		return new SimpleResponse(YgngError.SUCCESS);
	}

	@Override
	public SimpleResponse updateDevice(S_Device device) {
		device.setModifyTime(new Date());
		deviceRepository.save(device);
		return new SimpleResponse(YgngError.SUCCESS);	
	}

	@Override
	public SimpleResponse deleteDevice(Integer id) {
		deviceRepository.deleteById(id);
		return new SimpleResponse(YgngError.SUCCESS);	
	}
	
	

}
