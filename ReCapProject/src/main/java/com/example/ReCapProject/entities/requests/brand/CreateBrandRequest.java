package com.example.ReCapProject.entities.requests.brand;

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
public class CreateBrandRequest implements Request {

	@NotNull
	@NotBlank
	private String brandName;
	
	@NotNull
	@NotBlank
	private String modelName;
	
	@NotNull
	private int modelYear;
}
