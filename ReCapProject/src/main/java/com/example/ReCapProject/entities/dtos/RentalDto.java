package com.example.ReCapProject.entities.dtos;

import java.time.LocalDate;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto implements Dto {
	
	private int rentalId;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private boolean isReturned;
	private String brandName;
	private String modelName;
	private int modelYear;
	
}
