package com.example.ReCapProject.entities.requests.carImage;

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
public class UpdateCarImageRequest implements Request {

	@NotNull
	private int carId;
	
	@NotNull
	private int carImageId;
}
