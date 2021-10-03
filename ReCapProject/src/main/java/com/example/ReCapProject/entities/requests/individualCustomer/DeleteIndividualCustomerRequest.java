package com.example.ReCapProject.entities.requests.individualCustomer;

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
public class DeleteIndividualCustomerRequest implements Request {

	@NotNull
	private int userId;
	
}
