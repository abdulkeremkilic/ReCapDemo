package com.example.ReCapProject.core.utilities.results;

public class Result {
	
	private boolean success;
	private String messege;
	
	public Result(boolean success) {
		this.success = success;
	}
	
	public Result(boolean success, String messege) {
		this.success = success;
		this.messege = messege;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessege() {
		return messege;
	}
}
