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

import com.example.ReCapProject.business.abstracts.AdditionalServiceService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.AdditionalService;
import com.example.ReCapProject.entities.requests.additionalService.CreateAdditionalServiceRequest;
import com.example.ReCapProject.entities.requests.additionalService.DeleteAdditionalServiceRequest;
import com.example.ReCapProject.entities.requests.additionalService.UpdateAdditionalServiceRequest;

@RestController
@RequestMapping("/api/additionalservices")
@CrossOrigin
public class AdditionalServicesController {

	private AdditionalServiceService additionalServiceService;

	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		this.additionalServiceService = additionalServiceService;
	}
	
	
	@PostMapping("/add")
	public Result add(@Valid CreateAdditionalServiceRequest entity) {
		return this.additionalServiceService.add(entity);
	}
	
	
	@PostMapping("/update")
	public Result update(@Valid UpdateAdditionalServiceRequest entity) {
		return this.additionalServiceService.update(entity);
	}
	
	
	@DeleteMapping("delete")
	public Result delete(@Valid DeleteAdditionalServiceRequest entity) {
		return this.additionalServiceService.delete(entity);
	}
	
	
	@GetMapping("/getall")
	public DataResult<List<AdditionalService>> getAll() {
		return this.additionalServiceService.getAll();
	}
	
}
