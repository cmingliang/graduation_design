package com.warmnut.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="s_device_setting")
public class S_DeviceSetting {
	public static final String EMERGENT = "emergent_setting";
	/**开启紧急设置*/
	public static final int EMERGENT_OPEN = 1;
	/**关闭紧急设置*/
	public static final int EMERGENT_CLOSE = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String type;
	private Integer status;
	private Integer[] devices;
	private Date createTime;
	private Date modifyTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer[] getDevices() {
		return devices;
	}
	public void setDevices(Integer[] devices) {
		this.devices = devices;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
	
	


}
