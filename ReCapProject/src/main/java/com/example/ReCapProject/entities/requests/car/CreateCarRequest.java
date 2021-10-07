package com.example.ReCapProject.entities.requests.car;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class CreateCarRequest implements Request {

	@NotNull
	private int brandId;
	
	@NotNull
	private int colorId;;
	
	@NotNull
	private int cityId;
	
	@NotNull
	@NotBlank
	private String description;
	
	@NotNull
	private long kilometer;
	
	@NotNull
	private double dailyPrice;
	
	@Max(1600)
	@Min(0)
	@NotNull
	private double minFindexPoint;
	
}
