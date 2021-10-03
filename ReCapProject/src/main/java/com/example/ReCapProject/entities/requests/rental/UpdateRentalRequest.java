package com.example.ReCapProject.entities.requests.rental;

import com.example.ReCapProject.core.entities.abstracts.Request;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest implements Request  {

	@NotNull
	private int rentalId;
	
	@NotNull
	private int returnCityId;
	
	@NotNull
	private String returnDate;
	
	@NotNull
	private long returnKilometer;
	
	@NotNull
	private boolean isReturned;
	
}
