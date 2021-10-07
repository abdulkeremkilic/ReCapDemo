package com.example.ReCapProject.core.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ReCapProject.core.entities.concretes.User;

public interface UserDao extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);
}
