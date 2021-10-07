package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto implements Dto {

	private double dailyPrice;
	private String brandBrandName;
	private String brandModelName;
	private int brandModelYear;
	private String colorName;
	private String cityName;
	private boolean isAvailable;
}
