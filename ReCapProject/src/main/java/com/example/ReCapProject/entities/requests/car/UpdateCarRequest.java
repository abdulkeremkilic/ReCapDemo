package com.example.ReCapProject.entities.requests.car;

import javax.validation.constraints.NotBlank;

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
public class UpdateCarRequest implements Request  {

	@NotNull
	private int carId;
	
	@NotNull
	private int cityId;
	
	@NotNull
	private double dailyPrice;
	
	@NotNull
	@NotBlank
	private String description;
	
	@NotNull
	private long kilometer;
	
	@NotNull
	private boolean isAvailable;
	
}
