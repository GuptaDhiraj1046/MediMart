package com.softpro.MediMart.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softpro.MediMart.model.Response;

public interface ResponseRepository extends JpaRepository<Response, Integer>{

	@Query("SELECT r FROM Response r where r.responsetype = :responsetype")
	List<Response> findResponseByResponsesType(@Param("responsetype") String string);

}
