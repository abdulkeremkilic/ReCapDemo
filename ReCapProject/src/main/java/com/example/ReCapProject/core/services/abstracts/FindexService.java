package com.example.ReCapProject.core.services.abstracts;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.CorporateCustomer;
import com.example.ReCapProject.entities.concretes.IndividualCustomer;

public interface FindexService {

	DataResult<Double> evaluateFindexPointForCorporateCustomer(String taxNumber);	
	DataResult<Double> evaluateFindexPointForIndividualCustomer(String nationalId);
	
	Result checkFindexPointsForCorporate(CorporateCustomer corporateCustomer, Car car);
	Result checkFindexPointsForIndividual(IndividualCustomer individualCustomer, Car car);
	
}
