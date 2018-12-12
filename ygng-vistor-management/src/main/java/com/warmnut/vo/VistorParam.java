package com.warmnut.vo;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 预约参数
 * @author qy
 *
 */
@ApiModel(value="新增预约的参数")
public class VistorParam {
	
	/**预约人姓名*/
	private String name;
	/**预约人单位*/
	private String unit;
	/**预约人照片*/
	@ApiModelProperty(value="照片 MultipartFile")
	private MultipartFile imageFile;
	/**预约人证件类型*/
	@ApiModelProperty(value="证件类型:1-身份证",example="1")
	private Integer certificateType;
	/**预约人证件号码*/
	private String certificateNumber;
	/**预约人预约访问时间*/
	@ApiModelProperty(value="预约访问时间,字符串 yyyy-MM-dd HH:mm:ss",example="2018-10-10 10:10:10")
	private String intentVisitTime;	
	/**预约人预约离开时间*/
	@ApiModelProperty(value="预约离开时间,字符串 yyyy-MM-dd HH:mm:ss",example="2018-10-10 10:10:10")
    private String intentLeaveTime;
	
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
	public MultipartFile getImageFile() {
		return imageFile;
	}
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	public String getIntentVisitTime() {
		return intentVisitTime;
	}
	public void setIntentVisitTime(String intentVisitTime) {
		this.intentVisitTime = intentVisitTime;
	}
	public String getIntentLeaveTime() {
		return intentLeaveTime;
	}
	public void setIntentLeaveTime(String intentLeaveTime) {
		this.intentLeaveTime = intentLeaveTime;
	}
	
	
	

}
