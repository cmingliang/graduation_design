package com.warmnut.wrapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.warmnut.bean.S_Device;
import com.warmnut.support.DictMap;

/**
 * 设备列表的包装类
 * @author qy
 *
 */
@Component
public class DeviceWrapper {
	private static String tableName = "device";
	
	
	public void wrap(S_Device bean) {
		bean.setPropertyName(DictMap.getFieldDetail(tableName, "property",bean.getProperty()));	
	}
	
	public void wrap(List<S_Device>	 list) {
		if(list != null) {
			for(S_Device bean : list) {
				bean.setPropertyName(DictMap.getFieldDetail(tableName, "property",bean.getProperty()));
			}
		}
			
	}
	
	

}
