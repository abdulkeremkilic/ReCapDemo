package com.example.ReCapProject.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReCapProject.business.abstracts.LogInService;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.requests.userLogin.UserLogInRequest;

@RestController
@RequestMapping("/api/logins")
@CrossOrigin
public class LogInController {

	private LogInService logInService;

	@Autowired
	public LogInController(LogInService logInService) {
		this.logInService = logInService;
	}

	@GetMapping("/login")
	public Result login(@Valid UserLogInRequest loginRequest) {
		return this.logInService.logIn(loginRequest);
	}
	
}
