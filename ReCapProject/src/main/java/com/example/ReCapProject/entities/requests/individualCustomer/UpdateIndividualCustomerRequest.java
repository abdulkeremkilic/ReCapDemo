package com.example.ReCapProject.entities.requests.individualCustomer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class UpdateIndividualCustomerRequest implements Request {

	@NotNull
	private int individualCustomerId;
	
	@NotNull
	@NotBlank
	private String firstName;
	
	@NotNull
	@NotBlank
	private String lastName;
	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	@Size(min = 11, max = 11)
	private String nationalId;
	
}
