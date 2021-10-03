package com.example.ReCapProject.business.abstracts;

import com.example.ReCapProject.core.utilities.results.DataResult;

public interface FindexPointService {

	DataResult<Double> evaluateFindexPointForCorporateCustomer(String taxNumber);	
	DataResult<Double> evaluateFindexPointForIndividualCustomer(String nationalId);
	
}
