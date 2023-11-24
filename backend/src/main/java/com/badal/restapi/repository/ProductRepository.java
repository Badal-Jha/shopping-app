package com.badal.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.badal.restapi.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

	public Product findByProductCode(String productCode);
	public List<Product> findByProductNameContainingIgnoreCase(String productName);
	    
	public List<Product> findByProductBrandContainingIgnoreCase(String productBrand);
	    
	   
}
