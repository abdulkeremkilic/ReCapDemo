package com.example.ReCapProject.business.abstracts;

import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentRequest;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentWithSavedCreditCardRequest;

public interface PaymentService {

	Result add(CreatePaymentRequest entity);
	Result addWithSavedCreditCard(CreatePaymentWithSavedCreditCardRequest entity);
}
