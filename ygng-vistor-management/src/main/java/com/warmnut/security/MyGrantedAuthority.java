package com.warmnut.security;

import org.springframework.security.core.GrantedAuthority;

public class MyGrantedAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7753344656395602435L;
	private String url;
	private String method;	
	private String authority;

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public MyGrantedAuthority(String url, String method, String authority) {
		super();
		this.url = url;
		this.method = method;		
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}
