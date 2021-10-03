package com.example.ReCapProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.CreditCardService;
import com.example.ReCapProject.business.abstracts.PaymentService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.services.abstracts.PosService;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CreditCardDao;
import com.example.ReCapProject.dataAccess.abstracts.RentalDao;
import com.example.ReCapProject.entities.concretes.CreditCard;
import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.requests.creditCard.CreateCreditCardRequest;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentRequest;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentWithSavedCreditCardRequest;

@Service
public class PaymentManager implements PaymentService {

	private CreditCardDao creditCardDao;
	private RentalDao rentalDao;
	
	private PosService posService;
	private CreditCardService creditCardService;
	
	@Autowired
	public PaymentManager(CreditCardDao creditCardDao, RentalDao rentalDao, PosService posService, CreditCardService creditCardService) {
		
		this.creditCardDao = creditCardDao;
		this.rentalDao = rentalDao;
		
		this.posService = posService;
		this.creditCardService = creditCardService;
	}

	@Override
	public Result add(CreatePaymentRequest entity) {
		
		CreateCreditCardRequest creditCard = new CreateCreditCardRequest();
		creditCard.setCardBeholderName(entity.getCardBeholderName());
		creditCard.setCardNumber(entity.getCardNumber());
		creditCard.setCvvCode(entity.getCvvCode());
		creditCard.setExpireDate(entity.getExpireDate());
		creditCard.setSaveCard(entity.isSave());
		creditCard.setUserId(entity.getUserId());
		
		if (!creditCardService.add(creditCard).isSuccess())
			return new ErrorResult(Messages.CREDIT_CARD_IS_INVALID);
		
		CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
		paymentRequest.setCardBeholderName(creditCard.getCardBeholderName());
		paymentRequest.setCardNumber(creditCard.getCardNumber());
		paymentRequest.setCvvCode(creditCard.getCvvCode());
		paymentRequest.setExpireDate(creditCard.getExpireDate());
		paymentRequest.setRentalId(entity.getRentalId());
		
		if(!this.posService.withdraw(paymentRequest).isSuccess()) {
			this.rentalDao.deleteById(entity.getRentalId());
			return new ErrorResult(Messages.ERROR_MESSAGE);
		}
		
		return new SuccessResult(Messages.PAYMENT_SUCCESSFUL);	
	}
	

	@Override
	public Result addWithSavedCreditCard(CreatePaymentWithSavedCreditCardRequest entity) {
		
		CreditCard creditCard = this.creditCardDao.getById(entity.getCreditCardId());
		
		Rental rental = this.rentalDao.getById(entity.getRentalId());
		
		var result = BusinessRules.run(checkUserForCreditCard(rental, creditCard));
		
		if(result != null)
			return result;
		
		CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
		paymentRequest.setCardBeholderName(creditCard.getCardBeholderName());
		paymentRequest.setCardNumber(creditCard.getCreditCardNumber());
		paymentRequest.setCvvCode(creditCard.getCvvCode());
		paymentRequest.setExpireDate(creditCard.getExpireDate());
		paymentRequest.setRentalId(entity.getRentalId());
		
		if(!this.posService.withdraw(paymentRequest).isSuccess()) {
			this.rentalDao.deleteById(rental.getRentalId()); // If payment is not done; delete the rental object from the database!
			return new ErrorResult(Messages.ERROR_MESSAGE);
		}
		
		return new SuccessResult(Messages.PAYMENT_SUCCESSFUL);
	}

	
	private Result checkUserForCreditCard(Rental rental, CreditCard creditCard) {
		if(!rental.getUser().equals(creditCard.getUser()))
			return new ErrorResult(Messages.CREDIT_CARD_IS_INVALID);
		
		return new SuccessResult();
	}
}
