package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class CreditCardDto implements Dto {

	private int userId;
	private String cardBeholderName;
	private String creditCardNumber;
	private String cvvCode;
	private String expireDate;
}
