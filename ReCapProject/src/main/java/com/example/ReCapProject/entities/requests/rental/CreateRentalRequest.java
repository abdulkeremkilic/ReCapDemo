package com.example.ReCapProject.entities.requests.rental;

import java.util.List;

import javax.validation.constraints.NotBlank;

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
public class CreateRentalRequest implements Request{
	
	@NotNull
	private int carId;
	
	@NotNull
	private int userId;
	
	@NotNull
	private int pickUpCityId;
	
	@NotNull
	private int returnCityId;
	
	@NotNull
	@NotBlank
	private String rentDate;
	
	@NotNull
	@NotBlank
	private String returnDate;
	
	@Nullable
	private List<Integer> additionalServiceId;
	
}
