package com.example.ReCapProject.business.abstracts;

import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.requests.userLogin.UserLogInRequest;

public interface LogInService {

	Result logIn(UserLogInRequest logInRequest);
	
}
