package com.example.ReCapProject.entities.dtos;

import java.time.LocalDateTime;

import com.example.ReCapProject.core.entities.abstracts.Dto;

import lombok.Data;

@Data
public class MaintenanceDetailDto implements Dto {

	private int carId;
	private boolean inMaintenance;
	private LocalDateTime maintenanceDate;
}
