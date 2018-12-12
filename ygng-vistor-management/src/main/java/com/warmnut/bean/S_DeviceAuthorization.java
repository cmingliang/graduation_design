package com.warmnut.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="s_device_authorization")
public class S_DeviceAuthorization {

	public static final int DEFAULT_AUTHTYPE = 1;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer deviceId;
	private Integer userId;
	private Integer authType;
	private Date createTime;
	
	
	
	public S_DeviceAuthorization() {
		super();
	}
	public S_DeviceAuthorization(Integer deviceId, Integer userId) {
		super();
		this.deviceId = deviceId;
		this.userId = userId;
		this.authType = DEFAULT_AUTHTYPE;
		this.createTime = new Date();
	}
	public S_DeviceAuthorization(Integer deviceId, Integer userId, Integer authType) {
		super();
		this.deviceId = deviceId;
		this.userId = userId;
		this.authType = authType;
		this.createTime = new Date();
	}
	public S_DeviceAuthorization(Integer deviceId, Integer userId, Integer authType, Date createTime) {
		super();
		this.deviceId = deviceId;
		this.userId = userId;
		this.authType = authType;
		this.createTime = createTime;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAuthType() {
		return authType;
	}
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("S_DeviceAuthorization [id=");
		builder.append(id);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", authType=");
		builder.append(authType);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append("]");
		return builder.toString();
	}
	
	
	


}
