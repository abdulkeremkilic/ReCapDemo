package com.example.ReCapProject.entities.requests.city;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {

	@NotNull
	private int cityId;
	
	@NotNull
	@NotBlank
	private String cityName;
	
}
