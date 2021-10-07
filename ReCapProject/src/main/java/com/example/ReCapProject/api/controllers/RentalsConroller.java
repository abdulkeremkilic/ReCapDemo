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

import com.example.ReCapProject.business.abstracts.RentalService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.dtos.RentalDetailDto;
import com.example.ReCapProject.entities.requests.rental.CreateRentalRequest;
import com.example.ReCapProject.entities.requests.rental.DeleteRentalRequest;
import com.example.ReCapProject.entities.requests.rental.UpdateRentalRequest;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalsConroller {

	private RentalService rentalService;

	@Autowired
	public RentalsConroller(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@PostMapping("/addforcorporate")
	public Result addForCorporate(@Valid CreateRentalRequest rental) {
		return this.rentalService.addForCorporate(rental);
	}
	
	@PostMapping("/addforindividual")
	public Result addForIndividual(@Valid CreateRentalRequest rental) {
		return this.rentalService.addForIndividual(rental);
	}
	
	@PostMapping("/update")
	public Result update(@Valid UpdateRentalRequest rental) {
		return this.rentalService.update(rental);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@Valid DeleteRentalRequest rentalId) {
		return this.rentalService.delete(rentalId);
	}
	
	@GetMapping("/getall") 
	public DataResult<List<Rental>> getAll() {
		return this.rentalService.getAll();
	}
	
	@GetMapping("/getalldetails")
	public DataResult<List<RentalDetailDto>> getAllDetails() {
		return this.rentalService.getRentalDetails();
	}
	
	@GetMapping("/getdetailbyid")
	public DataResult<RentalDetailDto> getDetailById(int rentalId) {
		return this.rentalService.getRentalDetailById(rentalId);
	}
	
	@GetMapping("/getopenrentals")
	public DataResult<List<RentalDetailDto>> getOpenRentals() {
		return this.rentalService.getOpenRentals();
	}
	
	@GetMapping("/getclosedrentals")
	public DataResult<List<RentalDetailDto>> getClosedRentals() {
		return this.rentalService.getClosedRentals();
	}
	
}
