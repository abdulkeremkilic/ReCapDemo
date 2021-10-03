package com.example.ReCapProject.core.services.abstracts;

import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.requests.payment.CreatePaymentRequest;

public interface PosService {

	Result withdraw(CreatePaymentRequest entity);
	
}
