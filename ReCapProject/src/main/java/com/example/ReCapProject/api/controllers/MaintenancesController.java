package com.example.ReCapProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReCapProject.business.abstracts.MaintenanceService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Maintenance;
import com.example.ReCapProject.entities.requests.maintenance.CreateMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.DeleteMaintenanceRequest;
import com.example.ReCapProject.entities.requests.maintenance.UpdateMaintenanceRequest;

@RestController
@RequestMapping("/api/maintenances")
@CrossOrigin
public class MaintenancesController {
	
	private MaintenanceService maintenanceService;
	
	@Autowired
	public MaintenancesController(MaintenanceService maintenanceService) {
		this.maintenanceService = maintenanceService;
	}
	

	@PostMapping("/add")
	public Result add(@Valid CreateMaintenanceRequest maintenance) {
		return this.maintenanceService.add(maintenance);
	}
	

	@PostMapping("/update")
	public Result update(@Valid UpdateMaintenanceRequest maintenance) {
		return this.maintenanceService.update(maintenance);
	}
	
	
	@DeleteMapping("/delete")
	public Result delete(@Valid DeleteMaintenanceRequest maintenanceId) {
		return this.maintenanceService.delete(maintenanceId);
	}
	
	
	@GetMapping("/getbycarid")
	public DataResult<List<Maintenance>> getByCarId(int carId) {
		return this.maintenanceService.getByCarId(carId);
	}
	
	
	@GetMapping("/getall")
	public DataResult<List<Maintenance>> getAll() {
		return this.maintenanceService.getAll();
	}
	
}
