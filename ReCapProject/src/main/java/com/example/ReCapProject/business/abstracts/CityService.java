package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.City;
import com.example.ReCapProject.entities.dtos.CityDetailDto;
import com.example.ReCapProject.entities.requests.city.CreateCityRequest;
import com.example.ReCapProject.entities.requests.city.DeleteCityRequest;
import com.example.ReCapProject.entities.requests.city.UpdateCityRequest;

public interface CityService {

	Result add(CreateCityRequest entity);
	Result update(UpdateCityRequest entity);
	Result delete(DeleteCityRequest entity);
	
	DataResult<List<CityDetailDto>> getAllDetails();
	
	DataResult<City> getById(int cityId);
	
	DataResult<List<City>> getAll();
}
