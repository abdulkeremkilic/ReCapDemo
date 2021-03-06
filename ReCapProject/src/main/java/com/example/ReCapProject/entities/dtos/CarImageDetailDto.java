package com.example.ReCapProject.entities.dtos;

import java.time.LocalDateTime;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class CarImageDetailDto implements Dto {

	private String carImage;
	private LocalDateTime date;
}
