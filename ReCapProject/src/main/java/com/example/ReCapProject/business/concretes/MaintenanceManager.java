package com.example.ReCapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.MaintenanceService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CarDao;
import com.example.ReCapProject.dataAccess.abstracts.MaintenanceDao;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.Maintenance;
import com.example.ReCapProject.entities.requests.maintenance.CreateMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.DeleteMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.UpdateMaintenanceRequest;

@Service
public class MaintenanceManager implements MaintenanceService{

	private MaintenanceDao maintenanceDao;
	private CarDao carDao;
	
	@Autowired
	public MaintenanceManager(MaintenanceDao maintenanceDao, CarDao carDao) {
		this.maintenanceDao = maintenanceDao;
		this.carDao = carDao;
	}

	@Override
	public Result add(CreateMaintenanceRequest entity) {
		
		var result = BusinessRules.run(checkIfCarIsAvailable(entity.getCarId()));
		
		if(result != null)
			return result;
		
		Car car = this.carDao.getById(entity.getCarId());
		car.setAvailable(false);
		
		Maintenance maintenance = new Maintenance();
		maintenance.setCar(car);
		maintenance.setMaintenanceDate(entity.getMaintenanceDate());
		maintenance.setInMaintenance(true);
		
		this.maintenanceDao.save(maintenance);
		return new SuccessResult(Messages.MAINTENANCE_ADDED);
	}

	@Override
	public Result update(UpdateMaintenanceRequest entity) {
		
		Maintenance maintenance = this.maintenanceDao.getById(entity.getMaintenanceId());
		maintenance.setInMaintenance(entity.isInMaintenance());
		
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
	public DataResult<List<Maintenance>> getAll() {
		
		return new SuccessDataResult<List<Maintenance>> (this.maintenanceDao.findAll(), Messages.MAINTENANCES_LISTED);
	}
	
	
	@Override
	public DataResult<List<Maintenance>> getByCarId(int carId) {
		
		return new SuccessDataResult<List<Maintenance>>(this.maintenanceDao.getByCar_CarId(carId), Messages.MAINTENANCES_LISTED);
	}
	
	
	private Result checkIfCarIsAvailable(int carId) {
		if(this.carDao.getById(carId).isAvailable() == false)
			return new ErrorResult(Messages.CAR_IS_NOT_AVAILABLE);
		
		return new SuccessResult();
	}

}
