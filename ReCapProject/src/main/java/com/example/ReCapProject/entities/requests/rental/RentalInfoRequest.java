package com.example.ReCapProject.entities.requests.rental;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentalInfoRequest {

	private String brandName;
	private String modelName;
	private int modelYear;
	private long receivedByCustomerKm;
	private Date rentDate;
	
}
