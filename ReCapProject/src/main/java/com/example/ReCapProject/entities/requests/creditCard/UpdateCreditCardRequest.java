package com.example.ReCapProject.entities.requests.creditCard;

import javax.validation.constraints.NotBlank;

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
public class UpdateCreditCardRequest implements Request {

	@NotNull
	private int cardId;
	
	@NotNull
	@NotBlank
	private String cardName;
	
	@NotNull
	@NotBlank
	private String cardNumber;
	
	@NotNull
	@NotBlank
	private String cvvCode;
	
	@NotNull
	@NotBlank
	private String expireDate;
	
}
