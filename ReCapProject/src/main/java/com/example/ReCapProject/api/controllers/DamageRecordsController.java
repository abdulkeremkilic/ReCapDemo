package com.example.ReCapProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReCapProject.business.abstracts.DamageRecordService;
import com.example.ReCapProject.core.utilities.results.DataResult;
import com.example.ReCapProject.core.utilities.results.Result;
import com.example.ReCapProject.entities.concretes.DamageRecord;
import com.example.ReCapProject.entities.requests.damageRecord.CreateDamageRecordRequest;
import com.example.ReCapProject.entities.requests.damageRecord.DeleteDamageRecordRequest;
import com.example.ReCapProject.entities.requests.damageRecord.UpdateDamageRecordRequest;

@RestController
@RequestMapping("/api/damagerecords")
@CrossOrigin
public class DamageRecordsController {

	private DamageRecordService damageRecordService;

	@Autowired
	public DamageRecordsController(DamageRecordService damageRecordService) {
		this.damageRecordService = damageRecordService;
	}
	
	@PostMapping("/add")
	public Result add(@Valid CreateDamageRecordRequest damageRecord) {
		return this.damageRecordService.add(damageRecord);
	}
	
	@PostMapping("/update")
	public Result update(@Valid UpdateDamageRecordRequest damageRecord) {
		return this.damageRecordService.update(damageRecord);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@Valid DeleteDamageRecordRequest damageRecord) {
		return this.damageRecordService.delete(damageRecord);
	}
	
	@GetMapping("getdamagerecordsbycarid")
	public DataResult<List<DamageRecord>> getDamageRecordsByCarId(int carId) {
		return this.damageRecordService.getDamageRecordsByCarId(carId);
	}
	
}
