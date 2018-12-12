package com.warmnut.vo;

/**
 * 访客授权的参数
 * @author qy
 *
 */
public class VisitorAuthParam {
	private Integer type;
	private Integer[] devices;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer[] getDevices() {
		return devices;
	}
	public void setDevices(Integer[] devices) {
		this.devices = devices;
	}
	
	

}
