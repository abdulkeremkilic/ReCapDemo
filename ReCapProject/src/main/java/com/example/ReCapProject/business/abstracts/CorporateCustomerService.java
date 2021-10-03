package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.CorporateCustomer;
import com.example.ReCapProject.entities.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.example.ReCapProject.entities.requests.corporateCustomer.UpdateCorporateCustomerRequest;

public interface CorporateCustomerService {

	Result add(CreateCorporateCustomerRequest entity);
	Result update(UpdateCorporateCustomerRequest entity);
	Result delete(DeleteCorporateCustomerRequest entity);
	
	DataResult<List<CorporateCustomer>> getAll();
	
}
