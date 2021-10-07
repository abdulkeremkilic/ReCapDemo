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
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.CorporateCustomer;
import com.example.ReCapProject.entities.dtos.CorporateCustomerDto;
import com.example.ReCapProject.entities.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.UpdateCorporateCustomerRequest;

@RestController
@RequestMapping("/api/corporatecustomers")
@CrossOrigin
public class CorporateCustomersController {

	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}
	
	
	@PostMapping("/add")
	public Result add(@Valid CreateCorporateCustomerRequest customer) {
		return this.corporateCustomerService.add(customer);
	}
	
	
	@PostMapping("/update")
	public Result update(@Valid UpdateCorporateCustomerRequest customer) {
		return this.corporateCustomerService.update(customer);
	}
	
	
	@DeleteMapping("/delete")
	public Result deleteCorporate(@Valid DeleteCorporateCustomerRequest corporateCustomerId){
		return this.corporateCustomerService.delete(corporateCustomerId);
	}
	
	
	@GetMapping("/getall")
	public DataResult<List<CorporateCustomer>> getCorporateCustomers(){
		return this.corporateCustomerService.getAll();
	}
	
	
	@GetMapping("getdetails")
	public DataResult<List<CorporateCustomerDto>> getCorporateCustomersDetail() {
		return this.corporateCustomerService.getAllCorporateCustomerDetails();
	}
	
	
	@GetMapping("getbyid")
	public DataResult<CorporateCustomerDto> getCorporateCustomerById(int userId) {
		return this.corporateCustomerService.getDetailsById(userId);
	}

}
