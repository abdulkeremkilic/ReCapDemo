package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerDto implements Dto {
	
	// Learn how to draw info from List of Dto
	
	private int userId;
	private String email;
	private String companyName;
	
}
