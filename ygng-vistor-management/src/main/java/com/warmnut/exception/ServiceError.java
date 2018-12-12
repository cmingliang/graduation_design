package com.warmnut.exception;

/**
 * 错误枚举
 * @author qinyao
 *
 */
public enum ServiceError {
	REGISTER_DUPLICATE_PHONE(4101, "该手机号已注册,请直接登录"),
	
	REGISTER_CODE_ERROE(4102, "验证码错误"),
	
	ORDER_UNCOMPLETE(4201, "存在未完成的订单"),
	
	APP_INSUFFICIENT_ALLOWANCE(4401,"剩余授权数不足或授权过期"),
	
	FILE_FORMATTER_ERROR(4421,"上传文件格式错误"),
	
	USER_PASSWORD_ERROR(4301,"原密码错误");
	

	private final int value;

	private final String reasonPhrase;


	ServiceError(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}
	
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	

}
