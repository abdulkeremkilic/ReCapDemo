package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class CreditCardDetailDto implements Dto {

	private int userId;
	private String cardBeholderName;
	private String creditCardNumber;
	private String cvvCode;
	private String expireDate;
}
