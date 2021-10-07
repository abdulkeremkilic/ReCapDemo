package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class BrandDto implements Dto {

	private String brandBrandName;
	private String modelName;
	private int modelYear;
}
