package com.example.ReCapProject.entities.dtos;

import java.time.LocalDate;
import java.util.List;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class RentalDto implements Dto {
	
	private int rentalId;
	private long pickUpKilometer;
	private long returnKilometer;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private String brandBrandName;
	private String modelName;
	private int modelYear;
	private boolean isPayed;	
	private List<AdditionalServiceDto> additionalServices;
}
