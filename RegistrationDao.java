package com.softpro.MediMart.Dao;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class RegistrationDao {
	
	@NotEmpty(message = "* name can not be null")
	private String name;
	
	@NotEmpty(message = "* please select gender")
	private String gender;
	
	@NotEmpty(message = "* date of birth can not be null")
	private String dob;
	 
	@NotEmpty(message = "* contact can not be null")
	private String contactno;
	
	@NotEmpty(message = "* email can not be null")
	private String email;
	
	@NotEmpty(message = "* password can not be null")
	private String password;
	
	@NotEmpty(message = "* address can not be null")
	private String address;
	
	private MultipartFile profilepic;
	
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

	public MultipartFile getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(MultipartFile profilepic) {
		this.profilepic = profilepic;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
	

}
