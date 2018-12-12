package com.warmnut.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 访客授权
 * @author qy
 *
 */
@Entity
@Table(name="s_vistor_auth")
public class S_VistorAuth {
	public  static final int TYPE_FOLLOW_HOST = 1;
	
	public  static final int TYPE_USERDEFINED = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer type;
	private Integer[] devices;
	private Date createTime;
	private Date modifyTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
