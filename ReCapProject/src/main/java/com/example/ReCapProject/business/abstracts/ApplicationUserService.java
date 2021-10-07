package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.abstracts.ApplicationUser;

public interface ApplicationUserService {

	Result existsByEmail(String email);
	
	DataResult<ApplicationUser> getById(int userId);
	DataResult<ApplicationUser> getByEmail(String email);
	
	DataResult<List<ApplicationUser>> getAll();
}
