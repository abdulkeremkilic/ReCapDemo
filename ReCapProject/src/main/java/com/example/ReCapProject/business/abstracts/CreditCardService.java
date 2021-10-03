package com.example.ReCapProject.business.abstracts;

import java.util.List;

import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.CreditCard;
import com.example.ReCapProject.entities.dtos.CreditCardDto;
import com.example.ReCapProject.entities.requests.creditCard.CreateCreditCardRequest;
import com.example.ReCapProject.entities.requests.creditCard.DeleteCreditCardRequest;
import com.example.ReCapProject.entities.requests.creditCard.UpdateCreditCardRequest;

public interface CreditCardService {

	Result add(CreateCreditCardRequest entity);
	Result update(UpdateCreditCardRequest entity);
	Result delete(DeleteCreditCardRequest entity);
	
	DataResult<List<CreditCard>> getCreditCardsForUser(int userId);
	
	DataResult<List<CreditCardDto>> getCreditCardDetails(int userId);
}
