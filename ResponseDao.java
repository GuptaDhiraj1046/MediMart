package com.softpro.MediMart.Dao;

import jakarta.validation.constraints.NotEmpty;

public class ResponseDao {
	
	private String name;
	
	private String custid;
	
	private String contactno;
	private String responsetype;
	
	@NotEmpty(message = "subject can not be null")
	private String subject;
	
	@NotEmpty(message = "message can not be null")
	private String message;
	
	private String resdate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getResponsetype() {
		return responsetype;
	}

	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
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

	public String getResdate() {
		return resdate;
	}

	public void setResdate(String resdate) {
		this.resdate = resdate;
	}
	
	
}
