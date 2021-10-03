package com.example.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.CityService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CityDao;
import com.example.ReCapProject.entities.concretes.City;
import com.example.ReCapProject.entities.requests.city.CreateCityRequest;
import com.example.ReCapProject.entities.requests.city.DeleteCityRequest;
import com.example.ReCapProject.entities.requests.city.UpdateCityRequest;

@Service
public class CityManager implements CityService {

	private CityDao cityDao;
	
	@Autowired
	public CityManager(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	@Override
	public Result add(CreateCityRequest entity) {
		
		var result = BusinessRules.run(checkIfCityExists(entity.getCityName()));
		
		if(result != null)
			return result;
		
		City city = new City();
		city.setCityName(entity.getCityName());
		
		this.cityDao.save(city);
		return new SuccessResult(Messages.CITY_ADDED);
	}
	

	@Override
	public Result update(UpdateCityRequest entity) {
		
		var result = BusinessRules.run(checkIfCityExists(entity.getCityName()));
		
		if(result != null)
			return result;
		
		City city = this.cityDao.getById(entity.getCityId());
		city.setCityName(entity.getCityName());
		
		this.cityDao.save(city);
		return new SuccessResult(Messages.CITY_UPDATED);
	}
	

	@Override
	public Result delete(DeleteCityRequest entity) {
		this.cityDao.deleteById(entity.getCityId());
		return new SuccessResult(Messages.CITY_DELETED);
	}
	

	@Override
	public DataResult<List<City>> getAll() {
		return new SuccessDataResult<List<City>>(this.cityDao.findAll(), Messages.CITIES_LISTED);
	}
	
	
	private Result checkIfCityExists(String cityName) {
		
		if(this.cityDao.existsByCityName(cityName))
			return new ErrorResult(Messages.CITY_ALREADY_EXISTS);
		
		return new SuccessResult();
	}

}
