package com.example.ReCapProject.core.services;

import com.example.ReCapProject.business.constants.Messages;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.SuccessDataResult;
import com.example.ReCapProject.entities.concretes.Receipt;
import com.example.ReCapProject.entities.requests.creditCard.CreditCardInfoRequest;
import com.example.ReCapProject.entities.requests.rental.RentalInfoRequest;

// OutService
public class CreateReceiptService {

	public DataResult<Receipt> createReceipt(CreditCardInfoRequest creditCard, RentalInfoRequest rental) {
		
		Receipt receipt = new Receipt();
		receipt.setCardBeholderName(creditCard.getCardBeholderName());
		receipt.setCreditCardNumber("*************" + creditCard.getCardNumber().substring(12));
		receipt.setBrandName(rental.getBrandName());
		receipt.setModelName(rental.getModelName());
		receipt.setModelYear(rental.getModelYear());
		receipt.setReceiptDate(rental.getRentDate());
		receipt.setRecievedByCustomerKm(rental.getReceivedByCustomerKm());
		
		return new SuccessDataResult<Receipt>(receipt, Messages.RECEIPT_CREATED);
		
	}
}
