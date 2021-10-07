package com.example.ReCapProject.entities.requests.corporateCustomer;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCorporateCustomerRequest {

	@NotNull
	private int userId;
}
