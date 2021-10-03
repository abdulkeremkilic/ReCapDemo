package com.example.ReCapProject.business.concretes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReCapProject.business.abstracts.CreditCardService;
import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.business.BusinessRules;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.ErrorDataResult;
import com.example.ReCapProject.core.utilities.results.ErrorResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.core.utilities.results.SuccessResult;
import com.example.ReCapProject.dataAccess.abstracts.ApplicationUserDao;
import com.example.ReCapProject.dataAccess.abstracts.CreditCardDao;
import com.example.ReCapProject.entities.abstracts.ApplicationUser;
import com.example.ReCapProject.entities.concretes.CreditCard;
import com.example.ReCapProject.entities.dtos.CreditCardDto;
import com.example.ReCapProject.entities.requests.creditCard.CreateCreditCardRequest;
import com.example.ReCapProject.entities.requests.creditCard.DeleteCreditCardRequest;
import com.example.ReCapProject.entities.requests.creditCard.UpdateCreditCardRequest;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private ApplicationUserDao applicationUserDao;
	
	
	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ApplicationUserDao applicationUserDao) {
		
		this.creditCardDao = creditCardDao;
		this.applicationUserDao = applicationUserDao;
	}
	

	@Override
	public Result add(CreateCreditCardRequest entity) {
		
		var result = BusinessRules.run(checkCreditCardNumber(entity.getCardNumber()), 
				checkCreditCardCvv(entity.getCvvCode()), 
				checkCreditCardExpiryDate(entity.getExpireDate()));
		
		if(result != null)
			return result;
		
		ApplicationUser user = this.applicationUserDao.getById(entity.getUserId());
		
		CreditCard creditCard = new CreditCard();
		creditCard.setCreditCardNumber(entity.getCardNumber());
		creditCard.setCvvCode(entity.getCvvCode());
		creditCard.setExpireDate(entity.getExpireDate());
		creditCard.setCardBeholderName(entity.getCardBeholderName());
		creditCard.setUser(user);
		
		if(entity.isSaveCard()) 
			this.creditCardDao.save(creditCard);
		
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
		creditCard.setCardName(entity.getCardName());
		creditCard.setCreditCardNumber(entity.getCardNumber());
		creditCard.setCvvCode(entity.getCvvCode());
		creditCard.setExpireDate(entity.getExpireDate());
		
		this.creditCardDao.save(creditCard);
		return new SuccessResult(Messages.CREDIT_CARD_UPDATED);
		
	}
	

	@Override
	public Result delete(DeleteCreditCardRequest entity) {
		
		this.creditCardDao.deleteById(entity.getCardId());
		return new SuccessResult(Messages.CREDIT_CARD_DELETED);
	}
	

	@Override
	public DataResult<List<CreditCard>> getCreditCardsForUser(int userId) {
		
		if(!this.applicationUserDao.getById(userId).getCreditCards().isEmpty())
			return new SuccessDataResult<List<CreditCard>>(this.applicationUserDao.getById(userId).getCreditCards(), Messages.CREDIT_CARD_LISTED);
		
		return new ErrorDataResult<List<CreditCard>>();
	}
		
		
	@Override
	public DataResult<List<CreditCardDto>> getCreditCardDetails(int userId) {
		
		return new SuccessDataResult<List<CreditCardDto>>(this.creditCardDao.getCreditCardDetails(userId));
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

}
