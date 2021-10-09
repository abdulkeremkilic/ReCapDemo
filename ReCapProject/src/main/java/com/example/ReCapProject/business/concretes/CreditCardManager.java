package com.example.ReCapProject.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.ApplicationUserService;
import com.example.ReCapProject.business.abstracts.CreditCardService;
import com.example.ReCapProject.business.constants.messages.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorDataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.CreditCardDao;
import com.example.ReCapProject.entities.abstracts.ApplicationUser;
import com.example.ReCapProject.entities.concretes.CreditCard;
import com.example.ReCapProject.entities.dtos.CreditCardDetailDto;
import com.example.ReCapProject.entities.requests.creditCard.CreateCreditCardRequest;
import com.example.ReCapProject.entities.requests.creditCard.DeleteCreditCardRequest;
import com.example.ReCapProject.entities.requests.creditCard.UpdateCreditCardRequest;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	
	private ApplicationUserService applicationUserService;
	
	
	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ApplicationUserService applicationUserService) {
		
		this.creditCardDao = creditCardDao;
		
		this.applicationUserService = applicationUserService;
	}
	

	@Override
	public Result add(CreateCreditCardRequest entity) {
		
		var result = BusinessRules.run(checkCreditCardNumber(entity.getCardNumber()), 
				checkCreditCardCvv(entity.getCvvCode()), 
				checkCreditCardExpiryDate(entity.getExpireDate()));
		
		if(result != null)
			return result;
		
		ApplicationUser user = this.applicationUserService.getById(entity.getUserId()).getData();
		
		CreditCard creditCard = new CreditCard();
		creditCard.setUser(user);
		creditCard.setCvvCode(entity.getCvvCode());
		creditCard.setExpireDate(entity.getExpireDate());
		creditCard.setCreditCardNumber(entity.getCardNumber());
		creditCard.setCardBeholderName(entity.getCardBeholderName().toLowerCase().trim());
		
		if(entity.isSaveCard()) {
			if(checkIfCardAlreadyExists(entity.getCardNumber(), creditCard).isSuccess()) {
				return new SuccessResult(Messages.CREDIT_CARD_ALREADY_BEEN_SAVED);
			}
			this.creditCardDao.save(creditCard);
		}
		
		return new SuccessResult(Messages.CREDIT_CARD_ADDED);
	}
	

	@Override
	public Result update(UpdateCreditCardRequest entity) {
		
		var result = BusinessRules.run(checkCreditCardNumber(entity.getCardNumber()), 
				checkCreditCardCvv(entity.getCvvCode()), 
				checkCreditCardExpiryDate(entity.getExpireDate()));
		
		if(result != null)
			return result;
		
		CreditCard creditCard = this.creditCardDao.getById(entity.getCardId());
		creditCard.setCvvCode(entity.getCvvCode());
		creditCard.setExpireDate(entity.getExpireDate());
		creditCard.setCreditCardNumber(entity.getCardNumber());
		
		this.creditCardDao.save(creditCard);
		return new SuccessResult(Messages.CREDIT_CARD_UPDATED);
		
	}
	

	@Override
	public Result delete(DeleteCreditCardRequest entity) {
		
		this.creditCardDao.deleteById(entity.getCardId());
		return new SuccessResult(Messages.CREDIT_CARD_DELETED);
	}
	
	
	@Override
	public DataResult<CreditCard> getById(int creditCardId) {
		
		return new SuccessDataResult<>(this.creditCardDao.getById(creditCardId));
	}
	

	@Override
	public DataResult<List<CreditCard>> getCreditCardsForUser(int userId) {
		
		if(!this.applicationUserService.getById(userId).getData().getCreditCards().isEmpty())
			return new SuccessDataResult<>(this.applicationUserService.getById(userId).getData().getCreditCards(), Messages.CREDIT_CARD_LISTED);
		
		return new ErrorDataResult<>();
	}
		
		
	@Override
	public DataResult<List<CreditCardDetailDto>> getCreditCardDetails(int userId) {
		
		List<CreditCard> creditCards = this.creditCardDao.getByUser_UserId(userId);
		
		List<CreditCardDetailDto> creditCardDtos = new ArrayList<>();
		
		for (CreditCard creditCard : creditCards) {
			
			CreditCardDetailDto creditCardDto = new CreditCardDetailDto();
			creditCardDto.setUserId(creditCard.getUser().getUserId());
			creditCardDto.setCardBeholderName(creditCard.getCardBeholderName());
			creditCardDto.setCreditCardNumber(creditCard.getCreditCardNumber());
			creditCardDto.setCvvCode(creditCard.getCvvCode());
			creditCardDto.setExpireDate(creditCard.getExpireDate());
			
			creditCardDtos.add(creditCardDto);
		}
		
		return new SuccessDataResult<>(creditCardDtos, Messages.CREDIT_CARD_LISTED);
	}	
	
	
	// Card Validation rules!
	private Result checkCreditCardNumber(String creditCardNumber) {

		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + 
		               "(?<mastercard>5[1-5][0-9]{14})|" +
				       "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +  
		               "(?<amex>3[47][0-9]{13})|" +
				       "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" + 
		               "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(creditCardNumber);

		if (!matcher.matches()) 
			return new ErrorResult(Messages.CREDIT_CARD_IS_INVALID);
		
		return new SuccessResult();
	}
	
	
	private Result checkCreditCardCvv(String cvv) {

		String regex = "^[0-9]{3,3}$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cvv);

		if (!matcher.matches()) 
			return new ErrorResult(Messages.CREDIT_CARD_IS_INVALID);
		
		return new SuccessResult();
	}
	
	
	private Result checkCreditCardExpiryDate(String expiryDate) {

		String regex = "^(0[1-9]|1[0-2])/?(([0-9]{4}|[0-9]{2})$)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(expiryDate);

		if (!matcher.matches()) 
			return new ErrorResult(Messages.CREDIT_CARD_IS_INVALID);
		
		return new SuccessResult();
	}
	
	
	private Result checkIfCardAlreadyExists(String creditCardNumber, CreditCard creditCard) {
		
		if(this.creditCardDao.getByCreditCardNumber(creditCardNumber) != null 
				&& this.creditCardDao.getByCreditCardNumber(creditCardNumber).getUser() == creditCard.getUser()) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

}
