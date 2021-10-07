package com.example.ReCapProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.CreditCardService;
import com.example.ReCapProject.business.abstracts.PaymentService;
import com.example.ReCapProject.business.abstracts.RentalService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.services.abstracts.PosService;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CarDao;
import com.example.ReCapProject.entities.concretes.Car;
import com.example.ReCapProject.entities.concretes.CreditCard;
import com.example.ReCapProject.entities.concretes.Rental;
import com.example.ReCapProject.entities.requests.creditCard.CreateCreditCardRequest;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentRequest;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentWithSavedCreditCardRequest;

@Service
public class PaymentManager implements PaymentService {

	private CarDao carDao;
	
	private PosService posService;
	private RentalService rentalService;
	private CreditCardService creditCardService;
	
	@Autowired
	public PaymentManager(RentalService rentalService, CarDao carDao, PosService posService, CreditCardService creditCardService) {
		
		this.carDao = carDao;
		
		this.posService = posService;
		this.rentalService = rentalService;
		this.creditCardService = creditCardService;
	}

	@Override
	public Result add(CreatePaymentRequest entity) {
		
		var result = BusinessRules.run(checkIfAlreadyBeenPayed(entity.getRentalId()));
		
		if(result != null)
			return result;
		
		CreateCreditCardRequest creditCard = new CreateCreditCardRequest();
		creditCard.setCardBeholderName(entity.getCardBeholderName().toLowerCase().trim());
		creditCard.setCardNumber(entity.getCardNumber());
		creditCard.setExpireDate(entity.getExpireDate());
		creditCard.setCvvCode(entity.getCvvCode());
		creditCard.setUserId(entity.getUserId());
		creditCard.setSaveCard(entity.isSave());
		
		if (!creditCardService.add(creditCard).isSuccess())
			return new ErrorResult(Messages.CREDIT_CARD_IS_INVALID);
		
		CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
		paymentRequest.setCardBeholderName(creditCard.getCardBeholderName());
		paymentRequest.setCardNumber(creditCard.getCardNumber());
		paymentRequest.setExpireDate(creditCard.getExpireDate());
		paymentRequest.setCvvCode(creditCard.getCvvCode());
		paymentRequest.setRentalId(entity.getRentalId());
		
		if(!this.posService.withdraw(paymentRequest).isSuccess()) {
			this.rentalService.deleteById(entity.getRentalId());
			return new ErrorResult(Messages.ERROR_MESSAGE);
		}
		
		Rental rental = this.rentalService.getById(entity.getRentalId()).getData();
		rental.setPayed(true);
		rental.setReturned(false);
		
		Car car = this.rentalService.getById(entity.getRentalId()).getData().getCar();
		car.setAvailable(false);
		
		this.carDao.save(car);
		
		return new SuccessResult(Messages.PAYMENT_SUCCESSFUL);	
	}
	

	@Override
	public Result addWithSavedCreditCard(CreatePaymentWithSavedCreditCardRequest entity) {
		
		Rental rental = this.rentalService.getById(entity.getRentalId()).getData();
		
		CreditCard creditCard = this.creditCardService.getById(entity.getCreditCardId()).getData();
		
		var result = BusinessRules.run(checkUserForCreditCard(rental, creditCard), checkIfAlreadyBeenPayed(entity.getRentalId()));
		
		if(result != null)
			return result;
		
		CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
		paymentRequest.setCardBeholderName(creditCard.getCardBeholderName());
		paymentRequest.setCardNumber(creditCard.getCreditCardNumber());
		paymentRequest.setExpireDate(creditCard.getExpireDate());
		paymentRequest.setCvvCode(creditCard.getCvvCode());
		paymentRequest.setRentalId(entity.getRentalId());
		
		if(!this.posService.withdraw(paymentRequest).isSuccess()) {
			this.rentalService.deleteById(rental.getRentalId()); // If payment is not done; delete the rental object from the database!
			return new ErrorResult(Messages.ERROR_MESSAGE);
		}
		
		Car car = this.rentalService.getById(entity.getRentalId()).getData().getCar();
		car.setAvailable(false);
		this.carDao.save(car);
		
		return new SuccessResult(Messages.PAYMENT_SUCCESSFUL);
	}

	
	private Result checkUserForCreditCard(Rental rental, CreditCard creditCard) {
		
		if(!rental.getUser().equals(creditCard.getUser()))
			return new ErrorResult(Messages.CREDIT_CARD_IS_INVALID);
		
		return new SuccessResult();
	}
	
	
	private Result checkIfAlreadyBeenPayed(int rentalId) {
		
		if(this.rentalService.getById(rentalId).getData().isPayed())
			return new ErrorResult(Messages.PAYMENT_IS_ALREADY_BEEN_MADE);
		
		return new SuccessResult();
	}
	
}
