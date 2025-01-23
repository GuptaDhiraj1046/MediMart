package com.softpro.MediMart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "registration")
public class Registration {
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false, length = 10)
	private String gender;
	
	@Column(nullable = false, length = 50)
	private String dob;
	
	@Column(nullable = false, length = 13)
	private String contactno;
	
	@Id
	@Column(nullable = false, length = 100)
	private String email;
	
	@Column(nullable = false, length = 60)
	private String password;
	
	@Column(nullable = false, length = 500)
	private String address;
	
	@Column(nullable = false, length = 1000)
	private String profilepic;
	
	@Column(nullable = false, length = 100)
	private String regdate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
}
