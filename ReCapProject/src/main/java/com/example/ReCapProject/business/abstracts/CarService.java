package com.example.ReCapProject.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.dtos.CarDetailDto;
import com.example.ReCapProject.entities.requests.car.CreateCarRequest;
import com.example.ReCapProject.entities.requests.car.DeleteCarRequest;
import com.example.ReCapProject.entities.requests.car.UpdateCarRequest;

@Repository
public interface CarService  {

	Result add(CreateCarRequest entity);
	Result update(UpdateCarRequest entity);
	Result delete(DeleteCarRequest entity);
	
	DataResult<Car> getById(int carId);
	
	DataResult<List<Car>> getAll();
	DataResult<List<Car>> getCarByBrandName(String brandName);
	DataResult<List<Car>> getCarByColorName(String colorName);
	DataResult<List<Car>> getByCityName(String cityName);
	
	DataResult<List<CarDetailDto>> getCarsDetail();
	
	
}
