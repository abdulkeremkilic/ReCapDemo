package com.example.ReCapProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.ApplicationUserService;
import com.example.ReCapProject.business.abstracts.LogInService;
import com.example.ReCapProject.business.constants.messages.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.entities.requests.userLogin.UserLogInRequest;

@Service
public class LogInManager implements LogInService{

	private ApplicationUserService applicationUserService;
	
	@Autowired
	public LogInManager(ApplicationUserService applicationUserService) {
		this.applicationUserService = applicationUserService;
	}


	@Override
	public Result logIn(UserLogInRequest logInRequest) {
		
		var result = BusinessRules.run(isUserExists(logInRequest.getEmail()));
		
		if(result != null)
			return result;
		
		if(applicationUserService.getByEmail(logInRequest.getEmail()).getData().getPassword().equals(logInRequest.getPassword()))
			return new SuccessResult(Messages.SUCCESSFULLY_LOGGED_IN);
		
		return new ErrorResult(Messages.USER_INFO_INVALID);
		
	}
	
	public Result isUserExists(String email) {
		if(applicationUserService.existsByEmail(email).isSuccess())
			return new SuccessResult();
		
		return new ErrorResult(Messages.USER_DOES_NOT_EXIST);
	}

}
