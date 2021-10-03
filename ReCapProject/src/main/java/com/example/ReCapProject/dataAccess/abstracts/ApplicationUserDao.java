package com.example.ReCapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ReCapProject.entities.abstracts.ApplicationUser;

@Repository
public interface ApplicationUserDao extends JpaRepository<ApplicationUser, Integer> {

	boolean existsByEmail(String email);
	
	ApplicationUser getByEmail(String email);
}
