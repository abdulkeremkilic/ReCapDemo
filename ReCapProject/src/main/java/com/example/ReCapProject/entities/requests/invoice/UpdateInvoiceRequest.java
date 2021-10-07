package com.example.ReCapProject.entities.requests.invoice;

import com.example.ReCapProject.core.entities.abstracts.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest implements Request{

	private int invoiceId;
	
	
}
