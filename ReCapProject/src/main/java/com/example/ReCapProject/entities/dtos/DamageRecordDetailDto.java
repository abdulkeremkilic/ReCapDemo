package com.example.ReCapProject.entities.dtos;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class DamageRecordDetailDto implements Dto {

	private int carId;
	private String recordInfo;
}
