package com.example.ReCapProject.entities.requests.payment;

import com.example.ReCapProject.core.entities.abstracts.Request;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentWithSavedCreditCardRequest implements Request {

	@NotNull
	private int rentalId;
	
	@NotNull
	private int creditCardId;
}
