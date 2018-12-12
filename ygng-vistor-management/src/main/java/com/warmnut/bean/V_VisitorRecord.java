package com.warmnut.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="v_visitor_record")
@ApiModel(value="访客预约记录")
public class V_VisitorRecord {
	@Id
	private Integer id;
	@ApiModelProperty(value="访客姓名")
	private String name;
	@ApiModelProperty(value="访客单位",example="阳光暖果")
	private String unit;
	@ApiModelProperty(value="证件类型:1-身份证",example="1")
	private Integer certificateType;
	@ApiModelProperty(value="证件号码",example="3755989124451454455")
	private String certificateNumber;
	@ApiModelProperty(value="图片相对路径",example="image/110.jpg")
	private String imagePath;
	@ApiModelProperty(value="访客的id",example="1")
	private Integer visitor;
	@ApiModelProperty(value="被访人的id",example="12")
	private Integer interviewed;
	@Transient
	@ApiModelProperty(value="被访人的姓名",example="lisa")
	private String interviewedName;
	@ApiModelProperty(value="预约访问时间,字符串",example="2018-10-10 10:10:10")
	private Date intentVisitTime;
	@ApiModelProperty(value="实际访问时间,字符串",example="2018-10-10 10:10:10")
	private Date actualVisitTime;
	@ApiModelProperty(value="预约离开时间,字符串",example="2018-10-10 10:10:10")
	private Date intentLeaveTime;
	@ApiModelProperty(value="实际离开时间,字符串",example="2018-10-10 10:10:10")
	private Date actualLeaveTime;
	@ApiModelProperty(value="创建时间",example="2018-10-10 10:10:10")
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(Integer certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	public String getInterviewedName() {
		return interviewedName;
	}
	public void setInterviewedName(String interviewedName) {
		this.interviewedName = interviewedName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
