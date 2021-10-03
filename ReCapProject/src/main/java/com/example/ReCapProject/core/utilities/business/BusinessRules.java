package com.example.ReCapProject.core.utilities.business;

import com.example.ReCapProject.core.utilities.results.Result;

public class BusinessRules {

	public static Result run(Result... logics) {
		for(var logic : logics) {
			if(!logic.isSuccess()) {
				return logic;
			}
		}
		return null;
	}
	
}
