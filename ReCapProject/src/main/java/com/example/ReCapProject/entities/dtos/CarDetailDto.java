package com.example.ReCapProject.entities.dtos;

import java.util.List;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetailDto implements Dto {

	private int carId;
	private double dailyPrice;
	private String brandBrandName;
	private String brandModelName;
	private int brandModelYear;
	private String colorName;
	private String cityName;
	private boolean isAvailable;
	private List<CarImageDetailDto> carrImageDtos;
}
