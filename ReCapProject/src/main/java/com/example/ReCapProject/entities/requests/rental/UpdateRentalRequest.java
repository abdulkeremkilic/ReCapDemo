package com.example.ReCapProject.entities.requests.rental;

import java.util.List;

import com.example.ReCapProject.core.entities.abstracts.Request;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

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
	private boolean isReturned = false;
	
	@Nullable
	private List<Integer> additionalServiceId;
	
}
