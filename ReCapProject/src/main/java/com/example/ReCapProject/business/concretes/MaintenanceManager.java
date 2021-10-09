package com.example.ReCapProject.business.concretes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.CarService;
import com.example.ReCapProject.business.abstracts.MaintenanceService;
import com.example.ReCapProject.business.constants.messages.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.MaintenanceDao;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.Maintenance;
import com.example.ReCapProject.entities.dtos.MaintenanceDetailDto;
import com.example.ReCapProject.entities.requests.maintenance.CreateMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.DeleteMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.UpdateMaintenanceRequest;

@Service
public class MaintenanceManager implements MaintenanceService {

	private MaintenanceDao maintenanceDao;
	
	private CarService carService;
	
	private ModelMapper modelMapper;
		
	@Autowired
	public MaintenanceManager(MaintenanceDao maintenanceDao, CarService carService, ModelMapper modelMapper) {
		
		this.maintenanceDao = maintenanceDao;
		
		this.carService = carService;
		
		this.modelMapper = modelMapper;
	}
	

	@Override
	public Result add(CreateMaintenanceRequest entity) {
		
		var result = BusinessRules.run(checkIfCarIsAvailable(entity.getCarId()));
		
		if(result != null)
			return result;
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate maintenanceDate = LocalDate.parse(entity.getMaintenanceDate(), dateFormat);
		
		Car car = this.carService.getById(entity.getCarId()).getData();
		car.setAvailable(false);
		
		Maintenance maintenance = new Maintenance();
		maintenance.setCar(car);
		maintenance.setInMaintenance(true);
		maintenance.setMaintenanceDate(maintenanceDate);
		
		this.maintenanceDao.save(maintenance);
		return new SuccessResult(Messages.MAINTENANCE_ADDED);
	}
	

	@Override
	public Result update(UpdateMaintenanceRequest entity) {
		
		Maintenance maintenance = this.maintenanceDao.getById(entity.getMaintenanceId());
		maintenance.setInMaintenance(!entity.isReturned());
		
		if(!maintenance.isInMaintenance())
			this.maintenanceDao.getById(entity.getMaintenanceId()).getCar().setAvailable(true);
		
		this.maintenanceDao.save(maintenance);
		return new SuccessResult(Messages.MAINTENANCE_UPDATED);
	}
	

	@Override
	public Result delete(DeleteMaintenanceRequest entity) {
		
		this.maintenanceDao.deleteById(entity.getMaintenanceId());
		return new SuccessResult();
	}
	
	
	@Override
	public DataResult<List<Maintenance>> getByCarId(int carId) {
		
		return new SuccessDataResult<>(this.maintenanceDao.getByCar_CarId(carId), Messages.MAINTENANCES_LISTED);
	}
	

	@Override
	public DataResult<List<Maintenance>> getAll() {
		
		return new SuccessDataResult<> (this.maintenanceDao.findAll(), Messages.MAINTENANCES_LISTED);
	}
	
	
	@Override
	public DataResult<List<MaintenanceDetailDto>> getAllDetails() {
		
		List<Maintenance> maintenances = this.maintenanceDao.findAll();
		
		List<MaintenanceDetailDto> maintenanceDtos = maintenances.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return new SuccessDataResult<>(maintenanceDtos, Messages.MAINTENANCES_LISTED);
	}
	
	
	private Result checkIfCarIsAvailable(int carId) {
		if(!this.carService.getById(carId).getData().isAvailable())
			return new ErrorResult(Messages.CAR_IS_NOT_AVAILABLE);
		
		return new SuccessResult();
	}
	
	
	private MaintenanceDetailDto convertToDto(Maintenance maintenance) {
		
		return modelMapper.map(maintenance, MaintenanceDetailDto.class);
	}

}
