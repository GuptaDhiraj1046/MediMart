package com.softpro.MediMart.Dao;

import jakarta.validation.constraints.NotEmpty;

public class AdminLoginDao {
	
	private String name;
	@NotEmpty(message = "User id can not be null")
	private String userid;
	
	@NotEmpty(message = "password can not be null")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
