package com.example.ReCapProject.entities.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class InvoiceDetailDto implements Dto {

	private String invoiceNo;
	private LocalDateTime creationDate;
	private int rentalId;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private long pickUpKilometer;
	private long returnKilometer;
	private double rentalPrice;
	private int carId;
	private String brandBrandName;
	private String modelName;
	private int modelYear;
}
