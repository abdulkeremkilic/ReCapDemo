package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class AdditionalServiceDto implements Dto {
	
	private String serviceName;
	private double serviceFee;
}
