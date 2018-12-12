package com.warmnut.dto;

public class UserInfoDto {
	
	private Integer id;	
	private String name;	
	private String phone;
	private String email;
	private Integer gender;
	private Integer roleId;
	
	private String cname;
	private String address;
	private String cphone;
	private String tin;
	private String depositBank;
	private String account;
	
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCphone() {
		return cphone;
	}
	public void setCphone(String cphone) {
		this.cphone = cphone;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}	
	public String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfoDto [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);		
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", roleId=");
		builder.append(roleId);		
		builder.append(", cname=");
		builder.append(cname);
		builder.append(", address=");
		builder.append(address);
		builder.append(", cphone=");
		builder.append(cphone);
		builder.append(", tin=");
		builder.append(tin);
		builder.append(", depositBank=");
		builder.append(depositBank);
		builder.append(", account=");
		builder.append(account);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
