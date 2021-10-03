package com.example.ReCapProject.entities.requests.damageRecord;

import com.example.ReCapProject.core.entities.abstracts.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDamageRecordRequest implements Request {

	private int recordId;
	
}
