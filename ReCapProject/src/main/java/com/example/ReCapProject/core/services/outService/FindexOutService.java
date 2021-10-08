package com.example.ReCapProject.core.services.outService;

import com.example.ReCapProject.business.constants.messages.Messages;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;

// Simulation of a OutService
public class FindexOutService {

	public DataResult<Double> evaluateFindexPointForCorporate(String taxNumber) {
		if(taxNumber.length() == 10) {
			double evaluation = Math.random()*180;
			return new SuccessDataResult<Double>(evaluation);
		}
		return new ErrorDataResult<Double>(Messages.TAX_NUMBER_IS_NOT_VALID);
	}
	
	public DataResult<Double> evaluateFindexPointForIndividual(String nationalId) {
		if(nationalId.length() == 11) {
			double evaluation = Math.random()*180;
			return new SuccessDataResult<Double>(evaluation);
		}
		return new ErrorDataResult<Double>(Messages.NATIONAL_ID_NUMBER_IS_NOT_VALID);
	}
	
}
