package com.softpro.MediMart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "enquries")
public class Enquiry {
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false, length = 13)
	private String contactno;
	
	@Id
	@Column(nullable = false, length = 100)
	private String email;
	
	@Column(nullable = false, length = 200)
	private String subject;
	
	@Column(nullable = false, length = 1000)
	private String message;
	
	@Column(nullable = false, length = 50)
	private String posteddate;

	//Getter Setter Start**************
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
