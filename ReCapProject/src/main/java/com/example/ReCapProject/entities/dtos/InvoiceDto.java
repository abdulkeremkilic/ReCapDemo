package com.example.ReCapProject.entities.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto implements Dto{

	private String invoiceNo;
	private LocalDateTime creationDate;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private long pickUpKilometer;
	private long returnKilometer;
	private double rentalPrice;
}
