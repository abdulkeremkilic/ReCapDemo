package com.example.ReCapProject.entities.requests.creditCard;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.example.ReCapProject.core.entities.abstracts.Request;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest implements Request{

	@NotNull
	private int userId;
	
	@NotNull
	@NotBlank
	private String cardBeholderName;
	
	@NotNull
	@NotBlank
	@CreditCardNumber(ignoreNonDigitCharacters = true)
	private String cardNumber;
	
	@NotNull
	@NotBlank
	private String cvvCode;
	
	@NotNull
	@NotBlank
	private String expireDate;
	
	@Nullable
	private boolean saveCard = false;
	
}
