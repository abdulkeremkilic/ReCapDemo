package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class IndividualCustomerDetailDto implements Dto {

	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String nationalIdNumber;	
}
