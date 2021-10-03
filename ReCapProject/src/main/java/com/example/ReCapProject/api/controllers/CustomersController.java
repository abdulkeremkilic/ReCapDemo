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

import com.example.ReCapProject.business.abstracts.CorporateCustomerService;
import com.example.ReCapProject.business.abstracts.IndividualCustomerService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.CorporateCustomer;
import com.example.ReCapProject.entities.concretes.IndividualCustomer;
import com.example.ReCapProject.entities.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.UpdateIndividualCustomerRequest;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomersController {

	private CorporateCustomerService corporateCustomerService;
	private IndividualCustomerService individualCustomerService;

	@Autowired
	public CustomersController(CorporateCustomerService corporateCustomerService, IndividualCustomerService individualCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
		this.individualCustomerService = individualCustomerService;
	}
	
	
	
	@PostMapping("/addindividualcustomer")
	public Result add(@Valid CreateIndividualCustomerRequest customer) {
		return this.individualCustomerService.add(customer);
	}
	
	@PostMapping("/updateindividualcustomer")
	public Result update(@Valid UpdateIndividualCustomerRequest customer) {
		return this.individualCustomerService.update(customer);
	}
	
	@DeleteMapping("/deleteindividualtecustomer")
	public Result deleteIndividual(@Valid DeleteIndividualCustomerRequest individualCustomerId){
		return this.individualCustomerService.delete(individualCustomerId);
	}
	
	
	
	@PostMapping("/addcorporatecustomer")
	public Result add(@Valid CreateCorporateCustomerRequest customer) {
		return this.corporateCustomerService.add(customer);
	}
	
	@PostMapping("/updatecorporatecustomer")
	public Result update(@Valid UpdateCorporateCustomerRequest customer) {
		return this.corporateCustomerService.update(customer);
	}
	
	@DeleteMapping("/deletecorporatecustomer")
	public Result deleteCorporate(@Valid DeleteCorporateCustomerRequest corporateCustomerId){
		return this.corporateCustomerService.delete(corporateCustomerId);
	}
	
	

	@GetMapping("/getallcorporatecustomers")
	public DataResult<List<CorporateCustomer>> getCorporateCustomers(){
		return this.corporateCustomerService.getAll();
	}
	
	@GetMapping("/getallindividualcustomers")
	public DataResult<List<IndividualCustomer>> getIndividualCustomers(){
		return this.individualCustomerService.getAll();
	}
	
}
