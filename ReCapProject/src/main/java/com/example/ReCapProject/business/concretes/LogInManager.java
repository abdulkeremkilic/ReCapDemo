package com.example.ReCapProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.LogInService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.ApplicationUserDao;
import com.example.ReCapProject.entities.requests.userLogin.UserLogInRequest;

@Service
public class LogInManager implements LogInService{

	private ApplicationUserDao applicationUserDao;
	
	@Autowired
	public LogInManager(ApplicationUserDao applicationUserDao) {
		this.applicationUserDao = applicationUserDao;
	}


	@Override
	public Result logIn(UserLogInRequest logInRequest) {
		
		var result = BusinessRules.run(isUserExists(logInRequest.getEmail()));
		
		if(result != null)
			return result;
		
		if(applicationUserDao.getByEmail(logInRequest.getEmail()).getPassword().equals(logInRequest.getPassword()))
			return new SuccessResult(Messages.SUCCESSFULLY_LOGGED_IN);
		
		return new ErrorResult(Messages.USER_INFO_INVALID);
		
	}
	
	public Result isUserExists(String email) {
		if(applicationUserDao.existsByEmail(email))
			return new SuccessResult();
		
		return new ErrorResult(Messages.USER_DOES_NOT_EXIST);
	}

}
