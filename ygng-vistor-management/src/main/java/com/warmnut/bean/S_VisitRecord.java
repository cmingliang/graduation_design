package com.warmnut.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="s_visit_record")
public class S_VisitRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer visitor;
	private Integer interviewed;
	private Date intentVisitTime;
	private Date actualVisitTime;
	private Date intentLeaveTime;
	private Date actualLeaveTime;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVisitor() {
		return visitor;
	}
	public void setVisitor(Integer visitor) {
		this.visitor = visitor;
	}
	public Integer getInterviewed() {
		return interviewed;
	}
	public void setInterviewed(Integer interviewed) {
		this.interviewed = interviewed;
	}
	public Date getIntentVisitTime() {
		return intentVisitTime;
	}
	public void setIntentVisitTime(Date intentVisitTime) {
		this.intentVisitTime = intentVisitTime;
	}
	public Date getActualVisitTime() {
		return actualVisitTime;
	}
	public void setActualVisitTime(Date actualVisitTime) {
		this.actualVisitTime = actualVisitTime;
	}
	public Date getIntentLeaveTime() {
		return intentLeaveTime;
	}
	public void setIntentLeaveTime(Date intentLeaveTime) {
		this.intentLeaveTime = intentLeaveTime;
	}
	public Date getActualLeaveTime() {
		return actualLeaveTime;
	}
	public void setActualLeaveTime(Date actualLeaveTime) {
		this.actualLeaveTime = actualLeaveTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public S_VisitRecord() {
		super();
	}
	
	


}
