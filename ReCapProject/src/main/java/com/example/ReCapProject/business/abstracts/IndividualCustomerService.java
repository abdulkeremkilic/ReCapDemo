package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.IndividualCustomer;
import com.example.ReCapProject.entities.dtos.IndividualCustomerDetailDto;
import com.example.ReCapProject.entities.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.UpdateIndividualCustomerRequest;

public interface IndividualCustomerService {
	
	Result add(CreateIndividualCustomerRequest entity);
	Result update(UpdateIndividualCustomerRequest entity);
	Result delete(DeleteIndividualCustomerRequest entity);
	
	DataResult<IndividualCustomer> getById(int userId);
	DataResult<List<IndividualCustomer>> getAll();
	
	DataResult<IndividualCustomerDetailDto> getDetailsById(int userId);
	DataResult<List<IndividualCustomerDetailDto>> getIndividualCustomersDetail();
}
