package com.example.ReCapProject.entities.requests.creditCard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardInfoRequest {

	private String cardBeholderName;
	private String cardNumber;
	
}
