package com.example.ReCapProject.entities.requests.additionalService;

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
public class UpdateAdditionalServiceRequest implements Request {

	@NotNull
	private int additionalServiceId;
	
	@NotNull
	private double additionalServiceFee;
	
	@NotNull
	@NotBlank
	private String additionalServiceName;
}
