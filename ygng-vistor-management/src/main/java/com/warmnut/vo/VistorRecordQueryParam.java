package com.warmnut.vo;

import com.warmnut.common.bean.PageParam;

public class VistorRecordQueryParam extends PageParam{
	private String name;
	private String certificateNumber;	
	private String interviewedName;
	private String startTime;
	private String endTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInterviewedName() {
		return interviewedName;
	}
	public void setInterviewedName(String interviewedName) {
		this.interviewedName = interviewedName;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	

}
