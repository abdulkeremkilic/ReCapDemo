package com.example.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.ApplicationUserService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.ApplicationUserDao;
import com.example.ReCapProject.entities.abstracts.ApplicationUser;

@Service
public class ApplicationUserManager implements ApplicationUserService {
	
	private ApplicationUserDao applicationUserDao;
	
	@Autowired
	public ApplicationUserManager(ApplicationUserDao applicationUserDao) {
		this.applicationUserDao = applicationUserDao;
	}
	
	
	@Override
	public Result existsByEmail(String email) {
		
		this.applicationUserDao.existsByEmail(email);
		return new SuccessResult();
	}


	@Override
	public DataResult<ApplicationUser> getById(int userId) {
		
		return new SuccessDataResult<>(this.applicationUserDao.getById(userId));
	}
	

	@Override
	public DataResult<List<ApplicationUser>> getAll() {
		
		return new SuccessDataResult<>(this.applicationUserDao.findAll());
	}

	
	@Override
	public DataResult<ApplicationUser> getByEmail(String email) {
		
		return new SuccessDataResult<>(this.applicationUserDao.getByEmail(email));
	}
	
}
