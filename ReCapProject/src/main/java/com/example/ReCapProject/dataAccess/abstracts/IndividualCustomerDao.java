package com.example.ReCapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.concretes.IndividualCustomer;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {

	boolean existsByEmail(String email);
	boolean existsByNationalIdNumber(String nationalIdNumber);
}
