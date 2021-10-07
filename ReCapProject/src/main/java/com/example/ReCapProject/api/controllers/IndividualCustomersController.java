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

import com.example.ReCapProject.business.abstracts.IndividualCustomerService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.IndividualCustomer;
import com.example.ReCapProject.entities.dtos.IndividualCustomerDto;
import com.example.ReCapProject.entities.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.UpdateIndividualCustomerRequest;

@RestController
@RequestMapping("/api/individualcustomers")
@CrossOrigin
public class IndividualCustomersController {

	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}
	
	
	@PostMapping("/add")
	public Result add(@Valid CreateIndividualCustomerRequest customer) {
		return this.individualCustomerService.add(customer);
	}
	
	
	@PostMapping("/update")
	public Result update(@Valid UpdateIndividualCustomerRequest customer) {
		return this.individualCustomerService.update(customer);
	}
	
	
	@DeleteMapping("/delete")
	public Result deleteIndividual(@Valid DeleteIndividualCustomerRequest individualCustomerId){
		return this.individualCustomerService.delete(individualCustomerId);
	}
	
	
	@GetMapping("/getall")
	public DataResult<List<IndividualCustomer>> getIndividualCustomers(){
		return this.individualCustomerService.getAll();
	}
	
	
	@GetMapping("/getdetails")
	public DataResult<List<IndividualCustomerDto>> getIndividualCustmersDetail() {
		return this.individualCustomerService.getIndividualCustomersDetail();
	}
	
	
	@GetMapping("getbyid")
	public DataResult<IndividualCustomerDto> getIndividualCustomerById(int userId) {
		return this.individualCustomerService.getDetailsById(userId);
	}
	
}
