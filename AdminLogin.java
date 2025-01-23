package com.softpro.MediMart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adminlogin")
public class AdminLogin {
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Id
	@Column(nullable = false, length = 50)
	private String userid;
	
	@Column(nullable = false, length = 30)
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
