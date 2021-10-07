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

import com.example.ReCapProject.business.abstracts.CityService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.City;
import com.example.ReCapProject.entities.requests.city.CreateCityRequest;
import com.example.ReCapProject.entities.requests.city.DeleteCityRequest;
import com.example.ReCapProject.entities.requests.city.UpdateCityRequest;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin
public class CitiesController {

	private CityService cityService;

	@Autowired
	public CitiesController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostMapping("/add")
	public Result add(@Valid CreateCityRequest entity) {
		return this.cityService.add(entity);
	}
	
	@PostMapping("/update")
	public Result update(@Valid UpdateCityRequest entity) {
		return this.cityService.update(entity);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@Valid DeleteCityRequest entity) {
		return this.cityService.delete(entity);
	}
	
	@GetMapping("/getall")
	public DataResult<List<City>> getAll() {
		return this.cityService.getAll();
	}
	
	
	
}
