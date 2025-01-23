package com.softpro.MediMart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void SendRegistrationEmail(String mailTo, String name, String pass)
	{
		String subject = "Registration Successfull!! Welcome to MediMart";
		String message = "Hello Dear, "+name+"\nYour Registration is succesfull in MediMart(MARC Lab.) Application. \nAnd now you can login to our website through your creadentials.\nYour User ID : "+mailTo+"\nYour Password : "+pass+"\nThank You\nTeam MARC Lab.";
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailTo);
		msg.setSubject(subject);
		msg.setText(message);
		javaMailSender.send(msg);
	}
}
