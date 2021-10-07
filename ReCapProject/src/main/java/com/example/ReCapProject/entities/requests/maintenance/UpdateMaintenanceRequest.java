package com.example.ReCapProject.entities.requests.maintenance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMaintenanceRequest {

	private int maintenanceId;
	private boolean inMaintenance;
	
}
