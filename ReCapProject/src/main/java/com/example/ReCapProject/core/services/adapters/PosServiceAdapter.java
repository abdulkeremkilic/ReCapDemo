package com.example.ReCapProject.core.services.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.core.services.PosOutService;
import com.example.ReCapProject.core.services.abstracts.PosService;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.RentalDao;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentRequest;

@Service
public class PosServiceAdapter implements PosService{

	private RentalDao rentalDao;
	
	private PosOutService posOutService;
	
	@Autowired
	public PosServiceAdapter(RentalDao rentalDao) {
		
		this.posOutService = new PosOutService();
		this.rentalDao = rentalDao;
	}

	@Override
	public Result withdraw(CreatePaymentRequest entity) {
		if(this.posOutService.posService(entity.getCardBeholderName(), entity.getCardNumber(), entity.getCvvCode(), entity.getExpireDate(), rentalDao.getById(entity.getRentalId()).getRentalPrice()))
			return new SuccessResult();
		
		return new ErrorResult();
	}

}
