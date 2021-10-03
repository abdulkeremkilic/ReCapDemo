package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.dtos.RentalDto;
import com.example.ReCapProject.entities.requests.rental.CreateRentalRequest;
import com.example.ReCapProject.entities.requests.rental.DeleteRentalRequest;
import com.example.ReCapProject.entities.requests.rental.UpdateRentalRequest;

public interface RentalService {
	
	Result addForCorporate(CreateRentalRequest entity);
	Result addForIndividual(CreateRentalRequest entity);
	Result update(UpdateRentalRequest entity);
	Result delete(DeleteRentalRequest entity);
	
	DataResult<List<Rental>> getAll();
	DataResult<List<Rental>> getOpenRentals();
	DataResult<List<Rental>> getClosedRentals();
	
	DataResult<List<RentalDto>> getCarAndRentalDetails();
	
}
