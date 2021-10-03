package com.example.ReCapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.CorporateCustomer;
import com.example.ReCapProject.entities.dtos.CorporateCustomerDto;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {

	@Query("Select new com.example.ReCapProject.entities.dtos.CorporateCustomerDto"
	  		+ " (c.userId, c.email , c.companyName)"
	  		+ " From CorporateCustomer c")
	List<CorporateCustomerDto> getCustomerDetails();
	
	boolean existsByEmail(String email);
	boolean existsByTaxNumber(String taxNumber);
}
