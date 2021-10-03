package com.example.ReCapProject.entities.requests.color;

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
public class UpdateColorRequest implements Request{

	@NotNull
	private int colorId;
	
	@NotNull
	@NotBlank
	private String colorName;
	
}
