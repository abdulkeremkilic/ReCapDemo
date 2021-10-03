package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.IndividualCustomer;
import com.example.ReCapProject.entities.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.example.ReCapProject.entities.requests.individualCustomer.UpdateIndividualCustomerRequest;

public interface IndividualCustomerService {
	
	Result add(CreateIndividualCustomerRequest entity);
	Result update(UpdateIndividualCustomerRequest entity);
	Result delete(DeleteIndividualCustomerRequest entity);
	
	DataResult<List<IndividualCustomer>> getAll();
}
