package com.example.ReCapProject.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReCapProject.business.abstracts.PaymentService;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentRequest;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentWithSavedCreditCardRequest;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentsController {

	private PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	
	@PostMapping("/add")
	public Result add(CreatePaymentRequest entity) {
		return this.paymentService.add(entity);
	}
	
	
	@PostMapping("/addwithsavedcreditcard")
	public Result addWithSavedCreditCard(CreatePaymentWithSavedCreditCardRequest entity) {
		return this.paymentService.addWithSavedCreditCard(entity);
	}
	
}
