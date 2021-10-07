package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Maintenance;
import com.example.ReCapProject.entities.dtos.MaintenanceDetailDto;
import com.example.ReCapProject.entities.requests.maintenance.CreateMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.DeleteMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.UpdateMaintenanceRequest;

public interface MaintenanceService {

	Result add(CreateMaintenanceRequest entity);
	Result update(UpdateMaintenanceRequest entity);
	Result delete(DeleteMaintenanceRequest entity);
	
	DataResult<List<Maintenance>> getAll();
	DataResult<List<Maintenance>> getByCarId(int carId);
	
	DataResult<List<MaintenanceDetailDto>> getAllDetails();
}
