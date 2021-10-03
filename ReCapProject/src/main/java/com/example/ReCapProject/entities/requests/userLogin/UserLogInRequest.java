package com.example.ReCapProject.entities.requests.userLogin;

import javax.validation.constraints.NotBlank;

import com.example.ReCapProject.core.entities.abstracts.Request;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLogInRequest implements Request{

	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
	
}
