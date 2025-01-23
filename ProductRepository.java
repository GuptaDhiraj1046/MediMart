package com.softpro.MediMart.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softpro.MediMart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
