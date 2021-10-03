package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetailDto implements Dto {

	private double dailyPrice;
	private String modelName;
	private String brandName;
	private int modelYear;
	private String colorName;
	private boolean isAvailable;
}
