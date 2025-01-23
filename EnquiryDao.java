package com.softpro.MediMart.Dao;

import jakarta.validation.constraints.NotEmpty;

public class EnquiryDao {
	
	@NotEmpty(message = "name can not be null")
	private String name;
	
	@NotEmpty(message = "contact number can not be null")
	private String contactno;
	
	@NotEmpty(message = "email can not be null")
	private String email;
	
	@NotEmpty(message = "subject can not be null")
	private String subject;
	
	@NotEmpty(message = "message can not be null")
	private String message;
	
	private String posteddate;
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPosteddate() {
		return posteddate;
	}

	public void setPosteddate(String posteddate) {
		this.posteddate = posteddate;
	}
	
}
