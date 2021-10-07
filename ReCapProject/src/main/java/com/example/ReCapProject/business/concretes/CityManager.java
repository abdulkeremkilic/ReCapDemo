package com.example.ReCapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.example.ReCapProject.entities.dtos.CityDetailDto;
import com.example.ReCapProject.entities.requests.city.CreateCityRequest;
import com.example.ReCapProject.entities.requests.city.DeleteCityRequest;
import com.example.ReCapProject.entities.requests.city.UpdateCityRequest;

@Service
public class CityManager implements CityService {

	private CityDao cityDao;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public CityManager(CityDao cityDao, ModelMapper modelMapper) {
		
		this.cityDao = cityDao;
	
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateCityRequest entity) {
		
		var result = BusinessRules.run(checkIfCityExists(entity.getCityName()));
		
		if(result != null)
			return result;
		
		City city = new City();
		city.setCityName(entity.getCityName().toLowerCase().trim());
		
		this.cityDao.save(city);
		return new SuccessResult(Messages.CITY_ADDED);
	}
	

	@Override
	public Result update(UpdateCityRequest entity) {
		
		var result = BusinessRules.run(checkIfCityExists(entity.getCityName()));
		
		if(result != null)
			return result;
		
		City city = this.cityDao.getById(entity.getCityId());
		city.setCityName(entity.getCityName().toLowerCase().trim());
		
		this.cityDao.save(city);
		return new SuccessResult(Messages.CITY_UPDATED);
	}
	

	@Override
	public Result delete(DeleteCityRequest entity) {
		
		this.cityDao.deleteById(entity.getCityId());
		return new SuccessResult(Messages.CITY_DELETED);
	}
	
	
	@Override
	public DataResult<City> getById(int cityId) {
		
		return new SuccessDataResult<City>(this.cityDao.getById(cityId));
	}
	

	@Override
	public DataResult<List<City>> getAll() {
		
		return new SuccessDataResult<List<City>>(this.cityDao.findAll(), Messages.CITIES_LISTED);
	}
	
	
	@Override
	public DataResult<List<CityDetailDto>> getAllDetails() {
		
		List<City> cities = this.cityDao.findAll();
		
		List<CityDetailDto> cityDtos = cities.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CityDetailDto>>(cityDtos, Messages.CITIES_LISTED);
	}
	
	
	private Result checkIfCityExists(String cityName) {
		
		if(this.cityDao.existsByCityName(cityName.toLowerCase().trim()))
			return new ErrorResult(Messages.CITY_ALREADY_EXISTS);
		
		return new SuccessResult();
	}
	
	
	private CityDetailDto convertToDto(City city) {
		
		CityDetailDto cityDto = modelMapper.map(city, CityDetailDto.class);
		return cityDto;
	}

}
