package com.example.ReCapProject.core.services;

// OutService(Bank)
public class PosOutService {

	public boolean posService(String cardBeholderName, String cardNumber, String cvvCode, String expireDate, double price) {
		
		double cardBalance = 5000;	// Simulation 
		
		if(cardBalance <= price)
			return false;
		
		return true;
	}
}
