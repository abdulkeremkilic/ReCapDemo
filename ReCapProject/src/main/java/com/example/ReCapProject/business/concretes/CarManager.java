package com.example.ReCapProject.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.BrandService;
import com.example.ReCapProject.business.abstracts.CarImageService;
import com.example.ReCapProject.business.abstracts.CarService;
import com.example.ReCapProject.business.abstracts.CityService;
import com.example.ReCapProject.business.abstracts.ColorService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.business.constants.paths.Paths;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CarDao;
import com.example.ReCapProject.dataAccess.abstracts.CarImageDao;
import com.example.ReCapProject.entities.concretes.Brand;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.CarImage;
import com.example.ReCapProject.entities.concretes.City;
import com.example.ReCapProject.entities.concretes.Color;
import com.example.ReCapProject.entities.dtos.CarDetailDto;
import com.example.ReCapProject.entities.requests.car.CreateCarRequest;
import com.example.ReCapProject.entities.requests.car.DeleteCarRequest;
import com.example.ReCapProject.entities.requests.car.UpdateCarRequest;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private CarImageDao carImageDao;
	
	private CityService cityService;
	private ColorService colorService;
	private BrandService brandService;
	private CarImageService carImageService;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public CarManager(CarDao carDao, CarImageDao carImageDao, CityService cityService, BrandService brandService, 
			ColorService colorService, CarImageService carImageService, ModelMapper modelMapper) {
		
		this.carDao = carDao;
		this.carImageDao = carImageDao;
		
		this.cityService = cityService;
		this.brandService = brandService;
		this.colorService = colorService;
		this.carImageService = carImageService;
		
		this.modelMapper = modelMapper;
	}
	

	@Override
	public Result add(CreateCarRequest entity) {
		
		Color color = this.colorService.getById(entity.getColorId()).getData();
		
		Brand brand = this.brandService.getById(entity.getBrandId()).getData();
		
		City city = this.cityService.getById(entity.getCityId()).getData();
		
		Car car = new Car();
		car.setBrand(brand);
		car.setColor(color);
		car.setCity(city);
		car.setDailyPrice(entity.getDailyPrice());
		car.setCurrentKilometer(entity.getKilometer());
		car.setMinFindexPoint(entity.getMinFindexPoint());
		car.setDescription(entity.getDescription().toLowerCase().trim());
		
		// Default image
		if(this.carImageDao.getByCar_CarId(car.getCarId()).isEmpty()) {
			CarImage carImage = new CarImage();
			carImage.setImagePath(Paths.CAR_IMAGE_DEFAULT_PATH);
			carImage.setCar(car);
			carImageDao.save(carImage);
		}
		
		this.carDao.save(car);
		return new SuccessResult(Messages.CAR_ADDED);
	}
	

	@Override
	public Result update(UpdateCarRequest entity) {
		
		City city = this.cityService.getById(entity.getCityId()).getData();
		
		Car car = this.carDao.getById(entity.getCarId());
		car.setCity(city);
		car.setAvailable(entity.isAvailable());
		car.setDailyPrice(entity.getDailyPrice());
		car.setCurrentKilometer(entity.getKilometer());
		car.setDescription(entity.getDescription().toLowerCase().trim());
		
		this.carDao.save(car);
		return new SuccessResult(Messages.CAR_UPDATED);
	}
	

	@Override
	public Result delete(DeleteCarRequest entity) {
		this.carDao.deleteById(entity.getCarId());
		return new SuccessResult(Messages.CAR_DELETED);
	}

	
	@Override
	public DataResult<Car> getById(int carId) {
		return new SuccessDataResult<Car>(this.carDao.getById(carId), Messages.CAR_LISTED);
	}
	

	@Override
	public DataResult<List<Car>> getAll() {
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(), Messages.CARS_LISTED);
	}
	
	
	@Override
	public DataResult<List<CarDetailDto>> getCarsDetail() {
		
		List<Car> cars = this.carDao.findAll();
		
		List<CarDetailDto> carDtos = new ArrayList<CarDetailDto>();
		
		for(Car car : cars) {
			
			CarDetailDto carDto = this.convertToDto(car);
			carDto.setCarrImageDtos(this.carImageService.getImageDetailsByCarId(car.getCarId()).getData());
			
			carDtos.add(carDto);
		}
		
		return new SuccessDataResult<List<CarDetailDto>>(carDtos, Messages.CAR_DETAILS_LISTED);
	}
	
	

	@Override
	public DataResult<List<Car>> getCarByBrandName(String brandName) {
		
		return new SuccessDataResult<List<Car>>(this.carDao.getByBrand_BrandName(brandName), Messages.CARS_LISTED);
	}
	
	
	@Override
	public DataResult<List<Car>> getCarByColorName(String colorName) {
		
		return new SuccessDataResult<List<Car>>(this.carDao.getByColor_ColorName(colorName), Messages.CARS_LISTED);
	}
	

	@Override
	public DataResult<List<Car>> getByCityName(String cityName) {
		
		return new SuccessDataResult<List<Car>>(this.carDao.getByCity_CityName(cityName), Messages.CARS_LISTED);
	}
	
	

	
	
	private CarDetailDto convertToDto (Car car) {
		
		CarDetailDto carDto = modelMapper.map(car, CarDetailDto.class);
		return carDto;
	}
	
}
