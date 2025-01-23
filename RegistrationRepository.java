package com.softpro.MediMart.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softpro.MediMart.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, String>{

}
